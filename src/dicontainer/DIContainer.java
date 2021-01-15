package dicontainer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DIContainer
{
    private final Map<Class, Object> mapping;

    private static DIContainer instance;

    private DIContainer()
    {
        this.mapping = new HashMap<>();
    }

    public static DIContainer getInstance()
    {
        if (instance == null) {
            instance = new DIContainer();
        }
        return instance;
    }

    public <T> void setModule(Class from, T to)
    {
        to = (T) getModule((Class) to);

        if ( ! from.isInterface()) {
            throw new ContainerException(String.format("%s is not an interface.", from.getName()));
        }

        if ( ! from.isAssignableFrom(to.getClass())) {
            throw new ContainerException(String.format("The concrete class %s must implement the interface %s.", to.getClass().getName(), from.getName()));
        }

        getInstance().mapping.put(from, to);
    }


    private <T> T getClassFromInterface(Class<T> TInterface)
    {
        if(TInterface.isInterface()) {
            T result = (T) getInstance().mapping.get(TInterface);

            if (result != null) {
                return result;
            }
            throw new ContainerException(String.format("There is no binding specified for the interface %s", TInterface.getName()));
        }

        return null;
    }

    public <T> T getModule(Class<T> TInterface, Object... params)
    {
        T result;

        result = getClassFromInterface(TInterface);
        if (result != null)
            return result;
        
        return createModule(TInterface, params);
    }

    private <T> T createModule(Class<T> Module, Object[] params) {
        try
        {
            Constructor constructor = getInstance().getConstructor(Module);

            List<Object> parameters = new ArrayList<>();

            Class[] parameterTypes = constructor.getParameterTypes();

            for(Object param : params) {
                parameters.add(param);
            }

            for (int i = parameters.size(); i < parameterTypes.length; i++) {
                parameters.add(getModule(parameterTypes[i]));
            }

            return (T) constructor.newInstance(parameters.toArray());
        }
        catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
        {
            throw new ContainerException("An Exception has been thrown when making a Class", ex);
        }
    }

    public static void clear()
    {
        getInstance().mapping.clear();
    }

    private <T> Constructor getConstructor(Class<T> Module)
    {
        Constructor[] constructors = Module.getConstructors();
        
        if(constructors.length == 0)
            throw new ContainerException("This container does not support singleton ");
        
        return constructors[0];
    }
}
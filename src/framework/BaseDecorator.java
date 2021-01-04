package framework;

import framework.IFramework;

public abstract class BaseDecorator implements IFramework {
    private IFramework wrappee;

    public BaseDecorator(IFramework _wrappee){
        this.wrappee = _wrappee;
    }

    @Override
    public void connect(String _type, String _url, String _user, String _password, String _database) {

    }

    @Override
    public void form(String _table) {

    }
}

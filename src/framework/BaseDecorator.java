package framework;

import framework.IFramework;
import framework.implementation.API;

public abstract class BaseDecorator implements IFramework {
    protected IFramework wrappee;

    public BaseDecorator(IFramework _wrappee){
        this.wrappee = _wrappee;
    }

    @Override
    public void connect(ProxyFramework.DatabaseType _type, String _url, String _user, String _password, String _database) {
        wrappee.connect(_type, _url, _user, _password, _database);
    }

    @Override
    public void form(String _table) {
        wrappee.form(_table);
    }
}

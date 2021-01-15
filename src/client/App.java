package client;

import framework.IFramework;
import framework.ProxyFramework;

public class App {
    IFramework framework;

    public App(IFramework _framework){
        this.framework = _framework;
    }

    public void Connect(ProxyFramework.DatabaseType _type,
            String _url,
            String _user,
            String _password,
            String _database){
        framework.connect(_type, _url, _user, _password, _database);
    }
    public void Form(String _table){
        framework.form(_table);
    }
}

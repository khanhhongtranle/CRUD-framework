package framework.factory;

import framework.implementation.API;
import framework.implementation.MSSQL;

public class MSSQLCreator extends APIFactory {
    @Override
    public API createImplementation(String _url, String _user, String _password, String _database) {
        return new MSSQL(_url, _user, _password, _database);
    }
}

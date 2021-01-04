package framework.factory;

import framework.implementation.API;
import framework.implementation.MySQL;

public class MySQLCreator extends APIFactory {
    @Override
    public API createImplementation(String _url, String _user, String _password, String _database) {
        return new MySQL(_url, _user, _password, _database);
    }
}

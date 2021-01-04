package framework.factory;

import framework.implementation.API;

public abstract class APIFactory {

    public APIFactory(){

    }

    public static API create(String _type, String _url, String _user, String _password, String _database){
        API api = null;
        switch (_type){
            case "MYSQL":
                MySQLCreator creator = new MySQLCreator();
                api = creator.createImplementation(_url, _user, _password, _database);
                break;
            default:
                System.out.println("This database type is not supported.");
        }
        return api;
    }

    public abstract API createImplementation(String _url, String _user, String _password, String _database);
}

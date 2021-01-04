package framework.implementation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class MySQL implements API {

    private String url;
    private String user;
    private String password;
    private String database;
    private Connection connection = null;

    public MySQL(String _url, String _user, String _password, String _database){
        this.url = _url;
        this.user = _user;
        this.password = _password;
        this.database = _database;
    }

    @Override
    public Connection connectToDatabase() {
        System.out.println("Connected");
        if (connection != null){
            return connection;
        }else{
            try{
                connection = DriverManager.getConnection("jdbc:mysql://"+ this.url +"/" + this.database + "?useLegacyDatetimeCode=false&serverTimezone=America/New_York",this.user,this.password);
                return connection;
            } catch (Exception ex) {
                System.out.println(ex);
                return null;
            }
        }
    }

    @Override
    public ArrayList<String> getListOfColumns(String _table) {
        return null;
    }

    @Override
    public ArrayList<Object[]> getListOfRows(String _table) {
        return null;
    }
}

package framework;

import framework.abstraction.GUIDetails;
import framework.factory.APIFactory;
import framework.implementation.API;

import java.awt.*;

/**
 * core
 */
public class Framework implements IFramework {

    private static final Framework INSTANCE = new Framework();

    private API api = null;

    private Framework(){

    }

    public static Framework getInstance(){
        return INSTANCE;
    }

    /**
     *
     * @param _type: MYSQL, MSSQL, CSV
     * @param _url
     * @param _user
     * @param _password
     * @param _database
     */
    @Override
    public void connect(String _type, String _url, String _user, String _password, String _database) {
        this.api = APIFactory.create(_type, _url,_user,_password, _database);
    }

    /**
     *
     * @param _table: your table's name
     */
    @Override
    public void form(String _table) {
        try{
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    GUIDetails guiDetails = new GUIDetails(api);
                    guiDetails.connectToDatabase();
                    guiDetails.initComponents(_table);
                    guiDetails.setVisible();
                }
            });
        }
       catch (Exception ex){
            ex.printStackTrace();
       }
    }
}

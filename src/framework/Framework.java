package framework;

import framework.abstraction.GUIDetails;
import framework.factory.APIFactory;
import framework.implementation.API;

import java.awt.*;

/**
 * core
 */
public class Framework implements IFramework {
    private API api = null;

    @Override
    public void connect(String _type, String _url, String _user, String _password, String _database) {
        this.api = APIFactory.create(_type, _url,_user,_password, _database);
    }

    @Override
    public void form(String _table) {
        try{
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    GUIDetails guiDetails = new GUIDetails(api);
                    guiDetails.connectToDatabase();
                    guiDetails.initComponents(_table);
                }
            });
        }
       catch (Exception ex){
            ex.printStackTrace();
       }
    }
}

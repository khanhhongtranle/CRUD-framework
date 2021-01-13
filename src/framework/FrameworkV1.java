package framework;

import framework.abstraction.GUIDetails;
import framework.factory.APIFactory;
import framework.implementation.API;

import java.awt.*;
import java.sql.SQLException;

public class FrameworkV1 extends BaseDecorator {
    private API api = null;

    public FrameworkV1(IFramework _wrappee) {
        super(_wrappee);
    }

    @Override
    public void connect(String _type, String _url, String _user, String _password, String _database) {
        super.connect(_type,_url, _user, _password, _database);
        this.api = APIFactory.create(_type, _url, _user, _password, _database);
    }

    /*
     * membership features:
     * - create membership table
     * - create new user and password
     * - Authenticating users who visit your site
     */

    public void createMembership() {
        try {
            api.connectToDatabase();
            api.createMemberShipTable();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public boolean validateMember(String _username, String _password) {
        try{
            return api.validateMembership(_username, _password);
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

}

package framework;

import framework.abstraction.GUIDetails;
import framework.factory.APIFactory;
import framework.implementation.API;

import java.awt.*;
import java.sql.SQLException;
/*
 * membership features:
 * - create membership table
 * - create new user and password
 * - Authenticating users who visit your site
 */

public class FrameworkV1 extends BaseDecorator {
    private API api = null;

    public FrameworkV1(IFramework _wrappee) {
        super(_wrappee);
    }

    @Override
    public void connect(ProxyFramework.DatabaseType _type, String _url, String _user, String _password, String _database) {
        super.connect(_type,_url, _user, _password, _database);
        this.api = APIFactory.create(_type, _url, _user, _password, _database);
    }


    /**
     * create if not exists membership_users table in your database
     */
    public void createMembership() {
        try {
            api.connectToDatabase();
            api.createMemberShipTable();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Authenticating user
     * @param _username
     * @param _password
     * @return
     */
    public boolean validateMember(String _username, String _password) {
        try{
            return api.validateMembership(_username, _password);
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

}

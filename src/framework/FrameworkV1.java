package framework;

import framework.factory.APIFactory;
import framework.implementation.API;

public class FrameworkV1 extends BaseDecorator{
    private  IFramework wrappee = null;
    private API api = null;

    public FrameworkV1(IFramework _wrappee) {
        super(_wrappee);
        wrappee = _wrappee;
    }

    @Override
    public void connect(String _type, String _url, String _user, String _password, String _database) {
        this.api = APIFactory.create(_type, _url,_user,_password, _database);
    }

    /*
     * membership features:
     * - create membership table
     * - create new user and password
     * - Authenticating users who visit your site
     */

    public void createMembership(){
        try{
            api.connectToDatabase();
            api.createMemberShipTable();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void createMember(){

    }

    public boolean validateMember(){
        return true;
    }

}

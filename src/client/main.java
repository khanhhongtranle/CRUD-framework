package client;
import framework.APIFactory;
import framework.MySQLCreator;
import framework.abstraction.GUIDetails;
import framework.implementation.API;

import java.awt.*;

public class main {
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                APIFactory apiFactory = new MySQLCreator();
                API api = apiFactory.create();
                GUIDetails guiDetails = new GUIDetails(api);
                guiDetails.connectToDatabase();
                guiDetails.initComponents();
            }
        });
    }
}

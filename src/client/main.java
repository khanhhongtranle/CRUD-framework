package client;
import framework.factory.APIFactory;
import framework.factory.MySQLCreator;
import framework.abstraction.GUIDetails;
import framework.implementation.API;

import java.awt.*;

public class main {
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                API api = APIFactory.create("MYSQL","localhost:3306","root","", "funretro");
                GUIDetails guiDetails = new GUIDetails(api);
                guiDetails.connectToDatabase();
                guiDetails.initComponents();
            }
        });
    }
}

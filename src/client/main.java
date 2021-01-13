package client;

import framework.Framework;
import framework.FrameworkV1;
import framework.IFramework;

public class main {
    public static void main(String[] args){
        //Framework framework = Framework.getInstance();
//        //framework.membership();
//        framework.connect("MYSQL", "localhost:3306", "root", "123456", "funretro");
//        framework.form("users");
//        framework.connect("MSSQL", "localhost:1433", "temp", "12345", "AdventureWorksDW2012");
//        framework.form("FactFinance");

        FrameworkV1 frameworkV1 = new FrameworkV1(Framework.getInstance());
        frameworkV1.connect("MYSQL", "localhost:3306", "root", "123456", "funretro");
        frameworkV1.createMembership();
    }
}

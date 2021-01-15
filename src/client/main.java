package client;

import framework.Framework;
import framework.FrameworkV1;
import framework.IFramework;
import framework.ProxyFramework;

import java.sql.SQLException;

public class main {
    public static void main(String[] args) {
   //     Framework framework = Framework.getInstance();
//        //framework.membership();
//        framework.connect("MYSQL", "localhost:3306", "root", "123456", "funretro");
//        framework.form("users");

 //       framework.connect(, "localhost:1433", "temp", "12345", "AdventureWorksDW2012");
   //     framework.form("FactFinance");

        /*
         framework with singleton pattern

//        FrameworkV1 frameworkV1 = new FrameworkV1(Framework.getInstance());
//        frameworkV1.connect("MYSQL", "localhost:3306", "root", "123456", "funretro");
//        frameworkV1.createMembership();
//       // frameworkV1.form("membership_users");
//        if (frameworkV1.validateMember("admin1", "admin")){
//            System.out.println("Is member");
//        }else{
//            System.out.println("Is not member");
//        }

        /*
        framework with virtual proxy pattern
        */

        ProxyFramework proxyFramework = new ProxyFramework();
        proxyFramework.connect(ProxyFramework.DatabaseType.MSSQL, "localhost:1433", "phuc", "87435", "AdventureWorksDW2012");
        proxyFramework.form("membership_users");

        FrameworkV1 frameworkV1 = new FrameworkV1(proxyFramework);
        frameworkV1.connect(ProxyFramework.DatabaseType.MSSQL, "localhost:1433", "phuc", "87435", "AdventureWorksDW2012");
        frameworkV1.createMembership();
        frameworkV1.form("membership_users");


/*
        ProxyFramework proxyFramework = new ProxyFramework();
        proxyFramework.connect(ProxyFramework.DatabaseType.MySQL, "localhost:3306", "root", "87435", "sakila");
        proxyFramework.form("city");

        FrameworkV1 frameworkV1 = new FrameworkV1(proxyFramework);
        frameworkV1.connect(ProxyFramework.DatabaseType.MySQL, "localhost:3306", "root", "87435", "sakila");
        frameworkV1.form("membership_users");



 */

    }
}

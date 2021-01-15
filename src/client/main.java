package client;

import framework.Framework;
import framework.FrameworkV1;
import framework.IFramework;
import framework.ProxyFramework;
import dicontainer.*;
import java.sql.SQLException;

public class main {
    public static void main(String[] args) {

/*
        ProxyFramework proxyFramework = new ProxyFramework();
        proxyFramework.connect(ProxyFramework.DatabaseType.MSSQL, "localhost:1433", "phuc", "87435", "AdventureWorksDW2012");
        proxyFramework.form("FactFinance");

        //proxyFramework.connect(ProxyFramework.DatabaseType.MSSQL, "localhost:1433", "phuc", "87435", "temp");
        //proxyFramework.form("BOOK");


        FrameworkV1 frameworkV1 = new FrameworkV1(proxyFramework);
        //frameworkV1.connect(ProxyFramework.DatabaseType.MSSQL, "localhost:1433", "phuc", "87435", "AdventureWorksDW2012");
        frameworkV1.createMembership();
        frameworkV1.form("membership_users");

*/
        DIContainer container = DIContainer.getInstance();
        container.setModule(IFramework.class, Framework.class);

        App app = new App(new Framework());

        app.Connect(ProxyFramework.DatabaseType.MSSQL, "localhost:1433", "phuc", "87435", "temp");
        app.Form("BOOK");


    }
}
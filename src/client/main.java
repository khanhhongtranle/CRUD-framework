package client;

import framework.Framework;
import framework.FrameworkV1;
import framework.IFramework;
import framework.ProxyFramework;

import java.sql.SQLException;

public class main {
    public static void main(String[] args) {
        ProxyFramework proxyFramework = new ProxyFramework();
        proxyFramework.connect(ProxyFramework.DatabaseType.MySQL, "localhost:3306", "root", "123456", "funretro");
        proxyFramework.form("boards");

        FrameworkV1 frameworkV1 = new FrameworkV1(proxyFramework);
        frameworkV1.connect(ProxyFramework.DatabaseType.MySQL, "localhost:3306", "root", "123456", "funretro");
        frameworkV1.createMembership();
        frameworkV1.form("membership_users");
        if (frameworkV1.validateMember("admin", "admin")){
            System.out.println("Is a member");
        }else{
            System.out.println("Is not a member");
        }
    }
}

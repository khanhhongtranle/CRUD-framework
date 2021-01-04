package client;

import framework.Framework;

public class main {
    public static void main(String[] args){
        Framework framework = new Framework();
        //framework.membership();
        framework.connect("MYSQL", "localhost:3306", "root", "", "funretro");
        framework.form("users");
    }
}

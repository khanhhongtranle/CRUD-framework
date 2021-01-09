package framework.abstraction;

import framework.implementation.API;

import java.util.ArrayList;

public abstract class GUI {
    protected API api;

    public GUI(API _api){
        this.api = _api;
    }

    public abstract void connectToDatabase();
    public abstract void initComponents(String _table);
    public abstract void setVisible();
    protected abstract ArrayList<String> getListOfColumnsName(String _table);
//    protected abstract ArrayList<Object[]> getListOfRows(String _table);
}

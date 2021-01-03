package framework.abstraction;

import framework.implementation.API;

public abstract class GUI {
    protected API api;

    public GUI(API _api){
        this.api = _api;
    }

    public abstract void connectToDatabase();
    public abstract void initComponents();
}

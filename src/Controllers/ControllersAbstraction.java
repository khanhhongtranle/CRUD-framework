package Controllers;

import Views.ViewsInterface;

public abstract class ControllersAbstraction {
    protected ViewsInterface viewInterface;

    public ControllersAbstraction(ViewsInterface _viewInterface){
        this.viewInterface = _viewInterface;
    }

    public abstract void initializeComponents();
    public abstract void setViewVisible();
}

package Views;

import Controllers.ControllersInterface;

import javax.swing.*;

public abstract class ViewsAbstraction{
    protected ControllersInterface controllersInterface;

    public ViewsAbstraction(ControllersInterface conInterface){
        this.controllersInterface = conInterface;
    }

    public abstract void initializeComponents();
    public abstract void setViewVisible();
}

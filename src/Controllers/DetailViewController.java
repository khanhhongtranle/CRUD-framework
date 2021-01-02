package Controllers;

import ViewPrototypes.DetailViewPrototype;
import ViewPrototypes.Prototype;
import Views.DetailView;

import javax.swing.*;

public class DetailViewController implements ControllersInterface{

    public DetailViewController(){
    }

    @Override
    public void initializeComponents() {
        System.out.println("Init componenents");
    }

    @Override
    public void setViewVisible() {
        System.out.println("Set view visible");
    }
}

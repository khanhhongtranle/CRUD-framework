package test;

import Controllers.DetailViewController;
import ViewPrototypes.DetailViewPrototype;
import Views.DetailView;

import java.awt.*;

public class main {
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                DetailView detailView = new DetailView(new DetailViewController());
                detailView.initializeComponents();
                detailView.setViewVisible();
            }
        });
    }
}

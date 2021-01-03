package test;

import Controllers.DetailViewController;
import Models.Model;
import Views.DetailView;

import java.awt.*;
import java.util.*;
import java.util.List;

public class main {
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                List<Model> list = new ArrayList<Model>();
                list.add(new Model("1","John"));
                list.add(new Model("2","Mark"));
                DetailView detailViewPrototype = new DetailView("Contacts", list);

                DetailViewController detailViewController = new DetailViewController(detailViewPrototype);
                detailViewController.initializeComponents();
                detailViewController.setViewVisible();
            }
        });
    }
}

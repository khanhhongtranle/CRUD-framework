package Controllers;

import Views.ViewsInterface;

public class DetailViewController extends ControllersAbstraction {

    private ViewsInterface view;

    public DetailViewController(ViewsInterface _viewInterface){
        super(_viewInterface);
        this.view = _viewInterface;
    }

    @Override
    public void initializeComponents() {
        System.out.println("Init componenents");
        this.view.initializeComponents();
    }

    @Override
    public void setViewVisible() {
        System.out.println("Set view visible");
        this.view.setViewVisible();
    }
}

package framework;

import framework.implementation.API;

public abstract class APIFactory {

    public APIFactory(){

    }

    public abstract API create();
}

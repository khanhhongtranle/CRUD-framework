package framework;

import framework.implementation.API;
import framework.implementation.MySQL;

public class MySQLCreator extends APIFactory {
    @Override
    public API create() {
        return new MySQL();
    }
}

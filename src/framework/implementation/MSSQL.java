package framework.implementation;

import java.sql.Connection;
import java.util.ArrayList;

public class MSSQL implements API {
    @Override
    public Connection connectToDatabase() {
        return null;
    }

    @Override
    public ArrayList<String> getListOfColumns(String _table) {
        return null;
    }

    @Override
    public String getColumnType(String _table, String _column) {
        return null;
    }

    @Override
    public ArrayList<Object[]> getListOfRows(String _table) {
        return null;
    }

    @Override
    public boolean insert(String _table, ArrayList<String> values) {
        return false;
    }
}

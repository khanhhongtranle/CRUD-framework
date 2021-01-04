package framework.implementation;

import java.sql.Connection;
import java.util.ArrayList;

public interface API {
    Connection connectToDatabase();
    ArrayList<String> getListOfColumns(String _table);
    ArrayList<Object[]> getListOfRows(String _table);
    void insert(String _table, String values);
}

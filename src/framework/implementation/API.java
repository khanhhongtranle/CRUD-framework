package framework.implementation;

import java.sql.Connection;
import java.util.ArrayList;

public interface API {
    Connection connectToDatabase();
    ArrayList<String> getListOfColumns(String _table);
    String getColumnType(String _table, String _column);
    ArrayList<Object[]> getListOfRows(String _table);
    boolean insert(String _table, ArrayList<String> values);
}

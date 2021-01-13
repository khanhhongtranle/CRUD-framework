package framework.implementation;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface API {
    Connection connectToDatabase();
    ArrayList<String> getListOfColumns(String _table);
    String getColumnType(String _table, String _column);
    ArrayList<Object[]> getListOfRows(String _table);
    ArrayList<Object> getARecord(String _table, Object id);
    String getPrimaryKey(String _table);
    boolean insert(String _table, ArrayList<String> values);
    /**
     * delete a row where table.id = id
     * @param _table
     * @param id
     */
    boolean delete(String _table, Object id);
    boolean update(String _table, Object id, ArrayList<String> values);
    void createMemberShipTable();
    boolean validateMembership(String _username, String _password) throws SQLException;
}

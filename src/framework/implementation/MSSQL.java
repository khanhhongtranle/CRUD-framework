package framework.implementation;

import java.sql.*;
import java.util.ArrayList;

public class MSSQL implements API {

    private String url;
    private String user;
    private String password;
    private String database;
    private Connection connection = null;

    public MSSQL(String _url, String _user, String _password, String _database) {
        this.url = _url;
        this.user = _user;
        this.password = _password;
        this.database = _database;
    }

    @Override
    public Connection connectToDatabase() {
        try {
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            connection = DriverManager.getConnection("jdbc:sqlserver://" + url
                    + ";databaseName=" + database +";"
                    , user
                    , password);
            return connection;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<String> getListOfColumns(String _table) {
        ArrayList<String> columnNames = new ArrayList<>();
        try {
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from " + _table);
            ResultSetMetaData rsMetaData = rs.getMetaData();
            System.out.println("List of column names in the current table: ");
            int count = rsMetaData.getColumnCount();
            for (int i = 1; i <= count; i++) {
                System.out.println(rsMetaData.getColumnName(i));
                columnNames.add(rsMetaData.getColumnName(i));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return columnNames;
    }

    @Override
    public String getColumnType(String _table, String _column) {
        String columnsType = "varchar";

        try {
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery("select data_type from information_schema.columns where table_name = "
                    + _table
                    + " and column_name = "
                    + _column);
            while (rs.next()) {
                columnsType = rs.getString("DATA_TYPE");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return columnsType;
    }

    @Override
    public ArrayList<Object[]> getListOfRows(String _table) {
        String query = "SELECT * FROM " + _table;
        String[] columnNames = getListOfColumns(_table).toArray(new String[0]);

        ArrayList<Object[]> listOfRows = new ArrayList<>();
        Statement st;
        ResultSet rs;

        try {
            st = this.connection.createStatement();
            rs = st.executeQuery(query);
            Object[] sth;
            while (rs.next()) {
                sth = new Object[columnNames.length];
                for (int i = 0; i < columnNames.length; i++) {
                    sth[i] = rs.getString(columnNames[i]);
                }
                listOfRows.add(sth);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listOfRows;
    }

    public int getRowID(String _table) {

        return 0;
    }



    @Override
    public String getPrimaryKey(String _table) {
        String PK = null;
        try {
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COLUMN_NAME " +
                    "FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE " +
                    "where TABLE_NAME = '" + _table +
                    "' and CONSTRAINT_NAME LIKE '%PK%' OR CONSTRAINT_NAME LIKE '%PRIMARY KEY%'");
            while (rs.next()) {
                PK = rs.getString("COLUMN_NAME");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return PK;
    }

    @Override
    public ArrayList<Object> getARecord(String _table, Object id) {
        String PK = getPrimaryKey(_table);
        String query = "SELECT * FROM " + _table + " WHERE " + PK + " = ?";
        String[] columnNames = getListOfColumns(_table).toArray(new String[0]);

        ArrayList<Object> aRecord = new ArrayList<>();
        PreparedStatement st;
        ResultSet rs;

        try {
            st = this.connection.prepareStatement(query);
            st.setObject(1, id);
            rs = st.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < columnNames.length; i++) {
                    aRecord.add(rs.getString(columnNames[i]));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return aRecord;
    }

    @Override
    public boolean insert(String _table, ArrayList<String> values) {
        return false;
    }

    @Override
    public boolean delete(String _table, Object id) {
        return true;
    }

    @Override
    public boolean update(String _table, Object id, ArrayList<String> values) {
        return false;
    }
}

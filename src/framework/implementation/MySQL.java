package framework.implementation;

import java.sql.*;
import java.util.ArrayList;

public class MySQL implements API {

    private String url;
    private String user;
    private String password;
    private String database;
    private Connection connection = null;

    public MySQL(String _url, String _user, String _password, String _database) {
        this.url = _url;
        this.user = _user;
        this.password = _password;
        this.database = _database;
    }

    @Override
    public Connection connectToDatabase() {
        System.out.println("Connected");
        if (connection != null) {
            return connection;
        } else {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + this.url + "/" + this.database + "?useLegacyDatetimeCode=false&serverTimezone=America/New_York", this.user, this.password);
                return connection;
            } catch (Exception ex) {
                System.out.println(ex);
                return null;
            }
        }
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
            ResultSet rs = stmt.executeQuery("select data_type from information_schema.columns where table_schema = '" + database + "' and table_name = '" + _table + "' and column_name='" + _column + "'");
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

    @Override
    public boolean insert(String _table, ArrayList<String> values) {
        boolean result = false;
        String[] columnNames = getListOfColumns(_table).toArray(new String[0]);
        StringBuilder table_columns = new StringBuilder();
        StringBuilder param_values = new StringBuilder();
        for (int i = 0; i < columnNames.length; i++) {
            table_columns.append(columnNames[i]);
            param_values.append("?");
            if (i < columnNames.length - 1) {
                table_columns.append(",");
                param_values.append(",");
            }
        }
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO " + _table + "(" + table_columns.toString() + ") " + "VALUES " + "(" + param_values.toString() + ")");

            for (int index = 0; index < columnNames.length; index++){
                String columnType = getColumnType(_table, columnNames[index]);

                switch (columnType.toUpperCase()){
                    case "CHAR":
                    case "VARCHAR":
                    case "TEXT":
                        preparedStatement.setString(index+1, values.get(index));
                        break;
                    case "INT":
                        preparedStatement.setInt(index+1, Integer.parseInt(values.get(index)));
                        break;
                    case "DOUBLE":
                        preparedStatement.setDouble(index+1, Double.parseDouble(values.get(index)));
                        break;
                    case "FLOAT":
                        preparedStatement.setFloat(index+1, Float.parseFloat(values.get(index)));
                        break;
                    case "DATE":
                    case "TIME":
                    case "DATETIME":
                    case "TIMESTAMP":
                        preparedStatement.setDate(index+1, Date.valueOf(values.get(index)));
                    default:
                        preparedStatement.setString(index+1, values.get(index));
                }
            }
            int row = preparedStatement.executeUpdate();
            // rows affected
            System.out.println(row); //1
            if (row == 1){
                result = true;
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            result = false;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

//    @Override
//    public void insert(String _table, String values) {
//        Statement sm = null;
//
//
//        try {
//            sm = this.connection.createStatement();
//            String[] columnNames = getListOfColumns(_table).toArray(new String[0]);
//                    /*
//                    INSERT INTO table_name (column1, column2, column3, ...)
//                    VALUES (value1, value2, value3, ...);
//                    */
//            StringBuilder table_columns = new StringBuilder();
//            for (int i = 0; i < columnNames.length; i++) {
//                table_columns.append(columnNames[i]);
//                if (i < columnNames.length - 1) table_columns.append(",");
//            }
//            String sql = "INSERT INTO " + _table + "(" + table_columns.toString() + ") " + "VALUES " + "(" + values + ")";
//            System.out.println(sql);
//            int n = sm.executeUpdate (sql);
//            if (n >= 0){
//                System.out.println("Success");
//            }else{
//                System.out.println("Error");
//            }
//            sm.close();
//            //this.connection.close();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }


}

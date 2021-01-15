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
    
                for (int index = 0; index < columnNames.length; index++) {
                    String columnType = getColumnType(_table, columnNames[index]);
    
                    switch (columnType.toUpperCase()) {
                        case "CHAR":
                        case "VARCHAR":
                        case "TEXT":
                            preparedStatement.setString(index + 1, values.get(index));
                            break;
                        case "INT":
                            preparedStatement.setInt(index + 1, Integer.parseInt(values.get(index)));
                            break;
                        case "DOUBLE":
                            preparedStatement.setDouble(index + 1, Double.parseDouble(values.get(index)));
                            break;
                        case "FLOAT":
                            preparedStatement.setFloat(index + 1, Float.parseFloat(values.get(index)));
                            break;
                        case "DATE":
                        case "TIME":
                        case "DATETIME":
                        case "TIMESTAMP":
                            preparedStatement.setDate(index + 1, Date.valueOf(values.get(index)));
                        default:
                            preparedStatement.setString(index + 1, values.get(index));
                    }
                }
                int row = preparedStatement.executeUpdate();
                // rows affected
                System.out.println(row); //1
                if (row == 1) {
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
    
        @Override
        public boolean delete(String _table, Object id) {
            boolean result = true;
            try {
                PreparedStatement st = connection.prepareStatement("DELETE FROM " + _table + " WHERE " + getPrimaryKey(_table) + " = ?");
                st.setObject(1, id);
                st.executeUpdate();
            } catch (SQLException e) {
                result = false;
                System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            } catch (Exception e) {
                result = false;
                e.printStackTrace();
            }
            return result;
        }
    
        @Override
        public boolean update(String _table, Object _id, ArrayList<String> _values) {
    
            //delete and insert
            if (delete(_table, _id)) {
                return insert(_table, _values);
            }
            return false;
        }
    
        @Override
        public void createMemberShipTable() {
            String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS  membership_users ("
                    + "ID INT NOT NULL,"
                    + "USERNAME VARCHAR(45) NOT NULL,"
                    + "PASSWORD VARCHAR(45) NOT NULL,"
                    + "EMAIL VARCHAR(45),"
                    + "PRIMARY KEY (ID))";
            Statement stmt = null;
    
            try {
                stmt = this.connection.createStatement();
    
                stmt.executeUpdate(CREATE_TABLE_SQL);
    
                System.out.println("membership_users table created");
    
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    // Close connection
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    
        @Override
        public boolean validateMembership(String _username, String _password) throws SQLException {
            boolean result = false;
            PreparedStatement st = null;
            ResultSet rs;
            String query = "SELECT * FROM membership_users  WHERE username = ? and password = ?";
            try {
                st = this.connection.prepareStatement(query);
                st.setString(1, _username);
                st.setString(2, _password);
                rs = st.executeQuery();
                while (rs.next()) {
                    result = true;
                }
                rs.close();
            } catch (Exception se) {
                se.printStackTrace();
            }
            finally {
                if (st!=null){
                    st.close();
                }
            }
            return result;
        }
    }

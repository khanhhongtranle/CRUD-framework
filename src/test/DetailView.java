package test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DetailView {
    private String tableName;
    private String[] columnNames;
    protected JFrame frame;
    protected JPanel panel;
    protected JLabel tableNameLabel;
    protected JTable dataTable;
    protected JButton addButton;

    public DetailView(String tableName) {
        this.tableName = tableName;
        this.columnNames = getColumnNames();

        this.frame = new JFrame("Table Detail");

        this.panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        this.tableNameLabel = new JLabel("Table Name");
        this.panel.add(tableNameLabel);

        this.dataTable = new JTable(getData(), columnNames);
        this.panel.add(dataTable);

        this.addButton = new JButton("Add");
        this.panel.add(addButton);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Scanner scanner = new Scanner(System.in);
                Object[] newRow = new Object[columnNames.length];
                for (int i = 0; i < columnNames.length; i++) {
                    System.out.printf("Enter %s: ", columnNames[i]);
                    newRow[i] = scanner.nextLine();
                }
                addData(newRow);
            }
        });

        this.frame.setContentPane(panel);
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(400, 400);
        this.frame.pack();
    }

    public void setVisible(boolean b) {
        this.frame.setVisible(b);
    }

    public Connection getConnection() {
        Connection connection;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/somethingdb","root","At@9infinity");
            return connection;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

    public String[] getColumnNames() {
        Connection connection = getConnection();
        ArrayList<String> columnNames = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int count = resultSetMetaData.getColumnCount();
            for (int i = 1; i <= count; i++) {
                columnNames.add(resultSetMetaData.getColumnName(i));
            }
            statement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return columnNames.toArray(new String[0]);
    }

    public Object[][] getData() {
        Connection connection = getConnection();
        ArrayList<Object[]> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
            Object[] row;
            while(resultSet.next()) {
                row = new Object[columnNames.length];
                for (int i = 0; i < columnNames.length; i++) {
                    row[i] = resultSet.getString(columnNames[i]);
                }
                list.add(row);
            }
            statement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list.toArray(new Object[0][0]);
    }

    public void addData(Object[] row) {
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            statement = connection.createStatement();
            /*
            INSERT INTO table_name (column1, column2, column3, ...)
            VALUES (value1, value2, value3, ...);
            */
            StringBuilder table_name = new StringBuilder();
            for (int i = 0; i < columnNames.length; i++) {
                table_name.append(columnNames[i]);
                if (i < columnNames.length - 1) table_name.append(", ");
            }
            StringBuilder values = new StringBuilder();
            for (int i = 0; i < columnNames.length; i++) {
                values.append("'" + row[i] + "'" );
                if (i < columnNames.length - 1) values.append(",");
            }
            String query = "INSERT INTO " + "somethingelse" + "(" + table_name.toString() + ") " + "VALUES " + "(" + values.toString() + ")";
            int numUpdated = statement.executeUpdate (query);
            if (numUpdated >= 0){
                System.out.println("Success");
            } else{
                System.out.println("Error");
            }
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateData(Object[] row) {

    }

    public void deletaData(Object[] row) {

    }

}

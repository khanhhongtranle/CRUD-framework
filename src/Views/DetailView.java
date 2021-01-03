package Views;

import Controllers.ControllersInterface;
import ViewPrototypes.Prototype;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

/**
 * this is a JFrame
 */
public class DetailView extends ViewsAbstraction implements Prototype {
    protected JFrame frame;
    protected JPanel panel;
    protected JLabel tableNameLabel;
    protected JButton createButton;
    protected JButton updateButton;
    protected JButton deleteButton;

    //
    protected JTable dataTable;
    //

    public Connection getConnection() {
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/somethingdb","root","At@9infinity");
            return conn;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

    public ArrayList<String> getSomethingColumnNames() {
        ArrayList<String> columnNames = new ArrayList<>();
        Connection con = getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from " + "something");
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

    public ArrayList<Object[]> getSomethingList() {
        Connection con = getConnection();
        String query = "SELECT * FROM " + "something";
        String[] columnNames = getSomethingColumnNames().toArray(new String[0]);

        ArrayList<Object[]> somethingList = new ArrayList<>();
            Statement st;
            ResultSet rs;

        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            Object[] sth;
            while(rs.next()) {
                sth = new Object[columnNames.length];
                for (int i = 0; i < columnNames.length; i++) {
                    sth[i] = rs.getString(columnNames[i]);
                }
                somethingList.add(sth);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return somethingList;
    }

    public DetailView(ControllersInterface conInterface) {
        super(conInterface);
        this.frame = new JFrame("Detail");
        this.panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        this.tableNameLabel = new JLabel("Table Name");
        this.createButton = new JButton("Create");
        this.updateButton = new JButton("Update");
        this.deleteButton = new JButton("Delete");

        //
        String[] columnNames = getSomethingColumnNames().toArray(new String[0]);
        Object[][] data = getSomethingList().toArray(new Object[0][0]);
        this.dataTable = new JTable(data, columnNames);
//        String[] columnNames = {"First Name",
//                        "Last Name",
//                        "Sport",
//                        "# of Years",
//                        "Vegetarian"};
//        Object[][] data = {
//                {"Kathy", "Smith",
//                        "Snowboarding", new Integer(5), new Boolean(false)},
//                {"John", "Doe",
//                        "Rowing", new Integer(3), new Boolean(true)},
//                {"Sue", "Black",
//                        "Knitting", new Integer(2), new Boolean(false)},
//                {"Jane", "White",
//                        "Speed reading", new Integer(20), new Boolean(true)},
//                {"Joe", "Brown",
//                        "Pool", new Integer(10), new Boolean(false)}
//        };
//        this.dataTable = new JTable(data, columnNames);
        //

        this.panel.add(tableNameLabel);
        this.panel.add(createButton);
        this.panel.add(updateButton);
        this.panel.add(deleteButton);

        //
        this.panel.add(dataTable);
        //

        this.frame.setContentPane(panel);
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.pack();
    }

    @Override
    public void initializeComponents() {
        controllersInterface.initializeComponents();

        this.createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con = getConnection();

                Statement sm = null;

                try {
                    sm = con.createStatement();
                    String[] columnNames = getSomethingColumnNames().toArray(new String[0]);
                    /*
                    INSERT INTO table_name (column1, column2, column3, ...)
                    VALUES (value1, value2, value3, ...);
                    */
                    StringBuilder table_name = new StringBuilder();
                    for (int i = 0; i < columnNames.length; i++) {
                        table_name.append(columnNames[i]);
                        if (i < columnNames.length - 1) table_name.append(",");
                    }
                    StringBuilder values = new StringBuilder();
                    for (int i = 0; i < columnNames.length; i++) {
                        values.append("'56789'");
                        if (i < columnNames.length - 1) values.append(",");
                    }
                    String sql = "INSERT INTO " + "somethingelse" + "(" + table_name.toString() + ") " + "VALUES " + "(" + values.toString() + ")";
                    System.out.println(sql);
                    int n = sm.executeUpdate (sql);
                    if (n >= 0){
                        System.out.println("Success");
                    }else{
                        System.out.println("Error");
                    }
                    sm.close();
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public void setViewVisible() {
        controllersInterface.setViewVisible();
        this.frame.setVisible(true);
    }

    @Override
    public Prototype clone() {
        return new DetailView(controllersInterface);
    }
}

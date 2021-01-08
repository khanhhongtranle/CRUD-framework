package framework.abstraction;

import framework.abstraction.customcomponents.ButtonEditor;
import framework.abstraction.customcomponents.ButtonRenderer;
import framework.implementation.API;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class GUIDetails extends GUI implements GUIPrototype{

    protected JFrame frame;
    protected JPanel panel;
    protected JLabel tableNameLabel;
    protected JButton createButton;
    protected JButton resetButton;
    protected JTable table;
    protected JScrollPane scrollPane;
    public static String tableName;

    public GUIDetails(API _api) {
        super(_api);
    }

    @Override
    public void connectToDatabase() {
        api.connectToDatabase();
    }

    @Override
    public GUIPrototype clone() throws CloneNotSupportedException {
        GUIPrototype clone = (GUIPrototype) super.clone();
        return this;
    }

    public void initComponents(String _table){
        tableName = _table;

        this.frame = new JFrame("Detail");
        this.panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        this.tableNameLabel = new JLabel(_table);
        this.createButton = new JButton("Create");
        this.createButton.addActionListener(new handleCreate());
        this.resetButton = new JButton("Reset");
        this.resetButton.addActionListener(new handleReset());

        ArrayList<String> columns = getListOfColumnsName(_table);
        columns.add("Update"); //column update button
        columns.add("Delete"); //column delete button
        String[] columnNames = columns.toArray(new String[0]);
        setTableModel(columnNames);

        Object[][] data = getListOfRows(_table).toArray(new Object[0][0]);
        fillDataIntoTable(data);

        this.panel.add(tableNameLabel);
        this.panel.add(createButton);
        this.panel.add(resetButton);
        this.panel.add(scrollPane);

        this.frame.setContentPane(panel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null); // this method display the JFrame to center position of a screen
        this.frame.setVisible(true);
    }

    @Override
    protected ArrayList<String> getListOfColumnsName(String _table) {
        return api.getListOfColumns(_table);
    }

    //@Override
    protected ArrayList<Object[]> getListOfRows(String _table) {
        return api.getListOfRows(_table);
    }

    //handle event click

    private class handleCreate implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            GUICreate guiCreate = new GUICreate(api);
            guiCreate.connectToDatabase();
            guiCreate.initComponents(tableName);
        }
    }

    private class handleReset implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //clean the table
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            //fill data
            Object[][] data = getListOfRows(tableName).toArray(new Object[0][0]);
            fillDataIntoTable(data);
        }
    }

    //helper function

    private void setTableModel(String[] columnNames){
        this.table = new JTable();
        this.scrollPane = new JScrollPane(table);
        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
        for(int i = 0; i < columnNames.length; i++) {
            defaultTableModel.addColumn(columnNames[i]);
        }
        defaultTableModel.setColumnIdentifiers(columnNames);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(defaultTableModel);
    }

    private void fillDataIntoTable(Object[][] data){
        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
        int countOfColumns = 0;
        for (int i = 0; i < data.length; i++) {
            Object[] record = data[i];
            countOfColumns = record.length;
            //add 2 columns as update, delete button columns
            record = appendValue(record, "update");
            record = appendValue(record, "delete");
            defaultTableModel.addRow(record);
        }
        table.setModel(defaultTableModel);

        //add button into table
        table.getColumnModel().getColumn(countOfColumns).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(countOfColumns).setCellEditor(new ButtonEditor(new JTextField(), api));
        table.getColumnModel().getColumn(countOfColumns+1).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(countOfColumns+1).setCellEditor(new ButtonEditor(new JTextField(), api));

        scrollPane.setViewportView(table);
    }

    /**
     * @by mkyong
     * @param obj
     * @param newObj
     * @return
     */
    private Object[] appendValue(Object[] obj, Object newObj) {
        ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
        temp.add(newObj);
        return temp.toArray();
    }
}

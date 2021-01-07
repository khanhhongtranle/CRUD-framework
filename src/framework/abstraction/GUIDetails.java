package framework.abstraction;

import framework.implementation.API;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUIDetails extends GUI implements GUIPrototype{

    protected JFrame frame;
    protected JPanel panel;
    protected JLabel tableNameLabel;
    protected JButton createButton;
    protected JButton updateButton;
    protected JButton deleteButton;
    protected JButton resetButton;
    protected JTable table;
    protected JScrollPane scrollPane;
    protected String tableName;

    private boolean isChanged = false;

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
        this.tableName = _table;

        this.frame = new JFrame("Detail");
        this.panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        this.tableNameLabel = new JLabel(_table);
        this.createButton = new JButton("Create");
        this.createButton.addActionListener(new handleCreate());
        this.updateButton = new JButton("Update");
        this.updateButton.addActionListener(new handleUpdate());
        this.deleteButton = new JButton("Delete");
        this.resetButton = new JButton("Reset");
        this.resetButton.addActionListener(new handleReset());

        ArrayList<String> columns = getListOfColumnsName(_table);
//        columns.add("Update"); //column update button
//        columns.add("Delete"); //column delete button
        String[] columnNames = columns.toArray(new String[0]);
        this.table = new JTable();
        this.scrollPane = new JScrollPane(table);
        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
        for(int i = 0; i < columnNames.length; i++) {
            defaultTableModel.addColumn(columnNames[i]);
        }
        defaultTableModel.setColumnIdentifiers(columnNames);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(defaultTableModel);

        Object[][] data = getListOfRows(_table).toArray(new Object[0][0]);
        fillDataIntoTable(data);
        //this.table = new JTable(data, columnNames);
        this.table.getModel().addTableModelListener(new handleTableModelChanged());

        this.panel.add(tableNameLabel);
        this.panel.add(createButton);
        this.panel.add(updateButton);
        this.panel.add(deleteButton);
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

    private void fillDataIntoTable(Object[][] data){
        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();

        for (int i = 0; i < data.length; i++) {
            Object[] record = data[i];
            defaultTableModel.addRow(record);
        }
        table.setModel(defaultTableModel);
        scrollPane.setViewportView(table);
    }

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

    private class handleTableModelChanged implements TableModelListener{

        @Override
        public void tableChanged(TableModelEvent e) {
            if (e.getType() == TableModelEvent.UPDATE){
                isChanged = true;
            }
        }
    }

    private class handleUpdate implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isChanged){

            }
        }
    }
}

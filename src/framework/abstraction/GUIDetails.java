package framework.abstraction;

import framework.implementation.API;

import javax.swing.*;
import java.awt.*;
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
    protected JTable table;
    protected JScrollPane scrollPane;
    protected String tableName;

    public GUIDetails(API _api) {
        super(_api);
    }

    @Override
    public void connectToDatabase() {
        api.connectToDatabase();
    }

    @Override
    public GUIPrototype clone() {
        return null;
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
        this.deleteButton = new JButton("Delete");

        String[] columnNames = getListOfColumnsName(_table).toArray(new String[0]);
        Object[][] data = getListOfRows(_table).toArray(new Object[0][0]);
        this.table = new JTable(data, columnNames);
        this.scrollPane = new JScrollPane(table);

        this.panel.add(tableNameLabel);
        this.panel.add(createButton);
        this.panel.add(updateButton);
        this.panel.add(deleteButton);
        this.panel.add(scrollPane);

        this.frame.setContentPane(panel);
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.pack();
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

    private class handleCreate implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            GUICreate guiCreate = new GUICreate(api);
            guiCreate.connectToDatabase();
            guiCreate.initComponents(tableName);
        }
    }
}

package Views;

import Controllers.ControllersAbstraction;
import Models.Model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * this is a JFrame
 */
public class DetailView extends JFrame implements ViewsInterface, Prototype {
    protected JPanel panel;
    protected JLabel tableNameLabel;
    protected JButton createButton;
    protected JButton updateButton;
    protected JButton deleteButton;
    protected JScrollPane scrollPane;
    protected JTable table;

    private String tableName;
    private List<Model> modelList;

    public DetailView(String tableName, List<Model> modelList){
        this.tableName = tableName;
        this.modelList = modelList;
    }

    public DetailView(){

    }

    @Override
    public DetailView clone() {
        return new DetailView();
    }

    @Override
    public void initializeComponents() {
        this.panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        this.tableNameLabel = new JLabel("Table Name");
        this.createButton = new JButton("Create");
        this.updateButton = new JButton("Update");
        this.deleteButton = new JButton("Delete");
        this.table = new JTable();
        this.scrollPane = new JScrollPane();
        createTable();
        setUpDataTable(this.modelList);
        this.panel.add(tableNameLabel);
        this.panel.add(createButton);
        this.panel.add(updateButton);
        this.panel.add(deleteButton);
        this.panel.add(scrollPane);

        this.setContentPane(panel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
    }

    @Override
    public void setViewVisible() {
        this.setVisible(true);
    }

    public void createTable(){
        DefaultTableModel defaultTableModel = (DefaultTableModel) this.table.getModel();
        String columnName[] = {"ID","Name"};
        for(int i = 0; i < columnName.length; i++) {
            defaultTableModel.addColumn(columnName[i]);
        }
        defaultTableModel.setColumnIdentifiers(columnName);
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.table.setModel(defaultTableModel);
        scrollPane.setViewportView(this.table);
    }

    public void clearDataTable(){
        DefaultTableModel defaultTableModel = (DefaultTableModel)table.getModel();
        while(defaultTableModel.getRowCount() > 0)
        {
            defaultTableModel.removeRow(0);
        }
    }

    public void setUpDataTable(List<Model> listData){

        clearDataTable();

        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();

        for (int i = 0; i < listData.size(); i++) {
            String[] data = new String[2];

            data[0] = listData.get(i).getID();
            data[1] = listData.get(i).getName();

            defaultTableModel.addRow(data);
        }
        table.setModel(defaultTableModel);
        scrollPane.setViewportView(table);
    }
}

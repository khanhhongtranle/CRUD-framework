package framework.abstraction;

import framework.implementation.API;

import javax.swing.*;

public class GUIDetails extends GUI implements GUIPrototype{

    protected JFrame frame;
    protected JPanel panel;
    protected JLabel tableNameLabel;
    protected JButton createButton;
    protected JButton updateButton;
    protected JButton deleteButton;

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

    public void initComponents(){
        this.frame = new JFrame("Detail");
        this.panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        this.tableNameLabel = new JLabel("Table Name");
        this.createButton = new JButton("Create");
        this.updateButton = new JButton("Update");
        this.deleteButton = new JButton("Delete");
        this.panel.add(tableNameLabel);
        this.panel.add(createButton);
        this.panel.add(updateButton);
        this.panel.add(deleteButton);
        this.frame.setContentPane(panel);
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.pack();
        this.frame.setVisible(true);
    }
}

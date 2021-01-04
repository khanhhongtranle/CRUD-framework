package framework.abstraction;

import framework.implementation.API;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GUICreate extends GUI implements GUIPrototype {

    protected JFrame frame;
    protected JPanel panel;
    protected JLabel label;
    protected List<JTextField> listOfTextField;
    protected List<JLabel> listOfLabelsOfFields;
    protected JButton backButton;
    protected JButton createButton;
    protected String tableName;

    public GUICreate(API _api) {
        super(_api);
    }

    @Override
    protected void connectToDatabase() {
        api.connectToDatabase();
    }

    @Override
    public void initComponents(String _table) {
        this.tableName = _table;

        ArrayList<String> listOfColumns = getListOfColumnsName(_table);
        this.frame = new JFrame();
        this.frame.setTitle("Create a record");
        this.panel = new JPanel(new GridLayout(listOfColumns.size()+2, 2));
        //this.label = new JLabel("Table: " + _table);
        this.backButton = new JButton("Back");
        this.backButton.addActionListener(new handleBack());
        this.createButton = new JButton("Create");
        this.createButton.addActionListener(new handleCreate());

        this.listOfTextField = new ArrayList<JTextField>();
        this.listOfLabelsOfFields = new ArrayList<JLabel>();
        for(int i = 0; i < listOfColumns.size(); i++){
            this.listOfTextField.add(new JTextField());
            this.listOfLabelsOfFields.add(new JLabel(listOfColumns.get(i)));

            this.panel.add(this.listOfLabelsOfFields.get(i));
            this.panel.add(this.listOfTextField.get(i));
        }
        this.panel.add(backButton);
        this.panel.add(createButton);

        this.frame.add(panel);
        this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.frame.pack();
        this.frame.setVisible(true);
    }

    @Override
    protected ArrayList<String> getListOfColumnsName(String _table) {
        return api.getListOfColumns(_table);
    }

    @Override
    public GUIPrototype clone() {
        return null;
    }

    private class handleBack implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //dispose this frame
        }
    }

    private class handleCreate implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            StringBuilder values = new StringBuilder();
            for (int i = 0; i < listOfTextField.size(); i++){
                values.append("'" + listOfTextField.get(i).getText() + "'");
                if ( i != listOfTextField.size() - 1){
                    values.append(",");
                }
            }
            api.insert(tableName, values.toString());
        }
    }
}
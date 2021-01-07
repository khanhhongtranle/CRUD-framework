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
    protected List<JLabel> listOfLabelOfType;
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
        this.panel = new JPanel(new GridLayout(listOfColumns.size()+2, 3));
        //this.label = new JLabel("Table: " + _table);
        this.backButton = new JButton("Back");
        this.backButton.addActionListener(new handleBack());
        this.createButton = new JButton("Create");
        this.createButton.addActionListener(new handleCreate());

        this.listOfTextField = new ArrayList<JTextField>();
        this.listOfLabelsOfFields = new ArrayList<JLabel>();
        this.listOfLabelOfType = new ArrayList<JLabel>();
        for(int i = 0; i < listOfColumns.size(); i++){
            this.listOfTextField.add(new JTextField());
            this.listOfLabelsOfFields.add(new JLabel(listOfColumns.get(i)));
            this.listOfLabelOfType.add(new JLabel(getColumnType(_table, listOfColumns.get(i))));

            this.panel.add(this.listOfLabelsOfFields.get(i));
            this.panel.add(this.listOfTextField.get(i));
            this.panel.add(this.listOfLabelOfType.get(i));
        }
        this.panel.add(backButton);
        this.panel.add(createButton);

        this.frame.add(panel);
        this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null); // this method display the JFrame to center position of a screen
        this.frame.setVisible(true);
    }

    @Override
    protected ArrayList<String> getListOfColumnsName(String _table) {
        return api.getListOfColumns(_table);
    }

    protected String getColumnType(String _table, String _column){
        return api.getColumnType(_table, _column);
    }

    @Override
    public GUIPrototype clone() throws CloneNotSupportedException {
        GUIPrototype clone = (GUIPrototype) super.clone();
        return this;
    }

    private class handleBack implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //dispose this frame
            frame.dispose();
        }
    }

    private class handleCreate implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<String> values = new ArrayList<>();
            for (int i = 0; i < listOfTextField.size(); i++){
               values.add(listOfTextField.get(i).getText());
            }
            if (api.insert(tableName, values)){
                JOptionPane.showMessageDialog(null, "Insert successful");
            }else{
                JOptionPane.showMessageDialog(null, "Insert unsuccessful");
            }
        }
    }
}

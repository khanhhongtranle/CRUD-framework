package framework.abstraction;

import framework.implementation.API;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUIUpdate extends GUICreate{
    private JButton updateButton;

    public GUIUpdate(API _api) {
        super(_api);
    }

    //override super class's method
    public void initComponents(String _table, Object id){
        super.initComponents(_table);

        //rename title
        this.frame.setTitle("Update record");
        //replace button
        this.updateButton = new JButton("Update");
        this.panel.remove(this.createButton);
        this.panel.add(this.updateButton);
        //handle new event
        this.updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> values = new ArrayList<>();
                for (int i = 0; i < listOfTextField.size(); i++){
                    values.add(listOfTextField.get(i).getText());
                }
                if (api.update(tableName, id, values)){
                    JOptionPane.showMessageDialog(null, "Update successful");
                }else{
                    JOptionPane.showMessageDialog(null, "Update unsuccessful");
                }
            }
        });

        //fill data
        ArrayList<Object> data = api.getARecord(this.tableName, id);
        for (int index = 0; index < data.size(); index++){
            this.listOfTextField.get(index).setText(data.get(index).toString());
        }
    }
}

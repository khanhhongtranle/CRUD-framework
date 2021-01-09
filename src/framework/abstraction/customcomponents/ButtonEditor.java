/**
 * @auth: https://camposha.info/java-jtable-buttoncolumn-tutorial/
 */
package framework.abstraction.customcomponents;

import framework.abstraction.GUICreate;
import framework.abstraction.GUIDetails;
import framework.abstraction.GUIUpdate;
import framework.implementation.API;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEditor extends DefaultCellEditor {
    protected JButton btn;
    private String lbl;
    private Boolean clicked;
    private API controlAPI;
    private Object id;

    public ButtonEditor(JTextField textField,  API _api) {
        super(textField);
        this.controlAPI = _api;

        btn=new JButton();
        btn.setOpaque(true);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object obj,
                                                 boolean selected, int row, int col) {
        lbl=(obj==null) ? "":obj.toString();
        btn.setText(lbl);
        clicked=true;
        //get PK col
        String PK = controlAPI.getPrimaryKey(GUIDetails.tableName);
        int PkColIndex = table.getColumnModel().getColumnIndex(PK);
        id = table.getModel().getValueAt(row, PkColIndex);
        return btn;
    }

    @Override
    public Object getCellEditorValue() {
        if(clicked)
        {
            switch (lbl){
                case "update":
                    GUIUpdate guiUpdate = new GUIUpdate(controlAPI);
                    guiUpdate.connectToDatabase();
                    guiUpdate.initComponents(GUIDetails.tableName, id);
                    guiUpdate.setVisible();
                    break;
                case "delete":
                    boolean result = false;
                    result = controlAPI.delete(GUIDetails.tableName,id);
                    if (result){
                        JOptionPane.showMessageDialog(btn, lbl+" successful");
                    }else{
                        JOptionPane.showMessageDialog(btn, lbl+" unsuccessful");
                    }
                    break;
                default:
            }
            //JOptionPane.showMessageDialog(btn, lbl+" Clicked");
        }
        clicked=false;
        return new String(lbl);
    }

    @Override
    public boolean stopCellEditing() {
        clicked=false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        // TODO Auto-generated method stub
        super.fireEditingStopped();
    }
}

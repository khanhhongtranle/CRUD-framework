package ViewPrototypes;

import javax.swing.*;

public class DetailViewPrototype extends JFrame implements Prototype {
    protected JPanel panel;
    protected JLabel tableNameLabel;
    protected JButton createButton;
    protected JButton updateButton;
    protected JButton deleteButton;
    //protected JTable table;
//    protected JScrollPane scrollPane;

    public DetailViewPrototype(){
        //super(prototype);
        super();
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
        this.setContentPane(panel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
    }

    @Override
    public Prototype clone() {
        return new DetailViewPrototype();
    }

}

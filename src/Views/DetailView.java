package Views;

import Controllers.ControllersInterface;
import ViewPrototypes.Prototype;

import javax.swing.*;

/**
 * this is a JFrame
 */
public class DetailView extends ViewsAbstraction implements Prototype {
    protected JFrame frame;
    protected JPanel panel;
    protected JLabel tableNameLabel;
    protected JButton createButton;
    protected JButton updateButton;
    protected JButton deleteButton;

    public DetailView(ControllersInterface conInterface) {
        super(conInterface);
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
    }

    @Override
    public void initializeComponents() {
        controllersInterface.initializeComponents();
    }

    @Override
    public void setViewVisible() {
        controllersInterface.setViewVisible();
        this.frame.setVisible(true);
    }


    @Override
    public Prototype clone() {
        return new DetailView(controllersInterface);
    }
}

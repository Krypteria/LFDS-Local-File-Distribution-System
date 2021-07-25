package View;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Box;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Color;
import Controller.Controller;
import Model.Host;

public class HostControlPanel extends JPanel{
    private JLabel hostNameLabel;
    private JLabel hostAddrLabel; 
    private JButton deleteHostButton;
    private JButton editHostButton;
    private JCheckBox sendBox;

    private Controller controller;

    public HostControlPanel(Controller controller, Host host, JCheckBox sendBox){
        this.controller = controller;
        this.sendBox = sendBox;
        this.initGUI(host);
    }

    private void initGUI(Host host){
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        this.hostNameLabel = new JLabel(host.getName() + ": ");
        this.hostAddrLabel = new JLabel(host.getAddress());

        this.deleteHostButton = new JButton("Delete");
        this.editHostButton = new JButton("Edit");

        this.deleteHostButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                performDeleteAction();
            }
        });

        this.editHostButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                performEditAction();
            }
        });

        //Box.createRigidArea(new Dimension(5,0))

        this.add(hostNameLabel);
        this.add(hostAddrLabel);
        this.add(Box.createRigidArea(new Dimension(15,0)));
        this.add(this.sendBox);
        this.add(this.editHostButton);
        this.add(this.deleteHostButton);

        //this.setMaximumSize(new Dimension(1,1)); CAMBIAR
        this.setBackground(Color.blue);
        this.setVisible(true);
    }

    private void performDeleteAction(){

    }

    private void performEditAction(){

    }
}

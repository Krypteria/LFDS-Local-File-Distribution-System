package View;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Color;
import Controller.Controller;
import Model.Host;
import View.Dialogs.EditHostDialog;

public class HostControlPanel extends JPanel{
    private JLabel hostNameLabel;
    private JLabel hostAddrLabel; 
    private JButton deleteHostButton;
    private JButton editHostButton;
    private JCheckBox sendBox;

    private Controller controller;
    private MainWindow parentFrame;
    private FileManagmentPanel fileManagmentPanel;

    public HostControlPanel(Controller controller, MainWindow parent, Host host, JCheckBox sendBox, FileManagmentPanel fileManagmentPanel){
        this.fileManagmentPanel = fileManagmentPanel;
        this.controller = controller;
        this.sendBox = sendBox;
        this.parentFrame = parent;
        this.initGUI(host);
    }

    private void initGUI(Host host){
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        this.hostNameLabel = new JLabel(host.getName());
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

        this.sendBox.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                performSelectedAction();
            }
        });
        
        this.hostNameLabel.setPreferredSize(new Dimension(120,50));
        this.hostAddrLabel.setPreferredSize(new Dimension(100,50));

        this.add(hostNameLabel);
        this.add(hostAddrLabel);
        this.add(Box.createRigidArea(new Dimension(5,0)));
        this.add(this.sendBox);
        this.add(this.editHostButton);
        this.add(this.deleteHostButton);

        this.setMaximumSize(new Dimension(500,50));
        this.setPreferredSize(new Dimension(500,50));
        this.setBackground(Color.white);
        this.setVisible(true);
    }

    private void performDeleteAction(){ 
        String text = "Do you want to delete " + this.hostNameLabel.getText() + this.hostAddrLabel.getText() + "?";
        int status = JOptionPane.showOptionDialog(this.parentFrame, text, "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
        if(status == 0){
            this.controller.removeHost(this.hostAddrLabel.getText());
        }
    }

    private void performEditAction(){ 
        EditHostDialog dialog = new EditHostDialog(this.parentFrame, this.hostNameLabel.getText(), this.hostAddrLabel.getText());
        int status = dialog.open();

        if(status == 1){
            controller.editHost(dialog.getHostName(), this.hostAddrLabel.getText(), dialog.getHostAddr());
        }
    }

    private void performSelectedAction(){
        if(this.sendBox.isSelected()){
            this.fileManagmentPanel.addSelectedHost(this.hostNameLabel.getText(), this.hostAddrLabel.getText());
        }
        else{
            this.fileManagmentPanel.removeSelectedHost(this.hostAddrLabel.getText());
        }
    }
}

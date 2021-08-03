package View.Hosts;

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
import Model.Exceptions.HostRunTimeException;
import View.FileManagmentPanel;
import View.MainWindow;
import View.Dialogs.EditHostDialog;

public class HostControlPanel extends JPanel{
    private JLabel hostNameLabel;
    private JLabel hostAddrLabel; 
    private JButton deleteHostButton;
    private JButton editHostButton;
    private JCheckBox sendBox;

    private Controller controller;
    private MainWindow parent;
    private FileManagmentPanel fileManagmentPanel;

    public HostControlPanel(Controller controller, MainWindow parent, Host host, JCheckBox sendBox, FileManagmentPanel fileManagmentPanel){
        this.fileManagmentPanel = fileManagmentPanel;
        this.controller = controller;
        this.sendBox = sendBox;
        this.parent = parent;
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
        int status = JOptionPane.showOptionDialog(this.parent, text, "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
        if(status == 0){
            this.controller.removeHost(this.hostAddrLabel.getText());
        }
    }

    private void performEditAction(){ 
        try{
            EditHostDialog dialog = new EditHostDialog(this.parent, this.hostNameLabel.getText(), this.hostAddrLabel.getText());
            int status = dialog.open();
    
            if(status == 1){
                controller.editHost(dialog.getHostName(), this.hostAddrLabel.getText(), dialog.getHostAddr());
            }
        }
        catch(HostRunTimeException e){
            JOptionPane.showOptionDialog(this.parent, e.getMessage(), "Error", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null, null, null);            
        }
    }

    private void performSelectedAction(){
        if(this.sendBox.isSelected()){
            this.editHostButton.setEnabled(false);
            this.deleteHostButton.setEnabled(false);
            this.fileManagmentPanel.addSelectedHost(this.hostNameLabel.getText(), this.hostAddrLabel.getText());
        }
        else{
            this.editHostButton.setEnabled(true);
            this.deleteHostButton.setEnabled(true);
            this.fileManagmentPanel.removeSelectedHost(this.hostAddrLabel.getText());
        }
    }
}

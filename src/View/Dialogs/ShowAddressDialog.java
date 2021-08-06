package View.Dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import View.MainWindow;

public class ShowAddressDialog extends JDialog {
    
    private JLabel addressLabel;
    private JLabel addressTextLabel;
    private JButton okButton;

    private String address;

    private MainWindow parent;

    public ShowAddressDialog(MainWindow parent, String address){
        super(parent, true);
        this.address = address;
        this.parent = parent;
        this.initGUI();
    }

    private void initGUI(){
        this.addressLabel = new JLabel("Current address:");
        this.addressTextLabel = new JLabel(this.address);

        this.addressLabel.setPreferredSize(new Dimension(100,20));
        this.addressTextLabel.setPreferredSize(new Dimension(100,20));

        this.okButton = new JButton("Ok");
        this.okButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                performOkAction();
            } 
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2,1));

        JPanel p0 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p0.setPreferredSize(new Dimension(500, 50));
        p1.setPreferredSize(new Dimension(500, 50));
        
        p0.add(this.addressLabel);
        p0.add(Box.createRigidArea(new Dimension(10,0)));
        p0.add(this.addressTextLabel);

        p1.add(this.okButton);
        
        mainPanel.add(p0);
        mainPanel.add(p1);

        this.add(mainPanel);
        this.setMinimumSize(new Dimension(400,120));
        this.setResizable(false);
        this.setTitle("Current IP address");
        this.setLocationRelativeTo(this.parent);
        this.setVisible(false);
    }

    private void performOkAction(){
        this.setVisible(false);
    }

    public void open(){
        this.setVisible(true);
    }

}

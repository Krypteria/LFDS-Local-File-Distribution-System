package View.Dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import View.MainWindow;

public class EditHostDialog extends JDialog{
    private JLabel nameText;
    private JLabel addrText;
    private JTextArea nameArea;
    private JTextArea addrArea;
    private JButton addButton;

    private int status;
    private MainWindow parent;

    public EditHostDialog(MainWindow parent, String name, String addr){
        super(parent, true);
        this.parent = parent;
        this.status = 0;
        this.initGUI(name, addr);
    }

    private void initGUI(String name, String addr){
        this.nameText = new JLabel("Name:");
        this.addrText = new JLabel("IP Address:");
        this.nameArea = new JTextArea(name);
        this.addrArea = new JTextArea(addr);

        this.nameArea.setPreferredSize(new Dimension(120,20));
        this.addrArea.setPreferredSize(new Dimension(120,20));

        this.nameArea.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
        this.addrArea.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
        
        this.addButton = new JButton("Edit host");
        this.addButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isValidName(nameArea.getText())){
                    if(isValidAddress(addrArea.getText())){
                        status = 1;
                        setVisible(false);
                    }
                    else{
                        JOptionPane.showOptionDialog(parent, "Invalid IP address", "Invalid input", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null, null, null);
                    }
                }
                else{
                    JOptionPane.showOptionDialog(parent, "Invalid host name", "Invalid input", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null, null, null);
                }
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2,1));

        JPanel p0 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        p0.add(this.nameText);
        p0.add(this.nameArea);
        p0.add(Box.createRigidArea(new Dimension(10,0)));
        p0.add(this.addrText);
        p0.add(this.addrArea);
        p1.add(this.addButton);

        mainPanel.add(p0);
        mainPanel.add(p1);

        this.add(mainPanel);
        this.setMinimumSize(new Dimension(500,120));
        this.setResizable(false);
        this.setTitle("Edit host");
        this.setLocationRelativeTo(this.parent);
        this.setVisible(false);
    }

    public int open(){
        this.setVisible(true);
        return this.status;
    }

    public String getHostName(){
        return this.nameArea.getText();
    }

    public String getHostAddr(){
        return this.addrArea.getText();
    }

    private boolean isValidName(String name){
        return (name.length() > 0 && name.length() <= 15);
    }

    private boolean isValidAddress(String addr){
        return addr.matches("^(?:(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9])(\\.(?!$)|$)){4}$");
    }
}

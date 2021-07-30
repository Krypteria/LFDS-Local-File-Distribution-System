package View;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;

import Controller.Controller;

public  class TransferencesPanel extends JPanel{

    private final Color backgroundColor = Color.white;
    private final int MAX_WIDTH = 530;

    private Controller controller;
    private JPanel transferencesContent;

    public TransferencesPanel(Controller controller){
        this.controller = controller;
        this.initGUI();
    }    

    private void initGUI(){
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.gray),"Transferences"));

        this.transferencesContent = new JPanel();
        this.transferencesContent.setLayout(new BoxLayout(this.transferencesContent, BoxLayout.PAGE_AXIS));
        this.transferencesContent.setBackground(this.backgroundColor);
        this.clearTransferences();
        this.add(this.transferencesContent, BorderLayout.CENTER);

        this.setVisible(true);
    }

    private void clearTransferences(){
        this.transferencesContent.removeAll();
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setMaximumSize(new Dimension(MAX_WIDTH, 24));
        infoPanel.setBackground(Color.red);
        infoPanel.add(new JLabel("There is no transferences in progress"));
        this.transferencesContent.add(infoPanel);
    }
}

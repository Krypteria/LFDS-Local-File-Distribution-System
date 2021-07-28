package View;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.Controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

public class FileManagmentPanel extends JPanel{
    
    private final int MAX_WIDTH = 300;
    private Controller controller;
    
    private JFileChooser fileChooser;
    private JButton sendButton;
    private JButton selectFileButton;
    private JLabel selectedFileLabel;
    private JPanel secondReceiversPanel;
    
    public FileManagmentPanel(Controller controller){
        this.controller = controller;
        this.initGUI();
    }

    private void initGUI(){
        this.setLayout(new BorderLayout(5,5));
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.gray),"File managment"));
        this.fileChooser = new JFileChooser();
        this.selectFileButton = new JButton("Select file");
        this.selectFileButton.setPreferredSize(new Dimension(170,15));
       
        this.selectedFileLabel = new JLabel("TODO");
        this.sendButton = new JButton("Send");

        //Selector
        JPanel fileSelectorPanel = new JPanel();
        fileSelectorPanel.setLayout(new BoxLayout(fileSelectorPanel, BoxLayout.PAGE_AXIS));
        fileSelectorPanel.setBackground(Color.magenta);

        JPanel firstSelectorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel secondSelectorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        

        firstSelectorPanel.add(new JLabel("File:"));
        firstSelectorPanel.add(Box.createRigidArea(new Dimension(10,0)));
        firstSelectorPanel.add(this.selectFileButton);

        secondSelectorPanel.add(new JLabel("Selected file: "));
        secondSelectorPanel.add(this.selectedFileLabel);

        fileSelectorPanel.add(firstSelectorPanel);
        fileSelectorPanel.add(secondSelectorPanel);

        //Receiver
        JPanel receiversPanel = new JPanel();
        receiversPanel.setLayout(new BoxLayout(receiversPanel, BoxLayout.PAGE_AXIS));
        receiversPanel.setPreferredSize(new Dimension(MAX_WIDTH, 50));
        receiversPanel.setMaximumSize(new Dimension(MAX_WIDTH, 50));
        receiversPanel.setBackground(Color.yellow);

        JPanel firstReceiversPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.secondReceiversPanel = new JPanel();
        this.secondReceiversPanel.setLayout(new BoxLayout(this.secondReceiversPanel, BoxLayout.PAGE_AXIS));

        firstReceiversPanel.add(new JLabel("Receivers"));

        receiversPanel.add(firstReceiversPanel);
        receiversPanel.add(this.secondReceiversPanel);

        //Send button
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        controlPanel.setBackground(Color.blue);
        controlPanel.add(this.sendButton);
        
        this.add(fileSelectorPanel, BorderLayout.PAGE_START);
        this.add(receiversPanel, BorderLayout.CENTER);
        this.add(controlPanel, BorderLayout.PAGE_END);
        this.setVisible(true);
    }
}
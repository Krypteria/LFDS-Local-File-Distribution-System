package View;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Controller.Controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileManagmentPanel extends JPanel{

    private final Color backgroundColor = Color.white;

    private final int MAX_WIDTH = 300;
    private final String NOT_FILE = "None";

    private Controller controller;
    private MainWindow parent;
    
    private JFileChooser fileChooser;
    private JButton sendButton;
    private JButton selectFileButton;
    private JLabel selectedFileLabel;
    private JPanel secondReceiversPanel;
    
    public FileManagmentPanel(Controller controller, MainWindow parent){
        this.controller = controller;
        this.parent = parent;
        this.initGUI();
    }

    private void initGUI(){
        this.setLayout(new BorderLayout(5,5));
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.gray),"File managment"));

        this.fileChooser = new JFileChooser();
        this.fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        this.selectFileButton = new JButton("Select file");
        this.selectFileButton.setPreferredSize(new Dimension(170,15));
        this.selectFileButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                performSelectFileAction();
            }
        });
       
        this.selectedFileLabel = new JLabel(NOT_FILE);
        this.sendButton = new JButton("Send");

        //Selector
        JPanel fileSelectorPanel = new JPanel();
        fileSelectorPanel.setLayout(new BoxLayout(fileSelectorPanel, BoxLayout.PAGE_AXIS));
        fileSelectorPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.gray));

        JPanel firstSelectorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel secondSelectorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        firstSelectorPanel.setBackground(this.backgroundColor);
        secondSelectorPanel.setBackground(this.backgroundColor);
        
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
        receiversPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.gray));

        JPanel firstReceiversPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.secondReceiversPanel = new JPanel();
        this.secondReceiversPanel.setLayout(new BoxLayout(this.secondReceiversPanel, BoxLayout.PAGE_AXIS));
        firstReceiversPanel.setBackground(this.backgroundColor);
        this.secondReceiversPanel.setBackground(this.backgroundColor);

        firstReceiversPanel.add(new JLabel("Receivers"));

        receiversPanel.add(firstReceiversPanel);
        receiversPanel.add(this.secondReceiversPanel);

        //Send button
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        controlPanel.setBackground(this.backgroundColor);
        controlPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.gray));
        controlPanel.add(this.sendButton);
        
        this.add(fileSelectorPanel, BorderLayout.PAGE_START);
        this.add(receiversPanel, BorderLayout.CENTER);
        this.add(controlPanel, BorderLayout.PAGE_END);
        this.setVisible(true);
    }

    private void performSelectFileAction(){
        int status = this.fileChooser.showOpenDialog(this.parent);
        if(status == 0){ //
            File selectedFile = this.fileChooser.getSelectedFile();
            if(selectedFile.getName().length() > 22){
                this.selectedFileLabel.setText(selectedFile.getName().substring(0, 22) + "...");
            }
            else{
                this.selectedFileLabel.setText(selectedFile.getName());
            }
        }
    }
}
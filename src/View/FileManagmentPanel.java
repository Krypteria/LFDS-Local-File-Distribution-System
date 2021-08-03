package View;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Controller.Controller;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileManagmentPanel extends JPanel{

    private final Color backgroundColor = Color.white;

    private final int MAX_WIDTH = 300;
    private final String NOT_FILE = "None";

    private Controller controller;
    private MainWindow parent;
    private File selectedFile;
    
    private JFileChooser fileChooser;
    private JButton sendButton;
    private JButton selectFileButton;
    private JLabel selectedFileLabel;
    private JPanel secondReceiversPanel;

    private HashMap<String, String> selectedHostMap;
    
    public FileManagmentPanel(Controller controller, MainWindow parent){
        this.selectedHostMap = new HashMap<String, String>();
        this.selectedFile = null;
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
       
        this.sendButton = new JButton("Send");
        this.sendButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                performSendAction();     
            }
        });

        this.selectedFileLabel = new JLabel(NOT_FILE);

        //Selector
        JPanel fileSelectorPanel = new JPanel();
        fileSelectorPanel.setLayout(new BoxLayout(fileSelectorPanel, BoxLayout.PAGE_AXIS));
        fileSelectorPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.gray));
        
        JPanel firstSelectorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        firstSelectorPanel.setBackground(this.backgroundColor);
        
        JPanel secondSelectorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        secondSelectorPanel.setBackground(this.backgroundColor);
        
        firstSelectorPanel.add(new JLabel("File:"));
        firstSelectorPanel.add(Box.createRigidArea(new Dimension(10,0)));
        firstSelectorPanel.add(this.selectFileButton);

        secondSelectorPanel.add(new JLabel("Selected file: "));
        secondSelectorPanel.add(this.selectedFileLabel);

        fileSelectorPanel.add(firstSelectorPanel);
        fileSelectorPanel.add(secondSelectorPanel);

        //Receiver
        JPanel receiversPanel = new JPanel(new BorderLayout(0,5));
        receiversPanel.setBackground(this.backgroundColor);
        receiversPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.gray));

        JPanel firstReceiversPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        firstReceiversPanel.setMaximumSize(new Dimension(MAX_WIDTH, 30));
        firstReceiversPanel.setBackground(this.backgroundColor);

        this.secondReceiversPanel = new JPanel(new BorderLayout());
        this.secondReceiversPanel.setBackground(this.backgroundColor);

        firstReceiversPanel.add(new JLabel("Receivers"));

        receiversPanel.add(firstReceiversPanel, BorderLayout.PAGE_START);
        receiversPanel.add(this.secondReceiversPanel, BorderLayout.CENTER);

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
            this.selectedFile = this.fileChooser.getSelectedFile();
            if(selectedFile.getName().length() > 30){
                this.selectedFileLabel.setText(selectedFile.getName().substring(0, 30) + "...");
            }
            else{
                this.selectedFileLabel.setText(selectedFile.getName());
            }
        }
    }

    private void performSendAction(){
        if(!this.selectedHostMap.isEmpty()){
            if(this.selectedFile != null){
                for(Map.Entry<String, String> mapElement : this.selectedHostMap.entrySet()) {
                    String address = mapElement.getKey();   
                    controller.sendFile(address, selectedFile);                   
                }
                this.selectedFileLabel.setText("");
                this.removeAllSelectedHosts(); 
            }
            else{
                JOptionPane.showOptionDialog(this.parent, "Please, select a file", "Error", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null, null, null); 
            }
        }
        else{
            JOptionPane.showOptionDialog(this.parent, "You need to select at least one receiver", "Error", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null, null, null); 
        }
    }

    public void addSelectedHost(String name, String address){
        this.selectedHostMap.put(address, name);
        this.updateHostsReceivers(); 
    }

    public void removeSelectedHost(String address){
        this.selectedHostMap.remove(address);
        this.updateHostsReceivers(); 
    }

    public void removeAllSelectedHosts(){
        this.selectedHostMap.clear();
        this.updateHostsReceivers();
    }

    private void updateHostsReceivers(){
        this.secondReceiversPanel.removeAll();

        JPanel secondReceiversContentPanel = new JPanel();
        secondReceiversContentPanel.setLayout(new BoxLayout(secondReceiversContentPanel, BoxLayout.PAGE_AXIS)); 
        secondReceiversContentPanel.setBackground(this.backgroundColor);

        for(Map.Entry<String, String> mapElement : this.selectedHostMap.entrySet()) {
            JPanel selectedHostPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            selectedHostPanel.setMaximumSize(new Dimension(MAX_WIDTH, 28));
            selectedHostPanel.setPreferredSize(new Dimension(MAX_WIDTH, 28));
            selectedHostPanel.setBackground(this.backgroundColor);
            selectedHostPanel.setVisible(true);

            String address = mapElement.getKey();
            String name = mapElement.getValue();

            JLabel nameLabel = new JLabel(name);
            JLabel addrLabel = new JLabel(address);
            nameLabel.setPreferredSize(new Dimension(120,25));
            addrLabel.setPreferredSize(new Dimension(100,25));

            selectedHostPanel.add(new JLabel("[*]"));
            selectedHostPanel.add(Box.createRigidArea(new Dimension(1,0)));
            selectedHostPanel.add(nameLabel);
            selectedHostPanel.add(Box.createRigidArea(new Dimension(1,0)));
            selectedHostPanel.add(addrLabel);

            secondReceiversContentPanel.add(selectedHostPanel);
        }
        JScrollPane scrollPane = new JScrollPane(secondReceiversContentPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        this.secondReceiversPanel.add(scrollPane, BorderLayout.LINE_START);
        this.secondReceiversPanel.validate();
        this.secondReceiversPanel.repaint();
    }
}
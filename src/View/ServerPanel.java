package View;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Controller.Controller;
import Model.Exceptions.ServerRunTimeException;
import Model.Observers.ServerObserver;

public class ServerPanel extends JPanel implements ServerObserver{
    
    private final int MAX_WIDTH = 300;
    
    private final Color backgroundColor = Color.white;
    private static final float [] greenColor = Color.RGBtoHSB(28, 150, 78, null); 
	private static final float [] redColor = Color.RGBtoHSB(160, 21, 21, null); 

    private final String RUNNING = "Running";
    private final String STOPPED = "Stopped";

    private Controller controller;
    private MainWindow parent;

    private JButton openServerButton;
    private JButton closeServerButton;
    private JButton resetServerButton;
    
    private JLabel serverStatusLabel;
    private JLabel serverPortLabel;
    
    private JLabel serverTaskTitleLabel;
    private JLabel serverTaskInfoLabel;
    
    public ServerPanel(Controller controller, MainWindow parent){
        this.parent = parent;
        this.controller = controller;
        this.controller.addObserver(this);
        this.initGUI();
    }    

    private void initGUI(){
        this.setLayout(new BorderLayout(5,5));

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.PAGE_AXIS));
        statusPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.gray));
        statusPanel.setBackground(this.backgroundColor);
    

        JPanel taskPanel = new JPanel(new BorderLayout(0,6)); 
        taskPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.gray));
        taskPanel.setBackground(this.backgroundColor);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        controlPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.gray));
        controlPanel.setBackground(this.backgroundColor);

        //Status panel
        JPanel firstStatusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel secondStatusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        firstStatusPanel.setBackground(this.backgroundColor);
        secondStatusPanel.setBackground(this.backgroundColor);

        this.serverStatusLabel = new JLabel(RUNNING);
        this.serverStatusLabel.setForeground(Color.getHSBColor(greenColor[0], greenColor[1], greenColor[2]));
        this.serverPortLabel = new JLabel("2222");
        
        firstStatusPanel.add(new JLabel("Status:"));
        firstStatusPanel.add(Box.createRigidArea(new Dimension(10,0)));
        firstStatusPanel.add(this.serverStatusLabel);
        
        secondStatusPanel.add(new JLabel("Port in use:"));
        secondStatusPanel.add(Box.createRigidArea(new Dimension(10,0)));
        secondStatusPanel.add(this.serverPortLabel);
        
        statusPanel.add(firstStatusPanel);
        statusPanel.add(secondStatusPanel);
        
        //Task Panel
        JPanel firstTaskPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel secondTaskPanel = new JPanel();
        secondTaskPanel.setLayout(new BoxLayout(secondTaskPanel, BoxLayout.PAGE_AXIS));

        firstTaskPanel.setBackground(this.backgroundColor);
        secondTaskPanel.setBackground(Color.orange);

        firstTaskPanel.setPreferredSize(new Dimension(MAX_WIDTH, 23));
        firstTaskPanel.setMaximumSize(new Dimension(MAX_WIDTH, 23));
        secondTaskPanel.setPreferredSize(new Dimension(MAX_WIDTH, 40));
        secondTaskPanel.setMaximumSize(new Dimension(MAX_WIDTH, 40));

        this.serverTaskTitleLabel = new JLabel("Tasks");
        this.serverTaskInfoLabel = new JLabel("Aquí iran las cosas estas");

        firstTaskPanel.add(this.serverTaskTitleLabel);
        secondTaskPanel.add(this.serverTaskInfoLabel); //Si permito multi recibo lo que haré será meterlo en un jscrollpane

        taskPanel.add(firstTaskPanel, BorderLayout.PAGE_START);
        taskPanel.add(secondTaskPanel, BorderLayout.LINE_START);

        //Control Panel
        this.openServerButton = new JButton("Open");
        this.closeServerButton = new JButton("Close");
        this.resetServerButton = new JButton("Reset");

        this.openServerButton.setEnabled(false);

        this.openServerButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                performOpenAction();
            }
        });

        this.closeServerButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                performCloseAction();
            }
        });

        this.resetServerButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                performResetAction();                
            }
        });

        controlPanel.add(this.openServerButton);
        controlPanel.add(this.resetServerButton);
        controlPanel.add(this.closeServerButton);

        this.add(statusPanel, BorderLayout.PAGE_START);
        this.add(taskPanel, BorderLayout.CENTER);
        this.add(controlPanel, BorderLayout.PAGE_END);

        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.gray),"Server"));
        this.setVisible(true);
    }

    private void performOpenAction(){
        try{
            this.controller.openServer();
        }
        catch(ServerRunTimeException e){
            JOptionPane.showOptionDialog(this.parent, e.getMessage(), "Error", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null, null, null);
        }
    }

    private void performCloseAction(){
        try{
            this.controller.closeServer();
        }
        catch(ServerRunTimeException e){
            JOptionPane.showOptionDialog(this.parent, e.getMessage(), "Error", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null, null, null);            
        }
    }

    private void performResetAction(){
        updateStatus(STOPPED, Integer.parseInt(serverPortLabel.getText()));
        new Thread() {
            public void run() {
                try{
                    controller.resetServer();
                }
                catch(ServerRunTimeException e){
                    JOptionPane.showOptionDialog(parent, e.getMessage(), "Error", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null, null, null);
                }
            }
        }.start();
    }

    @Override
    public void updateStatus(String newStatus, int newPort) {
        this.serverStatusLabel.setText(newStatus);
        this.serverPortLabel.setText("" + newPort);
      
        if(newStatus.equals(RUNNING)){
            serverStatusLabel.setForeground(Color.getHSBColor(greenColor[0], greenColor[1], greenColor[2]));
            this.openServerButton.setEnabled(false);
            this.closeServerButton.setEnabled(true);
            this.resetServerButton.setEnabled(true);
        }
        else if(newStatus.equals(STOPPED)){
            serverStatusLabel.setForeground(Color.getHSBColor(redColor[0], redColor[1], redColor[2]));
            this.openServerButton.setEnabled(true);
            this.closeServerButton.setEnabled(false);
            this.resetServerButton.setEnabled(false);
        }
    }

    @Override
    public void updateTaskInfo(String newTask) {
        // TODO Auto-generated method stub
    }
}

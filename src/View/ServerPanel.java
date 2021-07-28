package View;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Controller.Controller;

public class ServerPanel extends JPanel{
    
    private final int MAX_WIDTH = 300;
    private final int MAX_HEIGHT = 300;
    private final Color backgroundColor = Color.white;

    private Controller controller;

    private JButton openServerButton;
    private JButton closeServerButton;
    private JButton resetServerButton;
    
    private JLabel serverStatusLabel;
    private JLabel serverPortLabel;
    
    private JLabel serverTaskTitleLabel;
    private JLabel serverTaskInfoLabel;
    
    public ServerPanel(Controller controller){
        this.controller = controller;
        this.initGUI();
    }    

    private void initGUI(){
        this.setLayout(new BorderLayout(5,5));

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.PAGE_AXIS));
        statusPanel.setPreferredSize(new Dimension(MAX_WIDTH, 46));
        statusPanel.setMaximumSize(new Dimension(MAX_WIDTH, 46));
        statusPanel.setBackground(Color.BLUE);
    

        JPanel taskPanel = new JPanel(); 
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.PAGE_AXIS));
        taskPanel.setPreferredSize(new Dimension(MAX_WIDTH, 20));
        taskPanel.setMaximumSize(new Dimension(MAX_WIDTH, 20));
        taskPanel.setBackground(Color.RED);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        controlPanel.setPreferredSize(new Dimension(MAX_WIDTH, 35));
        controlPanel.setMaximumSize(new Dimension(MAX_WIDTH, 35));
        controlPanel.setBackground(Color.GREEN);

        //Status panel
        JPanel firstStatusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel secondStatusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        firstStatusPanel.setPreferredSize(new Dimension(MAX_WIDTH, 23));
        firstStatusPanel.setMaximumSize(new Dimension(MAX_WIDTH, 23));

        secondStatusPanel.setPreferredSize(new Dimension(MAX_WIDTH, 23));
        secondStatusPanel.setMaximumSize(new Dimension(MAX_WIDTH, 23));

        this.serverStatusLabel = new JLabel("");
        this.serverPortLabel = new JLabel("");
        
        firstStatusPanel.add(new JLabel("Status"));
        firstStatusPanel.add(Box.createRigidArea(new Dimension(10,0)));
        firstStatusPanel.add(this.serverStatusLabel);
        
        secondStatusPanel.add(new JLabel("Port"));
        secondStatusPanel.add(Box.createRigidArea(new Dimension(10,0)));
        secondStatusPanel.add(this.serverPortLabel);
        
        statusPanel.add(firstStatusPanel);
        statusPanel.add(secondStatusPanel);
        
        //Task Panel
        JPanel firstTaskPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel secondTaskPanel = new JPanel();
        secondTaskPanel.setLayout(new BoxLayout(secondTaskPanel, BoxLayout.PAGE_AXIS));

        firstTaskPanel.setBackground(Color.yellow);
        secondTaskPanel.setBackground(Color.orange);
        
        firstTaskPanel.setPreferredSize(new Dimension(MAX_WIDTH, 23));
        firstTaskPanel.setMaximumSize(new Dimension(MAX_WIDTH, 23));
        secondTaskPanel.setPreferredSize(new Dimension(MAX_WIDTH, 40));
        secondTaskPanel.setMaximumSize(new Dimension(MAX_WIDTH, 40));

        this.serverTaskTitleLabel = new JLabel("Tasks");
        this.serverTaskInfoLabel = new JLabel("");

        firstTaskPanel.add(this.serverTaskTitleLabel);
        secondTaskPanel.add(this.serverTaskInfoLabel); //Si permito multi recibo lo que haré será meterlo en un jscrollpane

        taskPanel.add(firstTaskPanel);
        taskPanel.add(secondTaskPanel);

        //Control Panel
        this.openServerButton = new JButton("Open");
        this.closeServerButton = new JButton("Close");
        this.resetServerButton = new JButton("Reset");

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

        this.setBackground(backgroundColor);
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.gray),"Server"));
        this.setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
        this.setMaximumSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
        this.setVisible(true);
    }

    private void performOpenAction(){
        this.controller.openServer();
    }

    private void performCloseAction(){
        this.controller.closeServer();
    }

    private void performResetAction(){
        this.controller.resetServer();
    }
}

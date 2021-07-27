package View;

import Controller.Controller;
import Model.Host;
import Model.Observers.HostsObserver;
import View.Dialogs.AddHostDialog;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class HostsPanel extends JPanel implements HostsObserver{

    private final int MAX_WIDTH = 100;
    private final int MAX_HEIGHT = 202;
    private final Color backgroundColor = Color.white;

    private List<JCheckBox> sendCheckBoxesList;
    private JPanel hostsContentPanel;
    private JButton addHostButton;

    private Controller controller;
    private MainWindow parentFrame;

    public HostsPanel(Controller controller, MainWindow parentFrame){
        this.controller = controller;
        this.sendCheckBoxesList = new ArrayList<JCheckBox>();
        this.parentFrame = parentFrame;
        this.hostsContentPanel = new JPanel();
        
        this.controller.addObserver(this);
        this.initGUI();
    }

    private void initGUI(){        
        this.setLayout(new BorderLayout(5,5));
        this.setBackground(this.backgroundColor);
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.gray),"Hosts"));

        this.addHostButton = new JButton("Add new host");
        this.addHostButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                performAddAction();
            }
        });

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonsPanel.setBackground(this.backgroundColor);
        buttonsPanel.add(this.addHostButton);

        this.hostsContentPanel.setLayout(new BoxLayout(this.hostsContentPanel, BoxLayout.PAGE_AXIS));
        this.hostsContentPanel.setBackground(this.backgroundColor);
     
        //Dimensions
        this.setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
        this.setMaximumSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
        
        buttonsPanel.setPreferredSize(new Dimension(MAX_WIDTH,35));
        buttonsPanel.setMaximumSize(new Dimension(MAX_WIDTH,35));

        this.hostsContentPanel.setPreferredSize(new Dimension(MAX_WIDTH,MAX_HEIGHT));
        this.hostsContentPanel.setMaximumSize(new Dimension(MAX_WIDTH,MAX_HEIGHT));

        this.add(buttonsPanel, BorderLayout.PAGE_START);
        this.add(this.hostsContentPanel, BorderLayout.CENTER);

        this.setVisible(true);
    }
    
    private void performAddAction(){
        AddHostDialog dialog = new AddHostDialog(this.parentFrame);
        int status = dialog.open();

        if(status == 1){
            controller.addNewHost(dialog.getHostName(), dialog.getHostAddr());
        }
    }

    @Override
    public void updateHosts(List<Host> hostList) {
        this.hostsContentPanel.removeAll();
        this.sendCheckBoxesList.clear();

        JPanel hostControlPanel = new JPanel();
        hostControlPanel.setLayout(new BoxLayout(hostControlPanel, BoxLayout.PAGE_AXIS)); 
        hostControlPanel.setBackground(this.backgroundColor);

        for (Host host : hostList) {
            JCheckBox checkBox = new JCheckBox("Send");
            checkBox.setBackground(this.backgroundColor);
            this.sendCheckBoxesList.add(checkBox);
            hostControlPanel.add(new HostControlPanel(this.controller, this.parentFrame, host, this.sendCheckBoxesList.get(this.sendCheckBoxesList.size() - 1)));
        }
        JScrollPane scrollPane = new JScrollPane(hostControlPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
       
        this.hostsContentPanel.add(scrollPane);
        this.hostsContentPanel.validate();
        this.hostsContentPanel.repaint();
    }
}

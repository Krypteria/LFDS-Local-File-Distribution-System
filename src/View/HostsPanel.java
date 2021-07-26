package View;

import Controller.Controller;
import Model.Host;
import Model.Observers.HostsObserver;
import View.Dialogs.AddHostDialog;

import javax.swing.JPanel;
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

        this.add(new JPanel(), BorderLayout.PAGE_START);
        this.add(new JPanel(), BorderLayout.PAGE_END);
        this.add(new JPanel(), BorderLayout.LINE_START);
        this.add(new JPanel(), BorderLayout.LINE_END);

        this.addHostButton = new JButton("Add new host");
        this.addHostButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                performAddAction();
            }
        });
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2),"Hosts"));
        mainPanel.setBackground(Color.white);

        this.hostsContentPanel.setBackground(Color.white);
        this.hostsContentPanel.setLayout(new BoxLayout(this.hostsContentPanel, BoxLayout.PAGE_AXIS));

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonsPanel.setMaximumSize(new Dimension(500,35));
        buttonsPanel.setBackground(Color.white);
        buttonsPanel.add(this.addHostButton);


        mainPanel.add(buttonsPanel, BorderLayout.CENTER);
        mainPanel.add(this.hostsContentPanel, BorderLayout.CENTER);

        this.add(mainPanel, BorderLayout.CENTER);
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
        for (Host host : hostList) {
            this.sendCheckBoxesList.add(new JCheckBox("Send"));
            this.hostsContentPanel.add(new HostControlPanel(this.controller, this.parentFrame, host, this.sendCheckBoxesList.get(this.sendCheckBoxesList.size() - 1)));
        }
        this.hostsContentPanel.validate();
        this.hostsContentPanel.repaint();
    }
}

package View.Hosts;

import Controller.Controller;
import Model.Host;
import Model.Observers.HostsObserver;
import View.FileManagmentPanel;
import View.MainWindow;
import View.Dialogs.AddHostDialog;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class HostsPanel extends JPanel implements HostsObserver{

    private final Color backgroundColor = Color.white;

    private JButton addHostButton;

    private JPanel hostsContentPanel;
    
    private Controller controller;
    private MainWindow parentFrame;
    private FileManagmentPanel fileManagmentPanel;

    public HostsPanel(Controller controller, MainWindow parentFrame, FileManagmentPanel fileManagmentPanel){
        this.fileManagmentPanel = fileManagmentPanel;
        this.controller = controller;
        this.parentFrame = parentFrame;
        this.hostsContentPanel = new JPanel();
        
        this.controller.addObserver(this);
        this.initGUI();
    }

    private void initGUI(){        
        this.setLayout(new BorderLayout(5,5));
        this.setBackground(null);
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
        buttonsPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.gray));
        buttonsPanel.add(this.addHostButton);

        this.hostsContentPanel.setLayout(new BoxLayout(this.hostsContentPanel, BoxLayout.PAGE_AXIS));
        this.hostsContentPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.gray));
        this.hostsContentPanel.setBackground(this.backgroundColor);

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
    public void updateAllHosts(List<Host> hostList) {
        this.hostsContentPanel.removeAll();

        JPanel hostControlPanel = new JPanel();
        hostControlPanel.setLayout(new BoxLayout(hostControlPanel, BoxLayout.PAGE_AXIS)); 
        hostControlPanel.setBackground(this.backgroundColor);

        for (Host host : hostList) {
            JCheckBox checkBox = new JCheckBox("Send");
            checkBox.setBackground(this.backgroundColor);

            hostControlPanel.add(new HostControlPanel(this.controller, this.parentFrame, host, checkBox, this.fileManagmentPanel));
        }
        JScrollPane scrollPane = new JScrollPane(hostControlPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
       
        this.hostsContentPanel.add(scrollPane);
        this.hostsContentPanel.validate();
        this.hostsContentPanel.repaint();
    }
}

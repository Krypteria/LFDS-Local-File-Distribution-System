package View.Hosts;

import Controller.Controller;
import Model.Exceptions.HostRunTimeException;
import Model.Host.Host;
import Model.Observers.HostsObserver;
import View.FileManagmentPanel;
import View.MainWindow;
import View.Buttons.AddHostButton;
import View.Dialogs.AddHostDialog;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class HostsPanel extends JPanel implements HostsObserver{

    private final Color backgroundColor = Color.white;

    private JButton addHostButton;

    private JPanel hostsContentPanel;
    
    private Controller controller;
    private MainWindow parent;
    private FileManagmentPanel fileManagmentPanel;

    private List<Host> hostList;
    private HashMap<String, HostControlPanel> hostPanelMap;

    public HostsPanel(Controller controller, MainWindow parent, FileManagmentPanel fileManagmentPanel){
        this.hostPanelMap = new HashMap<String, HostControlPanel>();
        this.fileManagmentPanel = fileManagmentPanel;
        this.controller = controller;
        this.parent = parent;
        this.hostsContentPanel = new JPanel();
        
        this.controller.addObserver(this);
        this.initGUI();
    }

    private void initGUI(){        
        this.setLayout(new BorderLayout(5,5));
        this.setBackground(null);
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.gray),"Hosts"));

        this.addHostButton = new AddHostButton(this.controller);

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
        try{
            AddHostDialog dialog = new AddHostDialog(this.parent);
            int status = dialog.open();
    
            if(status == 1){
                controller.addNewHost(dialog.getHostName().trim(), dialog.getHostAddr());
                fileManagmentPanel.removeAllSelectedHosts();
            }
        }
        catch(HostRunTimeException e){
            JOptionPane.showOptionDialog(this.parent, e.getMessage(), "Error", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null, null, null);            
        }
    }

    @Override
    public void updateAllHosts(List<Host> hostList) {
        this.hostList = hostList;
        this.updateHostGUI();
    }

    private void updateHostGUI(){
        this.hostsContentPanel.removeAll();
        this.hostPanelMap.clear();

        JPanel hostControlPanel = new JPanel();
        hostControlPanel.setLayout(new BoxLayout(hostControlPanel, BoxLayout.PAGE_AXIS)); 
        hostControlPanel.setBackground(this.backgroundColor);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setMaximumSize(new Dimension(500,50));
        titlePanel.setPreferredSize(new Dimension(500,50));
        titlePanel.setBackground(Color.white);

        JLabel nameLabel = new JLabel("Contact name");
        JLabel addressLabel = new JLabel("IP address");
        nameLabel.setPreferredSize(new Dimension(120,50));
        addressLabel.setPreferredSize(new Dimension(100,50));

        titlePanel.add(nameLabel);
        titlePanel.add(addressLabel);

        hostControlPanel.add(titlePanel);

        for (Host host : hostList) {
            JCheckBox checkBox = new JCheckBox("Send");
            checkBox.setBackground(this.backgroundColor);

            HostControlPanel hostPanel = new HostControlPanel(this.controller, this.parent, host, checkBox, this.fileManagmentPanel);
            this.hostPanelMap.put(host.getAddress(), hostPanel);
            hostControlPanel.add(hostPanel);
        }
        JScrollPane scrollPane = new JScrollPane(hostControlPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
       
        this.hostsContentPanel.add(scrollPane);
        this.hostsContentPanel.validate();
        this.hostsContentPanel.repaint();
    }

    public void enableHostOptions(String address, boolean enable){
        this.hostPanelMap.get(address).enableHostOptions(enable);
    }

}

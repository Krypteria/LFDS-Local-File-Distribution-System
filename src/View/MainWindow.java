package View;

import javax.swing.UIManager.*;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;

import Controller.Controller;
import View.Hosts.HostsPanel;
import View.Transferences.TransferencesPanel;

public class MainWindow extends JFrame{
    private final int MAX_WIDTH = 830;
    private final int MAX_HEIGHT = 555;

    private Controller controller;
    private JPanel mainPanel;

    public MainWindow(Controller controller){
        this.controller = controller;
        this.enableNimbus();
        this.initGUI();
    }

    private void enableNimbus(){
        try{
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (Exception e) {
            System.out.println("Nimbus not loaded");
        }
    }

    private void initGUI(){
        this.setTitle("File transfer system");
        this.setSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));

        this.mainPanel = new JPanel();
        GridBagLayout layout = new GridBagLayout();

        //Setting the layout dimensions
        layout.columnWidths = new int[] {480,350};
        layout.rowHeights = new int[] {370,300};
        layout.columnWeights = new double[] {1,1};
        layout.rowWeights = new double[] {1,1,1};

        this.mainPanel.setLayout(layout);
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10,10,10,10);

        FileManagmentPanel fileManagmentPanel = new FileManagmentPanel(this.controller, this);
        HostsPanel hostsPanel = new HostsPanel(this.controller, this, fileManagmentPanel);
        ServerPanel serverPanel = new ServerPanel(this.controller, this);
        TransferencesPanel transferencePanel = new TransferencesPanel(this.controller, this, hostsPanel);

        setJPanelLayoutConfig(hostsPanel, constraints, 0, 0, 1, 1, true);
        setJPanelLayoutConfig(serverPanel, constraints, 1, 1, 1, 1, true);
        setJPanelLayoutConfig(fileManagmentPanel, constraints, 1, 0, 1, 1, true);
        setJPanelLayoutConfig(transferencePanel, constraints, 0, 1, 1, 1, true);

        this.add(mainPanel);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }
    

    private void setJPanelLayoutConfig(JComponent component, GridBagConstraints constraints, int x, int y, int w, int h, boolean expand){
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = w;
        constraints.gridheight = h;
        if(expand){
            constraints.weighty = 1.0;
            constraints.weightx = 1.0;
            constraints.fill = GridBagConstraints.BOTH;
        }
        this.mainPanel.add(component, constraints); 
    }
}
package View;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.GridLayout;
import java.awt.Dimension;

import Controller.Controller;

public class MainWindow extends JFrame{
    private Controller controller;

    public MainWindow(Controller controller){
        this.controller = controller;
        this.initGUI();
    }

    private void initGUI(){
        this.setTitle("File transfer system");
        this.setSize(new Dimension(600,600));

        JPanel mainPanel = new JPanel(new GridLayout(1,2));
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));

        HostsPanel hostsPanel = new HostsPanel(this.controller);
        SendPanel sendPanel = new SendPanel(this.controller);
        ServerPanel serverPanel = new ServerPanel(this.controller);
        TransferencesPanel transferencePanel = new TransferencesPanel(this.controller);

        leftPanel.add(hostsPanel);
        leftPanel.add(transferencePanel);

        rightPanel.add(sendPanel);
        rightPanel.add(serverPanel);
        
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        this.add(mainPanel);
        this.setVisible(true);
    }
}

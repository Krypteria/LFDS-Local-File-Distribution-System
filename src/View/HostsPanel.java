package View;

import Controller.Controller;
import Model.Host;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JCheckBox;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import java.util.List;
import java.util.ArrayList;

public class HostsPanel extends JPanel{

    private List<JCheckBox> sendCheckBoxesList;
    private Controller controller;

    public HostsPanel(Controller controller){
        this.controller = controller;
        this.sendCheckBoxesList = new ArrayList<JCheckBox>();
        this.initGUI();
    }

    private void initGUI(){
        this.setLayout(new BorderLayout(5,5));
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        
        JLabel title = new JLabel("Hosts");
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.add(title);
        titlePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
        //titlePanel.setMaximumSize(new Dimension(10,100)); CAMBIAR
        mainPanel.add(titlePanel);

        for (Host host : this.controller.getAllHosts()) {
            this.sendCheckBoxesList.add(new JCheckBox("Send"));
            mainPanel.add(new HostControlPanel(this.controller, host, this.sendCheckBoxesList.get(this.sendCheckBoxesList.size() - 1)));
        }

        mainPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        
        this.add(new JPanel(), BorderLayout.PAGE_START);
        this.add(new JPanel(), BorderLayout.PAGE_END);
        this.add(new JPanel(), BorderLayout.LINE_START);
        this.add(new JPanel(), BorderLayout.LINE_END);
        
        this.setBackground(Color.red);
        this.add(mainPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }
}

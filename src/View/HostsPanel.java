package View;

import Controller.Controller;
import Model.Host;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

public class HostsPanel extends JPanel{

    private Controller controller;

    public HostsPanel(Controller controller){
        this.controller = controller;
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
        mainPanel.add(titlePanel);

        for (Host host : this.controller.getAllHosts()) {
            mainPanel.add(createHostPanel(host));
        }

        mainPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        this.add(mainPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private JPanel createHostPanel(Host host){
        JPanel hostPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //21 - 1 - 16 - 5 - botones 
        JLabel hostLabel = new JLabel(host.getName() + ": " + host.getAddress() + "     ");

        //Añadir botones y toda la pesca

        hostPanel.add(hostLabel);
        //botones


        return hostPanel;
    }


}

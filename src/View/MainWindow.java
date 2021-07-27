package View;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Dimension;

import Controller.Controller;

public class MainWindow extends JFrame{
    private Controller controller;
    private JPanel mainPanel;

    public MainWindow(Controller controller){
        this.controller = controller;
        this.initGUI();
    }

    private void initGUI(){
        this.setTitle("File transfer system");
        this.setSize(new Dimension(1000,600));

        this.mainPanel = new JPanel(new GridBagLayout());
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(20,20,20,20);

        HostsPanel hostsPanel = new HostsPanel(this.controller, this);
        SendPanel sendPanel = new SendPanel(this.controller);
        ServerPanel serverPanel = new ServerPanel(this.controller);
        TransferencesPanel transferencePanel = new TransferencesPanel(this.controller);

        sendPanel.setBackground(Color.red);
        serverPanel.setBackground(Color.green);
        transferencePanel.setBackground(Color.pink);

        setJPanelLayoutConfig(hostsPanel, constraints, 0, 1, 4, 4, true);
        setJPanelLayoutConfig(new JButton("test B"), constraints, 4, 1, 4, 4, true);
        setJPanelLayoutConfig(new JButton("test C"), constraints, 4, 5, 4, 4, true);
        setJPanelLayoutConfig(new JButton("test D"), constraints, 4, 9, 4, 4, true);

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

/*
    JDialog editar y añadir -> JDialog general con todo y que reciba un JBUTTON que sea el que cambie
    luego en editar y añadir solo llamamos al super y fuera

    Capar numero de caracteres de nombre e IP, capar lo que se mete en IP

    Send panel

    Jfilechooser -> verificar formatos de ficheros y todo el rollo más adelante
    Ver como hacer que al pulsar en send se añada o elimine dinamicamente un nombre
    Boton de enviar que inicie el envio

    Server

    Transfer








*/
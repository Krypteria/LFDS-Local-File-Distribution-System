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
        this.setSize(new Dimension(1000,600));

        JPanel mainPanel = new JPanel(new GridLayout(1,2));
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));

        HostsPanel hostsPanel = new HostsPanel(this.controller, this);
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
        this.setLocationRelativeTo(null);
        this.setVisible(true);
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
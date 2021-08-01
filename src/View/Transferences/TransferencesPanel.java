package View.Transferences;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map;
import java.awt.BorderLayout;

import Controller.Controller;
import Model.Observers.TransferencesObserver;

public  class TransferencesPanel extends JPanel implements TransferencesObserver{

    private final String SEND_MODE = "send";
    private final String RECEIVE_MODE = "receive";

    private final Color backgroundColor = Color.white;
    private final int MAX_WIDTH = 530;

    private Controller controller;
    private JPanel transferencesContentPanel;

    private HashMap<String, TransferenceControlPanel> clientTransferencesMap, serverTransferencesMap;

    public TransferencesPanel(Controller controller){
        this.clientTransferencesMap = new HashMap<String, TransferenceControlPanel>();
        this.serverTransferencesMap = new HashMap<String, TransferenceControlPanel>();
        this.controller = controller;
        this.controller.addTransferenceObserverClient(this);
        this.initGUI();
    }    

    private void initGUI(){
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.gray),"Transferences"));

        this.transferencesContentPanel = new JPanel();
        this.transferencesContentPanel.setLayout(new BoxLayout(this.transferencesContentPanel, BoxLayout.PAGE_AXIS));
        this.transferencesContentPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
        this.transferencesContentPanel.setBackground(this.backgroundColor);
        this.clearTransferences();
        this.add(this.transferencesContentPanel, BorderLayout.CENTER);

        this.setVisible(true);
    }

    private void clearTransferences(){
        this.transferencesContentPanel.removeAll();

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setMaximumSize(new Dimension(MAX_WIDTH, 24));
        infoPanel.setBackground(Color.white);
        infoPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.gray));
        infoPanel.add(new JLabel("There is no transferences in process"));

        this.transferencesContentPanel.add(infoPanel);
        
        this.transferencesContentPanel.validate();
        this.transferencesContentPanel.repaint();
    }

    @Override
    public void addTransference(String mode, String src_addr, String dst_addr, String fileName) {
        if(mode.equals(SEND_MODE)){
            this.clientTransferencesMap.put(dst_addr, new TransferenceControlPanel(src_addr, dst_addr, fileName));
        }
        else if(mode.equals(RECEIVE_MODE)){
            this.serverTransferencesMap.put(src_addr, new TransferenceControlPanel(src_addr, dst_addr, fileName));
        }
        this.updateTransferenceContent();
    }

    @Override
    public void updateTransference(String mode, String addr, int progress) {
        if(mode.equals(SEND_MODE)){
            this.clientTransferencesMap.get(addr).updateProgressBar(progress);
        }
        else if(mode.equals(RECEIVE_MODE)){
            this.serverTransferencesMap.get(addr).updateProgressBar(progress);
        }
        this.updateTransferenceContent();
    }

    @Override
    public void endTransference(String mode, String addr) {
        if(mode.equals(SEND_MODE)){
            this.clientTransferencesMap.remove(addr);
        }
        else if(mode.equals(RECEIVE_MODE)){
            this.serverTransferencesMap.remove(addr);
        } 

        if(this.clientTransferencesMap.isEmpty() && this.serverTransferencesMap.isEmpty()){
            this.clearTransferences();
        }
        else{
            this.updateTransferenceContent();
        }
    }

    private void updateTransferenceContent(){
        this.transferencesContentPanel.removeAll();

        for(Map.Entry<String, TransferenceControlPanel> mapElement : this.serverTransferencesMap.entrySet()) {
            this.transferencesContentPanel.add(mapElement.getValue());
        }
        for(Map.Entry<String, TransferenceControlPanel> mapElement : this.clientTransferencesMap.entrySet()) {
            this.transferencesContentPanel.add(mapElement.getValue());
        }

        this.transferencesContentPanel.validate();
        this.transferencesContentPanel.repaint();
    }
}

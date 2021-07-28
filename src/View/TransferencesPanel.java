package View;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;

import Controller.Controller;

public  class TransferencesPanel extends JPanel{
    private Controller controller;

    public TransferencesPanel(Controller controller){
        this.controller = controller;
        this.initGUI();
    }    

    private void initGUI(){
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.gray),"Transferences"));
    }
}

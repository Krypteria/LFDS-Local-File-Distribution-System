package View.Dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import View.MainWindow;

public class ChangeDefaultRouteDialog extends JDialog {
    
    private JFileChooser fileChooser;
    private JLabel routeText;
    private JTextArea routeArea;

    private JButton routeButton;
    private JButton changeButton;

    private int status;
    private String DownloadRoute;

    private MainWindow parent;

    public ChangeDefaultRouteDialog(MainWindow parent, String DownloadRoute){
        super(parent, true);
        this.DownloadRoute = DownloadRoute;
        this.parent = parent;
        this.status = 0;
        this.initGUI();
    }

    private void initGUI(){
        this.fileChooser = new JFileChooser();
        this.fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        this.routeText = new JLabel("Selected route:");
        this.routeArea = new JTextArea(this.DownloadRoute);
        this.routeArea.setEditable(false);

        this.routeArea.setPreferredSize(new Dimension(350,20));
        this.routeArea.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));

        this.routeButton = new JButton("Select new default route");
        this.routeButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                performChangeRouteAction();
            }
        });

        this.changeButton = new JButton("Submit changes");
        this.changeButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                performSubmitAction();
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2,1));

        JPanel p0 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p0.setPreferredSize(new Dimension(500, 50));
        p1.setPreferredSize(new Dimension(500, 50));
        p2.setPreferredSize(new Dimension(500, 50));
        
        p0.add(this.routeText);
        p0.add(Box.createRigidArea(new Dimension(10,0)));
        p0.add(this.routeArea);
        p1.add(this.routeButton);
        p1.add(this.changeButton);

        mainPanel.add(p0);
        mainPanel.add(p1);

        this.add(mainPanel);
        this.setMinimumSize(new Dimension(500,150));
        this.setResizable(false);
        this.setTitle("Change default download route");
        this.setLocationRelativeTo(this.parent);
        this.setVisible(false);
    }

    private void performChangeRouteAction(){
        int status = this.fileChooser.showOpenDialog(this.parent);

        if(status == 0){
            this.DownloadRoute = this.fileChooser.getSelectedFile().getAbsolutePath();
            this.routeArea.setText(this.DownloadRoute);
        }
    }

    private void performSubmitAction(){
        this.status = 1;
        this.setVisible(false);
    }

    public int open(){
        this.setVisible(true);
        return this.status;
    }

    public String getDownloadRoute(){
        return this.DownloadRoute;
    }
}

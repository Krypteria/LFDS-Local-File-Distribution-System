package View.Transferences;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class TransferenceControlPanel extends JPanel{

    private final Color backgroundColor = Color.white;
    private JProgressBar progressBar;
    private JLabel src_addrLabel;
    private JLabel dst_addrLabel;
    private JLabel fileNameLabel;
    
    public TransferenceControlPanel(String src_addr, String dst_addr, String fileName){
        this.src_addrLabel = new JLabel(src_addr);
        this.dst_addrLabel = new JLabel(dst_addr);
        this.fileNameLabel = new JLabel(fileName);
        this.initGUI();
    }

    private void initGUI(){
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBackground(null);

        this.src_addrLabel.setPreferredSize(new Dimension(100,22));
        this.dst_addrLabel.setPreferredSize(new Dimension(100,22));
        this.fileNameLabel.setPreferredSize(new Dimension(150,22));

        this.progressBar = new JProgressBar(0, 100);
        this.progressBar.setValue(0);
        this.progressBar.setStringPainted(true);

        JPanel headerInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel contentInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerInfo.setMaximumSize(new Dimension(530,22));
        contentInfo.setMaximumSize(new Dimension(530,22));
        headerInfo.setBackground(this.backgroundColor);
        contentInfo.setBackground(this.backgroundColor);

        headerInfo.add(new JLabel("Source:"));
        headerInfo.add(this.src_addrLabel);
        headerInfo.add(new JLabel("Destination:"));
        headerInfo.add(this.dst_addrLabel);
        
        contentInfo.add(new JLabel("File name:"));
        contentInfo.add(this.fileNameLabel);
        contentInfo.add(new JLabel("Progress:"));
        contentInfo.add(this.progressBar);
    
        this.add(headerInfo);
        this.add(contentInfo);
        this.setMaximumSize(new Dimension(530,80));
        this.setBorder(BorderFactory.createMatteBorder(1, 1, 2, 1, Color.gray));
        this.setVisible(true);
    }

    public void updateProgressBar(int progress){
        this.progressBar.setValue(progress);
    }
}

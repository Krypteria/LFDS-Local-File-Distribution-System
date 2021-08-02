package View.Transferences;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class TransferenceControlPanel extends JPanel{

    private final Color backgroundColor = Color.white;
    private static final float [] greenColor = Color.RGBtoHSB(28, 150, 78, null); 
	private static final float [] redColor = Color.RGBtoHSB(160, 21, 21, null); 

    private JProgressBar progressBar;
    private JLabel src_addrLabel;
    private JLabel dst_addrLabel;
    private JLabel fileNameLabel;
    private JLabel modeLabel;
    
    private String mode;
    
    public TransferenceControlPanel(String mode, String src_addr, String dst_addr, String fileName){
        this.mode = mode;
        this.modeLabel = new JLabel("[" + mode + "]");
        this.src_addrLabel = new JLabel(src_addr);
        this.dst_addrLabel = new JLabel(dst_addr);
        this.fileNameLabel = new JLabel(fileName);
        this.initGUI();
    }

    private void initGUI(){
        this.setLayout(new BorderLayout());
        this.setBackground(null);

        if(this.mode.equals("Sending")){
            this.modeLabel.setForeground(Color.getHSBColor(redColor[0], redColor[1], redColor[2]));
        }
        else{
            this.modeLabel.setForeground(Color.getHSBColor(greenColor[0], greenColor[1], greenColor[2]));
        }

        this.modeLabel.setPreferredSize(new Dimension(70,20));
        this.src_addrLabel.setPreferredSize(new Dimension(100,20));
        this.dst_addrLabel.setPreferredSize(new Dimension(100,20));
        this.fileNameLabel.setPreferredSize(new Dimension(150,20));

        this.progressBar = new JProgressBar(0, 100);
        this.progressBar.setValue(0);
        this.progressBar.setStringPainted(true);

        JPanel modeInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel headerInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel contentInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        modeInfo.setPreferredSize(new Dimension(530,30));
        modeInfo.setMaximumSize(new Dimension(530,30));
        headerInfo.setMaximumSize(new Dimension(530,22));
        contentInfo.setMaximumSize(new Dimension(530,22));

        modeInfo.setBackground(this.backgroundColor);
        headerInfo.setBackground(this.backgroundColor);
        contentInfo.setBackground(this.backgroundColor);

        modeInfo.add(this.modeLabel);
        
        headerInfo.add(new JLabel("Source:"));
        headerInfo.add(this.src_addrLabel);
        headerInfo.add(new JLabel("Destination:"));
        headerInfo.add(this.dst_addrLabel);
        
        contentInfo.add(new JLabel("File name:"));
        contentInfo.add(this.fileNameLabel);
        contentInfo.add(new JLabel("Progress:"));
        contentInfo.add(this.progressBar);
    
        this.add(modeInfo, BorderLayout.PAGE_START);
        this.add(headerInfo, BorderLayout.CENTER);
        this.add(contentInfo, BorderLayout.PAGE_END);
        this.setMaximumSize(new Dimension(530,90));
        this.setBorder(BorderFactory.createMatteBorder(1, 1, 2, 1, Color.gray));
        this.setVisible(true);
    }

    public void updateProgressBar(int progress){
        this.progressBar.setValue(progress);
    }
}

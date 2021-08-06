package View.Transferences;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class TransferenceControlPanel extends JPanel{

    private final Color backgroundColor = Color.white;
    private static final float [] greenColor = Color.RGBtoHSB(28, 150, 78, null); 
	private static final float [] redColor = Color.RGBtoHSB(160, 21, 21, null); 
    private final int MAX_WIDTH = 500;

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
        JPanel firstContentInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel secondContentInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        modeInfo.setPreferredSize(new Dimension(MAX_WIDTH,25));
        modeInfo.setMaximumSize(new Dimension(MAX_WIDTH,26));
        firstContentInfo.setMaximumSize(new Dimension(MAX_WIDTH,25));
        secondContentInfo.setMaximumSize(new Dimension(MAX_WIDTH,25));

        modeInfo.setBackground(this.backgroundColor);
        firstContentInfo.setBackground(this.backgroundColor);
        secondContentInfo.setBackground(this.backgroundColor);

        modeInfo.add(this.modeLabel);

        JLabel sourceLabel = new JLabel("From:");
        JLabel destinationLabel = new JLabel("To:");
        JLabel filenameTitleLabel = new JLabel("File name:");
        JLabel progressLabel = new JLabel("Progress:");

        sourceLabel.setPreferredSize(new Dimension(35,25));
        destinationLabel.setPreferredSize(new Dimension(35,25));
        filenameTitleLabel.setPreferredSize(new Dimension(60,25));
        progressLabel.setPreferredSize(new Dimension(60,25));

        firstContentInfo.add(sourceLabel);
        firstContentInfo.add(this.src_addrLabel);
        firstContentInfo.add(Box.createRigidArea(new Dimension(20,0)));
        firstContentInfo.add(filenameTitleLabel);
        firstContentInfo.add(this.fileNameLabel);

        secondContentInfo.add(destinationLabel);
        secondContentInfo.add(this.dst_addrLabel);
        secondContentInfo.add(Box.createRigidArea(new Dimension(20,0)));
        secondContentInfo.add(progressLabel);
        secondContentInfo.add(this.progressBar);
    
        this.add(modeInfo, BorderLayout.PAGE_START);
        this.add(firstContentInfo, BorderLayout.CENTER);
        this.add(secondContentInfo, BorderLayout.PAGE_END);
        this.setMaximumSize(new Dimension(530,74));
        this.setBorder(BorderFactory.createMatteBorder(1, 1, 2, 1, Color.gray));
        this.setVisible(true);
    }

    public void updateProgressBar(int progress){
        this.progressBar.setValue(progress);
    }
}

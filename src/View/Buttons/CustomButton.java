package View.Buttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import Controller.Controller;

public abstract class CustomButton extends JButton implements MouseListener {
	
	protected Controller controller;
	private String imagePath;
	private String imagePath_large;
	private String tooltipText;
	private int width;
	private int height;
	
	public CustomButton(Controller c, String imagePath, String imagePath_large, String tt, int w, int h) {
		this.controller = c;
		this.imagePath = imagePath;
		this.imagePath_large = imagePath_large;
		this.tooltipText = tt;
		this.width = w;
		this.height = h;
		initGUI();
	}

	private void initGUI() {
		this.setIcon(new ImageIcon(imagePath));
		this.setToolTipText(this.tooltipText);
		this.setPreferredSize(new Dimension(this.width,this.height));
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		this.addMouseListener(this);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		this.setIcon(new ImageIcon(imagePath_large));
		this.updateUI();
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		this.setIcon(new ImageIcon(imagePath));
		this.updateUI();
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

}


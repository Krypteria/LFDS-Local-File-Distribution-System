package View.Buttons;

import Controller.Controller;

public class CloseServerButton extends CustomButton {
    private final static String imagePath = "closeserver.png";
	private final static String imagePath_g = "closeserver_g.png";
	private final static String tooltipText = "Close server";
    
    public CloseServerButton(Controller controller){
        super(controller, imagePath, imagePath_g, tooltipText, 40, 40);
    }
}

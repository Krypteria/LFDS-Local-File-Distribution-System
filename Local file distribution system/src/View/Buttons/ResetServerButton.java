package View.Buttons;

import Controller.Controller;

public class ResetServerButton extends CustomButton{
    private final static String imagePath = "resetserver.png";
	private final static String imagePath_g = "resetserver_g.png";
	private final static String tooltipText = "Reset server";
    
    public ResetServerButton(Controller controller){
        super(controller, imagePath, imagePath_g, tooltipText, 40, 40);
    }
}

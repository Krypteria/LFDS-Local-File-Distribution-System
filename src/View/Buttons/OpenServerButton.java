package View.Buttons;

import Controller.Controller;

public class OpenServerButton extends CustomButton{
    private final static String imagePath = "icons/openserver.png";
	private final static String imagePath_g = "icons/openserver_g.png";
	private final static String tooltipText = "Open server";
    
    public OpenServerButton(Controller controller){
        super(controller, imagePath, imagePath_g, tooltipText, 40, 40);
    }
}

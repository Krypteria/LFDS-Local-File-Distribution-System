package View.Buttons;

import Controller.Controller;

public class ShowIPAddressButton extends CustomButton{
    private final static String imagePath = "showipaddress.png";
	private final static String imagePath_g = "showipaddress_g.png";
	private final static String tooltipText = "Show current IP address";
    
    public ShowIPAddressButton(Controller controller){
        super(controller, imagePath, imagePath_g, tooltipText, 40, 40);
    }
}
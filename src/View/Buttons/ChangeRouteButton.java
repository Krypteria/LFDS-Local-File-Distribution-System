package View.Buttons;

import Controller.Controller;

public class ChangeRouteButton extends CustomButton{
    private final static String imagePath = "changeroute.png";
	private final static String imagePath_g = "changeroute_g.png";
	private final static String tooltipText = "Change current download route";
    
    public ChangeRouteButton(Controller controller){
        super(controller, imagePath, imagePath_g, tooltipText, 40, 40);
    }
}

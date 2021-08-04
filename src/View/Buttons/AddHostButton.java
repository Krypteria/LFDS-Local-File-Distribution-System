package View.Buttons;

import Controller.Controller;

public class AddHostButton extends CustomButton{
    private final static String imagePath = "icons/addhost.png";
	private final static String imagePath_g = "icons/addhost_g.png";
	private final static String tooltipText = "Add a new host";
    
    public AddHostButton(Controller controller){
        super(controller, imagePath, imagePath_g, tooltipText, 30, 30);
    }
}

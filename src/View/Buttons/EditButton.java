package View.Buttons;

import Controller.Controller;

public class EditButton extends CustomButton{
    private final static String imagePath = "icons/edit.png";
	private final static String imagePath_g = "icons/edit_g.png";
	private final static String tooltipText = "Edit the host information";
    
    public EditButton(Controller controller){
        super(controller, imagePath, imagePath_g, tooltipText, 25, 25);
    }
}

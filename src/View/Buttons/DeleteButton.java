package View.Buttons;

import Controller.Controller;

public class DeleteButton extends CustomButton{
    private final static String imagePath = "delete.png";
	private final static String imagePath_g = "delete_g.png";
	private final static String tooltipText = "Deletes the host";
    
    public DeleteButton(Controller controller){
        super(controller, imagePath, imagePath_g, tooltipText, 25, 25);
    }
}

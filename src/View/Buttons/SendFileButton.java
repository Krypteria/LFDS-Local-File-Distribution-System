package View.Buttons;

import Controller.Controller;

public class SendFileButton extends CustomButton{
    private final static String imagePath = "icons/send.png";
	private final static String imagePath_g = "icons/send_g.png";
	private final static String tooltipText = "Send the selected file";
    
    public SendFileButton(Controller controller){
        super(controller, imagePath, imagePath_g, tooltipText, 40, 40);
    }
}

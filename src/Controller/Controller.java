package Controller;

import java.util.List;

import Model.Host;
import Model.Local_distribution_system;

public class Controller{
    
    private Local_distribution_system lds;

    public Controller(){
        this.lds = new Local_distribution_system();
    }

    public List<Host> getAllHosts(){
        return lds.getAllHosts();
    }
}
package Controller;

import java.io.File;

import Model.Local_distribution_system;
import Model.Observers.HostsObserver;

public class Controller{
    
    private Local_distribution_system localDistributionSystem;

    public Controller(){
        this.localDistributionSystem = new Local_distribution_system();
    }

    //Observer methods
    public void addObserver(HostsObserver observer) {
        this.localDistributionSystem.addObserver(observer);  
    }

    //Server control methods
    public void openServer(){
        this.localDistributionSystem.openServer();
    }

    public void closeServer(){
        this.localDistributionSystem.closeServer();
    }   

    public void resetServer(){
        this.localDistributionSystem.resetServer();
    }

    //Networking methods
    public void sendFile(String dst_addr, File file){
        this.localDistributionSystem.sendFile(dst_addr, file);
    }

    //Other methods
    public void editHost(String name, String addr, String newAddr){
        this.localDistributionSystem.editHost(name, addr, newAddr);
    }

    public void addNewHost(String name, String addr){
        this.localDistributionSystem.addNewHost(name, addr);
    }

    public void removeHost(String addr){ 
        this.localDistributionSystem.removeHost(addr);
    }
}
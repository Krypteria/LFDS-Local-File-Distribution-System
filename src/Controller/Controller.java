package Controller;

import java.io.File;

import Model.Local_distribution_system;
import Model.Exceptions.ServerRunTimeException;
import Model.Observers.HostsObserver;
import Model.Observers.ServerObserver;
import Model.Observers.TransferencesObserver;

public class Controller{
    
    private Local_distribution_system localDistributionSystem;

    public Controller(){
        this.localDistributionSystem = new Local_distribution_system();
    }

    //Observer methods
    public void addObserver(HostsObserver observer) {
        this.localDistributionSystem.addObserver(observer);  
    }

    public void addObserver(ServerObserver observer) {
        this.localDistributionSystem.addObserver(observer);
    }

    public void addTransferenceObserverClient(TransferencesObserver observer) {
        this.localDistributionSystem.addTransferenceObserverClient(observer);
    }

    public void addTransferenceObserverServer(TransferencesObserver observer) {
        this.localDistributionSystem.addTransferenceObserverServer(observer);
    }

    //Server control methods
    public void openServer() throws ServerRunTimeException{
        this.localDistributionSystem.openServer();
    }

    public void closeServer() throws ServerRunTimeException{
        this.localDistributionSystem.closeServer();
    }   

    public void resetServer() throws ServerRunTimeException{
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
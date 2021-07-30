package Model;

import java.io.File;

import Model.Exceptions.ServerRunTimeException;
import Model.Observers.HostsObserver;
import Model.Observers.ServerObserver;
import Model.Observers.TransferencesObserver;


public class Local_distribution_system {
    private HostsRegister hostsRegister;
    private Server server;

    public Local_distribution_system(){
        this.hostsRegister = new HostsRegister();
        this.server = new Server();
        this.openServer();
    }

    //Server methods
    public void openServer() throws ServerRunTimeException{
        this.server.openServer();
    }
    
    public void closeServer() throws ServerRunTimeException{
        this.server.closeServer();
    }

    public void resetServer() throws ServerRunTimeException{
        this.server.resetServer();
    }

    //Hosts methods
    public void addNewHost(String name, String addr){
        this.hostsRegister.addNewHost(name, addr);
    }

    public void editHost(String name, String addr, String newAddr){
        this.hostsRegister.editHost(name, addr, newAddr);
    }

    public void removeHost(String addr){ 
        this.hostsRegister.removeHost(addr);
    }

    //Networking methods
    public void sendFile(String dst_addr, File file){
        Client client = new Client(dst_addr, file);
        new Thread(client).start();
    }


    //Serialitation methods
    public void saveAppState(){
        Local_distribution_system_DAO appDao = new Local_distribution_system_DAO();
        appDao.saveAppState();
    }

    public void loadAppState(){
        Local_distribution_system_DAO appDao = new Local_distribution_system_DAO();
        appDao.loadAppState();
    }

    public boolean seekValidDataFile(){
        Local_distribution_system_DAO appDao = new Local_distribution_system_DAO();
        return appDao.seekValidDataFile();
    }

    //Observer methods
    public void addObserver(HostsObserver observer) {
        this.hostsRegister.addObserver(observer);  
    }

    public void addObserver(ServerObserver observer) {
        this.server.addObserver(observer);
    }
  
    public void addTransferenceObserverServer(TransferencesObserver observer) {
        this.server.addTransferenceObserver(observer);
    }

    public void removeTransferenceObserverServer(TransferencesObserver observer) {
        this.server.removeTransferenceObserver(observer);
    }   
    
    /*public void addTransferenceObserverClient(TransferencesObserver observer) {
        this.server.addTransferenceObserver(observer);
    }

    public void removeTransferenceObserverClient(TransferencesObserver observer) {
        this.server.removeTransferenceObserver(observer);
    } */
}

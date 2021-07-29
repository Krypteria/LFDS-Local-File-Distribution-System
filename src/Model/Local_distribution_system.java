package Model;

import java.io.File;

import Model.Observers.HostsObserver;
import Model.Observers.ServerObserver;



public class Local_distribution_system {
    private HostsRegister hostsRegister;
    private Server server;

    public Local_distribution_system(){
        this.hostsRegister = new HostsRegister();
        this.server = new Server();
        this.openServer();
    }

    //Server methods
    public void openServer(){
        this.server.openServer();
    }
    
    public void closeServer(){
        this.server.closeServer();
    }

    public void resetServer(){
        this.closeServer();
        this.openServer();
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
}

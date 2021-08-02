package Model;

import java.io.File;
import java.io.FileNotFoundException;

import org.json.JSONObject;

import Model.Client.ClientsManager;
import Model.Exceptions.ServerRunTimeException;
import Model.Observers.HostsObserver;
import Model.Observers.ServerObserver;
import Model.Observers.TransferencesObserver;


public class Local_distribution_system implements UseState{
    private HostsManager hostsManager;
    private ClientsManager clientsManager;
    private Server server;

    public Local_distribution_system(){
        this.hostsManager = new HostsManager();
        this.clientsManager = new ClientsManager();
        this.server = new Server();
        this.openServer();
        
        this.loadAppState();
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
        this.hostsManager.addNewHost(name, addr);
        this.saveAppState();
    }

    public void editHost(String name, String addr, String newAddr){
        this.hostsManager.editHost(name, addr, newAddr);
        this.saveAppState();
    }

    public void removeHost(String addr){ 
        this.hostsManager.removeHost(addr);
        this.saveAppState();
    }

    //Networking methods
    public void sendFile(String dst_addr, File file){
        this.clientsManager.createClient(dst_addr, file);
    }


    //Serialitation methods
    public void saveAppState(){
        Local_distribution_system_DAO appDao = new Local_distribution_system_DAO();
        try {
            appDao.saveAppState(this.getState());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadAppState(){
        Local_distribution_system_DAO appDao = new Local_distribution_system_DAO();
        try {
            TransferObject transferObject;
            transferObject = appDao.loadAppState();
            this.setState(transferObject);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Observer methods
    public void addObserver(HostsObserver observer) {
        this.hostsManager.addObserver(observer);  
    }

    public void addObserver(ServerObserver observer) {
        this.server.addObserver(observer);
    }
  
    public void addTransferenceObserverServer(TransferencesObserver observer) {
        this.server.addTransferenceObserver(observer);
    }

    public void addTransferenceObserverClient(TransferencesObserver observer) {
        this.clientsManager.addTransferenceObserver(observer);
    }

    @Override
    public void setState(TransferObject transferObject) {
        if(transferObject != null){
            TransferObject hostManagerTransferObject = new TransferObject();
            JSONObject state = transferObject.getState();
    
            JSONObject hostManagerState = state.getJSONObject("hostManagerState");
            hostManagerTransferObject.setState(hostManagerState);
            this.hostsManager.setState(hostManagerTransferObject);
        }
    }

    @Override
    public TransferObject getState() {
        TransferObject transferObject = new TransferObject();
        JSONObject state = new JSONObject();

        state.put("hostManagerState", hostsManager.getState().getState());
        transferObject.setState(state);
        return transferObject;
    }
}

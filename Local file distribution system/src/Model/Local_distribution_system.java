package Model;

import java.io.File;
import java.io.FileNotFoundException;

import org.json.JSONObject;

import Model.Client.ClientsManager;
import Model.Exceptions.HostRunTimeException;
import Model.Exceptions.ServerRunTimeException;
import Model.Host.HostsManager;
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
        this.server.openProcedure();

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

    public void changeDefaultDownloadRoute(String route){
        this.server.changeDefaultDownloadRoute(route);
        this.saveAppState();
    }

    public void exitProcedure(){
        this.server.exitProcedure();
    }

    //Hosts methods
    public void addNewHost(String name, String addr) throws HostRunTimeException{
        this.hostsManager.addNewHost(name, addr);
        this.saveAppState();
    }

    public void editHost(String name, String addr, String newAddr) throws HostRunTimeException{
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

    //Serialization methods
    @Override
    public void setState(TransferObject transferObject) {
        if(transferObject != null){
            JSONObject state = transferObject.getState();
            JSONObject stateContent = state.getJSONObject("configFile");

            if(stateContent.has("hostManagerState")){
                TransferObject hostManagerTransferObject = new TransferObject();
                JSONObject hostManagerState = stateContent.getJSONObject("hostManagerState");
                hostManagerTransferObject.setState(hostManagerState);
                this.hostsManager.setState(hostManagerTransferObject);
            }
            if(stateContent.has("serverState")){
                TransferObject serverTransferObject = new TransferObject();
                JSONObject serverState = stateContent.getJSONObject("serverState");
                serverTransferObject.setState(serverState);
                this.server.setState(serverTransferObject);
            }
        }
    }

    @Override
    public TransferObject getState() {
        TransferObject transferObject = new TransferObject();
        JSONObject state = new JSONObject();
        JSONObject stateContent = new JSONObject();
        stateContent.put("hostManagerState", this.hostsManager.getState().getState());
        stateContent.put("serverState", this.server.getState().getState());

        state.put("configFile", stateContent);
        transferObject.setState(state);
        return transferObject;
    }
}

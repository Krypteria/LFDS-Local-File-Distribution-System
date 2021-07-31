package Model.Client;

import java.io.File;
import java.util.HashMap;

import Model.Observers.TransferencesObserver;

public class ClientsManager {
    
    private HashMap<String, Client> clientsMap;
    private TransferencesObserver observer;

    public ClientsManager(){
        this.clientsMap = new HashMap<String, Client>();
    }

    public void createClient(String dst_addr, File file){
        Client client = new Client(dst_addr, file, this);
        client.addTransferenceObserver(this.observer);
        this.clientsMap.put(dst_addr, client);

        new Thread(client).start();; 
    }

    public void removeClient(String dst_addr){
        this.clientsMap.remove(dst_addr);
    }

    //Observer methods
    public void addTransferenceObserver(TransferencesObserver observer){
        this.observer = observer;
    }
}

package Model.Client;

import java.io.File;

import Model.Observers.TransferencesObserver;

public class Client implements Runnable{

    private ClientsManager clientsManager;
    private Client_networking networking;
    private File file;
    private String dst_addr;

    public Client(String dst_addr, File file, ClientsManager clientsManager){
        this.clientsManager = clientsManager;
        this.networking = new Client_networking(dst_addr);
        this.file = file;
        this.dst_addr = dst_addr;
    }

    @Override
    public void run() {
        this.networking.send(this.file);
        this.clientsManager.removeClient(this.dst_addr);
    }


    //Observer methods
    public void addTransferenceObserver(TransferencesObserver observer){
        this.networking.addTransferenceObserver(observer);
    }
}
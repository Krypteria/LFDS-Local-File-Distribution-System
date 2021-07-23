package Model;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


public class Local_distribution_system {
    private HashMap<String, Host> hostsMap;
    private Server server;

    public Local_distribution_system(){
        this.hostsMap = new HashMap<>();
        this.server = new Server();
        this.openServer();

        this.addNewHost("Alejandro", "192.168.1.31");
        this.addNewHost("Jaime", "192.168.110.31");
        this.addNewHost("Juan Manuel", "192.2.1.31");
    }

    public void addNewHost(String name, String addr){
        this.hostsMap.put(addr, new Host(name, addr));
    }

    public void editHost(){

    }

    public void removeHost(){

    }

    public List<Host> getAllHosts(){
        return Collections.unmodifiableList(new ArrayList<Host>(this.hostsMap.values()));
    } 

    //Networking methods
    
    //GUI -> selecciono fichero e ip destino
    //Host -> stats -> 
    public void sendFile(String addr_dst, File file){
        Client client = new Client(addr_dst, file);
        new Thread(client).start();
    }

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
}

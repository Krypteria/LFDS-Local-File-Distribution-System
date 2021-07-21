package Model;
import java.util.HashMap;
import java.util.Map;

public class Local_distribution_system {
    private HashMap<String, Host> listaHosts;

    public Local_distribution_system(){
        this.listaHosts = new HashMap<>();
    }

    public void addNewHost(String name, String IP){
        this.listaHosts.put(IP, new Host(name, IP));
    }

    public void editHost(){

    }

    public void removeHost(){

    }

    public void seeHosts(){
        for (Map.Entry<String, Host> elemento : this.listaHosts.entrySet()) {
            String IP = elemento.getKey();
            Host host = elemento.getValue();

            System.out.print(IP + ":" + host.getName() + " " + host.getStats());
        }
    }

    //Networking methods

    public void sendFile(){

    }

    public void openServer(){

    }
    
    public void closeServer(){

    }

    public void resetServer(){

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

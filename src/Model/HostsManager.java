package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import Model.Observers.HostsObserver;
import Model.Observers.Observable;

public class HostsManager implements Observable<HostsObserver>{
    private HashMap<String, Host> hostsMap;
    private List<HostsObserver> hostsObserverList;

    public HostsManager(){
        this.hostsMap = new HashMap<>();    
        this.hostsObserverList = new ArrayList<HostsObserver>();
        this.TEMPORAL();
    }

    //Temporal hasta que defina el sistema de serializacion, la primera carga sería del tirón, no updateando cada vez
    private void TEMPORAL(){
        this.hostsMap.put("192.168.1.39", new Host("Portatil", "192.168.1.39")); 
        this.hostsMap.put("0.0.0.0", new Host("Localhost", "0.0.0.0")); 
    }

    public void addNewHost(String name, String addr){
        this.hostsMap.put(addr, new Host(name, addr));
        this.updateGUIHosts();
    }

    public void editHost(String name, String addr, String newAddr){
        Host host = this.hostsMap.get(addr);
        host.setName(name);
        host.setAddr(newAddr);

        this.hostsMap.remove(addr);
        this.hostsMap.put(newAddr, host);
        this.updateGUIHosts();
    }

    public void removeHost(String addr){ 
        this.hostsMap.remove(addr);
        this.updateGUIHosts();
    }

    public void updateGUIHosts(){
        for(HostsObserver observer : this.hostsObserverList){
            observer.updateAllHosts(Collections.unmodifiableList(new ArrayList<Host>(this.hostsMap.values())));
        }
    } 

    @Override
    public void addObserver(HostsObserver observer) {
        this.hostsObserverList.add(observer);  
        this.updateGUIHosts();
    }

}

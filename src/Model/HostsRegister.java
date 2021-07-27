package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import Model.Observers.HostsObserver;
import Model.Observers.Observable;

public class HostsRegister implements Observable<HostsObserver>{
    private HashMap<String, Host> hostsMap;
    private List<HostsObserver> hostsObserverList;

    public HostsRegister(){
        this.hostsMap = new HashMap<>();    
        this.hostsObserverList = new ArrayList<HostsObserver>();
        this.TEMPORAL();
    }

    //Temporal hasta que defina el sistema de serializacion, la primera carga sería del tirón, no updateando cada vez
    private void TEMPORAL(){
        this.hostsMap.put("192.168.1.31", new Host("012345678901234", "192.168.1.31")); 
        this.hostsMap.put("192.168.110.310", new Host("012345", "192.168.110.310")); 
        this.hostsMap.put("10.2.1.1", new Host("01234567890", "10.2.1.1")); 
        //this.hostsMap.put("10.2.1.2", new Host("afsdfdsa", "10.2.1.2")); 
        //this.hostsMap.put("10.2.1.3", new Host("afsdfdsa", "10.2.1.3")); */
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
            observer.updateHosts(Collections.unmodifiableList(new ArrayList<Host>(this.hostsMap.values())));
        }
    } 

    @Override
    public void addObserver(HostsObserver observer) {
        this.hostsObserverList.add(observer);  
        this.updateGUIHosts();
    }

    @Override
    public void removeObserver(HostsObserver observer) {
        this.hostsObserverList.remove(observer);
    }
}

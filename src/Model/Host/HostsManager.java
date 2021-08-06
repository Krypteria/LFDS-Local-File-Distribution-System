package Model.Host;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import Model.TransferObject;
import Model.UseState;
import Model.Exceptions.HostRunTimeException;
import Model.Observers.HostsObserver;
import Model.Observers.Observable;

public class HostsManager implements Observable<HostsObserver>, UseState{
    private HashMap<String, Host> hostsMap;
    private List<HostsObserver> hostsObserverList;

    public HostsManager(){
        this.hostsMap = new HashMap<>();    
        this.hostsObserverList = new ArrayList<HostsObserver>();
    }

    public void addNewHost(String name, String addr) throws HostRunTimeException{
        if(!this.hostsMap.containsKey(addr)){
            this.hostsMap.put(addr, new Host(name, addr));
            this.updateGUIHosts();
        }
        else{
            throw new HostRunTimeException("That address is already taken");
        }
    }

    public void editHost(String name, String addr, String newAddr) throws HostRunTimeException{
        if(addr.equals(newAddr) || !this.hostsMap.containsKey(newAddr)){
            Host host = this.hostsMap.get(addr);
            host.setName(name);
            host.setAddr(newAddr);
    
            this.hostsMap.remove(addr);
            this.hostsMap.put(newAddr, host);
            this.updateGUIHosts();
        }
        else{
            throw new HostRunTimeException("That address is already taken");
        }
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

    @Override
    public void setState(TransferObject transferObject) {
        JSONObject state = transferObject.getState();
        JSONArray hostsState = state.getJSONArray("hostsStateArray");

        for(int i = 0; i < hostsState.length(); i++){
            TransferObject hostTransferObject = new TransferObject();
            hostTransferObject.setState(hostsState.getJSONObject(i));

            Host host = new Host(null, null);
            host.setState(hostTransferObject);

            this.hostsMap.put(host.getAddress(), host);
        }
    }

    @Override
    public TransferObject getState() {
        TransferObject transferObject = new TransferObject();
        JSONObject state = new JSONObject();

        JSONArray hostsState = new JSONArray();
        for(Map.Entry<String, Host> entry : this.hostsMap.entrySet()) {
            Host host = entry.getValue();
            hostsState.put(host.getState().getState());
        }

        state.put("hostsStateArray", hostsState);
        transferObject.setState(state);
        return transferObject;
    }

}

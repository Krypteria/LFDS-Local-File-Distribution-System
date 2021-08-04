package Model.Host;

import org.json.JSONObject;

import Model.TransferObject;
import Model.UseState;

public class Host implements UseState{
    private String name;
    private String addr;

    public Host(String name, String addr){
        this.name = name;
        this.addr = addr;
    }

    public String getName(){
        return this.name;
    }

    public String getAddress(){
        return this.addr;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAddr(String addr){
        this.addr = addr;
    }

    @Override
    public void setState(TransferObject transferObject) {
        JSONObject state = transferObject.getState();

        JSONObject stateInfo = state.getJSONObject("hostState");
        this.name = stateInfo.getString("name");
        this.addr = stateInfo.getString("address");
    }
    
    @Override
    public TransferObject getState() {
        TransferObject transferObject = new TransferObject();
        JSONObject state = new JSONObject();
        JSONObject stateInfo = new JSONObject();

        stateInfo.put("name", this.name);
        stateInfo.put("address", this.addr);
        state.put("hostState", stateInfo);
        
        transferObject.setState(state);
        return transferObject;
    } 
}
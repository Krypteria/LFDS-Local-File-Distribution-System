package Model;

import org.json.JSONObject;

public class TransferObject{
    private JSONObject state;

    public JSONObject getState(){
        return this.state;
    }

    public void setState(JSONObject state){
        this.state = state;
    }
}
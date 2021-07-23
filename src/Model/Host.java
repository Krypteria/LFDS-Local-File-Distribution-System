package Model;

public class Host{
    private String name;
    private String addr;
    private Stats stats;

    public Host(String name, String addr){
        this.name = name;
        this.addr = addr;
        this.stats = new Stats();
    }

    public String getName(){
        return this.name;
    }

    public String getAddress(){
        return this.addr;
    }

    public String getStats(){
        return this.stats.getStats();
    }
}
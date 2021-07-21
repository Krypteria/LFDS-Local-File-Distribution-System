package Model;

public class Host{
    private String name;
    private String IP;
    private Stats stats;

    public Host(String name, String IP){
        this.name = name;
        this.IP = IP;
        this.stats = new Stats();
    }

    public String getName(){
        return this.name;
    }

    public String getStats(){
        return this.stats.getStats();
    }
}
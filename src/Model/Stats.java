package Model;

public class Stats{
    private int numFilesSend;
    private int numFilesReceived;
    private double numMBsSend;
    private double numMBsReceived;

    public Stats(){
        this.numFilesSend = 0;
        this.numFilesReceived = 0;
        this.numMBsSend = 0;
        this.numMBsReceived = 0;
    }

    public String getStats(){
        return "numFilesSend: " + this.numFilesSend + " numFilesReceived: " + this.numFilesReceived + 
            " numMBsSend: " + this.numMBsSend + " numMBsReceived: " + this.numMBsReceived + "\n";
    }

    //setstats, clearstats
}
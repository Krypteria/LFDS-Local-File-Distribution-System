package Model.Exceptions;

public class HostRunTimeException extends RuntimeException {
    public HostRunTimeException(){
        super();
    }
    
    public HostRunTimeException(String msg){
        super(msg);
    }
}

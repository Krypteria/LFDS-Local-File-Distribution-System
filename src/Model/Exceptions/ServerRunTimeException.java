package Model.Exceptions;

public class ServerRunTimeException extends RuntimeException {
    public ServerRunTimeException(){
        super();
    }
    
    public ServerRunTimeException(String msg){
        super(msg);
    }
}

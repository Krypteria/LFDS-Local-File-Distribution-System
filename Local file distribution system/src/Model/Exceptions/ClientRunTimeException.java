package Model.Exceptions;

public class ClientRunTimeException extends RuntimeException {
    public ClientRunTimeException(){
        super();
    }
    
    public ClientRunTimeException(String msg){
        super(msg);
    }
}

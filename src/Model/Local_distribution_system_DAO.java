package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONObject;
import org.json.JSONTokener;

public class Local_distribution_system_DAO{

    private final String directoryPath = System.getProperty("user.home")+"/config/"; 
    private final String fileName = "config.json";

    public void saveAppState(TransferObject transferObject) throws FileNotFoundException{
        File directory = new File(this.directoryPath);
        if(!directory.exists()){
            directory.mkdir();
        }

        OutputStream output = new FileOutputStream(new File(directory.getAbsolutePath() + "\\" + this.fileName));
        PrintStream printStream = new PrintStream(output);

        printStream.println(transferObject.getState().toString(3));
        printStream.close();
    }

    public TransferObject loadAppState() throws FileNotFoundException{
        File directory = new File(this.directoryPath);
        File configFile = new File(directory.getAbsolutePath() + "\\" + this.fileName);
        
        if(directory.exists() && configFile.exists()){
            TransferObject transferObject = new TransferObject();
    
            InputStream input = new FileInputStream(new File(this.directoryPath + this.fileName));
            JSONObject state = new JSONObject(new JSONTokener(input));
            transferObject.setState(state);
    
            return transferObject;
        }

        return null;
    }
}
package Model.Client;

import java.net.Socket;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import Misc.Pair;
import Model.Exceptions.ClientRunTimeException;
import Model.Observers.TransferenceObservable;
import Model.Observers.TransferencesObserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Client_networking implements TransferenceObservable<TransferencesObserver>{
    
    private final int PORT = 2020;
    private final int BUFFERSIZE = 65536;
    private final String SEND_MODE = "send";

    private Socket clientSocket;
    private byte[] buffer;
    
    private DataOutputStream output;
    private DataInputStream input;
    
    private String src_addr;
    private String dst_addr;
    
    private long totalFileSize;
    private long totalBytesReaded;

    private List<Pair<String, Long>> filePaths;
    private List<TransferencesObserver> transferenceObserversList;

    public Client_networking(String dst_addr){
        try{
            this.src_addr = Inet4Address.getLocalHost().getHostAddress();
            this.dst_addr = dst_addr;

            this.totalFileSize = 0;
            this.totalBytesReaded = 0;

            this.transferenceObserversList = new ArrayList<TransferencesObserver>();
            this.buffer = new byte[this.BUFFERSIZE];
            this.filePaths = new ArrayList<Pair<String, Long>>();
        }
        catch(UnknownHostException e){
            System.out.println("Destination address not valid");
        }
    }

    public void send(File file){
        try{
            this.clientSocket = new Socket();
            try{
                this.clientSocket.connect(new InetSocketAddress(dst_addr, this.PORT));
            }
            catch(IOException e){
                throw new ClientRunTimeException("The server " + dst_addr + " is not avalaible");
            }

            this.notifyAddToTransferenceObservers(file.getName());

            if(file.isDirectory()){
                this.sendHeader(file.getName(), this.getDirectoryHeader(file, 1));
                sendFiles(file);
            }
            else{
                this.sendHeader(file.getName(), this.getFileHeader(file, 1));
                sendFile(file, file.length());
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new ClientRunTimeException("Error during wait operation");
            }

            this.notifyRemoveToTransferenceObservers();

            try{
                this.input.close();
                this.output.close(); 
                this.clientSocket.close();
            }catch(IOException e){
                throw new ClientRunTimeException("Error while closing resources");
            }
        }
        catch(ClientRunTimeException e){
            this.notifyRemoveToTransferenceObservers();
            this.notifyException(e.getMessage());
        }
    }

    // ---- Header ----
    private void sendHeader(String fileName, String header) throws ClientRunTimeException{
        try {
            header = header.substring(0, header.length() - 1); //last \n deleted
            header = ""+this.totalFileSize + "\n" + fileName + "\n" + header;

            this.output = new DataOutputStream(new BufferedOutputStream(this.clientSocket.getOutputStream()));
            this.output.writeUTF(header);
            this.output.flush();
        } catch (IOException e) {
            throw new ClientRunTimeException("Error while receiving connection header");
        }
    }

    private String getDirectoryHeader(File file, int deepness){
        String header = "D:" + deepness + ":0:" + file.getName() + "\n";
        for (File internFile : file.listFiles()){
            if(internFile.isDirectory()){
                header += getDirectoryHeader(internFile, deepness + 1);
            }
            else if (internFile.isFile()){
                header += getFileHeader(internFile, deepness + 1); 
            }
        }
        return header;
    }

    private String getFileHeader(File file, int deepness){
        this.filePaths.add(new Pair<String, Long>(file.getAbsolutePath(), file.length()));
        this.totalFileSize += file.length();
        return "F:" + deepness + ":" + file.length() + ":" + file.getName() + "\n";
    }


    //---- FileManagement ----
    private void sendFiles(File file) throws ClientRunTimeException{
        for (Pair<String, Long> fileInfo : this.filePaths) {
            sendFile(new File(fileInfo.getFirst()), fileInfo.getSecond());
        }
    }

    private void sendFile(File file, Long fileSize) throws ClientRunTimeException{
        try{
            this.input = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
            this.output = new DataOutputStream(new BufferedOutputStream(this.clientSocket.getOutputStream()));

            //File lenght could be much bigger than the Integer max value
            int integerFileSizeValue = Integer.MAX_VALUE;
            boolean integerMaxValueExceeded = true;
            if(fileSize < Integer.MAX_VALUE){
                integerFileSizeValue = Math.toIntExact(fileSize);
                integerMaxValueExceeded = false;
            }

            int bytesReaded;
            while(integerFileSizeValue > 0 && (bytesReaded = this.input.read(this.buffer, 0, Math.min(this.BUFFERSIZE, integerFileSizeValue))) >= 0){
                this.output.write(this.buffer, 0, bytesReaded);
                this.output.flush();

                fileSize -= bytesReaded;
                this.totalBytesReaded += bytesReaded;
                if(integerMaxValueExceeded && fileSize < Integer.MAX_VALUE){
                    integerFileSizeValue = Math.toIntExact(fileSize);
                    integerMaxValueExceeded = false;
                }

                this.notifyUpdateToTransferenceObservers(this.getProgress()); 
            }
        }
        catch(FileNotFoundException e){
            throw new ClientRunTimeException("File " + file.getName() + "not found");
        }
        catch(IOException e){
            throw new ClientRunTimeException("Error while sending the file " + file.getName() + " to " + this.dst_addr);
        }
    }

    private int getProgress(){
        return (int)((this.totalBytesReaded * 100) / this.totalFileSize);
    }

    //Transference Observer methods
    @Override
    public void addTransferenceObserver(TransferencesObserver observer) {
        this.transferenceObserversList.add(observer);
    }

    private void notifyAddToTransferenceObservers(String fileName){
        for(TransferencesObserver observer : this.transferenceObserversList){
            observer.addTransference(SEND_MODE, this.src_addr, this.dst_addr, fileName);
        }
    }

    private void notifyUpdateToTransferenceObservers(int progress){
        for(TransferencesObserver observer : this.transferenceObserversList){
            observer.updateTransference(SEND_MODE, this.dst_addr, progress);
        }
    }

    private void notifyRemoveToTransferenceObservers(){
        for(TransferencesObserver observer : this.transferenceObserversList){
            observer.endTransference(SEND_MODE, this.dst_addr);
        }
    }

    private void notifyException(String message){
        for(TransferencesObserver observer : this.transferenceObserversList){
            observer.notifyException(message);
        }
    }
}

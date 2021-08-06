package Model;

import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.filechooser.FileSystemView;

import org.json.JSONObject;

import Misc.Pair;
import Model.Exceptions.ServerRunTimeException;
import Model.Observers.Observable;
import Model.Observers.ServerObserver;
import Model.Observers.TransferenceObservable;
import Model.Observers.TransferencesObserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Server implements Runnable, Observable<ServerObserver>, TransferenceObservable<TransferencesObserver>, UseState{
    
    private final int PORT = 2020;
    private final int BUFFERSIZE = 65536;
    
    private final String SEPARATOR = "\\";
    private final String RECEIVE_MODE = "receive";
    
    //Status info
    private final String RUNNING = "Running";
    private final String STOPPED = "Stopped";

    
    private List<ServerObserver> serverObserversList;
    private List<TransferencesObserver> transferenceObserversList;
    
    private ServerSocket serverSocket;
    private byte[] buffer;
    
    private Stack<Pair<String, Integer>> directoryStack;
    
    private DataOutputStream output;
    private DataInputStream input;
    
    private String defaultRoute;

    private String currentAddress;

    private boolean avalaible;
    private boolean endServerActivity;

    private long totalFileSize; 
    private int numberOfClients;


    public Server(){
        try{
            this.totalFileSize = 0;
            this.numberOfClients = 0;
            this.avalaible = true;
            this.endServerActivity = false;
            this.currentAddress = Inet4Address.getLocalHost().getHostAddress();
            this.defaultRoute = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();

            this.serverSocket = new ServerSocket(this.PORT);
            this.buffer = new byte[this.BUFFERSIZE];
            this.serverObserversList = new ArrayList<ServerObserver>();
            this.transferenceObserversList = new ArrayList<TransferencesObserver>();
            this.directoryStack = new Stack<Pair<String, Integer>>();
        }
        catch(IOException e){
            throw new ServerRunTimeException("Error during server socket opening");
        }
    }

    //Server control methods
    public void openServer(){
        new Thread(this).start();
        this.notifyObservers(RUNNING, PORT);
    }

    public void closeServer() throws ServerRunTimeException{ 
        if(this.avalaible){
            try {
                this.serverSocket.close();;
                this.endServerActivity = true;
                this.notifyObservers(STOPPED, PORT);
            } catch (IOException e) {
                throw new ServerRunTimeException("Error during stop operation");
            }
        }
        else{
            throw new ServerRunTimeException("The server cannot be shut down as it is performing operations, try when it is done");
        }
    }

    public void resetServer() throws ServerRunTimeException{
        if(this.avalaible){
            this.closeServer();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new ServerRunTimeException("Error during reset wait operation");
            }
            this.openServer();
        }
        else{
            throw new ServerRunTimeException("The server cannot be shut down as it is performing operations, try when it is done");
        }
    }

    public void openProcedure(){
        this.openServer();
    }

    public void exitProcedure(){
        this.closeServer();
    }

    public void changeDefaultDownloadRoute(String route){
        this.defaultRoute = route;
        for(ServerObserver observer : this.serverObserversList){
            observer.getDefaultDownloadRoute(route);
        }
    }
    
    //Transference methods
    @Override
    public void run() throws ServerRunTimeException{
        while(!this.endServerActivity){
            try{
                Socket clientSocket = this.serverSocket.accept();
                this.numberOfClients++;
                new Thread() {
                    public void run() {
                        processClientTransference(clientSocket);
                    }
                }.start();
            }
            catch(IOException e){
                this.numberOfClients--;
                throw new ServerRunTimeException("Error waiting for connections");
            }
        }
    }

    private void processClientTransference(Socket clientSocket) throws ServerRunTimeException{
        this.directoryStack.push(new Pair<String, Integer>(this.defaultRoute, 0));
        this.processHeader(clientSocket, this.receiveHeader(clientSocket));
        this.numberOfClients--;
    }

    private String receiveHeader(Socket clientSocket) throws ServerRunTimeException{
        String header = "";
        try {
            this.input = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            header = this.input.readUTF();
        } catch (IOException e) {
            throw new ServerRunTimeException("Error while receiving header");
        }
        
        return header;
    }

    private void processHeader(Socket clientSocket, String header) throws ServerRunTimeException{
        Scanner headerInfo = new Scanner(header);
        String generalFileName = "";
        String src_addr = clientSocket.getInetAddress().toString().substring(1);

        this.totalFileSize = Long.parseLong(headerInfo.nextLine());
        generalFileName = headerInfo.nextLine();

        this.notifyAddToTransferenceObservers(generalFileName, src_addr);
        while(headerInfo.hasNextLine()){
            
            String fileInfo = headerInfo.nextLine();
            String fileName = fileInfo.substring(fileInfo.lastIndexOf(":") + 1, fileInfo.length());
            String filePath = this.directoryStack.peek().getFirst() + SEPARATOR + fileName;
            int deepness = Character.getNumericValue(fileInfo.charAt(2));
            Long fileSize = Long.parseLong(fileInfo.substring(4, fileInfo.lastIndexOf(":")));

            if(fileInfo.charAt(0) == 'D'){ //Directory
                new File(filePath).mkdir();
                this.directoryStack.push(new Pair<String, Integer>(filePath,deepness));
            }
            else if(fileInfo.charAt(0) == 'F'){ //File
                //If the file deepness is <= actual directory deepness it means that the file is from another directory with less deepness
                while(!this.directoryStack.isEmpty() && this.directoryStack.peek().getSecond() >= deepness){
                    this.directoryStack.pop();
                    filePath = this.directoryStack.peek().getFirst() + SEPARATOR + fileName;
                }
                this.receiveFile(clientSocket, filePath, fileSize);
            }
        }

        this.clearDirectoryStack();
        headerInfo.close();
    
        this.notifyRemoveToTransferenceObservers(src_addr);

        try {
            this.output.close();
            this.input.close();
            clientSocket.close();
        } catch (IOException e) {
           throw new ServerRunTimeException("Error while closing client socket");
        }  
    }

    private void receiveFile(Socket clientSocket, String filePath, Long fileSize) throws ServerRunTimeException{
        try{
            this.output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(filePath))));

            //File lenght could be much bigger than the Integer max value
            int integerFileSizeValue = Integer.MAX_VALUE;
            boolean integerMaxValueExceeded = true;
            if(fileSize < Integer.MAX_VALUE){
                integerFileSizeValue = Math.toIntExact(fileSize);
                integerMaxValueExceeded = false;
            }

            int bytesReaded;
            long totalBytesReaded = 0;
            while(integerFileSizeValue > 0 && (bytesReaded = this.input.read(this.buffer, 0, Math.min(this.BUFFERSIZE, integerFileSizeValue))) >= 0){
                this.output.write(this.buffer, 0, bytesReaded);
                this.output.flush();
                fileSize -= bytesReaded;
                totalBytesReaded += bytesReaded;
                if(integerMaxValueExceeded && fileSize < Integer.MAX_VALUE){
                    integerFileSizeValue = Math.toIntExact(fileSize);
                    integerMaxValueExceeded = false;
                }
                this.notifyUpdateToTransferenceObservers(this.getProgress(totalBytesReaded), clientSocket.getInetAddress().toString().substring(1)); 
            }
        }
        catch(FileNotFoundException e){
            throw new ServerRunTimeException("Error, file not found");
        }
        catch(IOException e){
            throw new ServerRunTimeException("Error during file processing");
        }
    }

    private int getProgress(long totalBytesReaded){
        return (int)((totalBytesReaded * 100) / this.totalFileSize);
    }


    private void clearDirectoryStack(){
        this.directoryStack.clear();
        this.directoryStack.push(new Pair<String, Integer>(this.defaultRoute, 0));
    }

    //Server Observer methods
    @Override
    public void addObserver(ServerObserver observer) {
        this.serverObserversList.add(observer);
        observer.getDefaultDownloadRoute(this.defaultRoute);
        observer.getCurrentAddress(this.currentAddress);
    }

    private void notifyObservers(String status, int port){
        for(ServerObserver observer : this.serverObserversList){
            observer.updateStatus(status, port);
        }
    }

    //Transference Observer methods
    @Override
    public void addTransferenceObserver(TransferencesObserver observer) {
        this.transferenceObserversList.add(observer);
    }

    private void notifyAddToTransferenceObservers(String fileName, String src_addr){
        for(TransferencesObserver observer : this.transferenceObserversList){
            observer.addTransference(RECEIVE_MODE, src_addr, this.currentAddress, fileName);
            this.enableServerControlls(false);
        }
    }

    private void notifyUpdateToTransferenceObservers(int progress, String src_addr){
        for(TransferencesObserver observer : this.transferenceObserversList){
            observer.updateTransference(RECEIVE_MODE, src_addr, progress);
        }
    }

    private void notifyRemoveToTransferenceObservers(String src_addr){
        for(TransferencesObserver observer : this.transferenceObserversList){
            observer.endTransference(RECEIVE_MODE, src_addr);
            if(this.numberOfClients == 1){
                this.enableServerControlls(true);
            }
        }
    }

    private void enableServerControlls(boolean enable){
        for(ServerObserver observer : this.serverObserversList){
            observer.enableServerControlls(enable);
        }
    }

    @Override
    public void setState(TransferObject transferObject) {
        JSONObject state = transferObject.getState();
        this.defaultRoute = state.getString("downloadRoute");
    }

    @Override
    public TransferObject getState() {
        TransferObject transferObject = new TransferObject();
        JSONObject state = new JSONObject();
        
        state.put("downloadRoute", this.defaultRoute);
        transferObject.setState(state);
        
        return transferObject;
    }
}

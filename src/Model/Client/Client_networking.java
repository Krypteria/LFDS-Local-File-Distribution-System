package Model.Client;

import java.net.Socket;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import Misc.Pair;
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

    private final int PORT = 2222;
    private final int BUFFERSIZE = 65536;

    private final String SEND_MODE = "send";

    private List<TransferencesObserver> transferenceObserversList;
    private Socket clientSocket;
    private byte[] buffer;

    private DataOutputStream output;
    private DataInputStream input;
    private List<Pair<String, Long>> filePaths;

    private String src_addr;
    private String dst_addr;
    private long totalFileSize;


    public Client_networking(String dst_addr){
        try{
            this.src_addr = "198.100.200.204"; //VER SI FUNCIONA BIEN
            System.out.println(src_addr); 
            this.dst_addr = dst_addr;
            this.totalFileSize = 0;

            this.clientSocket = new Socket();
            this.clientSocket.connect(new InetSocketAddress(dst_addr, this.PORT));
            this.transferenceObserversList = new ArrayList<TransferencesObserver>();
            this.buffer = new byte[this.BUFFERSIZE];
            this.filePaths = new ArrayList<Pair<String, Long>>();
        }
        catch(UnknownHostException e){
            System.out.println("La IP destino no es válida");
        }
        catch(IOException e){
            System.out.println("Error al enviar el archivo");
        }
    }

    public void send(File file){
        this.notifyAddToTransferenceObservers(file);
        if(file.isDirectory()){
            this.sendHeader(this.getDirectoryHeader(file, 1));
            sendFiles(file);
        }
        else{
            this.sendHeader(this.getFileHeader(file, 1));
            sendFile(file, file.length());
        }
        this.notifyRemoveToTransferenceObservers();

        try{
            this.input.close();
            this.output.close(); 
            this.clientSocket.close();
        }catch(IOException e){
            System.out.println("Error al cerrar las conexiones");
        }
    }

    // ---- Header ----
    private void sendHeader(String header){
        System.out.println(header);
        try {
            header = header.substring(0, header.length() - 1); //elimino el ultimo salto de linea
            this.output = new DataOutputStream(new BufferedOutputStream(this.clientSocket.getOutputStream()));
            this.output.writeUTF(header);
            this.output.flush();

            System.out.println("Header enviado al servidor");
        } catch (IOException e) {
            System.out.println("EEE");
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
    private void sendFiles(File file){
        for (Pair<String, Long> fileInfo : this.filePaths) {
            sendFile(new File(fileInfo.getFirst()), fileInfo.getSecond());
        }
    }

    private void sendFile(File file, Long fileSize){
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
                this.notifyUpdateToTransferenceObservers(this.getProgress(totalBytesReaded)); 
            }
        }
        catch(FileNotFoundException e){
            System.out.println("El archivo seleccionado no existe");
        }
        catch(IOException e){
            System.out.println("Error al enviar el fichero");
        }
    }

    private int getProgress(long totalBytesReaded){
        return (int)((totalBytesReaded * 100) / this.totalFileSize);
    }

    //Transference Observer methods
    @Override
    public void addTransferenceObserver(TransferencesObserver observer) {
        this.transferenceObserversList.add(observer);
    }

    private void notifyAddToTransferenceObservers(File file){
        for(TransferencesObserver observer : this.transferenceObserversList){
            observer.addTransference(SEND_MODE, this.src_addr, this.dst_addr, file.getName());
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
}

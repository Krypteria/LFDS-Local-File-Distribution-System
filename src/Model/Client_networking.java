package Model;

import java.net.Socket;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import Misc.Pair;
import Model.Observers.Observable;
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
    private final int BUFFERSIZE = 8192;

    private List<TransferencesObserver> transferenceObserversList;
    private Socket clientSocket;
    private byte[] buffer;

    private DataOutputStream output;
    private DataInputStream input;
    private List<Pair<String, Integer>> filePaths;


    public Client_networking(String addr_dst){
        try{
            this.clientSocket = new Socket();
            this.clientSocket.connect(new InetSocketAddress(addr_dst, this.PORT));
            this.transferenceObserversList = new ArrayList<TransferencesObserver>();
            this.buffer = new byte[this.BUFFERSIZE];
            this.filePaths = new ArrayList<Pair<String, Integer>>();
        }
        catch(UnknownHostException e){
            System.out.println("La IP destino no es válida");
        }
        catch(IOException e){
            System.out.println("Error al enviar el archivo");
        }
    }

    public void send(File file){
        if(file.isDirectory()){
            this.sendHeader(this.getDirectoryHeader(file, 1));
            sendFiles(file);
        }
        else{
            this.sendHeader(this.getFileHeader(file, 1));
            sendFile(file, Integer.parseInt("" + file.length()));
        }

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
        this.filePaths.add(new Pair<String, Integer>(file.getAbsolutePath(), Integer.parseInt("" + file.length())));
        return "F:" + deepness + ":" + file.length() + ":" + file.getName() + "\n";
    }


    //---- FileManagement ----
    private void sendFiles(File file){
        for (Pair<String, Integer> fileInfo : this.filePaths) {
            sendFile(new File(fileInfo.getFirst()), fileInfo.getSecond());
        }
    }

    private void sendFile(File file, int fileSize){
        try{
            this.input = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
            this.output = new DataOutputStream(new BufferedOutputStream(this.clientSocket.getOutputStream()));

            int bytesReaded;          
            while(fileSize > 0 && (bytesReaded = this.input.read(this.buffer, 0, Math.min(this.BUFFERSIZE, fileSize))) >= 0){
                this.output.write(this.buffer, 0, bytesReaded);
                this.output.flush();
                fileSize -= bytesReaded;
                System.out.println("Enviando " + bytesReaded + " bytes");
            }
            System.out.println("Archivo enviado");
        }
        catch(FileNotFoundException e){
            System.out.println("El archivo seleccionado no existe");
        }
        catch(IOException e){
            System.out.println("Error al enviar el fichero");
        }
    }

   //Transference Observer methods
   @Override
   public void addTransferenceObserver(TransferencesObserver observer) {
       this.transferenceObserversList.add(observer);
   }

   @Override
   public void removeTransferenceObserver(TransferencesObserver observer) {
       this.transferenceObserversList.remove(observer);
   }
}

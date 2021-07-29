package Model;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import Misc.Pair;
import Model.Exceptions.ServerRunTimeException;
import Model.Observers.Observable;
import Model.Observers.ServerObserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Server implements Runnable, Observable<ServerObserver>{
    private final int PORT = 2222;
    private final int BUFFERSIZE = 8192;
    private final String SEPARATOR = "\\";
    
    //Status info
    private final String RUNNING = "Running";
    private final String STOPPED = "Stopped";
    
    //Task info
    private final String WAITING = "Waiting for transferences";
    private final String ON_TRANSFER = "Receiving a transference from"; //+<IPsrc>
    private final String DISABLED = "Disabled, transferences are not allowed";

    private final String defaultRoute = "D:\\Biblioteca\\Escritorio\\PruebaRecibo";

    private List<ServerObserver> serverObserversList;
    private ServerSocket serverSocket;
    private byte[] buffer;

    private Stack<Pair<String, Integer>> directoryStack;

    private DataOutputStream output;
    private DataInputStream input;

    private boolean avalaible;
    private boolean endServerActivity;

    public Server(){
        System.out.println("Servidor iniciandose");
        try{
            this.serverSocket = new ServerSocket(this.PORT);
            this.buffer = new byte[this.BUFFERSIZE];
            this.serverObserversList = new ArrayList<ServerObserver>();
            this.directoryStack = new Stack<Pair<String, Integer>>();
            this.directoryStack.push(new Pair<String, Integer>(this.defaultRoute, 0));

            this.avalaible = true;
            this.endServerActivity = false;
        }
        catch(IOException e){
            System.out.println("Error al crear el socket del servidor");
        }
    }

    public void openServer() throws ServerRunTimeException{
        try{
            new Thread(this).start();
            this.notifyObservers(RUNNING, PORT, WAITING);
        }catch(IllegalThreadStateException e){
            throw new ServerRunTimeException("Server is already open");
        }
    }

    //Implementar un modo de cancelar las transferencias, el servidor enviará a la vista información, si ON_TRANSFER -> lanzo mensaje si no cierro normal
    
    public void closeServer(){
        //A la hora de finalizarlo lo que haré será esperar a que una transferencia se termine -> dar la opción a cortarla
        if(!this.isAvalaible()){
            //Lanzaría un jdialog con la pregunta
            System.out.println("El servidor está activo actualmente, se cerrará cuando se termine la transferencia");

            //Podemos intentar cerrar el socket y que el cliente reciba una excepción controlada indicando el suceso
        }

        this.stop();
        this.notifyObservers(STOPPED, PORT, DISABLED);
    }

    public void resetServer(){
        this.closeServer();
        this.openServer();
    }

    private boolean isAvalaible(){
        return this.avalaible;
    }

    private void stop(){
        this.endServerActivity = true;
    }

    private void clearDirectoryStack(){
        this.directoryStack.clear();
        this.directoryStack.push(new Pair<String, Integer>(this.defaultRoute, 0));
    }

    @Override
    public void run(){
        System.out.println("Ejecutando método run");
        while(!this.endServerActivity){
            try{
                System.out.println("Esperando");
                Socket clientSocket = this.serverSocket.accept();
                System.out.println("Fin espera");
                this.processClientTransference(clientSocket);
            }
            catch(IOException e){
                System.out.println("Error al establecer la conexión entre cliente y servidor");
            }
        }
    }

    private void processClientTransference(Socket clientSocket){
        this.avalaible = false;
        this.processHeader(clientSocket, this.receiveHeader(clientSocket));
        this.avalaible = true;
    }

    private String receiveHeader(Socket clientSocket){
        String header = "";
        try {
            this.input = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            header = this.input.readUTF();

            System.out.println("[*] HEADER: ");
            System.out.println(header);
        } catch (IOException e) {
            System.out.println("Error al recibir el header");
        }
        
        return header;
    }

    private void processHeader(Socket clientSocket, String header){
        Scanner headerInfo = new Scanner(header);
        while(headerInfo.hasNextLine()){
            //Obtengo la información referente al siguiente fichero
            String fileInfo = headerInfo.nextLine();
            String fileName = fileInfo.substring(fileInfo.lastIndexOf(":") + 1, fileInfo.length());
            String filePath = this.directoryStack.peek().getFirst() + SEPARATOR + fileName;
            int deepness = Character.getNumericValue(fileInfo.charAt(2));
            int fileSize = Integer.parseInt(fileInfo.substring(4, fileInfo.lastIndexOf(":")));

            if(fileInfo.charAt(0) == 'D'){
                new File(filePath).mkdir();
                this.directoryStack.push(new Pair<String, Integer>(filePath,deepness));
            }
            else if(fileInfo.charAt(0) == 'F'){
                //Para que un fichero pertenezca a un directorio, tiene que tener más profundidad que este
                while(!this.directoryStack.isEmpty() && this.directoryStack.peek().getSecond() >= deepness){
                    this.directoryStack.pop();
                    filePath = this.directoryStack.peek().getFirst() + SEPARATOR + fileName;
                }
                this.receiveFile(clientSocket, filePath, fileSize);
            }
        }

        //Cerramos conexiones
        try {
            this.output.close();
            this.input.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Error al cerrar conexiones en el servidor");
        }
     
        headerInfo.close();
        this.clearDirectoryStack();
    }

    private void receiveFile(Socket clientSocket, String filePath, int fileSize){
        try{
            System.out.println(filePath);
            this.output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(filePath))));

            int bytesReaded;
            while(fileSize > 0 && (bytesReaded = this.input.read(this.buffer, 0, Math.min(this.BUFFERSIZE, fileSize))) >= 0){
                this.output.write(this.buffer, 0, bytesReaded);
                this.output.flush();
                fileSize -= bytesReaded;
                System.out.println("Recibiendo " + bytesReaded + " bytes");
            }
    
            System.out.println("Archivo recibido correctamente");
        }
        catch(IOException e){
            System.out.println("Error al ejecutar la transferencia por parte del servidor");
        }
        catch(Exception e){
            System.out.println("Algo raro ha pasado");
        }

        System.out.println("Salgo del método del servidor");
    }

    @Override
    public void addObserver(ServerObserver observer) {
        this.serverObserversList.add(observer);
    }

    @Override
    public void removeObserver(ServerObserver observer) {
        this.serverObserversList.remove(observer);
    }

    private void notifyObservers(String status, int port, String task){
        for(ServerObserver observer : this.serverObserversList){
            observer.updateStatus(status, port);
            observer.updateTaskInfo(task);
        }
    }
}

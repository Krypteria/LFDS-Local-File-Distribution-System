package Model;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Stack;

import Misc.Pair;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Server implements Runnable{
    private final int PORT = 2222;
    private final int BUFFERSIZE = 8192;
    private final String SEPARATOR = "\\";

    private final String defaultRoute = "D:\\Biblioteca\\Escritorio\\PruebaRecibo";

    private ServerSocket serverSocket;
    private byte[] buffer;

    private Stack<Pair<String, Integer>> directoryStack;

    private DataOutputStream output;
    private DataInputStream input;

    public Server(){
        System.out.println("Servidor iniciandose");
        try{
            this.serverSocket = new ServerSocket(this.PORT);
            this.buffer = new byte[this.BUFFERSIZE];
            this.directoryStack = new Stack<Pair<String, Integer>>();
            this.directoryStack.push(new Pair<String, Integer>(this.defaultRoute, 0));
        }
        catch(IOException e){
            System.out.println("Error al crear el socket del servidor");
        }
    }

    /*public void openServer(){
        //this.serverSocket = new ServerSocket(this.port);
        //this.run();
    }

    public void closeServer(){
        //this.serverSocket.close();
    }

    public void resetServer(){
        this.closeServer();
        this.openServer();
    }*/

    private void clearDirectoryStack(){
        this.directoryStack.clear();
        this.directoryStack.push(new Pair<String, Integer>(this.defaultRoute, 0));
    }

    @Override
    public void run(){
        System.out.println("Ejecutando método run");
        while(true){
            try{
                System.out.println("Esperando");
                Socket clientSocket = this.serverSocket.accept();
                System.out.println("Fin espera");
                this.processHeader(clientSocket, this.receiveHeader(clientSocket));
            }
            catch(IOException e){
                System.out.println("Error al establecer la conexión entre cliente y servidor");
            }
        }
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
                System.out.println(new File(filePath).mkdir());
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
}

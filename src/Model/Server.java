package Model;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Server implements Runnable{
    private final int PORT = 2222;
    private final int BUFFERSIZE = 8192;

    private ServerSocket serverSocket;
    private byte[] buffer;

    public Server(){
        System.out.println("Servidor iniciandose");
        try{
            this.serverSocket = new ServerSocket(this.PORT);
            this.buffer = new byte[this.BUFFERSIZE];
        }
        catch(IOException e){
            System.out.println("Error al crear el socket del servidor");
        }
    }

    public void openServer(){
        //this.serverSocket = new ServerSocket(this.port);
        //this.run();
    }

    public void closeServer(){
        //this.serverSocket.close();
    }

    public void resetServer(){
        this.closeServer();
        this.openServer();
    }

    @Override
    public void run(){
        System.out.println("Ejecutando método run");
        while(true){
            try{
                System.out.println("Esperando");
                Socket clientSocket = this.serverSocket.accept();
                System.out.println("Fin espera");
                this.receiveFiles(clientSocket, this.receiveHeader(clientSocket));
                //this.receiveFile(clientSocket);
            }
            catch(IOException e){
                System.out.println("Error al establecer la conexión entre cliente y servidor");
            }
        }
    }

    private String receiveHeader(Socket clientSocket){
        String header = "";
        try {
            DataInputStream input = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            input.read(this.buffer, 0, this.BUFFERSIZE);
            header = new String(this.buffer);

            System.out.println("[*] HEADER: ");
            System.out.println(header);
            System.out.println("-----");
        } catch (IOException e) {
            System.out.println("Error al recibir el header");
        }
        
        return header;
    }

    private void receiveFiles(Socket clientSocket, String header){
        //Proceso la primera linea del header
        //Si es envio simple -> receiveFile directamente
        //Si es un envio complejo -> 
        receiveFile(clientSocket);
    }

    private void receiveFile(Socket clientSocket){
        try{
            String rutaDestino = "D:\\Biblioteca\\Escritorio\\Prueba\\quijote.pdf";
            DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(rutaDestino))));
            DataInputStream input = new DataInputStream(new BufferedInputStream (clientSocket.getInputStream()));

            int bytesReaded;            
            while((bytesReaded = input.read(this.buffer)) >= 0){
                output.write(this.buffer, 0, bytesReaded);
                System.out.println("Recibiendo " + bytesReaded + " bytes");
            }
    
            output.close(); //cierro el stream para que los cambios se apliquen
            input.close();
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

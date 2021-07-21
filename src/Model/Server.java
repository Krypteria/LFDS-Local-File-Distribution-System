package Model;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Server implements Runnable{
    private int port = 2222;
    private ServerSocket serverSocket;
    private char[] buffer;
    private int bufferSize = 8192;

    public Server(){
        System.out.println("Servidor iniciandose");
        try{
            this.serverSocket = new ServerSocket(this.port);
            System.out.println("Dirección IP del servidor: " + this.serverSocket.getInetAddress().toString());
            this.buffer = new char[this.bufferSize];
            // this.run();
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
                this.receive_file_from(clientSocket);
            }
            catch(IOException e){
                System.out.println("Error al establecer la conexión entre cliente y servidor");
            }
        }
    }

    private void receive_file_from(Socket clientSocket){
        try{
            String rutaDestino = "D:\\Biblioteca\\Escritorio\\Prueba\\quijote.pdf";
            FileWriter output = new FileWriter(new File(rutaDestino));
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


            int bytesReaded;            
            while((bytesReaded = input.read(this.buffer)) >= 0){
                output.write(this.buffer, 0, bytesReaded);
                System.out.println("Recibiendo " + bytesReaded + " bytes");
            }
    
            output.close(); //cierro el stream para que los cambios se apliquen
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

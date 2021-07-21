package Model;

import java.net.Socket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Client_networking{

    private int port = 2222;
    private Socket clientSocket;
    private SocketAddress endpoint;
    private PrintWriter output;
    private BufferedReader input;
    private char[] buffer;
    private int bufferSize = 8192;

    public Client_networking(String addr_dst){
        try{
            this.clientSocket = new Socket();
            this.endpoint = new InetSocketAddress(addr_dst, this.port);
            this.buffer = new char[this.bufferSize];
            this.connect();
        }
        catch(UnknownHostException e){
            System.out.println("La IP destino no es válida");
        }
        catch(IOException e){
            System.out.println("Error al enviar el archivo");
        }
    }

    private void connect() throws UnknownHostException, IOException{
        System.out.println("Estableciendo la conexión");
        this.clientSocket.connect(this.endpoint);
        this.output = new PrintWriter(this.clientSocket.getOutputStream(), true);
        this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
    }

    private void disconnect(){
        //this.clientSocket.close();
    }

    public void send(File file){
        try{
            FileReader fileReader = new FileReader(file);
            int bytesReaded;
           
            while((bytesReaded = fileReader.read(this.buffer)) >= 0){
                this.output.write(this.buffer, 0, bytesReaded);
                System.out.println("Enviando " + bytesReaded + " bytes");
                //System.out.println(this.buffer);
            }

            System.out.println("Archivo enviado");
            fileReader.close();
            this.output.close(); //importante cerrar para que se guarden los cambios
            //this.disconnect();
            //this.clientSocket.close();
        }
        catch(FileNotFoundException e){
            System.out.println("El archivo seleccionado no existe");
        }
        catch(IOException e){
            System.out.println("Error al intentar leer el archivo");
        }
    }
}


/*

Varias situaciones:

Envio de 1 solo archivo
    TIPO1
    Forma habitual

Envio de varios archivos > 1
    Envio de un directorio (o varios)

    TIPO2

    Dir1 - tipo 
        file1 - tipo - dirPadre - tamaño
        file2 - tipo - dirPadre - tamaño
        Dir2 - tipo 
            file3 - tipo - tamaño - DirPadre

    Pila de directorios ->















*/
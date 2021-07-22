package Model;

import java.net.Socket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Client_networking{

    private int port = 2222;
    private Socket clientSocket;
    private SocketAddress endpoint;
    private OutputStream output;
    private BufferedInputStream input;
    private byte[] buffer;
    private int bufferSize = 8192;

    public Client_networking(String addr_dst){
        try{
            this.clientSocket = new Socket();
            this.endpoint = new InetSocketAddress(addr_dst, this.port);
            this.buffer = new byte[this.bufferSize];
            this.clientSocket.connect(this.endpoint);
        }
        catch(UnknownHostException e){
            System.out.println("La IP destino no es válida");
        }
        catch(IOException e){
            System.out.println("Error al enviar el archivo");
        }
    }

    public void send(File file){
        try{
            this.input = new BufferedInputStream(new FileInputStream(file));
            this.output = this.clientSocket.getOutputStream();

            //FileReader fileReader = new FileReader(file);
            int bytesReaded;
           
            while((bytesReaded = this.input.read(this.buffer)) >= 0){
                this.output.write(this.buffer, 0, bytesReaded);
                System.out.println("Enviando " + bytesReaded + " bytes");
                //System.out.println(this.buffer);
            }

            System.out.println("Archivo enviado");
            this.input.close();
            this.output.close(); //importante cerrar para que se guarden los cambios
            this.clientSocket.close();
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
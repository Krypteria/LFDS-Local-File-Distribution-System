package Model;

import java.net.Socket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Client_networking{

    private final int PORT = 2222;
    private final int BUFFERSIZE = 8192;

    private Socket clientSocket;
    private SocketAddress endpoint;
    private byte[] Filebuffer;

    private DataOutputStream output;
    private DataInputStream input;

    public Client_networking(String addr_dst){
        try{
            this.clientSocket = new Socket();
            this.endpoint = new InetSocketAddress(addr_dst, this.PORT);
            this.Filebuffer = new byte[this.BUFFERSIZE];

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
        if(file.isDirectory()){
            this.sendHeader(this.getDirectoryHeader(file, 0));
            sendDirectory(file);
        }
        else{
            this.sendHeader(this.getFileHeader(file, 0));
            sendFile(file);
        }
    }

    // ---- Header ----
    private void sendHeader(String header){
        try {
            //PrintWriter output = new PrintWriter(this.clientSocket.getOutputStream(), true);
            //output.write(header, 0, header.length());;
            this.output = new DataOutputStream(new BufferedOutputStream(this.clientSocket.getOutputStream()));
            System.out.println("HEADER LENGHT: " + header.length());
            this.output.write(header.getBytes(), 0, header.length());
            this.output.flush();

            System.out.println("Header enviado al servidor");
        } catch (IOException e) {
            System.out.println("EEE");
        }
    }

    private String getDirectoryHeader(File file, int deepness){
        String header = "D:" + deepness + ":" + file.getName() + "\n";
        for (File internFile : file.listFiles()){
            if(internFile.isDirectory()){
                header += getDirectoryHeader(internFile, deepness + 1);
            }
            else if (internFile.isFile()){
                header += getFileHeader(internFile, deepness); 
            }
        }
        return header;
    }

    private String getFileHeader(File file, int deepness){ //EL SALTO DE LINEA ESTE ES EL CAUSANDO DEL ESPACIO EN EL HEADER
        return "F:" + deepness + ":" + file.getName() + ":" + file.length() + "\n";
    }


    //---- FileManagement ----
    private void sendDirectory(File file){

    }

    private void sendFile(File file){
        try{
            //BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
            //OutputStream output = this.clientSocket.getOutputStream();
            this.input = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
            this.output = new DataOutputStream(new BufferedOutputStream(this.clientSocket.getOutputStream()));

            int bytesReaded;          
            while((bytesReaded = input.read(this.Filebuffer)) >= 0){
                output.write(this.Filebuffer, 0, bytesReaded);
                System.out.println("Enviando " + bytesReaded + " bytes");
            }

            System.out.println("Archivo enviado");
            input.close();
            output.close(); //importante cerrar para que se guarden los cambios
            this.clientSocket.close();
        }
        catch(FileNotFoundException e){
            System.out.println("El archivo seleccionado no existe");
        }
        catch(IOException e){
            System.out.println("Error al intentar obtener el outputstream");
        }
    }












    /*public void send_original(File file){
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
    }*/
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
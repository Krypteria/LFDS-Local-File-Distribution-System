package Model;

import java.io.File;

public class Client implements Runnable{

    Client_networking networking;
    File file;

    public Client(String addr_dst, File file){
        System.out.println("Ejecutando el cliente");
        this.networking = new Client_networking(addr_dst);
        if(file != null)
            this.file = file;
    }

    @Override
    public void run() {
        System.out.println("Ejecutando método run del cliente");
        System.out.println("Conexión establecida");
        String rutaSrc = "D:\\Biblioteca\\Escritorio\\Prueba";
        File file = new File(rutaSrc);
        this.networking.send(file);
    }
}
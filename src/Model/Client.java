package Model;

import java.io.File;

public class Client implements Runnable{

    Client_networking networking;
    File file;

    public Client(String dst_addr, File file){
        System.out.println("Ejecutando el cliente");
        this.networking = new Client_networking(dst_addr);
        this.file = file;
    }

    @Override
    public void run() {
        System.out.println("Ejecutando método run del cliente");
        System.out.println("Conexión establecida");
        this.networking.send(this.file);
    }
}
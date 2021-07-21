package Launcher;

import Model.Local_distribution_system;

//Borrar
import Model.Server;
import Model.Client;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Local_distribution_system app = new Local_distribution_system();
        //app.addNewHost("Alejandro", "192.168.1.3");
        //app.addNewHost("Juan", "192.168.1.1");
        //app.addNewHost("Cristina", "192.168.1.21");
        //app.seeHosts();

        
        new Thread(new Server()).start();
        new Thread(new Client("0.0.0.0", null)).start();;
	}
}

//PATH

/*

Networking
    Cliente - Server transference -> Add MBS,files to hosts -> Elección de ruta de guardado modificable -> Server open, close, reset ->
    Excepciones controladas en la transferencia 

Serialización de la información
    Transfer objects -> DAO

Logs
    Existencia y funcionamiento básico

Editar y eliminar Hosts
    Repercusión en los logs 
    Repercusión en la propia App

Logs 
    Visualización cómoda
    Seguridad -> Gestión de permisos de lectura -> cifrado

GUI

Background
    Ejecución de la aplicación en segundo plano

*/
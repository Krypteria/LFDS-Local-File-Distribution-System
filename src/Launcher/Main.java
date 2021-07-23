package Launcher;

import View.MainWindow;
import Controller.Controller;

import javax.swing.SwingUtilities;


public class Main {

	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainWindow(new Controller());
			}
		});
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
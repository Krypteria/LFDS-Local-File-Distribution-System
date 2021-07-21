package Script;

import java.util.Scanner;
import java.io.File;

public class script {

	public static void main(String[] args) {
		System.out.println("Introduce la ruta del archivo que quieras enviar: ");
        Scanner cin = new Scanner(System.in);
        
        String pathFichero = cin.nextLine();
        cin.close();

        File fichero = new File(pathFichero);

        String output = "";
        int nivel = 1;

        if(fichero.isDirectory()){
            output = "[*] Multienvio" + "\n";
            output += procesarDirectorio(fichero, nivel);
        }
        else{
            output = "fichero - nivel " + nivel + " - " + fichero.getName() + " - " + fichero.length() + "\n";
        }
        
        System.out.println(output);
    }
    public static String procesarDirectorio(File fichero, int nivel){
        String output = "directorio - nivel " + nivel + " - " + fichero.getName() + "\n";
        for (File ficherosAdjunto : fichero.listFiles()){
            if(ficherosAdjunto.isDirectory()){
                output += procesarDirectorio(ficherosAdjunto, nivel + 1);
            }
            else if (ficherosAdjunto.isFile()){
                output += "fichero - nivel " + nivel + " - " + ficherosAdjunto.getName() + " - " + ficherosAdjunto.length() + "\n";
            }
        }
        return output;
    }
}

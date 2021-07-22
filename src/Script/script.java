package Script;

import java.util.Scanner;
import java.util.Stack;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

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

    //D:\Biblioteca\Escritorio\Prueba

    public static String procesarDirectorio(File fichero, int nivel){
        String output = "D:" + nivel + ":" + fichero.getName() + "\n";
        for (File ficherosAdjunto : fichero.listFiles()){
            if(ficherosAdjunto.isDirectory()){
                output += procesarDirectorio(ficherosAdjunto, nivel + 1);
            }
            else if (ficherosAdjunto.isFile()){
                output += "F:" + nivel + ":" + ficherosAdjunto.getName() + ":" + ficherosAdjunto.length() + "\n";
            }
        }
        return output;
    }

    public static void test(String fileDecriptor){
        Stack<String> pilaDeRutas = new Stack<String>();
        String rutaBase = "D:\\Biblioteca\\Escritorio";

        /*BufferedReader reader = new BufferedReader(new StringReader(fileDecriptor));
        
        try{
            String line = reader.readLine();
            while(line != reader)
        }
        catch(IOException e){
            System.out.println("Ah");
        }*/
        
        String line="", path="";
        int currentLevel = 0;
        //Stream reader
        if(line.charAt(0) == 'D'){
            currentLevel++;
            new File(path).mkdir();
            pilaDeRutas.push(rutaBase + "\\" + line.substring(3, line.length()));

        }
        else if(line.charAt(0) == 'F'){

        }

    }
}

//Para gestionar la creación de directorios puedo usar una pila, java no permite desplazamientos usando dir.

/*
RUTA_DESCARGA -> RUTA_DESCARGAR/prueba -> RUTA_DESCARGA/dir1 -> RUTA_DESCARGAR/dir2 -> RUTA_DESCARGAR/dir3 -> POP
RUTA_DESCARGA -> RUTA_DESCARGAR/prueba -> RUTA_DESCARGA/dir1 -> RUTA_DESCARGAR/dir2 -> ficheros -> POP
RUTA_DESCARGA -> RUTA_DESCARGAR/prueba -> RUTA_DESCARGA/dir1 -> ficheros -> POP


*/
# Local file distribution system

Aplicación client-servidor fundamentada en programación socket que permite la distribución de cualquier tipo de fichero o directorio a cualquier ordenador de la red local.


## Como descargarlo y ejecutarlo
1. Descargar la última versión de ejecutable (*Local File Distribution System-x.x.exe*) en la pestaña de release.
2. Lanzar el ejecutable para instalar la aplicación.

La aplicación es instalada en la ruta *C:\Program Files\Local File Distribution System* y crea automáticamente un <b>acceso directo en el menú Windows y en el escritorio.</b>

## Funcionalidades
### Panel de hosts
Panel que permite la interación con la lista de contactos que maneja la aplicación de diferentes formas:

1.Podemos añadir un host a nuestra lista de contactos proporcionando un nombre y una dirección IP. <br>
2.Podemos editar la información relativa a un host existente (su direción nombre o direción IP). <br>
3.Podemos eliminar un host de la lista de contactos. <br>
4.Podemos asignar un host o un conjunto de hosts como destinatario de una transferencia. <br>

### Panel de ficheros
Desde este panel podemos:
1. Seleccionar el fichero o directorio que queramos enviar.
2. Visualizar la lista de destinatarios de la transferencia.
3. Iniciar la transferencia a través del botón del panel inferior.

### Panel de transferencias
Desde este panel podemos observar el estado de las transferencias.
Esta aplicación permite hasta un total de 50 transferencias en paralelo
Para cada transferencia veremos su tipo (si estamos enviando nosotros o recibiendo) su dirección origen, su dirección destino, el nombre del archivo que estemos recibiendo y el progreso

### Panel del servidor
El panel del servidor cuenta con los siguientes modulos:
primer modulo: muestra información relativa al estado del servidor (cerrado o abierto) y al puerto en uso
Segundo módulo: muestra las tareas que está ejecutando el servidor (recibiendo transferencias, en espera, rechazando transferencias)
Tercer módulo: contiene todos los controles relativos al servidor:
#### Controler del servidor

#### Controles extra

## Detalles de implementación


- Funcionalidades
- Host -> añadir, editar eliminar un host, seleccionar varios hosts para envios multiples ->
- File managment -> muestra las personas que van a recibir el archivo, permite seleccionar un archivo indicando que archivo está cargado actualmente, boton que inicia la transferencia
- transference panel -> Muestra de forma clara e intuitiva el estado de las transferences mostrando información relevante como la source, dst, nombre del archivo y porcentaje de transferencia
- Server panel -> muestra información relativa al estado del servidor (estado, puerto en uso, tareas actuales) también cuenta con una serie de opciones para su manejo (open,close,reset)
adicionalmente cuenta con boton ip, address

Aplicación
Aplicación cliente servidor fundamentada en programación socket, 

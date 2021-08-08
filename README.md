# Local file distribution system

Aplicación client-servidor fundamentada en programación socket que permite la distribución de cualquier tipo de fichero o directorio a cualquier ordenador de la red local.
[Imagen de la APP]

## Como descargarlo y ejecutarlo
1. Descargar la última versión de ejecutable (*Local File Distribution System-x.x.exe*) en la pestaña de release.
2. Lanzar el ejecutable para instalar la aplicación.

La aplicación es instalada en la ruta *C:\Program Files\Local File Distribution System* y crea automáticamente un <b>acceso directo en el menú Windows y en el escritorio.</b>

## Funcionalidades
Las funcionalidades de la aplicación están distribuidas en cuatro paneles especializados diferentes para favorecer un uso intuitivo y sencillo.

### Panel de hosts
La aplicación permite almacenar contactos, cada contacto está conformado por una dirección IP y un nombre, dichos contactos se muestran al usuario en este panel y permiten efectuar una serie de acciones.
[Imagen del panel entero]

#### Crear un nuevo contacto
[Imagen del botón]
A través de ese botón podemos crear un nuevo contacto introduciendo un nombre y una IP válida.
#### Modificar información de un contacto existente
[Imagen del botón]
Podemos modificar la información relativa a un contacto de manera facil y así evitar tener que eliminarlo e introducirlo de nuevo en el sistema.
#### Eliminar un contacto de la lista
[Imagen del botón]
Podemos eliminar un contacto de la lista de forma cómoda y rápida.
#### Asignar receptores
[Imagen del botón]
Utilizando el checkbox *send* podemos asignar que contactos queremos que sean los receptores de una futura transferencia.
Una vez que se selecciona un contacto y se asigna como receptor, se bloquean las opciones de editar y eliminar dicho comando hasta que hemos realizado la transferencia o hemos deseleccionado dicho contacto.

### Panel de ficheros
Desde este panel podemos elegir el fichero que queramos enviar, visualizar los receptores seleccionados y lanzar la transferencia.
[Imagen del panel entero]
#### Características especiales del manejo de ficheros
- La aplicación es capaz de enviar estructuras complejas de directorios.
- La aplicación es capaz de enviar cualquier tipo de archivo (audio, imagen, texto, ejecutables ...).
- La aplicación es capaz de enviar archivos de cualquier peso, no hay limitaciones al respecto.

### Panel de transferencias
Desde este panel podemos observar el estado en el que se encuentran las transferencias en proceso, cada transferencia cuenta con la siguiente información:
- Tipo de transferencia (entrante o saliente).
- Receptor o emisor de la transferencia (dependiendo del tipo de esta)
- Nombre del fichero enviado en la transferencia.
- Progreso de la transferencia en tiempo real.
[Imagen del panel entero]

#### Transferencias entrantes
Las transferencias entrastes, como su nombre indican, nos permiten visualizar el estado de una transferencia en la que nuestro equipo es el receptor
[Imagen transferencia entrante]
#### Transferencias salientes
Las transferencias salientes, como su nombre indican, nos permiten visualizar el estado de una transferencia en la que nuestro equipo es el emisor.
#### Características especiales del manejo de transferencias
- La aplicación permite el envio concurrente de transferencias.
- La aplicación no permite que se realizen dos envios al mismo host de forma concurrente debido al diseño de esta, para enviar otro archivo se tendrá que esperar a que termine la primera transferencia.
- La aplicación puede recibir hasta 50 transferencias entrantes en paralelo.

### Panel del servidor
En este panel podemos visualizar información relativa al estado y el funcionamiento del servidor así como interactuar con la aplicación a través de una serie de botones.
[Imagen del panel entero]
#### Estado del servidor
[Imagen del subpanel]
Desde este panel podemos visualizar el estado del servidor:
- Podemos visualizar si el servidor se encuentra operativo o no
- Podemos visualizar el puerto en el que está trabajando el servidor
- Podemos visualizar la tarea que está realizando (recibiendo transferencias, en espera, cerrado y rechazando transferencias)
#### Controles relativos al servidor
[Imagen del subpanel]
Desde este panel también podemos lanzar comandos relativos al funcionamiento del servidor:
- Podemos abrir el servidor (si este previamente está cerrado).
- Podemos reiniciar el servidor (si este previamente está abierto).
- Podemos cerrar el servidor (si este previamente está abierto).
- Podemos modificar la ruta en la que queremos que se ubiquen los archivos que recibamos mediante transferencias
- Podemos visualizar la IP local del equipo para facilitar el intercambio de contactos entre equipos.ç


## Detalles de implementación


- Funcionalidades
- Host -> añadir, editar eliminar un host, seleccionar varios hosts para envios multiples ->
- File managment -> muestra las personas que van a recibir el archivo, permite seleccionar un archivo indicando que archivo está cargado actualmente, boton que inicia la transferencia
- transference panel -> Muestra de forma clara e intuitiva el estado de las transferences mostrando información relevante como la source, dst, nombre del archivo y porcentaje de transferencia
- Server panel -> muestra información relativa al estado del servidor (estado, puerto en uso, tareas actuales) también cuenta con una serie de opciones para su manejo (open,close,reset)
adicionalmente cuenta con boton ip, address

Aplicación
Aplicación cliente servidor fundamentada en programación socket, 

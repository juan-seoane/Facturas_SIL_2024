## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources, multiplatform

- `imagenes`: la carpeta donde se guardan los archivos de imagen (.png, .jpg, etc...)
- `escaneos`: la carpeta con las facturas escaneadas, en formato .png
// FIXME - 24-07-20 : Falta la carpeta 'informes'
- `informes`: la carpeta con los PDF de cada informe

- `config`: un archivo 'credenciales.json' con los pares (user:pass) para autenticarse,
            y carpetas para cada usuario con sus archivos de configuración en formato .json
- `datos`: carpetas para cada usuario con sus archivos de trabajo en formato .json

- `lib`: the folder to maintain dependencies, OS-exclusive
- `bin`: the folder to maintain binaries, OS-exclusive

The compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there. OS-exclusive.

## Dependency Management for Linux

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

## Project Info

El proyecto trata de renovar el programa en Java,
y automatizar la entrada de facturas,
de manera que el procedimiento sea más ágil: en vez de escribir uno a uno los datos de cada factura,
escanear una factura física, y, después de un OCR que convierte sus datos relevantes
en un objeto Factura, ésta se añade automáticamente a la lista de facturas...
También se actualizan las GUI con JavaFX, en vez de Swing, y se usan FXtest + JUnit5 para pruebas unitarias. Esta vez se usa VSCode con Java, en vez de Netbeans IDE.

## TODO's y demás

    // REVIEW - 24-07-20 : Dos cosas que percibo en Linux: Las imágenes de los JFX no se cargan (seguramente no usan rutas relativas), y las ventanas tienen un título central superior '<1>', que habrá que quitar o cambiar...    
    // REVIEW - 24-07-20 : En Linux no funciona el Splash, habráque hacerlo con FX...   
    // REVIEW - 24-07-17 : Cuando cierras el VisorFCT y lo vuelves a abrir, habiendo seleccionado otra factura, no actualiza la Factura en el visor...
    // REVIEW - 24-07-23 : Falta poner el visorFCT AlwaysOnTop
    // REVIEW - 24-07-23 : Falta hacer los textfield del visorFCT no editables en principio
    // REVIEW - 24-07-23 : Falta limpiar el visorFCT antes de actualizar
    // REVIEW - 24-07-23 : Falta colocar en el Visor, el TipoIVA de cada Extracto
    // REVIEW - 24-07-23 : Al meter una Factura en CSV, si es devolución poner un signo negativo en el Total
    // REVIEW - 24-07-01 : Falta en el VisorFCT : informar la 'Categoría' de la Factura y reemplazar Labels (en principio no editables) por TextFields
    // REVIEW - 24-07-28 : Cuando se muestra el visorFCT aparece otra ventana... (como un new Stage que se crea al cargar o mostrare el visor)
    // REVIEW - 24-07-28 : Además el visor no puede pasar de la factura 2 a la 1 (bajando) o de la 4 a la 1 (subiendo)...
    // REVIEW - 24-07-29 : No se limpian los campos del visor cuando se mueve entre facturas con los controles
    // REVIEW - 24-07-01 : Falta en el VisorFCT : moverse a travéws de la tablaFCT con las flechas
    // REVIEW - 24-07-01 : Falta en el Main     : Organizar el arranque de las GUI's (y los hilos de los controladores)... A lo mejor puedo invocar el GUI de Acceso con runLater y dejar el Application.launch()para el controlador...
    // REVIEW - 24-07-30 : Hay que actualizar la tabla ( y recargar la lista de Facturas) cuando se edita una Factura
    // REVIEW - 24-07-30 : Organizar lo de las BasesNI, si salen en extractos no salen en Totales, y vicecersa...
    // FIXME  - 24-07-30 : Me parece que va a ser mejor quitar el campo BaseNI de Totales, y dejarlos como un extracto
    // FIXME  - 24-07-30 : Hay que aclara lo del num de Extractos en la Factura. Si sólo hay 1 tiene que ser 1...
    // TODO   - 24-07-30 : Hay que automatizar que se sumen los extractos con el mismo tipo de IVA...
    // TODO   - 24-07-30 : Organizar los signos en las devoluciones (Subtotal no tiene que tener el signo menos)
    // TODO   - 24-07-24 : Hacer una validación de las fechas de las facturas (avisar si no corresponden al trimestre en cuestión)
    // TODO   - 24-07-01 : Falta en la TablaFCT : Investigar cómo maximizar la ventana manteniendo el formato y cómo cargar las columnas de la config
        
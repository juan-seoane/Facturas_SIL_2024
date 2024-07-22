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

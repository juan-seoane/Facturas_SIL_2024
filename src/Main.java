
import controladores.Controlador;
import controladores.fxcontrollers.Acceso;
import controladores.fxcontrollers.SplashFX;

import java.io.IOException;

import javafx.application.Application;

public class Main {
    
    public static Controlador ctr;
    public static Acceso acceso;
    public static String usuario;
    public static boolean credsOK;
  
    public static void main(String[] args) throws IOException {

    // REVIEW - 24-07-20 : Dos cosas que percibo en Linux: Las imágenes de los JFX no se cargan (seguramente no usan rutas relativas), y las ventanas tienen un título central superior '<1>', que habrá que quitar o cambiar...    
    // REVIEW - 24-07-20 : En Linux no funciona el Splash, habráque hacerlo con FX...   
    // REVIEW - 24-07-17 : Cuando cierras el VisorFCT y lo vuelves a abrir, habiendo seleccionado otra factura, no actualiza la Factura en el visor...
    // REVIEW - 24-07-23 : Falta poner el visorFCT AlwaysOnTop
    // FIXME  - 24-07-23 : Falta hacer los textfield del visorFCT no editables en principio
    // FIXME  - 24-07-23 : Falta limpiar el visorFCT antes de actualizar
    // FIXME  - 24-07-23 : Al meter una Factura en CSV, si es devolución poner un signo negativo en el Total
    // FIXME  - 24-07-23 : Falta colocar en el Visor, el TipoIVA de cada Extracto
    // FIXME  - 24-07-01 : Falta en el VisorFCT : informar la 'Categoría' de la Factura y reemplazar Labels (en principio no editables) por TextFields
    // TODO   - 24-07-01 : Falta en el VisorFCT : moverse a travéws de la tablaFCT con las flechas
    // TODO   - 24-07-01 : Falta en la TablaFCT : Investigar cómo maximizar la ventana manteniendo el formato y cómo cargar las columnas de la config
    // REVIEW - 24-07-01 : Falta en el Main     : Organizar el arranque de las GUI's (y los hilos de los controladores)... A lo mejor puedo invocar el GUI de Acceso con runLater y dejar el Application.launch()para el controlador...
        Application.launch(SplashFX.class);
    // NOTE : Desde aquí no debería funcionar hasta que se acabe la aplicación JavaFX
        System.out.println("[Main.java] Aplicación finalizada");
        System.exit(0);
               
    }

}
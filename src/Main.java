
import java.io.IOException;
import javax.swing.UIManager;
import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;

import ui.Splash;
import controladores.Controlador;
import controladores.fxcontrollers.Acceso;
import javafx.application.Application;

public class Main {
    
    public static Controlador ctr;
    public static Acceso acceso;
    public static String usuario;
    public static boolean credsOK;
  
    public static void main(String[] args) throws IOException {
        
        try{
            GraphiteLookAndFeel graphite = new GraphiteLookAndFeel();
            UIManager.setLookAndFeel(graphite);
        }catch(Exception e){
            //System.out.println(" [Main] Error "+e);
        }
        
        Splash window = new Splash();

        window.run();
        window.setAlwaysOnTop(true);
        window.setVisible(false);
    // FIXME : 01-07-24 - Falta en el VisorFCT : informar la 'Categoría' de la Factura y reemplazar Labels por TextFields
    // TODO : 01-07-24 - Falta en el VisorFCT : moverse a travéws de la tablaFCT con las flechas
    // TODO : 01-07-24 - Falta en la TablaFCT : Investigar cómo maximizar la ventana manteniendo el formato
    // TODO : 01-07-24 - Falta en el Main     : Organizar el arranque de las GUI's (y los hilos de los controladores)... A lo mejor puedo invocar el GUI de Acceso con runLater y dejar el Application.launch()para el controlador...
        Application.launch(Acceso.class);
    // NOTE : Desde aquí no debería funcionar hasta que se acabe la aplicación JavaFX
        //System.out.println("[Main.java] Aplicación finalizada");
        System.exit(0);
               
    }

}
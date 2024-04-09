import java.io.IOException;
import javax.swing.UIManager;
import javafx.application.Application;
import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;

import ui.Splash;
import controladores.Controlador;
import controladores.fxcontrollers.Acceso;

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
            //System.out.println("Error "+e);
        }
        
        Splash window = new Splash();

        window.run();
        window.setAlwaysOnTop(true);
        window.setVisible(false);
   //TODO: Organizar el arranque de las GUI's... A lo mejor puedo invocar el GUI de Acceso con runLater y dejar el Application.launch()para el controlador...
        Application.launch(Acceso.class);
    //TODO: Desde aquí no debería funcionar hasta que se acable la aplicación JavaFX
        System.out.println("[Main.java] Aplicación finalizada");
        System.exit(0);
               
    }

}
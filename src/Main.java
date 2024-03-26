import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;

import controladores.Controlador;
import modelo.Config;
import ui.AccesoJFX;
import ui.Splash;

import javafx.application.Application;

public class Main {
    
    static Controlador ctr;
    public static AccesoJFX acceso;
  
    public static void main(String[] args) {

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
        
        Application.launch(AccesoJFX.class, args);
         
         
         while(!AccesoJFX.entrar()){
            System.out.println("");
         } 
         System.out.println("Acceso: superado");
         
/*         if (Config.getConfig(acceso.getUsuario())!=null){ 
           
            JOptionPane.showMessageDialog(null, "Bienvenido a Facturas SIL!");   
            initcomponents();
            ctr = new Controlador();
            acceso.imprimir("Arrancando Controlador Principal");
            ctr.start();
            acceso.cerrar();
            //TODO: revisar todos los métodos estáticos del programa 
        }else{
            System.out.println("No se ha podido iniciar la aplicación");
            acceso.imprimir("No se ha podido iniciar la aplicación...La aplicacion se cerrará!");
            
            System.exit(0);
        }
    */    }

    public static void initcomponents(){

    }
}
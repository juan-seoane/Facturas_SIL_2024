import javax.swing.UIManager;

import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;

import controladores.Controlador;
import modelo.Config;
import ui.Splash;

import ui.fxcontrollers.Acceso;

import javafx.application.Application;

public class Main {
    
    static Controlador ctr;
    public static Acceso acceso;
    public static String usuario;
    public static boolean credsOK;
  
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
   
        Application.launch(Acceso.class);
               
        while((acceso!=null) && !acceso.entrar() && (Acceso.intentos < 5)){
            //TODO: El while debe tener contenido, si no, falla... Buscar algo equivalente a la línea siguiente...
            System.out.println("");
            //usuario = Acceso.getUsuario().toLowerCase();   
        }
        
        usuario = Acceso.usuario.toLowerCase();
        
        System.out.println("Acceso: superado para el usuario " + usuario);

        if (Config.getConfig(usuario)!=null){ 

            //initcomponents();
            ctr = new Controlador();
            //TODO: Estaría bien una ventana en la que se relatase el arranque del programa...
            
            //
            Acceso.imprimir(Acceso.getCanvas() , "\n[Main.java>Acceso2] Arrancando Controlador Principal");
            System.out.println("[Main.java] Arrancando Controlador Principal");
            ctr.start();
        
            
        }else{
            System.out.println("[Main.java] No se ha podido iniciar la aplicación");
            System.exit(0);
        }       
    }

    public static void initcomponents(){

    }
}
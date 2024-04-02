import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;

import controladores.Controlador;
import modelo.Config;
import ui.Splash;
import ui.fxcontrollers.Acceso;
import ui.fxcontrollers.Acceso2;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main {
    
    static Controlador ctr;
    public static Acceso acceso;
    public static Acceso2 acceso2;
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
        /*
        Path path = Paths.get("");
        String directoryName = path.toAbsolutePath().toString();
        System.out.println("Current Working Directory at Main is = " + directoryName);
        mostrarRuta();
        */
        Application.launch(Acceso.class, args);
         
         
        while((acceso!=null) && !acceso.entrar() && (Acceso.intentos < 5)){
            System.out.println("");   
        }
        usuario = Acceso.getUsuario();
        
        credsOK = true;

        System.out.println("Acceso: superado");
        //TODO: Arreglar esta chapuza(uso 'admin' para comprobar otras cosas)
        if (Config.getConfig(usuario)!=null){ 
        
            Acceso2.setUsuario(usuario);

            //initcomponents();
            ctr = new Controlador();
            Acceso2.imprimir(Acceso2.getCanvas() , "\n[Main.java>Acceso2] Arrancando Controlador Principal");
            ctr.start();
        
            
        }else{
            System.out.println("[Main.java] No se ha podido iniciar la aplicación");
            System.exit(0);
        }       
    }

    /*
    private static void mostrarRuta() {
        String nombreArchivo1 = "D:\\OneDrive\\Documentos\\GitHub\\Facturas_SIL_2024\\src\\Main.java";
        String nombreArchivo2 = "D:\\OneDrive\\Documentos\\GitHub\\Facturas_SIL_2024\\src\\ui\\Acceso.java";
        String nombreArchivo3 = "D:\\OneDrive\\Documentos\\GitHub\\Facturas_SIL_2024\\src\\views\\Acceso.fxml";

        File fichero1 = new File(nombreArchivo1);
        File fichero2 = new File(nombreArchivo2);
        File fichero3 = new File(nombreArchivo3);

        String rutaRel1 = fichero1.getPath();
        String rutaRel2 = fichero2.getPath();
        String rutaRel3 = fichero3.getPath();
 
        System.out.println("Ruta del archivo Main: "+rutaRel1);
        System.out.println("Ruta del archivo Acceso.java: "+rutaRel2);
        System.out.println("Ruta del archivo Acceso.fxml: "+rutaRel3);
    }
*/
    public static void initcomponents(){

    }
}
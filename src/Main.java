import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;

import controladores.Controlador;
import modelo.Config;
import ui.Acceso;

import ui.Splash;

import javafx.application.Application;

public class Main {
    
    static Controlador ctr;
    public static Acceso acceso;
  
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
         
         
         while(!Acceso.entrar()){
            System.out.println("");
         } 
         System.out.println("Acceso: superado");
         
         if (Config.getConfig(acceso.getUsuario())!=null){ 
           
            JOptionPane.showMessageDialog(null, "Bienvenido a Facturas SIL!");   
            initcomponents();
            ctr = new Controlador();
            Acceso.imprimir("Arrancando Controlador Principal");
            ctr.start();
            acceso.cerrar();
            //TODO: revisar todos los métodos estáticos del programa 
        }else{
            System.out.println("No se ha podido iniciar la aplicación");
            Acceso.imprimir("No se ha podido iniciar la aplicación...La aplicacion se cerrará!");
            
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
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;

import controladores.Controlador;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelo.Config;
import ui.AccesoJFX;
import ui.Splash;


public class Main extends Application{
    
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
        
         
         
         while(!AccesoJFX.entrar()){
            System.out.println("");
         } 
         System.out.println("Acceso: superado");
         if (Config.getConfig(acceso.getUsuario())!=null){ 
           
            JOptionPane.showMessageDialog(null, "Bienvenido a Facturas SIL!");   
            initcomponents();
            ctr = new Controlador();
            acceso.imprimir("Arrancando Controlador Principal");
            ctr.start();
            acceso.cerrar();
            /*TODO: revisar todos los métodos estáticos del programa */
        }else{
            System.out.println("No se ha podido iniciar la aplicación");
            acceso.imprimir("No se ha podido iniciar la aplicación...La aplicacion se cerrará!");
            
            System.exit(0);
        }
    }

    public static void initcomponents(){

    }

    @Override
    public void start(Stage arg0) throws Exception {
            
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("./views/Acceso.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("acceso.css").toExternalForm());
        Stage ventanaAcceso = new Stage();
        ventanaAcceso.setScene(scene);
        ventanaAcceso.setTitle("Proceso de Autenticación");
        ventanaAcceso.show();
    }
}
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;

import controladores.Controlador;
import modelo.Config;
import ui.Acceso;
import ui.Splash;


public class Main {
    
    static Controlador ctr;
  
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
        
         Acceso acceso = new Acceso();
         while(!Acceso.entrar()){
            System.out.print("");
         } 
         System.out.println("Acceso: superado");
         if (Config.getConfig(acceso.getUsuario())!=null){ 
            acceso.dispose();
            JOptionPane.showMessageDialog(null, "Bienvenido a Facturas SIL!");   
            initcomponents();
            ctr = new Controlador();
            System.out.println("Arrancando Controlador Principal");
            ctr.start();
            /*TODO: revisar todos los métodos estáticos del programa */
        }else{
            System.out.println("No se ha podido iniciar la aplicación");
            System.exit(0);
        }
    }

    public static void initcomponents(){

    }
}
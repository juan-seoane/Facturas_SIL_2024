package ui.fxcontrollers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.w3c.dom.events.KeyboardEvent;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import modelo.Config;
import modelo.Contrasenha;

public class Acceso extends Application implements Initializable{

    
    private static Stage ventanaAcceso;

    private static Scene scene1;
    private static Scene scene2;

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnOK; 
    @FXML public TextArea txtArea;
    
    
    @FXML public static boolean aceptado = false;

    public static TextArea canvasAcceso;
    


    public static String usuario ="";
    public static int intentos = 1;
    
    @FXML 
    public void probar(ActionEvent event) throws InterruptedException{
        TextField userF = this.txtUsuario;
        PasswordField passF = this.txtPassword;
       // Acceso.imprimir(txtArea, "[Acceso.java>probar()]Datos introducidos : "+ userF.getText()+ " - "+passF.getText());

        System.out.println("[Acceso.java>probar()]Contenido del Área de Texto: "+ this.txtArea.getText());
        //Acceso.imprimir(this.txtArea, "texto introducido : "+ userF.getText() + " - " +passF.getText()+" - intentos: "+ intentos);
        //System.out.println("texto introducido : "+ userF.getText() + " - " +passF.getText());
        for (Contrasenha contr : Config.getConfig("ADMIN").getContrasenhas()){
            
            System.out.println("[Acceso.java>probar()>for(contraseñas)]Datos introducidos : usuario: "+ userF.getText()+ " - pass: "+passF.getText());

            //Acceso.imprimir( this.txtArea, "\nDatos introducidos : "+ userF.getText()+ " - "+passF.getText());
                
            if ((intentos<5)&&(userF.getText().toUpperCase().trim().equals(contr.getUsuario().toUpperCase()) && (passF.getText().toUpperCase().trim().equals(contr.getContrasenha().toUpperCase()))))
            {
               
                Acceso.aceptado = true;

                cambiarEscena(Acceso.scene2);

                Acceso.imprimir( Acceso.getCanvas(), "...Ok...Entrando!\nBienvenido a FacturasSIL 2024!");
                System.out.println("[Acceso.java: intentos<5 y cred OK]...OK, entrando...");

                esperar();
                System.exit(0);


            }else if(intentos<5){
                //puentear el acceso
                //Acceso.aceptado = true;
                //System.out.println("Usuario y Contraseña incorrectos...pero pase...");
                //txtArea.appendText("\nDatos incorrectas...pero pase...!");

                System.out.println("[Acceso.java: intentos<5 y cred NO]Usuario y Contraseña incorrectos... Repita, por favor");
                Acceso.imprimir(this.txtArea, "\nDatos incorrectos: " + this.txtUsuario.getText() +" - " + this.txtPassword.getText() + "\n...Por favor vuelva a intentarlo... (intentos: "+intentos+")");
                intentos++;
                reintentar();
            
            }
            else{
                cambiarEscena(Acceso.scene2);

                System.out.println("[Acceso.java: intentos>=5 y cred NO] El proceso de Autenticación ha fallado!");
                System.out.println("[Acceso.java] El programa se cerrará!");
//TODO: Estoy probando si recoge el Canvas (el textArea) en la segunda escena...
                Acceso.imprimir( Acceso.getCanvas(), "\nEl proceso de Autenticación ha fallado!");
                Acceso.imprimir( Acceso.getCanvas(), "\nEl programa se cerrará!");

                esperar();
                System.exit(0); 
                            
            }
            

            if (Acceso.aceptado){

                //Acceso.usuario = txtUsuario.getText();
                
                //TODO: Hacer el TextArea autoscrollable
                //TODO: La ventana mostrará el desarrollo de la carga de controladores y al final se cerrará
                //Main.usuario = this.usuario;   
                
                //System.exit(0);
                
            }
        }
    }
    private Scene crearScene1 () throws IOException{
        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(getClass().getResource("../fxviews/Acceso.fxml"));
        
       
        Parent root1 = loader1.load();
        Scene escena1 = new Scene(root1);
        //scene.getStylesheets().add(getClass().getResource("acceso.css").toExternalForm());

        
        return escena1;
    }

    private Scene crearScene2() throws IOException{
        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(getClass().getResource("../fxviews/Acceso2.fxml"));
               
        Parent root2 = loader2.load();
        Scene escena2 = new Scene(root2);
        //scene.getStylesheets().add(getClass().getResource("acceso.css").toExternalForm());

        return escena2;
    }

    private void cambiarEscena(Scene es) {

        Stage stage = (Stage) this.txtArea.getScene().getWindow();
        
        Acceso.ventanaAcceso = stage;

        //scene.getStylesheets().add(getClass().getResource("acceso.css").toExternalForm());
        Acceso.ventanaAcceso.setScene(es);
        //ventanaAcceso.initModality(Modality.APPLICATION_MODAL);
        Acceso.ventanaAcceso.setResizable(false);
        Acceso.ventanaAcceso.setTitle("Acceso a FacturasSIL");
        //Acceso.ventanaAcceso.show();

        //TODO: Definir Acceso.canvasAcceso otra vez
         
    }

    private void reintentar() {
        this.txtUsuario.clear();
        this.txtPassword.clear();
        this.txtUsuario.requestFocus();
    }

    public boolean entrar(){
        if(Acceso.aceptado){
            return true;
        }
        return false;
    }

    //TODO: Lo de abajo sólo funciona si se implementa el Interfaz "Inicializable" (implements Initilizable)
    @FXML
    public void initialize(URL location, ResourceBundle resources) {      
 
        canvasAcceso = this.txtArea;
        System.out.println("[Acceso - initialize()] canvasAcceso activado: " + (canvasAcceso!=null) );
       //TODO: 03/04/24 18:21 - Me quedo aquí, a punto de hacer el listener este...(repasar)
        // canvasAcceso.setOnKeyPressed(new function(){
        //        pulsartecla();
         //   }
        //);
    }    

    public static String getUsuario() {
        return Acceso.usuario;
    }

    public static TextArea getCanvas(){   
        return Acceso.canvasAcceso;
    }

    @FXML 
    private void esperar() throws InterruptedException{
        Stage stage = (Stage) Acceso.getCanvas().getScene().getWindow();
        stage.wait();
    }

    
  //  private void pulsartecla(KeyboardEvent ev){
 //       notifyAll();
 //   }

    public static void imprimir(TextArea tA, String cont) {
        tA.appendText("\n"+cont);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Acceso.ventanaAcceso = primaryStage;
        Acceso.ventanaAcceso.setTitle("Acceso a FacturasSIL");

        Acceso.scene1 = crearScene1();
        Acceso.scene2 = crearScene2();

        Acceso.ventanaAcceso.setScene(Acceso.scene1);
        //this.ventanaAcceso.initModality(Modality.APPLICATION_MODAL);
        Acceso.ventanaAcceso.setResizable(false);
 
        Acceso.ventanaAcceso.show();
   
    }
}
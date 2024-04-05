package ui.fxcontrollers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    public void probar() throws InterruptedException{
        TextField userF = this.txtUsuario;
        PasswordField passF = this.txtPassword;
       // Acceso.imprimir(txtArea, "[Acceso.java>probar()]Datos introducidos : "+ userF.getText()+ " - "+passF.getText());

        //System.out.println("[Acceso.java>probar()]Contenido del Área de Texto: "+ this.txtArea.getText());
        //Acceso.imprimir(this.txtArea, "texto introducido : "+ userF.getText() + " - " +passF.getText()+" - intentos: "+ intentos);
        //System.out.println("texto introducido : "+ userF.getText() + " - " +passF.getText());
        for (Contrasenha contr : Config.getConfig("ADMIN").getContrasenhas()){
            
            System.out.println("[Acceso.java>probar()>for(contraseñas)]Datos introducidos : usuario: "+ userF.getText()+ " - pass: "+passF.getText() + "Datos obtenidos de Config: " + (Contrasenha)(Config.getConfig("ADMIN").getContrasenhas().toArray()[0]));

            //Acceso.imprimir( this.txtArea, "\nDatos introducidos : "+ userF.getText()+ " - "+passF.getText());
                
            if ((Acceso.intentos<5)&&(userF.getText().toUpperCase().trim().equals(contr.getUsuario().toUpperCase()) && (passF.getText().toUpperCase().trim().equals(contr.getContrasenha().toUpperCase()))))
            {
                acierto();
                break;

            }else if(Acceso.intentos>=5){
                fallo();
                break;      
            }
        }
        reintento();
        
    }

    private void fallo() {
        cambiarEscena(Acceso.scene2);
        //System.out.println("[Acceso.java: intentos>=5 y cred NO] El proceso de Autenticación ha fallado!");
        System.out.println("[Acceso.java] El programa se cerrará!");
        Acceso.imprimir( Acceso.getCanvas(), "\nEl proceso de Autenticación ha fallado!");
        Acceso.imprimir( Acceso.getCanvas(), "\nEl programa se cerrará!\nPulse cualquier tecla para continuar..."); 
        Acceso.ventanaAcceso.requestFocus(); 
    }

    private void acierto() {
        Acceso.usuario=txtUsuario.getText().toUpperCase();
        Acceso.aceptado = true;
        cambiarEscena(Acceso.scene2);
        Acceso.imprimir( Acceso.getCanvas(), "...Ok...Entrando!\nBienvenido a FacturasSIL 2024!\nPulse una tecla para continuar...");
        //System.out.println("[Acceso.java: intentos<5 y cred OK]...OK, entrando...");
        Acceso.ventanaAcceso.requestFocus();  
    }

    private void reintento() throws InterruptedException {
        Acceso.imprimir(this.txtArea, "\nDatos incorrectos: " + this.txtUsuario.getText() +" - " + this.txtPassword.getText() + "\n...Por favor vuelva a intentarlo... (intentos: "+intentos+")");
        Acceso.intentos++;

        this.txtUsuario.clear();
        this.txtPassword.clear();
        this.txtUsuario.requestFocus();
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
        
        Acceso.ventanaAcceso.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
            public void handle(KeyEvent ke){
                //System.out.println("Key Pressed: " + ke.getCode());
                try {
                    pulsartecla();
                } catch (Exception e) {
                    System.out.println("[Acceso.java>cambiarEscena] Excepción manejando el KeyEvent");
                }
                ke.consume(); // <-- stops passing the event to next node
            }  
         });
         
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
        //TODO: No sé cómo hacer para poner el foco al arrancar en ese campo de texto... la siguiente línea no funciona
        //txtUsuario.requestFocus();
        canvasAcceso = this.txtArea;
        //System.out.println("[Acceso - initialize()] canvasAcceso activado: " + (canvasAcceso!=null) );
    }    

    public static String getUsuario() {
        //Este procedimiento tiene que leer el usuario antes de cerrarse la ventana...
        return Acceso.usuario;
    }

    public static TextArea getCanvas(){   
        return Acceso.canvasAcceso;
    }

    private void pulsartecla() throws Exception{

        //Acceso.ventanaAcceso.close();
        Platform.exit();
        if (!entrar()){
            System.exit(0);
        }
    }

    @FXML
    private void pulsarBotonOK() throws InterruptedException{
        probar();
    }

    @FXML
    private void pulsarEnter(KeyEvent ke) throws InterruptedException{
        if(ke.getCode()==KeyCode.ENTER){
            pulsarBotonOK();
            ke.consume(); // <-- stops passing the event to next node
        }
    }

    public static void imprimir(TextArea tA, String cont) {
        tA.appendText("\n"+cont);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Acceso.ventanaAcceso = primaryStage;
        Acceso.ventanaAcceso.setTitle("Acceso a FacturasSIL");

        // prevent automatic exit of application when last window is closed
        Platform.setImplicitExit(false);

        Acceso.scene1 = crearScene1();
        Acceso.scene2 = crearScene2();

        Acceso.ventanaAcceso.setScene(Acceso.scene1);
        //this.ventanaAcceso.initModality(Modality.APPLICATION_MODAL);
        Acceso.ventanaAcceso.setResizable(false);
 
        Acceso.ventanaAcceso.show();
   
    }
}
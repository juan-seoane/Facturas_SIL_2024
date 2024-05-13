package controladores.fxcontrollers;

import modelo.fx.ComprobacionesAcceso;
import controladores.Controlador;

import java.awt.HeadlessException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
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

    private boolean credsOK;
    
    @FXML 
    public void probar() throws InterruptedException, HeadlessException, NullPointerException, IOException{
        TextField userF = this.txtUsuario;
        PasswordField passF = this.txtPassword;

        String user = userF.getText();
        String pass = passF.getText();
       // Acceso.imprimir(txtArea, "[Acceso.java>probar()]Datos introducidos : "+ userF.getText()+ " - "+passF.getText());

        //System.out.println("[Acceso.java>probar()] Contenido del Área de Texto: "+ this.txtArea.getText());
        //Acceso.imprimir(this.txtArea, "texto introducido : "+ userF.getText() + " - " +passF.getText()+" - intentos: "+ intentos);
        //System.out.println("[Acceso.java>probar()] texto introducido : "+ userF.getText() + " - " +passF.getText());
//TODO: OJO! Usuario siempre se contrasta en mayúsculas (aunque esté escrito en minúsculas)
        ComprobacionesAcceso check = new ComprobacionesAcceso();
        credsOK = check.comprobarCredenciales(user, pass);

        if (!credsOK&&Acceso.intentos<3)
            reintento();
        else if(!credsOK&&Acceso.intentos>=3)
            fallo();
        else 
            acierto();
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
        Acceso.usuario=txtUsuario.getText();
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
    
    public boolean entrar(){
        if(Acceso.aceptado)
            return true;
        else
            return false;
    }

    private Scene crearScene1 () throws IOException{
        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(getClass().getResource("../../ui/fxviews/Acceso.fxml"));
        
       
        Parent root1 = loader1.load();
        Scene escena1 = new Scene(root1);
        //scene.getStylesheets().add(getClass().getResource("acceso.css").toExternalForm());

        
        return escena1;
    }

    private Scene crearScene2() throws IOException{
        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(getClass().getResource("../../ui/fxviews/Acceso2.fxml"));
               
        Parent root2 = loader2.load();
        Scene escena2 = new Scene(root2);
        //scene.getStylesheets().add(getClass().getResource("acceso.css").toExternalForm());

        return escena2;
    }

    private void cambiarEscena(Scene es) {
        //TODO: Probando con un elemento de la GUI no estático
        Stage stage = (Stage) this.txtArea.getScene().getWindow();
        
        Acceso.ventanaAcceso = stage;

        //scene.getStylesheets().add(getClass().getResource("acceso.css").toExternalForm());
        Acceso.ventanaAcceso.setScene(es);
        //ventanaAcceso.initModality(Modality.APPLICATION_MODAL);
        Acceso.ventanaAcceso.setResizable(false);
        Acceso.ventanaAcceso.setTitle("Acceso a FacturasSIL");
        
        Acceso.ventanaAcceso.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
            public void handle(KeyEvent ke){
                System.out.println("[Acceso.java>cambiarEscena()]Key Pressed: " + ke.getCode());
                try {
                    pulsartecla();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                ke.consume(); // <-- stops passing the event to next node
            }  
         });
         
    }

    //TODO: Lo de abajo sólo funciona si se implementa el Interfaz "Inicializable" (implements Initilizable)
    @FXML
    public void initialize(URL location, ResourceBundle resources) {     
        //TODO: No sé cómo hacer para poner el foco al arrancar en ese campo de texto... la siguiente línea no funciona
        //txtUsuario.requestFocus();
        canvasAcceso = this.txtArea;
        System.out.println("[Acceso - initialize()] canvasAcceso activado: " + (canvasAcceso!=null) );
    }    

    public static String getUsuario() {
        //Este procedimiento tiene que leer el usuario antes de cerrarse la ventana...
        return Acceso.usuario;
    }

    public static TextArea getCanvas(){   
        return Acceso.canvasAcceso;
    }

    private void pulsartecla() throws IOException {

        iniciarPrograma();
        
        if (!entrar()){
            System.exit(0);
        }
    }

    private void iniciarPrograma(){

        try {
            arrancarControlador();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Acceso.ventanaAcceso.close();
//        Platform.exit();
    }

    @FXML
    private void pulsarBotonOK() throws InterruptedException, HeadlessException, NullPointerException, IOException{
        probar();
    }

    @FXML
    private void pulsarEnter(KeyEvent ke) throws InterruptedException, HeadlessException, NullPointerException, IOException{
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
//#region RUN_CTLLR
    private void arrancarControlador() throws IOException{
 // TODO: 13-05-2024 : Aquí arroja una 'IOException' que afecta al hilo general del programa... Habría que ver cómo evitar que se propague a las llamadas anteriores...       
        Controlador ctrThread =new Controlador();
        ctrThread.setName("Controlador_Ppal");
        ctrThread.start();
        
        cargarPanelControl();
    }
//#endregion
//#region LOAD_C/P
    private boolean cargarPanelControl(){

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../ui/fxviews/PanelControl.fxml"));

        Parent root;
        
        try {

            root = loader.load();
//            PanelControl pc = loader.getController();

            Scene escena = new Scene(root);
            Stage  ventanaPCntrl = new Stage();
    
            ventanaPCntrl.setScene(escena);
            ventanaPCntrl.setResizable(false);
            ventanaPCntrl.show();
    
            ventanaPCntrl.setAlwaysOnTop(true);
            ventanaPCntrl.setOnCloseRequest(e -> System.exit(0));
            
            return true;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }
//#endregion
}
package controladores.fxcontrollers;

import modelo.base.Config;
import modelo.helpers.ComprobacionesAcceso;
import controladores.Controlador;
import controladores.helpers.FxmlHelper;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.BrokenBarrierException;

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

//#region CAMPOS_FXML
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnOK;
    @FXML public TextArea txtArea;
    @FXML public static boolean aceptado = false;
//#endregion

//#region OTROS_CAMPOS
    private static Stage ventanaAcceso;

    private static Scene scene1;
    private static Scene scene2;

    public static TextArea canvasAcceso;
    public static String usuario ="";
    public static int intentos = 1;

    private boolean credsOK;
//#endregion

//#region INIT
    //TODO: Lo de abajo sólo funciona si se implementa el Interfaz "Inicializable" (implements Initilizable)
    @FXML
    public void initialize(URL location, ResourceBundle resources) {     
        //TODO: No sé cómo hacer para poner el foco al arrancar en ese campo de texto... la siguiente línea no funciona
        //txtUsuario.requestFocus();
        canvasAcceso = this.txtArea;
        //System.out.println("[Acceso - initialize()] canvasAcceso activado: " + (canvasAcceso!=null) );
    }    
//#endregion

//#region RUN_CTLLR
private void arrancarControlador() throws IOException, InterruptedException, BrokenBarrierException{
    // TODO - 24-05-13 : Aquí arroja una 'IOException' que afecta al hilo general del programa... Habría que ver cómo evitar que se propague a las llamadas anteriores...       
           
           Controlador ctrThread = Controlador.getControlador();
   
           ctrThread.setName("Ctrl_Ppal");
           ctrThread.start();
   
           if (!cargarPanelControl()){
                System.out.println("[Acceso>arrancarControlador] Aplicacion finalizada");
                Platform.exit();
           }
       }
   //#endregion

//#region GETTERS/SETTERS
public static String getUsuario() {
    //Este procedimiento tiene que leer el usuario antes de cerrarse la ventana...
    return Acceso.usuario;
}

public static TextArea getCanvas(){   
    return Acceso.canvasAcceso;
}
//#endregion

//#region EVT_BTNS
private void pulsartecla() throws IOException {

    iniciarPrograma();
    
    if (!entrar()){
        System.out.println("[Acceso>pulsartecla] Autenticacion erronea. Se cierra la Aplicacion");
        Platform.exit();
    }
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
//#endregion

//#region LOGIN
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
        //System.out.println("[Acceso.java] El programa se cerrará!");
        Acceso.imprimir( Acceso.getCanvas(), "\nEl proceso de Autenticación ha fallado!");
        Acceso.imprimir( Acceso.getCanvas(), "\nEl programa se cerrará!\nPulse cualquier tecla para continuar..."); 
        Acceso.ventanaAcceso.requestFocus(); 
    }

    private void acierto() {
        Acceso.usuario=txtUsuario.getText();
        Acceso.aceptado = true;
        try {
            Config.getConfig(Acceso.usuario);
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
            System.out.println("[Acceso>acierto] Problema al cargar la Config. Se cierra la Aplicacion");
            Platform.exit();
        }
        cambiarEscena(Acceso.scene2);
        Acceso.imprimir( Acceso.getCanvas(), "...Ok...Entrando!\nBienvenido a FacturasSIL 24!\nPulse una tecla para continuar...");
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
    
    private void iniciarPrograma(){
        try {
            arrancarControlador();
        } catch (IOException | InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
            System.out.println("[Acceso>login] Problema al iniciar programa. Se cierra la Aplicacion");
            Platform.exit();
        }
        Acceso.ventanaAcceso.close();
    }
//#endregion

//#region HELPERS
    private Scene crearScene1 () throws IOException{
        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(getClass().getResource("../../resources/Acceso.fxml"));
        
       
        Parent root1 = loader1.load();
        Scene escena1 = new Scene(root1);
        //scene.getStylesheets().add(getClass().getResource("acceso.css").toExternalForm());

        
        return escena1;
    }

    private Scene crearScene2() throws IOException{
        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(getClass().getResource("../../resources/Acceso2.fxml"));
               
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
//                //System.out.println("[Acceso.java>cambiarEscena()]Key Pressed: " + ke.getCode());
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
//#endregion

//#region LOAD_C/P
    private synchronized boolean cargarPanelControl(){
/*
        File miDir = new File (".");
        String path = "";
        try {
            path = miDir.getCanonicalPath();
            //System.out.println("[Acceso>cargarPanelControl] path: " + path);
        } catch (IOException e) {
            //System.out.println("[Acceso>cargarPanelControl] Error al obtener la ruta de los controladores");
            e.printStackTrace();
        }
        path = path.replace('\\','/');
*/
        String ruta  = "../../resources/PanelControl.fxml";
        //System.out.println("[Acceso>cargarPanelControl] ruta  : " + ruta);
//        String ruta2 = "/resources/fxmltablaFCT.fxml";
//        //System.out.println("[Acceso>cargarPanelControl] ruta2 : " + ruta2);

        FxmlHelper FXMLpc = new FxmlHelper(ruta);
        //FxmlHelper FXMLcfct = new FxmlHelper(ruta2);

        Parent root;

        root = FXMLpc.cargarFXML();

        Scene escena = new Scene(root);
        Stage vPC = Controlador.setStage(escena, true);
        vPC.setResizable(false);
        // TODO - 24-05-30 : Aquí se ajusta el modo de la ventana de P/C

        vPC.setOnCloseRequest(e -> System.exit(0));
        PanelControl.setGUI(vPC);
        PanelControl.getPanelControl().mostrar();
        
        //Asignar el actual P/C a las clases necesarias
        //ControladorFacturas.FXcontrlTablaFCT = (FxCntrlTablaFCT)(FXMLtablafct.getFXcontr());
        //ControladorFacturas.FXcontrlVisorFCT = (FxCntrlVisorFCT)(FXMLvisorfct.getFXcontr());
        Controlador.setPanelControl((PanelControl)(FXMLpc.getFXcontr()));
        
        return true;        
    }

    public void rutaExiste(String ruta){

        boolean rutaExiste = (new File(ruta)).exists();
        if (rutaExiste){
            //System.out.println("[Acceso>rutaExiste] El archivo " + ruta + " existe.");
        }
        else{
            try {
                File miDir = new File (".");
                //System.out.println("[Acceso>rutaExiste] El archivo " + ruta + " no existe.");
                //System.out.println ("[Acceso>rutaExiste] Directorio actual: " + miDir.getCanonicalPath());
            }
            catch(Exception e) {
                //System.out.println("[Acceso>rutaExiste] El archivo " + ruta + " no existe.");
                //System.out.println("[Acceso>rutaExiste] Excepcion " + e + " durante el proceso.");
                e.printStackTrace();
            }
        }
    }
//#endregion

}
package controladores.fxcontrollers;

import modelo.ModeloFacturas;
import modelo.base.Config;
import modelo.records.Factura;
import controladores.Controlador;
import controladores.ControladorFacturas;
import controladores.helpers.FxmlHelper;

import java.net.URL;
import java.util.ResourceBundle;

import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * @author juanseoane
 */

public class PanelControl implements Initializable{

    public static Stage ventanaPCtl;

    public static Scene escena1;

    public static FxControladorFacturas fxFCTcontr;

    static Image icon;
//    static TrayIcon trayIcon;
    static Popup popMenu;
    
    @FXML private ToggleButton btnFCT;
    @FXML private Button btnRS;
    @FXML private Button btnCJA;
    @FXML private Button btnCFG;
    @FXML private Button btnNTS;
    @FXML private Button btnAutosave;
    
    @FXML private ToggleButton toggleModo;
    
    @FXML private Label lblEntradas;
    @FXML private Label lblTrimestre;
    @FXML private Label lblAnho;
    @FXML private Label lblUsuario;

    private static boolean botonpulsado = false;
    private static int botonactivo = 1;
    public static PanelControl instancia_pc;
    public static int modo;
    public Config configActual;
    public Controlador ctrlPpal;
    public ControladorFacturas ctrlFct;
    public String usuarioActual;
    public Stage tablaFCT;
    //TODO: 12-04-2024 - ¿Porqué no puede seguir siendo un Singleton?
    //TODO: 12-04-2024 - Hay que definir un usuariActual, y una configActual
    public PanelControl() {
        this.usuarioActual = Controlador.usuario;
// TODO : 29-05-2024 - Luego habrá que cambiar esto de abajo a modo NAV por defecto...
        PanelControl.modo = Controlador.INGR;
        try {
            this.configActual = Config.getConfig(this.usuarioActual);
        } catch (NullPointerException | IOException e) {
            System.out.println("[PanelControl>Constructor] Excepcion creando el P/C, imposible obtener la config actual");
            e.printStackTrace();
        }
//TODO : 21-06-2024 - Estas asignaciones me hacen falta
/*
        this.ctrlPpal = Controlador.getControlador();
        this.ctrlFct = ControladorFacturas.getControlador();

*/
        instancia_pc = this;

  }
//#region INITIALIZE
    @FXML
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO: 09/04/24 - Crear un Controlador general, y decidir cómo abrirá las tablas, etc...
        // TODO: 09/04/24 - Crear un controladorFicheros en un nuevo hilo para que gestione el Guardado Automático
        // TODO: Cambiar el diseño de los ToggleButton al pulsarse y el mensaje que arrojan
        // TODO: Arreglar la inicialización de la GUI del PanelControl... No funciona
        // TODO: 12-04-2024 - Lo dejo aquí (19:13H)
        setAño((Integer)this.configActual.configData.getAño().getAño());
        setTrimestre(this.configActual.configData.getAño().getTrimestre());
        setNumfacturas(ModeloFacturas.getNumeroFacturas());
        setUsuario(this.configActual.usuario.toLowerCase());
        //sólo cargar tablas, no mostrarlas...
        boolean ok = cargarTablaFacturas();
        //Esto sería mostrarla
        //ctrlFct.mostrarTablaFacturas();
        if (ok){
            System.out.println("[PanelControl>Constructor] Tabla Facturas cargada!!");
        }
    }
//#endregion
    public void setAño(int i) throws NumberFormatException{
        this.lblAnho.setText(i+"");
    }
    public void setNumfacturas(int i) throws NumberFormatException{
        this.lblEntradas.setText(i +"");
    }
    public void setTrimestre(int i){
        this.lblTrimestre.setText(i+"");
    }
    public void setUsuario(String user){
       this.lblUsuario.setText(user +"");
    }
    
    public static int getModo(){
        return PanelControl.modo;
    }
    @FXML    
    private void btnCFGpulsado(Event evt) {
        this.ctrlPpal = Controlador.getControlador();

        System.out.println(" [PanelControl] Boton CFG pulsado!");
        botonactivo = 4;
        botonpulsado = true;
    }
    @FXML
    private void btnNTSpulsado(Event evt) {
        System.out.println(" [PanelControl] Boton NTS pulsado!");
        botonactivo = 3;
        botonpulsado = true;

    }
    @FXML
    private void btnRSpulsado(Event evt) {
        System.out.println(" [PanelControl] Boton DIST pulsado!");        
        botonactivo = 2;
        botonpulsado = true;

    }
    @FXML
    private void btnFCTpulsado(Event evt) {
        System.out.println(" [PanelControl] Boton FCT pulsado!");
        this.ctrlFct = ControladorFacturas.getControlador();

        if (!((ToggleButton)(evt.getSource())).isSelected()){
            btnFCT.setStyle("-fx-background-color: transparent; -fx-border-color: #063970; -fx-border-radius: 10; -fx-border-width: 3"); 
            System.out.println( " [PanelControl] FCT desactivado!");
            botonactivo = 11;
            botonpulsado = true;
            ctrlFct.ocultarTablaFacturas();
        }
        else if (((ToggleButton)(evt.getSource())).isSelected()){
            btnFCT.setStyle("-fx-background-color: yellow; -fx-border-color: #063970; -fx-border-radius: 10; -fx-border-width: 3");
            System.out.println(" [PanelControl] FCT activo!");

            botonactivo = 1;
            botonpulsado = true;
            ctrlFct.mostrarTablaFacturas(); //Está fuera de sitio, debería estar en el hilo del Controlador, pero parece que funciona...
        } 

 // TODO : 29-05-2024 - Hay que desactivar el botón mientras está en uso, y colorearlo de amarillo (quizás pueda ser un ToggleButton)...       
    }
   
    /*
    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing
*/
    @FXML
    private void btnCJApulsado(Event evt) {
        System.out.println(" [PanelControl] Boton CJA pulsado!");
        botonactivo = 5;
        botonpulsado = true;
    }
    @FXML
    private void btnAutosavepulsado(Event evt) {
     
        botonactivo = 6;
        botonpulsado = true;
    }
    @FXML
    private void btnAutosavePressed(Event evt) {
        System.out.println(" [PanelControl] Boton AutoSave pulsado!");
        btnAutosavepulsado(evt);
        ((Button)evt.getSource()).setStyle("-fx-background-color: yellow; -fx-border-color: #063970; -fx-border-radius: 10; -fx-border-width: 3");

    }
    @FXML
    private void btnAutosaveReleased(Event evt) {
     
        ((Button)evt.getSource()).setStyle("-fx-background-color: transparent; -fx-border-color: #063970; -fx-border-radius: 10; -fx-border-width: 3"); 

    }  
    @FXML
    private void toggleModopulsado(Event evt) {
        System.out.println(" [PanelControl] Boton MODO pulsado!");
        if (((ToggleButton)(evt.getSource())).isSelected()){
            toggleModo.setText("MODO INGR");
            toggleModo.setStyle("-fx-background-color: yellow; -fx-border-color: #063970; -fx-border-radius: 10; -fx-border-width: 3");
            System.out.println( " [PanelControl] modo: INGR");
            modo = Controlador.INGR;
        }
        else if (!((ToggleButton)(evt.getSource())).isSelected()){
            toggleModo.setText("MODO NAV");
            toggleModo.setStyle("-fx-background-color: transparent; -fx-border-color: #063970; -fx-border-radius: 10; -fx-border-width: 3"); 
            System.out.println(" [PanelControl] modo: NAV");
            modo = Controlador.NAV;
        }       
        botonactivo = 7;
        botonpulsado = true;
    }
    
    public int seleccion(){
        return botonactivo;
    }
    public boolean botonpulsado(){
        return botonpulsado;
    }
    public static void pulsarboton(int boton){
        botonactivo = boton;
        botonpulsado = true;
    }
//#region RESET   
    public static void reset(){
        System.out.println("[PanelControl>reset] Reseteando P/C");
        botonpulsado = false;
    }
//#endregion    
//#region GET_PC
    public static PanelControl getPanelControl() {
        if (instancia_pc == null)
            instancia_pc = new PanelControl();
         return instancia_pc;
    }
//#endregion
//#region arrancarCFCT
/*
    public synchronized ControladorFacturas arrancarCfct() {
        
        Controlador.cfct = ControladorFacturas.getControlador();
        Controlador.cfct.setName("Contr_FCT");
        Controlador.cfct.start();

        return Controlador.cfct;
    }
*/
//#endregion
//#region LOAD_FCT/T
    public boolean cargarTablaFacturas() {
        if(this.tablaFCT == null){
            FxmlHelper loader = new FxmlHelper("/resources/fxmltablaFCT.fxml");

            Parent root;

            root = loader.cargarFXML();
            fxFCTcontr = (FxControladorFacturas)loader.getFXcontr();

            Scene escena = new Scene(root);
            this.tablaFCT = new Stage();

            this.tablaFCT.setScene(escena);
            this.tablaFCT.setResizable(true);
            // TODO : 30-05-2024 - Aquí se ajusta el modo de la ventana de la TablaFCT
            //this.tablaFCT.initModality(Modality.NONE);        
            
            return true;
        }
        return false;
    }
//#endregion
}

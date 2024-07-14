package controladores.fxcontrollers;

import modelo.ModeloFacturas;
import modelo.base.Config;
import controladores.Controlador;
import controladores.ControladorFacturas;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.BrokenBarrierException;
import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * @author juanseoane
 */

public class PanelControl implements Initializable{

//#region CAMPOS_FXML
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
//#endregion

//#region OTROS_CAMPOS
    static Image icon;
    //static TrayIcon trayIcon;
    static Popup popMenu;
    public static Stage ventanaPCtl;
    public static Scene escena1;
// TODO: 30-06-2024 - Los controladoresFx de FCT deberían estar en el ControladorFacturas, no aquí...
    static FxCntrlTablaFCT fxTablaFCTcontr;

    static FxCntrlVisorFCT fxVisorFCTcontr;

    static PanelControl instancia_pc = null;
    static int modo;
    static boolean botonpulsado = false;
    static int botonactivo = 1;
    static Stage GUIpanel;

    Config configActual;
    Controlador ctrlPpal;
    ControladorFacturas ctrlFct;
    String usuarioActual;
//#endregion
    
//TODO: 12-04-2024 - ¿Porqué no puede seguir siendo un Singleton?
//TODO: 12-04-2024 - Hay que definir un usuariActual, y una configActual

//#region CONSTR
    public PanelControl() {
        this.usuarioActual = Controlador.getUsuario();
// TODO : 29-05-2024 - Luego habrá que cambiar esto de abajo a modo NAV por defecto...
        PanelControl.modo = Controlador.INGR;
        try {
            this.configActual = Config.getConfig(this.usuarioActual);
        } catch (NullPointerException | IOException e) {
            //System.out.println("[PanelControl>Constructor] Excepcion creando el P/C, imposible obtener la config actual");
            e.printStackTrace();
        }
//TODO : 21-06-2024 - Estas asignaciones me hacen falta
        try {
            this.ctrlPpal = Controlador.getControlador();
            this.ctrlFct = Controlador.getControladorFacturas();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
//#endregion

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

        setUsuario(this.configActual.usuario.toLowerCase());
        //sólo cargar tablas, no mostrarlas...
        try {
            boolean ok1 = this.ctrlFct.cargarTablaFacturas();
           
            //Esto sería mostrarla
            //this.ctrlFct.mostrarTablaFacturas();
            if (ok1){
                System.out.println("[PanelControl>Constructor] Tabla Facturas cargada!!");
                fxTablaFCTcontr = this.ctrlFct.getFXcontrlTablaFCT();
                //TODO - 13-07-24 : Aquí el valor de la tableView de Fact y del visorFCT es null!!
                System.out.println("[PanelControl>Constructor] Tabla Facturas asignada!!");
            }
        } catch (InterruptedException | BrokenBarrierException e) {
            System.out.println("[PanelControl>Constructor] Tabla y Visor Facturas NO cargados!!");
            e.printStackTrace();
        }
        setNumfacturas(ModeloFacturas.getNumeroFacturas());
    }
//#endregion

//#region GETTERS/SETTERS
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
    public static void setGUI(Stage vPC) {
        GUIpanel = vPC;
     }
//#endregion

//#region EVT_BTNS
    @FXML    
    private void btnCFGpulsado(Event evt) throws InterruptedException, BrokenBarrierException {
        this.ctrlPpal = Controlador.getControlador();

        System.out.println(" [PanelControl] Boton CFG pulsado!");
        botonactivo = 4;
        botonpulsado = true;
    }
    @FXML
    private void btnNTSpulsado(Event evt) {
        //System.out.println(" [PanelControl] Boton NTS pulsado!");
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
    private void btnFCTpulsado(Event evt) throws InterruptedException, BrokenBarrierException {
        //System.out.println(" [PanelControl] Boton FCT pulsado!");
        this.ctrlFct = Controlador.getControladorFacturas();
        if (!((ToggleButton)(evt.getSource())).isSelected()){
            btnFCT.setStyle("-fx-background-color: transparent; -fx-border-color: #063970; -fx-border-radius: 10; -fx-border-width: 3"); 
            //System.out.println( " [PanelControl] FCT desactivado!");
            botonactivo = 11;
            botonpulsado = true;
        }
        else if (((ToggleButton)(evt.getSource())).isSelected()){
            btnFCT.setStyle("-fx-background-color: yellow; -fx-border-color: #063970; -fx-border-radius: 10; -fx-border-width: 3");
            //System.out.println(" [PanelControl] FCT activo!");
            botonactivo = 1;
            botonpulsado = true;
        }
 // TODO : 29-05-2024 - Hay que desactivar el botón mientras está en uso, y colorearlo de amarillo (quizás pueda ser un ToggleButton)...       
    }

    @FXML
    private void btnCJApulsado(Event evt) {
        //System.out.println(" [PanelControl] Boton CJA pulsado!");
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
        //System.out.println(" [PanelControl] Boton AutoSave pulsado!");
        btnAutosavepulsado(evt);
        ((Button)evt.getSource()).setStyle("-fx-background-color: yellow; -fx-border-color: #063970; -fx-border-radius: 10; -fx-border-width: 3");

    }
    @FXML
    private void btnAutosaveReleased(Event evt) {
        ((Button)evt.getSource()).setStyle("-fx-background-color: transparent; -fx-border-color: #063970; -fx-border-radius: 10; -fx-border-width: 3"); 
    }

    @FXML
    private void toggleModopulsado(Event evt) {
        //System.out.println(" [PanelControl] Boton MODO pulsado!");
        if (((ToggleButton)(evt.getSource())).isSelected()){
            toggleModo.setText("MODO INGR");
            toggleModo.setStyle("-fx-background-color: yellow; -fx-border-color: #063970; -fx-border-radius: 10; -fx-border-width: 3");
            //System.out.println( " [PanelControl] modo: INGR");
            modo = Controlador.INGR;
        }
        else if (!((ToggleButton)(evt.getSource())).isSelected()){
            toggleModo.setText("MODO NAV");
            toggleModo.setStyle("-fx-background-color: transparent; -fx-border-color: #063970; -fx-border-radius: 10; -fx-border-width: 3"); 
            //System.out.println(" [PanelControl] modo: NAV");
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
//#endregion

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

//#region RUN_CFCT_PREV
    /*public synchronized ControladorFacturas arrancarCfct() throws InterruptedException, BrokenBarrierException {
        
        Controlador.cfct = Controlador.getControladorFacturas();
        Controlador.cfct.setName("Contr_FCT");
        Controlador.cfct.start();

        return Controlador.cfct;
    }*/
//#endregion

//#region HELPERS
    public void mostrar() {
        GUIpanel.show();
    }
//#endregion
}

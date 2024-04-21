package controladores.fxcontrollers;

import modelo.ModeloFacturas;
import modelo.base.Config;
import controladores.Controlador;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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

    public static Stage ventanaPCtl;

    public static Scene escena1;

    static Image icon;
//    static TrayIcon trayIcon;
    static Popup popMenu;
    
    @FXML private Button btnFCT;
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
    public static PanelControl instancia;
    public static int modo;
    public Config configActual;
    public String usuarioActual;
    //TODO: 12-04-2024 - ¿Porqué no puede seguir siendo un Singleton?
    //TODO: 12-04-2024 - Hay que definir un usuariActual, y una configActual
    public PanelControl() throws IOException {
        this.usuarioActual = Controlador.usuario;
        PanelControl.modo = Controlador.NAV;
        this.configActual = Config.getConfig(usuarioActual).configActual;

  }
//#region INITIALIZE
    @FXML
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO: 09/04/24 - Crear un Controlador general, y decidir cómo abrirá las tablas, etc...
        // TODO: 09/04/24 - Crear un controladorFicheros en un nuevo hilo para que gestione el Guardado Automático
        // TODO: Cambiar el diseño de los ToggleButton al pulsarse y el mensaje que arrojan
        // TODO: Arreglar la inicialización de la GUI del PanelControl... No funciona
        // TODO: 12-04-2024 - Lo dejo aquí (19:13H)
        setAño((Integer)Config.getConfig(usuarioActual).año.año());
        setTrimestre(Config.getConfig(usuarioActual).año.trimestre());
        setNumfacturas(ModeloFacturas.getNumeroFacturas());
        setUsuario(Config.getConfig(usuarioActual).getUsuario().toLowerCase());
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
        System.out.println("Boton CFG pulsado!");
        botonactivo = 4;
        botonpulsado = true;
    }
    @FXML
    private void btnNTSpulsado(Event evt) {
        System.out.println("Boton NTS pulsado!");
        botonactivo = 3;
        botonpulsado = true;

    }
    @FXML
    private void btnRSpulsado(Event evt) {
        System.out.println("Boton DIST pulsado!");        
        botonactivo = 2;
        botonpulsado = true;

    }
    @FXML
    private void btnFCTpulsado(Event evt) {
        System.out.println("Boton FCT pulsado!");
        botonactivo = 1;
        botonpulsado = true;
    }
/*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing
*/
    @FXML
    private void btnCJApulsado(Event evt) {
        System.out.println("Boton CJA pulsado!");
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
        System.out.println("Boton AutoSave pulsado!");
        btnAutosavepulsado(evt);
        ((Button)evt.getSource()).setStyle("-fx-background-color: yellow; -fx-border-color: #063970; -fx-border-radius: 10; -fx-border-width: 3");

    }
    @FXML
    private void btnAutosaveReleased(Event evt) {
     
        ((Button)evt.getSource()).setStyle("-fx-background-color: transparent; -fx-border-color: #063970; -fx-border-radius: 10; -fx-border-width: 3"); 

    }  
    @FXML
    private void toggleModopulsado(Event evt) {
        System.out.println("Boton MODO pulsado!");
        if (((ToggleButton)(evt.getSource())).isSelected()){
            toggleModo.setText("MODO INGR");
            toggleModo.setStyle("-fx-background-color: yellow; -fx-border-color: #063970; -fx-border-radius: 10; -fx-border-width: 3");
            System.out.println("modo: INGR");
            modo = Controlador.INGR;
        }
        else if (!((ToggleButton)(evt.getSource())).isSelected()){
            toggleModo.setText("MODO NAV");
            toggleModo.setStyle("-fx-background-color: transparent; -fx-border-color: #063970; -fx-border-radius: 10; -fx-border-width: 3"); 
            System.out.println("modo: NAV");
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
    public static void reset(){
        botonpulsado = false;
//        PanelControl.getPanelControl().setAlwaysOnTop(true);
//        PanelControl.getPanelControl().toFront();
//        PanelControl.getPanelControl().repaint();
    }
//#region GET_PC()
    public static PanelControl getPanelControl() throws IOException{
        if (instancia == null)
            instancia = new PanelControl();
         return instancia;
    }
//#endregion
}

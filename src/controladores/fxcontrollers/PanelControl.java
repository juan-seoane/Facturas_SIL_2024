package controladores.fxcontrollers;

import modelo.ModeloFacturas;
import modelo.records.Config;
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
    
    public PanelControl() throws IOException {

        PanelControl.modo = Controlador.NAV;

  }

    @FXML
    public void initialize(URL arg0, ResourceBundle arg1) {

        // TODO: Cambiar el diseño de los ToggleButton al pulsarse y el mensaje que arrojan
        // TODO: Arreglar la inicialización de la GUI del PanelControl... No funciona
        setAño((Integer)Config.getConfig().getAnho().getAnho());
        setTrimestre(Config.getConfig().getAnho().getTrimestre());
        setNumfacturas(ModeloFacturas.getNumeroFacturas());
        setUsuario(Config.getConfig().getUsuario().toLowerCase());
    }

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
    private void btnCFGpulsado(Event evt) {//GEN-FIRST:event_btnCFGActionPerformed
        System.out.println("Boton CFG pulsado!");
        botonactivo = 4;
        botonpulsado = true;
    }
    @FXML
    private void btnNTSpulsado(Event evt) {//GEN-FIRST:event_btnNTSActionPerformed
        System.out.println("Boton NTS pulsado!");
        botonactivo = 3;
        botonpulsado = true;

    }
    @FXML
    private void btnRSpulsado(Event evt) {//GEN-FIRST:event_btnRSActionPerformed
       
        System.out.println("Boton DIST pulsado!");        botonactivo = 2;
        botonpulsado = true;

    }
    @FXML
    private void btnFCTpulsado(Event evt) {//GEN-FIRST:event_btnFCTActionPerformed
        System.out.println("Boton FCT pulsado!");
        botonactivo = 1;
        botonpulsado = true;
    }
/*/
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing
*/
    @FXML
    private void btnCJApulsado(Event evt) {//GEN-FIRST:event_btnCJAActionPerformed
        System.out.println("Boton CJA pulsado!");
        botonactivo = 5;
        botonpulsado = true;
    }
    @FXML
    private void btnAutosavepulsado(Event evt) {//GEN-FIRST:event_btnautosaveActionPerformed
     
        botonactivo = 6;
        botonpulsado = true;
    }
    @FXML
    private void btnAutosavePressed(Event evt) {//GEN-FIRST:event_btnautosaveActionPerformed
        System.out.println("Boton AutoSave pulsado!");
        btnAutosavepulsado(evt);
        ((Button)evt.getSource()).setStyle("-fx-background-color: yellow; -fx-border-color: #063970; -fx-border-radius: 10; -fx-border-width: 3");

    }
    @FXML
    private void btnAutosaveReleased(Event evt) {//GEN-FIRST:event_btnautosaveActionPerformed
     
        ((Button)evt.getSource()).setStyle("-fx-background-color: transparent; -fx-border-color: #063970; -fx-border-radius: 10; -fx-border-width: 3"); 

    }  
    @FXML
    private void toggleModopulsado(Event evt) {//GEN-FIRST:event_toggleModoActionPerformed
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
    public static PanelControl getPanelControl() throws IOException{
        if (instancia == null)
            instancia = new PanelControl();
         return instancia;
    }
    
    /**
     * @param args the command line arguments
     */

    private Scene crearEscena1() throws IOException{
        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(getClass().getResource("../../ui/fxviews/PanelControl.fxml"));
        
       
        Parent root1 = loader1.load();
        Scene scene1 = new Scene(root1);
        //scene.getStylesheets().add(getClass().getResource("acceso.css").toExternalForm());

        
        return scene1;
    }   
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores.fxcontrollers;

import controladores.Controlador;

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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    @FXML private ToggleButton btnAutosave;
    @FXML private ToggleButton toggleModo;
    
    @FXML private static Label lblEntradas;
    @FXML private static Label lblTrimestre;
    @FXML private static Label lblAnho;
    @FXML private static Label lblUsuario;

    private static boolean botonpulsado = false;
    private static int botonactivo = 1;
    public static PanelControl instancia;
    public static int modo;
    
    public PanelControl() throws IOException {

//        this.toFront();
//        this.setAlwaysOnTop(true);
//        this.setAutoRequestFocus(false);

        PanelControl.modo = Controlador.NAV;

  }

    public static void setNumfacturas(int i){
        PanelControl.lblEntradas.setText(i +"");
    }
    public static void setAño(int i){
        PanelControl.lblAnho.setText(i+"");
    }
    public static void setTrimestre(int i){
        PanelControl.lblTrimestre.setText(i+"");
    }
    public static void setUsuario(String user){
       PanelControl.lblUsuario.setText(user +"");
    }
    
    public static int getModo(){
        return PanelControl.modo;
    }
    @FXML    
    private void btnCFGpulsado(Event evt) {//GEN-FIRST:event_btnCFGActionPerformed
        botonactivo = 4;
        botonpulsado = true;
    }
    @FXML
    private void btnNTSpulsado(Event evt) {//GEN-FIRST:event_btnNTSActionPerformed
        botonactivo = 3;
        botonpulsado = true;

    }
    @FXML
    private void btnRSpulsado(Event evt) {//GEN-FIRST:event_btnRSActionPerformed
        botonactivo = 2;
        botonpulsado = true;

    }
    @FXML
    private void btnFCTpulsado(Event evt) {//GEN-FIRST:event_btnFCTActionPerformed
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
        botonactivo = 5;
        botonpulsado = true;
    }
    @FXML
    private void btnAutosavepulsado(Event evt) {//GEN-FIRST:event_btnautosaveActionPerformed
        botonactivo = 6;
        botonpulsado = true;
    }
    @FXML
    private void toggleModopulsado(Event evt) {//GEN-FIRST:event_toggleModoActionPerformed

        if (((ToggleButton)(evt.getSource())).isSelected()){
            toggleModo.setText("MODO INGR");
            toggleModo.setStyle("-fx-background-color: yellow");//240,200,0
            modo = Controlador.INGR;
        }
        else if (!((ToggleButton)(evt.getSource())).isSelected()){
            toggleModo.setText("MODO NAV");
            toggleModo.setStyle("-fx-background-color: transparent"); //240,240,240
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
    
    @FXML
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO: Cambiar el diseño de los ToggleButton al pulsarse y el mensaje que arrojan
        // TODO: Arreglar la inicialización de la GUI del PanelControl... No funciona
      // PanelControl.setAño(Config.getConfig().getAnho().getAnho());
      // PanelControl.setTrimestre(Config.getConfig().getAnho().getTrimestre());
      // PanelControl.setNumfacturas(ModeloFacturas.getNumeroFacturas());
      // PanelControl.setUsuario(Config.getConfig().getUsuario());
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.fxcontrollers;

import controladores.Controlador;
import modelo.Config;
import modelo.ModeloFacturas;

import javafx.application.Application;
import javafx.application.Platform;
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
public class PanelControl extends Application implements Initializable{

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
    
    public PanelControl() {
//        initComponents();
//        startup();

//        this.toFront();
//        this.setAlwaysOnTop(true);
//        this.setAutoRequestFocus(false);
        PanelControl.modo = Controlador.NAV;
    }
/*
    private void initComponents() {

        btnFCT = new javax.swing.JButton();
        btnRS = new javax.swing.JButton();
        btnNTS = new javax.swing.JButton();
        btnCFG = new javax.swing.JButton();
        btnCJA = new javax.swing.JButton();
        
        lblEntradas = new javax.swing.JLabel();
        lblTrimestre = new javax.swing.JLabel();
        lblAnho = new javax.swing.JLabel();

        btnautosave = new javax.swing.JButton();
        toggleModo = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Panel Control");
        setAlwaysOnTop(true);
        setIconImage(icon);
        setIconImages(startup());
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
//TODO: Esto mismo para todos los botones del PnlCtrl
        //btnFCT.setText("FCT");
//        btnFCT.setToolTipText("<html>Entra en el MODO FACTURAS</html>");
        btnFCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFCTActionPerformed(evt);
            }
        });

        jLabel1.setText("entradas");

        lblEntradas.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblEntradas.setText("999");

        jLabel4.setText("trimestre");

        lblTrimestre.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblTrimestre.setText("1");
        lblTrimestre.setToolTipText("<html>Muestra el trimestre actual<br/>\n0 significa año completo (sin trimestres)<br/>\nPuede cambiarlo en la VENTANA DE CONFIGURACION</html>");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("AÑO");

        lblAnho.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblAnho.setText("2013");
        lblAnho.setToolTipText("<html>Muestra el año actual<br/>\nPuede cambiarlo en la VENTANA DE CONFIGURACION</html>");

        btnautosave.setText("COPIA AUTO");
        btnautosave.setToolTipText("<html>Use este boton para generar una<br/>\nCOPIA DE SEGURIDAD AUTOMATICA<br/>\nde todos sus ficheros actuales<br/></html>");
        btnautosave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnautosaveActionPerformed(evt);
            }
        });

        toggleModo.setText("MODO NAV");
        toggleModo.setToolTipText("<html>Use este boton para cambiar<br/>\n entre el modo de NAVEGACION<br/> \npor los datos y el modo de<br/> \nINGRESO de facturas<html>");
        toggleModo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleModoActionPerformed(evt);
            }
        });

    }
    
    private List<Image> startup(){
        
//        popMenu = new Popup();
//        MenuItem item1 = new MenuItem("Exit");
//        popMenu.add(item1);
        
//        icon = Toolkit.getDefaultToolkit().getImage("imagenes/tray.png");
        //setIconImage(icon);
        List<Image> listaIconos = new ArrayList<Image>();
        listaIconos.add(icon);
        
//        trayIcon = new TrayIcon(icon, "Facturas SIL v2.0", popMenu);
               
        if (SystemTray.isSupported()) {
          SystemTray tray = SystemTray.getSystemTray();

          trayIcon.setImageAutoSize(true);
          trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              trayIcon.displayMessage("Tester!", "Some action performed", TrayIcon.MessageType.INFO);
            }
          });

          try {
            tray.add(trayIcon);
          } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
          }
        }else
          System.out.println("TrayIcon could not be added. SystemTray not supported");
        }
        return listaIconos;
    }
*/
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
    public static PanelControl getPanelControl(){
        if (instancia == null)
            instancia = new PanelControl();
         return instancia;
    }
    
    /**
     * @param args the command line arguments
     */

    private Scene crearEscena1() throws IOException{
        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(getClass().getResource("../fxviews/PanelControl.fxml"));
        
       
        Parent root1 = loader1.load();
        Scene scene1 = new Scene(root1);
        //scene.getStylesheets().add(getClass().getResource("acceso.css").toExternalForm());

        
        return scene1;
    }
    @Override
    public void start(Stage primaryStage) throws IOException{
//TODO: Completar el método start() dentro del controlador de la GUI de JFX PanelControl.java

    PanelControl.ventanaPCtl = primaryStage;
    PanelControl.ventanaPCtl.setTitle("Panel de Control");

    // prevent automatic exit of application when last window is closed
    Platform.setImplicitExit(false);

    PanelControl.escena1 = crearEscena1();
    PanelControl.ventanaPCtl.setScene(PanelControl.escena1);

    //PanelControl.ventanaPCtl.initModality(Modality.APPLICATION_MODAL);
    PanelControl.ventanaPCtl.setResizable(false);
    PanelControl.ventanaPCtl.show();
    }

    @FXML
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO: Completar la inicialización de la GUI del PanelControl
       PanelControl.setAño(Config.getConfig().getAnho().getAnho());
       PanelControl.setTrimestre(Config.getConfig().getAnho().getTrimestre());
       PanelControl.setNumfacturas(ModeloFacturas.getNumeroFacturas());
       PanelControl.setUsuario(Config.getConfig().getUsuario());
    }
}

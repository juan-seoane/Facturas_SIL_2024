package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

public class AccesoJFX extends Application implements Initializable{
 
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnOK;
    @FXML private TextArea txtArea;
    
    
    @FXML public static boolean aceptado = false;

    public Stage primaryStage;
    public String usuario;
    
    @FXML 
    public void probar(ActionEvent event) throws InterruptedException{
        TextField userF = txtUsuario;
        PasswordField passF = txtPassword;
        TextArea vent = txtArea;
        for (Contrasenha contr : Config.getConfig("ADMIN").getContrasenhas()){
            System.out.println("Datos introducidos : "+ userF.getText()+ " - "+passF.getText());
            vent.appendText("\nDatos introducidos : "+ userF.getText()+ " - "+passF.getText());
                //System.out.println("con la contraseña guardada : "+ pass.getUsuario() + " - " +pass.getContrasenha().toUpperCase());
            if (userF.getText().toUpperCase().trim().equals(contr.getUsuario()) && (passF.getText().toUpperCase().equals(contr.getContrasenha())))
            {
               
                Acceso.aceptado = true;
                
                vent.setText("...Entrando");
            
                System.out.println("OK, entrando...");

            }else{
                //puentear el acceso
                //Acceso.aceptado = true;
                //System.out.println("Usuario y Contraseña incorrectos...pero pase...");
                
                //txtArea.appendText("\nDatos incorrectas...pero pase...!");

                System.out.println("Usuario y Contraseña incorrectos... Repita, por favor");
                txtArea.appendText("\nDatos incorrectos...Por favor vuelva a intentarlo...");
                reintentar();
            
            }
            
            if (!Acceso.aceptado){
                    txtUsuario.setText(" ");
                    txtPassword.setText(" ");
                    userF.requestFocus();
                    vent.appendText("Usuario o Contraseña incorrectos,\nvuelva a intentarlo!");
            }else{
                    
                    this.usuario = txtUsuario.getText();
                    txtArea.appendText("\n\n...Pulse una tecla para continuar");
                    //TODO: Pedir el foco y esperar el evento de pulsar una tecla
                    System.out.println("El acceso fue aceptado después de probar() ...");
                    //Thread.sleep(2000);
                
                //System.exit(0); 
            }
        }
    }

private void reintentar() {
    this.txtUsuario.setText(" ");
    this.txtPassword.setText(" ");
    this.txtUsuario.requestFocus();
    }

    public static boolean entrar(){
        if (AccesoJFX.aceptado){
            System.out.println("Acceso aceptado");
        }
        return AccesoJFX.aceptado;
    }


    //TODO: Lo de abajo sólo funciona si se implementa el Interfaz "Inicializable" (implements Initilizable)
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        
        txtArea.setText("...introduzca sus credenciales");
    }
// De la clase de la que hereda, Application
    public static void main() {
        Application.launch();
    }


    public String getUsuario() {
        return this.usuario;
    }
    
    @FXML 
    public void cerrar(){
        Stage stage = (Stage) this.txtArea.getScene().getWindow();
        stage.close();
    }


    public void imprimir(String cont) {
        this.txtArea.appendText("\n"+cont);
    }

    @Override
    public void start(Stage arg0) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Acceso.fxml"));
        
        try {
            Parent root;
            root = loader.load();
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass().getResource("acceso.css").toExternalForm());
            Stage ventanaAcceso = new Stage();
            ventanaAcceso.setScene(scene);
            ventanaAcceso.setTitle("Acceso a FacturasSIL");
            ventanaAcceso.show();
        } catch (IOException e) {
            System.out.println("Error al generar la GUI de Acceso");
            e.printStackTrace();
        }   
    }
}
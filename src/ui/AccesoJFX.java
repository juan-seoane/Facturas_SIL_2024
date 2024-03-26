
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

public class Acceso extends Application implements Initializable{
 
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnOK;
    @FXML private TextArea txtArea;
    
    
    @FXML public static boolean aceptado = false;
 
    @Override
    public void start(Stage primaryStage) throws Exception{
        
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("./views/Acceso.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass().getResource("acceso.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Proceso de Autenticación");
            
            primaryStage.show();
           
        
    }
    

    @FXML 
    public void probar(ActionEvent event) throws InterruptedException{
        TextField user = txtUsuario;
        PasswordField pass = txtPassword;
        TextArea vent = txtArea;
           //for (Contrasenha pass : Config.getConfig("ADMIN").getContrasenhas()){
           System.out.println("Datos introducidos : "+ user.getText()+ " - "+pass.getText());
           vent.appendText("\nDatos introducidos : "+ user.getText()+ " - "+pass.getText());
            //System.out.println("con la contraseña guardada : "+ pass.getUsuario() + " - " +pass.getContrasenha().toUpperCase());
                if (user.getText().toUpperCase().trim().equals("ADMIN") && 
                        (pass.getText().toUpperCase().equals("ADMIN"))){
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
                user.requestFocus();
                vent.appendText("Usuario o Contraseña incorrectos,\nvuelva a intentarlo!");
           }else{
                
                txtArea.appendText("\n\n...Pulse una tecla para continuar");
                //TODO: Pedir el foco y esperar el evento de pulsar una tecla
                System.out.println("El acceso fue aceptado después de probar() ...");
                //Thread.sleep(2000);
               
               //System.exit(0); 
           }
        }

private void reintentar() {
    this.txtUsuario.setText(" ");
    this.txtPassword.setText(" ");
    this.txtUsuario.requestFocus();
    }


    //TODO: Lo de abajo sólo funciona si se implementa el Interfaz "Inicializable" (implements Initilizable)
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        
        txtArea.setText("...introduzca sus credenciales");
    }
// De la clase de la que hereda, Application
    public static void main(String[] args) {
        Application.launch(args);
    }

    }
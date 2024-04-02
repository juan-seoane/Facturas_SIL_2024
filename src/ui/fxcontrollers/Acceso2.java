package ui.fxcontrollers;

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
import javafx.stage.Modality;
import javafx.stage.Stage;

import modelo.Config;
import modelo.Contrasenha;

public class Acceso2 extends Application implements Initializable{
 
    @FXML private Button btnOK;
    @FXML public TextArea txtArea;
    

    public static TextArea canvasAcceso;

    public Stage primaryStage;
    public static String usuario;
    public static int intentos = 1;
    




    //TODO: Lo de abajo sólo funciona si se implementa el Interfaz "Inicializable" (implements Initilizable)
    @FXML
    public void initialize(URL location, ResourceBundle resources) {      
        this.txtArea.setText("...introduzca su usuario y contraseña");
        canvasAcceso = this.txtArea;
    }    
    // De la clase de la que hereda, Application
    public static void main() {
        Application.launch();
    }
    
    public static void setUsuario(String user){
        Acceso2.usuario = user;
    }

    public static String getUsuario() {
        return Acceso2.usuario;
    }

    public static TextArea getCanvas(){   
        return Acceso.canvasAcceso;
    }

    @FXML 
    public void cerrar(){
        
        Stage stage = (Stage) this.txtArea.getScene().getWindow();
        stage.close();
    }

    public static void imprimir(TextArea tA, String cont) {
        tA.appendText("\n"+cont);
    }

    @Override
    public void start(Stage arg0) {

        //Path path = Paths.get("");
        //String directoryName = path.toAbsolutePath().toString();
        //System.out.println("Current Working Directory at Acceso is = " + directoryName);

        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(getClass().getResource("../fxviews/Acceso2.fxml"));
        
        try {
            Parent root2; //= new AnchorPane();
            
            root2 = loader2.load();
            Scene scene2 = new Scene(root2);
            //scene.getStylesheets().add(getClass().getResource("acceso.css").toExternalForm());
            Stage ventanaAcceso2 = new Stage();
            ventanaAcceso2.setScene(scene2);
            ventanaAcceso2.initModality(Modality.APPLICATION_MODAL);
            ventanaAcceso2.setResizable(true);
            ventanaAcceso2.setTitle("Acceso a FacturasSIL");
            ventanaAcceso2.show();
        } catch (IOException e) {
            System.out.println("Error al generar la GUI de Acceso");
            e.printStackTrace();
        }   
    }
}
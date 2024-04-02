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

public class Acceso extends Application implements Initializable{
 
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnOK;
    @FXML public TextArea txtArea;
    
    
    @FXML public static boolean aceptado = false;

    public static TextArea canvasAcceso;
    
    public Stage ventanaAcceso;

    public static String usuario ="";
    public static int intentos = 1;
    
    @FXML 
    public void probar(ActionEvent event) throws InterruptedException{
        TextField userF = this.txtUsuario;
        PasswordField passF = this.txtPassword;
        System.out.println("[Acceso.java>probar()]Datos introducidos : "+ userF.getText()+ " - "+passF.getText());

        System.out.println("[Acceso.java>probar()]Contenido del Área de Texto: "+ this.txtArea.getText());
        //Acceso.imprimir(this.txtArea, "texto introducido : "+ userF.getText() + " - " +passF.getText()+" - intentos: "+ intentos);
        //System.out.println("texto introducido : "+ userF.getText() + " - " +passF.getText());
        for (Contrasenha contr : Config.getConfig("ADMIN").getContrasenhas()){
            
            System.out.println("[Acceso.java>probar()>for(contraseñas)]Datos a imprimir en el textArea : "+ userF.getText()+ " - "+passF.getText()+" - textArea !=null : "+(this.txtArea!=null));

            //Acceso.imprimir( this.txtArea, "\nDatos introducidos : "+ userF.getText()+ " - "+passF.getText());
                
            if ((intentos<5)&&(userF.getText().toUpperCase().trim().equals(contr.getUsuario().toUpperCase()) && (passF.getText().toUpperCase().trim().equals(contr.getContrasenha().toUpperCase()))))
            {
               
                Acceso.aceptado = true;
                
                Acceso.imprimir(this.txtArea, "\n[Acceso.java: intentos<5 y cred OK]...Ok...Entrando!");
            
                System.out.println("[Acceso.java]...OK, entrando...");
                cerrar();

            }else if(intentos<5){
                //puentear el acceso
                //Acceso.aceptado = true;
                //System.out.println("Usuario y Contraseña incorrectos...pero pase...");
                //txtArea.appendText("\nDatos incorrectas...pero pase...!");

                System.out.println("[Acceso.java: intentos<5 y cred NO]Usuario y Contraseña incorrectos... Repita, por favor");
                Acceso.imprimir(this.txtArea, "\n[Acceso.java: intentos<5 y cred NO] Datos incorrectos: " + this.txtUsuario.getText() +" - " + this.txtPassword.getText() + "\n...Por favor vuelva a intentarlo... (intentos: "+intentos+")");
                intentos++;
                reintentar();
            
            }
            else{
                
                System.out.println("[Acceso.java: intentos>=5 y cred NO] El proceso de Autenticación ha fallado!");
                System.out.println("[Acceso.java] El programa se cerrará!");

                Acceso.imprimir(this.txtArea, "[Acceso.java: intentos>=5 y cred NO] El proceso de Autenticación ha fallado!");
                Acceso.imprimir(this.txtArea, "\nEl programa se cerrará!");
                System.exit(0); 
                            
            }
            

            if (Acceso.aceptado){

                this.usuario = txtUsuario.getText();
                Acceso.imprimir(this.txtArea, "\n[Acceso.java] Bienvenido, " + this.usuario);
                //System.out.println("\n...Pulse una tecla para continuar");
                //TODO: Pedir el foco y esperar el evento de pulsar una tecla
                System.out.println("[Acceso.java] El acceso fue aceptado después de probar() ...");
                //Thread.sleep(2000);
                //TODO: Hacer el TextArea autoscrollable
                //TODO: La ventana mostrará el desarrollo de la carga de controladores y al final se cerrará
                //Main.usuario = this.usuario;        
                
            }
        }
    }

    private void reintentar() {
        this.txtUsuario.clear();
        this.txtPassword.clear();
        this.txtUsuario.requestFocus();
    }

    public boolean entrar(){
        if(Acceso.aceptado){
            cargarAcceso2();
            return true;
        }
        return false;
    }

    //TODO: Lo de abajo sólo funciona si se implementa el Interfaz "Inicializable" (implements Initilizable)
    @FXML
    public void initialize(URL location, ResourceBundle resources) {      
        this.txtArea.setText("...introduzca su usuario y contraseña");
        canvasAcceso = this.txtArea;
    }    
    // De la clase de la que hereda, Application
 //   public static void main() {
 //       Application.launch();
 //   }


    public static String getUsuario() {
        return Acceso.usuario;
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

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxviews/Acceso.fxml"));
        
        try {
            Parent root; //= new AnchorPane();
            
            root = loader.load();
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass().getResource("acceso.css").toExternalForm());
            ventanaAcceso = new Stage();
            ventanaAcceso.setScene(scene);
            ventanaAcceso.initModality(Modality.APPLICATION_MODAL);
            ventanaAcceso.setResizable(true);
            ventanaAcceso.setTitle("Acceso a FacturasSIL");
            ventanaAcceso.show();
        } catch (IOException e) {
            System.out.println("Error al generar la GUI de Acceso");
            e.printStackTrace();
        }   
    }

    public void cargarAcceso2() {
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("../fxviews/Acceso2.fxml"));
        this.ventanaAcceso.hide();
          
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
package tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.internal.MethodSorter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import static org.testfx.api.FxAssert.verifyThat;

import controladores.Controlador;
import controladores.ControladorFacturas;
import controladores.fxcontrollers.Acceso;
import controladores.fxcontrollers.FxCntrlTablaFCT;
import controladores.fxcontrollers.PanelControl;
import controladores.helpers.FxmlHelper;
import modelo.ModeloFacturas;
import modelo.base.Config;
import modelo.records.Factura;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FxApplicationTest extends ApplicationTest{

	public String usuario = "admin";
	public static Config config;
	public static Controlador ctrlPpal;
	public static ControladorFacturas ctrlFct;
	public static ModeloFacturas modeloFCT;
	public static PanelControl pc;
	public Stage login;
	public Stage tablaFCT;
	public Stage PCgui;
	public FxCntrlTablaFCT ctrlFxFct;
	public TableView<Factura> modTablaFCT; 
//Lo ejecuta antes de todo el conjunto de Tests
	@BeforeAll
	public static void setUp() throws Exception {

		System.out.println("[FxApplicationTest>setUp]******INICIO*****");

		ApplicationTest.launch(Acceso.class, "");

		System.out.println("[FxApplicationTest>setUp]******FINAL*****");
	
	}
/*
//Lo ejecuta antes de cada Test individual
	@Override
    public void start(Stage stage) {

		System.out.println("[FxApplicationTest>start]******INICIO*****");

		this.login = stage;
        this.login.show();

		System.out.println("[FxApplicationTest>start]******FINAL*****");
    }
*/
	@Test
	@Order(1)
	public void loginAdminOK(){

		System.out.println("[FxApplicationTest>loginAdminOK]******INICIO*****");

		//var ok = find("#txtUsuario");
		//System.out.println("[FxApplicationTest] txtUsuario existe: " + ok);

		
		moveTo("#txtUsuario");
		//clickOn("#txtUsuario)");
		type(KeyCode.A,KeyCode.D,KeyCode.M,KeyCode.I,KeyCode.N);
		moveTo("#txtPassword");
		clickOn("#txtPassword");
		type(KeyCode.A,KeyCode.D,KeyCode.M,KeyCode.I,KeyCode.N);
		moveTo("#btnOK");
		clickOn("#btnOK");
		clickOn("#txtArea");
	
		verifyThat("#txtArea", (TextArea textArea) -> {
			String text = textArea.getText();
			return (text.contains("admin - admin"));
		});
		System.out.println("[FxApplicationTest>loginAdminOK]******FINAL*****");

	}
	
	@Test
	@Order(2)
	public void pcCargaOK() throws NullPointerException, IOException, InterruptedException{
		
		System.out.println("[FxApplicationTest>PCcargaOK]******INICIO*****");
		System.out.println("[FxApplicationTest>PCcargaOK] Esperando a cargar el Panel de Control\r");

		do{
			System.out.print("[FxApplicationTest>PCcargaOK] Esperando a cargar el Panel Control\r");
		}while((ctrlFct=ControladorFacturas.getControlador())==null);
		ctrlPpal = Controlador.getControlador();
		config = Config.getConfig(usuario);
		Platform.runLater(new Runnable(){
			@Override
			public void run(){
				var arrayFxml = cargarFXML("../../resources/PanelControl.fxml");
				Scene escenaPC = setEscena((Parent)arrayFxml[0]);
				Stage stage =new Stage();
				stage.setScene(escenaPC);
				stage.setAlwaysOnTop(true);
				stage.show();
			}
		});
		assertNotNull(ctrlPpal);
		assertNotNull(config);
		System.out.println("[FxApplicationTest>PCcargaOK]******FINAL*****");
	}
	@Test
	@Order(3)
	public void pcFunciona() throws InterruptedException, NullPointerException, IOException{
		System.out.println("[FxApplicationTest>pcFunciona]******INICIO*****");
		do{
			System.out.print("[FxApplicationTest>pcFunciona] Esperando a cargar el Panel Control\r");
		}while((ctrlFct=ControladorFacturas.getControlador())==null);
		ctrlPpal = Controlador.getControlador();
		config = Config.getConfig(usuario);
		Platform.runLater(new Runnable(){
			@Override
			public void run(){
				var arrayFxml = cargarFXML("../../resources/PanelControl.fxml");
				Scene escenaPC = setEscena((Parent)arrayFxml[0]);
				Stage stage =new Stage();
				stage.setScene(escenaPC);
				stage.setAlwaysOnTop(true);
				stage.show();
			}
		});
		try {
			Thread.sleep(1000);
			clickOn("#btnFCT");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
		verifyThat("#btnFCT", (ToggleButton tbtn) -> {
			boolean act = tbtn.isSelected();
			return act;
		});

		//assertNotNull(this.PCgui);
		System.out.println("[FxApplicationTest>pcFunciona]******FINAL*****");
		Thread.sleep(2000);

	}

	@Test
	@Order(4)
	public void tablaFCTcargaOK() throws InterruptedException, NullPointerException, IOException {

		System.out.println("[FxApplicationTest>tablaFCTcargaOK]******INICIO*****");
		System.out.println("[FxApplicationTest>tablaFCTcargaOK] Esperando a cargar la TablaFCT\r");
		
		Platform.runLater(new Runnable(){
			@Override
			public void run(){
				var arrayFxml = cargarFXML("../../resources/fxmltablaFCT.fxml");
				Scene escenaTFCT = setEscena((Parent)arrayFxml[0]);
				showStage(escenaTFCT);
			}
		});
		config = Config.getConfig("admin");
		ctrlPpal = Controlador.getControlador();
		ctrlFct = ControladorFacturas.getControlador();
		modeloFCT = ModeloFacturas.getModelo();
		//do{
		//	pc = Controlador.pc;
		//}while (pc==null);

		assertNotNull(ctrlPpal);
		assertNotNull(config);
		assertNotNull(ctrlFct);
		//assertNotNull(pc);
		assertNotNull(modeloFCT);
		
		var modeloFCT = ModeloFacturas.getModelo();
		var listaFXFCT = modeloFCT.getListaFXFacturas();
		this.ctrlFxFct = ctrlFct.getFXcontrlFCT();
		assertNotNull(listaFXFCT);
		assertNotNull(this.ctrlFxFct);
		assertTrue(listaFXFCT.get(0) instanceof Factura);
		this.ctrlFxFct.tblvwFct.setItems(listaFXFCT);
		this.ctrlFxFct.tblvwFct.refresh();


		
		System.out.println("[FxApplicationTest>tablaFCTcargaOK]******FINAL*****");
		Thread.sleep(2000);
	}
//Lo ejecuta despues de todo el conjunto de Tests
	@AfterAll
	public static void afterTests() throws TimeoutException, InterruptedException {

		Thread.sleep(2000);

		System.out.println("[FxApplicationTest>afterTests]******INICIO*****");

		FxToolkit.hideStage();
		/*
		release(new KeyCode[]{});
		release(new MouseButton[]{});
		*/
		System.out.println("[FxApplicationTest>afterTests]******FINAL*****");
	}

	public Object[] cargarFXML(String ruta){

        System.out.println("[TablaFCTtest>cargarFXML] ruta  : " + ruta);

		FxmlHelper Fxml = new FxmlHelper(ruta);

		Parent root;

        root = Fxml.cargarFXML();

		var contr = (Fxml.getFXcontr());
//Devuelve un array con 2 objetos: el Parent (root) y el controladorFX
		var array = new Object[2];
		array[0] = root;
		array[1] = contr;
		return array;
	}

	private Scene setEscena(Parent root) {
		
		Scene escena = new Scene(root);

		return escena;
	}

	private Stage showStage(Scene escena){
			
		Stage st = new Stage();

		st.setScene(escena);
		st.setResizable(false);
		st.initModality(Modality.NONE);
		st.show();
		return st;
	}
/*
	 @SuppressWarnings("unchecked")
	public <T extends Node> T find(final String fxid){
		return (T)(lookup(fxid).queryAll().iterator().next());
	 }
*/
}

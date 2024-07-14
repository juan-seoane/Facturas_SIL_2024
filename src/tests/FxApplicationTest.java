package tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.TimeoutException;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
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
import controladores.fxcontrollers.FxCntrlVisorFCT;
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
	private FxCntrlVisorFCT ctrlFxVisorFct;
	public static FxCntrlTablaFCT ctrlFxTablaFct;
	public static TableView<Factura> modTablaFCT;
	public static long hashCodePC;
	public static long hashCodeTabla;
	public static long hashCodeVisor;
//Lo ejecuta antes de todo el conjunto de Tests
	@BeforeAll
	public static void setUp() throws Exception {

		System.out.println("[FxApplicationTest>setUp]******INICIO*****");

		ApplicationTest.launch(Acceso.class, "");

		System.out.println("[FxApplicationTest>setUp]******FINAL*****");
	
	}

//Lo ejecuta antes de cada Test individual
/*	@Override
    public void start(Stage stage) {

		System.out.println("[FxApplicationTest>start]******INICIO*****");

		this.login = stage;
        this.login.show();

		System.out.println("[FxApplicationTest>start]******FINAL*****");
    }
*/
	@Test
	@Order(1)
	public void loginAdminOK() throws InterruptedException{

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
		Thread.sleep(2000);
		System.out.println("[FxApplicationTest>loginAdminOK]******FINAL*****");

	}
	
	@Test
	@Order(2)
	public void pcCargaOK() throws NullPointerException, IOException, InterruptedException, BrokenBarrierException{
		
		System.out.println("[FxApplicationTest>PCcargaOK]******INICIO*****");
		System.out.println("[FxApplicationTest>PCcargaOK] Esperando a cargar el Panel de Control\r");

		Platform.runLater(new Runnable(){
			@Override
			public void run(){
				try {
					config = Config.getConfig("admin");
					ctrlPpal = Controlador.getControlador();
					ctrlFct = Controlador.getControladorFacturas();
					pc = Controlador.getPanelControl();
					var arrayFxml = cargarFXML("../../resources/PanelControl.fxml");
					Scene escenaPC = setEscena((Parent)arrayFxml[0]);
					Stage GUIpc = showStage(escenaPC,true);
					PanelControl.setGUI(GUIpc);
					pc.mostrar();
				} catch (NullPointerException | IOException | InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
				hashCodePC = pc.hashCode();
				System.out.println("[FxApplicationTest>pcCargaOK] hashCodePC: " + hashCodePC);
				assertNotNull(pc);
				assertNotNull(ctrlFct);
				assertNotNull(ctrlPpal);
				assertNotNull(config);
				
				//pc.mostrar();
			}
		}); 
		Thread.sleep(2000);
		System.out.println("[FxApplicationTest>PCcargaOK]******FINAL*****");
	}

	@Test
	@Order(3)
	public void pcFunciona() throws InterruptedException, NullPointerException, IOException, BrokenBarrierException{
		System.out.println("[FxApplicationTest>pcFunciona]******INICIO*****");
		do{
			System.out.print("[FxApplicationTest>pcFunciona] Esperando a cargar el Panel Control\r");
		}while((ctrlFct=ControladorFacturas.getControlador())==null);
		ctrlPpal = Controlador.getControlador();
		config = Config.getConfig(usuario);
		Platform.runLater(new Runnable(){
			@Override
			public void run(){
				try {
					config = Config.getConfig("admin");
					ctrlPpal = Controlador.getControlador();
					
					pc = PanelControl.getPanelControl();
					assertEquals(hashCodePC,pc.hashCode());
					var arrayFxml = cargarFXML("../../resources/PanelControl.fxml");
					Scene escenaPC = setEscena((Parent)arrayFxml[0]);
					Stage GUIpc = showStage(escenaPC,true);
					PanelControl.setGUI(GUIpc);
					pc.mostrar();
					ctrlFct = Controlador.getControladorFacturas();
				} catch (NullPointerException | IOException | InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			}
		});
/*		try {
			Thread.sleep(2000);
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
*/		System.out.println("[FxApplicationTest>pcFunciona]******FINAL*****");
		Thread.sleep(2000);

	}

	@Test
	@Order(4)
	public void tablaFCTcarganOK() throws InterruptedException, NullPointerException, IOException, BrokenBarrierException {

		System.out.println("[FxApplicationTest>tablaFCTcargaOK]******INICIO*****");
		System.out.println("[FxApplicationTest>tablaFCTcargaOK] Esperando a cargar la TablaFCT\r");
		
		Platform.runLater(new Runnable(){
			@Override
			public void run(){
				try{
					var arrayFxml = cargarFXML("../../resources/fxmltablaFCT.fxml");
					Scene escenaTFCT = setEscena((Parent)arrayFxml[0]);
					showStage(escenaTFCT,false);
					hashCodeVisor = ctrlFct.getFXcontrlVisorFCT().getVisorFCT().hashCode();
				}catch (InterruptedException | BrokenBarrierException ex){
					ex.printStackTrace();
				}
			}
		});
		config = Config.getConfig("admin");
		ctrlPpal = Controlador.getControlador();
		ctrlFct = ControladorFacturas.getControlador();
		ctrlFxTablaFct = ctrlFct.getFXcontrlTablaFCT();

		
		System.out.println("[FxApplicationTest>tablaFCTcargaOK] hashCodeVisor: " + hashCodeVisor);
		modeloFCT = ModeloFacturas.getModelo();

		assertNotNull(ctrlPpal);
		assertNotNull(config);
		assertNotNull(ctrlFct);
		assertNotNull(pc);
		assertEquals(hashCodePC,pc.hashCode());
		assertNotNull(modeloFCT);
		var listaFXFCT = modeloFCT.getListaFXFacturas();
		//this.ctrlFxTablaFct = ctrlFct.getFXcontrlTablaFCT();
		//assertNotNull(this.ctrlFxTablaFct);
		assertTrue(listaFXFCT.get(0) instanceof Factura);
		//this.ctrlFxTablaFct.tblvwFct.setItems(listaFXFCT);
		//this.ctrlFxTablaFct.tblvwFct.refresh();
		
		Thread.sleep(4000);
		clickOn("#btnVisorFct");
		System.out.println("[FxApplicationTest>tablaFCTcargaOK] boton Visor pulsado!!");
		
		Thread.sleep(4000);
		System.out.println("[FxApplicationTest>tablaFCTcargaOK]******FINAL*****");
	}

	@Test
	@Order(5)
	public void visorFCTcarganOK() throws InterruptedException, NullPointerException, IOException, BrokenBarrierException {

		System.out.println("[FxApplicationTest>visorFCTcargaOK]******INICIO*****");
		System.out.println("[FxApplicationTest>visorFCTcargaOK] Esperando a cargar el visorFCT\r");
		
		Platform.runLater(new Runnable(){
			@Override
			public void run(){
				var arrayFxml = cargarFXML("../../resources/visorFormFCT.fxml");
				Scene escenaVFCT = setEscena((Parent)arrayFxml[0]);
				showStage(escenaVFCT,true);
			}
		});
		config = Config.getConfig("admin");
		ctrlPpal = Controlador.getControlador();
		ctrlFct = ControladorFacturas.getControlador();
		ctrlFxTablaFct = ctrlFct.getFXcontrlTablaFCT();
		modeloFCT = ModeloFacturas.getModelo();

		assertNotNull(ctrlPpal);
		assertNotNull(config);
		assertNotNull(ctrlFct);
		assertNotNull(pc);
		assertNotNull(modeloFCT);

		var listaFXFCT = modeloFCT.getListaFXFacturas();
		ctrlFxTablaFct = ctrlFct.getFXcontrlTablaFCT();
		ctrlFxVisorFct = ctrlFct.getFXcontrlVisorFCT();

		assertNotNull(ctrlFxTablaFct);
		assertNotNull(ctrlFxVisorFct);
		//NOTE - 13-07-24 : actualizamos el VisorFCT con la FacturaActual...
		int iact = FxCntrlTablaFCT.getIndiceActual();
		Factura fact = listaFXFCT.get(1);
		//NOTE - 13-07-24 : facturaActual que tenemos que actualizar manualmente
		ControladorFacturas.facturaActual = fact;
		//NOTE - 13-07-24 : comprobamos que no sean 'null'
		assertEquals(0,iact);
		assertNotNull(fact);
		ctrlFxVisorFct.actualizarDatosVisor(iact, fact);
		assertTrue(listaFXFCT.get(0) instanceof Factura);
		
		Thread.sleep(4000);
		verifyThat("#lblVID", (Label label) -> {
			String text = label.getText();
			return (text.contains("2"));
		});

		Thread.sleep(4000);
		clickOn("#btnVCerrar");
		System.out.println("[FxApplicationTest>visorFCTcargaOK] boton Cerrar VisorFCT pulsado!!");
		
		System.out.println("[FxApplicationTest>visorFCTcargaOK]******FINAL*****");
		Thread.sleep(2000);
	}
//Lo ejecuta despues de todo el conjunto de Tests
	@AfterAll
	public static void afterTests() throws InterruptedException, TimeoutException {

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

        System.out.println("[FxApplicationTest>cargarFXML] ruta  : " + ruta);

		FxmlHelper Fxml = new FxmlHelper(ruta);

		Parent root;

        root = Fxml.cargarFXML();

		var contr = (Fxml.getFXcontr());
		
//Devuelve un array con 2 objetos: el Parent (root) y el controladorFX
		var array = new Object[3];
		array[0] = root;
		array[1] = contr;

		return array;
	}

	private Scene setEscena(Parent root) {
		
		Scene escena = new Scene(root);

		return escena;
	}

	private Stage showStage(Scene escena, boolean AOT){
			
		Stage st = new Stage();

		st.setScene(escena);
		st.setResizable(false);
		st.initModality(Modality.NONE);
		st.setAlwaysOnTop(AOT);
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

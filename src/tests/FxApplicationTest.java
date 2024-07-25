package tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.TimeoutException;

import javafx.application.Platform;
import javafx.collections.ObservableList;
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
import controladores.fxcontrollers.SplashFX;
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
	public static FxCntrlVisorFCT ctrlFxVisorFct;
	public static FxCntrlTablaFCT ctrlFxTablaFct;
	public static TableView<Factura> modTablaFCT;
	public static long hashCodePC;
	public static long hashCodeTabla;
	public static long hashCodeVisor;
	public static ObservableList<Factura> listaFXFCT;
//Lo ejecuta antes de todo el conjunto de Tests
	@BeforeAll
	public static void setUp() throws Exception {

		System.out.println("[FxApplicationTest>setUp]******INICIO*****");

		ApplicationTest.launch(SplashFX.class, "");

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
	public void visorFCTCargaOK() throws InterruptedException{
//#region parte1:loginOK
		Thread.sleep(7000);
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
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		verifyThat("#txtArea", (TextArea textArea) -> {
			String text = textArea.getText();
			return (text.contains("Ok"));
		});
		clickOn("#btnOK");
		System.out.println("[FxApplicationTest>PCcargaOK] Esperando a cargar el Panel de Control\r");
		Thread.sleep(2000);
		System.out.println("[FxApplicationTest>loginAdminOK]******FINAL*****");
//#endregion
//#region parte2_PccargaOK
		System.out.println("[FxApplicationTest>PCcargaOK]******INICIO*****");
		config = Config.getConfig("admin");
		ctrlPpal = Controlador.getControlador();
		ctrlFct = Controlador.getControladorFacturas();
		pc = Controlador.getPanelControl();
		assertNotNull(pc);
		hashCodePC = pc.hashCode();
		System.out.println("[FxApplicationTest>pcCargaOK] hashCodePC: " + hashCodePC);
		PCgui = PanelControl.getPanelControl().getGUI();
		assertNotNull(PCgui);
		assertNotNull(ctrlFct);
		assertNotNull(ctrlPpal);
		assertNotNull(config);
		System.out.println("[FxApplicationTest>PCcargaOK]******FINAL*****");
//#endregion
//#region parte3_pcFunciona
		System.out.println("[FxApplicationTest>pcFunciona]******INICIO*****");
		do{
			System.out.print("[FxApplicationTest>pcFunciona] Esperando a cargar el ControladorFacturas");
		}while((ctrlFct=ControladorFacturas.getControlador())==null);
		ctrlPpal = Controlador.getControlador();
		config = Config.getConfig(usuario);
		config = Config.getConfig("admin");
		ctrlPpal = Controlador.getControlador();
		pc = PanelControl.getPanelControl();
		hashCodePC = pc.hashCode(); 
		assertEquals(hashCodePC,pc.hashCode());
		clickOn("#btnFCT");
		Thread.sleep(1000);
		verifyThat("#btnFCT", (ToggleButton tbtn) -> {
			boolean act = tbtn.isSelected();
			return act;
		});
		System.out.println("[FxApplicationTest>PCfunciona] Esperando a cargar la TablaFCT");
		Thread.sleep(2000);
		System.out.println("[FxApplicationTest>pcFunciona]******FINAL*****");
		
//#endregion
//#region parte4_tablaFCTcargaOK
		System.out.println("[FxApplicationTest>tablaFCTcargaOK]******INICIO*****");
		System.out.println("[FxApplicationTest>tablaFCTcargaOK] Esperando a cargar la TablaFCT\r");
		/*Platform.runLater(new Runnable(){
			@Override
			public void run(){
					var arrayFxml = cargarFXML("../../resources/fxmltablaFCT.fxml");
					Scene escenaTFCT = setEscena((Parent)arrayFxml[0]);
					showStage(escenaTFCT,false);
			}
		});*/
		/*config = Config.getConfig("admin");
		ctrlPpal = Controlador.getControlador();
		ctrlFct = ControladorFacturas.getControlador();
		ctrlFxTablaFct = ctrlFct.getFXcontrlTablaFCT();*/
		System.out.println("[FxApplicationTest>tablaFCTcargaOK] hashCodeVisor: " + hashCodeVisor);
		modeloFCT = ModeloFacturas.getModelo();
		assertNotNull(ctrlPpal);
		assertNotNull(config);
		assertNotNull(ctrlFct);
		assertNotNull(pc);
		assertNotNull(modeloFCT);
		listaFXFCT = modeloFCT.getListaFXFacturas();
		ctrlFxTablaFct = ctrlFct.getFXcontrlTablaFCT();
		assertNotNull(ctrlFxTablaFct);
		assertTrue(listaFXFCT.get(0) instanceof Factura);
		ctrlFxTablaFct.tblvwfct.setItems(listaFXFCT);
		Thread.sleep(3000);
		clickOn("#btnVisorFct");
		System.out.println("[FxApplicationTest>tablaFCTcargaOK] boton Visor pulsado!! Esperando a que cargue el visorFCT.");	
		Thread.sleep(2000);
		System.out.println("[FxApplicationTest>tablaFCTcargaOK]******FINAL*****");
//#endregion
//#region parte5_visorFCTcargaOK
		System.out.println("[FxApplicationTest>visorFCTcargaOK]******INICIO*****");
		System.out.println("[FxApplicationTest>visorFCTcargaOK] Esperando a cargar el visorFCT\r");
		
		/*Platform.runLater(new Runnable(){
			@Override
			public void run(){
				var arrayFxml = cargarFXML("../../resources/visorFormFCT.fxml");
				Scene escenaVFCT = setEscena((Parent)arrayFxml[0]);
				showStage(escenaVFCT,true);
			}
		});*/
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
		listaFXFCT = modeloFCT.getListaFXFacturas();
		//NOTE - 24-07-13 : actualizamos el VisorFCT con la FacturaActual...
		int iact = FxCntrlTablaFCT.getIndiceActual();
		Factura fact = listaFXFCT.get(1);
		//NOTE - 24-07-13 : facturaActual que tenemos que actualizar manualmente
		ControladorFacturas.facturaActual = fact;
		//NOTE - 24-07-13 : comprobamos que no sean 'null'
		assertEquals(0,iact);
		assertNotNull(fact);
		ctrlFct.mostrarVisorFCT(iact, fact);
		assertTrue(listaFXFCT.get(0) instanceof Factura);
		//FIXME - 24-07-17 : No funciona esto, después de 'mostrarVisorFCT()'' debería poder asignarse un contrFXvisorFCT, no debería ser NULL 
		//ctrlFxVisorFct = Controlador.getControladorFacturas().getFXcontrlVisorFCT();
		//assertNotNull(ctrlFxVisorFct);
		Thread.sleep(3000);
		verifyThat("#lblVID", (Label label) -> {
			String text = label.getText();
			return (text.contains("2"));
		});
		Thread.sleep(2500);
		clickOn("#btnVCerrar");
		System.out.println("[FxApplicationTest>visorFCTcargaOK] boton Cerrar VisorFCT pulsado!! Esperando a que se cierre el visor.");
		Thread.sleep(2000);
		System.out.println("[FxApplicationTest>visorFCTcargaOK]******FINAL*****");		
//#endregion
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

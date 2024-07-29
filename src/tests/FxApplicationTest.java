package tests;

import controladores.Controlador;
import controladores.ControladorFacturas;
import controladores.fxcontrollers.FxCntrlTablaFCT;
import controladores.fxcontrollers.FxCntrlVisorFCT;
import controladores.fxcontrollers.PanelControl;
import controladores.fxcontrollers.SplashFX;
import controladores.helpers.FxmlHelper;
import modelo.ModeloFacturas;
import modelo.base.Config;
import modelo.records.Factura;

import java.util.concurrent.TimeoutException;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.service.query.NodeQuery;
import org.testfx.util.WaitForAsyncUtils;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FxApplicationTest extends ApplicationTest{

//#region CAMPOS
	public String usuario = "admin";
	public static Config config;
	public static Controlador ctrlPpal;
	public static ControladorFacturas ctrlFct;
	public static ModeloFacturas modeloFCT;
	public static PanelControl pc;
	public Stage login;
	public Stage tablaFCT;
	public Stage visorFCT;
	public Stage PCgui;
	public static FxCntrlVisorFCT ctrlFxVisorFct;
	public static FxCntrlTablaFCT ctrlFxTablaFct;
	public static TableView<Factura> modTablaFCT;
	public static long hashCodePC;
	public static long hashCodeTabla;
	public static long hashCodeVisor;
	public static ObservableList<Factura> listaFXFCT;
//#endregion

//#region beforeAll
//Lo ejecuta antes de todo el conjunto de Tests
	@BeforeAll
	public static void setUp() throws Exception {

		System.out.println("[FxApplicationTest>setUp]******INICIO*****");

		ApplicationTest.launch(SplashFX.class, "");

		System.out.println("[FxApplicationTest>setUp]******FINAL*****");
	
	}
//#endregion

//#region start 
//Lo ejecuta antes de cada Test individual
/*	@Override
    public void start(Stage stage) {

		System.out.println("[FxApplicationTest>start]******INICIO*****");

		this.login = stage;
        this.login.show();

		System.out.println("[FxApplicationTest>start]******FINAL*****");
    }
*/
//#endregion

//#region Tests
	@Test
	@Order(1)
	public void visorFCTCargaOK() throws InterruptedException{

//#region parte1_loginOK
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
		moveTo("#btnOK");
		clickOn("#btnOK");
		System.out.println("[FxApplicationTest>PCcargaOK] Esperando a cargar el Panel de Control\r");
		Thread.sleep(2000);
		System.out.println("[FxApplicationTest>loginAdminOK]******FINAL*****");
//#endregion

//#region parte2_PccargaOK	 @SuppressWarnings("unchecked")

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
		moveTo("#btnFCT");
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
		})
		config = Config.getConfig("admin");
		ctrlPpal = Controlador.getControlador();
		ctrlFct = ControladorFacturas.getControlador();
		ctrlFxTablaFct = ctrlFct.getFXcontrlTablaFCT();*/
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
		modTablaFCT = ctrlFxTablaFct.getTableView();
		assertNotNull(modTablaFCT);	
		System.out.println("[FxApplicationTest>tablaFCTcargaOK]******FINAL*****");
//#endregion

//#region parte5_tablaFCTfunciona

		System.out.println("[FxApplicationTest>tablaFCTfunciona]******INICIO*****");
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
		assertNotNull(listaFXFCT.get(3));
		assertTrue(listaFXFCT.get(3) instanceof Factura);
		/*//NOTE - 24-07-13 : actualizamos el VisorFCT con la FacturaActual...
		int iact = FxCntrlTablaFCT.getIndiceActual();
		Factura fact = listaFXFCT.get(1);
		//NOTE - 24-07-13 : facturaActual que tenemos que actualizar manualmente
		ControladorFacturas.facturaActual = fact;
		//NOTE - 24-07-13 : comprobamos que no sean 'null'
		assertEquals(0,iact);
		assertNotNull(fact);
		ctrlFct.mostrarVisorFCT(iact, fact);*/
		//NOTE - 24-07-26 : Esperamos a que acabe de cargar el visorFCT
		ctrlFxTablaFct = Controlador.getControladorFacturas().getFXcontrlTablaFCT();
		assertNotNull(ctrlFxTablaFct); 
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				ctrlFxTablaFct.setIndiceActual(3);
				ControladorFacturas.setFacturaActual(listaFXFCT.get(3));
			}	
		});
		Thread.sleep(2000);
		System.out.println("[FxApplicationTest>tablaFCTfunciona]*******FINAL******");
//#endregion

//#region parte6_visorFCTcargaOK

		System.out.println("[FxApplicationTest>visorFCTcargaOK]******INICIO*****");
		moveTo("#btnVisorFct");
		clickOn("#btnVisorFct");
		System.out.println("[FxApplicationTest>visorFCTcargaOK] boton Visor pulsado!! Esperando a que cargue el visorFCT.");
		int cnt = 1;
		do{
			Thread.sleep(1000);
			System.out.println("[FxApplicationTest>visorFCTcargaOK] " + cnt + " - Esperando a que cargue el visorFCT.");
		}while((ctrlFxVisorFct = Controlador.getControladorFacturas().getFXcontrlVisorFCT())==null);
		assertNotNull(ctrlFxVisorFct);
		visorFCT = ctrlFxVisorFct.getVisorFCT();
		hashCodeVisor = visorFCT.hashCode();
		System.out.println("[FxApplicationTest>tablaFCTcargaOK] hashCodeVisor: " + hashCodeVisor);
		//NOTE - 24-07-26 : Esperamos a que el visorFCT cargue todos los datos
		Thread.sleep(2000);
		/*verifyThat("#lblVID", (Label label) -> {
			String text = label.getText();
			return (text.contains("4"));
		});*/
		System.out.println("[FxApplicationTest>visorFCTcargaOK]*******FINAL******");
//#endregion

//#region parte7_visorFCTfunciona
		WaitForAsyncUtils.waitForFxEvents();
		//Thread.sleep(2000);
		System.out.println("[FxApplicationTest>visorFCTfunciona]******INICIO*****");
		Stage stage = visorFCT;
		Scene scene = stage.getScene();
		assertNotNull(scene);
		System.out.println("[FxApplicationTest>visorFCTfunciona] La escena NO es NULL");
		// NOTE - 24-07-28 : n - num de repeticiones;
		int n = listaFXFCT.size();
		System.out.println("[FxApplicationTest>visorFCTfunciona] Cantidad de facturas en la listaFX : " + n);
		Thread.sleep(2000);
		System.out.println("[FxApplicationTest>visorFCTfunciona] A punto de probar los botones de desplazamiento del visorFCT...");
		NodeQuery APcontroles = find("#apnControles");
		//NodeQuery botonDcha = lookup("#btnVdcha");
		System.out.print("[FxApplicationTest>visorFCTfunciona] Comprobando que los controles del visor existen... ");
		//assertNotNull(botonDcha);
		assertNotNull(APcontroles);
		System.out.println("OK");
		moveTo("#apnControles");
		Thread.sleep(2000);
		moveTo("#btnVdcha");
		for (int i=1;i<=n;i++){
			clickOn("#btnVdcha");
			System.out.println("[FxApplicationTest>visorFCTfunciona] boton Dcha pulsado en el visorFCT. Rep " + i);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		moveTo("#btnVizda");
		for (int i=1;i<=n;i++){
			clickOn("#btnVizda");
			System.out.println("[FxApplicationTest>visorFCTfunciona] boton Izda pulsado en el visorFCT. Rep " + i);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		moveTo("#btnVCerrar");
		clickOn("#btnVCerrar");
		System.out.println("[FxApplicationTest>visorFCTfunciona] boton Cerrar VisorFCT pulsado!! Esperando a que se cierre el visor.");
		System.out.println("[FxApplicationTest>visorFCTfunciona]******FINAL*****");		
//#endregion

	}
//#endregion

//#region afterAll
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
//#endregion

//#region HELPERS	
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
/*
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
*/

	public NodeQuery find(final String fxid){
		return (lookup(fxid)/*.queryAll().iterator().next()*/);
	 }


//#endregion

}

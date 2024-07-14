package controladores.fxcontrollers;

import modelo.ModeloFacturas;
import modelo.records.Factura;
import controladores.Controlador;
import controladores.ControladorFacturas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.BrokenBarrierException;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FxCntrlTablaFCT implements Initializable{

//#region CAMPOS_FXML
//ANCHOR - tableView
	@FXML public TableView<Factura> tblvwFct;
	@FXML private TableColumn<Factura, Integer> colID;
	@FXML private TableColumn<Factura, String> colNumFact;
	@FXML private TableColumn<Factura, String> colFecha;
	@FXML private TableColumn<Factura, String> colRS;
	@FXML private TableColumn<Factura, String> colCat;
	@FXML private TableColumn<Factura, String> colDev;
	@FXML private TableColumn<Factura, Integer> colNumExtr;
	@FXML private TableColumn<Factura, Double> colBase;
	@FXML private TableColumn<Factura, Integer> colTipoIVA;
	@FXML private TableColumn<Factura, Double> colIVA;
	@FXML private TableColumn<Factura, Double> colST;
	@FXML private TableColumn<Factura, Integer> colTipoRet;
	@FXML private TableColumn<Factura, Double> colRetenc;
	@FXML private TableColumn<Factura, Double> colTotal;
	@FXML private TableColumn<Factura, String> colNota;

	@FXML private Button btnVisorFct;
	@FXML private Button btnNuevaFct;
    @FXML private Button btnBorrarFct;
    @FXML private Button btnEditarFct;
    @FXML private Button btnFiltrosFct;
	@FXML private CheckBox chkbxFiltrosActivosFct;
	@FXML Label lblNumFact;
	@FXML public Label lblBase;
	@FXML Label lblIVA;
	@FXML Label lblST;
	@FXML Label lblBaseNI;
	@FXML Label lblRetenc;
	@FXML Label lblTotal;
//#endregion

//#region OTROS_CAMPOS

//NOTE - 14-07-24 : Singleton
	static FxCntrlTablaFCT instancia;

	boolean haCambiado = false;
	int pulsado = 0;

	static int indiceActual = 0;
	ControladorFacturas cfct;
	//private Property<ObservableList<Factura>> FxFacturaListProperty;
	private ObservableList<Factura> listaFxFacturas;
//#endregion

//#region CONSTR
//TODO: 22-06-2024 - En el constructor inicializamos los campos que necesitamos listos antes de nada...
	public FxCntrlTablaFCT() throws InterruptedException, BrokenBarrierException{
		System.out.println("[FxControladorFacturas>constructor] Comenzando el constructor de FxCntrlTablaFCT");
		// NOTE - 02-07-24 : Vamos a ver qué ocurre si el constructor de FxCntrlTablaFCT no crea una nueva tabla... parece que nada ...
		this.cfct = Controlador.getControladorFacturas();
		this.listaFxFacturas = ModeloFacturas.getModelo().getListaFXFacturas();
		this.tblvwFct = new TableView<Factura>();
		//this.FxFacturaListProperty = new SimpleObjectProperty<>(this.listaFxFacturas);
		System.out.println("[FxControladorFacturas>constructor] Acabando el constructor de FxCntrlTablaFCT");
	}
//#endregion

//#region INI_FCT/T
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//Adjudicar valores para las Columnas de la Tabla de Facturas
		//System.out.println("[FxControladorFacturas.java>initialize()] Comenzando initialize - Adjudicando valores para las columnas de la Tabla de Facturas");
		colID.setCellValueFactory(cellData -> cellData.getValue().getFxID());
		colNumFact.setCellValueFactory(cellData -> cellData.getValue().getFxNumFact());
		colFecha.setCellValueFactory(cellData -> cellData.getValue().getFxFecha());
		colRS.setCellValueFactory(cellData -> cellData.getValue().getFxRS());
		colCat.setCellValueFactory(cellData -> cellData.getValue().getFxCat());
		colDev.setCellValueFactory(cellData -> cellData.getValue().getFxDev());
		colNumExtr.setCellValueFactory(cellData -> cellData.getValue().getFxNumExtr());
		colBase.setCellValueFactory(cellData -> cellData.getValue().getFxBase());
		colTipoIVA.setCellValueFactory(cellData -> cellData.getValue().getFxTipoIVA());
		colIVA.setCellValueFactory(cellData -> cellData.getValue().getFxIVA());
		colST.setCellValueFactory(cellData -> cellData.getValue().getFxST());
		colTipoRet.setCellValueFactory(cellData -> cellData.getValue().getFxTipoRet());
		colRetenc.setCellValueFactory(cellData -> cellData.getValue().getFxRetenc());
		colTotal.setCellValueFactory(cellData -> cellData.getValue().getFxTotal());
		colNota.setCellValueFactory(cellData -> cellData.getValue().getFxNota());

//addColums2table
/*
		this.tblvwFct.getColumns().add(colID);
		tblvwFct.getColumns().add(colNumFact);
		tblvwFct.getColumns().add(colFecha);
		tblvwFct.getColumns().add(colRS);
		tblvwFct.getColumns().add(colCat);
		tblvwFct.getColumns().add(colDev);
		tblvwFct.getColumns().add(colNumExtr);
		tblvwFct.getColumns().add(colBase);
		tblvwFct.getColumns().add(colTipoIVA);
		tblvwFct.getColumns().add(colIVA);
		tblvwFct.getColumns().add(colST);
		tblvwFct.getColumns().add(colTipoRet);
		tblvwFct.getColumns().add(colRetenc);
		tblvwFct.getColumns().add(colTotal);
		tblvwFct.getColumns().add(colNota);
*/

		//this.tblvwFct.itemsProperty().bind(this.FxFacturaListProperty); // The Binding
		this.tblvwFct.setItems(listaFxFacturas);
		this.tblvwFct.refresh();
		String[] datosResumen = ModeloFacturas.getModelo().calcularTotales();
		actualizarTotales(datosResumen);
		//NOTE - 02-07-24 : generar listener en el TableView para que informe del cambio de elemento seleccionado

		//this.tblvwFct.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		//Arrancar el hilo del ControladorFacturas
		//System.out.println("[FxCntrlTablaFCT>initialize] Comenzando el initialize del ControladorFX de Facturas");
		try {
			//NOTE - 07-07-24 : Saco la barrera para los tests de JavaFX 
			//Controlador.barreraControladores.await();
			cfct = Controlador.getControladorFacturas(this);
		} catch (InterruptedException | BrokenBarrierException e) {
			System.out.println("[FxControladorFacturas>initialize] El programa se cierra porque se no ha podido acabar de inicializar el controlador FX de la TablaFCT");
			System.exit(0);
		}
		
		System.out.println("[FxCntrlTablaFCT>initialize] Saliendo del initialize del ControladorFX de Facturas");
	}
//#endregion

//#region EVT_BTN
	@FXML
	public void btnVisorFctPulsado(Event ev){
		this.haCambiado = true;
		this.pulsado = 1;
	}

	@FXML
	public void btnNuevaFctPulsado(Event ev){
		this.haCambiado = true;
		this.pulsado = 2;
	}	

	@FXML
	public void btnEditarFctPulsado(Event ev){
		this.haCambiado = true;
		this.pulsado = 3;
	}

	@FXML
	public void btnBorrarFctPulsado(Event ev){
		this.haCambiado = true;
		this.pulsado = 4;
	}

	@FXML
	public void btnFiltrosFctPulsado(Event ev){
		this.haCambiado = true;
		this.pulsado = 5;
	}

	public boolean HaCambiado(){
		return this.haCambiado;
	}

	public int getPulsado(){
		this.haCambiado = false;
		return this.pulsado;
	}

	public void reset(){
		System.out.println("[FxCntrlTablaFCT>reset] En el reset de contrFX de tablaFCT con hashCode (del contrFX " + this.hashCode());
		this.haCambiado = false;
		this.pulsado = 0;
	}
//#endregion

//#region GET/SET	
public static FxCntrlTablaFCT getFxController() throws InterruptedException, BrokenBarrierException {
	if (instancia==null){
		instancia = new FxCntrlTablaFCT();
	}
	return instancia;
}

//ANCHOR - tableView
	public synchronized TableView<Factura> getTableView() throws InterruptedException, BrokenBarrierException{
		//System.out.println("[FxCntrlTablaFCT>getTableView] la tabla no es NULL - devolviendo su valor hashCode de tablaFCT: " + this.tblvwFct.hashCode());
		this.tblvwFct = cfct.getFXcontrlTablaFCT().tblvwFct;
		return this.tblvwFct;
	}
	
	public int getIndiceSeleccionadoTabla() {
		return  indiceActual;
	}

	public Factura getFacturaSeleccionadaTabla() throws NullPointerException, IOException{
		if (this.tblvwFct.isVisible()){
			Factura selectedItem = this.tblvwFct.getSelectionModel().getSelectedItem();
			//System.out.println("[FxCntrlTablaFCT>getFacturaSeleccionadaTabla] Como this.tblvwFct.isVisible(): " + this.tblvwFct.isVisible() + " se muestra la factura seleccionada:\n"+ selectedItem.toString());
			return selectedItem;
		}
		else{
			//OJO : Estoy enviando la segunda factura al Visor, al arrancar...
			Factura f = cfct.m.getFactura(1);
			//System.out.println("[FxCntrlTablaFCT>getFacturaSeleccionadaTabla] Como this.tblvwFct.isVisible(): " + this.tblvwFct.isVisible() + " se muestra la *(segunda) factura:" + f.toString());
			return f;
		}
	}

	public void seleccionarIndiceTabla(int nuevoindice) throws NullPointerException, IOException {
		if (this.tblvwFct!=null && nuevoindice>0){
			this.tblvwFct.getSelectionModel().select(nuevoindice);
			ControladorFacturas.facturaActual = getFacturaSeleccionadaTabla();
		} 
	}

	public static int getIndiceActual() {
		return indiceActual;
	}

	public static void setIndiceActual(int index) {
		indiceActual = index;
	}
//#endregion

//#region ACT_TABLA
	public void actualizarTotales(String[] datos) throws NullPointerException{
		lblBase.setText(datos[0]);
		lblIVA.setText(datos[1]);
		lblST.setText(datos[2]);
		lblBaseNI.setText(datos[3]);
		lblRetenc.setText(datos[4]);
		lblTotal.setText(datos[5]);
		lblNumFact.setText(datos[6]);
	}
//#endregion

}

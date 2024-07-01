package controladores.fxcontrollers;

import java.net.URL;
import java.util.ResourceBundle;

import modelo.ModeloFacturas;
import modelo.records.Factura;
import modelo.records.Fecha;
import modelo.records.RazonSocial;
import controladores.ControladorFacturas;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;




public class FxCntrlTablaFCT implements Initializable{
//FXML tabla
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
	@FXML
	public Label lblBase;
	@FXML Label lblIVA;
	@FXML Label lblST;
	@FXML Label lblBaseNI;
	@FXML Label lblRetenc;
	@FXML Label lblTotal;

	private Property<ObservableList<Factura>> FxFacturaListProperty;
	ControladorFacturas cfct;
	boolean haCambiado = false;
	int pulsado = 0;
	private ObservableList<Factura> listaFxFacturas;
	public static FxCntrlTablaFCT instancia;
//TODO: 22-06-2024 - En el constructor inicializamos los campos que necesitamos listos antes de nada...
	public FxCntrlTablaFCT(){

		this.tblvwFct = new TableView<Factura>();
		this.listaFxFacturas = ModeloFacturas.getModelo().getListaFXFacturas();
		this.FxFacturaListProperty = new SimpleObjectProperty<>(this.listaFxFacturas);

	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
//#region INI_FCT/T		
		//Adjudicar valores para las Columnas de la Tabla de Facturas
//		System.out.println("[FxControladorFacturas.java>initialize()] Adjudicando valores para las columnas de la Tabla de Facturas");
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
		tblvwFct.getColumns().add(colID);
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
		//Arrancar el hilo del ControladorFacturas (aún no funciona)
		System.out.println("[FxControladorFacturas.java>initialize()] Arrancando el Hilo del Controlador de Facturas");
		cfct = ControladorFacturas.getControlador(this);
//#endregion

	}

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
	public synchronized TableView<Factura> getTableView(){
		return this.tblvwFct;
	}
	public void reset(){
		this.haCambiado = false;
		this.pulsado = 0;
	}

	public static FxCntrlTablaFCT getFxController() {
		if (instancia==null){
			instancia = new FxCntrlTablaFCT();
		}
		return instancia;
	}

	public void actualizarTotales(String[] datos) throws NullPointerException{
		lblBase.setText(datos[0]);
		lblIVA.setText(datos[1]);
		lblST.setText(datos[2]);
		lblBaseNI.setText(datos[3]);
		lblRetenc.setText(datos[4]);
		lblTotal.setText(datos[5]);
		lblNumFact.setText(datos[6]);
	}
 
}

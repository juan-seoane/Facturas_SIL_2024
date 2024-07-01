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
import javafx.stage.Stage;




public class FxCntrlVisorFCT implements Initializable{

// TODO : Falta cambiar los Label por TextField
	@FXML Label lblVID;
	@FXML Label lblVNumFactura;
	@FXML Label lblVFecha;
	@FXML Label lblVIDRS;
	@FXML Label lblVNombreEmpresa;
	@FXML Label lblVNIF;
	@FXML Label lblVRS;
	@FXML Label lblVBase1;
	@FXML Label lblVTipoIVA1;
	@FXML Label lblVIVA1;
	@FXML Label lblVST1;
	@FXML Label lblVBase2;
	@FXML Label lblVTipoIVA2;
	@FXML Label lblVIVA2;
	@FXML Label lblVST2;
	@FXML Label lblVBase3;
	@FXML Label lblVTipoIVA3;
	@FXML Label lblVIVA3;
	@FXML Label lblVST3;
	@FXML Label lblVBase4;
	@FXML Label lblVTipoIVA4;
	@FXML Label lblVIVA4;
	@FXML Label lblVST4;
	@FXML Label lblVTotalesBase;
	@FXML Label lblVTotalesIVA;
	@FXML Label lblVTotalesST;
	@FXML Label lblVTotalesBaseNI;
	@FXML Label lblVTotalesTipoRet;
	@FXML Label lblVTotalesRetenciones;
	@FXML Label lblVTotal;
	@FXML TextArea txtAreaVNota;
	@FXML Button btnVizda;
	@FXML Button btnVdcha;

	ControladorFacturas cfct;
	boolean haCambiado = false;
	int pulsado = 0;
	private ObservableList<Factura> listaFxFacturas;
	private Stage visorFct;
	public static FxCntrlVisorFCT instancia;

//TODO: 22-06-2024 - En el constructor inicializamos los campos que necesitamos listos antes de nada...
	public FxCntrlVisorFCT(){

		this.visorFct = new Stage();
		this.listaFxFacturas = ModeloFacturas.getModelo().getListaFXFacturas();


	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
//#region INI FCT/V
	// TODO : 30-06-2024 - Hay que designar una fila seleccionada en la tabla, en vez del primer elemento (0) de la Lista de Facturas
		this.lblVID.setText(listaFxFacturas.get(0).getID()+"");
		this.lblVNumFactura.setText(listaFxFacturas.get(0).getNumeroFactura());
		this.lblVFecha.setText(listaFxFacturas.get(0).getFecha().toString());
	// TODO : 30-06-2024 - Falta insertar en el VisorFCT los datos de la empresa y los extractos
		this.lblVTotalesBase.setText(listaFxFacturas.get(0).getTotales().getBase()+"");
		this.lblVTotalesIVA.setText(listaFxFacturas.get(0).getTotales().getIVA()+"");
		this.lblVTotalesST.setText(listaFxFacturas.get(0).getTotales().getSubtotal()+"");
		this.lblVTotalesBaseNI.setText(listaFxFacturas.get(0).getTotales().getBaseNI()+"");
		this.lblVTotalesTipoRet.setText(listaFxFacturas.get(0).getTotales().getRet()+"");
		this.lblVTotalesRetenciones.setText(listaFxFacturas.get(0).getTotales().getRetenciones()+"");
		this.lblVTotal.setText(listaFxFacturas.get(0).getTotales().getTotal()+"");
		if (listaFxFacturas.get(0).getNota()!=null)
			this.txtAreaVNota.setText(listaFxFacturas.get(0).getNota().getTexto());
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
	public synchronized TextArea getAreaNota(){
		return this.txtAreaVNota;
	}
	public void reset(){
		this.haCambiado = false;
		this.pulsado = 0;
	}

	public static FxCntrlVisorFCT getFxController() {
		if (instancia==null){
			instancia = new FxCntrlVisorFCT();
		}
		return instancia;
	}

	public void actualizarTotalesVisor(String[] datos) throws NullPointerException{
		lblVTotalesBase.setText(datos[0]);
		lblVTotalesIVA.setText(datos[1]);
		lblVTotalesST.setText(datos[2]);
		lblVTotalesBaseNI.setText(datos[3]);
		lblVTotalesRetenciones.setText(datos[4]);

	}
 
}

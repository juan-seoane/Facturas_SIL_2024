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
import javafx.scene.control.TextField;
import javafx.stage.Stage;




public class FxCntrlVisorFCT implements Initializable{

// TODO : Falta cambiar los Label por TextField
	@FXML Label lblVID;
	@FXML TextField tfNumFactura;
	@FXML TextField tfFecha;
	@FXML Label lblVIDRS;
	@FXML TextField tfNombreEmpresa;
	@FXML TextField tfNIF;
	@FXML TextField tfRS;
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
	@FXML Button btnVNueva;
	@FXML Button btnVEditar;
	@FXML Button btnVBorrar;
	@FXML Label lblVTitulo;
	@FXML Button btnVCerrar;
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
	// TODO : 30-06-2024 - Hay que designar la factura de la fila seleccionada en la tabla, en vez del primer elemento (0) de la Lista de Facturas
		actualizarDatosFacturaVisor(listaFxFacturas.get(0));
		actualizarDatosEmpresaVisor(listaFxFacturas.get(0));
		actualizarExtractosVisor(listaFxFacturas.get(0));
		actualizarTotalesVisor(listaFxFacturas.get(0));
		actualizarNotaVisor(listaFxFacturas.get(0));
//#endregion
	}

	@FXML
	public void btnCerraVPulsado(Event ev){
		this.haCambiado = true;
		this.pulsado = 1;
	}

	@FXML
	public void btnNuevaFctVPulsado(Event ev){
		this.haCambiado = true;
		this.pulsado = 2;
	}	

	@FXML
	public void btnEditarFctVPulsado(Event ev){
		this.haCambiado = true;
		this.pulsado = 3;
	}

	@FXML
	public void btnBorrarFctPulsado(Event ev){
		this.haCambiado = true;
		this.pulsado = 4;
	}

	@FXML
	public void btnIzdaVPulsado(Event ev){
		this.haCambiado = true;
		this.pulsado = 5;
	}

	@FXML
	public void btnDchaVPulsado(Event ev){
		this.haCambiado = true;
		this.pulsado = 6;
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
//#region Act_VISOR
	private void actualizarDatosFacturaVisor(Factura f) {
		this.lblVID.setText(f.getID()+"");
		this.tfNumFactura.setText(f.getNumeroFactura()+"");
		this.tfFecha.setText(f.getFecha().toString());
	}

	private void actualizarDatosEmpresaVisor(Factura f){
		this.lblVIDRS.setText(f.getRS().getID()+"");
		this.tfNIF.setText(f.getRS().getNif().toString());
		this.tfNombreEmpresa.setText(f.getRS().getNombre());
		this.tfRS.setText(f.getRS().getRazon());
	}

	private void actualizarExtractosVisor(Factura f){
		switch(f.getExtractos().size()){
			case 4:
				this.lblVBase4.setText(f.getExtractos().get(3).getBase()+"");
				this.lblVTipoIVA4.setText(f.getExtractos().get(3).getTipoIVA()+"");
				this.lblVIVA4.setText(f.getExtractos().get(3).getIVA()+"");
				this.lblVST4.setText(f.getExtractos().get(3).getTotal()+"");
			case 3: 
				this.lblVBase3.setText(f.getExtractos().get(2).getBase()+"");
				this.lblVTipoIVA3.setText(f.getExtractos().get(2).getTipoIVA()+"");
				this.lblVIVA3.setText(f.getExtractos().get(2).getIVA()+"");
				this.lblVST3.setText(f.getExtractos().get(2).getTotal()+"");			
			case 2:
				this.lblVBase2.setText(f.getExtractos().get(1).getBase()+"");
				this.lblVTipoIVA2.setText(f.getExtractos().get(1).getTipoIVA()+"");
				this.lblVIVA2.setText(f.getExtractos().get(1).getIVA()+"");
				this.lblVST2.setText(f.getExtractos().get(1).getTotal()+"");			
			case 1:
				this.lblVBase1.setText(f.getExtractos().get(0).getBase()+"");
				this.lblVTipoIVA1.setText(f.getExtractos().get(0).getTipoIVA()+"");
				this.lblVIVA1.setText(f.getExtractos().get(0).getIVA()+"");
				this.lblVST1.setText(f.getExtractos().get(0).getTotal()+"");			
				break;
			default:
				System.out.println("[FxCntrlVisorFCT>actualizarExtractosVisor] Error en el num de extractos de la factura seleccionada");
				break;
		}
	}

	private void actualizarTotalesVisor(Factura f) throws NullPointerException{
		this.lblVTotalesBase.setText(f.getTotales().getBase()+"");
		this.lblVTotalesIVA.setText(f.getTotales().getIVA()+"");
		this.lblVTotalesST.setText(f.getTotales().getSubtotal()+"");
		this.lblVTotalesBaseNI.setText(f.getTotales().getBaseNI()+"");
		this.lblVTotalesTipoRet.setText(f.getTotales().getRet()+"");
		this.lblVTotalesRetenciones.setText(f.getTotales().getRetenciones()+"");
		this.lblVTotal.setText(f.getTotales().getTotal()+"");
		//lblVTitulo.setText("num de fact: " + datos[6]);

	}

	private void actualizarNotaVisor(Factura f){
		if (f.getNota()!=null)
			this.txtAreaVNota.setText(f.getNota().getTexto());
		else this.txtAreaVNota.setText("--SIN NOTA--");	
	}
//#endregion 
}

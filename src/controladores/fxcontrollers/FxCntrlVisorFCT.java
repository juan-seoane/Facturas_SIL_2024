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
<<<<<<< HEAD
import javafx.scene.control.TextField;
=======
>>>>>>> 7aabd4bfd1b2e5a3d5f057ddd30a0d4a19a6c547
import javafx.stage.Stage;




public class FxCntrlVisorFCT implements Initializable{

// TODO : Falta cambiar los Label por TextField
	@FXML Label lblVID;
<<<<<<< HEAD
	@FXML TextField tfNumFactura;
	@FXML TextField tfFecha;
	@FXML Label lblVIDRS;
	@FXML TextField tfNombreEmpresa;
	@FXML TextField tfNIF;
	@FXML TextField tfRS;
=======
	@FXML Label lblVNumFactura;
	@FXML Label lblVFecha;
	@FXML Label lblVIDRS;
	@FXML Label lblVNombreEmpresa;
	@FXML Label lblVNIF;
	@FXML Label lblVRS;
>>>>>>> 7aabd4bfd1b2e5a3d5f057ddd30a0d4a19a6c547
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
<<<<<<< HEAD
	@FXML Button btnVNueva;
	@FXML Button btnVEditar;
	@FXML Button btnVBorrar;
	@FXML Label lblVTitulo;
	@FXML Button btnVCerrar;
=======
>>>>>>> 7aabd4bfd1b2e5a3d5f057ddd30a0d4a19a6c547
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
<<<<<<< HEAD
	// TODO : 30-06-2024 - Hay que designar la factura de la fila seleccionada en la tabla, en vez del primer elemento (0) de la Lista de Facturas
		actualizarDatosFacturaVisor(listaFxFacturas.get(0));
		actualizarDatosEmpresaVisor(listaFxFacturas.get(0));
		actualizarExtractosVisor(listaFxFacturas.get(0));
		actualizarTotalesVisor(listaFxFacturas.get(0));
		actualizarNotaVisor(listaFxFacturas.get(0));
=======
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
>>>>>>> 7aabd4bfd1b2e5a3d5f057ddd30a0d4a19a6c547
//#endregion
	}

	@FXML
<<<<<<< HEAD
	public void btnCerraVPulsado(Event ev){
=======
	public void btnVisorFctPulsado(Event ev){
>>>>>>> 7aabd4bfd1b2e5a3d5f057ddd30a0d4a19a6c547
		this.haCambiado = true;
		this.pulsado = 1;
	}

	@FXML
<<<<<<< HEAD
	public void btnNuevaFctVPulsado(Event ev){
=======
	public void btnNuevaFctPulsado(Event ev){
>>>>>>> 7aabd4bfd1b2e5a3d5f057ddd30a0d4a19a6c547
		this.haCambiado = true;
		this.pulsado = 2;
	}	

	@FXML
<<<<<<< HEAD
	public void btnEditarFctVPulsado(Event ev){
=======
	public void btnEditarFctPulsado(Event ev){
>>>>>>> 7aabd4bfd1b2e5a3d5f057ddd30a0d4a19a6c547
		this.haCambiado = true;
		this.pulsado = 3;
	}

	@FXML
	public void btnBorrarFctPulsado(Event ev){
		this.haCambiado = true;
		this.pulsado = 4;
	}

	@FXML
<<<<<<< HEAD
	public void btnIzdaVPulsado(Event ev){
=======
	public void btnFiltrosFctPulsado(Event ev){
>>>>>>> 7aabd4bfd1b2e5a3d5f057ddd30a0d4a19a6c547
		this.haCambiado = true;
		this.pulsado = 5;
	}

<<<<<<< HEAD
	@FXML
	public void btnDchaVPulsado(Event ev){
		this.haCambiado = true;
		this.pulsado = 6;
	}


=======
>>>>>>> 7aabd4bfd1b2e5a3d5f057ddd30a0d4a19a6c547
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
<<<<<<< HEAD
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
=======

	public void actualizarTotalesVisor(String[] datos) throws NullPointerException{
		lblVTotalesBase.setText(datos[0]);
		lblVTotalesIVA.setText(datos[1]);
		lblVTotalesST.setText(datos[2]);
		lblVTotalesBaseNI.setText(datos[3]);
		lblVTotalesRetenciones.setText(datos[4]);

	}
 
>>>>>>> 7aabd4bfd1b2e5a3d5f057ddd30a0d4a19a6c547
}

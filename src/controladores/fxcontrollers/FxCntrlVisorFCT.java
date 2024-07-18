package controladores.fxcontrollers;

import modelo.records.Factura;
import controladores.Controlador;
import controladores.ControladorFacturas;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.BrokenBarrierException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FxCntrlVisorFCT implements Initializable{

//#region CAMPOS FXML
// FIXME - 24-07-02 : Falta cambiar algunas Label por TextField
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
//#endregion

//#region CAMPOS_CLASE

	boolean haCambiado = false;
	int pulsado = 0;

	static Stage visorFct;
	static FxCntrlVisorFCT instancia;
	static int indexActual = 0;
//#endregion

//#region CONSTR
// TODO  - 24-06-22 : En el constructor inicializamos los campos que necesitamos listos antes de nada...
	public FxCntrlVisorFCT() throws InterruptedException, BrokenBarrierException{
		
		System.out.println("[FxCntrlVisorFCT>constructor] Arrancando el constructor del controlador FX del visorFCT");
		
// NOTE  - 24-07-18 : ¿Hace falta aquí asignar el Visor?
/*  		if (visorFct==null){
			Controlador.getControladorFacturas().cargarVisorFacturas();
			System.out.println("[FxCntrlVisorFCT>constructor] Asignado el VisorFCT con hashCode: "+ visorFct.hashCode());
		} */
	}
//#endregion

//#region GETTERS/SETTERS
	public static FxCntrlVisorFCT getFxController() {
		if (instancia==null){
			try {
				instancia = new FxCntrlVisorFCT();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
		return instancia;
	}

	public synchronized Stage getVisorFCT() throws InterruptedException, BrokenBarrierException{
		if (visorFct==null){
			System.out.println("[FxCntrlVisorFCT>getVisorFCT] El valor del visor era NULL.Se crea un visor nuevo");
			boolean ok = Controlador.getControladorFacturas().cargarVisorFacturas();
			if(ok){
				System.out.println("[FxCntrlVisorFCT>getVisorFCT] Se devuelve un visor nuevo con hashCode "+ visorFct.hashCode());
			}
		}
		return visorFct;
	}

	public void setVisor(Stage visor) {
		visorFct = visor;
	}

	public synchronized TextArea getAreaNota(){
		return this.txtAreaVNota;
	}
	public void reset(){
		this.haCambiado = false;
		this.pulsado = 0;
	}
//#endregion

//#region INI FCT/V
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			System.out.println("[FxCntrlVisorFCT>Initialize] Empezando la inicializacion del controlador FX del visorFCT");
			//ANCHOR - tableView
			if (FxCntrlTablaFCT.getFxController().getTableView()!=null){
				indexActual = FxCntrlTablaFCT.getIndiceActual();
				System.out.println("[FxCntrlVisorFCT>initialize] Index actual en tabla " + FxCntrlTablaFCT.getFxController().getTableView().hashCode() + " : " + indexActual);
				if (FxCntrlVisorFCT.getFxController()!=null){
					FxCntrlVisorFCT.visorFct = getVisorFCT();
					System.out.println("[FxCntrlVisorFCT>initialize] Llamando a actualizarDatosVisor - visor con hashCode: " + FxCntrlVisorFCT.visorFct.hashCode());
					actualizarDatosVisor(indexActual, ControladorFacturas.facturaActual);
				}
			}else{
				System.out.println("[FxCntrlVisorFCT>initialize] tabla de valor NULL. El programa se cerrará");
				System.exit(0);
			}
			System.out.println("[FxCntrlVisorFCT>Initialize] Acabando la inicializacion del controlador FX del visorFCT ");
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		
/* 		try {
			System.out.println("[FxCntrlVisorFCT>initialize>runLater] Entrando en la barreraVisor el hilo " + Thread.currentThread().getName());
			ControladorFacturas.barreraVisor.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			System.exit(0);
		} */
	}
//#endregion

//#region EventosBTN
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
//#endregion

//#region Act_VISOR
	public synchronized void actualizarDatosVisor(int index, Factura f) throws InterruptedException, BrokenBarrierException{
		try{
				Stage prueba = getVisorFCT();
				if (prueba!=null){
					visorFct = prueba;
				//if(visorFct!=null){
					System.out.println("[FxCntrlVisorFCT>actualizarDatosVisor] visorFct de hashCode: " + visorFct.hashCode());
				}
				//Thread.sleep(500);
				
// TODO  - 24-07-12 : Aquí lo dejo, parece que el visor no está inicializado, o lblVID es siempre null por otro motivo...
/* 			if(!ControladorFacturas.visorFCT.isShowing())
				ControladorFacturas.visorFCT.show(); */
			if(f!=null){
			//ANCHOR - tableView
				actualizarDatosFacturaVisor(f);
				actualizarDatosEmpresaVisor(f);
				actualizarExtractosVisor(f);
				actualizarTotalesVisor(f);
				actualizarNotaVisor(f);
				System.out.println("[FxCntrlVisorFCT>actualizarDatosVisor] Se muestra la Factura num " + index + " :\n" + f.toString());
			}else{
				System.out.println("[FxCntrlVisorFCT>actualizarDatosVisor] No se muestra ninguna factura");
			}
		} catch ( NullPointerException | InterruptedException | BrokenBarrierException  e) {
			System.out.println("[FxCntrlVisorFCT>actualizarDatosVisor] Excepc " + e + " en la carga de la Factura en el VisorFCT.");
		}
	}

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
			case 0: 
				this.lblVBase1.setText(f.getTotales().getBase()+"");
				// FIXME - 24-07-03 : Si el array de Extractos es 0...¿dónde se guarda el tipoIVA?
				//this.lblVTipoIVA1.setText(f.getExtractos().get(0).getTipoIVA()+"");
				this.lblVIVA1.setText(f.getTotales().getIVA()+"");
				this.lblVST1.setText(f.getTotales().getTotal()+"");
				break;
			default:
				//System.out.println("[FxCntrlVisorFCT>actualizarExtractosVisor] Error en el num de extractos de la factura seleccionada");
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

	public void actualizarTotalesVisor(String[] datos) throws NullPointerException{
		lblVTotalesBase.setText(datos[0]);
		lblVTotalesIVA.setText(datos[1]);
		lblVTotalesST.setText(datos[2]);
		lblVTotalesBaseNI.setText(datos[3]);
		lblVTotalesRetenciones.setText(datos[4]);

	}

	private void actualizarNotaVisor(Factura f){
		if (f.getNota()!=null)
			this.txtAreaVNota.setText(f.getNota().getTexto());
		else this.txtAreaVNota.setText("--SIN NOTA--");	
	}
//#endregion 

}

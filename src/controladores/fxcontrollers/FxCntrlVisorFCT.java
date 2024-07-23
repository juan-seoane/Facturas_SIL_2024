package controladores.fxcontrollers;

import modelo.records.Factura;
import controladores.Controlador;
import controladores.ControladorFacturas;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.BrokenBarrierException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

	@FXML TextField tfVBase1;
	@FXML TextField tfVTipoIVA1;
	@FXML TextField tfVIVA1;
	@FXML TextField tfVST1;
	@FXML TextField tfVBase2;
	@FXML TextField tfVTipoIVA2;
	@FXML TextField tfVIVA2;
	@FXML TextField tfVST2;
	@FXML TextField tfVBase3;
	@FXML TextField tfVTipoIVA3;
	@FXML TextField tfVIVA3;
	@FXML TextField tfVST3;
	@FXML TextField tfVBase4;
	@FXML TextField tfVTipoIVA4;
	@FXML TextField tfVIVA4;
	@FXML TextField tfVST4;
	
	@FXML TextField tfVTotalesBase;
	@FXML TextField tfVTotalesIVA;
	@FXML TextField tfVTotalesST;
	@FXML TextField tfVTotalesBaseNI;
	@FXML TextField tfVTotalesTipoRet;
	@FXML TextField tfVTotalesRetenciones;
	@FXML TextField tfVTotal;

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
	ControladorFacturas cfct;
	boolean haCambiado = false;
	int pulsado = 0;

	ArrayList<TextField> todoslostextfields;
	static Stage visorFct;
	static FxCntrlVisorFCT instancia;
	static int indexActual = 0;
//#endregion

//#region CONSTR
//TODO - 24-06-22 : En el constructor inicializamos los campos que necesitamos listos antes de nada...
	public FxCntrlVisorFCT() throws InterruptedException, BrokenBarrierException{
		
		System.out.println("[FxCntrlVisorFCT>constructor] Arrancando el constructor del controlador FX del visorFCT");
		
		if(this.cfct==null) this.cfct = Controlador.getControladorFacturas();

 		if (visorFct==null){
			ControladorFacturas.visorFCT = new Stage();
			visorFct = ControladorFacturas.visorFCT;
			if (visorFct!=null)
				System.out.println("[FxCntrlVisorFCT>constructor] Asignado el VisorFCT con hashCode: "+ visorFct.hashCode());
			else System.out.println("[FxCntrlVisorFCT>constructor] El VisorFCT sigue siendo NULL");
		}
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

	public synchronized Stage getVisorFCT(){
		if (ControladorFacturas.visorFCT==null){
			System.out.println("[FxCntrlVisorFCT>getVisorFCT] El valor del visor era NULL.Se crea un visor nuevo");
			Stage visor = getVisorFacturas();
				if(visor!=null){
					visorFct = visor;
					System.out.println("[FxCntrlVisorFCT>getVisorFCT] Se devuelve un visor nuevo con hashCode "+ visorFct.hashCode());
					return visorFct;
				}
		}else{
			if (visorFct!=null)
				return visorFct;
		}
		return null;
	}

	public static void setVisorFCT(Stage v) {
		visorFct = v;
	}
	private Stage getVisorFacturas() {
		return visorFct;
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
			if (cfct.getFXcontrlTablaFCT().getTableView()!=null){
				indexActual = cfct.getFXcontrlTablaFCT().getIndiceSeleccionadoTabla();
				System.out.println("[FxCntrlVisorFCT>initialize] Index actual en tabla " + cfct.getFXcontrlTablaFCT().getTableView().hashCode() + " : " + indexActual);
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
		Stage prueba = getVisorFCT();
		if (prueba!=null){
			visorFct = prueba;
		//if(visorFct!=null){
			System.out.println("[FxCntrlVisorFCT>actualizarDatosVisor] visorFct de hashCode: " + visorFct.hashCode());
		}
		//Thread.sleep(500);
				
//TODO - 24-07-12 : Aquí lo dejo, parece que el visor no está inicializado, o lblVID es siempre null por otro motivo...
/* 		if(!ControladorFacturas.visorFCT.isShowing())
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
				this.tfVBase4.setText(f.getExtractos().get(3).getBase()+"");
				this.tfVTipoIVA4.setText(f.getExtractos().get(3).getTipoIVA()+"");
				this.tfVIVA4.setText(f.getExtractos().get(3).getIVA()+"");
				this.tfVST4.setText(f.getExtractos().get(3).getTotal()+"");
			case 3: 
				this.tfVBase3.setText(f.getExtractos().get(2).getBase()+"");
				this.tfVTipoIVA3.setText(f.getExtractos().get(2).getTipoIVA()+"");
				this.tfVIVA3.setText(f.getExtractos().get(2).getIVA()+"");
				this.tfVST3.setText(f.getExtractos().get(2).getTotal()+"");			
			case 2:
				this.tfVBase2.setText(f.getExtractos().get(1).getBase()+"");
				this.tfVTipoIVA2.setText(f.getExtractos().get(1).getTipoIVA()+"");
				this.tfVIVA2.setText(f.getExtractos().get(1).getIVA()+"");
				this.tfVST2.setText(f.getExtractos().get(1).getTotal()+"");			
			case 1:
				this.tfVBase1.setText(f.getExtractos().get(0).getBase()+"");
				this.tfVTipoIVA1.setText(f.getExtractos().get(0).getTipoIVA()+"");
				this.tfVIVA1.setText(f.getExtractos().get(0).getIVA()+"");
				this.tfVST1.setText(f.getExtractos().get(0).getTotal()+"");			
				break;
			case 0: 
				this.tfVBase1.setText(f.getTotales().getBase()+"");
				// FIXME - 24-07-03 : Si el array de Extractos es 0...¿dónde se guarda el tipoIVA?
				this.tfVTipoIVA1.setText(f.getTotales().getTipoIVA()+"");
				this.tfVIVA1.setText(f.getTotales().getIVA()+"");
				this.tfVST1.setText(f.getTotales().getTotal()+"");
				break;
			default:
				System.out.println("[FxCntrlVisorFCT>actualizarExtractosVisor] Error en el num de extractos de la factura seleccionada");
				break;
		}
	}

	private void actualizarTotalesVisor(Factura f) throws NullPointerException{
		this.tfVTotalesBase.setText(f.getTotales().getBase()+"");
		this.tfVTotalesIVA.setText(f.getTotales().getIVA()+"");
		this.tfVTotalesST.setText(f.getTotales().getSubtotal()+"");
		this.tfVTotalesBaseNI.setText(f.getTotales().getBaseNI()+"");
		this.tfVTotalesTipoRet.setText(f.getTotales().getRet()+"");
		this.tfVTotalesRetenciones.setText(f.getTotales().getRetenciones()+"");
		this.tfVTotal.setText(f.getTotales().getTotal()+"");
		//lblVTitulo.setText("num de fact: " + datos[6]);

	}

	public void actualizarTotalesVisor(String[] datos) throws NullPointerException{
		tfVTotalesBase.setText(datos[0]);
		tfVTotalesIVA.setText(datos[1]);
		tfVTotalesST.setText(datos[2]);
		tfVTotalesBaseNI.setText(datos[3]);
		tfVTotalesRetenciones.setText(datos[4]);

	}

	private void actualizarNotaVisor(Factura f){
		if (f.getNota()!=null)
			this.txtAreaVNota.setText(f.getNota().getTexto());
		else this.txtAreaVNota.setText("--SIN NOTA--");	
	}
//#endregion 

//#region HELPERS
    private void addTextFields(Node node, ArrayList<TextField> textFields) {
        if (node instanceof TextField) {
            textFields.add((TextField) node);
        } else if (node instanceof Parent) {
            for (Node child : ((Parent) node).getChildrenUnmodifiable()) {
                addTextFields(child, textFields);
            }
        }
    }

	public void borrarTextfields(){
		todoslostextfields = new ArrayList<>();
        Scene scene = getVisorFCT().getScene();
        if (scene != null) {
            addTextFields(scene.getRoot(), todoslostextfields);
        }
		for(TextField tf : todoslostextfields){
			tf.setText("");
		}
		System.out.println("[FxCntrlVisorFCT>borrarTextfields] borrados los tf en Visor " + visorFct.hashCode());
	}

	public void setTFEditables(boolean editable){
		todoslostextfields = new ArrayList<>();
        Scene scene = getVisorFCT().getScene();
        if (scene != null) {
            addTextFields(scene.getRoot(), todoslostextfields);
        }	
		for (TextField tf : todoslostextfields){
			tf.setEditable(editable);
		}
		System.out.println("[FxCntrlVisorFCT>setTFEditables] actualizados los tf a ed:" + editable + " en Visor " + visorFct.hashCode());
	}
//#endregion

}

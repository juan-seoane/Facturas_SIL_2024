package controladores;

import modelo.*;
import modelo.records.Factura;
import ui.ventanas.VentanaFiltros;
import controladores.fxcontrollers.FxCntrlTablaFCT;
import controladores.fxcontrollers.FxCntrlVisorFCT;
import controladores.fxcontrollers.PanelControl;
import controladores.helpers.FxmlHelper;

import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.awt.HeadlessException;
import java.io.IOException;

public class ControladorFacturas extends Thread {
//NOTE - 14-07-24 : Singleton
    static ControladorFacturas instancia;
//#region CAMPOS_CFCT
//NOTE -14-07-24 : Se intenta que los campos sean privados y que sólo se acceda a ellos mediante getters ... sobre todo los que pueden llevar a conflictos, como los contrFX
    Controlador ctrlPpal;
    public PanelControl pc;
    static VentanaFiltros filtros;
    public ModeloFacturas m;
    static FxCntrlTablaFCT FXcontrlTablaFCT;
    static FxCntrlVisorFCT FXcontrlVisorFCT;
    Stage tablaFCT;
    //ANCHOR - visor FCT
    public static Stage visorFCT;
    //ANCHOR - tableView
    TableView<Factura> tableViewFCT;
    public static Factura facturaActual = null;
    static boolean GUIon = false;
    //public static CyclicBarrier  barreraTablaFCT;
    public static CyclicBarrier barreraVisor;
//#endregion

//#region CFCT_constr
    private ControladorFacturas() throws InterruptedException, BrokenBarrierException {
        System.out.println("[ControladorFactuCras>Constructor] Creando el ControladorFacturas...");
               
        /* barreraVisor = new CyclicBarrier(2,() -> {
            System.out.println( "[ControladorFactuCras>Constructor]  El hilo " + Thread.currentThread().getName() + " entra ahora en la barrera Visor");
        }); */
    
        m = ModeloFacturas.getModelo();
        //ANCHOR - 14-07-24 : FxCntrlTablaFCT
        if(GUIon){
            FXcontrlTablaFCT = FxCntrlTablaFCT.getFxController();
            System.out.println("[ControladorFacturas>constructor] El ControladorFX de la tablaFCT fue asignado correctamente");
        }else{      
            System.out.println("[ControladorFacturas>constructor] El ControladorFX de la tablaFCT sigue siendo NULL");
        }       
        System.out.println("[ControladorFacturas>Constructor] Saliendo del constructor de ControladorFacturas...");
    }
//#endregion

//#region GETTERS_CFCT
    public static synchronized ControladorFacturas getControlador() throws InterruptedException, BrokenBarrierException {

        if (instancia == null) {
            System.out.println("[ControladorFacturas>getControlador()] Instancia vacia, creando una nueva instancia generica (sin GUI asociada)");
            instancia = new ControladorFacturas();
        }
        
        return instancia;
    }

    public static synchronized ControladorFacturas getControlador(FxCntrlTablaFCT fxcntrfct) throws InterruptedException, BrokenBarrierException {

        if (instancia == null) {
            System.out.println("[ControladorFacturas>getControlador(fxcontr)] Instancia vacia, creando una nueva instancia generica con GUI asociada!");
            instancia = new ControladorFacturas();
        }
        GUIon = true;
        //ANCHOR - 14-07-24 : FxCntrlTablaFCT
        instancia.setFXcontrlTablaFCT(FXcontrlTablaFCT);
        return instancia;
    }
//#endregion

//#region get/set_ContrFX
    //ANCHOR - 14-07-24 : FxCntrlTablaFCT
    public synchronized FxCntrlTablaFCT getFXcontrlTablaFCT() throws InterruptedException, BrokenBarrierException{
        return FXcontrlTablaFCT;
    }
    //ANCHOR - 14-07-24 : FxCntrlVisorFCT
    public synchronized FxCntrlVisorFCT getFXcontrlVisorFCT(){
        return FXcontrlVisorFCT;
    }
    public synchronized Stage getVisorFCT(){
        return visorFCT;
    }

    public synchronized TableView<Factura> getTableViewFCT(){
        return this.tableViewFCT;
    }

    //ANCHOR - 14-07-24 : FxCntrlTablaFCT
    public synchronized void setFXcontrlTablaFCT(FxCntrlTablaFCT contr){
        FXcontrlTablaFCT = contr;
    }

    public synchronized void setFXcontrlVisorFCT(FxCntrlVisorFCT contr){
        FXcontrlVisorFCT = contr;
    }

    public synchronized void setVisorFCT(Stage v){
        visorFCT = v;
    }
    //ANCHOR - tableView
    public synchronized void setTableViewFCT(TableView<Factura> tvfct){
        this.tableViewFCT = tvfct;
    }
    
    private void colocarListenerEnTablaFCT(TableView<Factura> tabla){
        setTableViewFCT(tabla);
        //TODO -13-07-24 : Aquí la TableView 'tabla', al principio, es NULL, y el valor que se le va a asignar, también....
        tabla.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
				if (newSelection != null) {
					var index = tabla.getSelectionModel().getSelectedIndex();
                    ControladorFacturas.facturaActual =  tabla.getSelectionModel().getSelectedItem();
					FxCntrlTablaFCT.setIndiceActual(index);
					System.out.println("[FxCntrlTablaFCT>listener1] Has seleccionado el INDEX: " + FxCntrlTablaFCT.getIndiceActual() + " en la TABLAFCT");
					System.out.println("[FxCntrlTablaFCT>listener1] ...desde el hilo " + Thread.currentThread().getName());
                    try {
                        ((TableView<Factura>)(getFXcontrlTablaFCT().getTableView())).getSelectionModel().selectedIndexProperty().addListener(
                            (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                                try {
                                    FxCntrlTablaFCT.setIndiceActual(newValue.intValue());
                                    ControladorFacturas.facturaActual = ((TableView<Factura>)(getFXcontrlTablaFCT().getTableView())).getSelectionModel().getSelectedItem();
                                    System.out.println("[FxCntrlTablaFCT>listener2] Has seleccionado el INDEX: " + FxCntrlTablaFCT.getIndiceActual() + " en la TABLAFCT");
                                    System.out.println("[FxCntrlTablaFCT>listener2] ...desde el hilo " + Thread.currentThread().getName());
                            } catch (InterruptedException | BrokenBarrierException e) {
                                e.printStackTrace();
                            }
                            }
                        );
					// NOTE - 03-07-24 - Quito el reset() al controlador de la tablaFX, para que no se arme un bucle...     
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }else{
					//System.out.println("[FxCntrlTablaFCT>initialize listener] Parece que no se ha detectado la selección, a pesar de haber recogido el evento");
				}
			});
    }
//#endregion    

//#region RUN_CFCT_ppio
    @Override
    public void run() {
//REVIEW - 05-07-24 : Asignaciones cuando empieza a ejecutarse el hilo
        try {
            this.pc = Controlador.getPanelControl();
            this.ctrlPpal = Controlador.getControlador();
            //NOTE - 07-07-24 : Saco la barrera para los tests de JavaFX
            //Controlador.barreraControladores.await();
            /* if (GUIon) this.tableViewFCT = FXcontrlTablaFCT.getTableView(); */
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    
        while (true) {
//#endregion

/*      
            if (form.seHaEnviado()) {
                try {
                    if (form.getEstado().equals("editando")) {
                        recogerFormyEditar(m.leerFacturas().get(tabla.getIndice()));
                    } else {
                        recogerFormyAnexar();
                    }

                    form.reset();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "[ControladorFacturas] Se ha producido un error en la carga del formulario!");
                    form.enCasodeError(false);
                }
                visible(true);
            }
            if (form.pasoatras()){
                try{
                rellenarFormyActualizar(ModeloFacturas.getModelo().getPilaFacturasSig().push(ModeloFacturas.getModelo().getPilaFacturasAnt().pop()));
                form.borrarDatosNumericos();
               }catch(Exception ex){
                   JOptionPane.showMessageDialog(null,"[ControladorFacturas] No es posible dar más pasos atrás!");
               }
                form.getFormulario().setPasoAtras(false);
            }
            if (form.pasoadelante()){
                 try{
                rellenarFormyActualizar(ModeloFacturas.getModelo().getPilaFacturasAnt().push(ModeloFacturas.getModelo().getPilaFacturasSig().pop()));
                form.borrarDatosNumericos();
               }catch(Exception ex){
                   JOptionPane.showMessageDialog(null,"[ControladorFacturas] No es posible dar más pasos adelante!");
               }
            }
            if (visor.haCambiado()) {
                JOptionPane.showMessageDialog(null, "[ControladorFacturas] Ha pulsado un boton en el visor!");
                switch (visor.getPulsado()) {
                    case 1:	//anterior
                        anteriorFacturaVisor();
                        visor.reset();
                        visible(true);
                        break;
                    case 2:	//siguiente
                        siguienteFacturaVisor();
                        visor.reset();
                        visible(true);
                        break;
                    case 3: //editar factura
                        editarFacturaVisor();
                        visor.reset();
                        break;
                    case 5: //borrar factura
                        try {
                            borrarFacturaVisor();
                        } catch (NumberFormatException | IOException e) {
                            e.printStackTrace();
                        }
                        visible(true);
                        break;
                    case 6:
                        try {
                            guardarNotaVisor();
                        } catch (NumberFormatException | HeadlessException | IOException e) {
                            e.printStackTrace();
                        }
                        visor.reset();
                        visible(true);
                        break;
                    default:
                        //System.out.println(" [ControladorFacturas] Ninguna opcion seleccionada!");
                }
            }
*/

//#region TABLA_SWITCH
//FIXME - 13-07-24 : OJO!!! El valor de FXcontrlTablaFCT no es el mismo que en cargarTablaFacturas()!!!!
            if(FXcontrlTablaFCT!=null && FXcontrlTablaFCT.HaCambiado()) {
				//System.out.println("[ControladorFacturas>run] recogiendo evento del Controlador de Facturas - pulsado caso " + FXcontrlTablaFCT.getPulsado());
                //REVIEW:  HAY QUE PONER UN CASO CERO DONDE RECOJA EL INDICE DE LA FACTURA ACTUAL
                
                switch(FXcontrlTablaFCT.getPulsado()){
                    case 1:                  
                    System.out.println("[ControladorFacturas>run] Se muestra el Visor de Facturas");
                    mostrarVisorFCT(FxCntrlTablaFCT.getIndiceActual(), ControladorFacturas.facturaActual);
                    //actualizarVisor(tabla.getIndice());
                    break;                  
                case 2:
                    System.out.println("[ControladorFacturas>run] Se muestra el Formulario de Nueva Facturas");
                    break;
                case 3:
    //FIXME -03-07-24 - Falta el botón de 'Imprimir Tabla' en la tablaFCT
                    break;
                case 4:

                    break;
                case 5:
                    System.out.println("[ControladorFacturas>run] Se muestra la Ventana de Filtros. TableView con hashCode " + tableViewFCT.hashCode());
                    break;
                case 6:
                    break;
                }

                FXcontrlTablaFCT.reset();  
            }
//#endregion

//#region VISOR_SWITCH
            // FIXME - 03-07-24 : Hacer que el Visor funcione también como formulario (quizás haya que ponerle un botón enviar cuando Edites o Insertes una Factura)
            if(FXcontrlVisorFCT!=null && FXcontrlVisorFCT.HaCambiado()) {
				System.out.println("[ControladorFacturas>run] recogiendo evento del visorFCT en el Controlador de Facturas - pulsado caso " + FXcontrlVisorFCT.getPulsado());
                int elem = FXcontrlTablaFCT.getIndiceSeleccionadoTabla();
                System.out.println("[ControladorFacturas>visorSwitch] indice seleccionado en tablaFCT " + FXcontrlTablaFCT.tblvwFct.hashCode() +  " : " + elem);
                switch(FXcontrlVisorFCT.getPulsado()){
                    case 1:
                        try {
                            ocultarVisorFCT();
                        } catch (InterruptedException | BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                        //System.out.println("[ControladorFacturas>run] Se cerrará el Visor de Facturas");
                        break;
                    case 2:
                        //System.out.println("[ControladorFacturas>run] Se activará el Formulario de Nueva Factura");
                        break;
                    case 3:
                        //System.out.println("[ControladorFacturas>run] Se activará la Ediion del VisorFCT para la Factura actual");
                        break;
                    case 4:
                        //System.out.println("[ControladorFacturas>run] Se borrará la Factura actual");
                        break;
                    case 5:
                        //System.out.println("[ControladorFacturas>run] La seleccion de la tablaFCT se movera hacia arriba");
                        break;
                    case 6:
                        //System.out.println("[ControladorFacturas>run] La seleccion de la tablaFCT se movera hacia abajo");
                        break;
                }
                FXcontrlVisorFCT.reset();
            }
//#endregion

//#region LATENCIA_SW
            try {
                //System.out.println("[controladorFacturas] En el sleep");
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }
//#endregion

//#region (VisorOps)
    public boolean anteriorFacturaVisor() throws NullPointerException, IOException {
		//System.out.println("[ControladorFacturas] Ha pulsado en el visorFCT el boton atras. ");
        int i = FXcontrlTablaFCT.getIndiceSeleccionadoTabla();
        List<Factura> lista = m.leerFacturas();
        if (i > 0) {
// TODO : 30-06-2024 - HAY QUE AJUSTAR EL VISOR PARA QUE SELECCIONE LA FACTURA ADECUADA DESDPUES DE ACTIVAR EL FILTRO 
            int nuevoindice = i-1;
            //System.out.println(" [ControladorFacturas] Actualizando Visor!");
            actualizarVisor(nuevoindice);
            //System.out.println(" [ControladorFacturas] Actualizando tabla");
            FXcontrlTablaFCT.seleccionarIndiceTabla(nuevoindice);
        } else {
            int nuevoindice = lista.size()-1;
            //System.out.println(" [ControladorFacturas] Actualizando Visor!");
            actualizarVisor(nuevoindice);
            //System.out.println(" [ControladorFacturas] Actualizando tabla");
            FXcontrlTablaFCT.seleccionarIndiceTabla(nuevoindice);
        }
        //JOptionPane.showMessageDialog(null,"[ControladorFacturas] Index Visor: "+visor.getIndex()+" Index Tabla: "+tabla.getIndice());
        FXcontrlTablaFCT.reset();
        return true;
    }

    public boolean siguienteFacturaVisor() throws NullPointerException, IOException {
		System.out.println("[ControladorFacturas] Ha pulsado en el visorFCT el boton adelante.");
        int i = FXcontrlTablaFCT.getIndiceSeleccionadoTabla();
        List<Factura> lista = m.leerFacturas();
        if (i < (lista.size() - 1)) {
            int nuevoindice = i+1;
            //System.out.println(" [ControladorFacturas] Actualizando Visor!");
            actualizarVisor(nuevoindice);
            //System.out.println(" [ControladorFacturas] Actualizando tabla");
            FXcontrlTablaFCT.seleccionarIndiceTabla(nuevoindice);
            //System.out.println(" [ControladorFacturas] Index TablaFCT: " + FXcontrlTablaFCT.getIndiceSeleccionadoTabla());
        } else {
            int nuevoindice = 0;
            //System.out.println(" [ControladorFacturas] Actualizando Visor!");
            actualizarVisor(0);
            //System.out.println(" [ControladorFacturas] Actualizando tabla");
            FXcontrlTablaFCT.seleccionarIndiceTabla(nuevoindice);
        }
        //System.out.println("[ControladorFacturas]  Index TablaFCT: " + FXcontrlTablaFCT.getIndiceSeleccionadoTabla());
        FXcontrlTablaFCT.reset();
        return true;
    }

    public boolean editarFacturaVisor() {
/*        form.setEstado("editando");
        form.insertarFactura(m.getFactura(visor.getIndex()));
        form.setVisible(true);
        form.toFront(); */
        return true;
    }

    public boolean borrarFacturaVisor() throws NumberFormatException, IOException {
       System.out.println("[ControladorFacturas>borrarFacturaVisor()] Ha pulsado el boton borrar factura");
/*        int i = tabla.getIndice();
        System.out.println(" [ControladorFacturas] Enviando desde el controlador factura con indice " + i);
        Factura fact = m.getFactura(i);
        JOptionPane.showMessageDialog(null, "[ControladorFacturas] Recogiendo los datos de la factura");
        anteriorFacturaVisor();
        m.borrarFactura(fact, i);
        visor.reset();
		actualizarVisor(i-1);
        actualizarTabla(i - 1); */

        return true;
    }

    public boolean guardarNotaVisor() throws NumberFormatException, HeadlessException, IOException{
        /*
                Factura f = m.getFactura(visor.getIndex());
                f.setNota(visor.getNota());
                if (m.editarFactura((ArrayList<Factura>)m.leerFacturas(),f,visor.getIndex()))
                    JOptionPane.showMessageDialog(null,"[ControladorFacturas] Nota guardada!");
        */
                //System.out.println("[ControladorFacturas>guardarNotaVisor()] ");
                return true;
            }
            
    public boolean actualizarVisor(int index) {
/*
        JOptionPane.showMessageDialog(null,"[ControladorFacturas] Actualizando Visor!");
        Factura f = new Factura();
        List<Factura> lista = m.leerFacturas();
        if (lista.size() > 0)
            f = (Factura) lista.get(index);
        else 
            index = 0;
        visor = VisorFacturas.getVisor(index, f);
*/
        //System.out.println("[ControladorFacturas>actualizarVisor()] Actualizar Visor a index "+ index);
        return true;
    }    
//#endregion

//#region (FormOps)
    public boolean recogerFormyEditar(Factura f) throws NumberFormatException, IOException {
/*		JOptionPane.showMessageDialog(null,"[ControladorFacturas] Enviado :: Indice en Controlador: "+visor.getIndex());
        Factura facturaTemp = m.recogerFormulario(form);
        int sel = m.facturas.indexOf(f);
        m.facturas.remove(f);
        m.facturas.add(facturaTemp);
        m.insertarFacturas((ArrayList<Factura>)m.facturas);
        
        //System.out.println(" [ControladorFacturas] Index3: "+visor.getIndex());
        actualizarTabla(sel);
        limpiarFormyActualizar(tabla.getIndice());
        return true;
*/
        //System.out.println("[ControladorFacturas>recogerFormyEditar()] ");
        return true;
    }
 
    public boolean recogerFormyAnexar() throws NumberFormatException, IOException {
/*
        Factura f = m.recogerFormulario(form);  

        m.facturas.add(f);
        m.insertarFacturas((ArrayList<Factura>)m.facturas);
        int sel = m.facturas.indexOf(f);
        actualizarTabla(sel);
        limpiarFormyActualizar(tabla.getIndice());
*/
        //System.out.println("[ControladorFacturas>recogerFormyAnexar()] ");
        return true;
    }

    public boolean limpiarFormyActualizar(int index) {
/*
        form.limpiarFormulario();
        actualizarVisor(index);
        actualizarTabla(index);
*/
        //System.out.println("[ControladorFacturas>limpiarFormyActualizar()] ");        
        return true;
    }
   
    public boolean rellenarFormyActualizar(Factura f) {
/*         
        int index = m.getIndexOfFactura(f);
        
        form.insertarFactura(m.getFactura(index));
        actualizarVisor(index);
        actualizarTabla(index);
*/
        //System.out.println("[ControladorFacturas>rellenarFormyActualizar()] ");               
        return true;
    }   

    public boolean actualizarFormulario() {
/*
        JOptionPane.showMessageDialog(null,"[ControladorFacturas] Actualizando Formulario!");
        int tempx = form.getX();
        int tempy = form.getY();

        form = FormularioFact.getFormulario();
        form.setVisible(true);
        form.toFront();
*/        
        //System.out.println("[ControladorFacturas>actualizarFormulario()] ");
        return true;
    }
//#endregion 

//#region (TablaOps)
    public boolean actualizarTabla(int sel) throws NullPointerException, IOException {

        JOptionPane.showMessageDialog(null,"[ControladorFacturas] Actualizando Tabla!");
        //List<Factura> facturas = m.leerFacturasSinFiltrar();
        String[] totales = m.calcularTotales();
        /*
        Vector<Factura> vectorFacturas = new Vector<Factura>();
        if (ModeloFacturas.numeroFacturas>0){
            for (Factura f : facturas) {
                vectorFacturas.add(f);
            }
        } 
        else vectorFacturas.add(new Factura());

        try {
            tabla.actualizarModelo(vectorFacturas, sel);
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
        */
        FXcontrlTablaFCT.seleccionarIndiceTabla(sel);
        FXcontrlTablaFCT.actualizarTotales(totales);

        
        //System.out.println("[ControladorFacturas>actualizarTabla] Actualizar tabla  de hashCode " + tableViewFCT.hashCode() + " a index " + sel);
        return true;
    }
//#endregion

//#region (TABLA_PDF)
/*    
    public boolean imprimirTabla() {
        String titulo = JOptionPane.showInputDialog("[ControladorFacturas] Escriba el título del informe, por favor: ");
        JOptionPane.showMessageDialog(null, "[ControladorFacturas] pulse OK y espere a que se genere el informe!");

        List<Factura> facturas = m.leerFacturas();
        FacturasDataSource datasource = new FacturasDataSource();

        for (Factura f : facturas) {
            datasource.addFactura(f);
        }
        JOptionPane.showMessageDialog(null,"[ControladorFacturas] facturas añadidas a la cola de impresion!");
        try {
            Fichero directoriopersonal = new Fichero("informes/"+Config.getConfig().getUsuario()+"/");
            
            JasperReport reporte = (JasperReport) JRLoader.loadObjectFromLocation("adjuntos/informe01.jasper");

            //mapa de parámetros para enviar al informe
                HashMap hm = new HashMap();
                hm.put("titulo",titulo);
                hm.put("anho", Config.getConfig().getAnho().getAnho()+" trimestre "+Config.getConfig().getAnho().getTrimestre());
                hm.put("empresa", Config.getConfig().getMiRS().getNombre());
                hm.put("razonsocial", Config.getConfig().getMiRS().getRazon());
                hm.put("nif", Config.getConfig().getMiRS().getNIF().toString());
                hm.put("telefono", Config.getConfig().getMiRS().getTelefono());
                hm.put("direccion", Config.getConfig().getMiRS().getDireccion());
                hm.put("codigopostal", Config.getConfig().getMiRS().getCP());
                hm.put("poblacion", Config.getConfig().getMiRS().getPoblacion());
                
                try{
                    BufferedImage bi = ImageIO.read(new File("adjuntos/coffee.png"));  

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(bi, "png", baos );
                    baos.flush();

                    byte[] imageInByte = baos.toByteArray();
                    baos.close();
                    hm.put("logo",new ByteArrayInputStream(imageInByte));
                }catch(IOException ioe){
                    JOptionPane.showMessageDialog(null, "[ControladorFacturas] El logo no ha sido cargado");
                    hm.put("logo",null);
                }
//                InputStream is = new ByteArrayInputStream(imageInByte);

                
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, hm, datasource);

            JRExporter exporter = new JRPdfExporter();
            //parameters used for the destined file.
            String rutaInforme = "informes/"+Config.getConfig().getUsuario()+"/"+"IVA"+ Config.getConfig().getAnho().getAnho() + "_" + Config.getConfig().getAnho().getTrimestre() + ".pdf";

            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, rutaInforme);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            //export to .pdf  
            exporter.exportReport();
            JOptionPane.showMessageDialog(null, "[ControladorFacturas] informe guardado en FACTURASv20/" + rutaInforme);

            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle("Informe de Facturacion");
            viewer.setVisible(true);
            viewer.setAlwaysOnTop(true);
            viewer.setAutoRequestFocus(true);

        } catch (JRException jrex) {
            JOptionPane.showMessageDialog(null, "[ControladorFacturas] Error " + jrex + " al imprimir el informe");
            return false;
        }
        return true;

    }
*/
//#endregion

//#region CARG_T/FCT
    public synchronized boolean cargarTablaFacturas() throws InterruptedException, BrokenBarrierException {
        
        if(FXcontrlTablaFCT == null){
            Platform.runLater(new Runnable(){

                Stage tabla;

                @Override
                public void run() {
                    FxmlHelper loader = new FxmlHelper("../../resources/fxmltablaFCT.fxml");
                    Parent root;
                    root = loader.cargarFXML();
                    FxCntrlTablaFCT contrFxtablaTemp = (FxCntrlTablaFCT)loader.getFXcontr();
                    System.out.println("[ControladorFacturas] Controlador FX para tabla de FCT asignado: " + FXcontrlTablaFCT.hashCode());
                    Scene escena = new Scene(root);
                    tabla = new Stage();
                    tabla.setScene(escena);
                    tabla.setResizable(true);
                    // TODO : 30-05-2024 - Aquí se ajusta el modo de la ventana de la TablaFCT
                    //this.tablaFCT.initModality(Modality.NONE);
                    //ANCHOR - Asignar Stage T/FCT a CFCT
                    System.out.println("[CFCT>cargarTablaFacturas] Stage de Tabla con hashcode :" + tabla.hashCode());
                    tablaFCT = tabla;
                    System.out.println("[CFCT>cargarTablaFacturas] Stage de Tabla asignado a ControladorFacturas.tablaFCT con valor :" + tablaFCT.hashCode());
                    //ANCHOR - 14-07-24 : Asignar contrFX de T/FCT a CFCT
                    setFXcontrlTablaFCT(contrFxtablaTemp);
                    if(FXcontrlTablaFCT==null){
                        System.out.println("[CFCT>cargarTablaFacturas] El contrlFX de la tablaFCT es NULL. El programa se cierra!!!");
                        System.exit(0);
                    }

                    //ANCHOR - Asignar tableView a CFCT
                    try {
                        setTableViewFCT(FXcontrlTablaFCT.getTableView());
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
    
                    //ANCHOR - tableView
                    //TODO -13-07-24 : Aquí la TableView, al principio, es NULL, y el valor que se le va a asignar, también....
                    try {
                        colocarListenerEnTablaFCT(ControladorFacturas.getControlador().tableViewFCT);
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    System.out.println("[ControladorFacturas>cargarTablaFacturas] Cargada y asignada la tabla FCT con hashCode " + tableViewFCT.hashCode());
                    }
                });
            return true;
        }
        else {
            //FIXME - 14-07-24 : Falta algo aquí... arreglar - Falta comprobar si es el controladorFX correcto
            return false;
        }
    }
//#endregion

//#region MOSTR_T/FCT
public synchronized void mostrarTablaFacturas() {
        
    Platform.runLater(new Runnable(){
        @Override
        public void run(){
            //FIXME -  02-07-24 : Tendré que SINCRONIZAR MEDIANTE BARRERAS, para controlar que los hilos esperen hasta que esté activo el modeloFCT, o los elementos de la GUI correspondiente
            //ANCHOR - Mostrar T/FCT
            System.out.println("[controladorFacturas>mostrarTablaFacturas] tableView con hashCode " + tableViewFCT.hashCode());
            tablaFCT.show();
            System.out.println("[controladorFacturas>mostrarTablaFacturas]Stage de tablaFCt " + tablaFCT.hashCode());

        }
    });
    //REVIEW - 29-06-2024 - Hay que cargar el modeloFCT para que no sea NULL eventualmente (en los test)
    m = ModeloFacturas.getModelo();
    }

public synchronized void ocultarTablaFacturas() {
    Platform.runLater(new Runnable(){
        @Override
        public void run(){
            //System.out.println("[ControladorFacturas>ocultarTablaFacturas] Se oculta  la tabla FCT de hashCode " + tableViewFCT.hashCode() + "\n******************");
            tablaFCT.hide();
        }
    }
    );
    
    //this.tableViewFCT = null;
   
}
//#endregion

//#region C+M_V/FCT
public synchronized boolean cargarVisorFacturas() throws InterruptedException, BrokenBarrierException {
    if(FXcontrlVisorFCT == null){
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                Stage visor;
                FxmlHelper loader = new FxmlHelper("../../resources/visorFormFCT.fxml");

                Parent root;
        
                root = loader.cargarFXML();
                FXcontrlVisorFCT = (FxCntrlVisorFCT)loader.getFXcontr();
                System.out.println("[ControladorFacturas] FXcontrlVisorFCT" + FXcontrlVisorFCT.hashCode());
                Scene escena = new Scene(root);
                visor = new Stage();
        
                visor.setScene(escena);
                visor.setResizable(true);
                visor.show();
                //ANCHOR - Asignar V/FCT
                ControladorFacturas.visorFCT = visor;
                System.out.println("[ControladorFacturas>cargarVisorFacturas] asignado ContrlFact.visorFCT: " + ControladorFacturas.visorFCT.hashCode());             
                }
            });
            return true;
        }else{
            ControladorFacturas.visorFCT = FxCntrlVisorFCT.getFxController().getVisorFCT();
            if (ControladorFacturas.visorFCT!=null)
                return true;
            else return false;
        }
    }

public synchronized void mostrarVisorFCT(int index, Factura f){   
    try {
        System.out.println("[ControladorFacturas>mostrarVisorFCT] entrando en la barreraVisor desde el hilo " + Thread.currentThread().getName());
        //NOTE - 13-07-24 : Ojo, hay que actualizar la facturta Actual del Controlador de Facturas manualmente...
        ControladorFacturas.facturaActual = f;
        //NOTE - 13-07-24 : Ojo, hay que actualizar el index Actual del ControladorFX de la TablaFCT manualmente...
        FxCntrlTablaFCT.setIndiceActual(index);
        if(!cargarVisorFacturas()){
            System.out.println("[CFCT>mostrarVisorFCT] No se ha podido cargar correctsamente el VisorFCT. Chau!!!");
            System.exit(0);
        }
        setFXcontrlVisorFCT(FxCntrlVisorFCT.getFxController());
        setVisorFCT(FxCntrlVisorFCT.getFxController().getVisorFCT());
        //ControladorFacturas.barreraVisor.await();
    } catch (InterruptedException | BrokenBarrierException e) {
        System.exit(0);
    }
        
    Platform.runLater(new Runnable(){
        @Override
        public void run() {
            try {
                System.out.println("[ControladorFacturas>mostrarVisorFCT>runLater] Llamando a actualizarDatosVisor: ");
                System.out.println("[ControladorFacturas>mostrarVisorFCT>runLater] index: "+index + "\nfactura: \n" + f.toString());
                FXcontrlVisorFCT.actualizarDatosVisor(index, f);
                System.out.println("[ControladorFacturas>mostrarVisorFCT>runLater] se muestra el visorFCT del contrFX con hashCode: " + FxCntrlVisorFCT.getFxController().getVisorFCT().hashCode());
            } catch (InterruptedException | BrokenBarrierException  e) {
                e.printStackTrace();
            }
        }
    });
    System.out.println("[ControladorFacturas] Se muestra el VisorFCT cuya instancia en el CFCT tiene el valor " + visorFCT.hashCode());
}

public synchronized void ocultarVisorFCT() throws InterruptedException, BrokenBarrierException{
    Platform.runLater(new Runnable(){
        @Override
        public void run() {
            try {
                FxCntrlVisorFCT.getFxController().getVisorFCT().hide();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    });
    System.out.println("[ControladorFacturas>ocultarVisorFCT] Se oculta el VisorFCT " + FxCntrlVisorFCT.getFxController().getVisorFCT().hashCode());
}
//#endregion

//#region (VISIB)
//#endregion

//#region (FILTRAR)
//#endregion

//#region (AUTOSAVE)      
//#endregion

}

package controladores;

import modelo.*;
import modelo.records.Factura;
import controladores.fxcontrollers.FxCntrlTablaFCT;
import controladores.fxcontrollers.FxCntrlVisorFCT;
import controladores.helpers.FxmlHelper;

import java.awt.HeadlessException;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.util.*;
import java.util.concurrent.*;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ControladorFacturas extends Thread {
// NOTE   - 24-07-14 : Singleton
    static ControladorFacturas instancia;
//#region CAMPOS_CFCT
// NOTE   - 24-07-14 : Se intenta que los campos sean privados y que sólo se acceda a ellos mediante getters ... sobre todo los que pueden llevar a conflictos, como los contrFX
// REVIEW - 24-07-19 : Campos temporales (sólo para asignación indirecta)   
    private static FxCntrlTablaFCT cntrFXtabla = null;
    private static FxCntrlVisorFCT cntrFXvisor = null;
    private static Stage GUItabla = null;
    private static Stage GUIvisor = null;

    public static Factura facturaActual = null;
    static boolean GUIon = false;
    //public static CyclicBarrier  barreraTablaFCT;
    public static CyclicBarrier barreraVisor;
//#endregion

//#region CFCT_constr
    private ControladorFacturas() {
        System.out.println("[ControladorFactuCras>Constructor] Creando el ControladorFacturas...");

        /* barreraVisor = new CyclicBarrier(2,() -> {
            //System.out.println( "[ControladorFactuCras>Constructor]  El hilo " + Thread.currentThread().getName() + " entra ahora en la barrera Visor");
        }); */
        if(GUItabla==null)
            cargarTablaFacturas();
// NOTE  - 24-07-19 : Entonces, no se carga el visorFCT al arrancar, desde el constructor del CFCT
//       cargarVisorFacturas();

        System.out.println("[ControladorFacturas>Constructor] Saliendo del constructor de ControladorFacturas...");
    }
//#endregion

//#region GETTERS/SETTERS_CFCT
    public static synchronized ControladorFacturas getControlador() {

        if (instancia == null) {
            //System.out.println("[ControladorFacturas>getControlador()] Instancia vacia, creando una nueva instancia generica (sin GUI asociada)");
            instancia = new ControladorFacturas();
        }
        return instancia;
    }

    public static synchronized ControladorFacturas getControlador(FxCntrlTablaFCT fxcntrfct){

        if (instancia == null) {
            //System.out.println("[ControladorFacturas>getControlador(fxcontr)] Instancia vacia, creando una nueva instancia generica con GUI asociada!");
            instancia = new ControladorFacturas();
        }
        GUIon = true;
        //ANCHOR - 14-07-24 : FxCntrlTablaFCT
        // NOTE  - 24-07-18 : ¿Hace falta esto?
        //instancia.setFXcontrlTablaFCT(FxCntrlTablaFCT.getFxController());
        return instancia;
    }
        
    private void setFacturaActual(Factura f) {
        facturaActual = f;
        System.out.println("[ControladorFacturas>setFacturaActual] Asignada Factura: " + facturaActual.toString());
    }

//#endregion

//#region get/set_ContrFX
    //ANCHOR - 14-07-24 : FxCntrlTablaFCT
    // NOTE  - 24-07-18 : ¿Hace falta esto?
    public synchronized FxCntrlTablaFCT getFXcontrlTablaFCT() {
        FxCntrlTablaFCT fxcntrtabla = null;
        fxcntrtabla = FxCntrlTablaFCT.getFxController();
        return fxcntrtabla;
    }

    public static synchronized void setFXcontrlTablaFCT(FxCntrlTablaFCT contr){
        FxCntrlTablaFCT.setFxController(contr);
        cntrFXtabla = contr;
    }

    //ANCHOR - 14-07-24 : FxCntrlVisorFCT
    public synchronized FxCntrlVisorFCT getFXcontrlVisorFCT(){
        FxCntrlVisorFCT fxcntrlvisor = null;
        fxcntrlvisor = FxCntrlVisorFCT.getFxController();
        return fxcntrlvisor;
    }
    
    private static void setFXcontrlVisorFCT(FxCntrlVisorFCT cntr) {
        FxCntrlVisorFCT.setFXcontroller(cntr);
    }

    public synchronized Stage getVisorFCT(){
        Stage visorFCT = null;
        visorFCT = FxCntrlVisorFCT.getFxController().getVisorFCT();
        return visorFCT;
    }

    public synchronized TableView<Factura> getTableViewFCT(){
        TableView<Factura> tbvw = FxCntrlTablaFCT.getFxController().getTableView();
        colocarListenerEnTablaFCT(tbvw);
        return tbvw;
    }

    //ANCHOR - 14-07-24 : FxCntrlTablaFCT

    public synchronized void setVisorFCT(Stage v){
        FxCntrlVisorFCT.getFxController().setVisor(v);
        }
    //ANCHOR - tableView
    public synchronized void setTableViewFCT(TableView<Factura> tvfct){
        FxCntrlTablaFCT.getFxController().setTableView(tvfct);
        
    }
//#endregion

//#region ListenerTabla    
    public void colocarListenerEnTablaFCT(TableView<Factura> tabla){
        setTableViewFCT(tabla);
        System.out.println("Listener colocado en TableView: " + tabla.hashCode());
        // TODO  - 24-07-13 : Aquí la TableView 'tabla', al principio, es NULL, y el valor que se le va a asignar, también....
        tabla.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
				if (newSelection != null) {
					var index = tabla.getSelectionModel().getSelectedIndex();
                    ControladorFacturas.facturaActual =  tabla.getSelectionModel().getSelectedItem();
					FxCntrlTablaFCT.setIndiceActual(index);
					System.out.println("[FxCntrlTablaFCT>listener1] Has seleccionado el INDEX: " + FxCntrlTablaFCT.getIndiceActual() + " en la TABLAFCT");
					System.out.println("[FxCntrlTablaFCT>listener1] ...desde el hilo " + Thread.currentThread().getName());

                    ((TableView<Factura>)(getFXcontrlTablaFCT().getTableView())).getSelectionModel().selectedIndexProperty().addListener(
                    (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {

                        FxCntrlTablaFCT.setIndiceActual(newValue.intValue());
                        ControladorFacturas.facturaActual = ((TableView<Factura>)(getFXcontrlTablaFCT().getTableView())).getSelectionModel().getSelectedItem();
                        System.out.println("[FxCntrlTablaFCT>listener2] Has seleccionado el INDEX: " + FxCntrlTablaFCT.getIndiceActual() + " en la TABLAFCT");
                        System.out.println("[FxCntrlTablaFCT>listener2] ...desde el hilo " + Thread.currentThread().getName());

                    }
                ); 
					// NOTE - 03-07-24 - Quito el reset() al controlador de la tablaFX, para que no se arme un bucle...     
                }else{
					//System.out.println("[FxCntrlTablaFCT>initialize listener] Parece que no se ha detectado la selección, a pesar de haber recogido el evento");
				}
			});
    }

//#endregion    

//#region RUN_CFCT_ppio
    @Override
    public void run() {
        System.out.println("[ControladorFacturas] Comenzando el 'run'");
        //REVIEW - 24-07-05 : Asignaciones cuando empieza a ejecutarse el hilo
        //NOTE - 07-07-24 : Saco la barrera para los tests de JavaFX
        //Controlador.barreraControladores.await();
        /* if (GUIon) this.tableViewFCT = FXcontrlTablaFCT.getTableView(); */
   
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
//FIXME - 24-07-03 : OJO!!! El valor de FXcontrlTablaFCT no es el mismo que en cargarTablaFacturas()!!!!
            if(ControladorFacturas.GUItabla!=null && FxCntrlTablaFCT.getFxController().HaCambiado()) {
				System.out.println("[ControladorFacturas>run] recogiendo evento del Controlador de Facturas " + this.hashCode() + " - pulsado caso " + FxCntrlTablaFCT.getFxController().getPulsado());
                //REVIEW -  HAY QUE PONER UN CASO CERO DONDE RECOJA EL INDICE DE LA FACTURA ACTUAL
                
            switch(FxCntrlTablaFCT.getFxController().getPulsado()){
                case 1:
                    if (GUIvisor==null){
                        cargarVisorFacturas();
                        System.out.println("[ControladorFacturas>run] El visor era NULL. Se ha llamado a la funcion cargarVisorFacturas.");
                    }
                    mostrarVisorFacturas(FxCntrlTablaFCT.getIndiceActual(), ControladorFacturas.facturaActual);
                    System.out.println("[ControladorFacturas>run] Se muestra el Visor de Facturas");
                    actualizarVisor(FxCntrlTablaFCT.getIndiceActual());
                    break;
                case 2:
                    System.out.println("[ControladorFacturas>run] Se muestra el Formulario de Nueva Facturas");
                    break;
                case 3:
    //FIXME - 24-07-03 : Falta el botón de 'Imprimir Tabla' en la tablaFCT
                    break;
                case 4:

                    break;
                case 5:
                    System.out.println("[ControladorFacturas>run] Se muestra la Ventana de Filtros. TableView con hashCode " + getTableViewFCT().hashCode());
                    break;
                case 6:
                    break;
                }

                FxCntrlTablaFCT.getFxController().reset();  
            }
//#endregion

//#region VISOR_SWITCH
            // FIXME - 24-07-03 : Hacer que el Visor funcione también como formulario (quizás haya que ponerle un botón enviar cuando Edites o Insertes una Factura)
            if(Controlador.getControladorFacturas().getFXcontrlVisorFCT()!=null && FxCntrlVisorFCT.getFxController().HaCambiado()) {
				System.out.println("[ControladorFacturas>run] recogiendo evento del visorFCT en el Controlador de Facturas - pulsado caso " + FxCntrlVisorFCT.getFxController().getPulsado());
                System.out.println("[ControladorFacturas>visorSwitch] indice seleccionado en tablaFCT " + FxCntrlTablaFCT.getFxController().getTableView().hashCode());
                switch(FxCntrlVisorFCT.getFxController().getPulsado()){
                    case 1:
                        try {
                            ocultarVisorFCT();
                        } catch (InterruptedException | BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                        System.out.println("[ControladorFacturas>run] Se cierra el Visor de Facturas del CFCT -> " + Controlador.getControladorFacturas().hashCode());
                        break;
                    case 2:
                        System.out.println("[ControladorFacturas>run] Se activa el Formulario de Nueva Factura");
                        break;
                    case 3:
                        System.out.println("[ControladorFacturas>run] Se activa la Ediion del VisorFCT para la Factura actual");
                        break;
                    case 4:
                        System.out.println("[ControladorFacturas>run] Se borra la Factura actual");
                        break;
                    case 5:
                        System.out.println("[ControladorFacturas>run] La seleccion de la tablaFCT se movera hacia arriba");
                        break;
                    case 6:
                        System.out.println("[ControladorFacturas>run] La seleccion de la tablaFCT se movera hacia abajo");
                        break;
                }
                FxCntrlVisorFCT.getFxController().reset();
            }
//#endregion

//#region LATENCIA_SW
            try {
                //System.out.println("[controladorFacturas] En el sleep");
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("[controladorFacturas] Aplicacion finalizada");
                System.exit(0);
            }
        }
    }
//#endregion

//#region (VisorOps)
    public boolean anteriorFacturaVisor() throws NullPointerException, IOException {
		//System.out.println("[ControladorFacturas] Ha pulsado en el visorFCT el boton atras. ");
        int i = FxCntrlTablaFCT.getIndiceActual();
        List<Factura> lista = ModeloFacturas.getModelo().leerFacturas();
        if (i > 0) {
//  TODO  - 24-06-30 : HAY QUE AJUSTAR EL VISOR PARA QUE SELECCIONE LA FACTURA ADECUADA DESDPUES DE ACTIVAR EL FILTRO 
            int nuevoindice = i-1;
            //System.out.println(" [ControladorFacturas] Actualizando Visor!");
            actualizarVisor(nuevoindice);
            //System.out.println(" [ControladorFacturas] Actualizando tabla");
            FxCntrlTablaFCT.getFxController().seleccionarIndiceTabla(nuevoindice);
        } else {
            int nuevoindice = lista.size()-1;
            //System.out.println(" [ControladorFacturas] Actualizando Visor!");
            actualizarVisor(nuevoindice);
            //System.out.println(" [ControladorFacturas] Actualizando tabla");
            FxCntrlTablaFCT.getFxController().seleccionarIndiceTabla(nuevoindice);
        }
        //JOptionPane.showMessageDialog(null,"[ControladorFacturas] Index Visor: "+visor.getIndex()+" Index Tabla: "+tabla.getIndice());
        FxCntrlTablaFCT.getFxController().reset();
        return true;
    }

    public boolean siguienteFacturaVisor() throws NullPointerException, IOException {
		//System.out.println("[ControladorFacturas] Ha pulsado en el visorFCT el boton adelante.");
        int i = FxCntrlTablaFCT.getFxController().getIndiceActual();
        List<Factura> lista = ModeloFacturas.getModelo().leerFacturas();
        if (i < (lista.size() - 1)) {
            int nuevoindice = i+1;
            //System.out.println(" [ControladorFacturas] Actualizando Visor!");
            actualizarVisor(nuevoindice);
            //System.out.println(" [ControladorFacturas] Actualizando tabla");
            FxCntrlTablaFCT.getFxController().seleccionarIndiceTabla(nuevoindice);
            //System.out.println(" [ControladorFacturas] Index TablaFCT: " + FXcontrlTablaFCT.getIndiceSeleccionadoTabla());
        } else {
            int nuevoindice = 0;
            //System.out.println(" [ControladorFacturas] Actualizando Visor!");
            actualizarVisor(0);
            //System.out.println(" [ControladorFacturas] Actualizando tabla");
            FxCntrlTablaFCT.getFxController().seleccionarIndiceTabla(nuevoindice);
        }
        //System.out.println("[ControladorFacturas]  Index TablaFCT: " + FXcontrlTablaFCT.getIndiceSeleccionadoTabla());
        FxCntrlTablaFCT.getFxController().reset();
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
       //System.out.println("[ControladorFacturas>borrarFacturaVisor()] Ha pulsado el boton borrar factura");
/*        int i = tabla.getIndice();
        //System.out.println(" [ControladorFacturas] Enviando desde el controlador factura con indice " + i);
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
        String[] totales = ModeloFacturas.getModelo().calcularTotales();
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
        FxCntrlTablaFCT.getFxController().seleccionarIndiceTabla(sel);
        FxCntrlTablaFCT.getFxController().actualizarTotales(totales);

        
        System.out.println("[ControladorFacturas>actualizarTabla] Valor recogido de FxCntrlTablaFCT>tableView  " + FxCntrlTablaFCT.getFxController().getTableView().hashCode() + " a index " + sel);
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
    public synchronized boolean cargarTablaFacturas() {
        Platform.runLater(new Runnable(){
            Stage tabla;
            @Override
            public void run() {
                FxmlHelper loader = new FxmlHelper("../../resources/fxmltablaFCT.fxml");
                Parent root;
                root = loader.cargarFXML();
                FxCntrlTablaFCT cntrFXt = (FxCntrlTablaFCT)loader.getFXcontr();
                System.out.println("[ControladorFacturas] Controlador FX para tabla de FCT : " + cntrFXt.hashCode());
                Scene escena = new Scene(root);
                tabla = new Stage();
                tabla.setScene(escena);
                tabla.setResizable(true);
                                    
                // TODO   - 24-05-30 : Aquí se ajusta el modo de la ventana de la TablaFCT
                //this.tablaFCT.initModality(Modality.NONE);
                // REVIEW - 24-07-19 : Asignar Stage T/FCT y contrFX a CFCT
                GUItabla = tabla;
                cntrFXtabla = cntrFXt;
                System.out.println("[CFCT>cargarTablaFacturas>runLater] Stage de Tabla " + tabla.hashCode() + " guardado en el CFCT>GUItabla " + GUItabla.hashCode());
                System.out.println("[CFCT>cargarTablaFacturas>runLater] ContrFX de Tabla " + cntrFXt.hashCode() + " guardado en el CFCT>cntrFXtabla " + cntrFXtabla.hashCode());
            }
        });
        return true;
    }
//#endregion

//#region MOSTR_T/FCT
    public synchronized void mostrarTablaFacturas() {
        if (GUItabla==null){
            if ((GUItabla = getFXcontrlTablaFCT().getGUItabla()) == null){
                boolean ok = cargarTablaFacturas();
                if(!ok){
                    // STUB - 24-07-19 : Hacer algo si no carga bien...
                }
            }
        }
        Platform.runLater(new Runnable(){
            @Override
            public void run(){
        // FIXME  - 24-07-02 : Tendré que SINCRONIZAR MEDIANTE BARRERAS, para controlar que los hilos esperen hasta que esté activo el modeloFCT, o los elementos de la GUI correspondiente
        // ANCHOR - 24-07-19 : Mostrar T/FCT
                System.out.println("[controladorFacturas>mostrarTablaFacturas>runLater] GUItabla  " + GUItabla.hashCode() + " con TableView " + cntrFXtabla.getTableView().hashCode());
                GUItabla.show();
            }
        });
        // FIXME  - 24-07-19 : Tengo que asignar el valor manualmente!!!
        ControladorFacturas.setGUItabla(GUItabla);
        FxCntrlTablaFCT.getFxController().setTableView(Controlador.getControladorFacturas().getFXcontrlTablaFCT().getTableView());
        // NOTE   - 24-07-18 : Asignar GUI tabla a FXcontrTabla
        //System.out.println("[controladorFacturas>mostrarTablaFacturas] GUItabla recogido en FxCntrlTablaFCT " + FxCntrlTablaFCT.getFxController().getGUItabla().hashCode());
        // REVIEW - 24-06-09 : Hay que cargar el modeloFCT para que no sea NULL eventualmente (en los test)
        //m = ModeloFacturas.getModelo();
    }
    
    public Stage getGUItabla() {
        if(GUItabla!=null)
            System.out.println("[ControladorFacturas] Devolviendo el valor (temp) de GUItabla en el CFCT -> " + GUItabla.hashCode());
        return GUItabla;
    }

    private static void setGUItabla(Stage stg) {
        GUItabla = stg;
        boolean ok= false;
        if(FxCntrlTablaFCT.getFxController()!=null){
            FxCntrlTablaFCT.setGUItabla(stg);
            ok = true;
        }
        System.out.println("[ControladorFacturas] Insertando el valor (temp) de GUItabla en el CFCT -> " + GUItabla.hashCode() + (ok?" Insertado tambien en contrFX " + FxCntrlTablaFCT.getFxController().hashCode():" No se ha insertado en contrFX, no existe contrFX"));
    }

    public synchronized void ocultarTablaFacturas() {
        Platform.runLater(new Runnable(){
            @Override
            public void run(){
                System.out.println("[ControladorFacturas>ocultarTablaFacturas] Se oculta la GUItabla " + FxCntrlTablaFCT.getFxController().getGUItabla().hashCode() + "  + TableView " + FxCntrlTablaFCT.getFxController().getTableView().hashCode());
                FxCntrlTablaFCT.getFxController().getGUItabla().hide();
            }
        }
        );
        
        //this.tableViewFCT = null;
    
    }
//#endregion

//#region CARG_V/FCT
    public synchronized boolean cargarVisorFacturas(){
        if(getVisorFCT() == null){
            Platform.runLater(new Runnable(){
                @Override
                public void run() {
                    Stage visor;
                    FxmlHelper loader = new FxmlHelper("../../resources/visorFormFCT.fxml");
                    Parent root;
                    root = loader.cargarFXML();
                    FxCntrlVisorFCT cntrFXv = (FxCntrlVisorFCT)loader.getFXcontr();
                    Scene escena = new Scene(root);
                    visor = new Stage();
                    visor.setScene(escena);
                    visor.setResizable(true);

                    // REVIEW - 24-07-19 : Asignar V/FCT y contrFX/V 

                    GUIvisor = visor;
                    ControladorFacturas.setFXcontrlVisorFCT(cntrFXv);
                    if(visor!=null&&GUIvisor!=null)
                        //System.out.println("[ControladorFacturas>cargarVisorFacturas] asignado valor temp de visorFCT: " +visor.hashCode() + " -> con el valor final (en CFCT>GUIvisor) de: " + Controlador.getControladorFacturas().getVisorFCT().hashCode());
                    if(cntrFXv!=null&&cntrFXvisor!=null)
                        System.out.println("[ControladorFacturas>cargarVisorFacturas] asignado valor de cntrlFXv: " + cntrFXv.hashCode() + " con el valor final de CFCT>cntrFXvisor: " + Controlador.getControladorFacturas().getFXcontrlVisorFCT().hashCode());
                }
            });
            return true;
        }else{
            return false;
        }
    }
//#endregion

//#region MOSTR_V/FCT
    public synchronized void mostrarVisorFacturas(int index, Factura f){   
            //System.out.println("[ControladorFacturas>mostrarVisorFCT] entrando en la barreraVisor desde el hilo " + Thread.currentThread().getName());
            //NOTE - 13-07-24 : Ojo, hay que actualizar la facturta Actual del Controlador de Facturas manualmente...
            ControladorFacturas.getControlador().setFacturaActual(f);
            System.out.println("[ControladorFacturas>mostrarVisorFacturas] Entrando en la funcion. FacturaActual:\n" + ControladorFacturas.facturaActual.toString());
            if (GUIvisor==null){
                if(cargarVisorFacturas()){
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() {
                            GUIvisor.show();
                            FxCntrlVisorFCT.getFxController().setVisor(GUIvisor);
                            FxCntrlTablaFCT.setIndiceActual(index);
                            System.out.println("[ControladorFacturas>mostrarVisorFCT>runLater] se muestra el visorFCT del contrFX -> " + (((FxCntrlVisorFCT.getFxController())==null)?"NULL":"" + FxCntrlVisorFCT.getFxController().hashCode()));
                            System.out.println("[ControladorFacturas>mostrarVisorFCT>runLater] index: "+index + "\nfactura: \n" + f.toString());
                        }
                    });
                    //NOTE - 07-07-24 : Hacemos una pausa en el hilo del CFCT, no en el de la FXApplication (para ver si se inicializa el visor)
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                System.out.println("[ControladorFacturas>mostrarVisorFCT>runLater] Llamando a actualizarDatosVisor: ");
                                FxCntrlVisorFCT.getFxController().actualizarDatosVisor(index, f);
                            } catch (InterruptedException | BrokenBarrierException  e) {
                                e.printStackTrace();
                            }
                        }
                    });
            }else{
                System.out.println("[ControladorFacturas>mostrarVisorFCT] Hubo un problema al mostrar la GUI del visorFCT. El programa se cierra.");
                System.exit(0);
            }
        //System.out.println("[ControladorFacturas] Se muestra el VisorFCT cuya instancia en el CFCT tiene el valor " + GUIvisor.hashCode());
        }
    }

    public synchronized void ocultarVisorFCT() throws InterruptedException, BrokenBarrierException{
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                    Controlador.getControladorFacturas().getVisorFCT().hide();
            }
        });
        System.out.println("[ControladorFacturas>ocultarVisorFCT] Se oculta el VisorFCT " + Controlador.getControladorFacturas().getVisorFCT().hashCode());
    }
//#endregion

//#region (VISIB)
//#endregion

//#region (FILTRAR)
//#endregion

//#region (AUTOSAVE)      
//#endregion

}

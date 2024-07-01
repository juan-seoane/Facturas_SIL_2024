package controladores;

import modelo.*;
import modelo.base.Fichero;
import modelo.records.Factura;
import ui.ventanas.VentanaFiltros;
import controladores.fxcontrollers.FxCntrlTablaFCT;
import controladores.fxcontrollers.FxCntrlVisorFCT;
import controladores.fxcontrollers.PanelControl;
import controladores.helpers.FxmlHelper;

import java.util.*;
import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.awt.HeadlessException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class ControladorFacturas extends Thread {
    Controlador ctrlPpal;
    public static ControladorFacturas instancia;
//    private VisorFacturas visor;
//    private FormularioFact form;
//    private TablaFacturas tabla;
    public static ModeloFacturas m;
    public PanelControl pc;
    static VentanaFiltros filtros;
    public static FxCntrlTablaFCT FXcontrlTablaFCT;
    public static FxCntrlVisorFCT FXcontrlVisorFCT;
    Stage tablaFCT;
    Stage visorFCT;
    TableView<Factura> modeloTablaFCT = null;
    static boolean GUIon = false;

    private ControladorFacturas() {
        System.out.println("[ControladorFacturas>Constructor] Creando el ControladorFacturas...");
        try {
            m = ModeloFacturas.getModelo();
            System.out.println("[ControladorFacturas>Constructor] modelo FCT importado...");
        } catch (NullPointerException  e) {
            System.out.println("[ControladorFacturas>Constructor] Excepcion " + e +" al cargar el ModeloFacturas...");
            e.printStackTrace();
        }

        if(GUIon){
            FXcontrlTablaFCT = FxCntrlTablaFCT.getFxController();
            FXcontrlVisorFCT = FxCntrlVisorFCT.getFxController();
            System.out.println("[ControladorFacturas>constructor] Los ControladoresFX de la tablaFCT y del visorFCT fueron asignados correctamente");
        }else{
            System.out.println("[ControladorFacturas>constructor] Los ControladoresFX de la tablaFCT y del visorFCT siguen siendo NULL");
        }
    //TODO : 21-06-2024 - Estas asignaciones me hacen falta
    /*
        this.pc = PanelControl.getPanelControl();
        this.modeloTabla = tablaFCT.getTableView();
        this.ctrlPpal = Controlador.getControlador();
    */
    }

    public static synchronized ControladorFacturas getControlador() {

        if (instancia == null) {
            System.out.println("[ControladorFacturas>getControlador()] Instancia vacia, creando una nueva instancia generica (sin GUI asociada)");
            instancia = new ControladorFacturas();
        }

        return instancia;
    }

    public static synchronized ControladorFacturas getControlador(FxCntrlTablaFCT fxcntrfct) {

        if (instancia == null) {
            System.out.println("[ControladorFacturas>getControlador(fxcontr)] Instancia vacia, creando una nueva instancia generica con GUI asociada!");
            instancia = new ControladorFacturas();
        }
        GUIon = true;
        setFXcontrlFCT(fxcntrfct);
        return instancia;
    }
//#region get/setTablaFCT
    public FxCntrlTablaFCT getFXcontrlFCT(){
        return FXcontrlTablaFCT;
    }

    public static void setFXcontrlFCT(FxCntrlTablaFCT contr){
        FXcontrlTablaFCT = contr;
    }
//#endregion    
//#region RUN_CFCT()
    @Override
    public void run() {
        while (true) {

//            while (/*!visor.haCambiado() & !form.seHaEnviado() & !form.pasoatras() & !form.pasoadelante() & */!tabla.haCambiado()) {
//                System.out.print("");
//            }
/* FORM_SWITCH        
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
*/
/* VISOR_SWITCH
            if (visor.haCambiado()) {
                //JOptionPane.showMessageDialog(null, "[ControladorFacturas] Ha pulsado un boton en el visor!");
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
//#region nuevo_SWITCH  
            if(FXcontrlTablaFCT!=null && FXcontrlTablaFCT.HaCambiado()) {
				System.out.println("[ControladorFacturas>run] recogiendo evento del Controlador de Facturas - pulsado caso " + FXcontrlTablaFCT.getPulsado());
                // TODO : 30-06-2024 - HAY QUE PONER UN CASO CERO DONDE RECOJA EL INDICE DE LA FACTURA ACTUAL
                
                if (FXcontrlTablaFCT.getPulsado() == 1) {
                    
                    //System.out.println("[ControladorFacturas>run] Se mostrará el Visor de Facturas");
                    mostrarVisorFCT();
                    //actualizarVisor(tabla.getIndice());

                    
                }
                if (FXcontrlTablaFCT.getPulsado() == 2) {
                    System.out.println("[ControladorFacturas>run] Se mostrará el Formulario de Nueva Facturas");
//                    actualizarFormulario();

//                    tabla.reset();
                }
                if (FXcontrlTablaFCT.getPulsado() == 3) {
//                    imprimirTabla();
//                    tabla.reset();
//                    visible(true);
                }
                if (FXcontrlTablaFCT.getPulsado() == 4) {
//                    verFiltros();
//                    tabla.reset();

                }
                if (FXcontrlTablaFCT.getPulsado() == 5) {
                    System.out.println("[ControladorFacturas>run] Se mostrará la Ventana de Filtros");
/*
                    try {
                        actualizarTabla(0);
                    } catch (NullPointerException | IOException e) {
                        e.printStackTrace();
                    }
                    actualizarVisor(0);
                    actualizarTotales();
                    tabla.reset();
                    visible(true);
*/                    
                }

                if (FXcontrlTablaFCT.getPulsado() == 6) {
//                    actualizarVisor(tabla.getIndice());
//                    tabla.reset();
//                    visible(true);
                }
                FXcontrlTablaFCT.reset();  
            }
            if(FXcontrlVisorFCT!=null && FXcontrlVisorFCT.HaCambiado()) {
				System.out.println("[ControladorFacturas>run] recogiendo evento del visorFCT en el Controlador de Facturas - pulsado caso " + FXcontrlVisorFCT.getPulsado());
                // TODO : 30-06-2024 - HAY QUE PONER UN CASO CERO DONDE RECOJA EL INDICE DE LA FACTURA ACTUAL
                
                if (FXcontrlVisorFCT.getPulsado() == 1) {
                    ocultarVisorFCT();
                    System.out.println("[ControladorFacturas>run] Se cerrará el Visor de Facturas");

                }
                if (FXcontrlVisorFCT.getPulsado() == 2) {
                    System.out.println("[ControladorFacturas>run] Se activará el Formulario de Nueva Factura");

                }
                if (FXcontrlVisorFCT.getPulsado() == 3) {
                    System.out.println("[ControladorFacturas>run] Se activará la Ediion del VisorFCT para la Factura actual");

                }
                if (FXcontrlVisorFCT.getPulsado() == 4) {
                    System.out.println("[ControladorFacturas>run] Se borrará la Factura actual");


                }
                if (FXcontrlVisorFCT.getPulsado() == 5) {
                    System.out.println("[ControladorFacturas>run] La seleccion de la tablaFCT se movera hacia arriba");
               
                }

                if (FXcontrlVisorFCT.getPulsado() == 6) {
                    System.out.println("[ControladorFacturas>run] La seleccion de la tablaFCT se movera hacia abajo");

                }
                FXcontrlVisorFCT.reset();
            }
//#endregion

//#region LATENCIA<400ms
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(0);
            }
//#endregion
        }
    }

//#region VisorOps
    public synchronized void mostrarVisorFCT(){
        Platform.runLater(new Runnable(){

            @Override
            public void run() {
                visorFCT.show();
            }

        });
        
        System.out.println("[ControladorFacturas] Se muestra el VisorFCT\n******************");
    }

<<<<<<< HEAD
    public synchronized void ocultarVisorFCT(){
        Platform.runLater(new Runnable(){

            @Override
            public void run() {
                visorFCT.hide();
            }

        });
        
        System.out.println("[ControladorFacturas] Se muestra el VisorFCT\n******************");
    }
=======
>>>>>>> 7aabd4bfd1b2e5a3d5f057ddd30a0d4a19a6c547
    public boolean anteriorFacturaVisor() throws NullPointerException, IOException {
		JOptionPane.showMessageDialog(null,"[ControladorFacturas] Ha pulsado el boton atras. ");
/*        int i = tabla.getIndice();
        List<Factura> lista = m.leerFacturas();
        if (i > 0) {
            // TODO : 30-06-2024 - HAY QUE AJUSTAR EL VISOR PARA QUE SELECCIONE LA FACTURA ADECUADA DESDPUES DE ACTIVAR EL FILTRO 
            int nuevoindice = i-1;
//            //System.out.println(" [ControladorFacturas] Actualizando Visor!");
            actualizarVisor(nuevoindice);
//            //System.out.println(" [ControladorFacturas] Actualizando tabla");
            tabla.seleccionarIndice(nuevoindice);

        } else {
            int nuevoindice = lista.size()-1;
//            //System.out.println(" [ControladorFacturas] Actualizando Visor!");
            actualizarVisor(nuevoindice);
//            //System.out.println(" [ControladorFacturas] Actualizando tabla");
            tabla.seleccionarIndice(nuevoindice);
        }
//                        JOptionPane.showMessageDialog(null,"[ControladorFacturas] Index Visor: "+visor.getIndex()+" Index Tabla: "+tabla.getIndice());
        tabla.reset();
*/
        return true;
    }

    public boolean siguienteFacturaVisor() throws NullPointerException, IOException {
		JOptionPane.showMessageDialog(null,"[ControladorFacturas] Ha pulsado el boton adelante.");
/*        int i = tabla.getIndice();
        List<Factura> lista = m.leerFacturas();
        if (i < (lista.size() - 1)) {
            int nuevoindice = i+1;
//            //System.out.println(" [ControladorFacturas] Actualizando Visor!");
            actualizarVisor(nuevoindice);
//            //System.out.println(" [ControladorFacturas] Actualizando tabla");
            tabla.seleccionarIndice(nuevoindice);
            ////System.out.println(" [ControladorFacturas] Index2: "+visor.getIndex());
        } else {
            int nuevoindice = 0;
//            //System.out.println(" [ControladorFacturas] Actualizando Visor!");
            actualizarVisor(0);
//            //System.out.println(" [ControladorFacturas] Actualizando tabla");
            tabla.seleccionarIndice(nuevoindice);
        }
//                        JOptionPane.showMessageDialog(null,"[ControladorFacturas] Index Visor: "+visor.getIndex()+" Index Tabla: "+tabla.getIndice());
        tabla.reset();
*/
        return true;
    }

    public boolean editarFacturaVisor() {
/*
        form.setEstado("editando");
        form.insertarFactura(m.getFactura(visor.getIndex()));
        form.setVisible(true);
        form.toFront();
*/

        return true;
    }

    public boolean borrarFacturaVisor() throws NumberFormatException, IOException {
       System.out.println("[ControladorFacturas>borrarFacturaVisor()] Ha pulsado el boton borrar factura");
/*
        int i = tabla.getIndice();
        //System.out.println(" [ControladorFacturas] Enviando desde el controlador factura con indice " + i);
        Factura fact = m.getFactura(i);
        //JOptionPane.showMessageDialog(null, "[ControladorFacturas] Recogiendo los datos de la factura");
        anteriorFacturaVisor();
        m.borrarFactura(fact, i);
        visor.reset();
//		actualizarVisor(i-1);
        actualizarTabla(i - 1);
*/

        return true;
    }

    public boolean guardarNotaVisor() throws NumberFormatException, HeadlessException, IOException{
        /*
                Factura f = m.getFactura(visor.getIndex());
                f.setNota(visor.getNota());
                if (m.editarFactura((ArrayList<Factura>)m.leerFacturas(),f,visor.getIndex()))
                    JOptionPane.showMessageDialog(null,"[ControladorFacturas] Nota guardada!");
        */
                System.out.println("[ControladorFacturas>guardarNotaVisor()] ");
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
        System.out.println("[ControladorFacturas>actualizarVisor()] Actualizar Visor a index "+ index);
        return true;
    }    
//#eendregion
//#region FormOps
    public boolean recogerFormyEditar(Factura f) throws NumberFormatException, IOException {
/*		JOptionPane.showMessageDialog(null,"[ControladorFacturas] Enviado :: Indice en Controlador: "+visor.getIndex());
        Factura facturaTemp = m.recogerFormulario(form);
        int sel = m.facturas.indexOf(f);
        m.facturas.remove(f);
        m.facturas.add(facturaTemp);
        m.insertarFacturas((ArrayList<Factura>)m.facturas);
        
        System.out.println(" [ControladorFacturas] Index3: "+visor.getIndex());
        actualizarTabla(sel);
        limpiarFormyActualizar(tabla.getIndice());
        return true;
*/
        System.out.println("[ControladorFacturas>recogerFormyEditar()] ");
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
        System.out.println("[ControladorFacturas>recogerFormyAnexar()] ");
        return true;
    }

    public boolean limpiarFormyActualizar(int index) {
/*
        form.limpiarFormulario();
        actualizarVisor(index);
        actualizarTabla(index);
*/
        System.out.println("[ControladorFacturas>limpiarFormyActualizar()] ");        
        return true;
    }
   
    public boolean rellenarFormyActualizar(Factura f) {
/*         
        int index = m.getIndexOfFactura(f);
        
        form.insertarFactura(m.getFactura(index));
        actualizarVisor(index);
        actualizarTabla(index);
*/
        System.out.println("[ControladorFacturas>rellenarFormyActualizar()] ");               
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
        System.out.println("[ControladorFacturas>actualizarFormulario()] ");
        return true;
    }
//#endregion 
//#region TablaOps
    public synchronized void mostrarTablaFacturas() {
        
        Platform.runLater(new Runnable(){
            @Override
            public void run(){
               
                    //Mostrar los datos en la tabla
                
                //System.out.println("[PanelControl>mostrarTablaFacturas()] Mostrando valores en la Tabla de Facturas");
                //Obtener la tabla del Stage que la contiene
                
/*                 String ruta="../../resources/fxmltablaFCT.fxml";
                FxmlHelper tablaFXHelper = new FxmlHelper(ruta);
                FXcontrlTablaFCT = (FxCntrlTablaFCT) tablaFXHelper.getFXcontr(); 
*/

                tablaFCT.show();
                System.out.println("[ControladorFacturas>mostrarTablaFacturas] Se muestra la tabla FCT\n******************");

            }
        });
        //TODO : 29-06-2024 - Hay que cargar el modeloFCT para que no sea NULL eventualmente (en los test)
        //m = ModeloFacturas.getModelo();
        }

    public synchronized void ocultarTablaFacturas() {
        Platform.runLater(new Runnable(){
            @Override
            public void run(){
                System.out.println("[ControladorFacturas>ocultarTablaFacturas] Se oculta  la tabla FCT\n******************");
                tablaFCT.hide();
            }
        }
        );
        
        this.modeloTablaFCT = null;
       
    }

    @SuppressWarnings("unchecked")
    public boolean actualizarTabla(int sel) throws NullPointerException, IOException {

        JOptionPane.showMessageDialog(null,"[ControladorFacturas] Actualizando Tabla!");
        List<Factura> facturas = m.leerFacturasSinFiltrar();
        
        Vector vectorFacturas = new Vector();
        if (m.numeroFacturas>0){
            for (Factura f : facturas) {
                vectorFacturas.add(Factura.toVector(new Factura()));
            }
        } 
        else vectorFacturas.add(Factura.toVector(new Factura()));
/*
        try {
            tabla.actualizarModelo(vectorFacturas, sel);
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
        tablaFCT.actualizarTotales();
*/
        
        System.out.println("[ControladorFacturas>actualizarTabla()] Actualizar tabla a num " + sel);
        return true;
    }
/*    
    public boolean imprimirTabla() {
        String titulo = JOptionPane.showInputDialog("[ControladorFacturas] Escriba el título del informe, por favor: ");
        JOptionPane.showMessageDialog(null, "[ControladorFacturas] pulse OK y espere a que se genere el informe!");

        List<Factura> facturas = m.leerFacturas();
        FacturasDataSource datasource = new FacturasDataSource();

        for (Factura f : facturas) {
            datasource.addFactura(f);
        }
        //JOptionPane.showMessageDialog(null,"[ControladorFacturas] facturas añadidas a la cola de impresion!");
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
//#region LOAD_FCT/T
    public synchronized boolean cargarTablaFacturas() {
        if(FXcontrlTablaFCT == null){
            Platform.runLater(new Runnable(){

                Stage tabla;

                @Override
                public void run() {
                    FxmlHelper loader = new FxmlHelper("../../resources/fxmltablaFCT.fxml");

                    Parent root;

                    root = loader.cargarFXML();
                    FXcontrlTablaFCT = (FxCntrlTablaFCT)loader.getFXcontr();

                    Scene escena = new Scene(root);
                    tabla = new Stage();

                    tabla.setScene(escena);
                    tabla.setResizable(true);
                    // TODO : 30-05-2024 - Aquí se ajusta el modo de la ventana de la TablaFCT
                    //this.tablaFCT.initModality(Modality.NONE);       
                    tablaFCT = tabla;
                }
                    
            });
            return true;
        }
        return false;
    }
//#endregion
//#region LOAD_FCT/V
public synchronized boolean cargarVisorFacturas() {
    if(FXcontrlVisorFCT == null){
        Platform.runLater(new Runnable(){

            private Stage visor;

            @Override
            public void run() {
                FxmlHelper loader = new FxmlHelper("../../resources/visorFormFCT.fxml");

                Parent root;
        
                root = loader.cargarFXML();
                FXcontrlVisorFCT = (FxCntrlVisorFCT)loader.getFXcontr();
        
                Scene escena = new Scene(root);
                visor = new Stage();
        
                visor.setScene(escena);
                visor.setResizable(true);

                visorFCT = visor;
            }
            
        });
        return true;
    }
    return false;
}
//#endregion
/*
//#region VISIBILIDAD
    public void visible(boolean bool) {
        System.out.println("[ControladorFacturas> visible("+bool+")]");
//        int sel = visor.getIndex();
//        actualizarVisor(sel);
        actualizarFormulario();
//        actualizarTabla(sel);
//        tabla.setBounds(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize().getSize()));
        if (bool) {
/*            
               if (PanelControl.modo == Controlador.NAV){              
                   this.form.setVisible(false);
                   if(visor.isVisible())
                       visor.setVisible(true);
                   else
                       visor.setVisible(false);
                   this.tabla.setVisible(true);
               } else {
                   this.form.setVisible(true);
                   this.visor.setVisible(false);

                    this.tabla.setVisible(true);
//                }
        }else{
            
           this.form.setVisible(false);
           this.visor.setVisible(false);

            this.tabla.setVisible(false);
        }
        tabla.toBack();

       form.toFront();
       visor.toFront();
  
    public void visorVisible(Boolean bool){
        if (bool){
            visor.setVisible(true);
        }
        else visor.setVisible(false);
    }
    
    public void formVisible(Boolean bool){
        if (bool){
            form.setVisible(true);
        }
        else form.setVisible(false);
    }
//#endregion
//#region FILTRAR
/*
    public boolean verFiltros(){
         filtros = VentanaFiltros.getVentana();
         filtros.setVisible(true);
        return true;   
    }
   
   public List<Factura> filtrar(List<Factura> lista) {
       List<Factura> lista2, lista3, lista4;
       if (tabla.filtrosActivos()){
           if (this.filtros.getChbFiltroFecha().isSelected())
           {
           String anho = Config.getConfig().getAnho().getAnho()+"";
           FiltroFecha filtro1 = new FiltroFecha(this.filtros.getFechaInicio(),this.filtros.getFechaFinal());
           lista2 = filtro1.filtrar(lista);
           }
           else lista2 = lista;
           if (this.filtros.getChbFiltroCategoria().isSelected())
           {
           FiltroCategoria filtro2 = new FiltroCategoria(this.filtros.getCmbCategoriasFiltros().getSelectedItem().toString());
           lista3 = filtro2.filtrar(lista2);
           }
           else lista3 = lista2;
           if (this.filtros.getChbFiltroDistribuidor().isSelected())
           {
           FiltroDistribuidor filtro3 = new FiltroDistribuidor(this.filtros.getFiltroDist());
           lista4 = filtro3.filtrar(lista3);
           }
           else lista4 = lista3;

//TODO : 22-06-2024 - ACORDARSE DE ACTUALIZAR LOS TOTALES DESPUES DE FILTRAR!
           return lista4;
       }
       return lista;
   }
//#endregion
//#region AUTOSAVE      
    public boolean autosave(String ruta){
        
        if (m.autosave(ruta))
            return true;
        else return false;     
    }
//#endregion
*/ 
}

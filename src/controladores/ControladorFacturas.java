package controladores;

import modelo.*;
import modelo.base.Fichero;
import modelo.records.Factura;
import ui.ventanas.VentanaFiltros;
import controladores.fxcontrollers.FxControladorFacturas;
import controladores.fxcontrollers.PanelControl;
import controladores.helpers.FxmlHelper;

import java.util.*;
import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
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
    static FxControladorFacturas tablaFCT;
    TableView<Factura> modeloTablaFCT = null;

    private ControladorFacturas() {
        System.out.println("[ControladorFacturas>Constructor] Creando el ControladorFacturas...");
        try {
            m = ModeloFacturas.getModelo();
            System.out.println("[ControladorFacturas>Constructor] modelo FCT importado...");
        } catch (NullPointerException  e) {
            System.out.println("[ControladorFacturas>Constructor] Excepcion " + e +" al cargar el ModeloFacturas...");
            e.printStackTrace();
        }
        //TODO : 21-06-2024 - Estas asignaciones me hacen falta
        tablaFCT = FxControladorFacturas.getFxController();
        /*
        this.pc = PanelControl.getPanelControl();
        this.ctrlPpal = Controlador.getControlador();
        this.modeloTabla = tablaFCT.getTableView();
        */
    }

    public static synchronized ControladorFacturas getControlador() {

        if (instancia == null) {
            System.out.println("[ControladorFacturas>getControlador()] Instancia vacia, creando una nueva instancia generica (sin GUI asociada)");
            instancia = new ControladorFacturas();
        }

        return instancia;
    }

//TODO : Cambiar la GUI Tabla de Facturas a JavaFX y la clase Factura a Record
    public static synchronized ControladorFacturas getControlador(FxControladorFacturas fxcntrfct) {

        if (instancia == null) {
            System.out.println("[ControladorFacturas>getControlador(fxcontr)] Instancia vacia, creando una nueva instancia generica con GUI asociada!");
            instancia = new ControladorFacturas();
        }
        setTablaFCT(fxcntrfct);
        return instancia;
    }
//#region get/setTablaFCT
    public FxControladorFacturas getTablaFCT(){
        return tablaFCT;
    }

    public static void setTablaFCT(FxControladorFacturas contr){
        tablaFCT = contr;
    }
//#endregion    
//#region RUN_CFCT()
    @Override
    public void run() {
        while (true) {

//            while (/*!visor.haCambiado() & !form.seHaEnviado() & !form.pasoatras() & !form.pasoadelante() & */!tabla.haCambiado()) {
//                System.out.print("");
//            }
/*            if (visor.haCambiado()) {
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
                 form.getForm{
            if (ulario().setPasoAdelante(false);
            }
*/ 
//#region SWITCH Tabla 
            if(tablaFCT!=null && tablaFCT.HaCambiado()) {
				System.out.println("[ControladorFacturas>run] recogiendo evento del Controlador de Facturas - pulsado caso " + tablaFCT.getPulsado());
                // TODO : HAY QUE PONER UN CASO CERO DONDE RECOJA EL INDICE DE LA FACTURA ACTUAL
                
                if (tablaFCT.getPulsado() == 1) {
                    
                    System.out.println("[ControladorFacturas>run] Se mostrará el Visor de Facturas");
//                    actualizarVisor(tabla.getIndice());
//                    visor.setVisible(true);
//                    tabla.reset();
                    
                }
                if (tablaFCT.getPulsado() == 2) {
                    System.out.println("[ControladorFacturas>run] Se mostrará el Formulario de Nueva Facturas");
//                    actualizarFormulario();

//                    tabla.reset();
                }
                if (tablaFCT.getPulsado() == 3) {
//                    imprimirTabla();
//                    tabla.reset();
//                    visible(true);
                }
                if (tablaFCT.getPulsado() == 4) {
//                    verFiltros();
//                    tabla.reset();

                }
                if (tablaFCT.getPulsado() == 5) {
                    System.out.println("[ControladorFacturas>run] Se mostrará la Ventana de Filtros");
/*
                    try {
                        actualizarTabla(0);
                    } catch (NullPointerException | IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    actualizarVisor(0);
                    actualizarTotales();
                    tabla.reset();
                    visible(true);
*/                    
                }

                if (tablaFCT.getPulsado() == 6) {
//                    actualizarVisor(tabla.getIndice());
//                    tabla.reset();
//                    visible(true);
                }   
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
    public boolean anteriorFacturaVisor() throws NullPointerException, IOException {
		JOptionPane.showMessageDialog(null,"[ControladorFacturas] Ha pulsado el boton atras. ");
/*        int i = tabla.getIndice();
        List<Factura> lista = m.leerFacturas();
        if (i > 0) {
            // TODO : HAY QUE AJUSTAR EL VISOR PARA QUE SELECCIONE LA FACTURA ADECUADA DESDPUES DE ACTIVAR EL FILTRO 
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
        PanelControl.getPanelControl().tablaFCT.show();
        System.out.println("[PanelControl] Se muestra la tabla FCT\n******************");
        	//Mostrar los datos en la tabla
        
		//System.out.println("[PanelControl>mostrarTablaFacturas()] Mostrando valores en la Tabla de Facturas");
		//Obtener la tabla del Stage que la contiene
        
        String ruta="/resources/fxmltablaFCT.fxml";
        FxmlHelper FCTFXHelper = new FxmlHelper(ruta);

        System.out.println("*****[ControladorFacturas>mostrarTablaFacturas]*****");
/*        do{
            System.out.print("[ControladorFacturas>mostrarTablaFacturas] cargando las Facturas en la TablaFCT\r");
            this.modeloTablaFCT = tablaFCT.getTableView();
        }while(this.modeloTablaFCT==null);
*/
        //TODO: 22-06-2024 : Se ejecuta en cuanto se pueda en la aplicación JFX
        Platform.runLater(new Runnable(){
            @Override
            public void run(){
/*
                do{
                    System.out.print("[ControladorFacturas>] Inicializando GUI tablaFCT\r");
                
                }while(tablaFCT.lblBase!=null);
*/
                String[] datosResumen = m.calcularTotales();
                tablaFCT.actualizarTotales(datosResumen);
                ObservableList<Factura> listaFxFct = ControladorFacturas.m.getListaFXFacturas();
                //TODO : 29-06-24 - Me parece que este condicional de aquí abajo, sobra...
                if (modeloTablaFCT!=null){
                    modeloTablaFCT.setItems(listaFxFct);
                    modeloTablaFCT.refresh();
                    System.out.println("[ControladorFacturas>mostrarTablaFacturas] Regenerando el modeloTablaFCT ");
                }
               
            }
        });

    }

    public synchronized void ocultarTablaFacturas() {
        System.out.println("[ControladorFacturas>ocultarTablaFacturas] Se oculta  la tabla FCT\n******************");
        PanelControl.getPanelControl().tablaFCT.hide();
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

//TODO : ACORDARSE DE ACTUALIZAR LOS TOTALES DESPUES DE FILTRAR!
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

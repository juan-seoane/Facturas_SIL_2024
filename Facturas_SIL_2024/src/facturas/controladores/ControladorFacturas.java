package controladores;

import modelo.*;
import ui.*;
import java.util.*;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.export.*;
//import com.sun.media.imageioimpl.common.InputStreamAdapter;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ControladorFacturas extends Thread {

    public static ControladorFacturas instancia = null;
    private VisorFacturas visor;
    private FormularioFact form;
    private TablaFacturas tabla;
    private ModeloFacturas m;
    public static VentanaFiltros filtros;

    private ControladorFacturas() {
        m = ModeloFacturas.getModelo();
        tabla = new TablaFacturas(m.generarVectorFacturas(), m.getColumnas());
        actualizarTabla(0);
        if (m.getNumeroFacturas() > 0) {
            visor = VisorFacturas.getVisor(0, (Factura) m.getFactura(0));
        } else {
            visor = VisorFacturas.getVisor(0, new Factura());
        }
        this.form = FormularioFact.getFormulario();
        this.filtros = VentanaFiltros.getVentana();
        visible(true);
    }

    public static ControladorFacturas getControlador() {

        if (instancia == null) {
            instancia = new ControladorFacturas();
        }
        return instancia;
    }

    public void run() {
        while (true) {
            while (!visor.haCambiado() & !form.seHaEnviado() & !form.pasoatras() & !form.pasoadelante() & !tabla.haCambiado()) {
                System.out.print("");
            }
            if (visor.haCambiado()) {
                //JOptionPane.showMessageDialog(null, "Ha pulsado un boton en el visor!");
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
                        borrarFacturaVisor();
                        visible(true);
                        break;
                    case 6:
                        guardarNotaVisor();
                        visor.reset();
                        visible(true);
                        break;
                    default:
                        //System.out.println("Controlador :: Ninguna opcion seleccionada!");
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
                    JOptionPane.showMessageDialog(null, "Se ha producido un error en la carga del formulario!");
                    form.enCasodeError(false);
                }
                visible(true);
            }
            if (form.pasoatras()){
                try{
                rellenarFormyActualizar(ModeloFacturas.getModelo().getPilaFacturasSig().push(ModeloFacturas.getModelo().getPilaFacturasAnt().pop()));
//                form.borrarDatosNumericos();
               }catch(Exception ex){
                   JOptionPane.showMessageDialog(null,"No es posible dar más pasos atrás!");
               }
                form.getFormulario().setPasoAtras(false);
            }
            if (form.pasoadelante()){
                 try{
                rellenarFormyActualizar(ModeloFacturas.getModelo().getPilaFacturasAnt().push(ModeloFacturas.getModelo().getPilaFacturasSig().pop()));
                form.borrarDatosNumericos();
               }catch(Exception ex){
                   JOptionPane.showMessageDialog(null,"No es posible dar más pasos adelante!");
               }
                 form.getFormulario().setPasoAdelante(false);
            }
            if (tabla.haCambiado()) {
//				JOptionPane.showMessageDialog(null,">>>>>>Controlador :: recogiendo evento Tabla : "+tabla.getPulsado());
                /**
                 * TODO : HAY QUE PONER UN CASO CERO DONDE RECOJA EL INDICE DE
                 * LA FACTURA ACTUAL
                 */
                
                if (tabla.getPulsado() == 1) {
                    actualizarVisor(tabla.getIndice());
                    visor.setVisible(true);
                    tabla.reset();
                    
                }
                if (tabla.getPulsado() == 2) {

                    actualizarFormulario();

                    tabla.reset();
                }
                if (tabla.getPulsado() == 3) {
                    imprimirTabla();
                    tabla.reset();
                    visible(true);
                }
                if (tabla.getPulsado() == 4) {
                    verFiltros();
                    tabla.reset();

                }
                if (tabla.getPulsado() == 5) {
                    actualizarTabla(0);
                    actualizarVisor(0);
                    actualizarTotales();
                    tabla.reset();
                    visible(true);
                }
                if (tabla.getPulsado() == 6) {
                    actualizarVisor(tabla.getIndice());
                    tabla.reset();
                    visible(true);
                }
                
            }

        }
    }

    public boolean verFiltros(){
         filtros = VentanaFiltros.getVentana();
         filtros.setVisible(true);
        return true;   
    }

    public boolean anteriorFacturaVisor() {
//		JOptionPane.showMessageDialog(null,">>>>>>Ha pulsado el boton atras. ");
        int i = tabla.getIndice();
        List<Factura> lista = m.leerFacturas();
        if (i > 0) {
            /** TODO : LO DEJAMOS AQUÍ, HAY QUE AJUSTAR EL VISOR PARA QUE SELECCIONE LA FACTURA ADECUADA DESDPUÉS DE ACTIVAR EL FILTRO */
            int nuevoindice = i-1;
//            //System.out.println("Actualizando Visor!");
            actualizarVisor(nuevoindice);
//            //System.out.println("Actualizando tabla");
            tabla.seleccionarIndice(nuevoindice);

        } else {
            int nuevoindice = lista.size()-1;
//            //System.out.println("Actualizando Visor!");
            actualizarVisor(nuevoindice);
//            //System.out.println("Actualizando tabla");
            tabla.seleccionarIndice(nuevoindice);
        }
//                        JOptionPane.showMessageDialog(null,"Index Visor: "+visor.getIndex()+" Index Tabla: "+tabla.getIndice());
        tabla.reset();
        return true;
    }

    public boolean siguienteFacturaVisor() {
//		JOptionPane.showMessageDialog(null,">>>>>>Ha pulsado el boton adelante.");
        int i = tabla.getIndice();
        List<Factura> lista = m.leerFacturas();
        if (i < (lista.size() - 1)) {
            int nuevoindice = i+1;
//            //System.out.println("Actualizando Visor!");
            actualizarVisor(nuevoindice);
//            //System.out.println("Actualizando tabla");
            tabla.seleccionarIndice(nuevoindice);
            ////System.out.println("Index2: "+visor.getIndex());
        } else {
            int nuevoindice = 0;
//            //System.out.println("Actualizando Visor!");
            actualizarVisor(0);
//            //System.out.println("Actualizando tabla");
            tabla.seleccionarIndice(nuevoindice);
        }
//                        JOptionPane.showMessageDialog(null,"Index Visor: "+visor.getIndex()+" Index Tabla: "+tabla.getIndice());
        tabla.reset();
        return true;
    }

    public boolean editarFacturaVisor() {
        form.setEstado("editando");
        form.insertarFactura(m.getFactura(visor.getIndex()));
        form.setVisible(true);
        form.toFront();
        return true;
    }

    public boolean borrarFacturaVisor() {
        //JOptionPane.showMessageDialog(null,">>>>>>Ha pulsado el boton borrar factura");
        int i = tabla.getIndice();
        //System.out.println(">>>>>>Enviando desde el controlador factura con indice " + i);
        Factura fact = m.getFactura(i);
        //JOptionPane.showMessageDialog(null, "Recogiendo los datos de la factura");
        anteriorFacturaVisor();
        m.borrarFactura(fact, i);
        visor.reset();
//		actualizarVisor(i-1);
        actualizarTabla(i - 1);
        return true;
    }

    public boolean recogerFormyEditar(Factura f) {
//		JOptionPane.showMessageDialog(null,">>>>>>Enviado :: Indice en Controlador: "+visor.getIndex());
        Factura facturaTemp = m.recogerFormulario(form);
        int sel = m.facturas.indexOf(f);
        m.facturas.remove(f);
        m.facturas.add(facturaTemp);
        m.insertarFacturas((ArrayList<Factura>)m.facturas);
        
        ////System.out.println("Index3: "+visor.getIndex());
        actualizarTabla(sel);
        limpiarFormyActualizar(tabla.getIndice());
        return true;
    }

    public boolean recogerFormyAnexar() {
        Factura f = m.recogerFormulario(form);  

        m.facturas.add(f);
        m.insertarFacturas((ArrayList<Factura>)m.facturas);
        int sel = m.facturas.indexOf(f);
        actualizarTabla(sel);
        limpiarFormyActualizar(tabla.getIndice());
        
        return true;
    }

    public boolean limpiarFormyActualizar(int index) {
        form.limpiarFormulario();
        actualizarVisor(index);
        actualizarTabla(index);
        return true;
    }
   
    public boolean rellenarFormyActualizar(Factura f) {
        int index = m.getIndexOfFactura(f);
        
        form.insertarFactura(m.getFactura(index));
        actualizarVisor(index);
        actualizarTabla(index);
        return true;
    }
    
    public boolean guardarNotaVisor(){
        Factura f = m.getFactura(visor.getIndex());
        f.setNota(visor.getNota());
        if (m.editarFactura((ArrayList<Factura>)m.leerFacturas(),f,visor.getIndex()))
            JOptionPane.showMessageDialog(null,"Nota guardada!");
        return true;
    }
    
    public boolean actualizarVisor(int index) {
        //JOptionPane.showMessageDialog(null,">>>>>>Actualizando Visor!");
        Factura f = new Factura();
        List<Factura> lista = m.leerFacturas();
        if (lista.size() > 0)
            f = (Factura) lista.get(index);
        else 
            index = 0;
        visor = VisorFacturas.getVisor(index, f);
        return true;
    }

    public boolean actualizarFormulario() {
        //JOptionPane.showMessageDialog(null,">>>>>>Actualizando Formulario!");
        int tempx = form.getX();
        int tempy = form.getY();

        form = FormularioFact.getFormulario();
        form.setVisible(true);
        form.toFront();
        
        return true;
    }

    public boolean actualizarTabla(int sel) {
//            JOptionPane.showMessageDialog(null,">>>>>>Actualizando Tabla!");
        List<Factura> facturas = m.leerFacturas();
        
        Vector vectorFacturas = new Vector();
        if (m.numeroFacturas>0){
            for (Factura f : facturas) {
                vectorFacturas.add(f.toVector());
            }
        } 
        else vectorFacturas.add(new Factura().toVector());

        tabla.actualizarModelo(vectorFacturas, sel);
        actualizarTotales();

        return true;
    }

    public void actualizarTotales() {
        List<Factura> facturas = m.leerFacturas();
        
        int cuenta = 0;
        double base = 0;
        double iva = 0;
        double subtotal = 0;
        double baseNI = 0;
        double retenc = 0;
        double total = 0;

        for (Factura f : facturas) {
            cuenta++;
            base += f.getTotales().getBase();
            iva += f.getTotales().getIVA();
            subtotal += f.getTotales().getSubtotal();
            baseNI += f.getTotales().getBaseNI();
            retenc += f.getTotales().getRetenciones();
            total += f.getTotales().getTotal();
        }
        tabla.actualizarTotales(cuenta, base, iva, subtotal, baseNI, retenc, total);
    }

    public void visible(boolean bool) {
//        int sel = visor.getIndex();
//        actualizarVisor(sel);
//        actualizarFormulario();
//        actualizarTabla(sel);
        tabla.setBounds(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize().getSize()));
        if (bool) {
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
                }
        }else{
            this.form.setVisible(false);
            this.visor.setVisible(false);
            this.tabla.setVisible(false);
        }
        tabla.toBack();
        form.toFront();
        visor.toFront();
    }
    
    
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
//    
//    public List<Factura> filtrar(List<Factura> lista) {
//        List<Factura> lista2, lista3, lista4;
//        if (tabla.filtrosActivos()){
//            if (this.filtros.getChbFiltroFecha().isSelected())
//            {
//            String anho = Config.getConfig().getAnho().getAnho()+"";
//            FiltroFecha filtro1 = new FiltroFecha(this.filtros.getFechaInicio(),this.filtros.getFechaFinal());
//            lista2 = filtro1.filtrar(lista);
//            }
//            else lista2 = lista;
//            if (this.filtros.getChbFiltroCategoria().isSelected())
//            {
//            FiltroCategoria filtro2 = new FiltroCategoria(this.filtros.getCmbCategoriasFiltros().getSelectedItem().toString());
//            lista3 = filtro2.filtrar(lista2);
//            }
//            else lista3 = lista2;
//            if (this.filtros.getChbFiltroDistribuidor().isSelected())
//            {
//            FiltroDistribuidor filtro3 = new FiltroDistribuidor(this.filtros.getFiltroDist());
//            lista4 = filtro3.filtrar(lista3);
//            }
//            else lista4 = lista3;
//
//            /** TODO : ACORDARSE DE ACTUALIZAR LOS TOTALES DESPUES DE FILTRAR! */
//            return lista4;
//        }
//        return lista;
//    }
    
    public boolean autosave(String ruta){
        
        if (m.autosave(ruta))
            return true;
        else return false;     
    }
    
    public boolean imprimirTabla() {
        String titulo = JOptionPane.showInputDialog("Escriba el título del informe, por favor: ");
        JOptionPane.showMessageDialog(null, "pulse OK y espere a que se genere el informe!");

        List<Factura> facturas = m.leerFacturas();
        FacturasDataSource datasource = new FacturasDataSource();

        for (Factura f : facturas) {
            datasource.addFactura(f);
        }
        //JOptionPane.showMessageDialog(null,"facturas añadidas a la cola de impresion!");
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
                    JOptionPane.showMessageDialog(null, "El logo no ha sido cargado");
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
            JOptionPane.showMessageDialog(null, "informe guardado en FACTURASv20/" + rutaInforme);

            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle("Informe de Facturacion");
            viewer.setVisible(true);
            viewer.setAlwaysOnTop(true);
            viewer.setAutoRequestFocus(true);

        } catch (JRException jrex) {
            JOptionPane.showMessageDialog(null, "Error " + jrex + " al imprimir el informe");
            return false;
        }
        return true;

    }
}

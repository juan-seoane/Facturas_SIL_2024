package modelo;

import controladores.*;
import controladores.fxcontrollers.*;
import modelo.base.Config;
import modelo.base.Fichero;
import modelo.filtros.FiltroCategoria;
import modelo.filtros.FiltroDistribuidor;
import modelo.filtros.FiltroFecha;
import modelo.records.Extracto;
import modelo.records.Factura;
import modelo.records.Fecha;
import modelo.records.NIF;
import modelo.records.Nota;
import modelo.records.RazonSocial;
import modelo.records.TipoGasto;
import modelo.records.Totales;
import ui.*;
import ui.tablas.TablaFacturas;

import java.io.IOException;
import java.util.*;
import javax.swing.*;

public class ModeloFacturas {

    private static ModeloFacturas instancia = null;
    public static List<Factura> facturas;
    static Fichero<Factura> ficheroFacturas;
    //TODO : 11-04-2024 - Repasar los vectores de Facturas y demás...
    Vector<Factura> vectorfacturas;
    Stack<Factura> pilafacturasant = new Stack<Factura>();
    Stack<Factura> pilafacturassig = new Stack<Factura>();
    static Vector vectorcolumnas;
    static int ultimaID;
    public static int numeroFacturas = 0;

    private ModeloFacturas() {
        ficheroFacturas = new Fichero<Factura>(Config.getConfig().getRutaFCT().toString());
        facturas = ficheroFacturas.leer();
        this.numeroFacturas = facturas.size();
        this.ultimaID = facturas.size();
        vectorfacturas = generarVectorFacturas();
        
//        //System.out.println("Tamano del array de facturas: " + facturas.size());
    }
    
    public static ModeloFacturas getModelo(){
        
        if (instancia == null)
            instancia = new ModeloFacturas();
        return instancia;
    }
    
    public int getUltimaID() {
        return this.ultimaID;
    }

    public static int getNumeroFacturas() {
        return numeroFacturas;
    }

    public Factura getFactura(int index) {
        List<Factura> listafacturas = leerFacturas();
        if (listafacturas.size() > 0) {
            return listafacturas.get(index);
        } else {
            return new Factura();
        }
    }

    public Stack<Factura> getPilaFacturasAnt() {
        return pilafacturasant;
    }
    
    public Stack<Factura> getPilaFacturasSig() {
        return pilafacturassig;
    }
    
    public int getIndexOfFactura(Factura f){
        int index;
        
        List<Factura> listafact = leerFacturas();
        for (Factura fact : listafact )
            if (f.equals(fact)){
                index = fact.getID()-1;
                return index;
            }
        return 0;
    }
    
    public boolean autosave(String ruta){
        Fichero<Factura> auto = new Fichero<Factura>(ruta);
        if (auto.escribir((ArrayList<Factura>)facturas))
            return true;
        else return false;
    }
    
    public List<Factura> leerFacturas() {
        ficheroFacturas = new Fichero<Factura>(Config.getConfig().getRutaFCT().toString());
        facturas = ficheroFacturas.leer();
        this.numeroFacturas = facturas.size();
        this.ultimaID = facturas.size();
        return filtrar(facturas);
    }
    public List<Factura> filtrar(List<Factura> lista) {
        
        for (int i = 0; i < lista.size(); i++){
            Factura f1 = lista.get(i);
            for (int j = 0; j < lista.size(); j++){
                Factura f2 = lista.get(j);
                if (i == j)
                    continue;
                else if (f1.equals(f2)){
                    lista.remove(f2);
                }
            }
        }
        
        int i = 0;
        for (Factura f : lista){
            i++;
            f.setID(i);
            ultimaID = i;
        }
        
        List<Factura> lista2, lista3, lista4;
        if (TablaFacturas.filtrosActivos()){
            if (ControladorFacturas.filtros.getChbFiltroFecha().isSelected())
            {
            String año = Config.getConfig().año().año()+"";
            FiltroFecha filtro1 = new FiltroFecha(ControladorFacturas.filtros.getFechaInicio(),ControladorFacturas.filtros.getFechaFinal());
            lista2 = filtro1.filtrar(lista);
            }
            else lista2 = lista;
            if (ControladorFacturas.filtros.getChbFiltroCategoria().isSelected())
            {
            FiltroCategoria filtro2 = new FiltroCategoria(ControladorFacturas.filtros.getCmbCategoriasFiltros().getSelectedItem().toString());
            lista3 = filtro2.filtrar(lista2);
            }
            else lista3 = lista2;
            if (ControladorFacturas.filtros.getChbFiltroDistribuidor().isSelected())
            {
            FiltroDistribuidor filtro3 = new FiltroDistribuidor(ControladorFacturas.filtros.getFiltroDist());
            lista4 = filtro3.filtrar(lista3);
            }
            else lista4 = lista3;

            /** TODO : ACORDARSE DE ACTUALIZAR LOS TOTALES DESPUES DE FILTRAR! */
            return lista4;
        }
        return lista;
    }
    public boolean insertarFacturas(ArrayList<Factura> facturas) throws NumberFormatException, IOException {
        if ((numeroFacturas = facturas.size()) > 0) {
            Collections.sort(facturas);
        }
        //JOptionPane.showMessageDialog(null, "Espere unos segundos mientras se ordena la lista!");
        for (int i = 0; i < numeroFacturas; i++) {
            facturas.get(i).setID(i + 1);
        }
        PanelControl.getPanelControl().setNumfacturas(numeroFacturas);
        return (ficheroFacturas.escribir(facturas));
    }

    public boolean anexarFactura(modelo.records.Factura factura) throws NumberFormatException, IOException {

        factura.setID(this.ultimaID++);
        this.numeroFacturas++;
        PanelControl.getPanelControl().setNumfacturas(numeroFacturas);
        return (ficheroFacturas.anexar(factura));
    } 

    public boolean editarFactura(ArrayList<Factura> listafacturas, Factura factura, int index) throws NumberFormatException, IOException {
//        //System.out.println("Indice en Modelo : " + index);

        listafacturas.set(index, factura);
        Collections.sort(listafacturas);
        if (ficheroFacturas.escribir(listafacturas)) {
            leerFacturas();
            PanelControl.getPanelControl().setNumfacturas(numeroFacturas);
            return true;
        }
        return false;
    }

    public static void actualizarDistribuidorEnFacturas(RazonSocial antrs, RazonSocial nuevars){
        int res = JOptionPane.showConfirmDialog(null, "Quiere actualizar el Distribuidor en todas las facturas?", "ACTUALIZAR DISTRIBUIDOR EN FACTURAS:", JOptionPane.YES_NO_OPTION);
        
        if (res == JOptionPane.YES_OPTION){
            for (Factura f : facturas){
                if (f.getDistribuidor().equals(antrs)){
                    f.setDistribuidor(nuevars);
                }
            }
            JOptionPane.showMessageDialog(null,"Se han actualizado las facturas");
        }
        else 
            JOptionPane.showMessageDialog(null,"No se han actualizado las facturas");
    }
    
    public boolean borrarFactura(Factura factura, int index) throws NumberFormatException, IOException {
        pilafacturasant.push(factura);
//        //System.out.println("Indice en Modelo : " + index);
        int res = JOptionPane.showConfirmDialog(null,
                "Desea realmente borrar la factura?", "Advertencia!", JOptionPane.YES_NO_OPTION);

        if (res == JOptionPane.YES_OPTION) {
            facturas.remove(factura);
            JOptionPane.showMessageDialog(null, "Borrando factura!");
            numeroFacturas--;
            insertarFacturas((ArrayList<Factura>) facturas);
            return true;
        } else {
            return false;
        }
    }
/*
    public Factura recogerFormulario(FormularioFact form) {
        boolean isCIF;
        String letra;
        int numero = form.getNumNIFRS();
        if((letra = form.getLetraNIFRS().trim().toUpperCase()).equals("")){
            letra = form.getLetraCIFRS().trim().toUpperCase();
            isCIF = true;
        }else isCIF = false;

        String razon = form.getRS().toUpperCase();
        String textoNota = form.getNota();
        
        if (textoNota.equals("")) {
            textoNota = "";
        }
        if (form.esDevolucion()){
            textoNota = "Devolucion - "+textoNota;
        }
        //TODO : 11-04-2024 - Cambiar los datos de los formularios por datos obtenidos de la GUI JFX
        int dia = form.dia();
        int mes = form.mes();
        int año = form.año();
        String numeroFactura = form.getNumeroFactura().toUpperCase();
        String tipoGasto = form.getTipoGasto().toUpperCase();
        boolean esDevolucion = form.esDevolucion();
        form.limpiarFormulario();

        Fecha fecha = new Fecha(dia, mes, año);
//TODO: 11-04-2024 - Necesito un método (estático, a poder ser) para generar automáticamente el ID de cada Nota
        RazonSocial rs = completarRS(new RazonSocial(1, new NIF(numero, letra, isCIF), razon, new Nota(0,"")));
        // TODO : HAY QUE CONSEGUIR INTRODUCIR TODA LA RAZON SOCIAL COMO APARECE EN DISTRIBUIDORES 
        ArrayList<Extracto> subfacturas = form.getSubfacturas();

        Totales totales = form.getTotales();

        Nota nota = new Nota(1, textoNota);

        Factura f = new Factura(1, numeroFactura, fecha, rs, new TipoGasto(tipoGasto, ""), esDevolucion, subfacturas, totales, nota);
//        if (form.getEstado().equals("editando")) {
//            
//        } else {
////            facturas.add(f);
////            insertarFacturas((ArrayList) facturas);
////            this.facturas = leerFacturas();
//        }
        pilafacturasant.push(f);
        return f;
    }
    
    public RazonSocial completarRS(RazonSocial razon){
       Fichero<RazonSocial> ficheroRS = new Fichero<RazonSocial>(Config.getConfig().getRutaRS());
       ArrayList<RazonSocial> distribuidores = ficheroRS.leer();
       for (RazonSocial rs : distribuidores)
           if (rs.equals(razon))
               return rs;
       JOptionPane.showMessageDialog(null,"El distribuidor no concuerda con ninguno de los registrados!");
       return razon;
    }
*/  
    public Vector<Factura> generarVectorFacturas() {
        vectorfacturas = new Vector<Factura>();
        if (facturas.size() > 0) {
            for (Factura f : facturas) {
                vectorfacturas.add(f);
            }
        } else {
            vectorfacturas.add(new Factura());
        }
        return vectorfacturas;
    }

    public static Vector<String> getColumnas() {
        Vector<String> columnas = new Vector<String>();
        for (int i = 1; i <= Config.getConfig().nombresColumnas().length; i++) {
            columnas.add(Config.getConfig().nombresColumnas()[i]);
        }

        return columnas;
    }
}

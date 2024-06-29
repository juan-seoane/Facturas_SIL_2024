package modelo;

import controladores.*;
import controladores.fxcontrollers.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.io.IOException;
import java.util.*;
import javax.swing.*;

public class ModeloFacturas {

//#region CAMPOS_D_CLASE
    public static ModeloFacturas instancia;
    public static ArrayList<Factura> facturas;
    public static ArrayList<Factura> facturas_prev;
    public ArrayList<String[]> arrayFacturas;
    static Fichero<Factura> ficheroFacturas;
    //TODO : 11-04-2024 - Repasar los vectores de Facturas y demás...
    Stack<Factura> pilafacturasant = new Stack<Factura>();
    Stack<Factura> pilafacturassig = new Stack<Factura>();
    static Vector<Factura> vectorfacturas;
    static Vector vectorcolumnas;
    public static int ultimaID;
    public static int numeroFacturas = 0;
//#endregion

//#region constructor
    private ModeloFacturas() throws NullPointerException, IOException {
        System.out.println("[ModeloFacturas>Constructor] Creando nuevo ModeloFacturas");
        ficheroFacturas = new Fichero<Factura>(Config.getConfig(Controlador.usuario).getRutaFCT());
        System.out.println("[ModeloFacturas>Constructor] Leyendo fichero FCT -> " + Config.getConfig(Controlador.usuario).getRutaFCT() + " de Facturas del usuario "+ Controlador.usuario);
        this.arrayFacturas = ficheroFacturas.leerCSV(Config.getConfig(Controlador.usuario).getRutaFCT());
        ModeloFacturas.facturas = ConvertirArrayCSVenListaFCT(this.arrayFacturas);
        numeroFacturas = ModeloFacturas.facturas.size();
        System.out.println("[ModeloFacturas>Constructor] Fichero FCT de Facturas leido. Numero de Facturas = "+ numeroFacturas);
        ultimaID = ModeloFacturas.facturas.size();
//        System.out.println("[ModeloFacturas.java>Constructor] Última ID : " + ultimaID);
        vectorfacturas = generarVectorFacturas();
        
//        System.out.println("[ModeloFacturas.java>Constructor] Vector de Facturas generado!\n********************");
        
        System.out.println(" [ModeloFacturas>Constructor] Tamano del array de facturas: " + facturas.size());
    }
//#endregion

//#region getModelo    
    public static synchronized ModeloFacturas getModelo(){
        
        if (instancia == null)
            try {
                instancia = new ModeloFacturas();
            } catch (NullPointerException | IOException e) {
                System.out.println("[ModeloFacturas>getModelo] Excepc creando el modeloFCT");
                e.printStackTrace();
            }
        return instancia;
    }
//#endregion

//#region fct_getters
    public static synchronized int getUltimaID() {
        return ultimaID;
    }

    public static synchronized int getNumeroFacturas() {
        return numeroFacturas;
    }

    public Factura getFactura(int index) throws NullPointerException, IOException {
        List<Factura> listafacturas = leerFacturasSinFiltrar();
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
    
    public int getIndexOfFactura(Factura f) throws NullPointerException, IOException{
        int index;
        
        List<Factura> listafact = leerFacturasSinFiltrar();
        for (Factura fact : listafact )
            if (f.equals(fact)){
                index = fact.getID()-1;
                return index;
            }
        return 0;
    }
//#endregion

//#region AUTOSAVE
    public boolean autosave(String ruta){
/*         Fichero<Factura> auto = new Fichero<Factura>(ruta);
        if (auto.escribir((ArrayList<Factura>)facturas))
            return true;
        else */ return false;
    }
//#endregion
    
//#region leerFacturas_etc  
    public ObservableList<Factura> getListaFXFacturas(){
        List<Factura> fact_prev = null;
//TODO: 16-06-2024 - Habrá que filtrar la lista de facturas después
        try {
            fact_prev = leerFacturasSinFiltrar();
        } catch (NullPointerException | IOException e) {
            System.out.println("[ModeloFacturas>getListaFXFacturas] Error al recoger la lista observable (para JFX) de facturas");
            e.printStackTrace();
        }
        var facturasFX = FXCollections.observableList(fact_prev);
        return facturasFX;
    }
    
    public List<Factura> leerFacturas() throws NullPointerException, IOException {
        ficheroFacturas = new Fichero<Factura>(Config.getConfig(Controlador.usuario).getRutaFCT().toString());
        this.arrayFacturas = ficheroFacturas.leerCSV(ficheroFacturas.rutaArchivo);
        ModeloFacturas.facturas = ConvertirArrayCSVenListaFCT(this.arrayFacturas);
        numeroFacturas = ModeloFacturas.facturas.size();
        if (numeroFacturas == 0){
            ModeloFacturas.facturas.add(new Factura());
        }
        
        ultimaID = ModeloFacturas.facturas.size();

        return filtrar(ModeloFacturas.facturas);
    }

    public List<Factura> leerFacturasSinFiltrar() throws NullPointerException, IOException{
        ficheroFacturas = new Fichero<Factura>(Config.getConfig(Controlador.usuario).getRutaFCT().toString());
        ArrayList<String[]> arrayFct = ficheroFacturas.leerCSV(ficheroFacturas.rutaArchivo);
        var listaFct = ConvertirArrayCSVenListaFCT(arrayFct);
       
        numeroFacturas = listaFct.size();
        if (numeroFacturas == 0){
            listaFct.add(new Factura());
        }

        return listaFct;
    }
//#endregion

//#region listaFCTaCSVar
    public synchronized ArrayList<String[]> ConvertirListaFCTaCSV(ArrayList<Factura> lista){
        var arrayCSV = new ArrayList<String[]>();
        try {
            arrayCSV.add(getColumnas());
        } catch (NullPointerException | IOException e) {
            System.out.println("Error al trasvasar los nombres de columnas al CSV");
            e.printStackTrace();
        }
        int i=0;
        for (Factura f : lista){
            arrayCSV.addAll(Factura.convertirFCTaCSV(f));
            i++;    
        }
        return arrayCSV;
    }
//#endregion

//#region ArrayCSVaFCT
    private synchronized ArrayList<Factura> ConvertirArrayCSVenListaFCT(ArrayList<String[]> arrayFacturas) {
        facturas_prev = new ArrayList<Factura>(); 
        if (arrayFacturas.size()>0){
            for (String[] linea : arrayFacturas)
            {
                var resp = Factura.convertirCSVaFCT(linea);
                if (resp!=null){
                    facturas_prev.add(resp);
                }
            }
        }else{
            facturas_prev.add(new Factura());
        }
        System.out.println("\n[ModeloFacturas>ConvertirArrayCSVenListaFCT] Numero de facturas convertidas desde el CSV: " + facturas_prev.size() + "\n");
        return facturas_prev;
    }
    //#endregion 

//#region filtrar   
    public List<Factura> filtrar(List<Factura> lista) {
    
    //TODO: 14-06-2024 - Habría que convertir este ArrayList<String[]> a un ArrayList<Factura>...

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
        
        int ultID = 0;
        int i = 0;
        for (Factura f : lista){
            i++;
// TODO: 06-05-2024 - Hay que reemplazar estas operaciones con setters... No existen en un Java record...
            f.setID(i);
            ultID = i;
        }
        if (ultID!=0){
            ultimaID = ultID;
        }
        
/*
        List<Factura> lista2, lista3, lista4;
        if (TablaFacturas.filtrosActivos()){
            if (ControladorFacturas.filtros.getChbFiltroFecha().isSelected())
            {
            String año = Config.getConfigActual().configData.año().año()+"";
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

// TODO : ACORDARSE DE ACTUALIZAR LOS TOTALES DESPUES DE FILTRAR! 
            return lista4;
        }
*/
        return lista;
    }
//#endregion

//#region fct_ops 
    public boolean insertarFacturas(ArrayList<Factura> facturas) throws NumberFormatException, IOException {
        if ((numeroFacturas = facturas.size()) > 0) {
            Collections.sort(facturas);
        }
        //JOptionPane.showMessageDialog(null, "Espere unos segundos mientras se ordena la lista!");
// TODO: 06-05-2024 - Hay que reemplazar estas operaciones con setters... No existen en un Java record...
//        for (int i = 0; i < numeroFacturas; i++) {
//            facturas.get(i).setID(i + 1);
//        }
        PanelControl.getPanelControl().setNumfacturas(numeroFacturas);
        return (ficheroFacturas.escribir(facturas));
    }

    public boolean anexarFactura(modelo.records.Factura factura) throws NumberFormatException, IOException {
// TODO: 06-05-2024 - Hay que reemplazar estas operaciones con setters... No existen en un Java record...
//        factura.setID(this.ultimaID++);
        numeroFacturas++;
        PanelControl.getPanelControl().setNumfacturas(numeroFacturas);
        return (ficheroFacturas.anexar(factura));
    } 

    public boolean editarFactura(ArrayList<Factura> listafacturas, Factura factura, int index) throws NumberFormatException, IOException {
//        //System.out.println(" [ModeloFacturas] Indice en Modelo : " + index);

        listafacturas.set(index, factura);
        Collections.sort(listafacturas);
        if (ficheroFacturas.escribir(listafacturas)) {
            leerFacturasSinFiltrar();
            PanelControl.getPanelControl().setNumfacturas(numeroFacturas);
            return true;
        }
        return false;
    }

    public static void actualizarDistribuidorEnFacturas(RazonSocial antrs, RazonSocial nuevars){
        int res = JOptionPane.showConfirmDialog(null, "Quiere actualizar el Distribuidor en todas las facturas?", "ACTUALIZAR DISTRIBUIDOR EN FACTURAS:", JOptionPane.YES_NO_OPTION);
        
        if (res == JOptionPane.YES_OPTION){
            for (Factura f : facturas){
                if ((f.getRS().equals(antrs))){
                    f.setRS(nuevars);
                }
            }
            JOptionPane.showMessageDialog(null,"Se han actualizado las facturas");
        }
        else 
            JOptionPane.showMessageDialog(null,"No se han actualizado las facturas");
    }
    
    public boolean borrarFactura(Factura factura, int index) throws NumberFormatException, IOException {
        pilafacturasant.push(factura);
//        //System.out.println(" [ModeloFacturas] Indice en Modelo : " + index);
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
//#endregion

//#region RecogerFORM
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
       Fichero<RazonSocial> ficheroRS = new Fichero<RazonSocial>(Config.getConfigActual().getRutaRS());
       ArrayList<RazonSocial> distribuidores = ficheroRS.leer();
       for (RazonSocial rs : distribuidores)
           if (rs.equals(razon))
               return rs;
       JOptionPane.showMessageDialog(null,"El distribuidor no concuerda con ninguno de los registrados!");
       return razon;
    }
*/
//#endregion

//#region calcTOTs
    public String[] calcularTotales() {

        List<Factura> facturas = getListaFXFacturas();
        System.out.println("[ModeloFacturas>calcularTotales] Calculando resumen de datos ");
        
        int cuenta = 0;
        double base = 0;
        double iva = 0;
        double subtotal = 0;
        double baseNI = 0;
        double retenc = 0;
        double total = 0;

        for (Factura f : facturas)  {

            cuenta++;
            base += f.getTotales().getBase();
            iva += f.getTotales().getIVA();
            subtotal += f.getTotales().getSubtotal();
            baseNI += f.getTotales().getBaseNI();
            retenc += f.getTotales().getRetenciones();
            total += f.getTotales().getTotal();
        }
        String[] datos = { ""+base, ""+iva, ""+subtotal, ""+baseNI, ""+retenc, ""+total, ""+cuenta};
         return datos;
    }
//#endregion

//#region genVectorFact 
 //TODO: 14-06-2024 - repasar esta función, puede que no funcione...
    public Vector<Factura> generarVectorFacturas() {
        var vectorfacturas = new Vector<Factura>();
        
        if (facturas.size() == 0) {
            vectorfacturas.add(new Factura());
        }else{
            for (Factura f : ModeloFacturas.facturas) {
                    vectorfacturas.add(f);

                }    
            }
            return vectorfacturas;
        }
//#endregion

//#region getColumnas
    public static String[] getColumnas() throws NullPointerException, IOException {
        String[] columnas = new String[Config.getConfig(Controlador.usuario).uiData.getNombreColsFCT().length];
        for (int i = 0; i < Config.getConfig(Controlador.usuario).uiData.getNombreColsFCT().length; i++) {
            if (i==0){
                columnas[i] = "#";
                columnas[i]+= Config.getConfig(Controlador.usuario).uiData.getNombreColsFCT()[i];
            }else{
                columnas[i] = Config.getConfig(Controlador.usuario).uiData.getNombreColsFCT()[i];
            }
        }

        return columnas;
    }
//#endregion

}

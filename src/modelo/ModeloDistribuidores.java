/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controladores.*;
import ui.fxcontrollers.*;
import ui.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan Seoane
 */
public class ModeloDistribuidores {

    public static ModeloDistribuidores instancia = null;
    private static ArrayList<RazonSocial> listaDistribuidores;
    Fichero<RazonSocial> ficheroRS;
    public static Vector<Vector> vectorRS;
    public static Vector vectorcolumnas; 
    private static int ultimaID = 0;
    public static int numeroDistribuidores =  0;
    
     private ModeloDistribuidores(){
        ficheroRS = new Fichero<RazonSocial>(Config.getConfig().getRutaRS());
        listaDistribuidores = cargarDistribuidores();
        numeroDistribuidores = listaDistribuidores.size();
	vectorRS = generarVectorRS();
//        //System.out.println("Tamano del array de ditribuidores: "+numeroDistribuidores);
        
    }
    
    public static ModeloDistribuidores getModelo(){
        if (instancia == null)
            instancia = new ModeloDistribuidores();
        return instancia;
    }
     
    public ArrayList<RazonSocial> getListaDistribuidores() {
        return listaDistribuidores;
    }

    public Vector<Vector> getVectorRS() {
        return vectorRS;
    }

    public Vector getVectorcolumnas() {
        return vectorcolumnas;
    }

    public static int getUltimaID() {
        return ultimaID;
    }

    public static int getNumeroDistribuidores() {
        return numeroDistribuidores;
    }

    public void setVectorcolumnas(Vector vectorcolumnas) {
        this.vectorcolumnas = vectorcolumnas;
    }
    
    public boolean autosave(String ruta){
        
        Fichero<RazonSocial> auto = new Fichero<RazonSocial>(ruta);
        if (auto.escribir(listaDistribuidores))
            return true;
        else return false;
    }
    
    public ArrayList<RazonSocial> cargarDistribuidores() {  

        listaDistribuidores = ficheroRS.leer();
        
        for (RazonSocial rs : listaDistribuidores){
            rs.setTotales(actualizarTotales(rs, ModeloFacturas.facturas));
        }
            //JOptionPane.showMessageDialog(null,"ACTUALIZANDO LA BASE DE DATOS DE DISTRIBUIDORES");
        if (listaDistribuidores.size() == 0 ){
            listaDistribuidores.add(new RazonSocial());
        }
         return filtrarDistribuidores(listaDistribuidores);
    }
    
    public ArrayList<RazonSocial> filtrarDistribuidores(ArrayList<RazonSocial> lista){
        //ELIMINAMOS DUPPLICADOS
        for (int i = 0; i<lista.size(); i++){
            for (int j = 0; j< lista.size();j++){
                if (i == j)
                    continue;
                else { 
                    RazonSocial ri = (RazonSocial)lista.get(i);
                    RazonSocial rj = (RazonSocial)lista.get(j);
                    if (ri.equals(rj)){
                        lista.remove(ri);
                    }
                }                 
            }  
        }
        int i = 0;
        for (RazonSocial rs : lista){
            i++;
            rs.setID(i);
            ultimaID = i;
        }
        //APLICAMOS LOS FILTROS

        List<RazonSocial> lista2, lista3, lista4;
        if (TablaDistribuidores.filtrosactivos()){
            //**TODO  dejamnos un hueco para filtrar cantidades, por ejemplo
            lista2 = lista;
            if (ControladorDistribuidores.filtros.getChbFiltroCategoria().isSelected())
            {
            FiltroCategoriaRS filtro2 = new FiltroCategoriaRS(ControladorDistribuidores.filtros.getCmbCategoriasFiltros().getSelectedItem().toString());
            lista3 = filtro2.filtrar(lista2);
            }
            else lista3 = lista2;
            if (ControladorDistribuidores.filtros.getChbFiltroDistribuidor().isSelected())
            {
            FiltroDistribuidorRS filtro3 = new FiltroDistribuidorRS(ControladorDistribuidores.filtros.getFiltroDist());
            lista4 = filtro3.filtrar(lista3);
            }
            else lista4 = lista3;


            return (ArrayList<RazonSocial>)lista4;
        }
        return lista;
    }
    
    public Vector<Vector > generarVectorRS(){
        Vector<Vector > datamodel = new Vector<Vector >();
        int i = -1;
        for (RazonSocial rs : cargarDistribuidores())
        {
            i++;
            Vector fila = new Vector();
            fila.add(rs.getID());
            fila.add(rs.getNIF().toString());
            fila.add(rs.getNombre());
            fila.add(rs.getRazon());
            fila.add(rs.getDireccion());
            fila.add(rs.getCP());
            fila.add(rs.getPoblacion());
            fila.add(rs.getTelefono());
            fila.add(rs.getTotales().getTotal());
            fila.add(rs.getCategoria());
            datamodel.add(fila);
        }
        return datamodel;
    }
    
    public static Vector getColumnas(){
        Vector colnames = new Vector();
        colnames.add("ID");
        colnames.add("NIF");
        colnames.add("NOMBRE");
        colnames.add("RAZON");
        colnames.add("DIRECCION");
        colnames.add("C.P.");
        colnames.add("POBLACION");
        colnames.add("TELEFONO");
        colnames.add("TOTAL");
        colnames.add("CATEGORIA");
        return colnames;
    }

    public boolean insertarDistribuidor(RazonSocial rs) {
//        //System.out.println("Indice en Modelo : " + index);
        ultimaID++;
        rs.setID(ultimaID);
        listaDistribuidores.add(rs);
        Collections.sort(listaDistribuidores);
        if (insertarDistribuidores(listaDistribuidores)){
            listaDistribuidores = cargarDistribuidores();
            JOptionPane.showMessageDialog(null, "El Distribuidor ha sido registrado!");
            return true;
        }
        else return false;
    }
    
    public boolean insertarDistribuidores(ArrayList<RazonSocial> lista){
        if (ficheroRS.escribir(lista)) {       
            return true;
        }
        else return false;
    }
    public boolean editarDistribuidor(RazonSocial rs, int index) {
//        //System.out.println("Indice en Modelo : " + index);
        
        this.listaDistribuidores.set(index, rs);
        Collections.sort(listaDistribuidores);

        if (insertarDistribuidores(listaDistribuidores)) {
            listaDistribuidores = cargarDistribuidores();
           //JOptionPane.showMessageDialog(null, "El Distribuidor ha sido registrado!");
            return true;
        }
        else return false;
    }
    
    public boolean borrarDistribuidor(RazonSocial rs){
        this.listaDistribuidores.remove(rs);
        Collections.sort(listaDistribuidores);
        if (insertarDistribuidores(listaDistribuidores)) {
            listaDistribuidores = cargarDistribuidores();
//            JOptionPane.showMessageDialog(null, "El Distribuidor ha sido borrado!");
            return true;
        }
        else 
            return false;
    }
    
    public boolean comprobacionNIF(NIF  nuevoNIF){
        
        for (RazonSocial rs : this.getListaDistribuidores())
        {
            if (nuevoNIF.toString().equals(rs.getNIF().toString()))
            {
                JOptionPane.showMessageDialog(null,"La Razon Social ya existe!");
                FormularioFact.getFormulario().setNombreRS(rs.getRazon());

                return true;
            }
        }
       return false;
    }
    
    public boolean comprobacionRS(){
        boolean isCIF;
        NIF nuevoNIF;
        if (FormularioFact.getFormulario().getLetraNIFRS().equals("")){
            isCIF = true;
            nuevoNIF = new NIF(FormularioFact.getFormulario().getNumNIFRS(),FormularioFact.getFormulario().getLetraCIFRS().toUpperCase(),isCIF);
            if (!comprobacionNIF(nuevoNIF)){
                insertarDesdeFactura(nuevoNIF,FormularioFact.getFormulario().getRS());
            }
        }
        else{
            isCIF = false;
            nuevoNIF = new NIF(FormularioFact.getFormulario().getNumNIFRS(),FormularioFact.getFormulario().getLetraNIFRS().toUpperCase(),isCIF);
            if (!comprobacionNIF(nuevoNIF)){
                insertarDesdeFactura(nuevoNIF,FormularioFact.getFormulario().getRS());
            }
        }
        return false;
    }
    
    public boolean insertarDesdeFactura(NIF nuevoNIF,String razon){
        RazonSocial nuevoDistribuidor = new RazonSocial(this.getListaDistribuidores().size()+1,nuevoNIF,razon, new Nota());
        String nombreEmpresa = JOptionPane.showInputDialog(null, "Introduzca el nombre de la empresa :");
        nuevoDistribuidor.setNombre(nombreEmpresa.toUpperCase().trim());
            this.insertarDistribuidor(nuevoDistribuidor);
            JOptionPane.showMessageDialog(null,"La Razon Social ha sido registrada!");
            FormularioFact.getFormulario().resetRS();
        return true;
    }
    
    public boolean activarTablaEmergente(){
        ControladorDistribuidores.getControlador().setEstado(0);
        VentanaDistribuidores tablaemergente = new VentanaDistribuidores();
        tablaemergente.cargarTabla(this.getListaDistribuidores());
        tablaemergente.setVisible(true);
        while(!tablaemergente.seleccionado() & !tablaemergente.editar() & !tablaemergente.salir()){
            System.out.print("");
        }
        if (tablaemergente.seleccionado()){
            FormularioFact.getFormulario().setNombreRS(tablaemergente.razonseleccionada.getRazon());
            FormularioFact.getFormulario().setNumNIFRS(tablaemergente.razonseleccionada.getNIF().getNumero()+"");
            if (tablaemergente.razonseleccionada.getNIF().isCIF()){
                FormularioFact.getFormulario().setLetraCIFRS(tablaemergente.razonseleccionada.getNIF().getLetra()+"");
                FormularioFact.getFormulario().setLetraNIFRS("");
            }
            else{
                 FormularioFact.getFormulario().setLetraNIFRS(tablaemergente.razonseleccionada.getNIF().getLetra()+"");
                 FormularioFact.getFormulario().setLetraCIFRS("");
            }
            FormularioFact.getFormulario().setCategoria(tablaemergente.razonseleccionada.getCategoria());
        }
        FormularioFact.getFormulario().resetRS();
        tablaemergente.dispose();
        if (tablaemergente.editar()){
            PanelControl.pulsarboton(2);
            ControladorDistribuidores.setEstado(1);
//            PanelControl.getPanelControl().toFront();
        }
        return true;
    }
    
    public Totales actualizarTotales(RazonSocial rs, List<Factura> listafacturas){
        double base = 0.0;
        double iva = 0.0;
        double subtotal = 0.0;
        double baseni = 0.0;
        double retenciones = 0.0;
        double total = 0.0;

        for (Factura f : listafacturas)
        {
            if (rs.equals(f.getDistribuidor())){
                base += f.getTotales().getBase();
                iva += f.getTotales().getIVA();
                subtotal += f.getTotales().getSubtotal(); 
                baseni += f.getTotales().getBaseNI();
                retenciones += f.getTotales().getRetenciones();
                total += f.getTotales().getTotal();
            }
        }
        /** TODO : ARRREGLAR VARIOSIVAS , TIPOIVA, Y RET */
        /** TODO : HAY QUE ASOCIAR LOS TIPOS DE IVA HABITUALES DEL DISTRIBUIDOR CON LA RS Y EL FORMULARIO DE FACTURAS */
        Totales totales = new Totales(base, true, 0 , iva, subtotal, baseni, 0 , retenciones, total);
        return totales;
    }
}

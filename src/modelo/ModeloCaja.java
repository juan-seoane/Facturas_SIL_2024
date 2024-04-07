/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import ui.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import modelo.records.Config;

/**
 *
 * @author Juan Seoane
 */
public class ModeloCaja {
    private static ModeloCaja instancia = null;
    List<Caja> caja;
    static Fichero<Caja> ficheroCJA;
    Vector<Vector> vectorcaja;
//    Stack<Factura> pilafacturasant = new Stack<Factura>();
//    Stack<Factura> pilafacturassig = new Stack<Factura>();
    static Vector vectorcolumnas;
    static int ultimaID;
    public static int numeroEntradas = 0;
    public boolean filtros  = false;
    
    private ModeloCaja(){
        caja = leerCaja();
        this.numeroEntradas = caja.size();
        vectorcaja = generarVectorCaja();
        vectorcolumnas = getColumnas();
        ultimaID = caja.size();
        numeroEntradas = caja.size();
//        //System.out.println("tamaño de la list de caja : "+caja.size());
    }
    
    public static ModeloCaja getModelo(){ 
        if (instancia == null)
            instancia = new ModeloCaja();
        return instancia;
    }
    public Caja getCaja(int index){
        Caja c = leerCaja().get(index);
        return c;
    }
    
    public boolean autosave(String ruta){
        Fichero<Caja> auto = new Fichero<Caja>(ruta);
        if (auto.escribir((ArrayList<Caja>)caja))
            return true;
        else return false;   
    }
    
    public boolean guardarListaCajas(ArrayList<Caja> lista){
        if (ficheroCJA.escribir(lista))
            return true;
        else return false;
    }
    
    public boolean guardarCaja(Caja c){
        caja.add(c);
        Collections.sort(caja);
        int cuenta = 0;
        for (Caja cj : caja){
            cuenta++;
            cj.setID(cuenta);
        }
        if (guardarListaCajas((ArrayList<Caja>)caja))
            return true;
        else return false;
    }
    public void setFiltros(boolean bool){
        this.filtros = bool;
    }
    public boolean filtrosActivados(){
        return this.filtros;
    }
    public boolean editarCaja(Caja c1,Caja c2, int index){
        caja.remove(c1);
        caja.add(index,c2);
        Collections.sort(caja);
        int cuenta = 0;
        for (Caja cj : caja){
            cuenta++;
            cj.setID(cuenta);
        }
        if (guardarListaCajas((ArrayList<Caja>)caja))
            return true;
        else return false;
    }
    public boolean borrarEntrada(Caja c){
        caja.remove(c);
        Collections.sort(caja);
        int cuenta = 0;
        for (Caja cj : caja){
            cuenta++;
            cj.setID(cuenta);
        }
        if (guardarListaCajas((ArrayList<Caja>)caja))
            return true;
        else return false;
    }
    public Vector<Vector> generarVectorCaja(){
        List<Caja> cajafiltrada = leerCaja();
        Vector<Vector> vectorcaja = new Vector<Vector>();
        if (cajafiltrada.size()>0){
            for (Caja c : cajafiltrada){
                Vector temp = new Vector();
                temp.add(c.getID()+"");
                temp.add(c.getFecha().format);
                if (!c.isHaber()){
                    temp.add(c.getCaja()+"");
                    temp.add(" - ");
                }
                else if (c.isHaber()){
                    temp.add(" - ");
                    temp.add(c.getCaja()+"");
                } 
                temp.add(c.getOrigen());
                
                vectorcaja.add(temp);
            }
        }
        else{
            Vector temp = new Vector();
            temp.add(0);
            temp.add(new Fecha(1,1,2000));
            temp.add(" - ");
            temp.add(" - ");
            temp.add(" - ");
            
            vectorcaja.add(temp);
        }
            
        return vectorcaja;
    }
    public Vector getColumnas(){
        Vector columnas = new Vector();
        columnas.add("ID");
        columnas.add("FECHA");
        columnas.add("DEBE");
        columnas.add("HABER");
        columnas.add("ORIGEN");
        
        return columnas;
    }
    public List<Caja> filtrar (List<Caja> lista){       
        if (this.filtrosActivados()){
        List<Caja> lista1 = new ArrayList<Caja>();
        List<Caja> lista2 = new ArrayList<Caja>();
        List<Caja> lista3 = new ArrayList<Caja>();
        
        VentanaFiltrosCaja v = VentanaFiltrosCaja.getVentana();
        if (v.getChbxFecha().isSelected()){
            FiltroFechaCaja filtro1 = new FiltroFechaCaja(v.getTxtDesde().getText(), v.getTxtHasta().getText());
            lista1 = filtro1.filtrar(lista);
        }
        else lista1 = lista;
        if (v.getChbxOrigen().isSelected()){
            FiltroConceptoCaja filtro2 = new FiltroConceptoCaja(v.getTxtOrigen().getText());
            lista2 = filtro2.filtrar(lista1);
        }
        else lista2 = lista1;
        if (v.getChbxBalance().isSelected()){
            FiltroDebeHaberCaja filtro3 = new FiltroDebeHaberCaja(v.getBtnHaber().isSelected());
            lista3 = filtro3.filtrar(lista2);
        }
        else lista3 = lista2;
        
        return lista3;
        }
        else return lista;
    }
    public List<Caja> leerCaja(){
        ficheroCJA = new Fichero<Caja>(Config.getConfig().getRutaCJA().toString());
        caja = ficheroCJA.leer();
        this.numeroEntradas = caja.size();
        Collections.sort(caja);
        int i = 0;
        for (Caja c : caja){
           i++;
           c.setID(i);
        }
        return filtrar(caja);
    }   
}

package modelo;

import java.util.*;
import java.io.Serializable;

public class RazonSocial implements Serializable, Comparable{

    public int ID;
    private NIF nif;
    private String nombre;
    private String razon;
    private String direccion;
    private String CP;
    private String poblacion;
    private String telefono;
    private HashMap otrosDatos;
    private Totales totales;
    private Nota nota;
    private String categoria;
    private int[] tiposIVA;

    public RazonSocial() {
        this.ID = 0;
        this.nif = new NIF();
        this.nombre = "nombre de la empresa";
        this.razon = "nombre Razon Social";
        this.telefono = "900-000000";
        this.direccion = "direccion del distribuidor";
        this.CP = "99999";
        this.poblacion = "Ourense";
        this.otrosDatos = null;
        this.totales = new Totales();
        this.nota = new Nota();
        this.categoria = "COMPRAS";
        this.tiposIVA = null;
    }

    public RazonSocial(Integer ID, NIF nif,String razon, Nota nota) {
        this.ID = ID;
        this.nif = nif;
        this.nombre = "nombre de la empresa";
        this.razon = razon.toUpperCase();
        this.telefono = "900-000000";
        this.direccion = "direccion del distribuidor";
        this.CP = "99999";
        this.poblacion = "Ourense";
        this.otrosDatos = null;
        this.totales = new Totales();
        this.nota = nota;
        this.categoria = "COMPRAS";
        this.tiposIVA = null;
    }

    public NIF getNIF() {
        return this.nif;
    }

    public String getRazon() {
        return this.razon;
    }

    public int getID() {
        return this.ID;
    }

    public String getCP() {
        return this.CP;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCategoria() {
        return categoria;
    }

    public int[] getTiposIVA() {
        return tiposIVA;
    }

    public HashMap getOtrosDatos() {
        return otrosDatos;
    }

    public Totales getTotales() {
        return totales;
    }

    public String getNombre() {
        return nombre;
    }
    
    public Nota getNota(){
        return nota;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setID(int numero) {
        this.ID = numero;
    }

    public void setCP(String CP) {
        this.CP = CP;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setNif(NIF nif) {
        this.nif = nif;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setOtrosDatos(HashMap otrosDatos) {
        this.otrosDatos = otrosDatos;
    }

    public void setTotales(Totales totales) {
        this.totales = totales;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setTiposIVA(int[] tiposIVA) {
        this.tiposIVA = tiposIVA;
    }
    
    public boolean equals(Object b) {

        return (((this.nif.equals(((RazonSocial)b).getNIF())) & (this.razon.equals(((RazonSocial)b).getRazon()))));
    }
    
    public Vector toVector(){
  
	Vector vector = new Vector();

	vector.add(this.getID());
	vector.add(this.getNIF());
        vector.add(this.getNombre());
	vector.add(this.getRazon());
        vector.add(this.getDireccion());
        vector.add(this.getCP());
        vector.add(this.getPoblacion());
        vector.add(this.getTelefono());
        vector.add(this.getTotales().getTotal());
	vector.add(this.getCategoria());

	////System.out.println("transformando a Vector: "+vector.toString());
	return vector;

  }
    public String toString() {
        if (this.nif.isCIF())
            return ("CIF : " + this.nif.toString() + " Nombre : " + this.razon);
        else return ("NIF : " + this.nif.toString() + " Nombre : " + this.razon);
    }
    
    public int compareTo(Object b){
        if (this.equals((RazonSocial)b)){
            return 0;
        }
        else return (this.getNIF().compareTo(((RazonSocial)b).getNIF()));
     
  }    
}

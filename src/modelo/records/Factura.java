package modelo.records;

import java.util.ArrayList;
import java.util.Vector;

public class Factura implements Comparable<Factura> {
    private Integer ID;
    private String numeroFactura;
    private Fecha fecha;
    private RazonSocial RS;
    private TipoGasto categoria;
    private boolean esDevolucion;
    private ArrayList<Extracto> extractos;
    private Totales totales;
    private Nota nota;

    public Factura(Integer ID, String numeroFactura, Fecha fecha, RazonSocial RS, TipoGasto categoria, boolean esDevolucion, ArrayList<Extracto> extractos, Totales totales, Nota nota) {
        this.ID = ID;
        this.numeroFactura = numeroFactura;
        this.fecha = fecha;
        this.RS = RS;
        this.categoria = categoria;
        this.esDevolucion = esDevolucion;
        this.extractos = extractos;
        this.totales = totales;
        this.nota = nota;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Fecha getFecha() {
        return fecha;
    }

    public void setFecha(Fecha fecha) {
        this.fecha = fecha;
    }

    public RazonSocial getRS() {
        return RS;
    }

    public void setRS(RazonSocial RS) {
        this.RS = RS;
    }

    public TipoGasto getCategoria() {
        return categoria;
    }

    public void setCategoria(TipoGasto categoria) {
        this.categoria = categoria;
    }

    public boolean isEsDevolucion() {
        return esDevolucion;
    }

    public void setEsDevolucion(boolean esDevolucion) {
        this.esDevolucion = esDevolucion;
    }

    public ArrayList<Extracto> getExtractos() {
        return extractos;
    }

    public void setExtractos(ArrayList<Extracto> extractos) {
        this.extractos = extractos;
    }

    public Totales getTotales() {
        return totales;
    }

    public void setTotales(Totales totales) {
        this.totales = totales;
    }

    public Nota getNota() {
        return nota;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }
// TODO: 07-05-2024 - Revisar la forma de comparar facturas
    @Override
    public int compareTo(Factura b){
      return (this.fecha.compareTo(b.getFecha()));
   }
   
   public static Vector<Factura> toVector(Factura f){
     Vector<Factura> vector = new Vector<Factura>();
     vector.add(f);
 
     System.out.println("transformando a Vector: "+vector.toString());
     return vector;
   }
}


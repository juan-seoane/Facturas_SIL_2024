package modelo;

import java.util.*;
import java.io.Serializable;

public class Factura implements Comparable, Serializable{

  public int ID;
  public int ID2;
  private String numeroFactura;
  private Fecha fecha;
  private RazonSocial distribuidor;
  private TipoGasto categoria;
  private boolean esDevolucion;
  private ArrayList<Extracto> extractos;
  private Totales totales;
  private Nota nota;

  public Factura() {
  
	this.ID = 0;
	this.ID2 = 1;
	this.numeroFactura = "A0000000000";
	this.fecha = new Fecha(1,1,2000);
	this.distribuidor = new RazonSocial();
	this.categoria = new TipoGasto();
	this.esDevolucion = false;
	this.extractos = new ArrayList<Extracto>();
		extractos.add(new Extracto());
	this.totales = new Totales();
	this.nota = new Nota();
	
  }
  
  public Factura(Integer ID, String numeroFactura, Fecha fecha, RazonSocial RS, TipoGasto categoria, boolean esDevolucion, ArrayList<Extracto> extractos, Totales totales, Nota nota) {
  
	this.ID = ID;
	this.ID2 = extractos.size();
	this.numeroFactura = numeroFactura.toUpperCase();
	this.fecha = fecha;
	this.distribuidor = RS;
	this.categoria = categoria;
	this.esDevolucion = esDevolucion;
	this.extractos = extractos;
	this.totales = totales;
	this.nota = nota;
	
  }
  public int getID(){
  	return this.ID;
  }
  
  public String getNumeroFactura(){
  
	return this.numeroFactura;
  }
  public int getDia(){
	return this.fecha.getDia();
  }  
  public int getMes(){
	return this.fecha.getMes();
  }
  public int getAnho(){
	return this.fecha.getAnho();
  }
  public String getFecha(){
  
	return this.fecha.format;
  }
  public RazonSocial getDistribuidor(){
      return this.distribuidor;
  }
  public String getRS(){
  
	return (this.distribuidor.toString());
  }
  public NIF getNIF(){
     return (this.distribuidor.getNIF()); 
  }
  public String getNombreRS(){
	return (this.distribuidor.getNombre());
  }
  public String getRazonRS(){
	return (this.distribuidor.getRazon());
  }
  public Nota getNota(){
	return this.nota;
  }
  
  public String getCategoria(){
  
	return this.categoria.tipo;
  }
  
  public void setID(int i){
  	this.ID = i;
  }
  
  public void setDistribuidor(RazonSocial rs){
      this.distribuidor = rs;
  }
  
  public boolean esDevolucion(){
	return this.esDevolucion;
  }
  public int getTipoRetenciones(){
	return this.getTotales().getTipoRetenciones();
  }	
  public ArrayList<Extracto> getSubfacturas(){
	return this.extractos;
  }
  
   public Totales getTotales(){
  
	return this.totales;
  }

    public void setNota(String nota) {
        this.nota.setTexto(nota);
    }
  
  public boolean contiene(RazonSocial RS) {
  
	return this.distribuidor.equals(RS);
  }
  
  public boolean equals(Object b) {
	
	return (this.ID2 == ((Factura)b).ID2 && this.getFecha().equals(((Factura)b).getFecha()) && this.numeroFactura.equals(((Factura)b).getNumeroFactura()) &&  this.getRS().equals(((Factura)b).getRS())&&  this.getSubfacturas().equals(((Factura)b).getSubfacturas()) &&  this.getTotales().equals(((Factura)b).getTotales()));
	
  }

  public String toString(){
	
	return (this.ID + " Factura num: " + this.numeroFactura +" - #" +this.ID2+ " Fecha: "+ this.fecha + "\n"+ " Razon Social : "+ this.distribuidor + "\n" + this.categoria + "\n" + this.extractos + "\n" + this.totales + "\n" + this.nota +"\n\n");
  }
  
  public void ordenarExtractos() {
  
	Collections.sort(this.extractos);
  }
  
  public int compareTo(Object b){
  
     return (this.fecha.compareTo(((Factura)b).fecha));
  }
  
  public Vector toVector(){
  
	Vector vector = new Vector();

	vector.add(this.ID);
	vector.add(this.ID2);
	vector.add(this.fecha.format);
	vector.add(this.numeroFactura);
	vector.add(this.distribuidor.getNIF());
	vector.add(this.distribuidor.getRazon());
	vector.add(this.totales.getBase());
	vector.add(this.totales.getTipoIVA().getValor());
	vector.add(this.totales.getIVA());
	vector.add(this.totales.getSubtotal());
	vector.add(this.totales.getBaseNI());
	vector.add(this.totales.getTipoRetenciones());
	vector.add(this.totales.getRetenciones());
	vector.add(this.totales.getTotal());	
	vector.add(this.getCategoria());

	////System.out.println("transformando a Vector: "+vector.toString());
	return vector;

  }
}

package modelo;

import java.io.Serializable;

public class Extracto implements Comparable, Serializable{

  protected double base;
  protected TipoIVA tipoiva;
  protected double iva;
  protected double total;

  public Extracto(){
	
	this.base = 0.00;
	this.tipoiva = new TipoIVA(0);
	this.iva = 0.00;
	this.total = 0.00;
  }
  
  public Extracto(double base, int tipoiva){
  
	this.base = base;
	this.tipoiva = new TipoIVA(tipoiva);
	this.iva = base * this.tipoiva.getValor() / 100;
	this.total = this.base + this.iva;
  }
  
  public Extracto(int tipoiva, double total){
  
	this.total = total;
	this.tipoiva = new TipoIVA(tipoiva);
	this.base = (100 * this.total) / (100 + this.tipoiva.getValor());
	this.iva = this.base * this.tipoiva.getValor() / 100;
  }
  
  public Extracto(double base, int tipoiva, double iva, double total){
	 
	this.base = base;
	this.tipoiva = new TipoIVA(tipoiva);
	this.iva = iva;
	this.total = total;	 
  } 
  public double getBase(){
	return this.base;
  }
  public TipoIVA getTipoIVA(){
	return this.tipoiva;
  }
  public double getIVA(){
	return this.iva;
  }
  public double getTotal(){
	return this.total;
  }
  public String toString(){
  
	return (" base : "+this.base + " tipo IVA : " + this.tipoiva.getFormat() + " IVA : " + this.iva + " Total : " +this.total +"\n");
	
  }
  
  public int compareTo(Object b){
	
	if (b == null)
		return 1;
	else if (this.total < ((Extracto)b).total)
		return -1;
	else if (this.total ==((Extracto)b).total)
		return 0;
	else return 1;
  
  }
 
  public boolean equals(Object b) {
		
	return (this.base == ((Extracto)b).base && this.tipoiva.getValor() == ((Extracto)b).tipoiva.getValor() && this.iva == ((Extracto)b).iva && this.total == ((Extracto)b).total);
	  
  }

}

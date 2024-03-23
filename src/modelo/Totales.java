package modelo;

public class Totales extends Extracto {

  private boolean variosIVAs;
  private double subtotal;
  private double baseNI;
  private int ret;
  private double retenciones;

  public Totales(){
  
	this.variosIVAs = false;
	this.baseNI = 0.00;
	this.ret = 0;
	this.retenciones = 0.00;
  }
  
  public Totales(double base, boolean variosIVAs, int tipoIVA, double iva, double subtotal, double baseNI, int ret, double retenciones, double total){
  
	this.base = base;
	this.variosIVAs = variosIVAs;
	this.tipoiva = new TipoIVA(tipoIVA);
	this.iva = iva;
	this.subtotal = subtotal;
	this.baseNI = baseNI;
	this.ret = ret;
	this.retenciones = retenciones;
	this.total = total;
  }
  public int getTipoRetenciones(){
	return this.ret;
  } 
  public double getSubtotal(){
	return this.subtotal;
  }
  public double getBaseNI(){
	return this.baseNI;
  }
  public double getRetenciones(){
	return this.retenciones;
  }

    public boolean isVariosIVAs() {
        return variosIVAs;
    }

    public int getRet() {
        return ret;
    }

    public void setVariosIVAs(boolean variosIVAs) {
        this.variosIVAs = variosIVAs;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public void setBaseNI(double baseNI) {
        this.baseNI = baseNI;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public void setRetenciones(double retenciones) {
        this.retenciones = retenciones;
    }
  
  public boolean equals() {
  
	return false;	
  }

  public String toString(){
  
	return ("base : "+this.base + " tipo IVA : " + this.tipoiva.getFormat() + " IVA : " + this.iva + " subtotal : "+ this.subtotal + "\n base no imponible : "+ this.baseNI + " ret : "+this.ret +" % -> "+ this.retenciones + " Total : " +this.total +"\n");
  }
  public void calcularRetenciones() {
	
	
  }

  public boolean comprobarTotales() {
  
	return ((base + iva - retenciones == total) && (base *tipoiva.getValor() /100 == iva) && (base*ret/100 == retenciones));
  }

}

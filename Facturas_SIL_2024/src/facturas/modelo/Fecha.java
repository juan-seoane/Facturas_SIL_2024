package modelo;

import java.util.*;
import java.io.Serializable;

public class Fecha implements Comparable, Serializable{
	
  private int dia, mes, anho;
  public GregorianCalendar fecha;
  public long milis;
  public int formato;
  public String format;

  public Fecha(int dia, int mes, int anho){
	this.dia = dia;
	this.mes = mes;
	this.anho = anho;
	this.fecha = new GregorianCalendar(anho, (mes-1), dia);
	this.milis = this.fecha.getTimeInMillis();
	this.formato = 0;
	this.format = this.toString();
		
	}
  public int getDia(){
	return this.dia;
  }
  public int getMes(){
	return this.mes;
  }
  public int getAnho(){
	return this.anho;
  }
  public String format(){
      return (this.dia+"/"+this.mes+"/"+this.anho);
  }
  public String toString(){
	return (""+this.fecha.get(Calendar.DAY_OF_MONTH) + "/" + (this.fecha.get(Calendar.MONTH)+1) +"/"+ this.fecha.get(Calendar.YEAR));
	}
	
  public int compareTo(Object b) {
	if (b == null)
		return 1;
	if (this.fecha.before(((Fecha)b).fecha))
		return -1;
	else if (this.fecha.equals(((Fecha)b).fecha))
		return 0;
	else if (this.fecha.after(((Fecha)b).fecha))
		return 1;
	return 0;
		
  }	

}

package modelo.records;

import java.util.*;

public record Fecha(int dia, int mes, int año) implements Comparable<Fecha>{

  public String format(){
      return (this.dia()+"/"+this.mes()+"/"+this.año());
  }
  public long getMillis(){
	GregorianCalendar fecha = new GregorianCalendar(this.año(), (this.mes()-1), this.dia());
	long milis = fecha.getTimeInMillis();
	return milis;
  }
  public GregorianCalendar getFecha(){
	GregorianCalendar fecha = new GregorianCalendar(this.año(), (this.mes()-1), this.dia());
	return fecha;
  }
  public String toString(){
	GregorianCalendar fecha = new GregorianCalendar(this.año(), (this.mes()-1), this.dia());
	return (""+fecha.get(Calendar.DAY_OF_MONTH) + "/" + (fecha.get(Calendar.MONTH)+1) +"/"+ fecha.get(Calendar.YEAR));
	}

  public int compareTo(Fecha b) {
	GregorianCalendar fecha = new GregorianCalendar(this.año(), (this.mes()-1), this.dia());
	if (b == null)
		return 1;
	if (fecha.before(b.getFecha()))
		return -1;
	else if (fecha.equals(b.getFecha()))
		return 0;
	else if (fecha.after(b.getFecha()))
		return 1;
	return 0;
		
  }	

}

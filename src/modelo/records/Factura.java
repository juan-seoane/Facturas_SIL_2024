package modelo.records;

import java.util.*;
import java.io.Serializable;

public record Factura(Integer ID, String numeroFactura, Fecha fecha, RazonSocial RS, TipoGasto categoria, boolean esDevolucion, ArrayList<Extracto> extractos, Totales totales, Nota nota) implements Comparable<Factura>{

  public Factura() {
	  this(0, "A0000000000", new Fecha(1,1,2000), new RazonSocial(), new TipoGasto(null, null), false, new ArrayList<Extracto>(), new Totales(), new Nota(0,""));
  }
  
  public int getID2(){
  	return this.extractos().size();
  }

  public boolean contiene(RazonSocial RS) {
	  return this.RS().equals(RS);
  }
//TODO : 11-04-2024 - Creo que no va a funcionar el método de abajo, los extractos son final...
/*
  public void ordenarExtractos() {
  
	  Collections.sort(this.extractos());
  }
*/  
  public int compareTo(Factura b){
     return (this.fecha.compareTo(b.fecha()));
  }
  
  public static Vector<Factura> toVector(Factura f){
    Vector<Factura> vector = new Vector<Factura>();
    vector.add(f);

    System.out.println("transformando a Vector: "+vector.toString());
    return vector;
  }
}

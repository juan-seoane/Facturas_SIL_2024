package modelo.records;

import java.util.*;
import modelo.records.*;


public record MisDatos( int ID, NIF nif, String nombre, String razon, String direccion, String CP, String poblacion, String telefono, HashMap<String, String> otrosDatos, Totales totales, Nota nota, String categoria, int[] tiposIVA) implements Comparable<RazonSocial>{


    public MisDatos() {
        this(0, new NIF(0000, "A", false), "nombre de la empresa", "nombre Razon Social", "900-000000", "direccion del distribuidor", "99999", "Ourense", null, new Totales(), new Nota( 0, ""), "COMPRAS", null);
    }

    public MisDatos(Integer ID, NIF nif,String razon, Nota nota) {
        this(ID, nif, "nombre de la empresa", razon.toUpperCase(), "900-000000", "direccion del distribuidor", "99999", "Ourense", null, new Totales(), nota, "COMPRAS", null);
    }

    public Vector<Object> toVector(){
  
        Vector<Object> vector = new Vector<Object>();

        vector.add(this.ID());
        vector.add(this.nif());
        vector.add(this.nombre());
        vector.add(this.razon());
        vector.add(this.direccion());
        vector.add(this.CP());
        vector.add(this.poblacion());
        vector.add(this.telefono());
        vector.add(this.totales().total());
        vector.add(this.categoria());

        ////System.out.println("transformando a Vector: "+vector.toString());
        return vector;

    }
    
    public int compareTo(RazonSocial b){
        if (this.equals(b)){
            return 0;
        }
        else return (this.nif().compareTo(((RazonSocial)b).nif()));
     
  }    
}

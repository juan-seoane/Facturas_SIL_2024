package modelo.records;

import java.util.HashMap;
import java.util.ArrayList;
//import java.util.Vector; 
// TODO: 24-04-2024 - Parece que no puedo pasarlo a Vector
// TODO: 22-04-2024 - ¿Es necesario el campo ID? ¿No se genera automáticamente?
public record MisDatos( int ID, NIF nif,String nombreEmpresa, String nombre, String apellidos, String razon, String direccion, String CP, String poblacion, String telefono, HashMap<String, String> otrosDatos /*, Totales totales, Nota nota, String categoria, ArrayList<Integer> tiposIVA*/) implements Comparable<RazonSocial>{

/*
    public MisDatos() {
        this(0, new NIF(00000000, "A", false), "nombre de la empresa", "nombre Razon Social", "900-000000", "direccion del distribuidor", "99999", "Ourense", null, new Totales(), new Nota( 0, ""), "COMPRAS", null);
    }
*/
    public MisDatos(Integer ID, NIF nif,String razon) {
        this(ID, nif, "Nombre de la Empresa", "nombre", "apellidos", razon.toUpperCase(), "direccion del distribuidor", "99999", "Población del distribuidor","900-000000", new HashMap<String, String>() /*, new Totales(), nota, "COMPRAS", new ArrayList<Integer>()*/);
    }
/*
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
*/
    public String toJSON() {
		// TODO Auto-generated method stub
        // MisDatos[ID=0, nif=0-X, nombreEmpresa=Nombre de la Empresa, nombre=nombre, apellidos=apellidos, razon=NOMBREEMPRESAPRUEBA, direccion=900-000000, CP=direccion del distribuidor, poblacion=99999, telefono=Ourense, otrosDatos=null, totales=Totales[base=0.0, variosIVAs=false, tipoIVA=TipoIVA[valor=0, format=0], iva=0.0, subtotal=0.0, baseNI=0.0, ret=0, retenciones=0.0, total=0.0], nota=Nota[numero=0, texto=notaDePrueba], categoria=COMPRAS, tiposIVA=null]
		String cadenaResp = "{\n\t\"ID\": 0, \"nif\": { \"numero\": 12345678, \"letra\": \"X\", \"isCIF\": false},\n\t\"nombreEmpresa\": \"PruebaSA\",\n\t\"nombre\": \"nombrePrueba\",\n\t\"apellidos\": \"apellidos de prueba\",\n\t\"razon\": \"razon de prueba S.A.\",\n\t\"direccion\": \"calle de prueba n 00, portal A, piso 0\",\n\t\"CP\": \"12345\",\n\t\"poblacion\": \"poblacionDePrueba\",\n\t\"telefono\": \"123-456-789\",\n\t\"otrosDatos\": { \"fax\": \"123-123456º\" }\n}";
        return cadenaResp;
    }

    public int compareTo(RazonSocial b){
        if (this.equals(b)){
            return 0;
        }
        else return (this.nif().compareTo(((RazonSocial)b).nif()));
     
  }   
}

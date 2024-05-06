package modelo.records;

//import java.util.Vector; 
// TODO: 24-04-2024 - Parece que no puedo pasarlo a Vector
// TODO: 22-04-2024 - ¿Es necesario el campo ID? ¿No se genera automáticamente?
// TODO: 03-05-2024 - ¿Debería hacer 'Otros datos' un record aparte u otra clase que pueda serializar y controlar al pasar a JSON?
public record MisDatos( String user, NIF nif,String nombreEmpresa, String nombre, String apellidos, String razon, String direccion, String CP, String poblacion, String telefono /*, HashMap<String , String> otrosDatos , Totales totales, Nota nota, String categoria, ArrayList<Integer> tiposIVA*/) implements Comparable<RazonSocial>{

/*
    public MisDatos() {
        this(0, new NIF(00000000, "A", false), "nombre de la empresa", "nombre Razon Social", "900-000000", "direccion del distribuidor", "99999", "Ourense", null, new Totales(), new Nota( 0, ""), "COMPRAS", null);
    }
*/
    public MisDatos(String user, NIF nif,String razon) {
        this( user, nif, "Nombre de la Empresa", "nombre", "apellidos", razon.toUpperCase(), "direccion del distribuidor", "99999", "Población del distribuidor","900-000000" /*, new HashMap<String, String>(), new Totales(), nota, "COMPRAS", new ArrayList<Integer>()*/);
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
		// TODO: 28-04-2024 - Revisar esta salida comparándola con '/config/ADMIN/misdatos.json' > sobra 'ID', falta 'otrosDatos', el resto está desordenado...
		String cadenaResp = "{\n\t\"user\": \"" + this.user + "\",\n\t\"nif\": { \"numero\": " + this.nif.numero() +", \"letra\": \"" + this.nif.letra() + "\", \"isCIF\": " + this.nif.isCIF() + "},\n\t\"nombreEmpresa\": \"" + this.nombreEmpresa + "\",\n\t\"nombre\": \"" + this.nombre + "\",\n\t\"apellidos\": \"" + this.apellidos + "\",\n\t\"razon\": \"" + this.razon + "\",\n\t\"direccion\": \"" + this.direccion + "\",\n\t\"CP\": \"" + this.CP + "\",\n\t\"poblacion\": \"" + this.poblacion + "\",\n\t\"telefono\": \"" + this.telefono + "\""+/*,\n\t\"otrosDatos\": " + this.otrosDatos +*/"\n}";
        return cadenaResp;
    }
// TODO: 03-05-2024 - MisDatos se comparan en función del NIF de la Razón Social
    public int compareTo(RazonSocial b){
        if (this.equals(b)){
            return 0;
        }
        else return (this.nif().compareTo(((RazonSocial)b).nif()));
     
  }   
}

package modelo.base;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import modelo.records.Año;
import modelo.records.ConfigData;
import modelo.records.MisDatos;
import modelo.records.NIF;
import modelo.records.RutasConfig;
import modelo.records.RutasTrabajo;
import modelo.records.UIData;

import static java.nio.file.StandardCopyOption.*;

/*
 * @author Juan Seoane
 */
//TODO: 09/04/24 - Guardar en formato json
public class Fichero<T> {

    public String rutaArchivo;
    
    File fichero;
    FileInputStream fis;
    FileOutputStream fos;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    ObjectOutputStream aos;

    public Fichero(String rutaArchivo) {
//        //System.out.println("Creando nuevo fichero".toUpperCase());
        this.rutaArchivo = rutaArchivo;
        
        File directorio = new File(rutaArchivo.substring(0,rutaArchivo.lastIndexOf("/")));
        if (!directorio.exists()){
            try{
                directorio.mkdir();
            }catch(Exception e){
                File dir = new File(rutaArchivo.substring(0,rutaArchivo.lastIndexOf("/")));
            }
        }
        fichero = new File(rutaArchivo);
        if (!fichero.exists()) {
            try {
                // A partir del objeto File creamos el fichero físicamente
                if (fichero.createNewFile()) {
                    System.out.println("El fichero " + rutaArchivo + " se ha creado correctamente");
                } else {
                    System.out.println("No ha podido ser creado el fichero");
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else {
            System.out.println("El fichero " + this.rutaArchivo + " ya estaba creado");
        }
    }

    public ArrayList<T> leer() {
        System.out.println("LEYENDO FICHERO "+this.rutaArchivo);

        ArrayList<T> entradas = new ArrayList<T>();
        T entrada = null;
        if (fichero.length()!=0){
        abrirInputStream();
            try {
                while ((entrada = (T) ois.readObject()) != null) {
                    //System.out.println("objeto leido: " + entrada.toString());
                    entradas.add(entrada);
                }
//                //System.out.println("tamaño del vector de lectura: " + entradas.size());
//                //System.out.println("tipo de archivo : " + entradas.getClass().getName());

            } catch (NullPointerException npe) {
//                //System.out.println("Error en la lectura del tipo " + npe + " al leer el fichero "+ this.rutaArchivo);
            } catch (EOFException eofe) {
//                //System.out.println("Error en la lectura del tipo " + eofe + " al leer el fichero "+ this.rutaArchivo);
    //            ee.printStackTrace();
            } catch (IOException ioe) {
//                //System.out.println("Error en la lectura del tipo " + ioe + " al leer el fichero "+ this.rutaArchivo);
            } catch (ClassNotFoundException cnfe) {
//                //System.out.println("Error en la lectura del tipo " + cnfe + " al leer el fichero "+ this.rutaArchivo);
            }
        } else { 
        }
        cerrarInputStream();
        return entradas;
    }

    public synchronized boolean escribir(ArrayList<T> entradas) {
//        //System.out.println("Escribiendo fichero".toUpperCase()+" "+ this.rutaArchivo);
        abrirOutputStream();
        try {
//            //System.out.println("tamaño del vector de escritura: " + entradas.size());
            for (int i = 0; i < entradas.size(); i++) {
                oos.writeObject((T)(entradas.get(i)));
//                //System.out.println("escribiendo objeto numero " + i + " del tipo " + entradas.get(i).getClass().getName());
                oos.flush();
            }
//            JOptionPane.showMessageDialog(null,"Escribiendo en el fichero "+this.rutaArchivo+" !");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        cerrarOutputStream();
        return false;

    }

    public boolean anexar(T entrada) {
//        //System.out.println(">>>>>>Añadiendo a fichero".toUpperCase());
        ArrayList<T> entradas = this.leer();
        
        entradas.add(entrada);
        
        this.escribir(entradas);
//        //System.out.println("Entrada guardada!");
        return true;
        
    }
    
    public boolean editar(T entrada, int index){
//	//System.out.println(">>>>>>Editando entrada en fichero con indice "+index);
	ArrayList<T> entradas = this.leer();
	   if((entradas.set(index,entrada))!=null){
		this.escribir(entradas);
//		//System.out.println("Entrada editada!");
		return true;
	}
	return false;
    }
    
    public boolean borrar(T entrada, int index){
//	//System.out.println(">>>>>>Borrando entrada en fichero con indice "+index);

	ArrayList<T> entradas = this.leer();
	   if((entradas.remove(entrada))){
		this.escribir(entradas);
//		//System.out.println("Entrada borrada!");
		return true;
	}
	return false;
    }
    
    public boolean abrirInputStream() {
        try {
            fis = new FileInputStream(this.fichero);
        } catch (FileNotFoundException ex) {
            //System.out.println("Error de tipo FNFEx al leer el fichero");
            return false;
        }
        try {
            ois = new ObjectInputStream(fis);
        } catch (IOException ex) {
            //System.out.println("Error de tipo IOEx " + ex + " al leer el fichero "+ this.rutaArchivo);
            return false;
        }
//        //System.out.println("Flujos de entrada cargados al leer el fichero "+ this.rutaArchivo);
        return true;
    }

    public boolean abrirOutputStream() {
        try {
            fos = new FileOutputStream(this.fichero, false);
//            //System.out.println("Flujo de salida abierto!");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
            
            return false;
        }
        try {
            oos = new ObjectOutputStream(fos);
//            //System.out.println("Flujo de objetos abierto!");
        } catch (IOException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }

    public boolean cerrarInputStream() {
        try {
            if (fichero != null) {
                ois.close();
                fis.close();
//                //System.out.println("Flujo de entrada cerrado!");
                return true;
            }
        } catch (Exception e2) {
            //System.out.println("Error al cerrar la lectura del tipo " + e2 + " al leer el fichero "+ this.rutaArchivo);
        }
        return false;
    }

    public boolean cerrarOutputStream() {
        try {
            if (fichero != null) {
                aos.close();
                oos.close();
                fos.close();
//                //System.out.println("Flujo de objetos cerrado!");
                return true;
            }
        } catch (Exception e2) {
            //System.out.println("Error al cerrar la escritura del tipo " + e2 + " al leer el fichero");

        }
        return false;
    }

    public static boolean copia(String rutaOrigen, String rutaDestino){
        try {
                Files.copy(new File(rutaOrigen).toPath(), new File(rutaDestino).toPath(), REPLACE_EXISTING);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,"No se ha podido copiar el archivo "+rutaOrigen);
                return false;
            }
        return true;
    }

    public String toString(){
        
       return ("Fichero ->[ ruta= "+this.rutaArchivo+" ]"); 
        
    }
    
    public boolean estaVacio()
    {
        
        return (this.fichero.length()==0);
    }
//#region FILE_EXISTS()
    public static synchronized boolean fileExists(String ruta) throws NullPointerException, IOException{

        File f= new File(ruta);
        if (f.exists()&&f.isFile()){
            return true;
        }
        return false;
    }
//#endregion
//#region DIR_EXISTS()
    public static synchronized boolean dirExists(String ruta) throws NullPointerException, IOException{

        File f= new File(ruta);
        if (f.exists()&&f.isDirectory()){
            return true;
        }
        else if (!f.isFile())
            return false;
        else
            return false;
    }
//#endregion
//#region GUARDAR_JSON()
    public static synchronized boolean guardarJSON(String datosFormateados, String rutaYnombre){
              
        try{
            FileWriter f = new FileWriter(rutaYnombre);
            BufferedWriter output = new BufferedWriter(f);
            output.write(datosFormateados);
            //System.out.println("[Fichero.java]Fichero "+ rutaYnombre + " creado");
            output.close();
            System.out.println("[Fichero.java] Creado el fichero "+ rutaYnombre);
            return true;
        } catch (IOException ex) {
            System.out.println("[Fichero.java] Error guardando el fichero " + rutaYnombre);
            ex.printStackTrace();
            return false;
        }        
    }
//#endregion
//#region CREAR_CARPETA()
    public static synchronized boolean crearCarpeta(String ruta, String nombre) {
        File carpeta = new File(ruta, nombre);
        //La carpeta NO existe -> intenta crearla
        if ( ! carpeta.exists( )) {

            try {
                carpeta.mkdir();
                return true;

            } catch (Exception ex) { 
                System.out.println("[Fichero.java] Error creando la carpeta." +ruta + nombre);
                System.err.println( ex.toString( ));
                return false;
            }

        //La carpeta YA existe
        } else {
            System.out.println("[Fichero.java] La carpeta " + ruta + nombre + " ya existe.");
            return false; 
        }
    }

    public static synchronized boolean crearCarpeta(String rutaYnombre) {
        File carpeta = new File(rutaYnombre);
        //La carpeta NO existe -> intenta crearla
        if ( ! carpeta.exists( )) {

            try {
                carpeta.mkdir();
                return true;

            } catch (Exception e) { 
                System.out.println("[Fichero.java] Error creando la carpeta " + rutaYnombre);
                System.err.println( e.toString( ));
                return false;
            }

        //La carpeta YA existe
        } else {
            System.out.println("[Fichero.java] La carpeta " + rutaYnombre + " ya existe.");
            return false; 
        }
    }
//#endregion
//#region CREAR_FICHERO()
    public static synchronized boolean crearFichero(String ruta, String nombre) {
        File archivo = new File(ruta, nombre);
        //El archivo NO existe -> intenta crearlo
        if ( ! archivo.exists( )) {

            try {
                archivo.createNewFile();
                return true;

            } catch( Exception e ) {
                System.out.println("[Fichero.java] Error creando el archivo " + ruta + nombre);
                System.err.println( e.toString( ));
                return false;
            }

        } else {
            System.out.println("[Fichero.java] El archivo " + ruta + nombre + " ya existe.");
            return false;
        }
    }
    public static synchronized boolean crearFichero(String rutaYnombre) {
        File archivo = new File(rutaYnombre);
        //El archivo NO existe -> intenta crearlo
        if ( ! archivo.exists( )) {

            try {
                archivo.createNewFile();
                System.out.println("[Fichero.java] Creado el archivo "  +  rutaYnombre);
                return true;

            } catch( Exception e ) {
                System.out.println("[Fichero.java] Error creando el archivo "  +  rutaYnombre);
                System.err.println( e.toString( ));
                return false;
            }

        } else {
            System.out.println("El archivo " + rutaYnombre + " ya existe.");
            return false;
        }
    }
//#endregion
//#region LEER_JSON()
public static synchronized String leerJSON(String rutaYnombre){
    
    String fichero = "";
    try {
        FileReader f = new FileReader(rutaYnombre);
        BufferedReader input = new BufferedReader(f);
        String linea;
        while ((linea = input.readLine()) != null) {
            fichero += linea;
        }
        input.close();
        //System.out.println("[Fichero.java] Leído el fichero JSON "+ rutaYnombre);
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
        System.out.println("[Fichero.java] Error leyendo el fichero JSON "+ rutaYnombre);
    }
    return fichero;
}
//#endregion
//#region LEER_JSON_REC
    public static Object leerJSONrecord(String ruta, String tipo) {

        String json = Fichero.leerJSON(ruta);
	    //System.out.println("[ConfigTest>leerJSONrecord()] Fichero JSON " + ruta + " (tipo " + tipo.toUpperCase() + "record) leído:\n"+json);

		JsonElement jsonEl = new Gson().fromJson(json, JsonElement.class);
		JsonObject jsonObj = jsonEl.getAsJsonObject();

        Object objResp= null;
//#endregion
//#region RUTASCONFIG
        if (tipo.equals("rutasconfig")){
            String user = (jsonObj.get("user")).toString();
            user = user.substring(1, user.length()-1);
            String ruta1 = (jsonObj.get("rutaconfigdata")).toString();
            ruta1 = ruta1.substring(1, ruta1.length()-1);
            String ruta2 = (jsonObj.get("rutamisdatos")).toString();
            ruta2 = ruta2.substring(1, ruta2.length()-1);
            String ruta3 = (jsonObj.get("rutauidata")).toString();
            ruta3 = ruta3.substring(1, ruta3.length()-1);

            objResp= new RutasConfig(user, ruta1, ruta2, ruta3);
        }
//#endregion
//#region CONFIGDATA
        if (tipo.equals("configdata")){
            
            String dt1 = jsonObj.get("user").toString();
            dt1 = dt1.substring(1, dt1.length()-1);
            //System.out.println("\ncampo 'user' :\n" + dt1);

            String dt2 = jsonObj.get("año").toString();
            //System.out.println("\ncampo 'año' :\n" + dt2);
            JsonElement jsonEl2 = new Gson().fromJson(dt2, JsonElement.class);
		    JsonObject jsonObj2 = jsonEl2.getAsJsonObject();
            String c_año_año = jsonObj2.get("año").toString();
            int c_a = Integer.parseInt(c_año_año);
            String c_año_trim = jsonObj2.get("trimestre").toString();
            int c_t = Integer.parseInt(c_año_trim);

            Año c_año = new Año(c_a, c_t);

            String dt3 = jsonObj.get("rutas").toString();
            //System.out.println("\ncampo 'rutas' :\n" + dt3);
            JsonElement jsonEl3 = new Gson().fromJson(dt3, JsonElement.class);
            JsonObject jsonObj3 = jsonEl3.getAsJsonObject();
            String fct = jsonObj3.get("FCT").toString();
            fct = fct.substring(1,fct.length()-1);
            String rs = jsonObj3.get("RS").toString();
            rs = rs.substring(1,rs.length()-1);
            String cja = jsonObj3.get("CJA").toString();
            cja = cja.substring(1,cja.length()-1);

            RutasTrabajo r_trab = new RutasTrabajo(fct, rs, cja);

            String dt4 = jsonObj.get("tiposIVA").toString();
            //System.out.println("\ncampo 'tiposIVA' :\n" + dt4);
            dt4 = dt4.substring(1, dt4.length()-1);
            String[] c_tIVA_str = dt4.split(",");
            ArrayList<Integer> c_tIVA = new ArrayList<Integer>();
            for (int i=0; i<c_tIVA_str.length; i++){
                    c_tIVA.add(Integer.parseInt(c_tIVA_str[i]));
            }

            String dt5 = jsonObj.get("origenesCaja").toString();
            dt5 = dt5.substring(1, dt5.length()-1);
            //System.out.println("\ncampo 'origenesCaja' sin filtrar (sólo recortado al ppio y al final) :\n" + dt5);
        
            String[] c_orCaja_prev = dt5.split(",");
            //System.out.println("\ncampos de 'origenesCaja' separados :\n");
            //for (String str : c_orCaja_prev){
            //    System.out.print(" - "+str);
            //}

            var c_orCaja = new ArrayList<String>();
            for (int i=0; i<c_orCaja_prev.length; i++){
                c_orCaja.add(c_orCaja_prev[i].substring(1,c_orCaja_prev[i].length()-1));
            }
            //System.out.println("\ncampo 'origenesCaja' final :\n" + c_orCaja);

            objResp = new ConfigData(dt1, c_año, r_trab, c_tIVA, c_orCaja);
        }
//#endregion
//#region MISDATOS
        if (tipo.equals("misdatos")){
            String dt1 = jsonObj.get("user").toString();
            dt1 = dt1.substring(1, dt1.length()-1);
            //System.out.println("\ncampo 'user' :\n" + dt1);  

            String dt2_prev = jsonObj.get("nif").toString();
            //System.out.println("\ncampo 'nif' :\n" + dt2_prev);
            JsonElement jsonEl2 = new Gson().fromJson(dt2_prev, JsonElement.class);
		    JsonObject jsonObj2 = jsonEl2.getAsJsonObject();
            //System.out.println("\n[Fichero>leerJsONrecord] campo numero nif deserializado : " + jsonObj2.get("numero").toString());
            Integer c_n_nif = Integer.parseInt(jsonObj2.get("numero").toString());
            String c_l_nif = jsonObj2.get("letra").toString().substring(1,2);
            String c_b_nif = jsonObj2.get("isCIF").toString();
            Boolean c_iscif = false;
            if (c_b_nif.equals("true"))
                c_iscif = true;
            NIF c_nif = new NIF(c_n_nif, c_l_nif, c_iscif);
            //System.out.println("\ncampo 'nif' convertido :\n" + c_nif.toString());
// TODO: 05-05-2024 - Completar esto... Falta convertir el objeto 'nif' en un objeto 'NIF'

            String dt3 = jsonObj.get("nombreEmpresa").toString();
            dt3 = dt3.substring(1, dt3.length()-1);
            //System.out.println("\ncampo 'nombreEmpresa' :\n" + dt3);  

            String dt4 = jsonObj.get("nombre").toString();
            dt4 = dt4.substring(1, dt4.length()-1);
            //System.out.println("\ncampo 'nombre' :\n" + dt4);

            String dt5 = jsonObj.get("apellidos").toString();
            dt5 = dt5.substring(1, dt5.length()-1);
            //System.out.println("\ncampo 'apellidos' :\n" + dt5);

            String dt6 = jsonObj.get("razon").toString();
            dt6 = dt6.substring(1, dt6.length()-1);
            //System.out.println("\ncampo 'razon' :\n" + dt6);

            String dt7 = jsonObj.get("direccion").toString();
            dt7 = dt7.substring(1, dt7.length()-1);
            //System.out.println("\ncampo 'direccion' :\n" + dt7);

            String dt8 = jsonObj.get("CP").toString();
            dt8 = dt8.substring(1, dt8.length()-1);
            //System.out.println("\ncampo 'CP' :\n" + dt8);

            String dt9 = jsonObj.get("poblacion").toString();
            dt9 = dt9.substring(1, dt9.length()-1);
            //System.out.println("\ncampo 'poblacion' :\n" + dt9);

            String dt10 = jsonObj.get("telefono").toString();
            dt10 = dt10.substring(1, dt10.length()-1);
            //System.out.println("\ncampo 'telefono' :\n" + dt10);

           objResp = new MisDatos(dt1, c_nif,dt3,dt4,dt5,dt6,dt7,dt8,dt9,dt10);

        }
//#endregion
//#region UIDATA
        if (tipo.equals("uidata")){
            String dt1 = jsonObj.get("nombreColsFCT").toString();
            //System.out.println("\ncampo 'nombreColsFCT' :\n" + dt1);
            dt1 = dt1.substring(1, dt1.length()-1);
            int i = 0;
            String[] dt1Array = dt1.split(",");
            for (String str : dt1Array){
                str = str.substring(1, str.length()-1);
                //System.out.println(" - posición " + i + " = " + str);
                i++;
            }

            String dt2 = (jsonObj.get("anchoColsFCT")).toString();
            //System.out.println("\ncampo 'anchoColsFCT' :\n" + dt2);
            dt2 = dt2.substring(1, dt2.length()-1);

            String[] dt2ArrayStr = dt2.split(",");
            var dt2Array = new Integer[dt2.length()]; 
            i = 0;
            for (String str : dt2ArrayStr){
                if (!(str.equals(""))&&!(str==null)){
                    //System.out.println(" - posición " + i + " = " + str);
                    try{
                        dt2Array[i]= Integer.parseInt(dt2ArrayStr[i]);
                    }catch (Exception ex){
                        ex.printStackTrace();
                        System.out.println( "[Fichero.java] Error deserializando el fichero " + ruta);
                    }
                }
                i++;
            }

            objResp= new UIData(dt1Array,dt2Array); 
        }
//#endregion
    return objResp;
    }
}
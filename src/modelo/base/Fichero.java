package modelo.base;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
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
//                    //System.out.println("El fichero " + rutaArchivo + " se ha creado correctamente");
                } else {
//                    //System.out.println("No ha podido ser creado el fichero");
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else {
//            //System.out.println("El fichero " + this.rutaArchivo + " ya estaba creado");
        }
    }

    public ArrayList<T> leer() {
//        //System.out.println("LEYENDO FICHERO "+this.rutaArchivo);

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
//#region LEER_JSON()
    public static String leerJSON(String ruta){
    
        String fichero = "";
 
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                fichero += linea;
            }
        
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return fichero;
    }
//#endregion
//#region GUARDAR_JSON()
    public static boolean guardarJSON(String datosFormateados, String rutaYnombre){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaYnombre))) {
            bw.write(datosFormateados);
            System.out.println("Fichero creado");
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        
    }
//#endregion
}
package modelo.base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import modelo.records.Año;
import modelo.records.ConfigData;
import modelo.records.Contrasena;
import modelo.records.Credenciales;
import modelo.records.MisDatos;
import modelo.records.NIF;
import modelo.records.RutasConfig;
import modelo.records.RutasTrabajo;
import modelo.records.UIData;

// TODO: 19-04-2024 - Crear un Java Record 'CONFIG_DATA'
// TODO: 22-04-2024 - configdata.json y misdatos.json deberían contener un JsonArray de sus respectivos objetos
public class Config {

  //#region CAMPOS DE LA CLASE  
// TODO: Para que los cargue desde el JSON, estos valores deberían ser Strings, y con getters recoger los datos correspondientes
  public String usuario;
  public ConfigData configData;
  public MisDatos misDatos;
  public UIData uiData;
  public RutasConfig rutasconfig;

  public static ArrayList<Config> configuraciones;
  public static Config configActual = null;
//#endregion

//#region CONSTRUCTOR_NC_DE_CONFIG
  private Config(String user) throws NullPointerException, IOException{
    
    this.usuario = user;
    String dirCFGpers = "config/"+user.toUpperCase();
// Carpeta de Config personal
    if (!Fichero.dirExists(dirCFGpers)){
      Fichero.crearCarpeta(dirCFGpers);
      System.out.println("No existe carpeta personal para la Config del usuario " + user + "...Ha sido creada!");
    }
// Archivo 'rutasconfig.json'    
    String rutaCFG = "config/"+user.toUpperCase()+"/rutasconfig.json";
    File rutascfg = new File(rutaCFG);
    if (rutascfg.exists()){
      String rutasCFG = Fichero.leerJSON(rutaCFG); 
      this.rutasconfig = new Gson().fromJson(rutasCFG, RutasConfig.class);
      if (this.rutasconfig==null){
        System.out.println("No existen Rutas para la Config del usuario " + user);
        this.rutasconfig = getRutasConfigStd(user);
        Fichero.guardarJSON(this.rutasconfig.toJSON(), rutaCFG);
      }
    }else{
      System.out.println("[Config.java>Constructor] El fichero rutasconfig.json del usuario " + user +" no existe!!!\nSe creará una configuración estándar");
      this.rutasconfig = getRutasConfigStd(user);
      Fichero.guardarJSON(this.rutasconfig.toJSON(), rutaCFG);
    }
// Archivo 'configdata.json' 
      String rutaconfigdata = "config/" + user.toUpperCase() + "/configdata.json";
      File f_configdata = new File(rutaconfigdata);
      if (!(f_configdata.exists())||(f_configdata==null)){
        this.configData = getConfigDataStd();
        Fichero.guardarJSON(this.configData.toJSON(), rutaconfigdata);
      }else
        this.configData = getConfigData();
// Archivo 'misdatos.json' 
//TODO: ¿Porqué tienen que estar los datos fiscales del usuario todo el tiempo en memoria? No se cargan hasta que son necesarios...
    String rutamisdatos = "config/" + user.toUpperCase() + "/misdatos.json";
    File f_misdatos = new File(rutamisdatos);
    if (!(f_misdatos.exists())||(f_misdatos==null)){       
      this.misDatos = getMisDatosStd();
        Fichero.guardarJSON(this.misDatos.toJSON(), rutamisdatos);
    } else
      this.misDatos = getMisDatos();
// Archivo 'uidata.json' 
    String rutauidata = "config/" + user.toUpperCase() + "/uidata.json";
    File f_uidata = new File(rutauidata);
    if (!(f_uidata.exists())||(f_uidata==null)){       
      this.uiData = getUiDataStd();
        Fichero.guardarJSON(this.uiData.toJSON(), rutauidata);
    } else     
      this.uiData = getUiData();
// Asignamos la configActual, borrando el objeto 'MisDatos' 
// TODO: 06-05-2024 : Hay que hacer un método para borrar MisDatos (no tiene sentido que se guarden todo el tiempo en memoria)     
    this.misDatos = null;   
// Archivos de trabajo
    System.out.println("...Vamos a supervisar y/o crear los archivos de trabajo del usuario " + user + ":\n");
    String rutaDirTrab = "./datos/" + user.toUpperCase() ;
    Fichero.crearCarpeta(rutaDirTrab); //ya comp`rueba si existe o no...
    String trab1 = "/FCT" + this.configData.getAño().getAño()+this.configData.getAño().getTrimestre() + ".fct";
    // TODO: 06-05-2024 - Decidir si el archivo RS.rs se actualiza cada año
    String trab2 = "/RS"+ /*this.configData.año().año() +*/ ".rs";
    String trab3 = "/CJA"+ this.configData.getAño().getAño()+this.configData.getAño().getTrimestre() + ".cja";
    Fichero.crearFichero(rutaDirTrab, trab1);
    Fichero.crearFichero(rutaDirTrab, trab2);
    Fichero.crearFichero(rutaDirTrab, trab3);

    Config.configActual = this;
  
    System.out.println("[Config.java>Constructor] Creada la Configuracion [vacía] del Usuario " + user );
    }
    // TODO: 23-04-2024 - Aquí está la nueva configuración, ya creada... Luego habría que grabarla en los ficheros config.json, configdata.json, misdatos.json, uidata.json
  //#endregion

//#region GET_CONFIG(user)
// TODO: 11-04-2024 - Parece que, ahora mismo, no tiene sentido llamar a un constructor sin definir el usuario...
// TODO: 11-04-2024 - Pensar cómo meter usuario
// TODO: 18-04-2024 - Hacer un Diagrama de Flujo de todo el proceso de Config, para ajustar
// TODO: 02-05-2024 - Estoy probando con 'synchronized' intentando que no use la config mientras se genera...
  public static synchronized Config getConfig(String user) throws NullPointerException, IOException{
      try{
        new Config(user);
      }catch (Exception ex){
        ex.printStackTrace();
        System.out.println("[Config>getConfig] Error recogiendo la config de "+user);
        System.exit(0);
      }
    return Config.getConfigActual();
  }
//#endregion

//#region NUEVOS GETTERS
  private RutasConfig getRutasConfigStd(String user) {
    String ruta2 = "config/"+user.toUpperCase()+"/configdata.json";
    String ruta3 = "config/"+user.toUpperCase()+"/misdatos.json";
    String ruta4 = "config/"+user.toUpperCase()+"/uidata.json";
    RutasConfig resp = new RutasConfig(user, ruta2, ruta3, ruta4);
    return resp;    
  }

  public synchronized ConfigData getConfigDataStd() {

    var tiposIVAprueba = new ArrayList<Integer>();
    tiposIVAprueba.add(0);
    tiposIVAprueba.add(21);
    var origenesPrueba = new ArrayList<String>();
    origenesPrueba.add("caja");
    origenesPrueba.add("otros");  
// TODO: 28-04-2024 - Falta asignar rutas estándar para esta config (usuario ya definido)
    var rutas = new RutasTrabajo("config/"+this.usuario.toUpperCase()+"/FCT20242.fct", "config/"+this.usuario.toUpperCase()+"/RS.rs", "config/"+this.usuario.toUpperCase()+"/CJA20242.cja");


    Año año = new Año(2024, 2);

    ConfigData cfDataPrueba = new ConfigData("TESTuSER", año, rutas, tiposIVAprueba, origenesPrueba);
    System.out.println("Asignando ConfigData Estándar");
    return cfDataPrueba;
  }

  public synchronized MisDatos getMisDatosStd() {

    MisDatos msDtsPrueba = new MisDatos("TESTuSER", new NIF( 12345678, "X", false),"nombreEmpresaPrueba");
    System.out.println("Asignando MisDatos Estándar");
    return msDtsPrueba;  
  }

  public synchronized UIData getUiDataStd() {

    var nombresColprueba = getNombresColumnasStandard();
    var anchosColprueba = getAnchoColumnasStandard();
      
    UIData uiDataPrueba = new UIData(nombresColprueba,anchosColprueba);
    System.out.println("Asignando UIData Estándar");
    return uiDataPrueba;
    }

  public synchronized String[] getNombresColumnasStandard(){

    String[] nCols={"ID","#","fecha","numFactura","NIF","R.S.","base","tipo","IVA","SubTotal","base N.I.","t ret","Retenc","Total","Concepto"};	
    System.out.println("Asignando Nombres de Columna Estándar");
    return nCols;
  }

  public synchronized Integer[] getAnchoColumnasStandard(){

    Integer[] aCols={60,20,0,0,100,180,0,40,0,0,0,40,0,0,0};
    System.out.println("Asignando Anchos de Columna Estándar");
    return aCols; 
  }

  public synchronized UIData getUiData() {
    String ruta = this.rutasconfig.getRutaUIData();
    String datos = Fichero.leerJSON(ruta);
    UIData resp = new Gson().fromJson(datos, UIData.class);
    System.out.println("[Config.java] Asignando UIData del consiguiente archivo:\n" + resp.toJSON() + "\n");
    
    return resp;
    }
  
  public synchronized MisDatos getMisDatos() {
    String ruta = this.rutasconfig.getRutaMisDatos();
    String datos = Fichero.leerJSON(ruta);
    MisDatos resp = new Gson().fromJson(datos, MisDatos.class);
    System.out.println("[Config.java] Asignando MisDatos del consiguiente archivo:\n" + resp.toJSON() + "\n");
    
    return resp;
  }

  public synchronized ConfigData getConfigData() {
    String ruta = this.rutasconfig.getRutaConfigData();
    String datos = Fichero.leerJSON(ruta);
    ConfigData resp = new Gson().fromJson(datos, ConfigData.class);
    System.out.print("\n[Config.java>getConfigData()] : Datos leídos en " + this.rutasconfig.getRutaConfigData() + " :\n" + resp.toJSON());

    return resp;
  }
//#endregion

//#region OTROS GETTERS 
public static synchronized Config getConfigActual() {
  return Config.configActual;
}
// TODO: 28-04-2024 - Controlar que esté todo boien después de quitar el static de algunos métodos get
public String getUsuario(){
    return Config.getConfigActual().usuario;
  }

  public String getRutaRS(){

    String rs = getConfigData().getRutas().getRS();
    System.out.println("Asignando Ruta RS desde la config");
    return rs;
  }

  public String getRutaFCT(){

    String fct = getConfigData().getRutas().getFCT();
    System.out.println("Asignando Ruta FCT desde la config");
    return fct;
  }
  
  public String getRutaCJA(){
    String cja = getConfigData().getRutas().getCJA();
    System.out.println("Asignando Ruta CJA desde la config");
    return cja;
  }
//#endregion

// TODO: 10-04-2024 - Ver cómo buscar la lista de contrasenas de un usuario...  
// TODO: sustituir la lista siguiente por una lectura del archivo config.cfg 
// TODO: 19-04-2024 - Sopesar si debería generar un archivo 'std_config.json' con la configuración inicial (la de admin:admin)
// TODO: 20-04-2024 - Cambiar el nombre del archivo base 'config.json' a 'creds.json' y los datos de 'misdatos.json' para 'ADMIN'
// TODO: 20-04-2024 - Falta crear misdatos.json (se puede crear un archivo tipo) 
// TODO: 19-04-2024 - Completar RecConfig  

//#region REC_CONFIG()

// TODO: 23-04-2024 - Aquí debería guardar los 4 archivos JSON: config, configdata, misdatos, uidata...
// TODO : 02-05-2024 - También probando con 'synchronized', para esperar a que acabe antes de proseguir...  
  
  public static synchronized boolean recConfig(String p_usuario,Config p_config) throws NullPointerException, IOException{
    // Crea las carpetas personales (si no existen)
    crearCarpetasPersonales(p_usuario);

    String ruta1 = "config/"+p_usuario.toUpperCase()+"/rutasconfig.json";
    String ruta2 = "config/"+p_usuario.toUpperCase()+"/configdata.json";
    String ruta3 = "config/"+p_usuario.toUpperCase()+"/misdatos.json";
    String ruta4 = "config/"+p_usuario.toUpperCase()+"/uidata.json";
// TODO: 20-04-2024 - Rehacer 'datosFormateados', enviar la config a formatearse como JSON, o incluir una std_config 
// TODO: 23-04-2024 - Rehacer el método toString() para adaptarlo a la salida en JSON
    String datos1 = p_config.rutasconfig.toJSON();
    System.out.println("[Config.java]->\n" + datos1);
    String datos2 = p_config.getConfigData().toJSON();
    System.out.println("[Config.java]->\n" + datos2);
    String datos3 = p_config.getMisDatos().toJSON();
    System.out.println("[Config.java]->\n" + datos3);
    String datos4 = p_config.getUiData().toJSON();
    System.out.println("[Config.java]->\n" + datos4);
    
    // Crea los ficheros de Configuracion (también si no existen)
    if (!Fichero.fileExists(ruta1)){
      Fichero.guardarJSON(datos1, ruta1);
    }
    if (!Fichero.fileExists(ruta2)){
      Fichero.guardarJSON(datos2, ruta2);
    }
    if (!Fichero.fileExists(ruta3)){
      Fichero.guardarJSON(datos3, ruta3);
    }
    if (!Fichero.fileExists(ruta4)){
      Fichero.guardarJSON(datos4, ruta4);
    }
    
    /*
    System.out.println("El nuevo año se ha establecido en  " + p_config.getConfigData().año().año()+" - Trimestre: " + p_config.getConfigData().año().trimestre());
    
    String dirTrab = "datos/"+p_usuario.toUpperCase();
    String fiTrab1 = p_config.getConfigData().rutas().FCT();
    String fiTrab2 = p_config.getConfigData().rutas().RS();
    String fiTrab3 = p_config.getConfigData().rutas().CJA();
    */
    // TODO: 02-05-2024 - Por ahora ocultamos la creación de los archivos de trabajo...
    /*
    if(Fichero.crearCarpeta(dirTrab)&&Fichero.crearFichero(fiTrab1)&&Fichero.crearFichero(fiTrab2)&&Fichero.crearFichero(fiTrab3)){
      System.out.println("Archivos de trabajo personales en orden para usuario "+p_usuario);
    */  
    return true;
  }
//#endregion
//#region CREAR_DIRPERS()
  private static synchronized boolean crearCarpetasPersonales(String p_usuario) throws NullPointerException, IOException {
    
    Boolean rutaConfigOK = false;
    Boolean rutaTrabOK = false;

    String rutadircfg="config/"+p_usuario.toUpperCase()+"/";
    String rutadirtrab = "datos/"+p_usuario.toUpperCase()+"/";
    File dircfg  = new File(rutadircfg);
    File dirtrab = new File(rutadirtrab);
    if (dircfg.exists()&&Fichero.dirExists(rutadircfg)){
      System.out.println("El subdirectorio de configurtación personal del usuario "+p_usuario+" ya existe!");
      rutaConfigOK = true;
    }else{
      String rutacfg = "config/";
      if(Fichero.crearCarpeta(rutacfg, p_usuario.toUpperCase()))
        rutaConfigOK = true;
      else rutaConfigOK = false;
    }
      if (dirtrab.exists()&&Fichero.dirExists(rutadirtrab)){
        System.out.println("El subdirectorio de trabajo personal del usuario "+p_usuario+" ya existe!");
        rutaTrabOK = true;
      }else{
        String rutatrab = "datos/";
        if(Fichero.crearCarpeta(rutatrab, p_usuario.toUpperCase()))
          rutaTrabOK = true;
        else rutaTrabOK = false;
      } 
      if (rutaConfigOK&&rutaTrabOK) 
        return true;
      else return false;

  }
//#endregion
//#region LEER_CFG_JSON()
  public static synchronized RutasConfig leerRutasCFGjson(String ruta){
// TODO: 11-04-2024 - Crear métodos estáticos para guardar/leer las credenciales en el archivo base config.json
// TODO : Si no existe el fichero, devolver 'false' y crearlo 
    RutasConfig resp;
    File fichCFG = new File(ruta);
    if (fichCFG.exists()){
      String fichero = Fichero.leerJSON(ruta);
      Gson gson = new Gson();
//TODO: 04-05-2024 - Parece que el problema está aquí, cuando intenta leer el archivo 'rutasconfig.json'
 
      String json = Fichero.leerJSON(ruta);
      System.out.println("[ConfigTest>leerRutasConfigJson()] Fichero JSON leído:\n"+json);

      JsonElement rutasEl = new Gson().fromJson(json, JsonElement.class);
      JsonObject rutasObj = rutasEl.getAsJsonObject();

      String user = (rutasObj.get("user")).toString();
      user = user.substring(1, user.length()-2);
      String ruta1 = (rutasObj.get("rutaconfigdata")).toString();
      ruta1 = ruta1.substring(1, ruta1.length()-2);
      String ruta2 = (rutasObj.get("rutamisdatos")).toString();
      ruta2 = ruta2.substring(1, ruta2.length()-2);
      String ruta3 = (rutasObj.get("rutauidata")).toString();
      ruta3 = ruta3.substring(1, ruta3.length()-2);

      resp= new RutasConfig(user, ruta1, ruta2, ruta3);

      return resp;
    }else{
      return null;
    }
  }
//#endregion

//#region LEER_CREDS()
  public static synchronized Credenciales leerCredenciales(String ruta){
    String ficheroResp = Fichero.leerJSON(ruta);
    System.out.println(ficheroResp);
    
    Gson gson = new Gson();
    // TODO: 11-04-2024 - Revisar esto: Si es Arraylist.class o Contrasena.class
  
    Credenciales credenciales = gson.fromJson(ficheroResp, Credenciales.class);
    System.out.println(credenciales.toString());
    return credenciales;
  }
//#endregion

//#region GUARDAR_CREDS()
  public static boolean guardarCredenciales(String user) {
	
    String ruta="config/creds.json";

    Credenciales creds= new Credenciales();
    File f=new File(ruta);
    if (f.exists()&&f.isFile()){
      creds = leerCredenciales(ruta);
    }
    creds.creds.add(new Contrasena(user, user));
    
    // TODO: 11-04-2024 - Falta guardar en formato JSON
    if(Fichero.guardarJSON(creds.toString(), ruta))
      return true;
    else
      return false;
  }
//#endregion


}

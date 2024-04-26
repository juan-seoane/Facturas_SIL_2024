package modelo.base;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;

import com.google.gson.Gson;

import modelo.records.Año;
import modelo.records.ConfigData;
import modelo.records.MisDatos;
import modelo.records.NIF;
import modelo.records.Nota;
import modelo.records.UIData;
// TODO: 19-04-2024 - Crear un Java Record 'CONFIG_DATA'
// TODO: 22-04-2024 - configdata.json y misdatos.json deberían contener un JsonArray de sus respectivos objetos
public class Config {

  public String usuario;
  public ConfigData configData;
  public MisDatos misDatos;
  public UIData uiData;

  public static ArrayList<Config> configuraciones;
  public static Config configActual = null;
/*  
  public String[] nombresColumnas;
  public int[] anchoColumnas;
  public Año año;

  private HashMap<String, String> rutas;
  private ArrayList<TipoGasto> tiposGasto;
  private ArrayList<TipoIVA> tiposIVA;
  private ArrayList<Concepto> origenesCaja;

  private Config( String p_user, ConfigData p_configData, MisDatos p_misDatos, UIData p_uiData){
    this.usuario = p_user;
    this.configData = p_configData;
    this.misDatos = p_misDatos;
    this.uiData = p_uiData;
  }
*/
//#region CONSTRUCTOR_NC_DE_CONFIG
  private Config(String user){
    
    this.usuario = user;
    this.configData = getConfigDataStd();
    this.misDatos = getMisDatosStd();
    this.uiData = getUiDataStd();
    Config.configActual = this;

    /** TODO: misDatos se leeran tambien de un archivo misdatos.cfg **/
    System.out.println("Creada la Configuracion [vacía] del Usuario " + user + " con un Constructor No Canónico");
    // TODO: 23-04-2024 - Aquí está la nueva configuración, ya creada... Luego habría que grabarla en los ficheros config.json, configdata.json, misdatos.json, uidata.json
  }
//#endregion
//#region GETTERS DE CONFIGS STANDARD
// TODO: 23-04-2024 - Arreglar estos getters
private ConfigData getConfigDataStd() {

  var tiposIVAprueba = new ArrayList<Integer>();
  tiposIVAprueba.add(0);
  tiposIVAprueba.add(21);
  var origenesPrueba = new ArrayList<String>();
  origenesPrueba.add("caja");
  origenesPrueba.add("otros");  

  ConfigData cfDataPrueba = new ConfigData("testUser", new Año(2024, 2), new HashMap<String, String>(), tiposIVAprueba, origenesPrueba);
  return cfDataPrueba;
  }

private MisDatos getMisDatosStd() {

  MisDatos misDtsPrueba = new MisDatos(0,new NIF(00000000, "X", false),"nombreEmpresaPrueba");
  return misDtsPrueba;  
}

private UIData getUiDataStd() {

  var nombresColprueba = new ArrayList<String>();
  nombresColprueba.add("ID");
  nombresColprueba.add("numFactura");
  var anchosColprueba = new ArrayList<Integer>();
  anchosColprueba.add(10);
  anchosColprueba.add(100);
    
  UIData uiDataPrueba = new UIData(nombresColprueba,anchosColprueba);
  return uiDataPrueba;
  }
//TODO: 18-04-2024- Generar el Java Record "CFG_DATA" para guardar los datos de la Config
//TODO: 18-04-2024- Hacer un Diagrama de Flujo de todo el proceso de Config, para ajustar
//TODO: 11-04-2024 - Pensar cómo meter usuario
//#endregion
/* 
  private Config(){
    new Config( new MisDatos(), Config.getNombresColumnasStandard(), Config.getAnchoColumnasStandard(), new Año(2000,0), new HashMap<String, String>(), new ArrayList<TipoGasto>(), new ArrayList<TipoIVA>(), new ArrayList<Concepto>());
// HECHO: 09/04/24 - Cambiar archivo Config al formato json - usar json.jar / gson.jar
// TODO: 09/04/24 - Programar método CreacionNuevoUsuario (desde el Acceso)
// TODO: misDatos se leeran tambien de un archivo misdatos.cfg 
    System.out.println("Entrando en el constructor genérico privado de la clase Config, usuario " + this.usuario);
    Fichero<Config> ficheroCFG = new Fichero<Config>("config/"+this.usuario.toUpperCase()+"/config.cfg");
    System.out.println("Leído el fichero config/" + this.usuario.toUpperCase()+"/config.cfg");
    Config.configuraciones = ficheroCFG.leer();
    if (Config.configuraciones.isEmpty())
    {
      JOptionPane.showMessageDialog(null,"El archivo de configuracion esta vacio...\n pasando a escribir la configuracion por defecto");
      System.out.println("El archivo de configuracion esta vacio...\n pasando a escribir la configuracion por defecto");
      
      this.rutas.put("FCT","datos/"+Config.usuario.toUpperCase()+"/FCT"+this.año.año()+this.año.trimestre()+".fct");
      this.rutas.put("RS","datos/"+Config.usuario.toUpperCase()+"/RS.rs");
      this.rutas.put("CJA","datos/"+Config.usuario.toUpperCase()+"/CJA"+this.año+this.año.trimestre()+".cja");
      this.rutas.put("CFG","config/"+Config.usuario.toUpperCase()+"/config.cfg");
      this.rutas.put("TMP01","temp/"+Config.usuario.toUpperCase()+"/tmp01.tmp");
      this.rutas.put("TMP02","temp/"+Config.usuario.toUpperCase()+"/tmp02.tmp");
      this.rutas.put("TMP03","temp/"+Config.usuario.toUpperCase()+"/tmp03.tmp");
      
      this.tiposGasto.add(new TipoGasto("SUMINISTROS","electricidad,agua,etc..."));
      this.tiposGasto.add(new TipoGasto("ALQUILERES","alquiler de oficinas, bajos, etc..."));
      this.tiposGasto.add(new TipoGasto("COMPRAS","gastos de aprovisionamiento"));
      this.tiposGasto.add(new TipoGasto("GASTOS","gastos genéricos"));
      this.tiposGasto.add(new TipoGasto("VARIOS","otros gastos de diversos tipos"));
        
      this.tiposIVA.add(new TipoIVA(0,null));
      this.tiposIVA.add(new TipoIVA(4,null));
      this.tiposIVA.add(new TipoIVA(8,null));
      this.tiposIVA.add(new TipoIVA(10,null));
      this.tiposIVA.add(new TipoIVA(18,null));
      this.tiposIVA.add(new TipoIVA(21,null));
      
      this.origenesCaja.add(new Concepto("CAJA"));
      this.origenesCaja.add(new Concepto("MAQUINA TABACO"));
      this.origenesCaja.add(new Concepto("INGRESOS BANCARIOS"));
    }
      // TODO : hay que pasarle todos los datos de config  
      if (recConfig(Config.usuario,this))
        Config.configActual = Config.configuraciones.get(Config.configuraciones.size()-1);
      else
        JOptionPane.showMessageDialog(null,"Ha sucedido un error al grabar la configuración de " + this.usuario);
  }
*/
//TODO: 11-04-2024 - Parece que, ahora mismo, no tiene sentido llamar a un constructor sin definir el usuario...
/*
  public static Config getConfig(){
    if (Config.instancia == null)
      new Config();
    return Config.instancia;
  }
*/  
//#region GET_CONFIG(user)
  public static Config getConfig(String user){
    if (Config.configActual == null)
      new Config(user);
    else if(!Config.configActual.usuario.equals(user))
      new Config(user);
    return Config.configActual;
  }
//#endregion
/*    
    File fct = new File(Config.getConfig().getRutaFCT());
    File rs = new File(Config.getConfig().getRutaRS());
    File cja = new File(Config.getConfig().getRutaCJA());

  if (!fct.exists() || !rs.exists() || !cja.exists()){
    HashMap rutas = Config.getConfig().getRutas();

    if (!fct.exists()){
      //TODO: seguramente haya que repetir el if por si no existe alguno de los ficheros 
        if (this.rutas!=null){
          this.rutas.remove("FCT");
        }else{
          this.rutas =  new HashMap();
        }
        this.rutas.put("FCT","datos/"+Config.getConfig().getUsuario()+"/FCT"+Config.getConfig().getAño().getAño()+Config.getConfig().getAño().getTrimestre()+".fct");
    }
    if (!rs.exists()){
        this.rutas.remove("RS");
        this.rutas.put("RS","datos/"+Config.getConfig().getUsuario()+"/RS.rs");
    }
    if (!cja.exists()){
        this.rutas.remove("CJA");
        this.rutas.put("CJA","datos/"+Config.getConfig().getUsuario()+"/CJA"+Config.getConfig().getAño().getAño()+Config.getConfig().getAño().getTrimestre()+".cja");
    }
    Config.getConfig().setRutas(rutas);
    recConfig(Config.getConfig().getUsuario(),Config.getConfig());
  }   
*/ 

  public static String[] getNombresColumnasStandard(){

    String[] nCols={"ID","#","fecha","numFactura","NIF","R.S.","base","tipo","IVA","SubTotal","base N.I.","t ret","Retenc","Total","Concepto"};	
    return nCols;
  }

  public static int[] getAnchoColumnasStandard(){

    int[] aCols={60,20,0,0,100,180,0,40,0,0,0,40,0,0,0};
    return aCols; 
  }

  public String getUsuario(){
    return Config.configActual.usuario;
  }

  public String getRutaRS(){

    String rs = Config.configActual.configData.rutas().get("RS");
    return rs;
  }

  public String getRutaFCT(){

      String fct = Config.configActual.configData.rutas().get("FCT");
      System.out.println("Leyendo de ruta "+this.configData.rutas().get("FCT"));
      return fct;
  }
  
  public String getRutaCJA(){
      String cja = Config.configActual.configData.rutas().get("CJA");
      return cja;
  }

/*
// TODO: 10-04-2024 - Ver cómo buscar la lista de contrasenas de un usuario...  
// TODO: sustituir la lista siguiente por una lectura del archivo config.cfg 
//TODO: 19-04-2024 - Sopesar si debería generar un archivo 'std_config.json' con la configuración inicial (la de admin:admin)
  public boolean setConfig(String user,Config p_config){
    this.usuario = user;
    p_this.usuario(user);
    setAño(p_config.año());
    this.misdatos = (p_config.misdatos);
//        config.getRutas().remove("FCT");
//        config.getRutas().remove("RS");
    p_config.getRutas().remove("CFG");
//        config.getRutas().put("FCT","datos/"+user+"/FCT"+config.año.getAño()+config.año.getTrimestre()+".fct");
//	config.getRutas().put("RS","datos/"+user+"/RS.rs");
    p_config.getRutas().put("CFG","config/"+this.usuario.toUpperCase()+"/config.cfg");
//        config.getRutas().put("CJA","datos/"+user+"/CJA"+config.año.getAño()+config.año.getTrimestre()+".cja");
    setRutas(p_config.getRutas());
    setTiposGasto(p_config.getTiposGasto());
    setTiposIVA(p_config.getTiposIVA());
    this.misdatos.setContrasenhas(p_config.misdatos.getContrasenhas());
    instancia = this;
    return true;
    
  }
  
  public static boolean reinicializarConfig(String user){
    if (new Config(user)!= null)
        return true;
    return false; 
        
  }
  
  public void setConfig(String p_usuario,Año p_año, miRS p_miRS, HashMap p_rutas, ArrayList <TipoGasto> p_tiposGasto, ArrayList<TipoIVA> p_tiposIVA, ArrayList<Contrasenha> p_contrasenhas){
    this.usuario = p_usuario.toUpperCase();
	  this.setAño(p_año);
	  this.misdatos.setMiRS(p_miRS);
//    rutas.remove("FCT");
//        rutas.remove("RS");
    p_rutas.remove("CFG");
//    rutas.put("FCT","datos/"+usuario+"/FCT"+año.getAño()+año.getTrimestre()+".fct");
//	rutas.put("RS","datos/"+usuario+"/RS.rs");
    p_rutas.put("CFG","config/"+this.usuario+"/config.cfg");
//    rutas.put("CJA","datos/"+usuario+"/CJA"+año.getAño()+año.getTrimestre()+".cja");
    this.setRutas(p_rutas);
    this.setTiposGasto(p_tiposGasto);
    this.setTiposIVA(p_tiposIVA);
    this.misdatos.setContrasenhas(p_contrasenhas);
    instancia = this;
  }
  
  public boolean recConfig(String p_usuario,Año p_año, miRS p_mirazonsocial,HashMap p_rutas,ArrayList<TipoGasto> p_categorias, ArrayList<TipoIVA> p_ivas, ArrayList<Contrasenha> p_contrasenhas){
    Fichero<Config> archivoCFG = new Fichero<Config>("config/"+p_usuario.toUpperCase()+"/config.cfg");
    
    setConfig(p_usuario,p_año, p_mirazonsocial,p_rutas,p_categorias,p_ivas,p_contrasenhas);
    // TODO : si todo va bien se podrán escribir varias configuraciones. 
    // TODO : Por ahora se borra la lista para dejar una unica config grabada 
    Config.configuraciones.clear();
    Config.configuraciones.add(this);
    if (archivoCFG.escribir(Config.configuraciones)){
        return true;
    }
    else return false;
  }
 */
// TODO: 20-04-2024 - Cambiar el nombre del archivo base 'config.json' a 'creds.json' y los datos de 'misdatos.json' para 'ADMIN'
//TODO: 20-04-2024 - Falta crear misdatos.json (se puede crear un archivo tipo) 
//TODO: 19-04-2024 - Completar RecConfig  
//#region *REC_CONFIG()
// TODO: 23-04-2024 - Aquí debería guardar los 4 archivos JSON: config, configdata, misdatos, uidata...
  public static boolean recConfig(String p_usuario,Config p_config){
    String ruta1 = "config/"+p_usuario.toUpperCase()+"/config.json";
    String ruta2 = "config/"+p_usuario.toUpperCase()+"/configdata.json";
    String ruta3 = "config/"+p_usuario.toUpperCase()+"/misdatos.json";
    String ruta4 = "config/"+p_usuario.toUpperCase()+"/uidata.json";
    // TODO: 20-04-2024 - Rehacer 'datosFormateados', enviar la config a formatearse como JSON, o incluir una std_config 
    // TODO: 23-04-2024 - Rehacer el método toString() para adaptarlo a la salida en JSON
    String datos1 = p_config.toString();
    String datos2 = p_config.configData.toString();
    String datos3 = p_config.misDatos.toString();
    String datos4 = p_config.uiData.toString();

    if (Fichero.guardarJSON(datos1, ruta1)&&Fichero.guardarJSON(datos2, ruta2)&&Fichero.guardarJSON(datos3, ruta3)&&Fichero.guardarJSON(datos4, ruta4)){
        JOptionPane.showMessageDialog(null,"El nuevo año se ha establecido en  " + Config.getConfig(p_usuario).configData.año().año()+" - Trimestre: " + Config.getConfig(p_usuario).configData.año().trimestre());
        return true;
      }
    else return false;
  }
//#endregion
//TODO: 19-04-2024 - Completar formatearComoJSON
//#region *FORMTR_CM_JSON()
  public String formatearComoJSON() {
    String resp="";
    // TODO: 12-04-2024 - Falta dar formato JSON a esta config (A lo mejor llega con redefinir y sobrecargar el método toString())
    return resp;
  }
//#endregion
//#region LEER_CFG_JSON()
  public Config leerFicheroCFGjson(String ruta){
  // TODO: 11-04-2024 - Crear métodos estáticos para guardar/leer las credenciales en el archivo base config.json
    String fichero = Fichero.leerJSON(ruta);
    Gson gson = new Gson();

    Config configuracGuardada = gson.fromJson(fichero, Config.class);
    return configuracGuardada;
  }
//#endregion
//#region LEER_CREDS()
  public static Credenciales leerCredenciales(String ruta){
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
	
    String ruta="./config/creds.json";

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
@Override//#region CONSTRUCTOR_NC_DE_CONFIG
  public String toString(){
    String respuesta;

    String rutaCFGDT = "config/"+this.usuario.toUpperCase()+"/configdata.json";
    String rutaMSDTS = "config/"+this.usuario.toUpperCase()+"/misdatos.json";
    String rutaUIDT = "config/"+this.usuario.toUpperCase()+"/uidata.json";

    respuesta = "{"+"\n\t\"usuario\" : \""+Config.configActual.usuario+"\",\n\t\"configdata\" : \""+rutaCFGDT+"\",\n\t\"misdatos\" : \""+rutaMSDTS+"\",\n\t\"uidata\" : \""+rutaUIDT+"\"\n}";
    // TODO: 23-04-2024 - Completar String 'respuesta' en toString
    return respuesta;
  }
}

package modelo.records;

import java.io.File;
import java.io.Serializable;

import java.util.*;
import javax.swing.JOptionPane;

import modelo.Anho;
import modelo.Concepto;
import modelo.Fichero;
import modelo.MisDatos;
import modelo.TipoGasto;
import modelo.TipoIVA;
import modelo.miRS;

public class Config implements Serializable{

  public MisDatos misdatos;
  private static Config instancia = null;
  
  public HashMap nombresColumnas;
  public HashMap anchoColumnas;
  public static ArrayList<Config> configuraciones = new ArrayList<Config>();
  
  public Anho anho;
  public HashMap rutas;
  public ArrayList<TipoGasto> tiposGasto;
  public ArrayList<TipoIVA> tiposIVA;
  public ArrayList<Concepto> origenesCaja;
  private static String usuario = "ADMIN";
  
  public Config(){
      /** TODO: misDatos se leeran tambien de un archivo misdatos.cfg **/
        System.out.println("Entrando en el constructor genérico privado de la clase Config, usuario " + Config.usuario);
        Fichero<Config> ficheroCFG = new Fichero<Config>("config/"+Config.usuario.toUpperCase()+"/config.cfg");
        System.out.println("Leído el fichero config/" + Config.usuario.toUpperCase()+"/config.cfg");
        Config.configuraciones = ficheroCFG.leer();
        if (Config.configuraciones.isEmpty())
        {
           JOptionPane.showMessageDialog(null,"El archivo de configuracion esta vacio...\n pasando a escribir la configuracion por defecto");
           System.out.println("El archivo de configuracion esta vacio...\n pasando a escribir la configuracion por defecto");
           this.anho = new Anho();
           this.rutas = new HashMap();
                    this.rutas.put("FCT","datos/"+Config.usuario.toUpperCase()+"/FCT"+this.anho.getAnho()+this.anho.getTrimestre()+".fct");
                    this.rutas.put("RS","datos/"+Config.usuario.toUpperCase()+"/RS.rs");
                    this.rutas.put("CJA","datos/"+Config.usuario.toUpperCase()+"/CJA"+this.anho.getAnho()+this.anho.getTrimestre()+".cja");
                    this.rutas.put("CFG","config/"+Config.usuario.toUpperCase()+"/config.cfg");
                    this.rutas.put("TMP01","temp/"+Config.usuario.toUpperCase()+"/tmp01.tmp");
                    this.rutas.put("TMP02","temp/"+Config.usuario.toUpperCase()+"/tmp02.tmp");
                    this.rutas.put("TMP03","temp/"+Config.usuario.toUpperCase()+"/tmp03.tmp");
            this.tiposGasto = new ArrayList<TipoGasto>();
                    this.tiposGasto.add(new TipoGasto("SUMINISTROS","electricidad,agua,etc..."));
                    this.tiposGasto.add(new TipoGasto("ALQUILERES","alquiler de oficinas, bajos, etc..."));
                    this.tiposGasto.add(new TipoGasto("COMPRAS","gastos de aprovisionamiento"));
                    this.tiposGasto.add(new TipoGasto("GASTOS","gastos genéricos"));
                    this.tiposGasto.add(new TipoGasto("VARIOS","otros gastos de diversos tipos"));
            this.tiposIVA = new ArrayList<TipoIVA>();
                    this.tiposIVA.add(new TipoIVA(0));
                    this.tiposIVA.add(new TipoIVA(4));
                    this.tiposIVA.add(new TipoIVA(8));
                    this.tiposIVA.add(new TipoIVA(10));
                    this.tiposIVA.add(new TipoIVA(18));
                    this.tiposIVA.add(new TipoIVA(21));

          this.misdatos = new MisDatos();
          
          this.origenesCaja = new ArrayList<Concepto>();
                    this.origenesCaja.add(new Concepto("CAJA"));
                    this.origenesCaja.add(new Concepto("MAQUINA TABACO"));
                    this.origenesCaja.add(new Concepto("INGRESOS BANCARIOS"));
            /** TODO : hay que pasarle todos los datos de config */
            recConfig(Config.usuario,this);
        }

        instancia = Config.configuraciones.get(Config.configuraciones.size()-1);
        
        File fct = new File(Config.getConfig().getRutaFCT());
        File rs = new File(Config.getConfig().getRutaRS());
        File cja = new File(Config.getConfig().getRutaCJA());
        
        if (!fct.exists() || !rs.exists() || !cja.exists()){
            HashMap rutas = Config.getConfig().getRutas();

            if (!fct.exists()){
              /*TODO: seguramente haya que repetir el if por si no existe alguno de los ficheros */
                if (this.rutas!=null){
                  this.rutas.remove("FCT");
                }else{
                  this.rutas =  new HashMap();
                }
                this.rutas.put("FCT","datos/"+Config.getConfig().getUsuario()+"/FCT"+Config.getConfig().getAnho().getAnho()+Config.getConfig().getAnho().getTrimestre()+".fct");
            }
            if (!rs.exists()){
                this.rutas.remove("RS");
                this.rutas.put("RS","datos/"+Config.getConfig().getUsuario()+"/RS.rs");
            }
            if (!cja.exists()){
                this.rutas.remove("CJA");
                this.rutas.put("CJA","datos/"+Config.getConfig().getUsuario()+"/CJA"+Config.getConfig().getAnho().getAnho()+Config.getConfig().getAnho().getTrimestre()+".cja");
            }
            Config.getConfig().setRutas(rutas);
            recConfig(Config.getConfig().getUsuario(),Config.getConfig());
        }   
  }
  
  private Config(String user){
      /** TODO: misDatos se leeran tambien de un archivo misdatos.cfg **/
      System.out.println("Entrando en el constructor privado de la Configuracion del Usuario "+user);
      Config.usuario = user; 
      new Config();
       
  }
  
  public static Config getConfig(){
        if (instancia == null)
          new Config();
        return instancia;
  }
  
  public static Config getConfig(String user){
          new Config(user);
      return instancia;
  }

  public MisDatos getMisdatos() {
        return this.misdatos;
    }

  public String getUsuario(){
      return Config.usuario;
  }
  
  public Anho getAnho(){
    return this.anho;
  }
  
  public miRS getMiRS(){
      return this.misdatos.getMiRS();
  }
  
  public HashMap getRutas(){
    return this.rutas;
  }
  
  public ArrayList<TipoGasto> getTiposGasto(){
    return this.tiposGasto;
  }
  
  public ArrayList<TipoIVA> getTiposIVA(){
    return this.tiposIVA;
  }
  
  public String getRutaRS(){
      String rs = this.rutas.get("RS").toString();
      return rs;
  }

  public String getRutaFCT(){
      String fct = this.rutas.get("FCT").toString();
//      //System.out.println("Leyendo de ruta "+rutas.get("FCT").toString());
      return fct;
  }
  public String getRutaCJA(){
      String cja = this.rutas.get("CJA").toString();
      return cja;
  }
  public ArrayList<Contrasenha> getContrasenhas(){
      
      return this.misdatos.getContrasenhas();
  }

    public ArrayList<Concepto> getOrigenesCaja() {
        return this.origenesCaja;
    }
  
  public HashMap getNombresColumnas(){
     nombresColumnas = new HashMap();
     
     /** TODO: sustituir la lista siguiente por una lectura del archivo config.cfg **/
     
     nombresColumnas.put(1,"ID");
     nombresColumnas.put(2,"#");
     nombresColumnas.put(3,"fecha");
     nombresColumnas.put(4,"numFactura");
     nombresColumnas.put(5,"NIF");
     nombresColumnas.put(6,"R.S.");
     nombresColumnas.put(7,"base");
     nombresColumnas.put(8,"tipo");
     nombresColumnas.put(9,"IVA");
     nombresColumnas.put(10,"SubTotal");
     nombresColumnas.put(11,"base N.I.");
     nombresColumnas.put(12,"t ret");
     nombresColumnas.put(13,"Retenc");
     nombresColumnas.put(14,"Total");
     nombresColumnas.put(15,"Concepto");

     return nombresColumnas;
	
  }
  
  public HashMap getAnchoColumnas(){
     anchoColumnas = new HashMap();
     
     /** TODO: sustituir la lista siguiente por una lectura del archivo config.cfg **/
     anchoColumnas.put(1,60);
     anchoColumnas.put(2,20);
     anchoColumnas.put(3,null);
     anchoColumnas.put(4,null);
     anchoColumnas.put(5,100);
     anchoColumnas.put(6,180);
     anchoColumnas.put(7,null);
     anchoColumnas.put(8,40);
     anchoColumnas.put(9,null);
     anchoColumnas.put(10,null);
     anchoColumnas.put(11,null);
     anchoColumnas.put(12,40);
     anchoColumnas.put(13,null);
     anchoColumnas.put(14,null);
     anchoColumnas.put(15,null);

     return anchoColumnas;
	
  }
  
  public void setAnho(Anho anho){
    this.anho = anho;
  }
  
  public void setRutas(HashMap rutas){
    this.rutas = rutas;
  }
  
  public void setUsuario(String user){
    Config.usuario = user.toUpperCase();
  }
  
  public void setContrasenhas(ArrayList<Contrasenha> contras){   
      this.misdatos.setContrasenhas(contras);
  }
  
  public void setTiposGasto(ArrayList<TipoGasto> tiposGasto){
    this.tiposGasto = tiposGasto;
  }
  
  public void setTiposIVA(ArrayList<TipoIVA> tiposIVA){
    this.tiposIVA = tiposIVA;
  }

  public void setOrigenesCaja(ArrayList<Concepto> origenesCaja) {
      this.origenesCaja = origenesCaja;
  }
  
  public boolean setConfig(String user,Config p_config){
        Config.usuario = user;
        p_config.setUsuario(user);
        setAnho(p_config.getAnho());
	      this.misdatos = (p_config.misdatos);
//        config.getRutas().remove("FCT");
//        config.getRutas().remove("RS");
        p_config.getRutas().remove("CFG");
//        config.getRutas().put("FCT","datos/"+user+"/FCT"+config.anho.getAnho()+config.anho.getTrimestre()+".fct");
//	config.getRutas().put("RS","datos/"+user+"/RS.rs");
        p_config.getRutas().put("CFG","config/"+Config.usuario.toUpperCase()+"/config.cfg");
//        config.getRutas().put("CJA","datos/"+user+"/CJA"+config.anho.getAnho()+config.anho.getTrimestre()+".cja");
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
  
  public void setConfig(String p_usuario,Anho p_anho, miRS p_miRS, HashMap p_rutas, ArrayList <TipoGasto> p_tiposGasto, ArrayList<TipoIVA> p_tiposIVA, ArrayList<Contrasenha> p_contrasenhas){
    Config.usuario = p_usuario.toUpperCase();
	  this.setAnho(p_anho);
	  this.misdatos.setMiRS(p_miRS);
//        rutas.remove("FCT");
//        rutas.remove("RS");
        p_rutas.remove("CFG");
//        rutas.put("FCT","datos/"+usuario+"/FCT"+anho.getAnho()+anho.getTrimestre()+".fct");
//	rutas.put("RS","datos/"+usuario+"/RS.rs");
        p_rutas.put("CFG","config/"+Config.usuario+"/config.cfg");
//        rutas.put("CJA","datos/"+usuario+"/CJA"+anho.getAnho()+anho.getTrimestre()+".cja");
  this.setRutas(p_rutas);
	this.setTiposGasto(p_tiposGasto);
	this.setTiposIVA(p_tiposIVA);
	this.misdatos.setContrasenhas(p_contrasenhas);
        instancia = this;
  }
  
  public boolean recConfig(String p_usuario,Anho p_año, miRS p_mirazonsocial,HashMap p_rutas,ArrayList<TipoGasto> p_categorias, ArrayList<TipoIVA> p_ivas, ArrayList<Contrasenha> p_contrasenhas){
      Fichero<Config> archivoCFG = new Fichero<Config>("config/"+p_usuario.toUpperCase()+"/config.cfg");
      
      setConfig(p_usuario,p_año, p_mirazonsocial,p_rutas,p_categorias,p_ivas,p_contrasenhas);
      /** TODO : si todo va bien se podrán escribir varias configuraciones. 
       *    Por ahora se borrar la lista para dejar una unica config grabada */
      Config.configuraciones.clear();
      Config.configuraciones.add(this);
      if (archivoCFG.escribir(Config.configuraciones)){
          return true;
      }
      else return false;
  }
  
public static boolean recConfig(String p_usuario,Config p_config){
      Fichero<Config> archivoCFG = new Fichero<Config>("config/"+p_usuario.toUpperCase()+"/config.cfg");
      Config.configuraciones.clear();
      Config.configuraciones.add(p_config);
      if (archivoCFG.escribir(Config.configuraciones)){
          //JOptionPane.showMessageDialog(null,"El nuevo año se ha establecido en  "+Config.getConfig().getAnho().getAnho());
          return true;
      }
      else return false;
  }

public String toString(){
    return ("CONFIG : USER "+Config.usuario +" - AÑO : " + this.anho.getAnho()+" - RUTA PPAL : " +this.rutas.get("FCT") + " - TAMAÑO DEL ARAY DE CATEGORIAS : " + this.tiposGasto.size()+ " - TAMAÑO DEL ARAY DE CONFIGS : " + Config.configuraciones.size()+" NUM DE CONTRASEÑAS : "+this.misdatos.getContrasenhas().size());
}
}

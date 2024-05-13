package tests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import modelo.base.*;
import modelo.records.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ConfigTest {

	@Test
	void borrarDatosUsuario(){
		String user="admin";

		Credenciales cred_prev = Config.leerCredenciales("./config/creds.json");
		var listaCredsNueva = new ArrayList<Contrasena>();
		for (Contrasena c : cred_prev.getlistacreds()){
			if (!c.getUsuario() .equals(user))
				listaCredsNueva.add(c); 
		}

		var n_creds = new Credenciales();
		n_creds.setListaCredenciales(listaCredsNueva);

		Config.guardarCredenciales(n_creds);

		borrarSubdirs(user);

	}

	void borrarSubdirs(String user){
		File dir1 = new File("./config/"+user.toUpperCase());
		File dir2 = new File("./datos/"+user.toUpperCase());

		recursiveDelete(dir1);
		recursiveDelete(dir2);

		assertTrue(!dir1.exists());
		assertTrue(!dir2.exists());
	}

	static void recursiveDelete(File targetDirectory) {
		
		File[] data = targetDirectory.listFiles();
	
		for (File file : data) {
		  if (file.isDirectory())
			recursiveDelete(file);
	
		  else
			file.delete();
		}
	
		targetDirectory.delete();
	  }

	@Test
	void CreaConfigOK() throws NullPointerException, IOException{
		Config configPrueba = Config.getConfig("TESTuSER");
		assertNotNull(configPrueba);
	}
  
	@Test
	void leerCredencialesOK(){

		String ruta = "./config/creds.json";
		String ficheroResp = Fichero.leerJSON(ruta);

    	System.out.println("fichero:\n"+ficheroResp+"\n-------------------");

		//assertNotNull(ficheroResp);
    	Gson gson = new Gson();
        Type type = new TypeToken<Credenciales>() {}.getType();
        Credenciales credenciales = gson.fromJson(ficheroResp, type);
		
        int i=0;
        for (Contrasena contrasena : credenciales.creds) {
			System.out.println("Contra num "+i);
            System.out.println("Usuario: " + contrasena.usuario);
            System.out.println("Contraseña: " + contrasena.contra);
			System.out.println("-----------------------------");
			i++;
        }
    // TODO: 11-04-2024 - Revisar esto: Si es Arraylist.class o Contrasena.class
	// TODO: 21-04-2024 - Parece que hay un problema al leer las credenciales... El fichero lo lee bien, pero el Objeto 'Credenciales' lo coge mal...
    
    	System.out.println("\n---------------\ncredenciales:\n"+credenciales.toString());
		assertNotEquals(credenciales.creds.get(0).usuario, credenciales.creds.get(1).usuario);
  
	}
	@Test
	void RutasConfigJsonExiste(){

		String ruta="config/TESTUSER/rutasconfig.json";
		File fichero = new File(ruta);

		assertTrue(fichero.exists());
	}

	@Test
	void leerConfigDataJson(){
		String ruta="./config/TESTUSER/configdata.json";
		String datos = Fichero.leerJSON(ruta);
		Gson gson = new Gson();
		ConfigData configData = gson.fromJson(datos, ConfigData.class);
		assertEquals("TESTuSER", configData.getUser());
	}


	@Test
	void leerRutasConfigJson(){

		String ruta = "config/TESTUSER/rutasconfig.json";
		String datos = Fichero.leerJSON(ruta);
		Gson gson = new Gson();
		RutasConfig rutas = gson.fromJson(datos, RutasConfig.class);
		
		assertEquals("config/TESTUSER/misdatos.json", rutas.getRutaMisDatos());
	}

	@Test
	void leerUIDataJson(){

		String ruta = "config/TESTUSER/uidata.json";
		String datos = Fichero.leerJSON(ruta);
		Gson gson = new Gson();
		UIData uidata = gson.fromJson(datos, UIData.class);

		assertEquals( 15, uidata.getNombreColsFCT().length);
	}

	@Test
	void leerMisDatosJson(){

		String ruta = "config/TESTUSER/misdatos.json";
		String datos = Fichero.leerJSON(ruta);
		Gson gson = new Gson();
		MisDatos misdatos = gson.fromJson(datos, MisDatos.class);
		assertEquals( "TESTuSER", misdatos.getUser());
	}

	@Test
	void ConfigToStringOK() throws NullPointerException, IOException{
		while(Config.getConfig("TESTuSER")==null){
			System.out.print("");
		}
		Config cfgPrueba = Config.getConfig("TESTuSER");
		System.out.println(Config.getConfigActual().toString());
		assertEquals("TESTuSER", cfgPrueba.usuario);

	}

	@Test
	void ConfigFilesOK() throws NullPointerException, IOException{
	// TODO: 28-04-2024 - El problema es pasar a 'final String' un dato que viene del JSON en forma de 'String' (sin 'final')
		Config cfgPrueba = Config.getConfig("TESTuSER");

		String cfgjson = cfgPrueba.rutasconfig.toJSON();
		System.out.println(cfgjson);

		String cfgdtjson = cfgPrueba.configData.toJSON();
		System.out.println(cfgdtjson);

		String msdtsjson = cfgPrueba.getMisDatos().toJSON();
		System.out.println(msdtsjson);

		String uidtjson = cfgPrueba.uiData.toJSON();
		System.out.println(uidtjson);

		assertEquals("TESTuSER", cfgPrueba.usuario);
		
		String rutacfg1 = "config/"+cfgPrueba.usuario.toUpperCase()+"/rutasconfig.json";
		File fcfg1 = new File(rutacfg1);
		assertTrue(fcfg1.exists());

		String rutacfg2 = "config/"+cfgPrueba.usuario.toUpperCase()+"/configdata.json";
		File fcfg2 = new File(rutacfg2);
		assertTrue(fcfg2.exists());

		String rutacfg3 = "config/"+cfgPrueba.usuario.toUpperCase()+"/misdatos.json";
		File fcfg3 = new File(rutacfg3);
		assertTrue(fcfg3.exists());

		String rutacfg4 = "config/"+cfgPrueba.usuario.toUpperCase()+"/uidata.json";
		File fcfg4 = new File(rutacfg4);
		assertTrue(fcfg4.exists());
// TODO: 02-05-2024 - Hay que guardar las credenciales y los ficheros de trsbajo
	}
// TODO: 24-04-2024 - crear método toJSON() en cada record anterior, para luego grabar los ficheros
@Test
void WorkingFilesOK() throws NullPointerException, IOException{

	while(Config.getConfig("TESTuSER")==null){
		System.out.print("");
	}
	Config cfgPrueba = Config.getConfig("TESTuSER");

	File f1 = new File(cfgPrueba.configData.getRutas().getFCT());
	System.out.println("[ConfigTest] Chequeando el archivo "+ f1.getPath());
	assertTrue(f1.exists());

	File f2 = new File(cfgPrueba.configData.getRutas().getRS());
	System.out.println("[ConfigTest] Chequeando el archivo "+ f2.getPath());
	assertTrue(f2.exists());

	File f3 = new File(cfgPrueba.configData.getRutas().getCJA());
	System.out.println("[ConfigTest] Chequeando el archivo "+ f3.getPath());
	assertTrue(f3.exists());
	}
}
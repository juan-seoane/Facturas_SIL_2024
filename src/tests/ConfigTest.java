package tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import modelo.base.Config;
import modelo.base.Credenciales;
import modelo.base.Fichero;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class ConfigTest {

	@Test
	void leerCredencialesOK(){

		String ruta = "./config/creds.json";
		String ficheroResp = Fichero.leerJSON(ruta);

    	System.out.println("fichero:\n"+ficheroResp+"\n-------------------");

		//assertNotNull(ficheroResp);
    	Gson gson = new Gson();
        Type type = new TypeToken<Credenciales>() {}.getType();
        Credenciales credenciales = gson.fromJson(ficheroResp, type);
		
        //int i=0;
        //for (Contrasena contrasena : credenciales.creds) {
		//	System.out.println("Contra num "+i);
        //    System.out.println("Usuario: " + contrasena.usuario);
        //    System.out.println("Contraseña: " + contrasena.contra);
		//	System.out.println("-----------------------------");
		//	i++;
        //}
    // TODO: 11-04-2024 - Revisar esto: Si es Arraylist.class o Contrasena.class
	// TODO: 21-04-2024 - Parece que hay un problema al leer las credenciales... El fichero lo lee bien, pero el Objeto 'Credenciales' lo coge mal...
    
    	System.out.println("\n---------------\ncredenciales:\n"+credenciales.toString());
		assertNotEquals(credenciales.creds.get(0).usuario, credenciales.creds.get(1).usuario);
  
	}

	@Test
	void ConfigToStringOK(){
		while(Config.getConfig("TESTuSER")==null){
			System.out.print("");
		}
		Config cfgPrueba = Config.getConfig("TESTuSER");
		System.out.println(cfgPrueba.toString());
		assertEquals("TESTuSER", cfgPrueba.usuario);

	}

	@Test
	void ConfigFilesOK(){
		while(Config.getConfig("TESTuSER")==null){
			System.out.print("");
		}
		Config cfgPrueba = Config.getConfig("TESTuSER");

		String cfgjson = cfgPrueba.toString();
		System.out.println(cfgjson);

		String cfgdtjson = cfgPrueba.configData.toJSON();
		System.out.println(cfgdtjson);

		String msdtsjson = cfgPrueba.misDatos.toJSON();
		System.out.println(msdtsjson);

		String uidtjson = cfgPrueba.uiData.toJSON();
		System.out.println(uidtjson);

		assertEquals("TESTuSER", cfgPrueba.usuario);
	}
// TODO: 24-04-2024 - crear método toJSON() en cada record anterior, para luego grabar los ficheros
}

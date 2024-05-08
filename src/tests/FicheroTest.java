package tests;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

import modelo.base.Fichero;

public class FicheroTest {

	@Test 
	void fileCredsExists(){

		String ruta="./config/creds.json";
		var f = new File(ruta);
		assertTrue(f.exists());

	}
	
	@Test
	void OKleerJSON(){
    
		String ruta="./config/creds.json";
        String fichero = Fichero.leerJSON(ruta);
 			
		System.out.println(fichero);

		assertNotEquals("", fichero);

    }
	@Test
	void OKleerJSONconRecord(){
    
		String ruta="./config/TESTUSER/misdatos.json";
        String fichero = Fichero.leerJSON(ruta);
 			
		System.out.println(fichero);

		assertNotEquals("", fichero);

    }
}

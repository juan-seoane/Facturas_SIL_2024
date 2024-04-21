package tests;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.Test;

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
        String fichero = "";
 
        BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(ruta));
			String linea;
			while ((linea = br.readLine()) != null) {
				fichero += linea;
			}
		} catch (IOException e) {
		}
			
		System.out.println(fichero);

		assertNotEquals("", fichero);

    }
}

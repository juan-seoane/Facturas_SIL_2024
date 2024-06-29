package tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle.Control;

import org.junit.Test;

import controladores.Controlador;
import modelo.ModeloFacturas;
import modelo.base.Config;
import modelo.base.Fichero;
import modelo.records.Factura;

public class FacturaTest {

	@SuppressWarnings("unchecked")
	@Test
	public void convertirCSVok() throws NullPointerException, IOException{
		String usuario = "admin";
		String rutaYnombre = "./datos/" + usuario.toUpperCase() + "/FCT20242.csv";
		var config = Config.getConfig(usuario);
		var contrlPpal = Controlador.getControlador();
		Controlador.usuario = "admin" ;
		var modeloFCT = ModeloFacturas.getModelo();

		var fichero = new Fichero(rutaYnombre);
		ArrayList<String[]> datosCSV = fichero.leerCSV(rutaYnombre);
		
		System.out.println("*****[FacturaTest] Datos en el CSV leido : " + datosCSV.size() + " lineas");

		assertNotNull(datosCSV);
		
		var listaPruebaFCT = new ArrayList<Factura>();
		for(String[] linea : datosCSV){
			Factura f = Factura.convertirCSVaFCT(linea);
			if(f!=null){
		        listaPruebaFCT.add(f);
			    System.out.println("****[FacturaTest] Factura convertida: ");
			    System.out.println(f.toString());
			}
	    }
	

	    assertNotNull(listaPruebaFCT);

	}

}

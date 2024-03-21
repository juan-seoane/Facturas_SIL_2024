package controladores;

import modelo.*;

public class ControladorFicheros{

	private modelo.Fichero<modelo.Factura> fct;
	private Controlador controlador;
	
	public ControladorFicheros(Controlador controlador){
		
		this.controlador = controlador;
		//System.out.println("Ruta del fichero FCT : "+(String)(Config.getConfig().getRutas().get("FCT")));
		this.fct = new modelo.Fichero<modelo.Factura>((String)(Config.getConfig().getRutas().get("FCT")));
	}
	
	public modelo.Fichero<modelo.Factura> getFCT(){
		return this.fct;
	
	}

}
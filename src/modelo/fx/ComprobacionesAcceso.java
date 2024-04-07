package modelo.fx;

import controladores.fxcontrollers.Acceso;
import modelo.records.Config;
import modelo.records.Contrasenha;

public class ComprobacionesAcceso {

//TODO: Queda este atributo, baseUser, para hacer comprobaciones...
//	private String baseUser = "ADMIN";
	public static boolean userOK = false;
	public static boolean passOK = false;

	public boolean comprobarCredenciales(String user, String pass){
		//TODO: Otra vez tuve que hacer público el constructor de la clase Config...por lo que...¿Singleton...?
		Config cfg = new Config();
		for (Contrasenha contr : Config.getConfig(user.toUpperCase()).getContrasenhas()){
			//TODO: Revisar el modo de comprobación de credenciales    
				System.out.println("[ComprobacionesAcceso.java>comprobarCredenciales()]Datos introducidos : usuario: "+ user + "  - pass: "+ pass + " > Datos obtenidos de Config: " + contr.getUsuario() +" - " + contr.getContrasenha());
	
				Acceso.imprimir(Acceso.getCanvas(), "\nDatos introducidos : " + user + " - " + pass );
				if (user.toUpperCase().equals(Config.getConfig().getUsuario().toUpperCase())){
					ComprobacionesAcceso.userOK = true;
					if(pass.equals(contr.getContrasenha())){
						ComprobacionesAcceso.passOK = true;
						return true;
					}
					return false;
			}
			return false;	
		}
		return false;
	}
}


package modelo.fx;

import modelo.records.Config;
import modelo.records.Contrasenha;

public class ComprobacionesAcceso {

	private String baseUser = "ADMIN";
	public static boolean userOK = false;
	public static boolean passOK = false;

	public boolean comprobarCredenciales(String user, String pass){
		//TODO: Otra vez tuve que hacer público el constructor de la clase Config...por lo que...¿Singleton...?
		Config cfg = new Config();
		for (Contrasenha contr : Config.getConfig(this.baseUser).getContrasenhas()){
			//TODO: Revisar el modo de comprobación de credenciales    
				System.out.println("[ComprobacionesAcceso.java>comprobarCredenciales()]Datos introducidos : usuario: "+ user + " > " +ComprobacionesAcceso.userOK + " - pass: "+ pass + " > Datos obtenidos de Config(baseUser): " + contr.getContrasenha());
	
				//Acceso.imprimir( this.txtArea, "\nDatos introducidos : "+ userF.getText()+ " - "+passF.getText());
				if (user.toUpperCase().equals(Config.getConfig().getUsuario())){
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


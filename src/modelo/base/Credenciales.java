package modelo.base;

import java.util.ArrayList;
//TODO: 19-04-2024 - Plantearse si puede ser un Java Record (habría que prescindir del Setter)
public class Credenciales{

	public ArrayList<Contrasena> creds;

	public Credenciales(){
		this.creds= new ArrayList<Contrasena>();
	}

	public Credenciales(ArrayList<Contrasena> listacreds){
			this.creds = listacreds;
	}

	public ArrayList<Contrasena> getlistacreds(){
		return this.creds;
	}

	public static boolean setUsuarioActual(String user){
		if(!((Config.usuario = user).equals("")))
			return true;
		else
			return false;
	}
	//TODO: 19-04-2024 - Comprobar que pase a String en el mismo formato que el JSON de credenciales
	@Override
	public String toString(){
		String listaContr ="";
		int i=0;
		for (Contrasena c : this.creds){
			i++;
			if (i<this.creds.size())
				listaContr += c.toString()+",\n";
			else
				listaContr += c.toString()+"\n";
				
		}
		
		return ("creds: [ {\n"+listaContr+"\n	]\n}");
	}
}
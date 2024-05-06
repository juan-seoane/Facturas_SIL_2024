package modelo.records;

import java.util.ArrayList;

public record ConfigData(String user, Año año, RutasTrabajo rutas, ArrayList<Integer> tiposIVA, ArrayList<String> origenesCaja ){

	public String toJSON() {
		// TODO: 28-04-2024 - Falta el objeto 'TiposGasto' (clase 'Concepto') antes de 'tiposIVA'
		// TODO : 03-05-2024 - El campo 'origenesCaja' falta por serializar (no salen con comillas)
		// TODO : 03-05-2024 - El archivo RS.rs debería copiarse y renovarse cada año... 
		String cadenaResp = "{\n\t\"user\": \"" + this.user +  "\",\n\t\"año\": { \"año\": 2024, \"trimestre\": 2 },\n\t\"rutas\": {\n\t\t\"FCT\": \"./datos/" + this.user.toUpperCase() + "/FCT" + this.año.año() + this.año.trimestre() + ".fct\",\n\t\t\"RS\":  \"./datos/" + this.user.toUpperCase() + "/RS.rs\",\n\t\t\"CJA\": \"./datos/" + this.user.toUpperCase() + "/CJA" + this.año.año() + this.año.trimestre() + ".cja\"\n\t\t},\n\t\"tiposIVA\": " + this.tiposIVA + ",\n\t\"origenesCaja\": [ \"caja\", \"otros\" ]\n}";
		System.out.println("[ConfigData.java]->\n"+cadenaResp);
		return cadenaResp;
	}

}

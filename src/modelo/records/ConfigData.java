package modelo.records;

import java.util.ArrayList;
import java.util.HashMap;

public record ConfigData(String usuario, Año año, HashMap<String, String> rutas, ArrayList<Integer> tiposIVA, ArrayList<String> origenesCaja ){

	public String toJSON() {
		// TODO Auto-generated method stub
		//[usuario=testUser, año=Año[año=2024, trimestre=2], rutas={}, tiposIVA=[0, 21], origenesCaja=[caja, otros]]
		String cadenaResp = "{\n\t\"usuario\": \"TESTuSER\"\n\t\"año\": { \"año\": 2024, \"trimestre\": 2 }\n\t\"rutas\": {\n\t\t\"FCT\": \"config/TESTUSER/FCT20242.fct\",\n\t\t\"RS\": \"config/TESTUSER/RS.rs\",\n\t\t\"CJA\": \"config/TESTUSER/CJA20242.cja\"\n\t\t},\n\t\"tiposIVA\": [ 0, 10, 21],\n\t\"origenesCaja\": [ \"Caja\", \"Devoluciones\"]\n}";
		return cadenaResp;
	}

}

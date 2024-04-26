package modelo.records;

import java.util.ArrayList;

public record UIData(ArrayList<String> nombreColsFCT, ArrayList<Integer> anchoColsFCT) {

	public String toJSON() {
		String cadenaResp = "{\n\t\"nombreColsFcT\": [\"ID\",\"numFact\",\"nombreEmpresa\"],\n\t\"anchoColsFCT\": [ 10, 50, 100]\n}";
		return cadenaResp;
	}

}

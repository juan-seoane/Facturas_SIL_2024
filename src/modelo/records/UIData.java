package modelo.records;

public record UIData(String[] nombreColsFCT, Integer[] anchoColsFCT) {

	public String toJSON() {
		
		int i = 0;
		String nombreColsFCTformat ="[\t";
		for(String str : this.nombreColsFCT){
			i++;
			if (i<this.nombreColsFCT.length){
				nombreColsFCTformat += "\"" + str + "\",";
			}else{
				nombreColsFCTformat += "\"" + str + "\"";
			}
		}	
		nombreColsFCTformat += "\t]";

		i = 0;
		String anchoColsFCTformat ="[\t";
		for(Integer num : this.anchoColsFCT){
			i++;
			if(i<this.anchoColsFCT.length){
				anchoColsFCTformat += " " + num + ",";
			}else{
				anchoColsFCTformat += " " + num;
			}
		}	
		anchoColsFCTformat += "\t]";
		
		String cadenaResp = "{\n\t\"nombreColsFCT\": " + nombreColsFCTformat + ",\n\t\"anchoColsFCT\": " + anchoColsFCTformat + "\n}";
		return cadenaResp;
	}

}

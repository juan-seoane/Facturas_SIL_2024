package modelo.records;

public record RutasConfig(String user, String rutaconfigdata, String rutamisdatos, String rutauidata) {
	public String toJSON(){
		String respuesta;
		respuesta = "{\t\"user\": \""+this.user+"\",\t\"rutaconfigdata\": \""+this.rutaconfigdata+"\",\t\"rutamisdatos\": \""+this.rutamisdatos+"\",\t\"rutauidata\": \""+this.rutauidata+"\"\t}";
		return respuesta;
	}
}
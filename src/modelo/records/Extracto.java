package modelo.records;

import modelo.records.TipoIVA;

public record Extracto(double base, TipoIVA tipoiva, double iva, double total) implements Comparable<Extracto>{
//TODO: 10-04-2024 - Comprobar lo del los demás constructores en los Java records
	
	public Extracto(double base, int tipoiva, double total){
	 
		this(base,new TipoIVA(tipoiva, null), base+base*tipoiva/100, total);	 
	} 

	public Extracto(){
		
		this(0.0,new TipoIVA(0, null), 0.0,0.0);

	}
	
	public Extracto(double base, int tipoiva){
	
		this(base,new TipoIVA(tipoiva, null), base*tipoiva/100, Totales.calcularTotales(base, tipoiva));

	}
	
	public Extracto(int tipoiva, double total){
		//TODO: 10-04-2024 - Comprobar las expresiones de abajo
		this((100 * total) / (100 + tipoiva), new TipoIVA(tipoiva, null), (((100 * total) / (100 + tipoiva))* tipoiva / 100), total);
	
	}

	public int compareTo(Extracto b){
		
		if (b == null)
			return 1;
		else if (this.total < b.total())
			return -1;
		else if (this.total == b.total())
			return 0;
		else return 1;
	
	}
}

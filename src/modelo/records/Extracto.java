package modelo.records;

public class Extracto implements Comparable<Extracto> {
    private double base;
    private int tipoiva;
    private double iva;
    private double total;

    public Extracto(double base, int tipoiva, double iva, double total) {
        this.base = base;
        this.tipoiva = tipoiva;
        this.iva = iva;
        this.total = total;
    }

    public double getBase() {
        return base;
    }

    public void setBase(double base) {
        this.base = base;
    }

    public int getTipoIVA() {
        return tipoiva;
    }

    public void setTipoIVA(int tipoiva) {
        this.tipoiva = tipoiva;
    }

    public double getIVA() {
        return iva;
    }

    public void setIVA(double iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
// TODO - 2024-05-07 : - Revisar la forma de comparar extractos
    @Override
	public int compareTo(Extracto b){
		
		if (b == null)
			return 1;
		else if (this.total < b.getTotal())
			return -1;
		else if (this.total == b.getTotal())
			return 0;
		else return 1;
	
	}

}



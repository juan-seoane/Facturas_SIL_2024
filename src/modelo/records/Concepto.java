package modelo.records;

/*
 * @author Juan Seoane
 */

public record Concepto(String origen) {
    
    public Concepto(String origen){
        this.origen = origen.toUpperCase().trim(); 
    }
}

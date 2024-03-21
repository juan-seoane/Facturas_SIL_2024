/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Juan Seoane
 */
public class Caja implements Serializable, Comparable {

    private int ID;
    private Fecha fecha;
    private Date date;
    private String origen;
    private double caja;
    private boolean haber;
    private Nota nota;

    public Caja() {
        this.ID = 0;
        this.fecha = new Fecha(1,1,Config.getConfig().getAnho().getAnho());
        this.date = new GregorianCalendar().getTime();
        this.origen = new Concepto().getOrigen();
        this.caja = 0.00;
        this.haber = true; // TRUE : HABER  FALSE: DEBE
        this.nota = new Nota();
    }
    
    public Caja(int ID, Fecha fecha, Date date,String origen, double caja, boolean haber, Nota nota) {
        this.ID = ID;
        this.fecha = fecha;
        this.date = date;
        this.origen = origen;
        this.caja = caja;
        this.haber = haber;
        this.nota = nota;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setFecha(Fecha fecha) {
        this.fecha = fecha;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public void setDate(Date date){
        this.date = date;
    }
    
    public void setCaja(double caja, boolean haber) {
        this.caja = caja;
        if (!haber){
            this.caja = caja*(-1);
        }
    }
    public void setHaber(boolean haber){
        this.haber = haber;
    }
    public void setNota(String texto){
        this.nota.setTexto(texto);
    }
    
    public int getID() {
        return ID;
    }

    public Fecha getFecha() {
        return fecha;
    }

    public Date getDate(){
        return this.date;
    }
    
    public String getOrigen() {
        return origen;
    }

    public double getCaja() {
        return caja;
    }
    public boolean isHaber(){
        return this.haber;
    }
    public Nota getNota(){
        return this.nota;
    }
    public boolean equals(Object b){
        return (this.getDate().equals(((Caja)b).getDate())&& this.getCaja() == ((Caja)b).getCaja() && this.getOrigen().equals(((Caja)b).getOrigen()) && this.isHaber() == ((Caja)b).isHaber());
    }
    
    public String toString(){
        return ("CAJA >>> ID : "+ this.ID + " FECHA : "+this.fecha.format+ " ORIGEN : "+this.origen+ " CAJA : "+this.caja +" €");
    }
    
    public int compareTo(Object b){
        return (this.getFecha().compareTo(((Caja)b).getFecha()));
    }
}

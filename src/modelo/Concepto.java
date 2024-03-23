/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author Juan Seoane
 */
public class Concepto implements Serializable{
    
    private String origen;
    
    public Concepto(){
        this.origen = "NUEVO CONCEPTO";
    }
    public Concepto(String origen){
        this.origen = origen.toUpperCase().trim(); 
    }


    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }
    
    
    
}

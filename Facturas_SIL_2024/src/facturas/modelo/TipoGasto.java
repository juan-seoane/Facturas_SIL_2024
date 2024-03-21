package modelo;

import java.io.Serializable;

public class TipoGasto implements Serializable{

  public String tipo;
  public String descripcion;
	
  public TipoGasto(){
	
	this.tipo = "INICIO";
	this.descripcion = "descripcion del tipo de gasto";
  }
  
  // public TipoGasto(String tipo){
	
	// this.tipo = tipo;
	// this.descripcion = "descripcion del tipo de gasto";
  // }
  
  public TipoGasto(String tipo, String descripcion){
	
	this.tipo = tipo.toUpperCase();
	this.descripcion = descripcion;
  }
  
  public boolean equals(Object b){
	
	return (this.tipo.equals(((TipoGasto)b).tipo));
  }
  
  public String toString(){
  
	return ("Concepto : "+this.tipo);
  }
}

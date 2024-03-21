package modelo;

import java.io.Serializable;

public class Anho implements Serializable{

  public int anho;
  public int trimestre;

  public Anho(){
  
	this.anho = 2000;
	this.trimestre = 0;
  }

  public Anho(int anho, int trimestre){
  
	this.anho = anho;
	this.trimestre = trimestre;
  }
  
  public void setAnho(int anho){
  
	this.anho = anho;
  }
  
  public void setTrimestre(int trimestre){
  
	this.trimestre = trimestre;
  }
  
  public int getAnho(){
  
	return this.anho;
  }
  
  public int getTrimestre(){
  
	return this.trimestre;
  }
}
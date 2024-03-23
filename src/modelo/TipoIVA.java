package modelo;

import java.io.Serializable;

public class TipoIVA implements Serializable{

  private int valor;
  private String format;

  public TipoIVA(){
  
	this.valor = 0;
	this.format = format(this.valor);
  }
  
  public TipoIVA(int v){
  
	this.valor = v;
	this.format = format(v);
  }

  public int getValor(){
	
	return this.valor;
  }
  
  public String getFormat(){
  
	return this.format;
  }
  
  public String format(int v){
  
	if (v>21)
		return ("V");
	else return (""+v);
		
  }
}
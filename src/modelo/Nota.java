package modelo;

import java.io.Serializable;
import java.awt.*;

public class Nota implements Serializable{

  private int numero;
  private String texto;

  
  public Nota(){
	
	this.numero = 1;
	this.texto = "";
  }
  
  public Nota(int numero, String texto){
	this.numero = numero;
        if (texto!=null && !texto.equals(""))
            this.texto = texto;
        else 
            this.texto = "";
  }
  
  public TextArea format(int r, int c){
	
	return new TextArea(this.texto,r,c);
  }
  public String getTexto(){
	return this.texto;
  }
  public int getNumero(){
 	return this.numero;
  }

  public void setTexto(String texto) {
        this.texto = texto;
    }
  
  public String toString(){
	
	return ("NOTA # " + this.numero +" \n "+this.texto+" \n\n ");
  }
    
}

package modelo.records;

import java.awt.*;

public record Nota(int numero, String texto) {
  
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
}

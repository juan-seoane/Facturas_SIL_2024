package modelo;

import java.io.Serializable;

public class NIF implements Serializable {

  public int numero;
  public String letra;
  public boolean isCIF;

  
  public NIF(){
	
	this.numero = 99999999;
	this.letra = dameLetraNIF();
        this.isCIF = false;
  }
  
  public NIF(int numero, String letra, boolean isCIF){
  
	this.numero = numero;
	this.letra = letra.toUpperCase();
        this.isCIF = isCIF;
  }
  public int getNumero(){
	return this.numero;
  }
  
  public String dameLetraNIF() {

    switch(this.numero%23) {
      case 0: return "T";
      case 1: return "R";
      case 2: return "W";
      case 3: return "A";
      case 4: return "G";
      case 5: return "M";
      case 6: return "Y";
      case 7: return "F";
      case 8: return "P";
      case 9: return "D";
      case 10: return "X";
      case 11: return "B";
      case 12: return "N";
      case 13: return "J";
      case 14: return "Z";
      case 15: return "S";
      case 16: return "Q";
      case 17: return "V";
      case 18: return "H";
      case 19: return "L";
      case 20: return "C";
      case 21: return "K";
      case 22: return "E";
    }
    return "?";
  }
  public void setIsCIF(boolean bool){
      this.isCIF = bool;
  }
  public boolean isCIF(){
      return this.isCIF;
  }
  public String getLetra(){
	return this.letra;
  }
  public boolean comprobarNIF() {
      if (!this.isCIF()){
        if (dameLetraNIF().equals(this.getLetra()))
            return true;
        else return false;
      } else
          return true;
  }
  public boolean equals(Object b) {
		
	return ((this.getNumero() == ((NIF)b).getNumero()) && (this.getLetra().equals((((NIF)b).getLetra()))) && (this.isCIF() == ((NIF)b).isCIF()));
	  
  }
  public int compareTo(Object b){
      if (this == ((NIF)b)){
          return 0;
      }
      else if (this.getLetra().compareTo(((NIF)b).getLetra()) == -1){
          return -1;
      }
      else if (this.getLetra().compareTo(((NIF)b).getLetra()) == 1){
          return 1;
      }
      else if (this.getNumero() < ((NIF)b).getNumero()){
          return -1;
      }
      else if (this.getNumero() > ((NIF)b).getNumero()){
          return 1;
      }
      else return 0;    
  }
  
  public String toString(){
	if (this.isCIF){
           String num = this.numero +"";
           String prov = num.substring(0,2);
           String resto = num.substring(2,num.length());
           return (this.letra+"-"+prov+"-"+resto);
        }
        else    
            return (this.numero +"-"+this.letra);
  }

  }



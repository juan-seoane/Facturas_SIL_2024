package modelo.records;

public record NIF(int numero, String letra, boolean isCIF) implements Comparable<NIF>{

  public NIF(int numero, String letra, boolean isCIF){
  
    this.numero = numero;
    this.letra = letra.toUpperCase();
    this.isCIF = isCIF;
  }

  public String dameLetraNIF() {

    switch(this.numero()%23) {
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

  public boolean comprobarNIF() {
      if (!this.isCIF()){
        if (dameLetraNIF().equals(this.letra())){
          System.out.println("OK, la letra se corresponde con el número");
          return true;
        }else {
          System.out.println("La letra de este NIF no se corresponde con su número");
          return false;
        }
      } else{
          System.out.println("No se puede comprobar porque no es un NIF, es un CIF");
          return true;
      }
  }

  public int compareTo(NIF b){
      if (this == b){
          return 0;
      }
      else if (this.letra().compareTo((b).letra()) == -1){
          return -1;
      }
      else if (this.letra().compareTo((b).letra()) == 1){
          return 1;
      }
      else if (this.numero() < (b).numero()){
          return -1;
      }
      else if (this.numero() > (b).numero()){
          return 1;
      }
      else return 0;    
  }
  @Override
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



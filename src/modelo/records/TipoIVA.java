package modelo.records;


public record TipoIVA(int valor, String format){

  public TipoIVA(int valor, String format){

    this.valor = valor;
    if (format!=null)
      this.format = format;
    else
      this.format = format(valor);
  }
  
  public String format(int v){
  
    if (v>21)
      return ("V");
    else return (""+v);  
  }
}
package modelo.records;


public class TipoIVA {
  private int valor;
  private String format;

  public TipoIVA(int valor, String format){

    this.valor = valor;
    if (format!=null)
      this.format = format;
    else
      this.format = format(valor);
  }
  
  public int getValor() {
      return valor;
  }

  public void setValor(int valor) {
      this.valor = valor;
  }

  public String getFormat() {
      return format;
  }

  public void setFormat(String format) {
      this.format = format;
  }

  public String format(int v){
  
    if (v>21)
      return ("V");
    else return (""+v);  
  }
}
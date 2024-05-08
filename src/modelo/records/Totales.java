package modelo.records;

//TODO: 07-05-2024 - La clase Totales...¿heredaba de la clase Extracto?
//import modelo.Extracto;

public class Totales {
  private double base;
  private boolean variosIVAs;
  private TipoIVA tipoIVA;
  private double iva;
  private double subtotal;
  private double baseNI;
  private int ret;
  private double retenciones;
  private double total;

  public Totales(){
    this(0.0, false, new TipoIVA(0, null), 0.0, 0.0, 0.0, 0, 0.0, 0.0);
  }

  public Totales(double base, boolean variosIVAs, TipoIVA tipoIVA, double iva, double subtotal, double baseNI, int ret, double retenciones, double total) {
      this.base = base;
      this.variosIVAs = variosIVAs;
      this.tipoIVA = tipoIVA;
      this.iva = iva;
      this.subtotal = subtotal;
      this.baseNI = baseNI;
      this.ret = ret;
      this.retenciones = retenciones;
      this.total = total;
  }

  public double getBase() {
      return base;
  }

  public void setBase(double base) {
      this.base = base;
  }

  public boolean isVariosIVAs() {
      return variosIVAs;
  }

  public void setVariosIVAs(boolean variosIVAs) {
      this.variosIVAs = variosIVAs;
  }

  public TipoIVA getTipoIVA() {
      return tipoIVA;
  }

  public void setTipoIVA(TipoIVA tipoIVA) {
      this.tipoIVA = tipoIVA;
  }

  public double getIVA() {
      return iva;
  }

  public void setIVA(double iva) {
      this.iva = iva;
  }

  public double getSubtotal() {
      return subtotal;
  }

  public void setSubtotal(double subtotal) {
      this.subtotal = subtotal;
  }

  public double getBaseNI() {
      return baseNI;
  }

  public void setBaseNI(double baseNI) {
      this.baseNI = baseNI;
  }

  public int getRet() {
      return ret;
  }

  public void setRet(int ret) {
      this.ret = ret;
  }

  public double getRetenciones() {
      return retenciones;
  }

  public void setRetenciones(double retenciones) {
      this.retenciones = retenciones;
  }

  public double getTotal() {
      return total;
  }

  public void setTotal(double total) {
      this.total = total;
  }

  //TODO: 07-05-2024 - Puede que necesite calcular los totales pasando otros parámetros...
  public static double calcularTotales(double base, int tipoIVA) {
	  return (base + (base * tipoIVA)/100 );
  }

  public static boolean comprobarTotales(Totales tot) {
	  return ((tot.getBase() + tot.getIVA() - tot.getRetenciones() == tot.getTotal()) && (tot.getBase() * tot.getTipoIVA().getValor() /100 == tot.getIVA()) && (tot.getBase()*tot.getRet()/100 == tot.getRetenciones()));
  }
}

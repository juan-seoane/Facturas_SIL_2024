package modelo.records;

//TODO: 10-04-2024 - La clase Totales heredaba de la clase Extracto
//import modelo.Extracto;

public record Totales( double base, boolean variosIVAs, TipoIVA tipoIVA, double iva, double subtotal, double baseNI, int ret, double retenciones, double total) {

  public Totales(){
    this(0.0, false, new TipoIVA(0, null), 0.0, 0.0, 0.0, 0, 0.0, 0.0);
  }
  
  public static boolean comprobarTotales(Totales tot) {
	  return ((tot.base() + tot.iva() - tot.retenciones() == tot.total()) && (tot.base() * tot.tipoIVA().valor() /100 == tot.iva()) && (tot.base()*tot.ret()/100 == tot.retenciones()));
  }

  public static double calcularTotales(double base, int tipoIVA) {
	  return (base + (base * tipoIVA)/100 );
  }

}

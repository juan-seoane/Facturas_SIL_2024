package modelo.records;

public record TipoGasto(String tipo, String descripcion){
	
  public TipoGasto(String tipo, String descripcion){
    if (tipo!=null){
      this.tipo = tipo.toUpperCase();
    }else{
	    this.tipo = "INICIO";
    }
    if (descripcion != null){
      this.descripcion = descripcion;
    }else{
      this.descripcion = "descripcion del tipo de gasto";
    }
  }

  public boolean equals(Object b){

	  return (this.tipo().equals(((TipoGasto)b).tipo()));
  }
}

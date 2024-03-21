package modelo;

import java.util.*;
import java.io.Serializable;

public class MisDatos implements Serializable{

  private miRS miRS;
  private ArrayList<Contrasenha> contrasenhas;

  public MisDatos(){
      
	this.miRS = new miRS();
	this.contrasenhas = new ArrayList<Contrasenha>();
	contrasenhas.add(new Contrasenha());
  }

  public miRS getMiRS(){
    return this.miRS;
  }

  public ArrayList<Contrasenha> getContrasenhas(){
    return this.contrasenhas;
  }

  public void setMiRS(miRS RS){
    this.miRS = RS;
  }

  public void setContrasenhas(ArrayList<Contrasenha> contrasenhas){
    this.contrasenhas = contrasenhas;
  }
  
}
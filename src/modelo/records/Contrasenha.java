package modelo.records;
// TODO: 09/04/24 - Convertir en Java Record
//TODO: 09/04/24 - Si las Contraseñas se guardan en formato json, no es necesaria la encriptación
import java.security.Key;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import controladores.fxcontrollers.Acceso;
import modelo.AESCryptoImpl;
import modelo.CryptoKeyable;
import modelo.Cryptographical;


public class Contrasenha implements Serializable{

  private String usuario;
  private String contrasenha;
  private SecretKey key;
  
public Contrasenha(){
	
	this.usuario = "admin".toUpperCase();
        try {
            this.contrasenha = encriptar("admin");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Contrasenha.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try{
            Acceso.imprimir(Acceso.getCanvas(), "La nueva contraseña es  : "+ this.contrasenha);
        }catch(Exception ex){
            ex.printStackTrace();
        }

  }
  
  public Contrasenha(String usuario,String contrasenha){
	
	this.usuario = usuario.toUpperCase();
        try {	
            this.contrasenha = encriptar(contrasenha);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Contrasenha.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
  
  public void setUsusario(String usuario){
	
	this.usuario = usuario.toUpperCase();
  }
  
  public void setContrasenha(String contrasenha){
        try {
            this.contrasenha = encriptar(contrasenha);
        } catch (NoSuchAlgorithmException ex) {
             System.out.println("Error al encriptar la contraseña!");
        }
  }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasenha() {
        
        return desencriptar(this.contrasenha);
    }

    public String encriptar(String contras) throws NoSuchAlgorithmException{
        this.key = KeyGenerator.getInstance("AES").generateKey();
        
        try {
            Cryptographical crypto;
            crypto = AESCryptoImpl.initialize(new AESCryptoKey(this.key));
            String enc = crypto.encrypt(contras);
            System.out.println("La contraseña encriptada es: "+enc);
            return enc;
        } catch (Exception ex) {
            Logger.getLogger(Contrasenha.class.getName()).log(Level.SEVERE, null, ex);
        }
       return null;
    }
    public String desencriptar(String contras){
        Cryptographical crypto;
        try {
            crypto = AESCryptoImpl.initialize(new AESCryptoKey(this.key));
            String dec = crypto.decrypt(contras);
            System.out.println("La contraseña desencriptada es: "+dec);
            return dec;
        } catch (Exception ex) {
            Logger.getLogger(Contrasenha.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

private static class AESCryptoKey implements CryptoKeyable {
        
        SecretKey key;
        
        public AESCryptoKey(SecretKey key) {
            this.key = key;
        }

        @Override
        public Key getKey() {
            return this.key;
        }
    }
}  

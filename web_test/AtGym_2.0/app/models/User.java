package models;

import java.util.*;
import play.db.ebean.Model;
import play.data.validation.Constraints.*;
import javax.persistence.Entity;

@Entity
public class User extends Model{
  
  public String email;
  public String nachname;
  public String vorname;
  public String password;
  public double groesse;
  public double gewicht;
  public Geschlecht geschlecht;
  public Map<String, Plan> plaene = new HashMap<String, Plan>();
  
 /* public User(String email, String password, double groesse, double gewicht, Geschlecht geschlecht){
	this.email=email;
	this.password = password;
	this.groesse = groesse;
	this.gewicht = gewicht;
	this.geschlecht = geschlecht;
  }
  
  public void setGewicht(double gewicht){
	this.gewicht=gewicht;
  }
  
  public void planHinzufuegen(){
  
  }*/
    
}
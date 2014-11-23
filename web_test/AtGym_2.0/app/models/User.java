package models;

import java.util.*;
import play.db.ebean.*;
import play.data.validation.Constraints.*;

public class User extends Model{
    
  private int id;
  private String email;
  private String password;
  private double groesse;
  private double gewicht;
  private Geschlecht geschlecht;
  private Map<String, Plan> plaene = new HashMap<String, Plan>();
  
  public User(int id, String email, String password, double groesse, double gewicht, Geschlecht geschlecht){
	this.id=id;
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
  
  }
    
}
package models;

import java.util.*;
import play.db.ebean.Model;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.*;
import javax.persistence.Entity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Entity
public class User extends Model{
  
  private String email;
  private String nachname;
  private String vorname;
  private String password;
  private double groesse;
  private double gewicht;
  private Geschlecht geschlecht;
  private Map<String, Plan> plaene = new HashMap<String, Plan>();
  
  public User(String email, String nachname, String vorname, String password, double groesse, double gewicht, Geschlecht geschlecht){
	this.email=email;
	this.nachname=nachname;
	this.vorname = vorname;
	this.password = password;
	this.groesse = groesse;
	this.gewicht = gewicht;
	this.geschlecht = geschlecht;
  }
  
  public void setEmail(String email){
	this.email=email;
  }
  public String getEmail(){
  return email;
  }
  public void setVorname(String vorname){
	this.vorname=vorname;
  }
  public String getVorname(){
  return vorname;
  }
  public void setNachname(String nachname){
	this.nachname=nachname;
  }
  public String getNachname(){
  return nachname;
  }
  public void setPassword(String password){
	this.password=password;
  }
   public String getPassword(){
  return password;
  }
  public double getGroesse(){
  return groesse;
  }
  public void setGroesse(double groesse){
  this.groesse=groesse;
  }
  public double getGewicht(){
  return gewicht;
  }
  public void setGewicht(double gewicht){
  this.gewicht=gewicht;
  }
  public Geschlecht getGeschlecht(){
  return geschlecht;
  }
  public void setGeschlecht(Geschlecht geschlecht){
  this.geschlecht=geschlecht;
  }
  
  
  public List<ValidationError> validate() {
		List<ValidationError> error = new ArrayList<>();
		
		if(email == null || email.length() == 0 ){
			error.add(new ValidationError("email", "This field is needed"));
		}
		
		if(email != null || email.length() != 0 ){
		String pattern = "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
		boolean matches = Pattern.matches(pattern, email);
		if(matches == false )
			error.add(new ValidationError("email", "This field is needed"));
		}
		
		if(password == null || password.length() == 0){
			error.add(new ValidationError("password", "This field is needed"));
		}
		
		if(vorname == null || vorname.length() == 0){
			error.add(new ValidationError("vorname", "This field is needed"));
		}
		if(vorname != null || vorname.length() != 0){
		String pattern = "[A-Za-z]+";
		boolean matches = Pattern.matches(pattern, vorname);
		if(matches == false )
			error.add(new ValidationError("vorname", "This field is needed"));
		}
		
		if(nachname == null || nachname.length() == 0){
			error.add(new ValidationError("nachname", "This field is needed"));
		}
		if(nachname != null || nachname.length() != 0){
		String pattern = "[A-Za-z]+";
		boolean matches = Pattern.matches(pattern, nachname);
		if(matches == false )
			error.add(new ValidationError("nachname", "This field is needed"));
		}
		
		if(groesse <= 0 || groesse > 280){
			error.add(new ValidationError("groesse", "This field is needed"));
		}
		if(gewicht <= 0){
			error.add(new ValidationError("gewicht", "This field is needed"));
		}
		if(geschlecht == null){
			error.add(new ValidationError("geschlecht", "This field is needed"));
		}
		// Nothing in "error" return null, else return error
		return error.isEmpty() ? null : error;
	}
 
 /* public void setGewicht(double gewicht){
	this.gewicht=gewicht;
  }
  
  public void planHinzufuegen(){
  
  }*/
    
}
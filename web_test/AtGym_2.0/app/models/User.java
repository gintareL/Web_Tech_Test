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
  
  private String email = null;
  private String nachname = null;
  private String vorname = null;
  private String password = null;
  private double groesse=0;
  private double gewicht=0;
  private Geschlecht geschlecht;
  private String bild =null;
  private Map<Integer,Plan> plaene = new HashMap<Integer, Plan>();
  
  public User(){}
  public User(String email, String nachname, String vorname, String password, double groesse, double gewicht, Geschlecht geschlecht){
	this.email=email;
	this.nachname=nachname;
	this.vorname = vorname;
	this.password = password;
	this.groesse = groesse;
	this.gewicht = gewicht;
	this.geschlecht = geschlecht;
  }
  
  public Map<Integer, Plan> getPlans(){
  return plaene;
  }
  public void setPlans(Plan p){
  this.plaene.put(p.getId(), p);
  }
  public String getBild(){
	if(bild != null) return bild;
	else if(getGeschlecht().equals("weiblich")) return "default_bild_w.jpg";
	else return "default_bild_m.jpg";
  }
  public void setBild(String bild){
	this.bild = bild;
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
  public String getGeschlecht(){
  if(this.geschlecht == Geschlecht.maennlich) return "maennlich";
  else if(this.geschlecht == Geschlecht.weiblich) return "weiblich";
  return null;
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
 
	
  public void uebungLoeschen(int p, Tag t, AusgewaehlteUebung u){
	plaene.get(p).getUebungen().get(t).loeschen(u);
  }
  public void planHinzufuegen(){
  
  }
    
}
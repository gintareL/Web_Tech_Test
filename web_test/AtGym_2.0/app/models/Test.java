package models;

import java.util.*;
import play.db.ebean.Model;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.*;
import javax.persistence.Entity;

@Entity
public class Test extends Model{
	
	public String email;
  public String nachname;
  public String vorname;
  public String password;
  public double groesse;
  public double gewicht;
  public Geschlecht geschlecht;
	

	public Test(String email, String nachname, String vorname, String password, double groesse, double gewicht, Geschlecht geschlecht){
	this.email=email;
	this.nachname=nachname;
	this.vorname = vorname;
	this.password = password;
	this.groesse = groesse;
	this.gewicht = gewicht;
	this.geschlecht = geschlecht;
  }
	
	public List<ValidationError> validate() {
		List<ValidationError> error = new ArrayList<>();
		
		if(email == null || email.length() == 0){
			error.add(new ValidationError("email", "This field is needed"));
		}
		if(password == null || password.length() == 0){
			error.add(new ValidationError("password", "This field is needed"));
		}
		if(vorname == null || vorname.length() == 0){
			error.add(new ValidationError("vorname", "This field is needed"));
		}
		if(nachname == null || nachname.length() == 0){
			error.add(new ValidationError("nachname", "This field is needed"));
		}
		if(groesse <= 0){
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
	
}
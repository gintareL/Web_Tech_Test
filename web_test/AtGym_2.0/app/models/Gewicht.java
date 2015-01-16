package models;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.*;
import play.db.ebean.Model;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.*;
import javax.persistence.Entity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Object.*;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigestSpi;
import java.security.MessageDigest;
import java.security.Security;
import java.util.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import play.db.ebean.Model;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.*;

public class Gewicht{
	
	private double gewicht;
	private Date datum;
	private String datumString;

	public Gewicht(){}	
	
public Gewicht(double gewicht){
		this.gewicht=gewicht;
	   this.datum = new Date();
	   DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
	   this.datumString=dateFormat.format(datum);
}

public Gewicht(double gewicht, Date datum){
	this.gewicht=gewicht;
	  this.datum = datum;
	   DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
	   this.datumString=dateFormat.format(datum);
}

public Gewicht(double gewicht, String datum){
	this.gewicht=gewicht;
	this.datumString = datum;
	
}


public double getGewicht(){
	return gewicht;
}	

public String getDatumString(){
	 return datumString;
}

public Date getDatum(){
	return datum;
}

public void setGewicht(double gewicht){
	this.gewicht=gewicht;
	 
	   //get current date time with Date()
	   this.datum = new Date();
	    DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
	   this.datumString=dateFormat.format(datum);
	  
}
  public List<ValidationError> validate() {
		List<ValidationError> error = new ArrayList<>();
		
		
		if(gewicht < 0 | gewicht > 300){
			error.add(new ValidationError("gewicht", "This field is needed"));
		} else{
			setGewicht(gewicht);
		}
		
		return error.isEmpty() ? null : error;
	}


}
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
	private int id;
	private double gewicht;
	private Date datum;
	
public Gewicht(){}	
public Gewicht(double gewicht){
	this.gewicht=gewicht;
	  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	   //get current date time with Date()
	   this.datum = new Date();
	  
	   System.out.println(dateFormat.format(datum));
}

public Gewicht(double gewicht, Date datum){
	this.gewicht=gewicht;
	  this.datum = datum;
}

public void setId(int id){
	this.id= id;
}
public int fetId(){
	return id;
}

public double getGewicht(){
	return gewicht;
}	

public Date getDatum(){
	return datum;
}

public void setGewicht(double gewicht){
	this.gewicht=gewicht;
	  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	   //get current date time with Date()
	   this.datum = new Date();
	  
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
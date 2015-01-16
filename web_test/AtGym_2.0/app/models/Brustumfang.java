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

public class Brustumfang{
	
	private double umfang;
	private Date datum;
	private String datumString;

	public Brustumfang(){}	
	
public Brustumfang(double umfang){
		this.umfang=umfang;
	   this.datum = new Date();
	   DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
	   this.datumString=dateFormat.format(datum);
}

public Brustumfang(double umfang, Date datum){
	this.umfang=umfang;
	  this.datum = datum;
	   DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
	   this.datumString=dateFormat.format(datum);
}

public Brustumfang(double umfang, String datum){
	this.umfang=umfang;
	this.datumString = datum;
	
}


public double getUmfang(){
	return umfang;
}	

public String getDatumString(){
	 return datumString;
}

public Date getDatum(){
	return datum;
}

public void setUmfang(double umfang){
	this.umfang=umfang;
	 
	   //get current date time with Date()
	   this.datum = new Date();
	    DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
	   this.datumString=dateFormat.format(datum);
	  
}
  public List<ValidationError> validate() {
		List<ValidationError> error = new ArrayList<>();
		
		
		if(umfang < 0 | umfang > 300){
			error.add(new ValidationError("umfang", "This field is needed"));
		} else{
			
			setUmfang(umfang);
		}
		
		return error.isEmpty() ? null : error;
	}


}
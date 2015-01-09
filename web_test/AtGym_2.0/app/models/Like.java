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

public class Like{
	
	private int id;
	private String muskelgruppe;
	

	public Like(){}	
	
public Like(int id){
		this.id = id;
	
	
}




public int getId(){
	return id;
}	
public String getMuskelgruppe(){
	return muskelgruppe;
}

public void setId(int id){
	this.id=id;	 
}
public void setMuskelgruppe(String muskelgruppe){
	this.muskelgruppe=muskelgruppe;
}


  public List<ValidationError> validate() {
		List<ValidationError> error = new ArrayList<>();
		
		if(id < 0){
			error.add(new ValidationError("umfang", "This field is needed"));
		} else{
			setMuskelgruppe(muskelgruppe);
			setId(id);
		}
		
		
		
		return error.isEmpty() ? null : error;
	}


}
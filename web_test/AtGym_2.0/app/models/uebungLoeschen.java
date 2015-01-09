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

public class uebungLoeschen{
	
	private int plan;
	private int uebung;
	private String tag;

	public uebungLoeschen(){}	
	
public uebungLoeschen(int plan, int uebung, String tag){
		this.plan=plan;
		this.uebung=uebung;
		this.tag=tag;
}




public int getPlan(){
	return plan;
}	

public int getUebung(){
	return uebung;
}	

public String getTag(){
	 return tag;
}

public void setPlan(int plan){
	this.plan=plan;	 
}

public void setUebung(int uebung){
	this.uebung=uebung;	 
}

public void setTag(String tag){
	this.tag=tag;	 
}



  public List<ValidationError> validate() {
		List<ValidationError> error = new ArrayList<>();
		System.out.println("validierung");
		if(plan < 0){
			error.add(new ValidationError("umfang", "This field is needed"));
		} else{
			
			setPlan(plan);
		}
		if(uebung < 0){
			error.add(new ValidationError("umfang", "This field is needed"));
		} else{
			setUebung(uebung);
		}
		if(tag == null){
			error.add(new ValidationError("umfang", "This field is needed"));
		} else{
			setTag(tag);
		}
		
		
		return error.isEmpty() ? null : error;
	}


}
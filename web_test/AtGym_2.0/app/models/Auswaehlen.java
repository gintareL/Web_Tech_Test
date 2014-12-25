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

public class Auswaehlen{
	
	private int id;
	private int satz;
	private String tag;
	private String plan;

	public Auswaehlen(){}	
	
public Auswaehlen(int id, int satz, String tag, String plan){
		this.id = id;
		this.satz = satz;
		this.tag = tag;
		this.plan = plan;
}




public int getId(){
	return id;
}	

public int getSatz(){
	return satz;
}	

public String getTag(){
	 return tag;
}

public String getPlan(){
	return plan;
}

public void setId(int id){
	this.id=id;	 
}

public void setSatz(int satz){
	this.satz=satz;	 
}

public void setTag(String tag){
	this.tag=tag;	 
}

public void setPlan(String plan){
	this.plan=plan;	 
}

  public List<ValidationError> validate() {
		List<ValidationError> error = new ArrayList<>();
		
		if(id < 0){
			error.add(new ValidationError("umfang", "This field is needed"));
		} else{
			
			setId(id);
		}
		if(satz < 0){
			error.add(new ValidationError("umfang", "This field is needed"));
		} else{
			setSatz(satz);
		}
		if(tag == null){
			error.add(new ValidationError("umfang", "This field is needed"));
		} else{
			setTag(tag);
		}
		if(plan == null){
			error.add(new ValidationError("umfang", "This field is needed"));
		} else{
			setPlan(plan);
		}
		
		return error.isEmpty() ? null : error;
	}


}
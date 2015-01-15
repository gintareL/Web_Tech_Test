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

public class Satz{
	private int id;
	private int wh;
	private int gewicht;
	int plan;
	int uebung;
	String tag;
	int satz;
	
	public Satz(){}
	
	public Satz(int id, int wh, int gewicht){
		this.id=id;
		this.wh=wh;
		this.gewicht=gewicht;
	}
	
	public Satz( int wh, int gewicht){
		
		this.wh=wh;
		this.gewicht=gewicht;
	}
	
	public int getId(){
		return id;
	}
	
	public int getWh(){
		return wh;
	}
	
	public int getGewicht(){
		return gewicht;
	}
	
	public void setId(int id){
		this.id=id;
	}
	
	public void setWh(int wh){
		this.wh=wh;
	}
	
	public void setSatz(int satz){
		this.satz=satz;
	}
	
	public int getSatz(){
		return satz;
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
	
	public void setGewicht(int gewicht){
		this.gewicht=gewicht;
	}
	
	 public List<ValidationError> validate() {
		List<ValidationError> error = new ArrayList<>();
		setPlan(plan);
		setTag(tag);
		setUebung(uebung);
		setSatz(satz);
		
		if(wh < 0){
			error.add(new ValidationError("umfang", "This field is needed"));
		} else{
			setWh(wh);
		}
		if(gewicht < 0){
			error.add(new ValidationError("umfang", "This field is needed"));
		} else{
			setGewicht(gewicht);
		}
		
		
		return error.isEmpty() ? null : error;
	}
	
	
}
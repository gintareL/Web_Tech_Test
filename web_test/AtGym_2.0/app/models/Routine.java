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
public class Routine{
	private Date datum;
	private String datumString;
	private int plan;
	private String tag;
	private int uebung;
	private Satz[] satz;
	
	public Routine(){}
	
	public Routine(int plan, String tag, int uebung, int anzahl){
		this.plan=plan;
		this.tag=tag;
		this.uebung=uebung;
		satz=new Satz[anzahl];
		this.datum = new Date();
	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    this.datumString=dateFormat.format(datum);
	}
	
	public Routine(int plan, String tag, int uebung, int anzahl, Date datum){
		this.plan=plan;
		this.tag=tag;
		this.uebung=uebung;
		satz=new Satz[anzahl];
	  this.datum = datum;
	   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	   this.datumString=dateFormat.format(datum);
}

public Routine(int plan, String tag, int uebung, int anzahl, String datum){
		this.plan=plan;
		this.tag=tag;
		this.uebung=uebung;
		satz=new Satz[anzahl];
		this.datumString = datum;
	
}

public Routine(int plan, String tag, int uebung, Satz[] satz, String datum){
		this.plan=plan;
		this.tag=tag;
		this.uebung=uebung;
		this.satz=satz;
		this.datumString = datum;
	
}

public Routine(int plan, String tag, int uebung, Satz[] satz, Date datum){
		this.plan=plan;
		this.tag=tag;
		this.uebung=uebung;
		this.satz=satz;
		this.datum = datum;
	   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	   this.datumString=dateFormat.format(datum);
	
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
	
	public void setTag(String tag){
		this.tag=tag;
	}
	
	public void setPlan(int plan){
		this.datum = new Date();
	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		this.datumString=dateFormat.format(datum);
		this.plan=plan;
	}
	
	
	public void setUebung(int uebung){
		this.uebung=uebung;
	}
	
	public Satz[] getSatz(){
		return satz;
	}
	
	public List<ValidationError> validate() {
		
		List<ValidationError> error = new ArrayList<>();
		setPlan(plan);
		setUebung(uebung);
		setTag(tag);
		
		
		
			int wh0=0;
			int gewicht0=0;
			int wh1=0;
			int gewicht1=0;
			int wh2=0;
			int gewicht2=0;
			System.out.println();
		if(wh0 <= 0 ){
			error.add(new ValidationError("umfang", "This field is needed"));
		} else{
			satz[0].setWh(wh0);
		}
		if(gewicht0 < 0 ){
			error.add(new ValidationError("umfang", "This field is needed"));
		} else{
			satz[0].setGewicht(gewicht0);
		}
		if(wh1 <= 0 ){
			error.add(new ValidationError("umfang", "This field is needed"));
		} else{
			satz[1].setWh(wh1);
		}
		if(gewicht1 < 0 ){
			error.add(new ValidationError("umfang", "This field is needed"));
		} else{
			satz[1].setGewicht(gewicht1);
		}
		if(wh2 <= 0 ){
			error.add(new ValidationError("umfang", "This field is needed"));
		} else{
			satz[2].setWh(wh2);
		}
		if(gewicht2 < 0 ){
			error.add(new ValidationError("umfang", "This field is needed"));
		} else{
			satz[2].setGewicht(gewicht2);
		}
		
		
		
		
		
		return error.isEmpty() ? null : error;
	}
	
	
}
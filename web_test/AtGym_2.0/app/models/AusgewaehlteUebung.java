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

public class AusgewaehlteUebung{
	private Uebung uebung; 
	private int wh;
	
	public AusgewaehlteUebung(Uebung u, int anzahl){
		uebung = u;
		wh=anzahl;
	}
	
	public AusgewaehlteUebung(){
		
	}

	
	public Uebung getUebung(){
		return uebung;
	}
	
	
	public int getWh(){
		return wh;
	}

}
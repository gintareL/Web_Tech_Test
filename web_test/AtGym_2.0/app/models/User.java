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
@Entity
public class User extends Model{
	public Models model;
	private String email = null;
	private String nachname = null;
	private String vorname = null;
	private String password;
	private int id;
	private int groesse=0;
	private Geschlecht geschlecht;
	private SortedMap<Integer, String> bild =new TreeMap<Integer, String>();
	public String defaultBild=null;

	private Map<String,Plan> plaene = new HashMap<String, Plan>();
	private SortedMap<Integer,Gewicht> gewichtList = new TreeMap<Integer, Gewicht>();
	
	private SortedMap<Integer,Bauchumfang> bauchumfangList = new TreeMap<Integer,Bauchumfang>();
	private SortedMap<Integer,Armumfang> armumfangList = new TreeMap<Integer,Armumfang>();
	private SortedMap<Integer,Hueftenumfang> hueftenumfangList = new TreeMap<Integer,Hueftenumfang>();
	private SortedMap<Integer,Brustumfang> brustumfangList = new TreeMap<Integer,Brustumfang>();
	private Set<Routine> routine = new HashSet<Routine>(); // für Analyse kann man die Liste iterieren und die Daten auslesen.

	private Gewicht gewicht = null;
	private Bauchumfang bauchumfang = null;
	private Hueftenumfang hueftenumfang = null;
	private Armumfang armumfang = null;
	private Brustumfang brustumfang = null;


	public User(){}
	public User(String email, String password){
		this.email=email;
		this.password = password;
	}

	public User(String vorname, String nachname, String email, String password, int groesse, int geschlecht){
		this.email=email;
		this.nachname=nachname;
		this.vorname = vorname;
		this.password = password;
		
		this.groesse = groesse;
		if(geschlecht == 0)
		this.geschlecht = Geschlecht.weiblich;
		else this.geschlecht = Geschlecht.maennlich;
	}
	public User(String vorname, String nachname, String email, String password, int groesse, int geschlecht, String bild){
		this.email=email;
		this.nachname=nachname;
		this.vorname = vorname;
		this.password = password;
		int anzahl = (this.bild.size()+1);
		this.bild.put(anzahl,bild);
		this.groesse = groesse;
		if(geschlecht == 0)
		this.geschlecht = Geschlecht.weiblich;
		else this.geschlecht = Geschlecht.maennlich;
	}

	public User(String vorname, String nachname, String email, String password, int groesse, Geschlecht geschlecht){
		this.email=email;
		this.nachname=nachname;
		this.vorname = vorname;
		this.password = password;	
		this.groesse = groesse;	
		this.geschlecht = geschlecht;
	}

	public User(String vorname, String nachname, String email, String password, int groesse, Geschlecht geschlecht, String bild){
		this.email=email;
		this.nachname=nachname;
		this.vorname = vorname;
		this.password = password;
		int anzahl = (this.bild.size()+1);
		this.bild.put(anzahl,bild);
		this.groesse = groesse;
		
		this.geschlecht = geschlecht;
	}
	public User(int id, String vorname, String nachname, String email, String password, int groesse, int geschlecht, SortedMap<Integer, String> bild){
		this.id=id;
		this.email=email;
		this.nachname=nachname;
		this.vorname = vorname;
		this.password = password;
		this.bild=bild;
		this.groesse = groesse;
		if(geschlecht == 0)
		this.geschlecht = Geschlecht.weiblich;
		else this.geschlecht = Geschlecht.maennlich;
	}
	public User(int id, String vorname, String nachname, String email, String password, int groesse, int geschlecht){
		this.id=id;
		this.email=email;
		this.nachname=nachname;
		this.vorname = vorname;
		this.password = password;
		
		this.groesse = groesse;
		if(geschlecht == 0)
		this.geschlecht = Geschlecht.weiblich;
		else this.geschlecht = Geschlecht.maennlich;
	}

	public Gewicht getGewicht(){
		return this.gewicht;
	}

	public void setGewicht(Gewicht g){
		this.gewicht=g;
		gewichtList.put(0, g);
	}

	public Bauchumfang getBauchumfang(){
		return bauchumfang;
	}

	public void setBauchumfang(Bauchumfang b){
		bauchumfang=b;
		bauchumfangList.put(0, b);
	}

	public Armumfang getArmumfang(){
		return armumfang;
	}

	public void setArmumfang(Armumfang b){
		armumfang=b;
		armumfangList.put(0, b);
	}

	public Brustumfang getBrustumfang(){
		return brustumfang;
	}

	public void setBrustumfang(Brustumfang b){
		brustumfang=b;
		brustumfangList.put(0, b);
	}

	public Hueftenumfang getHueftenumfang(){
		return hueftenumfang;
	}

	public void setHueftenumfang(Hueftenumfang b){
		hueftenumfang=b;
		hueftenumfangList.put(0, b);
	}

	public void setRoutine(int plan, String tag, int uebung, Satz[] satz, Date datum){
		Routine r = new Routine(plan, tag, uebung, satz, datum);
		routine.add(r);
	}
	public void setRoutineOhneSaetze(int plan, String tag, int uebung, int saetze, Date datum){
		Satz[] satz = new Satz[saetze];
		Routine r = new Routine(plan, tag, uebung, satz, datum);
		routine.add(r);
	}
	
	public void setSaetze(int plan, String tag, int uebung, int satz, int wh, int gewicht){
		for(Routine r : routine){
			if(r.getPlan() == plan && r.getUebung()==uebung && r.getTag().equals(tag)){
				if(satz <= r.getSatz().length){
					r.getSatz()[(satz-1)].setWh(wh);
					r.getSatz()[(satz-1)].setGewicht(gewicht);
				}
				/*if(satz == r.getSatz().length){
					model.routineSpeichern(r);
				}*/
			}
		}
		System.out.println("as ueseris");
	}
	
	

	public void setRoutineString(int plan, String tag, int uebung, Satz[] satz, String datum){
		Routine r = new Routine(plan, tag, uebung, satz, datum);
		
		routine.add(r);
	}

	public Map<String, Plan> getPlans(){
		return plaene;
	}
	public void setPlans(Plan p){
		this.plaene.put(p.getName(), p);
	}
	public SortedMap<Integer, String> getBild(){
		
		return bild;
	}
	public void setBild(String bild){
		int anzahl = this.bild.size()+1;
		this.bild.put(anzahl, bild);
	}
	public void setEmail(String email){
		this.email=email;
	}
	public void setPassword(String password){
		this.password=password;
	}

	public String getEmail(){
		return email;
	}
	public void setVorname(String vorname){
		this.vorname=vorname;
	}
	public String getVorname(){
		return vorname;
	}
	public void setNachname(String nachname){
		this.nachname=nachname;
	}
	public String getNachname(){
		return nachname;
	}
	public String getPassword(){
		return password;
	}

	public int getGroesse(){
		return groesse;
	}
	public void setGroesse(int groesse){
		this.groesse=groesse;
	}

	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id=id;
	}

	public String getGeschlecht(){
		if(this.geschlecht == Geschlecht.maennlich) return "männlich";
		else if(this.geschlecht == Geschlecht.weiblich) return "weiblich";
		return null;
	}
	public void setGeschlecht(Geschlecht geschlecht){
		this.geschlecht=geschlecht;
	}
	public Set<Routine> getRoutine(){
		routine= model.routineAuslesen(this);
		return routine;
	}

	public SortedMap<Integer, Gewicht> getGewichtList(){
		return gewichtList;
	}

	public SortedMap<Integer, Bauchumfang> getBauchumfangList(){
		return bauchumfangList;
	}

	public SortedMap<Integer, Armumfang> getArmumfangList(){
		return armumfangList;
	}

	public SortedMap<Integer, Hueftenumfang> getHueftenumfangList(){
		return hueftenumfangList;
	}

	public SortedMap<Integer, Brustumfang> getBrustumfangList(){
		return brustumfangList;
	}

	/*public Gewicht[] getGewichtArray(){
		return gewichtArray;
	}
	
	public void setGewichtArray(Gewicht[] g){
		gewichtArray = g;
	}*/

	public List<ValidationError> validate() {
		List<ValidationError> error = new ArrayList<>();
		
		if(email == null || email.length() == 0 ){
			error.add(new ValidationError("email", "This field is needed"));
		}
		
		if(email != null || email.length() != 0 ){
			String pattern = "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
			boolean matches = Pattern.matches(pattern, email);
			if(matches == false )
			error.add(new ValidationError("email", "This field is needed"));
		}
		
		if(password == null || password.length() == 0){
			error.add(new ValidationError("password", "This field is needed"));
		}

		return error.isEmpty() ? null : error;
	}

	public void uebungLoeschen(String p, Tag t, int u){
		plaene.get(p).getUebungen().get(t).loeschen(u);
	}

}
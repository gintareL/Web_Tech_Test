package models;
import java.sql.*;
import java.sql.Connection;  
 import java.sql.DriverManager;  
 import java.sql.ResultSet;  
 import java.sql.Statement; 
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSetMetaData;
import java.sql.DatabaseMetaData;
 
import java.util.*;
import play.db.ebean.Model;
import play.data.validation.Constraints.*;
import javax.persistence.Entity;
import java.io.IOException;
import javax.swing.*;

public class Models extends Model{
	Connection conn = null;
	 Statement stmt = null;
	 ResultSet rs = null;	
	//static int index=100; 	
	private static Models instance = null;
	private static User user;
   private Models() {
		conn = AtGymDatabase.dbConnector();
		
      // Exists only to defeat instantiation.
   }
   
   public boolean checkUser(String em, String password){
	   String p = getHash(password);
	   try {
	 stmt = conn.createStatement();
	 rs = stmt.executeQuery( "SELECT * FROM user where email ='" + em + "';" );
	 while ( rs.next() ) {
		 String email = rs.getString("email");
		 String passwort = rs.getString("password");
	 if(email.equals(em)==true && passwort.equals(p)==true ){
		
		 this.user = new User(rs.getString("vorname"), rs.getString("nachname"), email, password, rs.getInt("groesse"), rs.getInt("geschlecht"));
		 gewichtList();
		 bauchumfangList();
		 armumfangList();
		 hueftenList();
		 brustumfangList();
		return true;
	 }
	
	 }
	  return false;
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
	   return false;
    }
   }
   
   public void select(){
    try {
	 stmt = conn.createStatement();
	 rs = stmt.executeQuery( "SELECT * FROM TAG;" );
	 while ( rs.next() ) {
	 System.out.println(rs.getString("name"));
	 }
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
   }
   
   public static Models getInstance() {
      if(instance == null) {
         instance = new Models();
      }
      return instance;
   }
   
   public boolean emailCheck(String em){
   try {
	 stmt = conn.createStatement();
	 rs = stmt.executeQuery( "SELECT email FROM user;" );
	 while ( rs.next() ) {
	 String email = rs.getString("email");
	 if(email.equals(em)==true){
	 return false;
	 }
	 
	 }
	 return true;
	 
	 
	  
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
	  return false;
    }
   }
   
   public boolean neuerUser(User u){
  
   this.user = u;
   
   int geschlecht;
   if(user.getGeschlecht() != null){
   if(user.getGeschlecht().equals("weiblich")==true) {
   geschlecht = 0;
   }  else {
   geschlecht = 1;
   }
   } else {
	   return false;
   }
   String password = getHash(u.getPassword());
   if(emailCheck(user.getEmail())==true){
	try {
	 stmt = conn.createStatement();
	       String sql = "INSERT INTO USER (userid,vorname,nachname,bild,email,password,groesse,geschlecht) " +
                   "VALUES (null,'"+ u.getVorname()+"','" + u.getNachname()+"','null','"+ u.getEmail() +"','"+password+"',"+ u.getGroesse()+","+ geschlecht +");"; 
      stmt.executeUpdate(sql);
     stmt.close();
	 gewichtList();
	 bauchumfangList();
	 hueftenList();
	  brustumfangList();
	 return true;
     
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
	  return false;
    }
	} else{
	return false;
	}	
   }
   
   public void gewichtList(){
	   try {
	 stmt = conn.createStatement();
	 rs = stmt.executeQuery( "SELECT g.datum, g.umfang FROM user u, gewicht g where u.userid=g.user;" );
	 while ( rs.next() ) {
	 user.getGewicht().put(rs.getDate("datum"), rs.getDouble("umfang"));
	 }
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
   }
   public void bauchumfangList(){
	   try {
	 stmt = conn.createStatement();
	 rs = stmt.executeQuery( "SELECT g.datum, g.umfang FROM user u, bauchumfang g where u.userid=g.user;" );
	 while ( rs.next() ) {
	 user.getBauchumfang().put(rs.getDate("datum"), rs.getDouble("umfang"));
	 }
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
   }
   
    public void hueftenList(){
	   try {
	 stmt = conn.createStatement();
	 rs = stmt.executeQuery( "SELECT g.datum, g.umfang FROM user u, huefte g where u.userid=g.user;" );
	 while ( rs.next() ) {
	 user.getHueften().put(rs.getDate("datum"), rs.getDouble("umfang"));
	 }
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
   }
   
    public void armumfangList(){
	   try {
	 stmt = conn.createStatement();
	 rs = stmt.executeQuery( "SELECT g.datum, g.umfang FROM user u, armumfang g where u.userid=g.user;" );
	 while ( rs.next() ) {
	 user.getArmumfang().put(rs.getDate("datum"), rs.getDouble("umfang"));
	 }
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
   }
   
    public void brustumfangList(){
	   try {
	 stmt = conn.createStatement();
	 rs = stmt.executeQuery( "SELECT g.datum, g.umfang FROM user u, brustumfang g where u.userid=g.user;" );
	 while ( rs.next() ) {
	 user.getBrustumfang().put(rs.getDate("datum"), rs.getDouble("umfang"));
	 }
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
   }
   
   
   public SortedMap<Integer, Uebung> beine(){
   SortedMap<Integer, Uebung> uebungenBeine = new TreeMap<Integer, Uebung>();
   
   try {
	 stmt = conn.createStatement();
	 String name;
	 String bild;
	 String equipment;
	 String grad;
	 String muskel1;
	 String muskel2;
	 Muskel muskelgruppe = Muskel.beine;
	 rs = stmt.executeQuery( "SELECT u.id, u.name, u.bild, b.equipment, b.grad, b.muskel, b.muskel2 FROM uebung u, beschreibung b  where u.muskel='beine' and b.id = u.beschreibung;" );
	 while ( rs.next() ) {
	 name = rs.getString("name");
	 bild = rs.getString("bild");
	 bild = "assets//images//"+bild+".gif";
	 equipment = rs.getString("equipment");
	 grad = rs.getString("grad");
	 muskel1 = rs.getString("muskel");
	 muskel2 = rs.getString("muskel2");
	 int id = rs.getInt("id");
	 Uebung uebung = new Uebung(name, equipment, grad, muskel1, muskel2, bild, muskelgruppe);
	 uebungenBeine.put(id, uebung);
	 }
	
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
   
   return uebungenBeine;
   }
   
    public SortedMap<Integer, Uebung> bauch(){
   SortedMap<Integer, Uebung> uebungenBauch = new TreeMap<Integer, Uebung>();
   
   try {
	 stmt = conn.createStatement();
	 String name;
	 String bild;
	 String equipment;
	 String grad;
	 String muskel1;
	 String muskel2;
	 Muskel muskelgruppe = Muskel.bauch;
	 rs = stmt.executeQuery( "SELECT u.id, u.name, u.bild, b.equipment, b.grad, b.muskel, b.muskel2 FROM uebung u, beschreibung b  where u.muskel='bauch' and b.id = u.beschreibung;" );
	 while ( rs.next() ) {
	 name = rs.getString("name");
	 bild = rs.getString("bild");
	 bild = "assets//images//"+bild+".gif";
	 equipment = rs.getString("equipment");
	 grad = rs.getString("grad");
	 muskel1 = rs.getString("muskel");
	 muskel2 = rs.getString("muskel2");
	 int id = rs.getInt("id");
	 Uebung uebung = new Uebung(name, equipment, grad, muskel1, muskel2, bild, muskelgruppe);
	 uebungenBauch.put(id, uebung);
	 }
	
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
   
   return uebungenBauch;
   }
   
     public SortedMap<Integer, Uebung> arme(){
   SortedMap<Integer, Uebung> uebungenArme = new TreeMap<Integer, Uebung>();
   
   try {
	 stmt = conn.createStatement();
	 String name;
	 String bild;
	 String equipment;
	 String grad;
	 String muskel1;
	 String muskel2;
	 Muskel muskelgruppe = Muskel.arme;
	 rs = stmt.executeQuery( "SELECT u.id, u.name, u.bild, b.equipment, b.grad, b.muskel, b.muskel2 FROM uebung u, beschreibung b  where u.muskel='arme' and b.id = u.beschreibung;" );
	 while ( rs.next() ) {
	 name = rs.getString("name");
	 bild = rs.getString("bild");
	 bild = "assets//images//"+bild+".gif";
	 equipment = rs.getString("equipment");
	 grad = rs.getString("grad");
	 muskel1 = rs.getString("muskel");
	 muskel2 = rs.getString("muskel2");
	 int id = rs.getInt("id");
	 Uebung uebung = new Uebung(name, equipment, grad, muskel1, muskel2, bild, muskelgruppe);
	 uebungenArme.put(id, uebung);
	 }
	
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
   
   return uebungenArme;
   }
   
       public SortedMap<Integer, Uebung> brust(){
   SortedMap<Integer, Uebung> uebungenbrust = new TreeMap<Integer, Uebung>();
   
   try {
	 stmt = conn.createStatement();
	 String name;
	 String bild;
	 String equipment;
	 String grad;
	 String muskel1;
	 String muskel2;
	 Muskel muskelgruppe = Muskel.brust;
	 rs = stmt.executeQuery( "SELECT u.id, u.name, u.bild, b.equipment, b.grad, b.muskel, b.muskel2 FROM uebung u, beschreibung b  where u.muskel='brust' and b.id = u.beschreibung;" );
	 while ( rs.next() ) {
	 name = rs.getString("name");
	 bild = rs.getString("bild");
	 bild = "assets//images//"+bild+".gif";
	 equipment = rs.getString("equipment");
	 grad = rs.getString("grad");
	 muskel1 = rs.getString("muskel");
	 muskel2 = rs.getString("muskel2");
	 int id = rs.getInt("id");
	 Uebung uebung = new Uebung(name, equipment, grad, muskel1, muskel2, bild, muskelgruppe);
	 uebungenbrust.put(id, uebung);
	 }
	
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
   
   return uebungenbrust;
   }
   
       public SortedMap<Integer, Uebung> ruecken(){
   SortedMap<Integer, Uebung> uebungenruecken = new TreeMap<Integer, Uebung>();
   
   try {
	 stmt = conn.createStatement();
	 String name;
	 String bild;
	 String equipment;
	 String grad;
	 String muskel1;
	 String muskel2;
	 Muskel muskelgruppe = Muskel.ruecken;
	 rs = stmt.executeQuery( "SELECT u.id, u.name, u.bild, b.equipment, b.grad, b.muskel, b.muskel2 FROM uebung u, beschreibung b  where u.muskel='ruecken' and b.id = u.beschreibung;" );
	 while ( rs.next() ) {
	 name = rs.getString("name");
	 bild = rs.getString("bild");
	 bild = "assets//images//"+bild+".gif";
	 equipment = rs.getString("equipment");
	 grad = rs.getString("grad");
	 muskel1 = rs.getString("muskel");
	 muskel2 = rs.getString("muskel2");
	 int id = rs.getInt("id");
	 Uebung uebung = new Uebung(name, equipment, grad, muskel1, muskel2, bild, muskelgruppe);
	 uebungenruecken.put(id, uebung);
	 }
	
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
   
   return uebungenruecken;
   }
   
        public SortedMap<Integer, Uebung> schultern(){
   SortedMap<Integer, Uebung> uebungenschultern = new TreeMap<Integer, Uebung>();
   
   try {
	 stmt = conn.createStatement();
	 String name;
	 String bild;
	 String equipment;
	 String grad;
	 String muskel1;
	 String muskel2;
	 Muskel muskelgruppe = Muskel.schultern;
	 rs = stmt.executeQuery( "SELECT u.id, u.name, u.bild, b.equipment, b.grad, b.muskel, b.muskel2 FROM uebung u, beschreibung b  where u.muskel='schultern' and b.id = u.beschreibung;" );
	 while ( rs.next() ) {
	 name = rs.getString("name");
	 bild = rs.getString("bild");
	 bild = "assets//images//"+bild+".gif";
	 equipment = rs.getString("equipment");
	 grad = rs.getString("grad");
	 muskel1 = rs.getString("muskel");
	 muskel2 = rs.getString("muskel2");
	 int id = rs.getInt("id");
	 Uebung uebung = new Uebung(name, equipment, grad, muskel1, muskel2, bild, muskelgruppe);
	 uebungenschultern.put(id, uebung);
	 }
	
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
   
   return uebungenschultern;
   }
   
   public User aktuellUser(){
   return user;
   }
   
   //TEST
 /*  public void plan(){
	Uebung u = new Uebung();
	AusgewaehlteUebung a = new AusgewaehlteUebung(u, 3);
	AusgewaehlteUebung b = new AusgewaehlteUebung(u, 2);
	TagPlan t = new TagPlan();
	t.tag = Tag.Mittwoch;
	t.uebungen.add(a);
	t.uebungen.add(b);
	Map<Tag, TagPlan> uebungen = new HashMap<Tag, TagPlan>();
	uebungen.put(t.tag, t);
	Plan p = new Plan(1, "PlanTest", uebungen);
	user.setPlans(p);
   }*/

   public static String getHash(String p) 
{
  String password = p;
        String algorithm = "SHA";

        byte[] plainText = password.getBytes();
 
        MessageDigest md = null;
 
        try { 
            md = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        md.reset(); 
        md.update(plainText);
        byte[] encodedPassword = md.digest();
 
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < encodedPassword.length; i++) {
			
            if ((encodedPassword[i] & 0xff) < 0x10) {
				
                sb.append("0");
            }
 
            sb.append(Long.toString(encodedPassword[i] & 0xff, 16));
        }
 System.out.println(sb.toString());
    return sb.toString();
}
   }

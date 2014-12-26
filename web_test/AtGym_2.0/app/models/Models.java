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
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	 rs = stmt.executeQuery( "SELECT * FROM user where email ='" + em + "' and password='"+p+"';" );
	 while ( rs.next() ) {
		 String email = rs.getString("email");
		 String passwort = rs.getString("password");
	 if(email.equals(em)==true && passwort.equals(p)==true ){
		
		 this.user = new User(rs.getString("vorname"), rs.getString("nachname"), email, password, rs.getInt("groesse"), rs.getInt("geschlecht"));
		  user.setId(selectId(email));
		 gewichtList();
		 bauchumfangList();
		 armumfangList();
		 hueftenList();
		 brustumfangList();
		 plaene();
		// stmt.close();
		return true;
	 }
	
	 }
	  return false;
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
	   return false;
    }finally {
    try { if (rs != null) rs.close(); } catch (Exception e) {};
    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
    //try { if (conn != null) conn.close(); } catch (Exception e) {};
}
   }
   
   public int selectId(String email){
	   int id = -1;
    try {
	 stmt = conn.createStatement();
	 rs = stmt.executeQuery( "SELECT userid FROM user where email='" + email +"';" );
	 while ( rs.next() ) {
		id = rs.getInt("userid");
	 }
	 // stmt.close();
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
	 
    }finally {
    try { if (rs != null) rs.close(); } catch (Exception e) {};
    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
   // try { if (conn != null) conn.close(); } catch (Exception e) {};
}
	return id;
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
	 rs = stmt.executeQuery( "SELECT email FROM user where email='"+em+"';" );
		 while ( rs.next() ) {
		 String email = rs.getString("email");
			if(email.equals(em)==true){
			// stmt.close();	
			 return true;
			 }
		 
		 }
	//  stmt.close();
	 return false;  
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
	  return false;
    }finally {
    try { if (rs != null) rs.close(); } catch (Exception e) {};
    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
   // try { if (conn != null) conn.close(); } catch (Exception e) {};
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
   if(emailCheck(user.getEmail())==false){
	try {
	 stmt = conn.createStatement();
	       String sql = "INSERT INTO USER (userid,vorname,nachname,bild,email,password,groesse,geschlecht) " +
                   "VALUES (null,'"+ u.getVorname()+"','" + u.getNachname()+"','null','"+ u.getEmail() +"','"+password+"',"+ u.getGroesse()+","+ geschlecht +");"; 
      stmt.executeUpdate(sql);
	//  stmt.close();
	  
		 user.setId(selectId(user.getEmail()));
		 gewichtList();
		 bauchumfangList();
		 hueftenList();
		 brustumfangList();
		 plaene();
	 return true;
     
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
	  return false;
    }finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		//	try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
	} else{
	return false;
	}	
   }
   
   public void gewichtCheck(){
	   if(user.getGewicht() != null){
		   DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		   try {
				stmt = conn.createStatement();
				String sql = "INSERT INTO gewicht (id,umfang,datum, user) " +
                   "VALUES (null,"+ user.getGewicht().getGewicht()+",'" + dateFormat.format(user.getGewicht().getDatum())+"',"+ user.getId() +");"; 
				stmt.executeUpdate(sql);
  //   stmt.close();
	 
	 
     
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
	  
	   }finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		//	try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
	}
 }
   
   
   public void gewichtList(){
	   try {
			 stmt = conn.createStatement();
			 rs = stmt.executeQuery( "SELECT g.id, g.datum, g.umfang FROM user u, gewicht g where g.user=" +user.getId()+" and u.userid=g.user;" );
			 while ( rs.next() ) {
				user.getGewichtList().put(rs.getInt("id"), new Gewicht(rs.getDouble("umfang"), rs.getString("datum")));
			 }
	 
    // stmt.close();
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		//	try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
   }
   
   public void bauchumfangCheck(){
	   if(user.getBauchumfang() != null){
		   DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		   try {
				stmt = conn.createStatement();
				String sql = "INSERT INTO bauchumfang (id,umfang,datum, user) " +
                   "VALUES (null,"+ user.getBauchumfang().getUmfang()+",'" + dateFormat.format(user.getBauchumfang().getDatum())+"',"+ user.getId() +");"; 
      stmt.executeUpdate(sql);
     //stmt.close();
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
	  
    } finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
			//try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
	   }
   }
   
   public void bauchumfangList(){
	  try {
	 stmt = conn.createStatement();
	 rs = stmt.executeQuery( "SELECT g.id, g.datum, g.umfang FROM user u, bauchumfang g where g.user=" +user.getId()+" and u.userid=g.user;" );
	 while ( rs.next() ) {
		user.getBauchumfangList().put(rs.getInt("id"), new Bauchumfang(rs.getDouble("umfang"), rs.getString("datum")));
	 }
	 // stmt.close();
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }finally {
		try { if (rs != null) rs.close(); } catch (Exception e) {};
		try { if (stmt != null) stmt.close(); } catch (Exception e) {};
	//	try { if (conn != null) conn.close(); } catch (Exception e) {};
	}
   }
   
   
    public void hueftenumfangCheck(){
	   if(user.getHueftenumfang() != null){
		   DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		   try {
				stmt = conn.createStatement();
				String sql = "INSERT INTO hueftenumfang (id,umfang,datum, user) " +
                   "VALUES (null,"+ user.getHueftenumfang().getUmfang()+",'" + dateFormat.format(user.getHueftenumfang().getDatum())+"',"+ user.getId() +");"; 
      stmt.executeUpdate(sql);
    // stmt.close();
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
	  
    } finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		//	try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
	   }
   }
   
    public void hueftenList(){
	    try {
	 stmt = conn.createStatement();
	 rs = stmt.executeQuery( "SELECT g.id, g.datum, g.umfang FROM user u, hueftenumfang g where g.user=" +user.getId()+" and u.userid=g.user;" );
	 while ( rs.next() ) {
		user.getHueftenumfangList().put(rs.getInt("id"), new Hueftenumfang(rs.getDouble("umfang"), rs.getString("datum")));
	 }
	 // stmt.close();
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }finally {
		try { if (rs != null) rs.close(); } catch (Exception e) {};
		try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		//try { if (conn != null) conn.close(); } catch (Exception e) {};
	}
   }
   
    public void armumfangList(){
	   try {
	 stmt = conn.createStatement();
	 rs = stmt.executeQuery( "SELECT g.id, g.datum, g.umfang FROM user u, armumfang g where g.user=" +user.getId()+" and u.userid=g.user;" );
	 while ( rs.next() ) {
		
		user.getArmumfangList().put(rs.getInt("id"), new Armumfang(rs.getDouble("umfang"), rs.getString("datum")));
	 }
	 // stmt.close();
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    } finally {
		try { if (rs != null) rs.close(); } catch (Exception e) {};
		try { if (stmt != null) stmt.close(); } catch (Exception e) {};
	//	try { if (conn != null) conn.close(); } catch (Exception e) {};
	}
   }
   
   public void armumfangCheck(){
	   if(user.getArmumfang() != null){
		   DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		   try {
				stmt = conn.createStatement();
				String sql = "INSERT INTO armumfang (id,umfang,datum, user) " +
                   "VALUES (null,"+ user.getArmumfang().getUmfang()+",'" + dateFormat.format(user.getArmumfang().getDatum())+"',"+ user.getId() +");"; 
      stmt.executeUpdate(sql);
   //  stmt.close();
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
	  
    } finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		//	try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
	   }
   }
   
    public void brustumfangList(){
	   try {
	 stmt = conn.createStatement();
	 rs = stmt.executeQuery( "SELECT g.id, g.datum, g.umfang FROM user u, brustumfang g where g.user=" +user.getId()+" and u.userid=g.user;" );
	 while ( rs.next() ) {
		
		// Gewicht g = new Gewicht(rs.getDouble("umfang"), rs.getString("datum"));
		user.getBrustumfangList().put(rs.getInt("id"), new Brustumfang(rs.getDouble("umfang"), rs.getString("datum")));
	 }
	 // stmt.close();
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    } finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		//	try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
   }
   
   public void brustumfangCheck(){
	   if(user.getBrustumfang() != null){
		   DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		   try {
				stmt = conn.createStatement();
				String sql = "INSERT INTO brustumfang (id,umfang,datum, user) " +
                   "VALUES (null,"+ user.getBrustumfang().getUmfang()+",'" + dateFormat.format(user.getBrustumfang().getDatum())+"',"+ user.getId() +");"; 
      stmt.executeUpdate(sql);
   //  stmt.close();
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
	  
    } finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		//	try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
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
	 Uebung uebung = new Uebung(id, name, equipment, grad, muskel1, muskel2, bild, muskelgruppe);
	 uebungenBeine.put(id, uebung);
	 }
	//  stmt.close();
	
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    } finally {
		try { if (rs != null) rs.close(); } catch (Exception e) {};
		try { if (stmt != null) stmt.close(); } catch (Exception e) {};
	//	try { if (conn != null) conn.close(); } catch (Exception e) {};
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
	 Uebung uebung = new Uebung(id, name, equipment, grad, muskel1, muskel2, bild, muskelgruppe);
	 uebungenBauch.put(id, uebung);
	 }
	// stmt.close();
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    } finally {
		try { if (rs != null) rs.close(); } catch (Exception e) {};
		try { if (stmt != null) stmt.close(); } catch (Exception e) {};
	//	try { if (conn != null) conn.close(); } catch (Exception e) {};
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
	 Uebung uebung = new Uebung(id, name, equipment, grad, muskel1, muskel2, bild, muskelgruppe);
	 uebungenArme.put(id, uebung);
	 }
	// stmt.close();
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    } finally {
		try { if (rs != null) rs.close(); } catch (Exception e) {};
		try { if (stmt != null) stmt.close(); } catch (Exception e) {};
	//	try { if (conn != null) conn.close(); } catch (Exception e) {};
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
	 Uebung uebung = new Uebung(id, name, equipment, grad, muskel1, muskel2, bild, muskelgruppe);
	 uebungenbrust.put(id, uebung);
	 }
	// stmt.close();
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }finally {
		try { if (rs != null) rs.close(); } catch (Exception e) {};
		try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		//try { if (conn != null) conn.close(); } catch (Exception e) {};
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
	 Uebung uebung = new Uebung(id, name, equipment, grad, muskel1, muskel2, bild, muskelgruppe);
	 uebungenruecken.put(id, uebung);
	 }
	// stmt.close();
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    } finally {
		try { if (rs != null) rs.close(); } catch (Exception e) {};
		try { if (stmt != null) stmt.close(); } catch (Exception e) {};
	//	try { if (conn != null) conn.close(); } catch (Exception e) {};
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
	 Uebung uebung = new Uebung(id, name, equipment, grad, muskel1, muskel2, bild, muskelgruppe);
	 uebungenschultern.put(id, uebung);
	 }
	// stmt.close();
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }finally {
		try { if (rs != null) rs.close(); } catch (Exception e) {};
		try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		//try { if (conn != null) conn.close(); } catch (Exception e) {};
	}
   
   return uebungenschultern;
   }
   
   public User aktuellUser(){
   return user;
   }
   
   public void plaene(){
	  try {
	 stmt = conn.createStatement();
	 String name;
	 String bild;
	 String equipment;
	 String grad;
	 String muskel1;
	 String muskel2;
	 int id; 
	 Muskel muskelgruppe;
	 rs = stmt.executeQuery( "SELECT u.id as uid, u.name as uname, u.bild as bild, u.muskel as muskelgruppe, b.equipment as bequi, b.grad as grad, b.muskel as bm1, b.muskel2 as bm2, p.name as pname, p.id as pid, t.name as tag, count() as saetze FROM uebung u, beschreibung b, ausgewaehlteuebung a, plan p, Tag t where b.id=u.beschreibung and a.plan=p.id and u.id=a.uebung and a.tag=t.name and p.user="+user.getId()+" group by p.id, u.id, t.name;" );
	 while ( rs.next() ) {
	 name = rs.getString("uname");
	 bild = rs.getString("bild");
	 bild = "assets//images//"+bild+".gif";
	 equipment = rs.getString("bequi");
	 grad = rs.getString("grad");
	 muskel1 = rs.getString("bm1");
	 muskel2 = rs.getString("bm2");
	 id = rs.getInt("uid");
	 String m = rs.getString("muskelgruppe");
	 muskelgruppe=Muskel.valueOf(m);
	 
	 Uebung uebung = new Uebung(id, name, equipment, grad, muskel1, muskel2, bild, muskelgruppe);
		
		int saetze = rs.getInt("saetze");
	 AusgewaehlteUebung ausgewaehlt = new AusgewaehlteUebung(uebung, saetze);
		int planId = rs.getInt("pid");
			String planName = rs.getString("pname");
     String t = rs.getString("tag");
	 Tag tag=Tag.valueOf(t);
	 
	 if(user.getPlans().containsKey(planName) == true){
		 if(user.getPlans().get(planName).getUebungen().containsKey(tag) == true){
			 user.getPlans().get(planName).getUebungen().get(tag).getUebungen().add(ausgewaehlt);
		 } else{
			 TagPlan tagPlan = new TagPlan();
			tagPlan.tag = tag;
			tagPlan.getUebungen().add(ausgewaehlt);
			
			user.getPlans().get(planName).getUebungen().put(tag, tagPlan);
		 }
		 
	 } else{
		 TagPlan tagPlan = new TagPlan();
		tagPlan.tag = tag;
		tagPlan.getUebungen().add(ausgewaehlt);
			
		Map<Tag, TagPlan> uebungen = new HashMap<Tag, TagPlan>();
		uebungen.put(tagPlan.tag, tagPlan);
		Plan p = new Plan(planId, planName, uebungen);
		user.setPlans(p);
	 }
	 
	 
	  
	 }
	// stmt.close();
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }finally {
		try { if (rs != null) rs.close(); } catch (Exception e) {};
		try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		//try { if (conn != null) conn.close(); } catch (Exception e) {};
	}
	    
   }
   
 public void planHinzufuegen(int id, int satz, String tag, String plan){
			
	 String name;
	 String bild;
	 String equipment;
	 String grad;
	 String muskel1;
	 String muskel2;
	 String muskel;
	 Muskel muskelgruppe;
	  try {
	 stmt = conn.createStatement();
	 rs = stmt.executeQuery( "SELECT u.name, u.bild, b.equipment, b.grad, b.muskel, b.muskel2, u.muskel as muskelgruppe FROM uebung u, beschreibung b  where b.id = u.beschreibung and u.id="+id+";" );
	 while ( rs.next() ) {
	 name = rs.getString("name");
	 bild = rs.getString("bild");
	 bild = "assets//images//"+bild+".gif";
	 
	 equipment = rs.getString("equipment");
	 grad = rs.getString("grad");
	 muskel1 = rs.getString("muskel");
	 muskel2 = rs.getString("muskel2");
	 muskelgruppe=Muskel.valueOf(rs.getString("muskelgruppe"));
	
	
	 Uebung uebung = new Uebung(id, name, equipment, grad, muskel1, muskel2, bild, muskelgruppe);
	 AusgewaehlteUebung ausgewaehlt = new AusgewaehlteUebung(uebung, satz);
	
	
	if(user.getPlans().containsKey(plan) == true){
		
		if(user.getPlans().get(plan).getUebungen().containsKey(Tag.valueOf(tag)) == true){
			int planId = user.getPlans().get(plan).getId();
			user.getPlans().get(plan).getUebungen().get(Tag.valueOf(tag)).getUebungen().add(ausgewaehlt);
			 
				
				 for(int i = 0; i < satz; i++){
					String sql = "INSERT INTO satz (id) " +
					   "VALUES (null);"; 
					stmt.executeUpdate(sql);
					int satzId=-1;
					rs = stmt.executeQuery( "select max(id) as id from satz;" );
						 while ( rs.next() ) {
							 satzId = rs.getInt("id");
						 }
				if(satzId != -1){
					sql = "INSERT INTO ausgewaehlteuebung (plan, uebung, tag, satz ) " +
					   "VALUES ("+planId+","+id+",'"+tag+"',"+satzId+");"; 
					stmt.executeUpdate(sql);
					}
				}
			
		} else{
			TagPlan tagPlan = new TagPlan();
			tagPlan.tag = Tag.valueOf(tag);
			tagPlan.getUebungen().add(ausgewaehlt);
			user.getPlans().get(plan).getUebungen().put(Tag.valueOf(tag), tagPlan);
			 int planId = user.getPlans().get(plan).getId();
			
				 for(int i = 0; i < satz; i++){
					String sql = "INSERT INTO satz (id) " +
					   "VALUES (null);"; 
					stmt.executeUpdate(sql);
					int satzId=-1;
					rs = stmt.executeQuery( "select max(id) as id from satz;" );
						 while ( rs.next() ) {
							 satzId = rs.getInt("id");
						 }
				if(satzId != -1){
					sql = "INSERT INTO ausgewaehlteuebung (plan, uebung, tag, satz ) " +
					   "VALUES ("+planId+","+id+",'"+tag+"',"+satzId+");"; 
					stmt.executeUpdate(sql);
					}
				}
		}
	} else{
		
		TagPlan tagPlan = new TagPlan();
		tagPlan.tag = Tag.valueOf(tag);
		tagPlan.getUebungen().add(ausgewaehlt);
		Map<Tag, TagPlan> uebungen = new HashMap<Tag, TagPlan>();
		uebungen.put(tagPlan.tag, tagPlan);
		Plan p = new Plan(plan, uebungen);
		user.setPlans(p);
		 
		String sql = "INSERT INTO plan(id,name,user) " +
                   "VALUES (null,'"+ plan+"'," + user.getId()+");"; 
				   
		stmt.executeUpdate(sql);
		
		int planId=-1;
		rs = stmt.executeQuery("select max(id) as id from plan;" );
			 while ( rs.next() ) {
				 
				 planId = rs.getInt("id");
			 }
			 
		if(planId != -1){
			p.setId(planId);
				for(int i = 0; i < satz; i++){
					sql = "INSERT INTO satz (id) " +
					   "VALUES (null);"; 
					stmt.executeUpdate(sql);
					int satzId=-1;
					rs = stmt.executeQuery( "select max(id) as id from satz;" );
						 while ( rs.next() ) {
							 satzId = rs.getInt("id");
						 }
				if(satzId != -1){
					sql = "INSERT INTO ausgewaehlteuebung (plan, uebung, tag, satz ) " +
					   "VALUES ("+planId+","+id+",'"+tag+"',"+satzId+");"; 
					stmt.executeUpdate(sql);
					}
				}
		}
		
	}
	 }
	// stmt.close();
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    } finally {
		try { if (rs != null) rs.close(); } catch (Exception e) {};
		try { if (stmt != null) stmt.close(); } catch (Exception e) {};
	//	try { if (conn != null) conn.close(); } catch (Exception e) {};
	}
	
  }
 
 
 
 

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

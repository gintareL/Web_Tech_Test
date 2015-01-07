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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Models extends Model{
	Connection conn = null;
	 Statement stmt = null;
	 Statement stmt1 = null;
	 ResultSet rs = null;	
	 ResultSet rs1 = null;	
	//static int index=100; 	
	private static Models instance = null;
	//private static User user;
	private static Map<String, User>alleUser = new HashMap<String, User>();
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
			
		 // this.user = new User(rs.getString("vorname"), rs.getString("nachname"), email, p, rs.getInt("groesse"), rs.getInt("geschlecht"),rs.getString("bild"));
		 // user.setId(selectId(email));
		  User userToList = new User(rs.getString("vorname"), rs.getString("nachname"), email, p, rs.getInt("groesse"), rs.getInt("geschlecht"),rs.getString("bild"));
		  userToList.setId(selectId(email));
		  
		 
		 gewichtList(userToList);
		 bauchumfangList(userToList);
		 armumfangList(userToList);
		 hueftenList(userToList);
		 brustumfangList(userToList); 
		 routineAuslesen(userToList);
		 plaene(userToList);
		 alleUser.put(em, userToList);
	
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
   
   public boolean neuerUser(User userNeu){
  // User userNeu = u;
 // this.user = u;
   
   int geschlecht;
   if(userNeu.getGeschlecht() != null){
   if(userNeu.getGeschlecht().equals("weiblich")==true) {
   geschlecht = 0;
   userNeu.setBild("assets//images//default_bild_w.jpg");
    }  else {
   geschlecht = 1;
     userNeu.setBild("assets//images//default_bild_m.jpg");
   }
   } else {
	   return false;
   }
   String password = getHash(userNeu.getPassword());
   if(emailCheck(userNeu.getEmail())==false){
	try {
	 stmt = conn.createStatement();
	       String sql = "INSERT INTO USER (userid,vorname,nachname,bild,email,password,groesse,geschlecht) " +
                   "VALUES (null,'"+ userNeu.getVorname()+"','" + userNeu.getNachname()+"','"+userNeu.getBild()+"','"+ userNeu.getEmail() +"','"+password+"',"+ userNeu.getGroesse()+","+ geschlecht +");"; 
      stmt.executeUpdate(sql);
	//  stmt.close();
	  
		 userNeu.setId(selectId(userNeu.getEmail()));
		 gewichtList(userNeu);
		 bauchumfangList(userNeu);
		 hueftenList(userNeu);
		 brustumfangList(userNeu);
		 plaene(userNeu);
		 routineAuslesen(userNeu);
	alleUser.put(userNeu.getEmail(), userNeu);
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
   
   public void imageSave(User userp, String file){
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd-hh-mm-ss");
		String datum = dateFormat.format(date);
		String bild = null;
		String bildname = null; 
	   try {
			File quellDatei = new File(file);
			bildname = "bild"+userp.getPassword()+datum+".jpg";
			String bildfile = ".\\public\\images\\"+bildname;
			
			bild = "assets//images//"+bildname;
			File zielDatei = new File(bildfile);
			quellDatei.renameTo(zielDatei);
			userp.setBild(bild);
			
			} catch (Exception e) {
			e.printStackTrace();
			}
	
			try {	
				
				stmt = conn.createStatement();
				System.out.println(userp.getId());
				String sql = "update user set bild='"+bild+"' where userid="+userp.getId()+";"; 
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
   
   public void gewichtCheck(User userp){
	   if(userp.getGewicht() != null){
		   DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		   try {
				stmt = conn.createStatement();
				String sql = "INSERT INTO gewicht (id,umfang,datum, user) " +
                   "VALUES (null,"+ userp.getGewicht().getGewicht()+",'" + dateFormat.format(userp.getGewicht().getDatum())+"',"+ userp.getId() +");"; 
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
   
   
   public void gewichtList(User u){
	   try {
			 stmt = conn.createStatement();
			 rs = stmt.executeQuery( "SELECT g.id, g.datum, g.umfang FROM user u, gewicht g where g.user=" +u.getId()+" and u.userid=g.user;" );
			 while ( rs.next() ) {
				u.getGewichtList().put(rs.getInt("id"), new Gewicht(rs.getDouble("umfang"), rs.getString("datum")));
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
   
   public void bauchumfangCheck(User userp){
	   if(userp.getBauchumfang() != null){
		   DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		   try {
				stmt = conn.createStatement();
				String sql = "INSERT INTO bauchumfang (id,umfang,datum, user) " +
                   "VALUES (null,"+ userp.getBauchumfang().getUmfang()+",'" + dateFormat.format(userp.getBauchumfang().getDatum())+"',"+ userp.getId() +");"; 
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
   
   public void bauchumfangList(User u){
	  try {
	 stmt = conn.createStatement();
	 rs = stmt.executeQuery( "SELECT g.id, g.datum, g.umfang FROM user u, bauchumfang g where g.user=" +u.getId()+" and u.userid=g.user;" );
	 while ( rs.next() ) {
		u.getBauchumfangList().put(rs.getInt("id"), new Bauchumfang(rs.getDouble("umfang"), rs.getString("datum")));
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
   
   
    public void hueftenumfangCheck(User userp){
	   if(userp.getHueftenumfang() != null){
		   DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		   try {
				stmt = conn.createStatement();
				String sql = "INSERT INTO hueftenumfang (id,umfang,datum, user) " +
                   "VALUES (null,"+ userp.getHueftenumfang().getUmfang()+",'" + dateFormat.format(userp.getHueftenumfang().getDatum())+"',"+ userp.getId() +");"; 
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
   
    public void hueftenList(User u){
	    try {
	 stmt = conn.createStatement();
	 rs = stmt.executeQuery( "SELECT g.id, g.datum, g.umfang FROM user u, hueftenumfang g where g.user=" +u.getId()+" and u.userid=g.user;" );
	 while ( rs.next() ) {
		u.getHueftenumfangList().put(rs.getInt("id"), new Hueftenumfang(rs.getDouble("umfang"), rs.getString("datum")));
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
   
    public void armumfangList(User u){
	   try {
	 stmt = conn.createStatement();
	 rs = stmt.executeQuery( "SELECT g.id, g.datum, g.umfang FROM user u, armumfang g where g.user=" +u.getId()+" and u.userid=g.user;" );
	 while ( rs.next() ) {
		
		u.getArmumfangList().put(rs.getInt("id"), new Armumfang(rs.getDouble("umfang"), rs.getString("datum")));
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
   
   public void armumfangCheck(User userp){
	   if(userp.getArmumfang() != null){
		   DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		   try {
				stmt = conn.createStatement();
				String sql = "INSERT INTO armumfang (id,umfang,datum, user) " +
                   "VALUES (null,"+ userp.getArmumfang().getUmfang()+",'" + dateFormat.format(userp.getArmumfang().getDatum())+"',"+ userp.getId() +");"; 
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
   
    public void brustumfangList(User u){
	   try {
	 stmt = conn.createStatement();
	 rs = stmt.executeQuery( "SELECT g.id, g.datum, g.umfang FROM user u, brustumfang g where g.user=" +u.getId()+" and u.userid=g.user;" );
	 while ( rs.next() ) {
		
		// Gewicht g = new Gewicht(rs.getDouble("umfang"), rs.getString("datum"));
		u.getBrustumfangList().put(rs.getInt("id"), new Brustumfang(rs.getDouble("umfang"), rs.getString("datum")));
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
   
   public void brustumfangCheck(User userp){
	   if(userp.getBrustumfang() != null){
		   DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		   try {
				stmt = conn.createStatement();
				String sql = "INSERT INTO brustumfang (id,umfang,datum, user) " +
                   "VALUES (null,"+ userp.getBrustumfang().getUmfang()+",'" + dateFormat.format(userp.getBrustumfang().getDatum())+"',"+ userp.getId() +");"; 
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
	 rs = stmt.executeQuery( "SELECT u.like, u.dislike, u.id, u.name, u.bild, b.equipment, b.grad, b.muskel, b.muskel2 FROM uebung u, beschreibung b  where u.muskel='beine' and b.id = u.beschreibung;" );
	 while ( rs.next() ) {
	 name = rs.getString("name");
	 bild = rs.getString("bild");
	 bild = "assets//images//"+bild+".gif";
	 equipment = rs.getString("equipment");
	 grad = rs.getString("grad");
	 muskel1 = rs.getString("muskel");
	 muskel2 = rs.getString("muskel2");
	 int id = rs.getInt("id");
	 int like = rs.getInt("like");
	 int dislike = rs.getInt("dislike");
	 Uebung uebung = new Uebung(id, name, equipment, grad, muskel1, muskel2, bild, muskelgruppe, like, dislike);
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
	 rs = stmt.executeQuery( "SELECT u.like, u.dislike, u.id, u.name, u.bild, b.equipment, b.grad, b.muskel, b.muskel2 FROM uebung u, beschreibung b  where u.muskel='bauch' and b.id = u.beschreibung;" );
	 while ( rs.next() ) {
	 name = rs.getString("name");
	 bild = rs.getString("bild");
	 bild = "assets//images//"+bild+".gif";
	 equipment = rs.getString("equipment");
	 grad = rs.getString("grad");
	 muskel1 = rs.getString("muskel");
	 muskel2 = rs.getString("muskel2");
	  int like = rs.getInt("like");
	 int dislike = rs.getInt("dislike");
	 int id = rs.getInt("id");
	 Uebung uebung = new Uebung(id, name, equipment, grad, muskel1, muskel2, bild, muskelgruppe, like, dislike);
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
	 rs = stmt.executeQuery( "SELECT u.like, u.dislike, u.id, u.name, u.bild, b.equipment, b.grad, b.muskel, b.muskel2 FROM uebung u, beschreibung b  where u.muskel='arme' and b.id = u.beschreibung;" );
	 while ( rs.next() ) {
	 name = rs.getString("name");
	 bild = rs.getString("bild");
	 bild = "assets//images//"+bild+".gif";
	 equipment = rs.getString("equipment");
	 grad = rs.getString("grad");
	 muskel1 = rs.getString("muskel");
	 muskel2 = rs.getString("muskel2");
	 int id = rs.getInt("id");
	  int like = rs.getInt("like");
	 int dislike = rs.getInt("dislike");
	 Uebung uebung = new Uebung(id, name, equipment, grad, muskel1, muskel2, bild, muskelgruppe, like, dislike);
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
	 rs = stmt.executeQuery( "SELECT u.like, u.dislike, u.id, u.name, u.bild, b.equipment, b.grad, b.muskel, b.muskel2 FROM uebung u, beschreibung b  where u.muskel='brust' and b.id = u.beschreibung;" );
	 while ( rs.next() ) {
	 name = rs.getString("name");
	 bild = rs.getString("bild");
	 bild = "assets//images//"+bild+".gif";
	 equipment = rs.getString("equipment");
	 grad = rs.getString("grad");
	 muskel1 = rs.getString("muskel");
	 muskel2 = rs.getString("muskel2");
	 int id = rs.getInt("id");
	  int like = rs.getInt("like");
	 int dislike = rs.getInt("dislike");
	 Uebung uebung = new Uebung(id, name, equipment, grad, muskel1, muskel2, bild, muskelgruppe, like, dislike);
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
	 rs = stmt.executeQuery( "SELECT u.like, u.dislike, u.id, u.name, u.bild, b.equipment, b.grad, b.muskel, b.muskel2 FROM uebung u, beschreibung b  where u.muskel='ruecken' and b.id = u.beschreibung;" );
	 while ( rs.next() ) {
	 name = rs.getString("name");
	 bild = rs.getString("bild");
	 bild = "assets//images//"+bild+".gif";
	 equipment = rs.getString("equipment");
	 grad = rs.getString("grad");
	 muskel1 = rs.getString("muskel");
	 muskel2 = rs.getString("muskel2");
	 int id = rs.getInt("id");
	  int like = rs.getInt("like");
	 int dislike = rs.getInt("dislike");
	 Uebung uebung = new Uebung(id, name, equipment, grad, muskel1, muskel2, bild, muskelgruppe, like, dislike);
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
	 rs = stmt.executeQuery( "SELECT u.like, u.dislike, u.id, u.name, u.bild, b.equipment, b.grad, b.muskel, b.muskel2 FROM uebung u, beschreibung b  where u.muskel='schultern' and b.id = u.beschreibung;" );
	 while ( rs.next() ) {
	 name = rs.getString("name");
	 bild = rs.getString("bild");
	 bild = "assets//images//"+bild+".gif";
	 equipment = rs.getString("equipment");
	 grad = rs.getString("grad");
	 muskel1 = rs.getString("muskel");
	 muskel2 = rs.getString("muskel2");
	 int id = rs.getInt("id");
	  int like = rs.getInt("like");
	 int dislike = rs.getInt("dislike");
	 Uebung uebung = new Uebung(id, name, equipment, grad, muskel1, muskel2, bild, muskelgruppe, like, dislike);
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
   
  /* public User aktuellUser(){
   return user;
   }*/
   
    public User aktuellUserList(String email){
		
		
   return alleUser.get(email);
   }
   
   public void plaene(User u){
	 
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
	 rs = stmt.executeQuery( "SELECT u.id as uid, u.name as uname, u.bild as bild, u.muskel as muskelgruppe, b.equipment as bequi, b.grad as grad, b.muskel as bm1, b.muskel2 as bm2, p.name as pname, p.id as pid, t.name as tag, a.satz as saetze FROM uebung u, beschreibung b, ausgewaehlteuebung a, plan p, Tag t where b.id=u.beschreibung and a.plan=p.id and u.id=a.uebung and a.tag=t.name and p.user="+u.getId()+";" );
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
	 
	 if(u.getPlans().containsKey(planName) == true){
		 
		 if(u.getPlans().get(planName).getUebungen().containsKey(tag) == true){
			 u.getPlans().get(planName).getUebungen().get(tag).getUebungen().add(ausgewaehlt);
		 } else{
			 TagPlan tagPlan = new TagPlan(); 
			tagPlan.tag = tag;
			tagPlan.getUebungen().add(ausgewaehlt);
			
			u.getPlans().get(planName).getUebungen().put(tag, tagPlan);
		 }
		 
		 
	 } else{
		 TagPlan tagPlan = new TagPlan();
		tagPlan.tag = tag;
		tagPlan.getUebungen().add(ausgewaehlt);
			
		Map<Tag, TagPlan> uebungen = new HashMap<Tag, TagPlan>();
		uebungen.put(tagPlan.tag, tagPlan);
		Plan p = new Plan(planId, planName, uebungen);
		u.setPlans(p);
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
   public void routineAuslesen(User u){
	   try {
				stmt = conn.createStatement();
				stmt1 = conn.createStatement();
				rs = stmt.executeQuery( "select r.datum as datum, r.plan as plan, r.uebung as uebung, r.tag as tag, count() as anzahl from routine r, plan p where p.user="+u.getId()+" and p.id=r.plan group by r.plan, r.uebung, r.tag, r.datum;" );
						 while ( rs.next() ) {
							int plan = rs.getInt("plan");
							int uebung = rs.getInt("uebung");
							String datum = rs.getString("datum");
							String tag = rs.getString("tag");
							int laenge = rs.getInt("anzahl");
							System.out.println("plan: " + plan + " uebung: " + uebung + " tag: " + tag + " datum: " + datum);
							Satz[] satz = new Satz[laenge];
							int i = 0;
							rs1 = stmt1.executeQuery("select s.id, s.wh, s.gewicht from routine r, satz s where s.id=r.satz and r.plan="+plan+" and r.uebung="+uebung+" and r.datum='"+datum+"' and r.tag='"+tag+"';");
							
							while ( rs1.next() ) {
								
								int id = rs1.getInt("id");
								int wh = rs1.getInt("wh");
								int gewicht = rs1.getInt("gewicht");
								
								satz[i] = new Satz(id, wh, gewicht); 
								
								i++;
							}
							 u.setRoutineString(plan, tag, uebung, satz, datum);
							
							 
						 }
						
				 
				
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
	  
	   }finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
			try { if (rs1 != null) rs1.close(); } catch (Exception e) {};
			try { if (stmt1 != null) stmt1.close(); } catch (Exception e) {};
		//	try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
	   
   }
   
   public void routineStep1(User userp, int plan, int uebung, String tag, int wh, int gewicht, int satz){
	  if( saetzeSpeichern(userp, plan, uebung, tag, wh, gewicht, satz) == true){
		 
		 routineNew(userp, plan, uebung, tag, satzFeld);
	  } else{
		  
	  }
	   
   }
  private Satz[] satzFeld;
  
   

   public boolean saetzeSpeichern(User userp, int plan, int uebung, String tag, int wh, int gewicht, int satz){
	
	 for(Plan p : userp.getPlans().values()){
		 if(p.getId() == plan){
			 for(AusgewaehlteUebung a : p.getUebungen().get(Tag.valueOf(tag)).getUebungen()){
				 if(a.getUebung().getId() == uebung){
					 
							
							 a.setSaetze(satz,wh, gewicht);
						  if(satz < (a.getWh()-1)){
							  return false;
						  } else{
							
							 satzFeld=a.getSaetze();
							  return true;
						  }
					 }
				 }
			 }
		 }
		 return false;
		 
		
	 }
   
   public void routineNew(User userp, int plan, int uebung, String tag, Satz[] satz){
	   
	  
		   DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		   Date datum = new Date();
		   try {
				stmt = conn.createStatement();
				
				 for(int i = 0; i < satz.length; i++){
					String sql = "INSERT INTO satz (id, wh, gewicht) " +
					   "VALUES (null,"+satz[i].getWh()+","+satz[i].getGewicht()+");"; 
					stmt.executeUpdate(sql);
					
					rs = stmt.executeQuery( "select max(id) as id from satz;" );
						 while ( rs.next() ) {
							 int id = rs.getInt("id");  
							
							 String sql1 = "INSERT INTO routine (datum, plan, uebung, tag, satz) " +
								"VALUES ('"+dateFormat.format(datum)+"',"+plan+","+uebung+",'"+tag+"',"+id+");"; 
								Satz s = new Satz(id, satz[i].getWh(), satz[i].getGewicht());
								
							stmt.executeUpdate(sql1);	
							 
						 }
						
				 }
				 userp.setRoutine(plan, tag, uebung, satz, datum);
	} catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
	  
	   }finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (stmt != null) stmt.close(); } catch (Exception e) {};
		//	try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
	
	   
	   
	  
   }
 public void planHinzufuegen(User userp, int id, int satz, String tag, String plan){
			
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
	
	
	if(userp.getPlans().containsKey(plan) == true){
		
		if(userp.getPlans().get(plan).getUebungen().containsKey(Tag.valueOf(tag)) == true){
			int planId = userp.getPlans().get(plan).getId();
			userp.getPlans().get(plan).getUebungen().get(Tag.valueOf(tag)).getUebungen().add(ausgewaehlt);
			 
					String sql = "INSERT INTO ausgewaehlteuebung (plan, uebung, tag, satz ) " +
					   "VALUES ("+planId+","+id+",'"+tag+"',"+satz+");"; 
					stmt.executeUpdate(sql);
					
				
			
		} else{
			TagPlan tagPlan = new TagPlan();
			tagPlan.tag = Tag.valueOf(tag);
			tagPlan.getUebungen().add(ausgewaehlt);
			userp.getPlans().get(plan).getUebungen().put(Tag.valueOf(tag), tagPlan);
			 int planId = userp.getPlans().get(plan).getId();
			
				
				 String	sql = "INSERT INTO ausgewaehlteuebung (plan, uebung, tag, satz ) " +
					   "VALUES ("+planId+","+id+",'"+tag+"',"+satz+");"; 
					stmt.executeUpdate(sql);
					
				
		}
	} else{
		
		TagPlan tagPlan = new TagPlan();
		tagPlan.tag = Tag.valueOf(tag);
		tagPlan.getUebungen().add(ausgewaehlt);
		Map<Tag, TagPlan> uebungen = new HashMap<Tag, TagPlan>();
		uebungen.put(tagPlan.tag, tagPlan);
		Plan p = new Plan(plan, uebungen);
		userp.setPlans(p);
		 
		String sql = "INSERT INTO plan(id,name,user) " +
                   "VALUES (null,'"+ plan+"'," + userp.getId()+");"; 
				   
		stmt.executeUpdate(sql);
		
		int planId=-1;
		rs = stmt.executeQuery("select max(id) as id from plan;" );
			 while ( rs.next() ) {
				 
				 planId = rs.getInt("id");
			 }
			 
		if(planId != -1){
			p.setId(planId);
				
				
					sql = "INSERT INTO ausgewaehlteuebung (plan, uebung, tag, satz ) " +
					   "VALUES ("+planId+","+id+",'"+tag+"',"+satz+");"; 
					stmt.executeUpdate(sql);
					
				
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
  
  public void planLoeschen(User userp){
		try {
				stmt = conn.createStatement();
				
				String sql = "delete from plan where id=(select p.id as id from plan p where p.user="+userp.getId()+" except select pl.id from ausgewaehlteuebung a, plan pl where a.plan=pl.id and pl.user="+userp.getId()+");"; 
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
	
	public void planLoeschenKomplett(User userp, int plan){
		try {
				stmt = conn.createStatement();
				
				rs = stmt.executeQuery("select name from plan where id="+plan+";");
				 while ( rs.next() ) {
				 
				 String planName = rs.getString("name");
				 userp.getPlans().remove(planName);
			 }
				
				String sql = "delete from plan where id="+plan+";"; 
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
  
  public void uebungLoeschen(User userp, int plan, int uebung, String t){
	  String planName;
	  Tag tag = Tag.valueOf(t);
		
		   try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select name from plan where id="+plan+";");
				 while ( rs.next() ) {
				 
				 planName = rs.getString("name");
				 userp.uebungLoeschen(planName, tag, uebung);
			 }
				String sql = "delete from ausgewaehlteuebung where plan="+plan+" and uebung="+uebung+" and tag='"+tag +"';"; 
				stmt.executeUpdate(sql);
				
				planLoeschen(userp);
				
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
 
	public void like(int uebung){
		try {	
				int likes=0;
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select like from uebung where id="+uebung+";");
				 while ( rs.next() ) {
				 
				 likes = rs.getInt("like");
				 likes = likes+1;
				
			 }
				String sql = "update uebung set like="+likes+" where id="+uebung+";"; 
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
	
	public void dislike(int uebung){
		
		try {	
				int dislikes=0;
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select dislike from uebung where id="+uebung+";");
				 while ( rs.next() ) {
				 
				 dislikes = rs.getInt("dislike");
				 dislikes = dislikes+1;
				 
			 }
				
				String sql = "update uebung set dislike="+dislikes+" where id="+uebung+";"; 
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
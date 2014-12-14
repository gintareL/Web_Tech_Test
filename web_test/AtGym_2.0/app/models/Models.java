package models;
import java.sql.*;
import java.sql.Connection;  
 import java.sql.DriverManager;  
 import java.sql.ResultSet;  
 import java.sql.Statement; 

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
	static int index=100; 	
	private static Models instance = null;
	private static User user;
   private Models() {
		conn = AtGymDatabase.dbConnector();
		
      // Exists only to defeat instantiation.
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
	 rs = stmt.executeQuery( "SELECT * FROM USER;" );
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
   if(user.getGeschlecht().equals("weiblich")==true) {
   geschlecht = 0;
   }  else {
   geschlecht = 1;
   }
   
   if(emailCheck(user.getEmail())==true){
	try {
	 stmt = conn.createStatement();
	       String sql = "INSERT INTO USER (userid,email,password,vorname,nachname,bild,groesse,gewicht,geschlecht) " +
                   "VALUES (null,'"+ u.getEmail() +"','"+u.getPassword()+"','"+ u.getVorname()+"','" + u.getNachname()+"','null',"+ u.getGroesse()+","+ u.getGewicht()+","+ geschlecht +");"; 
      stmt.executeUpdate(sql);
     stmt.close();
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
   
   public Map<Integer, Uebung> beine(){
   Map<Integer, Uebung> uebungenBeine = new HashMap<Integer, Uebung>();
   
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
}
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
import java.util.ArrayList;
import java.lang.StringBuffer.*;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



public class Models extends Observable{
	
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;	
	ResultSet rs1 = null;	
	
	private static Models instance = null;
	private static Map<Integer, Uebung> alleUebungen = new HashMap<Integer, Uebung>();
	private static Map<String, User>alleUser = new HashMap<String, User>();
	
	private Models() {
		
		conn = AtGymDatabase.dbConnector();
		

	}
	public Map<String, User> getAlleUser(){
		return alleUser;
	}
	
	public Map<Integer, Uebung> getAlleUebungen(){
		return alleUebungen;
	}
	
	
	public void abmelden(String email){
		alleUser.remove(email);
	}

	public boolean checkUser(String em, String password){
		PreparedStatement preparedStatement = null;
		String p = getHash(password);
		try {
			String sql = "SELECT * FROM user where email =? and password=?;";
			preparedStatement =conn.prepareStatement(sql);
			preparedStatement.setString(1, em);
			preparedStatement.setString(2, p);
			rs = preparedStatement.executeQuery();
			Set<String>bilder = new HashSet<String>();
			while ( rs.next() ) {
				String email = rs.getString("email");
				String passwort = rs.getString("password");
				int userid = rs.getInt("userid");
				
				
				if(email.equals(em)==true && passwort.equals(p)==true ){
					
					
					
					User userToList = new User(userid, rs.getString("vorname"), rs.getString("nachname"), email, p, rs.getInt("groesse"), rs.getInt("geschlecht"));
					userToList.model=this;
					bilderList(userToList);
					if(bilder.isEmpty() == true){
						if(userToList.getGeschlecht().equals("weiblich")==true) {
							userToList.defaultBild="assets//images//default_bild_w.jpg";
						}  else {
							userToList.defaultBild="assets//images//default_bild_m.jpg";
						}
					}
					
					gewichtList(userToList);
					bauchumfangList(userToList);
					armumfangList(userToList);
					hueftenList(userToList);
					brustumfangList(userToList); 
					//routineAuslesen(userToList);
					plaene(userToList);
					alleUser.put(em, userToList);
					System.out.println("User: " + userid);
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
			try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
		}
	}
	
	public void bilderList(User userp){
		PreparedStatement preparedStatement =null;
		ResultSet resultSet = null;
		try {
			String sql = "SELECT * FROM bild where user=?;";
			preparedStatement =conn.prepareStatement(sql);
			preparedStatement.setInt(1, userp.getId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				userp.setBild(resultSet.getString("bild"));
			}
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
			
		}finally {
			try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
			try { if (resultSet != null) resultSet.close(); } catch (Exception e) {};
		}
	}

	public int selectId(String email){
		PreparedStatement preparedStatement =null;
		int id = -1;
		try {
			String sql = "SELECT userid FROM user where email=?;";
			preparedStatement =conn.prepareStatement(sql);
			preparedStatement.setString(1, email);
			
			rs = preparedStatement.executeQuery();
			
			while ( rs.next() ) {
				id = rs.getInt("userid");
			}
			
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
			
		}finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
			
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
		PreparedStatement preparedStatement =null;
		try {
			String sql = "SELECT email FROM user where email=?;" ;
			preparedStatement =conn.prepareStatement(sql);
			preparedStatement.setString(1, em);
			
			rs = preparedStatement.executeQuery();
			while ( rs.next() ) {
				String email = rs.getString("email");
				if(email.equals(em)==true){
					
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
			try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
		}
	}

	public boolean neuerUser(User userNeu){
		int geschlecht;
		PreparedStatement preparedStatement1 =null;
		if(userNeu.getGeschlecht() != null){
			if(userNeu.getGeschlecht().equals("weiblich")==true) {
				geschlecht = 0;
				userNeu.defaultBild="assets//images//default_bild_w.jpg";
			}  else {
				geschlecht = 1;
				userNeu.defaultBild="assets//images//default_bild_m.jpg";
			}
		} else {
			return false;
		}
		String password = getHash(userNeu.getPassword());
		if(emailCheck(userNeu.getEmail())==false){
			userNeu.model=this;
			try {
				String sql = "INSERT INTO USER (userid,vorname,nachname,email,password,groesse,geschlecht) " +
				"VALUES (null,?,?,?,?,?,?);";

				preparedStatement1 =conn.prepareStatement(sql);

				preparedStatement1.setString(1, userNeu.getVorname());
				preparedStatement1.setString(2, userNeu.getNachname());
				preparedStatement1.setString(3, userNeu.getEmail());
				preparedStatement1.setString(4, password);
				preparedStatement1.setInt(5, userNeu.getGroesse());
				preparedStatement1.setInt(6, geschlecht);
				preparedStatement1.executeUpdate();
				
				
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
				try { if (preparedStatement1 != null) preparedStatement1.close(); } catch (Exception e) {};
				//	try { if (conn != null) conn.close(); } catch (Exception e) {};
			}
		} else{
			return false;
		}	
	}
	
	public void routineSpeichern(Routine routine){
		
	}

	public void imageSave(User userp, String file){
		PreparedStatement preparedStatement =null;
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY-hh-mm-ss");
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
			String sql = "insert into bild(bildid, bild, user) values (null,?,?);";

			preparedStatement =conn.prepareStatement(sql);

			preparedStatement.setString(1, bild);
			preparedStatement.setInt(2, userp.getId());
			preparedStatement.executeUpdate();
			
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
			
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
			//	try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
		
		
	}

	public void gewichtCheck(User userp){
		PreparedStatement preparedStatement1 =null;
		if(userp.getGewicht() != null){
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
			try {
				
				String sql = "INSERT INTO gewicht (id,umfang,datum, user) " +
				"VALUES (null,?,?,?);"; 
				
				preparedStatement1 =conn.prepareStatement(sql);

				preparedStatement1.setDouble(1, userp.getGewicht().getGewicht());
				preparedStatement1.setString(2, dateFormat.format(userp.getGewicht().getDatum()));
				preparedStatement1.setInt(3, userp.getId());
				
				preparedStatement1.executeUpdate();
				
			} catch ( Exception e ) {
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				System.exit(0);
				
			}finally {
				try { if (rs != null) rs.close(); } catch (Exception e) {};
				try { if (preparedStatement1 != null) preparedStatement1.close(); } catch (Exception e) {};
			}
		}
	}


	public void gewichtList(User u){
		PreparedStatement preparedStatement =null;
		try {
			String sql = "SELECT g.id, g.datum, g.umfang FROM user u, gewicht g where g.user=? and u.userid=g.user;" ;
			preparedStatement =conn.prepareStatement(sql);
			preparedStatement.setInt(1, u.getId());
			
			rs = preparedStatement.executeQuery();
			while ( rs.next() ) {
				u.getGewichtList().put(rs.getInt("id"), new Gewicht(rs.getDouble("umfang"), rs.getString("datum")));
			}
		/*	if(u.getGewichtList().isEmpty() == false){
			
			Gewicht[] gewichtArray = new Gewicht[u.getGewichtList().size()];
		int j = 0;
		gewichtArray = new Gewicht[u.getGewichtList().size()];
		
		for (int index : u.getGewichtList().keySet()){
			gewichtArray[j] = u.getGewichtList().get(index);
			j++;
		
		}
		u.setGewichtArray(gewichtArray);
		}	*/
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
			//	try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
		
		
	}

	public void bauchumfangCheck(User userp){
		PreparedStatement preparedStatement1 =null;
		if(userp.getBauchumfang() != null){
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
			try {
				String sql = "INSERT INTO bauchumfang (id,umfang,datum, user) " +
				"VALUES (null,?,?,?);";
				
				preparedStatement1 =conn.prepareStatement(sql);

				preparedStatement1.setDouble(1, userp.getBauchumfang().getUmfang());
				preparedStatement1.setString(2, dateFormat.format(userp.getBauchumfang().getDatum()));
				preparedStatement1.setInt(3, userp.getId());
				
				preparedStatement1.executeUpdate();

			} catch ( Exception e ) {
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				System.exit(0);
				
			} finally {
				try { if (rs != null) rs.close(); } catch (Exception e) {};
				try { if (preparedStatement1 != null) preparedStatement1.close(); } catch (Exception e) {};
				//try { if (conn != null) conn.close(); } catch (Exception e) {};
			}
		}
	}

	public void bauchumfangList(User u){
		PreparedStatement preparedStatement = null;
		try {
			String sql = "SELECT g.id, g.datum, g.umfang FROM user u, bauchumfang g where g.user=? and u.userid=g.user;"  ;
			preparedStatement =conn.prepareStatement(sql);
			preparedStatement.setInt(1, u.getId());
			
			rs = preparedStatement.executeQuery();
			while ( rs.next() ) {
				u.getBauchumfangList().put(rs.getInt("id"), new Bauchumfang(rs.getDouble("umfang"), rs.getString("datum")));
			}
			
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
			//	try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
	}


	public void hueftenumfangCheck(User userp){
		PreparedStatement preparedStatement1 =null;
		if(userp.getHueftenumfang() != null){
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
			try {
				String sql = "INSERT INTO hueftenumfang (id,umfang,datum, user) " +
				"VALUES (null,?,?,?);";
				
				preparedStatement1 =conn.prepareStatement(sql);

				preparedStatement1.setDouble(1, userp.getHueftenumfang().getUmfang());
				preparedStatement1.setString(2, dateFormat.format(userp.getHueftenumfang().getDatum()));
				preparedStatement1.setInt(3, userp.getId());
				
				preparedStatement1.executeUpdate();
				
			} catch ( Exception e ) {
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				System.exit(0);
				
			} finally {
				try { if (rs != null) rs.close(); } catch (Exception e) {};
				try { if (preparedStatement1 != null) preparedStatement1.close(); } catch (Exception e) {};
				//	try { if (conn != null) conn.close(); } catch (Exception e) {};
			}
		}
	}

	public void hueftenList(User u){
		try {
			String sql = "SELECT g.id, g.datum, g.umfang FROM user u, hueftenumfang g where g.user=? and u.userid=g.user;"  ;
			PreparedStatement preparedStatement =conn.prepareStatement(sql);
			preparedStatement.setInt(1, u.getId());
			
			rs = preparedStatement.executeQuery();
			
			while ( rs.next() ) {
				u.getHueftenumfangList().put(rs.getInt("id"), new Hueftenumfang(rs.getDouble("umfang"), rs.getString("datum")));
			}
			
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
		PreparedStatement preparedStatement = null;
		try {
			
			String sql = "SELECT g.id, g.datum, g.umfang FROM user u, armumfang g where g.user=? and u.userid=g.user;" ;
			preparedStatement =conn.prepareStatement(sql);
			preparedStatement.setInt(1, u.getId());
			
			rs = preparedStatement.executeQuery();
			while ( rs.next() ) {
				
				u.getArmumfangList().put(rs.getInt("id"), new Armumfang(rs.getDouble("umfang"), rs.getString("datum")));
			}
			
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
			//	try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
	}

	public void armumfangCheck(User userp){
		PreparedStatement preparedStatement1 =null;
		if(userp.getArmumfang() != null){
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
			try {
				String sql = "INSERT INTO armumfang (id,umfang,datum, user) " +
				"VALUES (null,?,?,?);";
				
				preparedStatement1 =conn.prepareStatement(sql);

				preparedStatement1.setDouble(1, userp.getArmumfang().getUmfang());
				preparedStatement1.setString(2, dateFormat.format(userp.getArmumfang().getDatum()));
				preparedStatement1.setInt(3, userp.getId());
				
				preparedStatement1.executeUpdate();
			} catch ( Exception e ) {
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				System.exit(0);
				
			} finally {
				try { if (rs != null) rs.close(); } catch (Exception e) {};
				try { if (preparedStatement1 != null) preparedStatement1.close(); } catch (Exception e) {};
				//	try { if (conn != null) conn.close(); } catch (Exception e) {};
			}
		}
	}

	public void brustumfangList(User u){
		PreparedStatement preparedStatement =null;
		try {
			String sql = "SELECT g.id, g.datum, g.umfang FROM user u, brustumfang g where g.user=? and u.userid=g.user;" ;
			preparedStatement =conn.prepareStatement(sql);
			preparedStatement.setInt(1, u.getId());
			
			rs = preparedStatement.executeQuery();
			while ( rs.next() ) {
				
				u.getBrustumfangList().put(rs.getInt("id"), new Brustumfang(rs.getDouble("umfang"), rs.getString("datum")));
			}
			
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
			//	try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
	}

	public void brustumfangCheck(User userp){
		PreparedStatement preparedStatement1 =null;
		if(userp.getBrustumfang() != null){
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
			try {
				
				String sql = "INSERT INTO brustumfang (id,umfang,datum, user) " +
				"VALUES (null,?,?,?);";
				
				preparedStatement1 =conn.prepareStatement(sql);

				preparedStatement1.setDouble(1, userp.getBrustumfang().getUmfang());
				preparedStatement1.setString(2, dateFormat.format(userp.getBrustumfang().getDatum()));
				preparedStatement1.setInt(3, userp.getId());
				
				preparedStatement1.executeUpdate();
				
			} catch ( Exception e ) {
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				System.exit(0);
				
			} finally {
				try { if (rs != null) rs.close(); } catch (Exception e) {};
				try { if (preparedStatement1 != null) preparedStatement1.close(); } catch (Exception e) {};
				//	try { if (conn != null) conn.close(); } catch (Exception e) {};
			}
		}
	}

	public SortedMap<Integer, Uebung> uebungenListeMuskelgruppe(String muskelgruppe){
		SortedMap<Integer, Uebung> uebungen = new TreeMap<Integer, Uebung>();
		PreparedStatement preparedStatement =null;
		try {
			String name;
			String bild;
			String equipment;
			String grad;
			String muskel1;
			String muskel2;
			String sql = "SELECT u.like, u.dislike, u.id, u.name, u.bild, b.equipment, b.grad, b.muskel, b.muskel2 FROM uebung u, beschreibung b  where u.muskel=? and b.id = u.beschreibung;"  ;
			preparedStatement =conn.prepareStatement(sql);
			preparedStatement.setString(1, muskelgruppe);
			
			rs = preparedStatement.executeQuery();
			
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
				Uebung uebung = new Uebung(id, name, equipment, grad, muskel1, muskel2, bild, Muskel.valueOf(muskelgruppe), like, dislike);
				if(alleUebungen.containsKey(id) == false){
					alleUebungen.put(id, uebung);
				}
				uebungen.put(id, uebung);
				
			}
			
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
			//	try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
		return uebungen;
	}
	
	public Uebung getUebung(int uebungid){
		Uebung uebung = alleUebungen.get(uebungid);

		return uebung;
	}
	public void setLike(Uebung task){
		PreparedStatement preparedStatement1 =null;
		try {	
			int likes=task.getLike()+1;
			
			
			String sql = "update uebung set like=? where id=?;";

			preparedStatement1 =conn.prepareStatement(sql);

			preparedStatement1.setInt(1, likes);
			preparedStatement1.setInt(2, task.getId());
			preparedStatement1.executeUpdate();
			task.setLike(likes);
			preparedStatement1.close();
			
			if(countObservers()>0){
				setChanged();
				notifyObservers(task.getId());
			}
			
			
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
			
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (preparedStatement1 != null) preparedStatement1.close(); } catch (Exception e) {};
			//	try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
	}
	
	public JsonNode getLikesAktuell(JsonNode obj) {
		JsonNode likes = null;
		PreparedStatement preparedStatement =null;
		int uebungId = obj.get("uebung").asInt();
		Integer likesAnzahl = null;
		try {	
			String sql = "SELECT u.like FROM uebung u where u.id=?;"  ;
			preparedStatement =conn.prepareStatement(sql);
			preparedStatement.setInt(1, uebungId);
			
			rs = preparedStatement.executeQuery();
			while ( rs.next() ) {
				
				likesAnzahl = new Integer(rs.getInt("like"));
				
			}
			
			
			ObjectMapper mapper = new ObjectMapper();
			likes = mapper.readTree("{\"uebung\":\""+uebungId+"\",\"likes\":\"" + likesAnzahl.toString()	+ "\"}");
			System.out.println("JSON-Likes: "+likes);
			
		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
			
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
			//	try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
		return likes;
	}
	

	public User aktuellUserList(String email){
		return alleUser.get(email);
	}

	public void plaene(User u){
		PreparedStatement preparedStatement = null;
		try {
			String name;
			String bild;
			String equipment;
			String grad;
			String muskel1;
			String muskel2;
			int id; 
			Muskel muskelgruppe;
			String sql = "SELECT u.id as uid, u.name as uname, u.bild as bild, u.muskel as muskelgruppe, b.equipment as bequi, b.grad as grad, b.muskel as bm1, b.muskel2 as bm2, p.name as pname, p.id as pid, t.name as tag, a.satz as saetze FROM uebung u, beschreibung b, ausgewaehlteuebung a, plan p, Tag t where b.id=u.beschreibung and a.plan=p.id and u.id=a.uebung and a.tag=t.name and p.user=?;" ;
			preparedStatement =conn.prepareStatement(sql);
			preparedStatement.setInt(1, u.getId());
			
			rs = preparedStatement.executeQuery();	
			
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
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
			//try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
		
	}
	public Set<Routine> routineAuslesen(User u){
		Set <Routine> routine = new HashSet<Routine>();
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		try {
			String sql = "select r.datum as datum, r.plan as plan, r.uebung as uebung, r.tag as tag, count() as anzahl from routine r, plan p where p.user=? and p.id=r.plan group by r.plan, r.uebung, r.tag, r.datum;" ;
			preparedStatement =conn.prepareStatement(sql);
			preparedStatement.setInt(1, u.getId());
			
			rs = preparedStatement.executeQuery();	


			while ( rs.next() ) {
				int plan = rs.getInt("plan");
				int uebung = rs.getInt("uebung");
				String datum = rs.getString("datum");
				String tag = rs.getString("tag");
				int laenge = rs.getInt("anzahl");
				Satz[] satz = new Satz[laenge];
				int i = 0;
				String sql2 = "select s.id, s.wh, s.gewicht from routine r, satz s where s.id=r.satz and r.plan=? and r.uebung=? and r.datum=? and r.tag=?;";
				preparedStatement2 =conn.prepareStatement(sql2);
				preparedStatement2.setInt(1,plan);
				preparedStatement2.setInt(2,uebung);
				preparedStatement2.setString(3,datum);
				preparedStatement2.setString(4,tag);
				
				rs1 = preparedStatement2.executeQuery();	
				while ( rs1.next() ) {
					
					int id = rs1.getInt("id");
					int wh = rs1.getInt("wh");
					int gewicht = rs1.getInt("gewicht");
					
					satz[i] = new Satz(id, wh, gewicht); 
					
					i++;
				}
				routine.add(new Routine(plan, tag, uebung, satz, datum));
				//	u.setRoutineString(plan, tag, uebung, satz, datum);
			}
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
			
		}finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
			try { if (rs1 != null) rs1.close(); } catch (Exception e) {};
			try { if (preparedStatement2 != null) preparedStatement2.close(); } catch (Exception e) {};
			//	try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
		return routine;
	}

	public void routineStep1(User userp, int plan, int uebung, String tag, int wh, int gewicht, int satz){
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
		Date datum = new Date();
		
		
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement2 = null;
		
		try {
			
			String sql = "INSERT INTO satz (id, wh, gewicht) " +
			"VALUES (null,?,?);"; 
			
			preparedStatement =conn.prepareStatement(sql);

			preparedStatement.setInt(1, wh);
			preparedStatement.setDouble(2, gewicht);
			
			preparedStatement.executeUpdate();
			
			String sql2 =  "select max(id) as id from satz;"  ;
			preparedStatement2 =conn.prepareStatement(sql2);
			
			rs = preparedStatement2.executeQuery();	
			
			while ( rs.next() ) {
				int id = rs.getInt("id");  
				
				String sql1 = "INSERT INTO routine (datum, plan, uebung, tag, satz) " +
				"VALUES (?,?,?,?,?);"; 
				
				preparedStatement1 =conn.prepareStatement(sql1);

				preparedStatement1.setString(1, dateFormat.format(datum));
				preparedStatement1.setInt(2, plan);
				preparedStatement1.setInt(3, uebung);
				preparedStatement1.setString(4, tag);
				preparedStatement1.setInt(5, id);
				
				preparedStatement1.executeUpdate();
				
			}
			
			
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
			
		}finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
			try { if (preparedStatement1 != null) preparedStatement1.close(); } catch (Exception e) {};
			try { if (preparedStatement2 != null) preparedStatement2.close(); } catch (Exception e) {};
			//	try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
	}
	
	
	public void planHinzufuegen(User userp, int id, int satz, String tag, String plan){
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;
		PreparedStatement preparedStatement4 = null;
		String name;
		String bild;
		String equipment;
		String grad;
		String muskel1;
		String muskel2;
		String muskel;
		Muskel muskelgruppe;
		try {
			String sql2 =  "SELECT u.name, u.bild, b.equipment, b.grad, b.muskel, b.muskel2, u.muskel as muskelgruppe FROM uebung u, beschreibung b  where b.id = u.beschreibung and u.id=?;"  ;
			preparedStatement =conn.prepareStatement(sql2);
			preparedStatement.setInt(1, id);
			
			rs = preparedStatement.executeQuery();	

			while ( rs.next() ) {
				name = rs.getString("name");
				bild = rs.getString("bild");
				bild = "assets//images//"+bild+".gif";
				
				equipment = rs.getString("equipment");
				grad = rs.getString("grad");
				muskel1 = rs.getString("muskel");
				muskel2 = rs.getString("muskel2");
				muskelgruppe=Muskel.valueOf(rs.getString("muskelgruppe"));
				
				System.out.println("vor der Abfrage");
				Uebung uebung = new Uebung(id, name, equipment, grad, muskel1, muskel2, bild, muskelgruppe);
				AusgewaehlteUebung ausgewaehlt = new AusgewaehlteUebung(uebung, satz);
				//if(userp.getPlans().get(plan).getUebungen().get(Tag.valueOf(tag)).getUebungen().isEmpty()==false || userp.getPlans().get(plan).getUebungen().get(Tag.valueOf(tag)).getUebungen().contains(ausgewaehlt) == false){
				System.out.println("Diese Uebung ist nicht ausgewaehlt worden");
				if(userp.getPlans().containsKey(plan) == true){
					System.out.println("plan ist true");
					if(userp.getPlans().get(plan).getUebungen().containsKey(Tag.valueOf(tag)) == true){
						System.out.println("Tag exists");
						if(userp.getPlans().get(plan).getUebungen().get(Tag.valueOf(tag)).getUebungen().contains(ausgewaehlt) == false){
							boolean exist = false;
						for(AusgewaehlteUebung a : userp.getPlans().get(plan).getUebungen().get(Tag.valueOf(tag)).getUebungen()){
							System.out.println("Vergleich: " + a.getUebung().getId() + " " + uebung.getId());
						if(a.getUebung().getId() == uebung.getId() && a.getWh() != satz){
							int planId = userp.getPlans().get(plan).getId();
								a.setWh(satz);
								
								String sql3 = "UPDATE ausgewaehlteuebung set satz=? where plan=? and uebung=? and tag=?;";
								
								preparedStatement2 =conn.prepareStatement(sql3);

								preparedStatement2.setInt(1, satz);
								preparedStatement2.setInt(2, planId);
								preparedStatement2.setInt(3, id);
								preparedStatement2.setString(4, tag);
								
								
								preparedStatement2.executeUpdate();	
							exist = true;
							break;
						} else if(a.getUebung().getId() == uebung.getId() && a.getWh() == satz){
							exist = true;
							break;
						}
						}
						if(exist == false){
						int planId = userp.getPlans().get(plan).getId();
						userp.getPlans().get(plan).getUebungen().get(Tag.valueOf(tag)).getUebungen().add(ausgewaehlt);
						
						String sql3 = "INSERT INTO ausgewaehlteuebung (plan, uebung, tag, satz ) " +
						"VALUES (?,?,?,?);";
						
						preparedStatement3 =conn.prepareStatement(sql3);

						preparedStatement3.setInt(1, planId);
						preparedStatement3.setInt(2, id);
						preparedStatement3.setString(3, tag);
						preparedStatement3.setInt(4, satz);
						
						
						preparedStatement3.executeUpdate();	
						}
						}
						
					} else{
						TagPlan tagPlan = new TagPlan();
						tagPlan.tag = Tag.valueOf(tag);
						tagPlan.getUebungen().add(ausgewaehlt);
						userp.getPlans().get(plan).getUebungen().put(Tag.valueOf(tag), tagPlan);
						int planId = userp.getPlans().get(plan).getId();
						
						String sql3 = "INSERT INTO ausgewaehlteuebung (plan, uebung, tag, satz ) " +
						"VALUES (?,?,?,?);";
						
						preparedStatement3 =conn.prepareStatement(sql3);

						preparedStatement3.setInt(1, planId);
						preparedStatement3.setInt(2, id);
						preparedStatement3.setString(3, tag);
						preparedStatement3.setInt(4, satz);
						
						
						preparedStatement3.executeUpdate();	
						
						
					}
				} else{
					
					TagPlan tagPlan = new TagPlan();
					tagPlan.tag = Tag.valueOf(tag);
					tagPlan.getUebungen().add(ausgewaehlt);
					Map<Tag, TagPlan> uebungen = new HashMap<Tag, TagPlan>();
					uebungen.put(tagPlan.tag, tagPlan);
					Plan p = new Plan(plan, uebungen);
					userp.setPlans(p);
					
					
					String sql3 =  "INSERT INTO plan(id,name,user) " +
					"VALUES (null,?,?);"; 
					
					preparedStatement3 =conn.prepareStatement(sql3);

					preparedStatement3.setString(1, plan);
					preparedStatement3.setInt(2, userp.getId());
					
					
					preparedStatement3.executeUpdate();	
					
					int planId=-1;
					String sql1 = "select max(id) as id from plan;"   ;
					PreparedStatement preparedStatement1 =conn.prepareStatement(sql1);
					
					rs = preparedStatement1.executeQuery();
					
					
					while ( rs.next() ) {
						
						planId = rs.getInt("id");
					}
					
					if(planId != -1){
						p.setId(planId);
						
						String sql4 = "INSERT INTO ausgewaehlteuebung (plan, uebung, tag, satz ) " +
						"VALUES (?,?,?,?);";
						
						preparedStatement4 =conn.prepareStatement(sql4);

						preparedStatement4.setInt(1, planId);
						preparedStatement4.setInt(2, id);
						preparedStatement4.setString(3, tag);
						preparedStatement4.setInt(4, satz);
						
						
						preparedStatement4.executeUpdate();		
						
					}
					
				}
				}
			//}
			
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (preparedStatement4 != null) preparedStatement4.close(); } catch (Exception e) {};
			try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
			try { if (preparedStatement2 != null) preparedStatement2.close(); } catch (Exception e) {};
			try { if (preparedStatement3 != null) preparedStatement3.close(); } catch (Exception e) {};
			//	try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
		
	}

	public void planLoeschen(User userp){
		PreparedStatement preparedStatement4 = null;
		try {
			String sql = "delete from plan where id=(select p.id as id from plan p where p.user=? except select pl.id from ausgewaehlteuebung a, plan pl where a.plan=pl.id and pl.user=?);"; 
			preparedStatement4 =conn.prepareStatement(sql);

			preparedStatement4.setInt(1, userp.getId());
			preparedStatement4.setInt(2, userp.getId());
			
			
			preparedStatement4.executeUpdate();		
			
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
			
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (preparedStatement4 != null) preparedStatement4.close(); } catch (Exception e) {};
			//	try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
	}
	
	public void planLoeschenKomplett(User userp, int plan){
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement4 = null;
		try {
			String sql = "select name from plan where id=?;"  ;
			preparedStatement =conn.prepareStatement(sql);
			preparedStatement.setInt(1, plan);
			
			rs = preparedStatement.executeQuery();
			
			
			while ( rs.next() ) {
				
				String planName = rs.getString("name");
				userp.getPlans().remove(planName);
			}
			
			
			String sql4 = "delete from plan where id=?;";
			preparedStatement4 =conn.prepareStatement(sql4);

			preparedStatement4.setInt(1, plan);
			
			preparedStatement4.executeUpdate();	
			
			
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
			
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (preparedStatement4 != null) preparedStatement4.close(); } catch (Exception e) {};
			try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
			//	try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
	}

	public void uebungLoeschen(User userp, int plan, int uebung, String t){
		String planName;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement4 = null;
		Tag tag = Tag.valueOf(t);
		
		try {
			String sql = "select name from plan where id=?;"  ;
			preparedStatement =conn.prepareStatement(sql);
			preparedStatement.setInt(1, plan);
			
			rs = preparedStatement.executeQuery();

			while ( rs.next() ) {
				
				planName = rs.getString("name");
				userp.uebungLoeschen(planName, tag, uebung);
			}
			String sql4 = "delete from ausgewaehlteuebung where plan=? and uebung=? and tag=?;"; 
			preparedStatement4 =conn.prepareStatement(sql4);

			preparedStatement4.setInt(1, plan);
			preparedStatement4.setInt(2, uebung);
			preparedStatement4.setString(3, t);
			
			preparedStatement4.executeUpdate();	
			
			planLoeschen(userp);
			
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
			
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (preparedStatement4 != null) preparedStatement4.close(); } catch (Exception e) {};
			try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
			//	try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
		
		
	}

	public void like(int uebung){
		PreparedStatement preparedStatement1 =null;
		int likes=alleUebungen.get(uebung).getLike()+1;
		try {
			String sql = "update uebung set like=? where id=?;";

			preparedStatement1 =conn.prepareStatement(sql);

			preparedStatement1.setInt(1, likes);
			preparedStatement1.setInt(2, uebung);
			preparedStatement1.executeUpdate();
			if(countObservers()>0){
				setChanged();
				notifyObservers(uebung);
			}
			
			
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
			
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (preparedStatement1 != null) preparedStatement1.close(); } catch (Exception e) {};
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



	public String Plannamen(User user, String input){
		ArrayList<String> plannamen = new ArrayList<>();
		String ergebnis = "";
		//stmt = conn.createStatement();
		PreparedStatement preparedStatement = null;
		int id =  user.getId();
		try {
		
			String sql = "select p.name from plan p where p.user=?;"  ;
			preparedStatement =conn.prepareStatement(sql);
							
							
			preparedStatement.setInt(1, id);				
			rs = preparedStatement.executeQuery();
				
			System.out.println("Test sql:" + sql);
			 
			while ( rs.next() ) {
					plannamen.add(rs.getString("p.name"));
			}
			boolean sorted = false;
			String[] meinTextArray = plannamen.toArray(new String[plannamen.size()]);
			String eingabe = input;
			
			if (null != eingabe && 0 < eingabe.trim().length()) {
				if (!sorted) {
					java.util.Arrays.sort(meinTextArray);
					sorted = true;
				}
			}
				
			StringBuffer auswahl = new StringBuffer();
			boolean resultFound = false;
			
			for (int i = 0; i < meinTextArray.length; i++) {
				if (meinTextArray[i].toUpperCase().startsWith(eingabe.toUpperCase())){
					auswahl.append(meinTextArray[i]).append(";");
					resultFound = true;
				}else {
					if (resultFound){
						break;
					}
				}
			}
			
			if (0 < auswahl.length()) {
				auswahl.setLength(auswahl.length() -1);
			}
			System.out.println(auswahl.toString());
			ergebnis = auswahl.toString();
		
					
				 
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
	  
		} finally {
			try { if (rs != null) rs.close(); } catch (Exception e) {};
			try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) {};
		//	try { if (conn != null) conn.close(); } catch (Exception e) {};
		}
		
		return ergebnis;	
	
	}

}
package models;
import java.sql.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.DatabaseMetaData;
 
public class AtGymDatabase{

	Connection conn = null;
	
	
	
    public static Connection dbConnector (){
	
	try{
		String sDriverName = "org.sqlite.JDBC";
		Class.forName(sDriverName);
		Connection conn = DriverManager.getConnection("jdbc:sqlite:.\\app\\db\\Gym");
		//JOptionPane.showMessageDialog(null, "gemacht");
		Statement stmt = conn.createStatement();
		String sql = "CREATE TABLE IF NOT EXISTS bild('bildid' integer primary key autoincrement, 'bild' varchar not null, 'user' integer references user('userid'));";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE IF NOT EXISTS user('userid' integer primary key autoincrement, 'vorname' varchar not null,'nachname' varchar not null,'email' varchar not null,'password' varchar not null,'groesse' integer,'geschlecht' integer references geschlecht('id'));";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE IF NOT EXISTS armumfang('id' integer primary key autoincrement, 'umfang' double, 'datum' date,'user' integer references user('userid'));";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE IF NOT EXISTS bauchumfang('id' integer primary key autoincrement, 'umfang' double, 'datum' date,'user' integer references user('userid'));";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE IF NOT EXISTS brustumfang('id' integer primary key autoincrement, 'umfang' double, 'datum' date,'user' integer references user('userid'));";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE IF NOT EXISTS hueftenumfang('id' integer primary key autoincrement, 'umfang' double, 'datum' date,'user' integer references user('userid'));";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE IF NOT EXISTS gewicht('id' integer primary key autoincrement, 'umfang' double, 'datum' date,'user' integer references user('userid'));";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE IF NOT EXISTS geschlecht('id' integer primary key autoincrement, 'name' varchar not null);";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE IF NOT EXISTS tag('id' integer primary key autoincrement, 'name' varchar not null);";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE IF NOT EXISTS muskelgruppe('name' varchar  primary key);";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE IF NOT EXISTS beschreibung('id' integer primary key autoincrement, 'equipment' varchar not null, 'grad' varchar not null, 'muskel' varchar not null, 'muskel2' varchar);";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE IF NOT EXISTS uebung('id' integer primary key autoincrement, 'name' varchar not null, 'bild' varchar,'beschreibung' integer references beschreibung('id'), 'muskel' varchar references muskelgruppe, 'like' integer, 'dislike' integer);";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE IF NOT EXISTS plan('id' integer primary key autoincrement, 'name' varchar not null, 'user' integer not null references user('userid'));";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE IF NOT EXISTS ausgewaehlteuebung('plan' integer not null references plan('id'), 'uebung' integer not null references uebung('id'), 'tag' integer not null references tag('id'), 'satz' integer not null, primary key('plan', 'uebung', 'tag'));";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE IF NOT EXISTS satz('id' integer primary key autoincrement, 'wh' integer, 'gewicht' double);";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE IF NOT EXISTS routine('datum' date, 'plan' integer references plan('id'), 'uebung' references uebung('id'), tag varchar, 'satz' references satz('id'), primary key('datum', 'plan', 'uebung', 'satz'));";
		stmt.executeUpdate(sql);
		
			sql = "SELECT count(*) as anzahl FROM uebung;" ;
			ResultSet rs = stmt.executeQuery( sql );
			while ( rs.next() ) {
				if(rs.getInt("anzahl") == 0){
					stmt.executeUpdate("INSERT INTO beschreibung (id,equipment,grad,muskel,muskel2) VALUES (1, 'Keins, Lang- oder Kurzhantel','hoch','Vierköpfiger Oberschenkelmuskel, Beinbizeps, großer Gesäßmuskel','Rückenstrecker,  Dreiköpfiger Adduktor');"+
							"INSERT INTO beschreibung (id,equipment,grad,muskel,muskel2) VALUES (2, 'keins oder Kurzhantel','mittel','Beinbizeps,  Plattsehnenmuskel,  Halbsehnenmuskel','Zwillingswadenmuskel, großer Gesäßmuskel, gerade Bauchmuskeln');"+
							"INSERT INTO beschreibung (id,equipment,grad,muskel,muskel2) VALUES (3, 'Keins, Kurz oder Langhantel','mittel','lateraler Kopf des Zwillingswadenmuskel, medialer Kopf des Zwillingswadenmuskel, Schollenmuskel ,  Plattsehnenmuskel,  Halbsehnenmuskel','keine');"+
							"INSERT INTO beschreibung (id,equipment,grad,muskel,muskel2) VALUES (4, 'Keins oder Kurzhanteln','mittel','Quadrizeps, Großer Gesäßmuskel','Beinbizeps');"+
							"INSERT INTO beschreibung (id,equipment,grad,muskel,muskel2) VALUES (5, 'SZ-Stange Kurz- oder Langhantelstange','niedrig','Bizeps, Armbeuger','Oberarmspeichenmuskel');"+
							"INSERT INTO beschreibung (id,equipment,grad,muskel,muskel2) VALUES (6, 'SZ-, Kurz- oder Langhantelstange, Flachbank','niedrig','Trizeps, Knorrenmuskel','Langer radialer Handstrecker, kurzer radialer Handstrecker');"+
							"INSERT INTO beschreibung (id,equipment,grad,muskel,muskel2) VALUES (7, 'Kurzhantel','mittel','Mittlerer Teil des Deltamuskels, Hinterer Teil des Deltamuskels, Vorderer Teil des Deltamuskels','Obere Fasern des Kapuzenmuskels, Trizeps');"+
							"INSERT INTO beschreibung (id,equipment,grad,muskel,muskel2) VALUES (8, 'Keins','mittel','Rückenstrecker','Großer Gesäßmuskel, Beinbizeps');"+
							"INSERT INTO beschreibung (id,equipment,grad,muskel,muskel2) VALUES (9, 'Kurzhantel, Flachbank','mittel','Vorderer Sägemuskel, Großer Brustmuskel','Trizeps, breiter Rückenmuskel');"+
							"INSERT INTO beschreibung (id,equipment,grad,muskel,muskel2) VALUES (10, 'Kurz- oder Langhantel','niedrig','Großer Brustmuskel, Trizeps ','Vorderer Teil des Deltamuskels, Knorrenmuskel');"+
							"INSERT INTO beschreibung (id,equipment,grad,muskel,muskel2) VALUES (11, 'Keins','niedrig','Großer Brustmuskel','Trizeps, Vorderer Teil des Deltamuskels');"+
							"INSERT INTO beschreibung (id,equipment,grad,muskel,muskel2) VALUES (12, 'Kurzhanteln','mittel','Großer Brustmuskel','Vorderer Teil des Deltamuskels, Vorderer Sägemuskel');"+
							"INSERT INTO beschreibung (id,equipment,grad,muskel,muskel2) VALUES (13, 'Keins','niedrig','Schräger Bauchmuskel, ','Gerader Bauchmuskel, Pyramidenförmiger Muskel');"+
							"INSERT INTO beschreibung (id,equipment,grad,muskel,muskel2) VALUES (14, 'Keins','mittel','Gerader Bauchmuskel, Pyramidenförmiger Muskel','Schräger Bauchmuskel');"+
							"INSERT INTO muskelgruppe (name) VALUES ('arme');"+
							"INSERT INTO muskelgruppe (name) VALUES ('beine');"+
							"INSERT INTO muskelgruppe (name) VALUES ('bauch');"+
							"INSERT INTO muskelgruppe (name) VALUES ('brust');"+
							"INSERT INTO muskelgruppe (name) VALUES ('schultern');"+
							"INSERT INTO tag (id, name) VALUES (1, 'Montag');"+
							"INSERT INTO tag (id, name) VALUES (2, 'Dienstag');"+
							"INSERT INTO tag (id, name) VALUES (3, 'Mittwoch');"+
							"INSERT INTO tag (id, name) VALUES (4, 'Donnerstag');"+
							"INSERT INTO tag (id, name) VALUES (5, 'Freitag');"+
							"INSERT INTO tag (id, name) VALUES (6, 'Samstag');"+
							"INSERT INTO tag (id, name) VALUES (7, 'Sonntag');"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (1, 'Squats oder Kniebeuge','squats1',1,'beine',2, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (2, 'Beckenheben','beckenheben',2,'beine',3, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (3, 'Wadenheben','wadenheben',3,'beine',3, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (4, 'Seitlicher Ausfallschritt','seitlicherAusfallschritt',4,'beine',2, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (5, 'Ausfallschritt','ausfallschritt',4,'beine',1, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (6, 'Beckenheben mit ausgestrecktem Bein','beckenheben1',2,'beine',0, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (7, 'Kniebeuge mit einem Bein','squats2',1,'beine',0, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (8, 'Kniebeuge','squats3',1,'beine',1, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (9, 'Kniebeuge','squats4',1,'beine',1, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (10, 'Curls','arme2',5,'arme',3, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (11, 'Stirndrücken','trizeps1',6,'arme',2, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (12, 'Skull Crusher','trizeps',6,'arme',4, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (13, 'Schulterdrücken','schulterndrueckeb',7,'arme',4, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (14, 'Nose Breaker','trizeps2',6,'arme',2, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (15, 'Schulterdrücken','schulterndrueckeb',7,'schultern',2, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (16, 'Schulterdrücken 2','schulterheben',7,'schultern',1, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (17, 'Schulterdrücken (mit einem Stuhl)','Schultern',7,'schultern',1, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (18, 'Schulternheben (seitlich)','seitheben',7,'schultern',0, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (19, 'Schulternheben','seitheben1',7,'schultern',1, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (20, 'Schulternheben (Vorheben)','vorheben',7,'schultern',1, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (21, 'Rückenstrecken im Liegen','ruecken1',8,'ruecken',2, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (22, 'Rückenstrecken im Liegen','rueckenhebn',8,'ruecken',1, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (23, 'Pull Over','pullover',9,'ruecken',2, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (24, 'Pull Over','pullover',9,'brust',2, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (25, 'Bankdrücken im Liegen','bankdruecken',10,'brust',2, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (26, 'Bankdrücken','bankdruecken1',10,'brust',4, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (27, 'Liegestütze ','liegestuetze',11,'brust',2, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (28, 'Liegestütze 2','liegestuetzen1',11,'brust',1, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (29, 'Fliegende','butterfly',12,'brust',3, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (30, 'Seitliches Hüftheben','bauch3',13,'bauch',4, 0);"+
							"INSERT INTO uebung (id, name, bild, beschreibung, muskel, like, dislike) VALUES (31, 'Beinheben im Liegen','klappmesser',14,'bauch',3, 0);"+
							"INSERT INTO user (userid,vorname,nachname,email,password,groesse,geschlecht) VALUES (1,'Christian', 'Bale', 'chris@bale.com','fd4cef7a4e607f1fcc920ad6329a6df2df99a4e8',183,1);"+
							"INSERT INTO armumfang(id, umfang, datum, user) VALUES (1,30, '01-01-2007', 1);"+
							"INSERT INTO armumfang(id, umfang, datum, user) VALUES (2,32, '01-03-2007', 1);"+
							"INSERT INTO armumfang(id, umfang, datum, user) VALUES (3,34, '01-04-2007', 1);"+
							"INSERT INTO armumfang(id, umfang, datum, user) VALUES (4,35, '01-06-2007', 1);"+
							"INSERT INTO armumfang(id, umfang, datum, user) VALUES (5,36, '01-08-2007', 1);"+
							"INSERT INTO armumfang(id, umfang, datum, user) VALUES (6,37, '01-09-2007', 1);"+
							"INSERT INTO armumfang(id, umfang, datum, user) VALUES (7,39, '01-11-2007', 1);"+
							"INSERT INTO armumfang(id, umfang, datum, user) VALUES (8,40, '01-01-2008', 1);"+
							"INSERT INTO bauchumfang(id, umfang, datum, user) VALUES (1,60, '01-01-2007', 1);"+
							"INSERT INTO bauchumfang(id, umfang, datum, user) VALUES (2,63, '01-03-2007', 1);"+
							"INSERT INTO bauchumfang(id, umfang, datum, user) VALUES (3,64, '01-04-2007', 1);"+
							"INSERT INTO bauchumfang(id, umfang, datum, user) VALUES (4,65, '01-06-2007', 1);"+
							"INSERT INTO bauchumfang(id, umfang, datum, user) VALUES (5,70, '01-08-2007', 1);"+
							"INSERT INTO bauchumfang(id, umfang, datum, user) VALUES (6,80, '01-09-2007', 1);"+
							"INSERT INTO bauchumfang(id, umfang, datum, user) VALUES (7,85, '01-11-2007', 1);"+
							"INSERT INTO bauchumfang(id, umfang, datum, user) VALUES (8,90, '01-01-2008', 1);"+
							"INSERT INTO brustumfang(id, umfang, datum, user) VALUES (1,80, '01-01-2007', 1);"+
							"INSERT INTO brustumfang(id, umfang, datum, user) VALUES (2,83, '01-03-2007', 1);"+
							"INSERT INTO brustumfang(id, umfang, datum, user) VALUES (3,85, '01-04-2007', 1);"+
							"INSERT INTO brustumfang(id, umfang, datum, user) VALUES (4,90, '01-06-2007', 1);"+
							"INSERT INTO brustumfang(id, umfang, datum, user) VALUES (5,95, '01-08-2007', 1);"+
							"INSERT INTO brustumfang(id, umfang, datum, user) VALUES (6,100, '01-09-2007', 1);"+
							"INSERT INTO brustumfang(id, umfang, datum, user) VALUES (7,108, '01-11-2007', 1);"+
							"INSERT INTO brustumfang(id, umfang, datum, user) VALUES (8,115, '01-01-2008', 1);"+
							"INSERT INTO hueftenumfang(id, umfang, datum, user) VALUES (1,80, '01-01-2007', 1);"+
							"INSERT INTO hueftenumfang(id, umfang, datum, user) VALUES (2,83, '01-03-2007', 1);"+
							"INSERT INTO hueftenumfang(id, umfang, datum, user) VALUES (3,85, '01-04-2007', 1);"+
							"INSERT INTO hueftenumfang(id, umfang, datum, user) VALUES (4,90, '01-06-2007', 1);"+
							"INSERT INTO hueftenumfang(id, umfang, datum, user) VALUES (5,95, '01-08-2007', 1);"+
							"INSERT INTO hueftenumfang(id, umfang, datum, user) VALUES (6,100, '01-09-2007', 1);"+
							"INSERT INTO hueftenumfang(id, umfang, datum, user) VALUES (7,108, '01-11-2007', 1);"+
							"INSERT INTO hueftenumfang(id, umfang, datum, user) VALUES (8,115, '01-01-2008', 1);"+
							"INSERT INTO gewicht(id, umfang, datum, user) VALUES (1,59, '01-01-2007', 1);"+
							"INSERT INTO gewicht(id, umfang, datum, user) VALUES (2,70, '01-03-2007', 1);"+
							"INSERT INTO gewicht(id, umfang, datum, user) VALUES (3,75, '01-04-2007', 1);"+
							"INSERT INTO gewicht(id, umfang, datum, user) VALUES (4,80, '01-06-2007', 1);"+
							"INSERT INTO gewicht(id, umfang, datum, user) VALUES (5,85, '01-08-2007', 1);"+
							"INSERT INTO gewicht(id, umfang, datum, user) VALUES (6,90, '01-09-2007', 1);"+
							"INSERT INTO gewicht(id, umfang, datum, user) VALUES (7,92, '01-11-2007', 1);"+
							"INSERT INTO gewicht(id, umfang, datum, user) VALUES (8,95, '01-01-2008', 1);"+
							"INSERT INTO bild(bildid, bild, user) VALUES (1,'assets//images//cb1.jpg',1);"+
							"INSERT INTO bild(bildid, bild, user) VALUES (2,'assets//images//cb2.jpg',1);"+
							"INSERT INTO bild(bildid, bild, user) VALUES (3,'assets//images//cb3.jpg',1);"+
							"INSERT INTO bild(bildid, bild, user) VALUES (4,'assets//images//cb4.jpg',1);"+
							"INSERT INTO plan(id, name, user) VALUES (1,'BatmanPlan',1);"+
							"INSERT INTO ausgewaehlteuebung(plan, uebung, tag, satz) VALUES (1,24,'Montag', 3);"+
							"INSERT INTO ausgewaehlteuebung(plan, uebung, tag, satz) VALUES (1,26,'Mittwoch', 3);"+
							"INSERT INTO ausgewaehlteuebung(plan, uebung, tag, satz) VALUES (1,3,'Freitag', 3);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (1,12,10);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (2,12,10);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (3,12,10);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (4,12,15);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (5,12,15);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (6,12,15);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (7,12,25);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (8,12,25);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (9,12,25);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (10,12,32);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (11,12,32);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (12,12,32);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (13,12,40);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (14,12,40);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (15,12,40);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (16,12,8);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (17,12,9);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (18,12,10);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (19,12,14);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (20,12,14);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (21,12,14);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (22,12,23);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (23,12,24);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (24,12,24);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (25,12,36);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (26,12,36);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (27,12,36);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (28,12,44);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (29,12,44);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (30,12,43);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (31,12,13);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (32,12,12);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (33,12,11);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (34,12,17);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (35,12,16);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (36,12,15);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (37,12,25);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (38,12,26);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (39,12,27);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (40,12,36);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (41,12,36);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (42,12,35);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (43,12,43);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (44,12,43);"+
							"INSERT INTO satz(id, wh, gewicht) VALUES (45,12,43);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-01-2007',1,24,'Montag',1);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-01-2007',1,24,'Montag',2);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-01-2007',1,24,'Montag',3);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-03-2007',1,24,'Montag',4);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-03-2007',1,24,'Montag',5);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-03-2007',1,24,'Montag',6);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-06-2007',1,24,'Montag',7);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-06-2007',1,24,'Montag',8);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-06-2007',1,24,'Montag',9);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-08-2007',1,24,'Montag',10);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-08-2007',1,24,'Montag',11);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-08-2007',1,24,'Montag',12);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-10-2007',1,24,'Montag',13);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-11-2007',1,24,'Montag',14);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-12-2007',1,24,'Montag',15);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-01-2007',1,26,'Mittwoch',16);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-01-2007',1,26,'Mittwoch',17);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-01-2007',1,26,'Mittwoch',18);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-03-2007',1,26,'Mittwoch',19);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-03-2007',1,26,'Mittwoch',20);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-03-2007',1,26,'Mittwoch',21);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-06-2007',1,26,'Mittwoch',22);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-06-2007',1,26,'Mittwoch',23);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-06-2007',1,26,'Mittwoch',24);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-08-2007',1,26,'Mittwoch',25);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-08-2007',1,26,'Mittwoch',26);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-08-2007',1,26,'Mittwoch',27);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-10-2007',1,26,'Mittwoch',28);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-11-2007',1,26,'Mittwoch',29);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-12-2007',1,26,'Mittwoch',30);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-01-2007',1,3,'Freitag',31);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-01-2007',1,3,'Freitag',32);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-01-2007',1,3,'Freitag',33);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-03-2007',1,3,'Freitag',34);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-03-2007',1,3,'Freitag',35);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-03-2007',1,3,'Freitag',36);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-06-2007',1,3,'Freitag',37);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-06-2007',1,3,'Freitag',38);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-06-2007',1,3,'Freitag',39);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-08-2007',1,3,'Freitag',40);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-08-2007',1,3,'Freitag',41);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-08-2007',1,3,'Freitag',42);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-10-2007',1,3,'Freitag',43);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-11-2007',1,3,'Freitag',44);"+
							"INSERT INTO routine(datum, plan, uebung, tag, satz) VALUES ('01-12-2007',1,3,'Freitag',45);"
							
					);
				
				}				
			}
		
		
		
		stmt.close();
		return conn;
	} catch(Exception e) {
		JOptionPane.showMessageDialog(null, e);
		return null;  
	}
     
		
 }
 
	public void dbErzeugen(){
		Statement stmt = null;
		ResultSet rs = null;
		
	}

}
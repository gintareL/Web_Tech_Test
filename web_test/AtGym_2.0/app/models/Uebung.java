package models;
import java.util.*;
import play.db.ebean.Model;
import javax.persistence.Entity;

public class Uebung {
	private String name = "name";
	private String beschreibung;
	private String bild;
	private Muskel muskel;
	
	public String getName(){
	return name;
	}
}
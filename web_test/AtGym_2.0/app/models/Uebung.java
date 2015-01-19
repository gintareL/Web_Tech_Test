package models;
import java.util.*;
import play.db.ebean.Model;
import javax.persistence.Entity;

public class Uebung{
	private int id;
	private String name;
	private String equipment;
	private String grad;
	private String muskel1;
	private String muskel2;
	private String bild;
	private Muskel muskelgruppe;
	private int like;
	private int dislike;
	
	
	public Uebung( int id, String n, String e, String g, String m1, String m2, String b, Muskel m){
		this.id=id;
		name = n;
		equipment = e;
		grad = g;
		muskel1 = m1;
		muskel2 = m2;
		bild = b;
		muskelgruppe = m;
	}
	
	public Uebung( int id, String n, String e, String g, String m1, String m2, String b, Muskel m, int like, int dislike){
		this.id=id;
		name = n;
		equipment = e;
		grad = g;
		muskel1 = m1;
		muskel2 = m2;
		bild = b;
		muskelgruppe = m;
		this.like=like;
		this.dislike = dislike;
	}
	public String getName(){
	return name;
	}
	public String getMuskelgruppe(){
		if(Muskel.arme == this.muskelgruppe){
			return "Arme";
		}
		if(Muskel.beine == this.muskelgruppe){
			return "Beine";
		}
		if(Muskel.brust == this.muskelgruppe){
			return "Brust";
		}
		if(Muskel.beine == this.muskelgruppe){
			return "Beine";
		}
		if(Muskel.schultern == this.muskelgruppe){
			return "Schultern";
		}
		else{
			return "Ruecken";
		}
	}
	public String getEquipment(){
	return equipment;
	}
	public String getGrad(){
	return grad;
	}
	
	public String getMuskel1(){
	return muskel1;
	}
	
	public String getMuskel2(){
	return muskel2;
	}
	
	public int getId(){
	return id;
	}
	
	public int getLike(){
	return like;
	}
	public void setLike(int like){
		this.like=like;
		
	}
	public int getDislike(){
	return dislike;
	}
	public void setDisike(int dislike){
		this.dislike=dislike;
		
	}
	
	public String getBild(){
	return bild;
	}
}
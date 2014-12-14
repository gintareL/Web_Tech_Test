package models;
import java.util.*;

public class Uebung {
	private String name = "name";
	private String equipment;
	private String grad;
	private String muskel1;
	private String muskel2;
	private String bild;
	private Muskel muskelgruppe;
	
	
	public Uebung(String n, String e, String g, String m1, String m2, String b, Muskel m){
		name = n;
		equipment = e;
		grad = g;
		muskel1 = m1;
		muskel2 = m2;
		bild = b;
		muskelgruppe = m;
	}
	public String getName(){
	return name;
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
	

	
	public String getBild(){
	return bild;
	}
}
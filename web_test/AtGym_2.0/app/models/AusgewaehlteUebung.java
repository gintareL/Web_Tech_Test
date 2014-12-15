package models;
import java.util.*;

public class AusgewaehlteUebung{
	public Uebung uebung; 
	public Satz[] saetze;
	public String wh;
	public AusgewaehlteUebung(Uebung u, int anzahl){
		uebung = u;
		saetze = new Satz[anzahl];
		wh = String.valueOf(anzahl);
	}
	
	public Uebung getUebung(){
		return uebung;
	}
	
	public String getWh(){
	return wh;
	}
	
}
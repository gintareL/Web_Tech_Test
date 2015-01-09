package models;
import java.util.*;
//import java.util.stream.*;

public class TagPlan {
	public Tag tag;
	private Set<AusgewaehlteUebung> uebungen = new HashSet<AusgewaehlteUebung>();
	
	
	public void loeschen(int u){
		AusgewaehlteUebung zuLoeschen=null;
		for(AusgewaehlteUebung a : uebungen){
			if(a.getUebung().getId()==u){
				zuLoeschen = a;
			}
		}
		if(zuLoeschen!=null){
			uebungen.remove(zuLoeschen);
		}
	
	}
	public Set<AusgewaehlteUebung> getUebungen(){
		return uebungen;
	}
	
	public String getTag(){
	if(tag == Tag.Mittwoch) return "Mittwoch";
	else if(tag == Tag.Montag) return "Montag";
	else if(tag == Tag.Dienstag) return "Dienstag";
	else if(tag == Tag.Donnerstag) return "Donnerstag";
	else if(tag == Tag.Freitag) return "Freitag";
	else if(tag == Tag.Samstag) return "Samstag";
	else if(tag == Tag.Sonntag) return "Sonntag";
	
	return null;
	}
}
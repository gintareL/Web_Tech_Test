package models;
import java.util.*;
//import java.util.stream.*;

public class TagPlan {
	public Tag tag;
	public Set<AusgewaehlteUebung> uebungen = new HashSet<AusgewaehlteUebung>();
	
	public Set<AusgewaehlteUebung> getUebungen(){
	return uebungen;
	}
	public void loeschen(AusgewaehlteUebung u){
	uebungen.remove(u);
	}
	
	public String getTag(){
	if(tag == Tag.Mittwoch) return "Mittwoch";
	else if(tag == Tag.Montag) return "Montag";
	return null;
	}
}
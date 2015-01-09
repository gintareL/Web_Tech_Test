package models;
import java.util.*;

public class Plan{
	private int id;
	public String name;
	public Map<Tag, TagPlan> uebungen = new HashMap<Tag, TagPlan>();
	
	public Plan(String name, Map<Tag, TagPlan> uebungen){
	
	this.name = name;
	this.uebungen = uebungen;
	}

	public Plan(int id, String name, Map<Tag, TagPlan> uebungen){
	this.id=id;
	this.name = name;
	this.uebungen = uebungen;
	}
	
	public String getName(){
	return name;
	}
	
	public Map<Tag, TagPlan> getUebungen(){
	return uebungen;
	}
	
	public int getId(){
	return id;
	}

	public void setId(int id){
	this.id=id;
	}
}
package models;

import java.util.*;
import play.db.ebean.Model;
import play.data.validation.Constraints.*;
import javax.persistence.Entity;


public class Models extends Model{
	private static Models instance = null;
	private static User user;
   private Models() {
      // Exists only to defeat instantiation.
   }
   public static Models getInstance() {
      if(instance == null) {
         instance = new Models();
      }
      return instance;
   }
   
   public void neuerUser(User u){
	this.user = u;
	 plan();
   }
   
   public User aktuellUser(){
   return user;
   }
   
   //TEST
   public void plan(){
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
   }
}
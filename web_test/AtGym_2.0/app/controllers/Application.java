package controllers;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.libs.Json;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.*;
import play.mvc.*;
import play.api.data.*;
import play.api.data.Forms.*;
import play.data.*;
import play.data.Form;
import models.*;
import views.html.*;
import javax.swing.*;
public class Application extends Controller {
	final static Form<User> loginForm = Form.form(User.class); 
	final static Models model = Models.getInstance();
	final static Form<Gewicht> gewichtForm = Form.form(Gewicht.class); 
	
	public static Result login() {
    	session().clear();
    	Form<User> userForm = Form.form(User.class);
		
    	return ok(logIn.render(userForm));
		
    }
	
	
	
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
	
	
	public static Result home1() {
		Form<User> userForm = Form.form(User.class).bindFromRequest();
    	if(userForm.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/atGym");
    	}else{
		
			User u = userForm.get();
					
			if(u.getEmail() != null & u.getPassword() != null ){
				User user;
				if(u.getVorname() == null  && model.checkUser(u.getEmail(), u.getPassword()) == true){
					user = model.aktuellUser();
					session("User1", user.getVorname());
					return ok(home_boot.render(user));
				} else if(u.getVorname() != null && model.neuerUser(userForm.get())==true){
					user = model.aktuellUser();
					session("User1", user.getVorname());
					return ok(home_boot.render(user));
			}
			}
			
		else{
			JOptionPane.showMessageDialog(null, "Diese E-Mail ist schon registriert");
			return redirect("/atGym");
			} 
	}return redirect("/atGym");
	}
	public static Result home(){
		User user = model.aktuellUser();
	String username = session("User1");
	
	
	if(username != null) {
		return ok(home.render(user));
    }else{
		return redirect("/atGym");
    	}	
	}
	
	public static Result ourGym(){
	User user = model.aktuellUser();
	String username = session("User1");
	if(username != null) {
		return ok(our_gym_boot.render(user, username));
    }else{
		return redirect("/atGym");
    	}	
	}
	
	public static Result myGym(){
		User user = model.aktuellUser();
		String username = session("User1");
		
		String geschlecht = user.getGeschlecht();
		if(username != null && geschlecht != null) {
			return ok(myGym.render(user, username, geschlecht));
		}else{
			return redirect("/atGym");
			}
	}
	
/*	public static Result hello() {
    DynamicForm requestData = Form.form().bindFromRequest();
    double gewicht = requestData.get("gewicht");
   
    return ok();
}*/
	
	public static Result aboutMeg(){
		Form<Gewicht> gewichtForm = Form.form(Gewicht.class).bindFromRequest();
		if(gewichtForm.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/aboutMe");
    	}else{
		
		
			Gewicht g = gewichtForm.get();
			User user = model.aktuellUser();
			user.setGewicht(g);
			model.gewichtCheck();
			System.out.println(g.getGewicht());
		return redirect("/aboutMe");
		
		}
	}
	
	public static Result aboutMeb(){
		Form<Bauchumfang> bauchForm = Form.form(Bauchumfang.class).bindFromRequest();
		if(bauchForm.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/aboutMe");
    	}else{
		
		
			Bauchumfang g = bauchForm.get();
			User user = model.aktuellUser();
			System.out.println(g.getUmfang());
			user.setBauchumfang(g);
			model.bauchumfangCheck();
			
		return redirect("/aboutMe");
		
		}
	}
	
	public static Result aboutMeh(){
		Form<Hueftenumfang> hueftenForm = Form.form(Hueftenumfang.class).bindFromRequest();
		if(hueftenForm.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/aboutMe");
    	}else{
		
		
			Hueftenumfang g = hueftenForm.get();
			User user = model.aktuellUser();
			
			user.setHueftenumfang(g);
			model.hueftenumfangCheck();
			
		return redirect("/aboutMe");
		
		}
	}
	
		public static Result aboutMea(){
		Form<Armumfang> armForm = Form.form(Armumfang.class).bindFromRequest();
		if(armForm.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/aboutMe");
    	}else{
		
		
			Armumfang g = armForm.get();
			User user = model.aktuellUser();
			
			user.setArmumfang(g);
			model.armumfangCheck();
			
		return redirect("/aboutMe");
		
		}
	}
	
		public static Result aboutMebr(){
		Form<Brustumfang> brustForm = Form.form(Brustumfang.class).bindFromRequest();
		if(brustForm.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/aboutMe");
    	}else{
		
		
			Brustumfang g = brustForm.get();
			User user = model.aktuellUser();
			
			user.setBrustumfang(g);
			model.brustumfangCheck();
			
		return redirect("/aboutMe");
		
		}
	}
	
	
	public static Result aboutMe(){
		Form<Gewicht> gewichtForm = Form.form(Gewicht.class);
		Form<Bauchumfang> bauchForm = Form.form(Bauchumfang.class);
		Form<Hueftenumfang> hueftenForm = Form.form(Hueftenumfang.class);
		Form<Armumfang> armForm = Form.form(Armumfang.class);
		Form<Brustumfang> brustForm = Form.form(Brustumfang.class);
	User user = model.aktuellUser();
	String username = session("User1");
	String geschlecht = session("User4");
	String bild = session("bild");
	if(username != null) {
		
		return ok(aboutMe.render(user, username, geschlecht, bild, gewichtForm, bauchForm, hueftenForm, armForm, brustForm));
    }else{
		return redirect("/atGym");
    	}
	}
	
	public static Result myPlans(){
		User user = model.aktuellUser();
	String username = session("User1");
		if(username != null) {
			return ok(myPlans.render(user));
		}else{
			return redirect("/atGym");
			}
	}
	
	public static Result myRoutine(){
		String username = session("User1");
		if(username != null) {
			return ok(myRoutine.render(username));
		}else{
			return redirect("/atGym");
			}
	}
	
	public static Result myAnalyse(){
	String username = session("User1");
		if(username != null) {
			return ok(myAnalyse.render(username));
		}else{
			return redirect("/atGym");
			}
	}
	
	public static Result auswaehlenBeine(){
		Form<Auswaehlen> uebungForm = Form.form(Auswaehlen.class).bindFromRequest();
		if(uebungForm.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/beine");
    	}else{
			
			Auswaehlen g = uebungForm.get();
			User user = model.aktuellUser();
			model.planHinzufuegen(g.getId(), g.getSatz(), g.getTag(), g.getPlan());
			
			
			
		return redirect("/beine");
		
		}
	}
	public static Result auswaehlenBauch(){
		Form<Auswaehlen> uebungForm = Form.form(Auswaehlen.class).bindFromRequest();
		if(uebungForm.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/bauch");
    	}else{
			
			Auswaehlen g = uebungForm.get();
			User user = model.aktuellUser();
			model.planHinzufuegen(g.getId(), g.getSatz(), g.getTag(), g.getPlan());
			
			
			
		return redirect("/bauch");
		
		}
	}
	public static Result auswaehlenBrust(){
		Form<Auswaehlen> uebungForm = Form.form(Auswaehlen.class).bindFromRequest();
		if(uebungForm.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/brust");
    	}else{
			
			Auswaehlen g = uebungForm.get();
			User user = model.aktuellUser();
			model.planHinzufuegen(g.getId(), g.getSatz(), g.getTag(), g.getPlan());
			
			
			
		return redirect("/brust");
		
		}
	}
	
	public static Result auswaehlenArme(){
		Form<Auswaehlen> uebungForm = Form.form(Auswaehlen.class).bindFromRequest();
		if(uebungForm.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/arme");
    	}else{
			
			Auswaehlen g = uebungForm.get();
			User user = model.aktuellUser();
			model.planHinzufuegen(g.getId(), g.getSatz(), g.getTag(), g.getPlan());
			
			
			
		return redirect("/arme");
		
		}
	}
	public static Result auswaehlenRuecken(){
		Form<Auswaehlen> uebungForm = Form.form(Auswaehlen.class).bindFromRequest();
		if(uebungForm.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/ruecken");
    	}else{
			
			Auswaehlen g = uebungForm.get();
			User user = model.aktuellUser();
			model.planHinzufuegen(g.getId(), g.getSatz(), g.getTag(), g.getPlan());
			
			
			
		return redirect("/ruecken");
		
		}
	}
	
	public static Result auswaehlenSchultern(){
		Form<Auswaehlen> uebungForm = Form.form(Auswaehlen.class).bindFromRequest();
		if(uebungForm.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/schultern");
    	}else{
			
			Auswaehlen g = uebungForm.get();
			User user = model.aktuellUser();
			model.planHinzufuegen(g.getId(), g.getSatz(), g.getTag(), g.getPlan());
			
			
			
		return redirect("/schultern");
		
		}
	}
	
	
	public static Result beine(){
		Form<Auswaehlen> uebungenForm = Form.form(Auswaehlen.class);
		User user = model.aktuellUser();
		SortedMap<Integer, Uebung> beineUebungen = model.beine();
		String username = session("User1");
		if(username != null) {
			return ok(beine.render(user, beineUebungen, uebungenForm));
		}else{
			return redirect("/atGym");
			}
	}
	public static Result bauch(){
		Form<Auswaehlen> uebungenForm = Form.form(Auswaehlen.class);
		User user = model.aktuellUser();
		SortedMap<Integer, Uebung> bauchUebungen = model.bauch();
		String username = session("User1");
		
		if(username != null) {
			return ok(bauch.render(user, bauchUebungen, uebungenForm));
		}else{
			return redirect("/atGym");
			}
	}
	
	public static Result arme(){
		Form<Auswaehlen> uebungenForm = Form.form(Auswaehlen.class);
		User user = model.aktuellUser();
		SortedMap<Integer, Uebung> armeUebungen = model.arme();
		String username = session("User1");
		
		if(username != null) {
			return ok(arme.render(user, armeUebungen, uebungenForm));
		}else{
			return redirect("/atGym");
			}
	}
	
	
	
	public static Result brust(){
		Form<Auswaehlen> uebungenForm = Form.form(Auswaehlen.class);
		User user = model.aktuellUser();
		SortedMap<Integer, Uebung> brustUebungen = model.brust();
	String username = session("User1");
		if(username != null) {
			return ok(brust.render(user, brustUebungen, uebungenForm));
		}else{
			return redirect("/atGym");
			}
	}
	
	
	
	public static Result ruecken(){
		Form<Auswaehlen> uebungenForm = Form.form(Auswaehlen.class);
		User user = model.aktuellUser();
		SortedMap<Integer, Uebung> rueckenUebungen = model.ruecken();

		String username = session("User1");
		if(username != null) {
			return ok(ruecken.render(user, rueckenUebungen, uebungenForm));
		}else{
			return redirect("/atGym");
			}
	}
	
	public static Result schultern(){
		Form<Auswaehlen> uebungenForm = Form.form(Auswaehlen.class);
		User user = model.aktuellUser();
		SortedMap<Integer, Uebung> schulternUebungen = model.schultern();

	String username = session("User1");
		if(username != null) {
		
			return ok(schultern.render(user, schulternUebungen, uebungenForm));
		}else{
			return redirect("/atGym");
			}
	}
	
	public static Result vipPlaene(){
		User user = model.aktuellUser();
	String username = session("User1");
		if(username != null) {
			return ok(vipPlaene.render(user));
		}else{
			return redirect("/atGym");
			}
	}
	
	public static class LogIn {
	public String email;
	public String password;

}

}

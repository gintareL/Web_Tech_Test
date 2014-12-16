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
			
			if(model.neuerUser(userForm.get())==true){
    		User user = model.aktuellUser();
			
			
    		session("User1", user.getVorname());
			
			
    		return ok(home_boot.render(user));
			}else{
			JOptionPane.showMessageDialog(null, "Diese E-Mail ist schon registriert");
			return redirect("/atGym");
			} 
	}
	}
	public static Result home(){
	String username = session("User1");
	
	
	if(username != null) {
		return ok(home.render(username));
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
		String geschlecht = session("User4");
		if(username != null && geschlecht != null) {
			return ok(myGym.render(user, username, geschlecht));
		}else{
			return redirect("/atGym");
			}
	}
	
	public static Result aboutMe(){
	User user = model.aktuellUser();
	String username = session("User1");
	String geschlecht = session("User4");
	String bild = session("bild");
	if(username != null) {
		return ok(aboutMe.render(user, username, geschlecht, bild));
    }else{
		return redirect("/atGym");
    	}
	}
	
	public static Result myPlans(){
	String username = session("User1");
		if(username != null) {
			return ok(myPlans.render(username));
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
	
	public static Result beine(){
		User user = model.aktuellUser();
		SortedMap<Integer, Uebung> beineUebungen = model.beine();
		String username = session("User1");
		if(username != null) {
			return ok(beine.render(user, beineUebungen));
		}else{
			return redirect("/atGym");
			}
	}
	public static Result bauch(){
		User user = model.aktuellUser();
		SortedMap<Integer, Uebung> bauchUebungen = model.bauch();
		String username = session("User1");
		
		if(username != null) {
			return ok(bauch.render(user, bauchUebungen));
		}else{
			return redirect("/atGym");
			}
	}
	public static Result arme(){
	String username = session("User1");
		if(username != null) {
			return ok(arme.render(username));
		}else{
			return redirect("/atGym");
			}
	}
	
	public static Result brust(){
	String username = session("User1");
		if(username != null) {
			return ok(brust.render(username));
		}else{
			return redirect("/atGym");
			}
	}
	
	public static Result fertigePlaene(){
	String username = session("User1");
		if(username != null) {
			return ok(fertigePlaene.render(username));
		}else{
			return redirect("/atGym");
			}
	}
	
	public static Result ruecken(){
		String username = session("User1");
		if(username != null) {
			return ok(ruecken.render(username));
		}else{
			return redirect("/atGym");
			}
	}
	
	public static Result schultern(){
	String username = session("User1");
		if(username != null) {
		
			return ok(schultern.render(username));
		}else{
			return redirect("/atGym");
			}
	}
	
	public static Result vipPlaene(){
	String username = session("User1");
		if(username != null) {
			return ok(vipPlaene.render(username));
		}else{
			return redirect("/atGym");
			}
	}
	
	public static class LogIn {
	public String email;
	public String password;

}

}

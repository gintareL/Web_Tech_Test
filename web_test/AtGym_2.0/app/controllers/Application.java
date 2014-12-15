package controllers;
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
    		return badRequest(logIn.render(userForm));
    	}else{
			
			if(model.neuerUser(userForm.get())==true){
    		User user = model.aktuellUser();
			session().clear();
			session("User4", user.getGeschlecht());
			session("bild", user.getBild());
    		session("User1", user.getVorname());
			session("User2", user.getNachname());
			session("User3", user.getEmail());
			
    		return ok(home_boot.render(user));
			}else{
			JOptionPane.showMessageDialog(null, "Diese E-Mail ist schon registriert");
			return badRequest(logIn.render(userForm));
			} 
	}
	}
	public static Result home(){
	String username = session("User1");
	String nachname = session("User2");
	String email = session("User3");
	
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
		String username = session("User1");
		if(username != null) {
			return ok(beine.render(username));
		}else{
			return redirect("/atGym");
			}
	}
	public static Result bauch(){
		String username = session("User1");
		if(username != null) {
			return ok(bauch.render(username));
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
	
	public static Result einloggen() {
		return ok(
			einloggen.render(form(Einloggen.class))
		);
	}
	
	public static Result authenticate() {
		Form<Login> loginForm = form(Einloggen.class).bindFromRequest();
		if(loginForm.hasErrors()) {
			return badRequest(einloggen.render(loginForm));
		} else{
			session().clear();
			session("email", loginForm.get().email);
			return redirect("/home");
		}
	}
	
	public static class Einloggen {
	public String email;
	public String password;
	
		public String validate() {
			if(User.authenticate(email, password) == null) {
				return "Invalid user or password";
			}
			return null;
		}
	}

	/*public static WebSocket<String> feedback() {
		WebSocket<String> ws = null;
		
		ws = new WebSocket<String>(){
			public void onReady(WebSocket.In<String> in, final WebSocket.Out<String> out) {
			
				//For each event received on the socket
				in.onMessage(new Callback<String>() {
					public void invoke(String event) {
						int counter++;
						String likes = counter.toString();
						out.write(likes);
					}
				});
				
				in.onClose(newCallback0() {
					public void invoke(){
						System.out.println("Disconnected!");
					}
				});
			}
		};
		return ws;
	}*/
	
	}
	
	

package controllers;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.mvc.Http.MultipartFormData.FilePart;
import play.libs.Json;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.*;
import play.mvc.*;
import play.mvc.Http.*;
import play.mvc.Http.RequestBody;
import play.api.data.*;
import play.api.data.Forms.*;
import play.data.*;
import play.data.Form;
import models.*;
import views.html.*;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
public class Application_fehler extends Controller {
	final static Form<User> loginForm = Form.form(User.class); 
	final static Models model = Models.getInstance();
	final static Form<Gewicht> gewichtForm = Form.form(Gewicht.class); 
	private static List<WebSocket.Out<JsonNode>> lobbyConnection = new ArrayList<WebSocket.Out<JsonNode>>();
	
	public static Result login() {
    	session().clear();
    	Form<User> userForm = Form.form(User.class);
		
    	return ok(logIn.render(userForm));
		
    }
	
	
	
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
	
	
	public static Result home1() {
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class);
		Form<User> userForm = Form.form(User.class).bindFromRequest();
    	if(userForm.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/atGym");
    	}else{
		
			User u = userForm.get();
					
			if(u.getEmail() != null & u.getPassword() != null ){
				User user;
				if(u.getVorname() == null  && model.checkUser(u.getEmail(), u.getPassword()) == true){
					System.out.println(u.getEmail());
					user = model.aktuellUser(u.getEmail());
					session("User1", user.getVorname());
					//session("email", user.getEmail());
					return ok(home_boot.render(user, uebungLoeschen));
				} else if(u.getVorname() != null && model.neuerUser(userForm.get())==true){
					user = model.aktuellUser(u.getEmail());
					//session("User1", user.getVorname());
					session("email", user.getEmail());
					return ok(home_boot.render(user, uebungLoeschen));
			}
			}
			
		else{
			JOptionPane.showMessageDialog(null, "Diese E-Mail ist schon registriert");
			return redirect("/atGym");
			} 
	}return redirect("/atGym");
	}
	public static Result home(){
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class);
		String email = session("email");
		User user = model.aktuellUser(email);
	String username = session("User1");
	
	
	if(username != null) {
		return ok(home.render(user, uebungLoeschen));
    }else{
		return redirect("/atGym");
    	}	
	}
	
	public static Result ourGym(){
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class);
		String email = session("email");
	User user = model.aktuellUser(email);
	String username = session("User1");
	if(username != null) {
		return ok(our_gym_boot.render(user, username, uebungLoeschen));
    }else{
		return redirect("/atGym");
    	}	
	}
	
	public static Result myGym(){
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class);
		String email = session("email");
		User user = model.aktuellUser(email);
		String username = session("User1");
		
		String geschlecht = user.getGeschlecht();
		if(username != null && geschlecht != null) {
			return ok(myGym.render(user, username, geschlecht, uebungLoeschen));
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
			String email = session("email");
			User user = model.aktuellUser(email);
			user.setGewicht(g);
			model.gewichtCheck(user);
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
		
		String email = session("email");
			Bauchumfang g = bauchForm.get();
			User user = model.aktuellUser(email);
			System.out.println(g.getUmfang());
			user.setBauchumfang(g);
			model.bauchumfangCheck(user);
			
		return redirect("/aboutMe");
		
		}
	}
	
	public static Result aboutMeh(){
		Form<Hueftenumfang> hueftenForm = Form.form(Hueftenumfang.class).bindFromRequest();
		if(hueftenForm.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/aboutMe");
    	}else{
		
		String email = session("email");
			Hueftenumfang g = hueftenForm.get();
			User user = model.aktuellUser(email);
			
			user.setHueftenumfang(g);
			model.hueftenumfangCheck(user);
			
		return redirect("/aboutMe");
		
		}
	}
	
		public static Result aboutMea(){
		Form<Armumfang> armForm = Form.form(Armumfang.class).bindFromRequest();
		if(armForm.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/aboutMe");
    	}else{
		
		String email = session("email");
			Armumfang g = armForm.get();
			User user = model.aktuellUser(email);
			
			user.setArmumfang(g);
			model.armumfangCheck(user);
			
		return redirect("/aboutMe");
		
		}
	}
	
		public static Result aboutMebr(){
		Form<Brustumfang> brustForm = Form.form(Brustumfang.class).bindFromRequest();
		if(brustForm.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/aboutMe");
    	}else{
		
		String email = session("email");
			Brustumfang g = brustForm.get();
			User user = model.aktuellUser(email);
			
			user.setBrustumfang(g);
			model.brustumfangCheck(user);
			
		return redirect("/aboutMe");
		
		}
	}
	
	public static Result upload() {
	  MultipartFormData body = request().body().asMultipartFormData();
	  FilePart picture = body.getFile("picture");
	  if (picture != null) {
		  String email = session("email");
		  User user = model.aktuellUser(email);
		String fileName = picture.getFilename();
		
		String contentType = picture.getContentType(); 
		File file = picture.getFile();
		model.imageSave(user, file.getPath());
	
		return redirect("/aboutMe");
	  } else {
		flash("error", "Missing file");
		return redirect("/aboutMe"); 
	  }
	}
	
	public static Result aboutMe(){
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class);
		Form<Gewicht> gewichtForm = Form.form(Gewicht.class);
		Form<Bauchumfang> bauchForm = Form.form(Bauchumfang.class);
		Form<Hueftenumfang> hueftenForm = Form.form(Hueftenumfang.class);
		Form<Armumfang> armForm = Form.form(Armumfang.class);
		Form<Brustumfang> brustForm = Form.form(Brustumfang.class);
	 String email = session("email");
		  User user = model.aktuellUser(email);
	String username = session("User1");
	
	if(username != null) {
		
		return ok(aboutMe.render(user, gewichtForm, bauchForm, hueftenForm, armForm, brustForm, uebungLoeschen));
    }else{
		return redirect("/atGym");
    	}
	}
	
	public static Result myPlans(){
		Form<PlanLoeschen> planLoeschen = Form.form(PlanLoeschen.class);
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class);
		 String email = session("email");
		  User user = model.aktuellUser(email);
	String username = session("User1");
		if(username != null) {
			return ok(myPlans.render(user, uebungLoeschen, planLoeschen));
		}else{
			return redirect("/atGym");
			}
	}
	
	public static Result myRoutine(){
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class);
		Form<Satz> satzSave = Form.form(Satz.class);
		 String email = session("email");
		  User user = model.aktuellUser(email);
		String username = session("User1");
		if(username != null) {
			return ok(myRoutine.render(user, uebungLoeschen, satzSave));
		}else{
			return redirect("/atGym");
			}
	}
	
	public static Result myAnalyse(){
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class);
		 String email = session("email");
		  User user = model.aktuellUser(email);
	String username = session("User1");
		if(username != null) {
			return ok(myAnalyse.render(user, uebungLoeschen));
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
			 String email = session("email");
		  User user = model.aktuellUser(email);
			model.planHinzufuegen(user, g.getId(), g.getSatz(), g.getTag(), g.getPlan());
			
			
			
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
			 String email = session("email");
			User user = model.aktuellUser(email);
			model.planHinzufuegen(user, g.getId(), g.getSatz(), g.getTag(), g.getPlan());
			
			
			
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
			 String email = session("email");
		  User user = model.aktuellUser(email);
			model.planHinzufuegen(user, g.getId(), g.getSatz(), g.getTag(), g.getPlan());
			
			
			
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
			 String email = session("email");
		  User user = model.aktuellUser(email);
			model.planHinzufuegen(user, g.getId(), g.getSatz(), g.getTag(), g.getPlan());
			
			
			
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
			 String email = session("email");
		  User user = model.aktuellUser(email);
			model.planHinzufuegen(user, g.getId(), g.getSatz(), g.getTag(), g.getPlan());
			
			
			
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
			 String email = session("email");
		  User user = model.aktuellUser(email);
			model.planHinzufuegen(user, g.getId(), g.getSatz(), g.getTag(), g.getPlan());
			
			
			
		return redirect("/schultern");
		
		}
	}
		public static WebSocket<String> guess() {
		 WebSocket<String> ws = null;
			// final int r = Integer.parseInt(session("random"));
		/*		 ws = new WebSocket<String>() {
					 public void onReady(WebSocket.In<String> in, final WebSocket.Out<String> out) {
							in.onMessage(new Callback<String>() {
								 public void invoke(String g) {
								 int guess = Integer.parseInt(g);
								 String res = "< secret number!";
								 if (guess > r) res = "> secret number!";
								 else if (guess == r) res = "correct!";
								 out.write(res);
								 }
							});
					in.onClose(new Callback0() {
						 public void invoke() {
						 System.out.println("Disconnected!");
						 }
					});
				 }
			 };*/
		 return ws;
		 }
	
	
	public static Result beine(){
		Form<Like> likes = Form.form(Like.class);
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class);
		Form<Auswaehlen> uebungenForm = Form.form(Auswaehlen.class);
		 String email = session("email");
		  User user = model.aktuellUser(email);
		SortedMap<Integer, Uebung> beineUebungen = model.beine();
		String username = session("User1");
		if(username != null) {
			return ok(beine.render(user, beineUebungen, uebungenForm, uebungLoeschen, likes));
		}else{
			return redirect("/atGym");
			}
	}
	public static Result bauch(){
		Form<Like> likes = Form.form(Like.class);
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class);
		Form<Auswaehlen> uebungenForm = Form.form(Auswaehlen.class);
		 String email = session("email");
		  User user = model.aktuellUser(email);
		SortedMap<Integer, Uebung> bauchUebungen = model.bauch();
		String username = session("User1");
		
		if(username != null) {
			return ok(bauch.render(user, bauchUebungen, uebungenForm, uebungLoeschen, likes));
		}else{
			return redirect("/atGym");
			}
	}
	
	public static Result arme(){
		Form<Like> likes = Form.form(Like.class);
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class);
		Form<Auswaehlen> uebungenForm = Form.form(Auswaehlen.class);
		 String email = session("email");
		  User user = model.aktuellUser(email);
		SortedMap<Integer, Uebung> armeUebungen = model.arme();
		String username = session("User1");
		
		if(username != null) {
			return ok(arme.render(user, armeUebungen, uebungenForm, uebungLoeschen, likes));
		}else{
			return redirect("/atGym");
			}
	}
	
	
	
	public static Result brust(){
		Form<Like> likes = Form.form(Like.class);
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class);
		Form<Auswaehlen> uebungenForm = Form.form(Auswaehlen.class);
		 String email = session("email");
		  User user = model.aktuellUser(email);
		SortedMap<Integer, Uebung> brustUebungen = model.brust();
	String username = session("User1");
		if(username != null) {
			return ok(brust.render(user, brustUebungen, uebungenForm, uebungLoeschen, likes));
		}else{
			return redirect("/atGym");
			}
	}
	
	public static Result like(){
		Form<Like> likes = Form.form(Like.class).bindFromRequest();
		if(likes.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/arme");
    	}else{
		
		String email = session("email");
		  User user = model.aktuellUser(email);
			Like l = likes.get();
			
			model.like(l.getId());
			String muskelgruppe = l.getMuskelgruppe();
			System.out.println(muskelgruppe + " " + l.getId());
			
			
			if(muskelgruppe.equals("Arme")){
				return redirect("/arme");
			} else if(muskelgruppe.equals("Bauch")){
				return redirect("/bauch");
			} else if(muskelgruppe.equals("Beine")){
				return redirect("/beine");
			} else if(muskelgruppe.equals("Brust")){
				return redirect("/brust");
			}else if(muskelgruppe.equals("Schultern")){
				return redirect("/schultern");
			} else {
				return redirect("/ruecken");
			}
			
		
		
		}
	}
	public static Result dislike(){
		Form<Like> dislikes = Form.form(Like.class).bindFromRequest();
		if(dislikes.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/arme");
    	}else{
		
		
			Like l = dislikes.get();
			String email = session("email");
		  User user = model.aktuellUser(email);
			model.dislike(l.getId());
			
			
			
		return redirect("/arme");
		
		}
	}
	
	public static Result ruecken(){
		Form<Like> likes = Form.form(Like.class);
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class);
		Form<Auswaehlen> uebungenForm = Form.form(Auswaehlen.class);
		 String email = session("email");
		  User user = model.aktuellUser(email);
		SortedMap<Integer, Uebung> rueckenUebungen = model.ruecken();

		String username = session("User1");
		if(username != null) {
			return ok(ruecken.render(user, rueckenUebungen, uebungenForm, uebungLoeschen, likes));
		}else{
			return redirect("/atGym");
			}
	}
	
	public static Result schultern(){
		Form<Like> likes = Form.form(Like.class);
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class);
		Form<Auswaehlen> uebungenForm = Form.form(Auswaehlen.class);
		 String email = session("email");
		  User user = model.aktuellUser(email);
		SortedMap<Integer, Uebung> schulternUebungen = model.schultern();

	String username = session("User1");
		if(username != null) {
		
			return ok(schultern.render(user, schulternUebungen, uebungenForm, uebungLoeschen, likes));
		}else{
			return redirect("/atGym");
			}
	}
	
	
	public static Result uebungLoeschenBrust(){
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
		if(uebungLoeschen.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/brust");
    	}else{
		
		
			uebungLoeschen g = uebungLoeschen.get();
			 String email = session("email");
		  User user = model.aktuellUser(email);
			model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
			System.out.println(g.getPlan());
			System.out.println(g.getUebung());
			System.out.println(g.getTag());
			
			
		return redirect("/brust");
		
		}
		
	}
	
	public static Result uebungLoeschenBeine(){
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
		if(uebungLoeschen.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/beine");
    	}else{
		
		
			uebungLoeschen g = uebungLoeschen.get();
			 String email = session("email");
			User user = model.aktuellUser(email);
			model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
			System.out.println(g.getPlan());
			System.out.println(g.getUebung());
			System.out.println(g.getTag());
			
			
		return redirect("/beine");
		
		}
		
	}
	
	
	public static Result uebungLoeschenBauch(){
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
		if(uebungLoeschen.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/bauch");
    	}else{
		
		
			uebungLoeschen g = uebungLoeschen.get();
			 String email = session("email");
			User user = model.aktuellUser(email);
			model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
			System.out.println(g.getPlan());
			System.out.println(g.getUebung());
			System.out.println(g.getTag());
			
			
		return redirect("/bauch");
		
		}
		
	}
	
	public static Result uebungLoeschenArme(){
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
		if(uebungLoeschen.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/arme");
    	}else{
		
		
			uebungLoeschen g = uebungLoeschen.get();
			 String email = session("email");
			User user = model.aktuellUser(email);
			model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
			System.out.println(g.getPlan());
			System.out.println(g.getUebung());
			System.out.println(g.getTag());
			
			
		return redirect("/arme");
		
		}
		
	}
	
	public static Result uebungLoeschenSchultern(){
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
		if(uebungLoeschen.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/schultern");
    	}else{
		
		
			uebungLoeschen g = uebungLoeschen.get();
			 String email = session("email");
			User user = model.aktuellUser(email);
			model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
			System.out.println(g.getPlan());
			System.out.println(g.getUebung());
			System.out.println(g.getTag());
			
			
		return redirect("/schultern");
		
		}
		
	}
	
	public static Result uebungLoeschenRuecken(){
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
		if(uebungLoeschen.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/ruecken");
    	}else{
		
		
			uebungLoeschen g = uebungLoeschen.get();
			 String email = session("email");
		  User user = model.aktuellUser(email);
			model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
			System.out.println(g.getPlan());
			System.out.println(g.getUebung());
			System.out.println(g.getTag());
			
			
		return redirect("/ruecken");
		
		}
		
	}
	
	public static Result uebungLoeschenMyGym(){
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
		if(uebungLoeschen.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/myGym");
    	}else{
		
		
			uebungLoeschen g = uebungLoeschen.get();
			 String email = session("email");
		  User user = model.aktuellUser(email);
			model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
			System.out.println(g.getPlan());
			System.out.println(g.getUebung());
			System.out.println(g.getTag());
			
			
		return redirect("/myGym");
		
		}
		
	}
	
	public static Result uebungLoeschenAboutMe(){
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
		if(uebungLoeschen.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/aboutMe");
    	}else{
		
		
			uebungLoeschen g = uebungLoeschen.get();
			 String email = session("email");
		  User user = model.aktuellUser(email);
			model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
			System.out.println(g.getPlan());
			System.out.println(g.getUebung());
			System.out.println(g.getTag());
			
			
		return redirect("/aboutMe");
		
		}
		
	}
	
	public static Result uebungLoeschenMyPlans(){
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
		if(uebungLoeschen.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/myPlans");
    	}else{
		
		
			uebungLoeschen g = uebungLoeschen.get();
			 String email = session("email");
		  User user = model.aktuellUser(email);
			model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
			System.out.println(g.getPlan());
			System.out.println(g.getUebung());
			System.out.println(g.getTag());
			
			
		return redirect("/myPlans");
		
		}
		
	}
	
	public static Result planLoeschen(){
		Form<PlanLoeschen> planLoeschen = Form.form(PlanLoeschen.class).bindFromRequest();
		if(planLoeschen.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/myPlans");
    	}else{
		
		
			PlanLoeschen g = planLoeschen.get();
			 String email = session("email");
		  User user = model.aktuellUser(email);
			model.planLoeschenKomplett(user, g.getPlan());
			
			
		return redirect("/myPlans");
		
		}
		
	}
	
	public static Result uebungLoeschenMyRoutine(){
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
		if(uebungLoeschen.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/myRoutine");
    	}else{
		
		
			uebungLoeschen g = uebungLoeschen.get();
			 String email = session("email");
		  User user = model.aktuellUser(email);
			model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
			System.out.println(g.getPlan());
			System.out.println(g.getUebung());
			System.out.println(g.getTag());
			
			
		return redirect("/myRoutine");
		
		}
		
	}
	
	public static Result satzSave(){
		Form<Satz> satzSave = Form.form(Satz.class).bindFromRequest();
		if(satzSave.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/myRoutine");
    	}else{
		
		
			Satz g = satzSave.get();
			
			 String email = session("email");
		  User user = model.aktuellUser(email);
			model.routineStep1(user, g.getPlan(), g.getUebung(), g.getTag(), g.getWh(), g.getGewicht(), g.getSatz());
			
		}
		return redirect("/myRoutine");
	}
	
	
	public static Result uebungLoeschenAnalyse(){
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
		if(uebungLoeschen.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/myAnalyse");
    	}else{
		
		
			uebungLoeschen g = uebungLoeschen.get();
			 String email = session("email");
		  User user = model.aktuellUser(email);
			model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
			System.out.println(g.getPlan());
			System.out.println(g.getUebung());
			System.out.println(g.getTag());
			
			
		return redirect("/myAnalyse");
		
		}
		
	}
	
	public static Result uebungLoeschenHome(){
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
		System.out.println("Home");
		if(uebungLoeschen.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/home");
    	}else{
		
		
			uebungLoeschen g = uebungLoeschen.get();
			System.out.println(g.getUebung());
			 String email = session("email");
		  User user = model.aktuellUser(email);
			model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
			System.out.println(g.getPlan());
			System.out.println(g.getUebung());
			System.out.println(g.getTag());
			
			
		return redirect("/home");
		
		}
		
	}
	
	public static Result uebungLoeschenOurGym(){
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
		if(uebungLoeschen.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/ourGym");
    	}else{
		
		
			uebungLoeschen g = uebungLoeschen.get();
			 String email = session("email");
		  User user = model.aktuellUser(email);
			model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
			System.out.println(g.getPlan());
			System.out.println(g.getUebung());
			System.out.println(g.getTag());
			
			
		return redirect("/ourGym");
		
		}
		
	}
	
	public static Result uebungLoeschenVIP(){
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
		if(uebungLoeschen.hasErrors()){
		
    		System.out.println("Errors gefunden!");
    		return redirect("/vipPlaene");
    	}else{
		
		
			uebungLoeschen g = uebungLoeschen.get();
			 String email = session("email");
		  User user = model.aktuellUser(email);
			model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
			System.out.println(g.getPlan());
			System.out.println(g.getUebung());
			System.out.println(g.getTag());
			
			
		return redirect("/vipPlaene");
		
		}
		
	}
	
	
	
	
}

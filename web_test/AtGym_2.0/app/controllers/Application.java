package controllers;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
import play.api.libs.json.*; 
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
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import models.*;
import views.html.*;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;



public class Application extends Controller {
	final static Form<User> loginForm = Form.form(User.class); 
	final static Models model = Models.getInstance();
	final static Form<Gewicht> gewichtForm = Form.form(Gewicht.class); 
	
	
	private static HashMap<Integer, UebungObserver> uebungObservers = new HashMap<Integer, UebungObserver>();
	
	public static Result login() {
		String email = session("email");
		if(email != null){
			model.abmelden(email);
		}
		for(String s : model.getAlleUser().keySet()){
			System.out.println(s);
		}
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
			session("email", u.getEmail());		
			if(u.getEmail() != null & u.getPassword() != null ){
				User user;
				if(u.getVorname() == null  && model.checkUser(u.getEmail(), u.getPassword()) == true){
					//user = model.aktuellUser();
					user=model.aktuellUserList(u.getEmail());
					
					session("User1", user.getVorname());
					
					return ok(home_boot.render(user, uebungLoeschen));
				} else if(u.getVorname() != null && model.neuerUser(userForm.get())==true){
					user=model.aktuellUserList(u.getEmail());
					//user = model.aktuellUser();
					session("User1", user.getVorname());
					
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
		// User user = model.aktuellUser();
		String username = session("User1");
		String email = session("email");
		
		if(username != null && email != null) {
			User user = model.aktuellUserList(email);
			return ok(home.render(user, uebungLoeschen));
		}else{
			return redirect("/atGym");
		}	
	}
	
	public static Result ourGym(){
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class);
		//User user = model.aktuellUser();
		String username = session("User1");
		String email = session("email");
		
		if(username != null && email != null) {
			User user = model.aktuellUserList(email);
			return ok(our_gym_boot.render(user, username, uebungLoeschen));
		}else{
			return redirect("/atGym");
		}	
	}
	
	public static Result myGym(){
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class);
		//User user = model.aktuellUser();
		String username = session("User1");
		
		
		String email = session("email");
		
		if(username != null && email != null) {
			User user = model.aktuellUserList(email);
			String geschlecht = user.getGeschlecht();
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
			
			String email = session("email");
			Gewicht g = gewichtForm.get();
			//User user = model.aktuellUser();
			User user = model.aktuellUserList(email);
			user.setGewicht(g);
			model.gewichtCheck(user);
			//System.out.println(g.getGewicht());
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
			//User user = model.aktuellUser();
			String email = session("email");
			User user = model.aktuellUserList(email);
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
			
			
			Hueftenumfang g = hueftenForm.get();
			//User user = model.aktuellUser();
			String email = session("email");
			User user = model.aktuellUserList(email);
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
			
			
			Armumfang g = armForm.get();
			//User user = model.aktuellUser();
			String email = session("email");
			User user = model.aktuellUserList(email);
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
			
			
			Brustumfang g = brustForm.get();
			//	User user = model.aktuellUser();
			String email = session("email");
			User user = model.aktuellUserList(email);
			user.setBrustumfang(g);
			model.brustumfangCheck(user);
			
			return redirect("/aboutMe");
			
		}
	}
	
	public static Result upload() {
		MultipartFormData body = request().body().asMultipartFormData();
		FilePart picture = body.getFile("picture");
		if (picture != null) {
			String fileName = picture.getFilename();
			
			String contentType = picture.getContentType(); 
			File file = picture.getFile();
			String email = session("email");
			User user = model.aktuellUserList(email);
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
		//User user = model.aktuellUser();
		String username = session("User1");
		String email = session("email");
		if(username != null && email != null) {
			User user = model.aktuellUserList(email);
			return ok(aboutMe.render(user, gewichtForm, bauchForm, hueftenForm, armForm, brustForm, uebungLoeschen));
		}else{
			return redirect("/atGym");
		}
	}
	
	public static Result myPlans(){
		Form<PlanLoeschen> planLoeschen = Form.form(PlanLoeschen.class);
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class);
		//User user = model.aktuellUser();
		String username = session("User1");
		String email = session("email");
		if(username != null && email != null) {
			User user = model.aktuellUserList(email);
			return ok(myPlans.render(user, uebungLoeschen, planLoeschen));
		}else{
			return redirect("/atGym");
		}
	}
	
	public static Result myRoutine(){
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class);
		Form<Satz> satzSave = Form.form(Satz.class);
		//User user = model.aktuellUser();
		String username = session("User1");
		String email = session("email");
		if(username != null && email != null) {
			User user = model.aktuellUserList(email);
				
			return ok(myRoutine.render(user, uebungLoeschen, satzSave));
		}else{
			return redirect("/atGym");
		}
	}
	

	
	public static Result myAnalyse(){
		Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class);
		//User user = model.aktuellUser();
		String username = session("User1");
		String email = session("email");
		if(username != null && email != null) {
			User user = model.aktuellUserList(email);
			return ok(myAnalyse.render(user, uebungLoeschen));
		}else{
			return redirect("/atGym");
		}
	}
	
	public static Result auswaehlenBeine(){
		String email = session("email");
		if(email != null){
			Form<Auswaehlen> uebungForm = Form.form(Auswaehlen.class).bindFromRequest();
			if(uebungForm.hasErrors()){
				
				System.out.println("Errors gefunden!");
				return redirect("/beine");
			}else{
				
				Auswaehlen g = uebungForm.get();
				//User user = model.aktuellUser();
				User user = model.aktuellUserList(email);
				model.planHinzufuegen(user, g.getId(), g.getSatz(), g.getTag(), g.getPlan());
				
				
				
				return redirect("/beine");
			}
		}else{
			return redirect("/atGym");
		}
	}
	public static Result auswaehlenBauch(){
		
		String email = session("email");
		if(email != null){
			Form<Auswaehlen> uebungForm = Form.form(Auswaehlen.class).bindFromRequest();
			if(uebungForm.hasErrors()){
				
				System.out.println("Errors gefunden!");
				return redirect("/bauch");
			}else{
				
				Auswaehlen g = uebungForm.get();
				//User user = model.aktuellUser();
				User user = model.aktuellUserList(email);
				model.planHinzufuegen(user, g.getId(), g.getSatz(), g.getTag(), g.getPlan());
				
				
				
				return redirect("/bauch");
				
			}
		}else{
			return redirect("/atGym");
		}
	}
	public static Result auswaehlenBrust(){
		String email = session("email");
		if(email != null){
			Form<Auswaehlen> uebungForm = Form.form(Auswaehlen.class).bindFromRequest();
			if(uebungForm.hasErrors()){
				
				System.out.println("Errors gefunden!");
				return redirect("/brust");
			}else{
				
				Auswaehlen g = uebungForm.get();
				//User user = model.aktuellUser();
				User user = model.aktuellUserList(email);
				model.planHinzufuegen(user, g.getId(), g.getSatz(), g.getTag(), g.getPlan());
				
				
				
				return redirect("/brust");
				
			}
		}else{
			return redirect("/atGym");
		}
	}
	
	public static Result auswaehlenArme(){
		String email = session("email");
		if(email != null){
			Form<Auswaehlen> uebungForm = Form.form(Auswaehlen.class).bindFromRequest();
			if(uebungForm.hasErrors()){
				
				System.out.println("Errors gefunden!");
				return redirect("/arme");
			}else{
				
				Auswaehlen g = uebungForm.get();
				//User user = model.aktuellUser();
				User user = model.aktuellUserList(email);
				model.planHinzufuegen(user, g.getId(), g.getSatz(), g.getTag(), g.getPlan());
				
				return redirect("/arme");
			}
		}else{
			return redirect("/atGym");
		}
	}
	public static Result auswaehlenRuecken(){
		String email = session("email");
		if(email != null){
			Form<Auswaehlen> uebungForm = Form.form(Auswaehlen.class).bindFromRequest();
			if(uebungForm.hasErrors()){
				
				System.out.println("Errors gefunden!");
				return redirect("/ruecken");
			}else{
				
				Auswaehlen g = uebungForm.get();
				//User user = model.aktuellUser();
				User user = model.aktuellUserList(email);
				model.planHinzufuegen(user, g.getId(), g.getSatz(), g.getTag(), g.getPlan());
				return redirect("/ruecken");
				
			}
		}else{
			return redirect("/atGym");
		}
	}
	
	public static Result auswaehlenSchultern(){
		String email = session("email");
		if(email != null){
			Form<Auswaehlen> uebungForm = Form.form(Auswaehlen.class).bindFromRequest();
			if(uebungForm.hasErrors()){
				
				System.out.println("Errors gefunden!");
				return redirect("/schultern");
			}else{
				
				Auswaehlen g = uebungForm.get();
				//User user = model.aktuellUser();
				User user = model.aktuellUserList(email);
				model.planHinzufuegen(user, g.getId(), g.getSatz(), g.getTag(), g.getPlan());	
				return redirect("/schultern");
				
			}
		}else{
			return redirect("/atGym");
		}
	}
	
	public static Result beine(){
		
		session("muskelgruppe", "beine");
		String muskelgruppe = session("muskelgruppe");
		System.out.println("Seite: " + muskelgruppe);
		
		String username = session("User1");
		String email = session("email");
		if(username != null & email != null) {
			Form<Like> likes = Form.form(Like.class);
			Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class);
			Form<Auswaehlen> uebungenForm = Form.form(Auswaehlen.class);
			//User user = model.aktuellUser();
			User user = model.aktuellUserList(email);
			SortedMap<Integer, Uebung> beineUebungen = model.uebungenListeMuskelgruppe("beine");
			return ok(beine.render(user, beineUebungen, uebungenForm, uebungLoeschen, likes));
		}else{
			return redirect("/atGym");
		}
	}
	public static Result bauch(){
		
		session("muskelgruppe", "bauch");
		String muskelgruppe = session("muskelgruppe");
		System.out.println("Seite: " + muskelgruppe);
		
		String username = session("User1");
		String email = session("email");
		
		if(username != null & email != null) {
			Form<Like> likes = Form.form(Like.class);
			Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class);
			Form<Auswaehlen> uebungenForm = Form.form(Auswaehlen.class);
			//User user = model.aktuellUser();
			User user = model.aktuellUserList(email);
			SortedMap<Integer, Uebung> bauchUebungen = model.uebungenListeMuskelgruppe("bauch");
			return ok(bauch.render(user, bauchUebungen, uebungenForm, uebungLoeschen, likes));
		}else{
			return redirect("/atGym");
		}
	}
	
	public static Result arme(){
		session("muskelgruppe", "arme");
		String muskelgruppe = session("muskelgruppe");
		System.out.println("Seite: " + muskelgruppe);
		
		String username = session("User1");
		String email = session("email");
		
		if(username != null && email != null) {
			Form<Like> likes = Form.form(Like.class);
			Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class);
			Form<Auswaehlen> uebungenForm = Form.form(Auswaehlen.class);
			//User user = model.aktuellUser();
			User user = model.aktuellUserList(email);
			//	SortedMap<Integer, Uebung> armeUebungen = model.arme();
			SortedMap<Integer, Uebung> armeUebungen = model.uebungenListeMuskelgruppe("arme");
			return ok(arme.render(user, armeUebungen, uebungenForm, uebungLoeschen, likes));
		}else{
			return redirect("/atGym");
		}
	}
	
	
	
	public static Result brust(){
		
		session("muskelgruppe", "brust");
		String muskelgruppe = session("muskelgruppe");
		System.out.println("Seite: " + muskelgruppe);
		
		String username = session("User1");
		String email = session("email");
		if(username != null && email != null) {
			Form<Like> likes = Form.form(Like.class);
			Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class);
			Form<Auswaehlen> uebungenForm = Form.form(Auswaehlen.class);
			//User user = model.aktuellUser();
			User user = model.aktuellUserList(email);
			SortedMap<Integer, Uebung> brustUebungen = model.uebungenListeMuskelgruppe("brust");
			return ok(brust.render(user, brustUebungen, uebungenForm, uebungLoeschen, likes));
		}else{
			return redirect("/atGym");
		}
	}
	
	public static Result like(){
		
		Form<Like> likes = Form.form(Like.class).bindFromRequest();
		if(likes.hasErrors()){
			
			System.out.println("Errors gefunden!");
			return redirect("/atGym");
		}else{
			
			
			Like l = likes.get();
			//User user = model.aktuellUser();
			model.like(l.getId());
			session("uebungId", Integer.toString(l.getId()));
			String muskelgruppe = l.getMuskelgruppe();
			
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
			//User user = model.aktuellUser();
			model.dislike(l.getId());
			session("uebungId", Integer.toString(l.getId()));
			String muskelgruppe = l.getMuskelgruppe();
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
			//return redirect("/ruecken");
		}
	}
	
	public static Result ruecken(){
		
		session("muskelgruppe", "ruecken");
		String muskelgruppe = session("muskelgruppe");
		System.out.println("Seite: " + muskelgruppe);
		
		
		String username = session("User1");
		String email = session("email");
		if(username != null && email != null) {
			Form<Like> likes = Form.form(Like.class);
			Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class);
			Form<Auswaehlen> uebungenForm = Form.form(Auswaehlen.class);
			//User user = model.aktuellUser();
			User user = model.aktuellUserList(email);
			SortedMap<Integer, Uebung> rueckenUebungen = model.uebungenListeMuskelgruppe("ruecken");
			return ok(ruecken.render(user, rueckenUebungen, uebungenForm, uebungLoeschen, likes));
		}else{
			return redirect("/atGym");
		}
	}
	
	public static Result schultern(){
		
		session("muskelgruppe", "schultern");
		String muskelgruppe = session("muskelgruppe");
		System.out.println("Seite: " + muskelgruppe);
		
		
		String username = session("User1");
		String email = session("email");
		if(username != null && email != null) {
			Form<Like> likes = Form.form(Like.class);
			Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class);
			Form<Auswaehlen> uebungenForm = Form.form(Auswaehlen.class);
			//	User user = model.aktuellUser();
			User user = model.aktuellUserList(email);
			SortedMap<Integer, Uebung> schulternUebungen = model.uebungenListeMuskelgruppe("schultern");

			return ok(schultern.render(user, schulternUebungen, uebungenForm, uebungLoeschen, likes));
		}else{
			return redirect("/atGym");
		}
	}
	
	
	public static Result uebungLoeschenBrust(){
		String email = session("email");
		if(email != null){
			Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
			if(uebungLoeschen.hasErrors()){
				
				System.out.println("Errors gefunden!");
				return redirect("/brust");
			}else{
				
				
				uebungLoeschen g = uebungLoeschen.get();
				//	User user = model.aktuellUser();
				User user = model.aktuellUserList(email);
				model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
				//	System.out.println(g.getPlan());
				//	System.out.println(g.getUebung());
				//	System.out.println(g.getTag());
				
				
				return redirect("/brust");
				
			}
		} else {
			return redirect("/atGym");
		}
		
	}
	

	public static Result uebungLoeschenBeine(){
		String email = session("email");
		if(email != null){
			
			Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
			if(uebungLoeschen.hasErrors()){
				
				System.out.println("Errors gefunden!");
				return redirect("/beine");
			}else{
				
				
				uebungLoeschen g = uebungLoeschen.get();
				//User user = model.aktuellUser();
				User user = model.aktuellUserList(email);
				model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
				//	System.out.println(g.getPlan());
				//	System.out.println(g.getUebung());
				//	System.out.println(g.getTag());
				
				
				return redirect("/beine");
				
			}
		} else {
			return redirect("/atGym");
		}
	}
	
	
	public static Result uebungLoeschenBauch(){
		String email = session("email");
		if(email != null){
			
			Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
			if(uebungLoeschen.hasErrors()){
				
				System.out.println("Errors gefunden!");
				return redirect("/bauch");
			}else{
				
				
				uebungLoeschen g = uebungLoeschen.get();
				//User user = model.aktuellUser();
				User user = model.aktuellUserList(email);
				model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
				
				
				return redirect("/bauch");
				
			}
		} else {
			return redirect("/atGym");
		}
	}
	
	public static Result uebungLoeschenArme(){
		String email = session("email");
		if(email != null){
			Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
			if(uebungLoeschen.hasErrors()){
				
				System.out.println("Errors gefunden!");
				return redirect("/arme");
			}else{
				uebungLoeschen g = uebungLoeschen.get();
				//User user = model.aktuellUser();
				User user = model.aktuellUserList(email);
				model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
				
				return redirect("/arme");
				
			}
		} else {
			return redirect("/atGym");
		}
	}
	
	public static Result uebungLoeschenSchultern(){
		String email = session("email");
		if(email != null){
			Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
			if(uebungLoeschen.hasErrors()){
				
				System.out.println("Errors gefunden!");
				return redirect("/schultern");
			}else{
				uebungLoeschen g = uebungLoeschen.get();
				//User user = model.aktuellUser();
				User user = model.aktuellUserList(email);
				model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
				
				return redirect("/schultern");
				
			}
		} else {
			return redirect("/atGym");
		}
	}
	
	public static Result uebungLoeschenRuecken(){
		String email = session("email");
		if(email != null){
			Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
			if(uebungLoeschen.hasErrors()){
				
				System.out.println("Errors gefunden!");
				return redirect("/ruecken");
			}else{
				uebungLoeschen g = uebungLoeschen.get();
				//	User user = model.aktuellUser();
				User user = model.aktuellUserList(email);
				model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
				
				return redirect("/ruecken");
				
			}
		} else {
			return redirect("/atGym");
		}
	}
	
	public static Result uebungLoeschenMyGym(){
		String email = session("email");
		if(email != null){
			Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
			if(uebungLoeschen.hasErrors()){
				
				System.out.println("Errors gefunden!");
				return redirect("/myGym");
			}else{
				uebungLoeschen g = uebungLoeschen.get();
				//User user = model.aktuellUser();
				User user = model.aktuellUserList(email);
				model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
				
				return redirect("/myGym");
				
			}
		} else {
			return redirect("/atGym");
		}
	}
	
	public static Result uebungLoeschenAboutMe(){
		String email = session("email");
		if(email != null){
			Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
			if(uebungLoeschen.hasErrors()){
				
				System.out.println("Errors gefunden!");
				return redirect("/aboutMe");
			}else{
				
				
				uebungLoeschen g = uebungLoeschen.get();
				//	User user = model.aktuellUser();
				User user = model.aktuellUserList(email);
				model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
				return redirect("/aboutMe");
				
			}
		} else {
			return redirect("/atGym");
		}
	}
	
	public static Result uebungLoeschenMyPlans(){
		String email = session("email");
		if(email != null){
			Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
			if(uebungLoeschen.hasErrors()){
				
				System.out.println("Errors gefunden!");
				return redirect("/myPlans");
			}else{
				
				
				uebungLoeschen g = uebungLoeschen.get();
				User user = model.aktuellUserList(email);
				model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
				return redirect("/myPlans");
				
			}
		} else {
			return redirect("/atGym");
		}
	}
	
	public static Result planLoeschen(){
		String email = session("email");
		if(email != null){
			Form<PlanLoeschen> planLoeschen = Form.form(PlanLoeschen.class).bindFromRequest();
			if(planLoeschen.hasErrors()){
				
				System.out.println("Errors gefunden!");
				return redirect("/myPlans");
			}else{
				PlanLoeschen g = planLoeschen.get();
				User user = model.aktuellUserList(email);
				model.planLoeschenKomplett(user, g.getPlan());
				return redirect("/myPlans");
				
			}
		} else {
			return redirect("/atGym");
		}
	}
	
	public static Result uebungLoeschenMyRoutine(){
		String email = session("email");
		if(email != null){
			Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
			if(uebungLoeschen.hasErrors()){
				
				System.out.println("Errors gefunden!");
				return redirect("/myRoutine");
			}else{
				uebungLoeschen g = uebungLoeschen.get();
				User user = model.aktuellUserList(email);
				model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
				
				return redirect("/myRoutine");
				
			}
		} else {
			return redirect("/atGym");
		}
	}
	
	public static Result satzSave(){
		String email = session("email");
		if(email != null){
			Form<Satz> satzSave = Form.form(Satz.class).bindFromRequest();
			if(satzSave.hasErrors()){
				
				System.out.println("Errors gefunden!");
				return redirect("/myRoutine");
			}else{
				Satz g = satzSave.get();
				
				User user = model.aktuellUserList(email);
				model.routineStep1(user, g.getPlan(), g.getUebung(), g.getTag(), g.getWh(), g.getGewicht(), g.getSatz());
				
			}
			return redirect("/myRoutine");
		} else {
			return redirect("/atGym");
		}
	}
	
	
	public static Result uebungLoeschenAnalyse(){
		String email = session("email");
		if(email != null){
			Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
			if(uebungLoeschen.hasErrors()){
				
				System.out.println("Errors gefunden!");
				return redirect("/myAnalyse");
			}else{
				uebungLoeschen g = uebungLoeschen.get();
				User user = model.aktuellUserList(email);
				model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
				
				return redirect("/myAnalyse");
				
			}
		} else {
			return redirect("/atGym");
		}
	}
	
	public static Result uebungLoeschenHome(){
		String email = session("email");
		if(email != null){
			Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
			System.out.println("Home");
			if(uebungLoeschen.hasErrors()){
				
				System.out.println("Errors gefunden!");
				return redirect("/home");
			}else{
				uebungLoeschen g = uebungLoeschen.get();
				
				User user = model.aktuellUserList(email);
				model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
				return redirect("/home");
				
			}
		} else {
			return redirect("/atGym");
		}
	}
	
	public static Result uebungLoeschenOurGym(){
		String email = session("email");
		if(email != null){
			Form<uebungLoeschen> uebungLoeschen = Form.form(uebungLoeschen.class).bindFromRequest();
			if(uebungLoeschen.hasErrors()){
				
				System.out.println("Errors gefunden!");
				return redirect("/ourGym");
			}else{
				uebungLoeschen g = uebungLoeschen.get();
				User user = model.aktuellUserList(email);
				model.uebungLoeschen(user, g.getPlan(),g.getUebung(),g.getTag() );
				return redirect("/ourGym");
				
			}
		} else {
			return redirect("/atGym");
		}
	}
	private static List<WebSocket.Out<String>> uebungConnection = new ArrayList<WebSocket.Out<String>>(); 
	
	public static WebSocket<JsonNode> uebungWebSocket() {
		return new WebSocket<JsonNode>() {
			public void onReady(WebSocket.In<JsonNode> in,
					final WebSocket.Out<JsonNode> out) {
				System.out.println(": WebSocketArtikel ready...");
				final GymObserver obs = new GymObserver();
				obs.gym = out;
				// final Integer id = new Integer(obs.hashCode());
				// observer.put(id,obs);
				System.out.println( ": Anzahl observer: "
						+ Models.getInstance().countObservers());
				in.onMessage(new Callback<JsonNode>() {
					public void invoke(JsonNode obj) {

						// out.write(Model.sharedInstance.zeigeAktuelleMenge(obj));

					}

				});

				in.onClose(new Callback0() {
					public void invoke() {
						// observer.remove(id);
						Models.getInstance().deleteObserver(obs);

						
					}
				});

			}
		};
		
	}
	
	/*public static WebSocket<String> uebungWebSocket() {
		final String email = session("email");
		final String musklgruppe = session("muskelgruppe");
		System.out.println("User WEBSOCKET: "+ email);
		
		WebSocket<String> ws = null;
		final String r = session("muskelgruppe");
		ws = new WebSocket<String>() {
			public void onReady(WebSocket.In<String> in, final WebSocket.Out<String> out) {
				
			//	uebungConnection.add(out);
				final UebungObserver uebungObserver = new UebungObserver(model.getAlleUebungen(), out);
				
				
				in.onMessage(new Callback<String>() {
					public void invoke(String g) {
						
						int uebungid = Integer.parseInt(g);
						Uebung uebung = model.getUebung(uebungid);
						model.setLike(uebung);
						String res = Integer.toString(uebung.getLike());
						
						System.out.println("likes: " + res);
						out.write(res);
					}
				});
				in.onClose(new Callback0() {
					public void invoke() {
						System.out.println("Disconnected!");
					}
				});
			}
		};
		return ws;
	
	//final Integer uebungId = new Integer(session("uebungId"));
	//final Uebung uebung = model.getUebung(uebungId);
	
	/*	return new WebSocket<JsonNode>() {
		
			public void onReady(WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out) {
					//Überprüfe ob GameObserver schon vorhanden ist
					
						if(uebungObservers.containsKey(uebungId) == false){
						uebungObservers.put(uebungId, new UebungObserver(uebung));
						
					}
					uebungObservers.get(uebungId).uebungen=out;	
				
					
				in.onMessage(new Callback<JsonNode>() {
					public void invoke(JsonNode event) throws Throwable {
						String action = event.get("type").asText();
						System.out.println(action);
						
						
						if(action.equals("like")){
						
						}
						
						if(action.equals("dislike")) {
						
						}
						
					}
					
				});
				
			}
		}; 
	
}*/


}


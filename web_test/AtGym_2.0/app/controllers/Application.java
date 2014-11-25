package controllers;

import play.*;
import play.mvc.*;
import play.api.data.*;
import play.api.data.Forms.*;
import play.data.Form;
import models.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
	
	
	public static Result home() {
		return ok(home_boot.render());
	}
	
	public static Result anmeldung(){
		return ok(anmeldung.render());
	}
	
	public static Result addUser() {
	/*User user = Form.form(User.class).bindFromRequest().get();
	user.save();*/
    return ok(home_boot.render());
}
	
	public static Result ourgym(){
		return ok(our_gym_boot.render());
	}
	
	public static Result mygym(){
		return ok(myGym.render());
	}
	
	public static Result beine(){
		return ok(beine.render());
	}
	public static Result bauch(){
		return ok(bauch.render());
	}
	public static Result arme(){
		return ok(arme.render());
	}
	
	public static Result brust(){
		return ok(brust.render());
	}
	
	public static Result fertigePlaene(){
		return ok(fertigePlaene.render());
	}
	
	public static Result ruecken(){
		return ok(ruecken.render());
	}
	
	public static Result schultern(){
		return ok(schultern.render());
	}
	
	public static Result vipPlaene(){
		return ok(vipPlaene.render());
	}
	
	public static class LogIn {
	public String email;
	public String password;

}

}

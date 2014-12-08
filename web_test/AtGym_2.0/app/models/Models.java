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
   
   public void neuerUser(User user){
	this.user = user;
	
   }
}
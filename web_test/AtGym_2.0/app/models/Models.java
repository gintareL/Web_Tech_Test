package models;

import java.util.*;
import play.db.ebean.Model;
import play.data.validation.Constraints.*;
import javax.persistence.Entity;


public class Models extends Model{
  public Map<String,User> users = new HashMap<String,User>();
  public Map<Muskel,UebungMuskel> uebungenNachKategorien = new HashMap<Muskel, UebungMuskel>();
}
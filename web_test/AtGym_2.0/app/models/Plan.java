package models;
import java.util.*;

public class Plan{
	public String name;
	public Map<Tag, TagPlan> uebungen = new HashMap<Tag, TagPlan>();
}
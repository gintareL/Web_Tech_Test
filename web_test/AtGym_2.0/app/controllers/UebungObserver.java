package controllers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Observable;
import play.libs.Json;
import play.mvc.WebSocket;
import models.Uebung;
import java.util.*;

public class UebungObserver implements java.util.Observer {
	
	
	public WebSocket.Out<String> uebung;
	
	public Map<Integer, Uebung> uebungen;
	
	/*public UebungObserver(Uebung uebung) {
		this.uebung = uebung;
		uebung.addObserver(this);
	}*/
	public UebungObserver(Map<Integer, Uebung> uebungen, WebSocket.Out<String> out) {
		this.uebungen = uebungen;
		for(Uebung u : uebungen.values()){
			u.addObserver(this);
			System.out.println("Observer Konstruktor");
		}
		uebung=out;
		
	}

	@Override
	public void update(Observable o, Object uebungId) {
		
		
	}
	
	
}
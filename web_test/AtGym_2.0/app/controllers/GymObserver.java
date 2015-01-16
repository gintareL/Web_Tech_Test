package controllers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Observable;
import play.libs.Json;
import play.mvc.WebSocket;
import java.util.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import models.Models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GymObserver implements java.util.Observer {
	public WebSocket.Out<JsonNode> gym;
	
	public WebSocket.Out<String> uebung;
	
	public GymObserver() {
		Models.getInstance().addObserver(this);
	}
	

	@Override
	public void update(Observable arg0, Object uebungId) {
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonUebung = null;
		try {
			jsonUebung = mapper.readTree("{\"uebung\":\""+uebungId+"\"}");
			System.out.println("JSON: "+jsonUebung);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gym.write(Models.getInstance().getLikesAktuell(jsonUebung));
	}
	
	
}
package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class GuiLoginController {
	
	@FXML private TextField cfUser;
	@FXML private TextField psswUser;

	@FXML private TextField cfAdmin;
	@FXML private TextField psswAdmin;
	
	/**
	 * Metodo per accedere all'area utenti.
	 */
	public void credenzialiUser() throws IOException {
		Main m = new Main();
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    String fileName = "credentials.json";
	    Credenziali[] credenzialiFromJson;

	    String codiceFiscaleUtente = cfUser.getText();
	    String passwordUtente = psswUser.getText();
	    boolean trovato = false;

	    try{
	        JsonReader reader = new JsonReader(new FileReader(fileName));
	        credenzialiFromJson = gson.fromJson(reader, Credenziali[].class);

	        for(int i=0; i < credenzialiFromJson.length; i++) {
	            if(credenzialiFromJson[i].getCodiceFiscale().equalsIgnoreCase(codiceFiscaleUtente) &&
	            		credenzialiFromJson[i].getPassword().equals(passwordUtente)) {     
	            	m.changeScene("GuiUser.fxml");
	            	trovato = true;
	            } 
	        }
	        
	        if(! trovato) {
	        	GuiErrore.errore("Codice fiscale e/o password errati.");
	        }
	        
	    } catch (FileNotFoundException e){
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Metodo per accedere all'area amministratore.
	 */
	public void credenzialiAdmin() throws IOException {
		Main m = new Main(); 
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    String fileName = "admin.json";
	    Admin adminFromJson;
	    
	    String cfAmministratore = cfAdmin.getText();
	    String passwordAmministratore = psswAdmin.getText();
	    
	    try{
	        JsonReader reader = new JsonReader(new FileReader(fileName));
	        adminFromJson = gson.fromJson(reader, Admin.class);

            if(adminFromJson.getCodiceFiscale().equalsIgnoreCase(cfAmministratore) &&
            		adminFromJson.getPassword().equals(passwordAmministratore)) {     
            	m.changeScene("GuiAdmin.fxml");
            } else {
            	GuiErrore.errore("Codice fiscale e/o password errati.");
            }
	        
	    } catch (FileNotFoundException e){
	        e.printStackTrace();
	    }
	}
}

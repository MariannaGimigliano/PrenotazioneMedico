package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonReader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class GuiUserController implements Initializable{
	
	/** 
	 * Variabili Preferenze.
	 */
	@FXML private TextField txtCfUtente;
	
	@FXML private ComboBox<String> combWorkspace;
	@FXML private ComboBox<String> combAttivita;
	@FXML private ComboBox<String> combDate;
	@FXML private TextField txtOrario;
	@FXML private TextField txtNoteAggiuntive;
	@FXML private Button bttAggiungiPreferenze;
	
	@FXML private ComboBox<String> combModificaAttivita;
	@FXML private ComboBox<String> combPreferenze;
	@FXML private TextField nuovoAttributoPreferenze;
	@FXML private Button bttModificaPreferenze;
	
	@FXML private Button bttVisualizzaPreferenze;
	@FXML private Button bttTelegram;
	
	@FXML private TableView<Preferenza> tableViewPreferenze;
	@FXML private TableColumn<Preferenza, String> colonnaDescrizioneWorkspacePref;
	@FXML private TableColumn<Preferenza, String> colonnaDescrizioneAttivitaPref;
	@FXML private TableColumn<Preferenza, String> colonnaData;
	@FXML private TableColumn<Preferenza, String> colonnaOrario;
	@FXML private TableColumn<Preferenza, String> colonnaNoteAggiuntive;

	/** 
	 * Metodo per esprimere una preferenza.
	 */
	public void aggiungiPreferenze(){
    	
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String fileName = "preferencesUsers.json";
        Preferenza[] preferenzeFromJson;
        
        LinkedList<Preferenza> preferenze = new LinkedList<>();

        try {	
        	JsonReader reader = new JsonReader(new FileReader(fileName));
			preferenzeFromJson = gson.fromJson(reader, Preferenza[].class);
			
			for(int i=0; i<preferenzeFromJson.length; i++) {
				Preferenza preferenzaX = new Preferenza(preferenzeFromJson[i].getCfUtente(), preferenzeFromJson[i].getDescrizioneWorkspace(),
						preferenzeFromJson[i].getDescrizioneAttivita(), preferenzeFromJson[i].getData(), preferenzeFromJson[i].getOrario(),
						preferenzeFromJson[i].getNoteAggiuntive());
				preferenze.add(preferenzaX);
			}
        	
			String cfUtente = txtCfUtente.getText();
			String descrizioneWorkspace = combWorkspace.getSelectionModel().getSelectedItem().toString();
			String descrizioneAttivita= combAttivita.getSelectionModel().getSelectedItem().toString();
	    	String data = combDate.getSelectionModel().getSelectedItem().toString();
	    	String orario = txtOrario.getText();
	    	String noteAggiuntive = txtNoteAggiuntive.getText();
	    	
	    	Preferenza preferenzaX = new Preferenza(cfUtente, descrizioneWorkspace, descrizioneAttivita, data, orario, noteAggiuntive);
	    	preferenze.add(preferenzaX);
	    	
	    	try{
	            FileWriter writer = new FileWriter(fileName);
	            gson.toJson(preferenze, writer);
	            writer.close();
	        } catch (JsonIOException | IOException e){
	            e.printStackTrace();
	        }
	    	
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        
    	combWorkspace.getSelectionModel().clearSelection();
    	combAttivita.getSelectionModel().clearSelection();
    	combDate.getSelectionModel().clearSelection();
    	txtOrario.clear();
    	txtNoteAggiuntive.clear();
    	
    	visualizzaPreferenze();
    }
	
	/** 
	 * Metodo per modificare una preferenza.
	 */
	public void modificaPreferenze() {
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	String fileName = "preferencesUsers.json";
    	Preferenza[] preferenzeFromJson;
    	
    	LinkedList<Preferenza> preferenze = new LinkedList<>();
    	String cfUtente = txtCfUtente.getText();
    	String descrizioneAttivitaPref = combModificaAttivita.getSelectionModel().getSelectedItem().toString();
    	
    	String fileName2 = "activities.json";
    	Attivita[] attivitaFromJson;
    	
		boolean trovato = false;
    	
    	 try {	
         	JsonReader reader = new JsonReader(new FileReader(fileName));
 			preferenzeFromJson = gson.fromJson(reader, Preferenza[].class);
 			
 			for(int i=0; i<preferenzeFromJson.length; i++) {
 				Preferenza preferenzaX = new Preferenza(preferenzeFromJson[i].getCfUtente(), preferenzeFromJson[i].getDescrizioneWorkspace(),
 						preferenzeFromJson[i].getDescrizioneAttivita(), preferenzeFromJson[i].getData(), preferenzeFromJson[i].getOrario(),
 						preferenzeFromJson[i].getNoteAggiuntive());
 				preferenze.add(preferenzaX);
		    	
 				for(int j=0; j<preferenze.size(); j++) {
		    		if(preferenze.get(j).getCfUtente().equalsIgnoreCase(cfUtente) && preferenze.get(j).getDescrizioneAttivita().equals(descrizioneAttivitaPref)) {
		    		
			    		String parola = combPreferenze.getSelectionModel().getSelectedItem().toString();
			        	switch(parola) {
			    	    	case "Data":
			    	    		String tmp1 = nuovoAttributoPreferenze.getText();
			    	    		try{
			    	    			JsonReader reader2 = new JsonReader(new FileReader(fileName2));
			    	    			attivitaFromJson = gson.fromJson(reader2, Attivita[].class);

			    	    	    	for(int k=0; k<attivitaFromJson.length; k++) {
			    	    	    		if(attivitaFromJson[k].getDescrizione().equals(descrizioneAttivitaPref)) {
			    	    	    			Attivita attivitaX = new Attivita(attivitaFromJson[k].getId(), attivitaFromJson[k].getDescrizione(), attivitaFromJson[k].getDurata(),
			    	    		    				attivitaFromJson[k].getGiornoDisponibile(), attivitaFromJson[k].getDateDisponibili());;
				    	    	    		for(int z=0; z<attivitaX.getDateDisponibili().size(); z++) {
				    	    	    			if(attivitaX.getDateDisponibili().get(z).equals(tmp1)) {
				    			    	    		preferenze.get(j).setData(tmp1);
				    	    	    				trovato = true;
				    	    	    			}
				    	    	    		}
			    	    	    		}
			    	    	    	}
			    	    		} catch (FileNotFoundException e){
			    	    	        e.printStackTrace();
			    	    	    }
			    	    		break;
			    	    	case "Orario":
			    	    		String tmp2 = nuovoAttributoPreferenze.getText();
			    	    		preferenze.get(j).setOrario(tmp2);
			    	    		break;
			    	    	case "NoteAggiuntive":
			    	    		String tmp3 = nuovoAttributoPreferenze.getText();
			    	    		preferenze.get(j).setNoteAggiuntive(tmp3);
			    	    		break;
			        	}
		    		}
	    		}
			}
 			if(! trovato) {
 				GuiErrore.errore("Data inserita non disponibile.");
			} else {
	        	try{
	                FileWriter writer = new FileWriter(fileName);
	                gson.toJson(preferenze, writer);
	                writer.close();
	            } catch (JsonIOException | IOException e){
	                e.printStackTrace();
	            }
			}
	    	
	    } catch (FileNotFoundException e){
	        e.printStackTrace();
	    }
    	
    	nuovoAttributoPreferenze.clear();
    	combModificaAttivita.getSelectionModel().clearSelection();
    	combPreferenze.getSelectionModel().clearSelection();
    	
    	visualizzaPreferenze();    	
    }
	
	/** 
	 * Metodo per inserire nel file json "preferences.json" le preferenze di uno specifico 
	 * utente e visualizzarle nella relativa tabella.
	 */
	public void visualizzaPreferenze() {
		
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	String fileName = "preferencesUsers.json";
    	String fileName2 = "preferences.json";
    	Preferenza[] preferenzeFromJson;
    	Preferenza preferenzaX = null;
    	
    	String fileName3 = "user.json";
        Utente[] userFromJson;
    	
    	LinkedList<Preferenza> preferenze = new LinkedList<>();
    	String cfUtente = txtCfUtente.getText();
    	
    	boolean trovato = false;
    	
    	try{
			JsonReader reader = new JsonReader(new FileReader(fileName));
			preferenzeFromJson = gson.fromJson(reader, Preferenza[].class);
			
			for(int i=0; i<preferenzeFromJson.length; i++) {
	    		if(preferenzeFromJson[i].getCfUtente().equalsIgnoreCase(cfUtente)) {
	    			preferenzaX = new Preferenza(preferenzeFromJson[i].getCfUtente(), preferenzeFromJson[i].getDescrizioneWorkspace(),
							preferenzeFromJson[i].getDescrizioneAttivita(), preferenzeFromJson[i].getData(), preferenzeFromJson[i].getOrario(),
							preferenzeFromJson[i].getNoteAggiuntive());
    		    	preferenze.add(preferenzaX);
	    		}
	    	} 
			
			try{
				JsonReader reader2 = new JsonReader(new FileReader(fileName3));
				userFromJson = gson.fromJson(reader2, Utente[].class);
	
		    	for(int i=0; i<userFromJson.length; i++) {
		    		if(userFromJson[i].getCodiceFiscale().equals(cfUtente)) {
		    			trovato = true;
		    		} 
		    	}
			} catch (FileNotFoundException e){
	            e.printStackTrace();
	        }
			
			if(! trovato) {
				GuiErrore.errore("Codice fiscale errato.");
			} else {
				try{
		            FileWriter writer = new FileWriter(fileName2);
		            gson.toJson(preferenze, writer);
		            writer.close();
		        } catch (JsonIOException | IOException e){
		            e.printStackTrace();
		        }
				tableViewPreferenze.getItems().clear();
		    	preferenzeTableView();
			}

    	} catch (FileNotFoundException e){
            e.printStackTrace();
        }
	}
	
	/**
	 * Metodo per inserire le preferenze dell'utente nella relativa tabella.
	 */
	public void preferenzeTableView() {
		
		ObservableList<Preferenza> preferenzeList = FXCollections.observableArrayList();
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String fileName = "preferences.json";
		Preferenza[] preferenzeFromJson;
		
		try{
			JsonReader reader = new JsonReader(new FileReader(fileName));
			preferenzeFromJson = gson.fromJson(reader, Preferenza[].class);
	
	    	for(int i=0; i<preferenzeFromJson.length; i++) {
	    		Preferenza preferenzaX = new Preferenza(preferenzeFromJson[i].getCfUtente(), preferenzeFromJson[i].getDescrizioneWorkspace(),
						preferenzeFromJson[i].getDescrizioneAttivita(), preferenzeFromJson[i].getData(), preferenzeFromJson[i].getOrario(),
						preferenzeFromJson[i].getNoteAggiuntive());
	    		preferenzeList.add(preferenzaX);
			}
	    	
	    } catch (FileNotFoundException e){
	        e.printStackTrace();
	    }

		colonnaDescrizioneWorkspacePref.setCellValueFactory(new PropertyValueFactory<Preferenza, String>("DescrizioneWorkspace"));
		colonnaDescrizioneAttivitaPref.setCellValueFactory(new PropertyValueFactory<Preferenza, String>("DescrizioneAttivita"));
		colonnaData.setCellValueFactory(new PropertyValueFactory<Preferenza, String>("Data"));
		colonnaOrario.setCellValueFactory(new PropertyValueFactory<Preferenza, String>("Orario"));
		colonnaNoteAggiuntive.setCellValueFactory(new PropertyValueFactory<Preferenza, String>("NoteAggiuntive"));
		
		tableViewPreferenze.setItems(preferenzeList);
		setComboboxModificaAttivita();
	}
	
	/**
	 * Metodo per popolare la combobox con tutti i workspace
	 * disponibili.
	 */
	public void comboboxWorkspace() {
		
		ObservableList<String> combWorkspaceList = FXCollections.observableArrayList();
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String fileName = "workspace.json";
		Workspace[] workspaceFromJson;
		
		try{
			JsonReader reader = new JsonReader(new FileReader(fileName));
			workspaceFromJson = gson.fromJson(reader, Workspace[].class);

	    	for(int i=0; i<workspaceFromJson.length; i++) {
	    		combWorkspaceList.add(workspaceFromJson[i].getDescrizione());
			}
	    	
	    } catch (FileNotFoundException e){
	        e.printStackTrace();
	    }
		combWorkspace.setItems(combWorkspaceList);
	}
	
	/**
	 * Metodo per popolare la combobox con le attività presenti
	 * nel workspace selezionato.
	 */
	public void setComboboxAttivita() {
	
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String fileName = "workspace.json";
		Workspace[] workspaceFromJson;
		
		ObservableList<String> combAttivitaList = FXCollections.observableArrayList();
		String descrizioneWorkspace = combWorkspace.getSelectionModel().getSelectedItem().toString();
		
		try{
			JsonReader reader = new JsonReader(new FileReader(fileName));
			workspaceFromJson = gson.fromJson(reader, Workspace[].class);

	    	for(int i=0; i<workspaceFromJson.length; i++) {
	    		if(workspaceFromJson[i].getDescrizione().equals(descrizioneWorkspace)) {
					for(int j=0; j<workspaceFromJson[i].getListaAttivita().size(); j++) {
						 combAttivitaList.add(workspaceFromJson[i].getListaAttivita().get(j).getDescrizione());
					}
				}
	    		
	    	}
		} catch (FileNotFoundException e){
	        e.printStackTrace();
	    }
		combAttivita.setItems(combAttivitaList);
	}
	
	/**
	 * Metodo per popolare la combobox con le date disponibili relative
	 * all'attività selezionata.
	 */
	public void setComboboxDate() {
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	String fileName = "activities.json";
    	Attivita[] attivitaFromJson;
    	
    	ObservableList<String> combDateList = FXCollections.observableArrayList();
		String descrizioneAttivita = combAttivita.getSelectionModel().getSelectedItem().toString();
		
		try{
			JsonReader reader = new JsonReader(new FileReader(fileName));
			attivitaFromJson = gson.fromJson(reader, Attivita[].class);

	    	for(int i=0; i<attivitaFromJson.length; i++) {
	    		if(attivitaFromJson[i].getDescrizione().equals(descrizioneAttivita)) {
	    			for(int j=0; j<attivitaFromJson[i].getDateDisponibili().size(); j++) {
	    				combDateList.add(attivitaFromJson[i].getDateDisponibili().get(j));
					}
				}
	    		
	    	}
		} catch (FileNotFoundException e){
	        e.printStackTrace();
	    }
		combDate.setItems(combDateList);
	}
	
	/**
	 * Metodo per popolare la combobox con le attività scelte
	 * da uno specifico utente.
	 */
	public void setComboboxModificaAttivita() {
		
		ObservableList<String> preferenzeList = FXCollections.observableArrayList();
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String fileName = "preferences.json";
		Preferenza[] preferenzeFromJson;
		
		try{
			JsonReader reader = new JsonReader(new FileReader(fileName));
			preferenzeFromJson = gson.fromJson(reader, Preferenza[].class);
	
	    	for(int i=0; i<preferenzeFromJson.length; i++) {
	    		preferenzeList.add(preferenzeFromJson[i].getDescrizioneAttivita());
			}
	    	
	    } catch (FileNotFoundException e){
	        e.printStackTrace();
	    }
		combModificaAttivita.setItems(preferenzeList);
	}
	
	/**
	 * Inizializza Api Telegram.
	 */
	public void telegram(){
	    ApiContextInitializer.init();
	    try{
	        TelegramBotsApi api = new TelegramBotsApi();
	        api.registerBot(new PrenotazioneMedicoBot());
	        System.out.println("PrenotazioneMedicoBot Online");
	    } catch (TelegramApiException e){
	        e.printStackTrace();
	    }  
	}
	
	/**
	 * Called to initialize a controller.
	 */
	public void initialize(URL location, ResourceBundle resources) {
		
		comboboxWorkspace();
		
		ObservableList<String> combPreferenzeList = FXCollections.observableArrayList("Data", "Orario", "NoteAggiuntive");
		combPreferenze.setItems(combPreferenzeList);	
	}
}

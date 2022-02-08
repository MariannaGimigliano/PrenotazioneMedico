package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

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

public class GuiAdminController implements Initializable{
	
	/**
	 * Variabili Utente.
	 */
	@FXML private TextField txtNomeUtente;
	@FXML private TextField txtCognomeUtente;
	@FXML private TextField txtSessoUtente;
	@FXML private TextField txtCodiceFiscaleUtente;
	@FXML private TextField txtDataNascitaUtente;
	@FXML private Button bttAggiungiUtente;
	
	@FXML private TextField cfUtente;
	@FXML private TextField nuovoAttributoUtente;
	@FXML private Button bttModificaUtente;
	@FXML private ComboBox<String> combUser;
	
	@FXML private TableView<Utente> tableViewUser;
	@FXML private TableColumn<Utente, String> colonnaNome;
	@FXML private TableColumn<Utente, String> colonnaCognome;
	@FXML private TableColumn<Utente, String> colonnaSesso;
	@FXML private TableColumn<Utente, String> colonnaCodiceFiscale;
	@FXML private TableColumn<Utente, String> colonnaDataNascita;
	
	/**
	 * Variabili Workspace.
	 */
	@FXML private TextField txtIdWorkspace;
	@FXML private TextField txtDescrizioneWorkspace;
	@FXML private TextField txtListaAttivitaWorkspace;
	@FXML private Button bttAggiungiWorkspace;
	
	@FXML private TextField idWorkspace;
	@FXML private TextField nuovoAttributoWorkspace;
	@FXML private Button bttModificaWorkspace;
	@FXML private ComboBox<String> combWorkspace;
	
	@FXML private Button bttnRimuoviListaWorkspace;
	
	@FXML private TableView<Workspace> tableViewWorkspace;
	@FXML private TableColumn<Workspace, Integer> colonnaIdWorkspace;
	@FXML private TableColumn<Workspace, String> colonnaDescrizioneWorkspace;
	
	/**
	 * Variabili Attività.
	 */
	@FXML private TextField txtIdAttivita;
	@FXML private TextField txtDescrizioneAttivita;
	@FXML private TextField txtDurataAttivita;
	@FXML private TextField txtGiornoDisponibileAttivita;
	@FXML private TextField txtDateDisponibiliAttivita;
	@FXML private Button bttAggiungiAttivita;
	
	@FXML private TextField idAttivita;
	@FXML private TextField nuovoAttributoAttivita;
	@FXML private Button bttModificaAttivita;
	@FXML private ComboBox<String> combAttivita;
	
	@FXML private Button bttnRimuoviListaAttivita;
	
	@FXML private TableView<Attivita> tableViewAttivita;
	@FXML private TableColumn<Attivita, Integer> colonnaIdAttivita;
	@FXML private TableColumn<Attivita, String> colonnaDescrizioneAttivita;
	@FXML private TableColumn<Attivita, Integer> colonnaDurata;
	@FXML private TableColumn<Attivita, String> colonnaGiornoDisponibile;
	@FXML private TableColumn<Attivita, String> colonnaDateDisponibili;
	
	/**
	 * Variabili Preferenza.
	 */
	@FXML private ComboBox<String> combCfUtentePreferenze;
	@FXML private ComboBox<String> combAttivitaUtentePreferenze;
	@FXML private TextField nuovoAttributoPreferenze;
	@FXML private Button bttModificaPreferenze;
	@FXML private ComboBox<String> combPreferenze;
	
	@FXML private TableView<Preferenza> tableViewPreferenze;
	@FXML private TableColumn<Preferenza, String> colonnaCfUtente;
	@FXML private TableColumn<Preferenza, String> colonnaDescrizioneWorkspacePref;
	@FXML private TableColumn<Preferenza, String> colonnaDescrizioneAttivitaPref;
	@FXML private TableColumn<Preferenza, String> colonnaData;
	@FXML private TableColumn<Preferenza, String> colonnaOrario;
	@FXML private TableColumn<Preferenza, String> colonnaNoteAggiuntive;
	
	/**
	 * Metodo per aggiungere un utente.
	 */
    public void aggiungiUtente(){  
    	
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	String fileName = "user.json";
    	String fileName2 = "credentials.json";
    	Utente[] userFromJson;
    	Credenziali[] credenzialiFromJson;
    	LinkedList<Utente> user = new LinkedList<>();
    	LinkedList<Credenziali> credenziali = new LinkedList<>();
    	
    	try{
			JsonReader reader = new JsonReader(new FileReader(fileName2));
			credenzialiFromJson = gson.fromJson(reader, Credenziali[].class);
			
			for(int i=0; i<credenzialiFromJson.length; i++) {
				Credenziali credenzialiX = new Credenziali(credenzialiFromJson[i].getCodiceFiscale(), credenzialiFromJson[i].getPassword());
				credenziali.add(credenzialiX);
			}

    	
	    	try{
				JsonReader reader2 = new JsonReader(new FileReader(fileName));
				userFromJson = gson.fromJson(reader2, Utente[].class);
	
		    	for(int i=0; i<userFromJson.length; i++) {
			    	Utente userX = new Utente(userFromJson[i].getNome(), userFromJson[i].getCognome(), userFromJson[i].getSesso(), 
			    			userFromJson[i].getCodiceFiscale(), userFromJson[i].getDataDiNascita());
			    	user.add(userX);
		    	}
		    	
		    	String nome = txtNomeUtente.getText();
		    	String cognome = txtCognomeUtente.getText();
		    	String sesso = txtSessoUtente.getText();
		    	String codiceFiscale = txtCodiceFiscaleUtente.getText();
		    	String dataDiNascita = txtDataNascitaUtente.getText();
		    	Utente utenteX = new Utente(nome, cognome, sesso, codiceFiscale, dataDiNascita);
		    	user.add(utenteX);
		    	Credenziali credenziali2 = new Credenziali(codiceFiscale, "");
		    	credenziali.add(credenziali2);
	    	
		    	
		        try{
		            FileWriter writer = new FileWriter(fileName);
		            gson.toJson(user, writer);
		            writer.close();
		        } catch (JsonIOException | IOException e){
		            e.printStackTrace();
		        }
		        
		        try{
		            FileWriter writer = new FileWriter(fileName2);
		            gson.toJson(credenziali, writer);
		            writer.close();
		        } catch (JsonIOException | IOException e){
		            e.printStackTrace();
		        }
		        
	    	} catch (JsonIOException | IOException e){
	            e.printStackTrace();
	        }
	        
    	}  catch (FileNotFoundException e){
            e.printStackTrace();
        }
    	
    	txtNomeUtente.clear();
    	txtCognomeUtente.clear();
    	txtSessoUtente.clear();
    	txtCodiceFiscaleUtente.clear();
    	txtDataNascitaUtente.clear();
    	
    	tableViewUser.getItems().clear();
    	userTableView();
    }
    
    /**
	 * Metodo per modificare un attributo di utente.
	 */
    public void modificaUtente() {
    	
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	String fileName = "user.json";
    	String cfUserX = cfUtente.getText();
    	Utente[] userFromJson;
    	LinkedList<Utente> user = new LinkedList<>();
    	
    	try{
			JsonReader reader = new JsonReader(new FileReader(fileName));
			userFromJson = gson.fromJson(reader, Utente[].class);

	    	for(int i=0; i<userFromJson.length; i++) {
		    	Utente userX = new Utente(userFromJson[i].getNome(), userFromJson[i].getCognome(), userFromJson[i].getSesso(), 
		    			userFromJson[i].getCodiceFiscale(), userFromJson[i].getDataDiNascita());
		    	user.add(userX);
		    	
		    	for(int j=0; j<user.size(); j++) {
		    		if(user.get(j).getCodiceFiscale().equalsIgnoreCase(cfUserX)) {
		    		
			    		String parola = combUser.getSelectionModel().getSelectedItem().toString();
			        	switch(parola) {
			    	    	case "Nome":
			    	    		String tmp1 = nuovoAttributoUtente.getText();
			    	    		user.get(j).setNome(tmp1);
			    	    		break;
			    	    	case "Cognome":
			    	    		String tmp2 = nuovoAttributoUtente.getText();
			    	    		user.get(j).setCognome(tmp2);
			    	    		break;
			    	    	case "Sesso":
			    	    		String tmp3 = nuovoAttributoUtente.getText();
			    	    		user.get(j).setSesso(tmp3);
			    	    		break;
			    	    	case "CodiceFiscale":
			    	    		String tmp4 = nuovoAttributoUtente.getText();
			    	    		user.get(j).setCodiceFiscale(tmp4);
			    	    		break;
			    	    	case "DataDiNascita":
			    	    		String tmp5 = nuovoAttributoUtente.getText();
			    	    		user.get(j).setDataDiNascita(tmp5);
			    	    		break;
			        	}
			        
			        	try{
			                FileWriter writer = new FileWriter(fileName);
			                System.out.println(userX.toString());
			                gson.toJson(user, writer);
			                writer.close();
			            } catch (JsonIOException | IOException e){
			                e.printStackTrace();
			            }
		    		}
	    		}
			}
	    	
	    } catch (FileNotFoundException e){
	        e.printStackTrace();
	    }
    	
    	cfUtente.clear();
    	nuovoAttributoUtente.clear();
    	combUser.getSelectionModel().clearSelection();
    	
    	tableViewUser.getItems().clear();
    	userTableView();
    }
    
    /**
	 * Metodo per aggiungere un workspace.
	 */
    public void aggiungiWorkspace(){  
    	
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String fileName2 = "workspace.json";
		Workspace[] workspaceFromJson;
    	LinkedList<Workspace> workspace = new LinkedList<>();
    	
    	String fileName3 = "activities.json";
    	Attivita[] attivitaFromJson;
    	Attivita attivitaX = null;
    	LinkedList<Attivita> listaAttivita = new LinkedList<>();

    	try{
			JsonReader reader = new JsonReader(new FileReader(fileName2));
			workspaceFromJson = gson.fromJson(reader, Workspace[].class);

	    	for(int i=0; i<workspaceFromJson.length; i++) {
	    		Workspace workspaceX = new Workspace(workspaceFromJson[i].getId(), workspaceFromJson[i].getDescrizione(), 
	    				workspaceFromJson[i].getListaAttivita());
	    		workspace.add(workspaceX);
	    	}
	    	
	    	String id = txtIdWorkspace.getText();
	    	int idInt = Integer.parseInt(id);
	    	String descrizione = txtDescrizioneWorkspace.getText();
	    	
	    	try{
	    		JsonReader reader2 = new JsonReader(new FileReader(fileName3));
	    		attivitaFromJson = gson.fromJson(reader2, Attivita[].class);

		    	String idAttivitaX = txtListaAttivitaWorkspace.getText();
		    	int idAttivitaInt = Integer.parseInt(idAttivitaX);
		    	for(int i=0; i<attivitaFromJson.length; i++) {
		    		if(attivitaFromJson[i].getId() == idAttivitaInt) {
		   				attivitaX = new Attivita(attivitaFromJson[i].getId(), 
		   					attivitaFromJson[i].getDescrizione(), attivitaFromJson[i].getDurata(), 
		   					attivitaFromJson[i].getGiornoDisponibile(), attivitaFromJson[i].getDateDisponibili());
		   				listaAttivita.add(attivitaX);
	    			}	
	    		}
	        } catch (FileNotFoundException e){
	            e.printStackTrace();
	        }
	   
	    	Workspace workspace2 = new Workspace(idInt, descrizione, listaAttivita);
	    	workspace.add(workspace2);
	    	
	        try{
	            FileWriter writer = new FileWriter(fileName2);
	            gson.toJson(workspace, writer);
	            writer.close();
	        } catch (JsonIOException | IOException e){
	            e.printStackTrace();
	        }
	        
    	}  catch (FileNotFoundException e){
            e.printStackTrace();
        }
    	
    	txtIdWorkspace.clear();
    	txtDescrizioneWorkspace.clear();
    	txtListaAttivitaWorkspace.clear();
    	
    	tableViewWorkspace.getItems().clear();
    	workspaceTableView();
    }
    
    /**
	 * Metodo per modificare un attributo di workspace.
	 */
    public void modificaWorkspace() {
    	
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String fileName2 = "workspace.json";
		Workspace[] workspaceFromJson;
    	LinkedList<Workspace> workspace = new LinkedList<>();
    	
    	Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
    	String fileName3 = "activities.json";
    	Attivita[] attivitaFromJson;
    	
    	String idworkspaceX = idWorkspace.getText();
    	int idWorkspaceInt = Integer.parseInt(idworkspaceX);
    	
    	LinkedList<Attivita> listaAttivita = new LinkedList<>();

    	try{
			JsonReader reader = new JsonReader(new FileReader(fileName2));
			workspaceFromJson = gson.fromJson(reader, Workspace[].class);

	    	for(int i=0; i<workspaceFromJson.length; i++) {
	    		Workspace workspaceX = new Workspace(workspaceFromJson[i].getId(), workspaceFromJson[i].getDescrizione(), 
	    				workspaceFromJson[i].getListaAttivita());
	    		workspace.add(workspaceX);
		    	
		    	if(workspaceFromJson[i].getId() == idWorkspaceInt) {
		    		listaAttivita.addAll(workspaceFromJson[i].getListaAttivita());
		    	}
		    	
		    	for(int j=0; j<workspace.size(); j++) {
		    		if(workspace.get(j).getId() == idWorkspaceInt) {
		    		
			    		String parola = combWorkspace.getSelectionModel().getSelectedItem().toString();
			        	switch(parola) {
			    	    	case "Id":
			    	    		String tmp1 = nuovoAttributoWorkspace.getText();
			    	    		int tmp1Int = Integer.parseInt(tmp1);
			    	    		workspace.get(j).setId(tmp1Int);
			    	    		break;
			    	    	case "Descrizione":
			    	    		String tmp2 = nuovoAttributoWorkspace.getText();
			    	    		workspace.get(j).setDescrizione(tmp2);
			    	    		break;
			    	    	case "ListaAttivita":
			    	    		String tmp3 = nuovoAttributoWorkspace.getText();
			    	    		int tmp3Int = Integer.parseInt(tmp3);
			    	    		try{
			    	        		JsonReader reader2 = new JsonReader(new FileReader(fileName3));
			    	        		attivitaFromJson = gson2.fromJson(reader2, Attivita[].class);
			    	        		
			    	        		for(int z=0; z<attivitaFromJson.length; z++) {
			        	    			if(attivitaFromJson[z].getId() == tmp3Int) {
			        	    				Attivita attivitaX = new Attivita(attivitaFromJson[z].getId(), 
			        	    					attivitaFromJson[z].getDescrizione(), attivitaFromJson[z].getDurata(), 
			        	    					attivitaFromJson[z].getGiornoDisponibile(), attivitaFromJson[z].getDateDisponibili());
			        	    				listaAttivita.add(attivitaX);
			        	    			}	
			        	    		}
			    	    		} catch (FileNotFoundException e){
			    	                e.printStackTrace();
			    	            }
			    	    		workspace.get(j).setListaAttivita(listaAttivita);
			    	    		break;
			        	}
			        	
			        	try{
			                FileWriter writer = new FileWriter(fileName2);
			              
			                gson.toJson(workspace, writer);
			                writer.close();
			            } catch (JsonIOException | IOException e){
			                e.printStackTrace();
			            }
		    		}
	    		}
			}
	    	
	    } catch (FileNotFoundException e){
	        e.printStackTrace();
	    }
    	
    	idWorkspace.clear();
    	nuovoAttributoWorkspace.clear();
    	combWorkspace.getSelectionModel().clearSelection();
    	
    	tableViewWorkspace.getItems().clear();
    	workspaceTableView();
    }
    
    /**
	 * Metodo per eliminare tutte le attività presenti nel
	 * workspace selezionato.
	 */
    public void rimuoviListaWorkspace() {
    	
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String fileName2 = "workspace.json";
		Workspace[] workspaceFromJson;
    	LinkedList<Workspace> workspace = new LinkedList<>();
    	
    	String idworkspaceX = idWorkspace.getText();
    	int idWorkspaceInt = Integer.parseInt(idworkspaceX);
    	
    	LinkedList<Attivita> listaAttivita = new LinkedList<>();

    	try{
			JsonReader reader = new JsonReader(new FileReader(fileName2));
			workspaceFromJson = gson.fromJson(reader, Workspace[].class);

	    	for(int i=0; i<workspaceFromJson.length; i++) {
	    		Workspace workspaceX = new Workspace(workspaceFromJson[i].getId(), workspaceFromJson[i].getDescrizione(), 
	    				workspaceFromJson[i].getListaAttivita());
	    		workspace.add(workspaceX);
		    	
		    	if(workspaceFromJson[i].getId() == idWorkspaceInt) {
		    		listaAttivita.addAll(workspaceFromJson[i].getListaAttivita());
		    	}
		    	
		    	for(int j=0; j<workspace.size(); j++) {
		    		if(workspace.get(j).getId() == idWorkspaceInt) {
		    		
			    		String parola = combWorkspace.getSelectionModel().getSelectedItem().toString();
			        	if(parola.equals("ListaAttivita")) {
			        		for (int k=listaAttivita.size()-1; k>=0; k--) {
			        			listaAttivita.remove(k);
							}
		    	    		workspace.get(j).setListaAttivita(listaAttivita);
			        	}
			        	
			        	try{
			                FileWriter writer = new FileWriter(fileName2);
			              
			                gson.toJson(workspace, writer);
			                writer.close();
			            } catch (JsonIOException | IOException e){
			                e.printStackTrace();
			            }
		    		}
	    		}
			}
	    	
	    } catch (FileNotFoundException e){
	        e.printStackTrace();
	    }
    	
    	idWorkspace.clear();
    	combWorkspace.getSelectionModel().clearSelection();
    	
    	tableViewWorkspace.getItems().clear();
    	workspaceTableView();
    	
    }
    
    /**
	 * Metodo per aggiungere un'attività.
	 */
    public void aggiungiAttivita(){  
    	
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	String fileName3 = "activities.json";
    	Attivita[] attivitaFromJson;
    	LinkedList<Attivita> attivita = new LinkedList<>();
    	LinkedList<String> dateDisponibili = new LinkedList<String>();

    	try{
			JsonReader reader = new JsonReader(new FileReader(fileName3));
			attivitaFromJson = gson.fromJson(reader, Attivita[].class);

	    	for(int i=0; i<attivitaFromJson.length; i++) {
		    	Attivita attivitaX = new Attivita(attivitaFromJson[i].getId(), attivitaFromJson[i].getDescrizione(), 
		    			attivitaFromJson[i].getDurata(), attivitaFromJson[i].getGiornoDisponibile(), 
		    			attivitaFromJson[i].getDateDisponibili());;
		    	attivita.add(attivitaX);
	    	}
	    	
	    	String id = txtIdAttivita.getText();
	    	String descrizione = txtDescrizioneAttivita.getText();
	    	String durata = txtDurataAttivita.getText();
	    	String giornoDisponibile = txtGiornoDisponibileAttivita.getText();
	    	String date = txtDateDisponibiliAttivita.getText();
	    	dateDisponibili.add(date);
	   
	    	int idInt = Integer.parseInt(id);
	    	int durataInt = Integer.parseInt(durata);
	    	Attivita attivita2 = new Attivita(idInt , descrizione, durataInt, giornoDisponibile, dateDisponibili);
	    	attivita.add(attivita2);
	    	
	        try{
	            FileWriter writer = new FileWriter(fileName3);
	            gson.toJson(attivita, writer);
	            writer.close();
	        } catch (JsonIOException | IOException e){
	            e.printStackTrace();
	        }
	        
    	}  catch (FileNotFoundException e){
            e.printStackTrace();
        }
    	
    	txtIdAttivita.clear();
    	txtDescrizioneAttivita.clear();
    	txtDurataAttivita.clear();
    	txtGiornoDisponibileAttivita.clear();
    	txtDateDisponibiliAttivita.clear();
    	
    	tableViewAttivita.getItems().clear();
    	attivitaTableView();
    }
    
    /**
	 * Metodo per modificare un attributo di attività.
	 */
    public void modificaAttivita() {
    	
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	String fileName3 = "activities.json";
    	Attivita[] attivitaFromJson;
    	LinkedList<Attivita> attivita = new LinkedList<>();
    	
    	String idAttivitaX = idAttivita.getText();
    	int idAttivitaInt = Integer.parseInt(idAttivitaX);
    	
    	LinkedList<String> listaDate = new LinkedList<String>();

    	try{
			JsonReader reader = new JsonReader(new FileReader(fileName3));
			attivitaFromJson = gson.fromJson(reader, Attivita[].class);

	    	for(int i=0; i<attivitaFromJson.length; i++) {
	    		Attivita attivitaX = new Attivita(attivitaFromJson[i].getId(), attivitaFromJson[i].getDescrizione(), 
	    				attivitaFromJson[i].getDurata(),attivitaFromJson[i].getGiornoDisponibile(), 
	    				attivitaFromJson[i].getDateDisponibili());;
		    	attivita.add(attivitaX);
		    	
		    	if(attivitaFromJson[i].getId() == idAttivitaInt) {
		    		listaDate.addAll(attivitaFromJson[i].getDateDisponibili());
		    	}
		    	
		    	for(int j=0; j<attivita.size(); j++) {
		    		if(attivita.get(j).getId() == idAttivitaInt) {
		    		
			    		String parola = combAttivita.getSelectionModel().getSelectedItem().toString();
			        	switch(parola) {
			    	    	case "Id":
			    	    		String tmp1 = nuovoAttributoAttivita.getText();
			    	    		int tmp1Int = Integer.parseInt(tmp1);
			    	    		attivita.get(j).setId(tmp1Int);
			    	    		break;
			    	    	case "Descrizione":
			    	    		String tmp2 = nuovoAttributoAttivita.getText();
			    	    		attivita.get(j).setDescrizione(tmp2);
			    	    		break;
			    	    	case "Durata":
			    	    		String tmp3 = nuovoAttributoAttivita.getText();
			    	    		int tmp3Int = Integer.parseInt(tmp3);
			    	    		attivita.get(j).setDurata(tmp3Int);
			    	    		break;
			    	    	case "GiornoDisponibile":
			    	    		String tmp4 = nuovoAttributoAttivita.getText();
			    	    		attivita.get(j).setGiornoDisponibile(tmp4);
			    	    		break;
			    	    	case "DateDisponibili":
			    	    		String data = nuovoAttributoAttivita.getText();
			    	    		listaDate.add(data);
			    	    		attivita.get(j).setDateDisponibili(listaDate);
			    	    		break;
			        	}
			        	
			        	try{
			                FileWriter writer = new FileWriter(fileName3);
			              
			                gson.toJson(attivita, writer);
			                writer.close();
			            } catch (JsonIOException | IOException e){
			                e.printStackTrace();
			            }
		    		}
	    		}
			}
	    	
	    } catch (FileNotFoundException e){
	        e.printStackTrace();
	    }
    	
    	idAttivita.clear();
    	nuovoAttributoAttivita.clear();
    	combAttivita.getSelectionModel().clearSelection();
    	
    	tableViewAttivita.getItems().clear();
    	attivitaTableView();
    }	
    
    /**
	 * Metodo per eliminare tutte le date disponibili per
	 * l'attività selezionata.
	 */
    public void rimuoviListaAttivita() {
    	
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	String fileName3 = "activities.json";
    	Attivita[] attivitaFromJson;
    	LinkedList<Attivita> attivita = new LinkedList<>();
    	
    	String idAttivitaX = idAttivita.getText();
    	int idAttivitaInt = Integer.parseInt(idAttivitaX);
    	
    	LinkedList<String> listaDate = new LinkedList<String>();

    	try{
			JsonReader reader = new JsonReader(new FileReader(fileName3));
			attivitaFromJson = gson.fromJson(reader, Attivita[].class);

	    	for(int i=0; i<attivitaFromJson.length; i++) {
	    		Attivita attivitaX = new Attivita(attivitaFromJson[i].getId(), attivitaFromJson[i].getDescrizione(), 
	    				attivitaFromJson[i].getDurata(),attivitaFromJson[i].getGiornoDisponibile(), 
	    				attivitaFromJson[i].getDateDisponibili());;
		    	attivita.add(attivitaX);
		    	
		    	if(attivitaFromJson[i].getId() == idAttivitaInt) {
		    		listaDate.addAll(attivitaFromJson[i].getDateDisponibili());
		    	}
		    	
		    	for(int j=0; j<attivita.size(); j++) {
		    		if(attivita.get(j).getId() == idAttivitaInt) {
		    		
			    		String parola = combAttivita.getSelectionModel().getSelectedItem().toString();
			    		if(parola.equals("DateDisponibili")) {
			        		for (int k=listaDate.size()-1; k>=0; k--) {
			        			listaDate.remove(k);
							}
			        		attivita.get(j).setDateDisponibili(listaDate);
			        	}
			        	
			        	try{
			                FileWriter writer = new FileWriter(fileName3);
			              
			                gson.toJson(attivita, writer);
			                writer.close();
			            } catch (JsonIOException | IOException e){
			                e.printStackTrace();
			            }
		    		}
	    		}
			}
	    	
	    } catch (FileNotFoundException e){
	        e.printStackTrace();
	    }
    	
    	idAttivita.clear();
    	combAttivita.getSelectionModel().clearSelection();
    	
    	tableViewAttivita.getItems().clear();
    	attivitaTableView();
    }
    
    /**
	 * Metodo per modificare la preferenza di uno specifico utente.
	 */
    public void modificaPreferenze() {
    	
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	String fileName = "preferencesUsers.json";
    	Preferenza[] preferenzeFromJson;
    	LinkedList<Preferenza> preferenze = new LinkedList<>();
    	
    	String cfUtente = combCfUtentePreferenze.getSelectionModel().getSelectedItem().toString();
    	String descrizioneAttivita = combAttivitaUtentePreferenze.getSelectionModel().getSelectedItem().toString();
    	
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
		    		if(preferenze.get(j).getCfUtente().equals(cfUtente) && preferenze.get(j).getDescrizioneAttivita().equals(descrizioneAttivita)) {
		    		
			    		String parola = combPreferenze.getSelectionModel().getSelectedItem().toString();
			        	switch(parola) {
			    	    	case "Data":
			    	    		String tmp1 = nuovoAttributoPreferenze.getText();
			    	    		try{
			    	    			JsonReader reader2 = new JsonReader(new FileReader(fileName2));
			    	    			attivitaFromJson = gson.fromJson(reader2, Attivita[].class);

			    	    	    	for(int k=0; k<attivitaFromJson.length; k++) {
			    	    	    		if(attivitaFromJson[k].getDescrizione().equals(descrizioneAttivita)) {
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
    	
    	combCfUtentePreferenze.getSelectionModel().clearSelection();
    	combAttivitaUtentePreferenze.getSelectionModel().clearSelection();
    	nuovoAttributoPreferenze.clear();
    	combPreferenze.getSelectionModel().clearSelection();
    	
    	tableViewPreferenze.getItems().clear();
    	preferenzeTableView();
		    	
    }
    
    /**
	 * Metodo per popolare la combobox con i codici fiscali di
	 * tutti gli utenti che hanno espresso una preferenza.
	 */
    public void setComboboxCfUtente() {
    	
    	ObservableList<String> cfList = FXCollections.observableArrayList();
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String fileName = "preferencesUsers.json";
    	Preferenza[] preferenzeFromJson;
		
		try{
			JsonReader reader = new JsonReader(new FileReader(fileName));
			preferenzeFromJson = gson.fromJson(reader, Preferenza[].class);
	
	    	for(int i=0; i<preferenzeFromJson.length; i++) {
	    		if(!cfList.contains(preferenzeFromJson[i].getCfUtente())){
	    			cfList.add(preferenzeFromJson[i].getCfUtente());
	    		}
			}
	    	
	    } catch (FileNotFoundException e){
	        e.printStackTrace();
	    }
		combCfUtentePreferenze.setItems(cfList);
	}
    
    /**
	 * Metodo per popolare la combobox con le attività scelte
	 * dall'utente selezionato.
	 */
    public void setComboboxAttivitaUtente() {
    	
    	ObservableList<String> attivitaList = FXCollections.observableArrayList();
    	String cfUtente = combCfUtentePreferenze.getSelectionModel().getSelectedItem().toString();
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String fileName = "preferencesUsers.json";
    	Preferenza[] preferenzeFromJson;
		
		try{
			JsonReader reader = new JsonReader(new FileReader(fileName));
			preferenzeFromJson = gson.fromJson(reader, Preferenza[].class);
	
	    	for(int i=0; i<preferenzeFromJson.length; i++) {
	    		if(preferenzeFromJson[i].getCfUtente().equals(cfUtente)){
	    			attivitaList.add(preferenzeFromJson[i].getDescrizioneAttivita());
	    		}
			}
	    	
	    } catch (FileNotFoundException e){
	        e.printStackTrace();
	    }
		combAttivitaUtentePreferenze.setItems(attivitaList);
	}
    
    /**
	 * Metodo per inserire la lista degli utenti nella relativa tabella.
	 */
	public void userTableView() {
		
		ObservableList<Utente> userList = FXCollections.observableArrayList();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String fileName = "user.json";
		Utente[] userFromJson;
		Utente userX = null;
		
		try{
			JsonReader reader = new JsonReader(new FileReader(fileName));
			userFromJson = gson.fromJson(reader, Utente[].class);

	    	for(int i=0; i<userFromJson.length; i++) {
	    		userX = new Utente(userFromJson[i].getNome(), userFromJson[i].getCognome(), userFromJson[i].getSesso(), 
	    				userFromJson[i].getCodiceFiscale(), userFromJson[i].getDataDiNascita());
	    		userList.add(userX);
			}
	    	
	    } catch (FileNotFoundException e){
	        e.printStackTrace();
	    }
		colonnaNome.setCellValueFactory(new PropertyValueFactory<Utente, String>("Nome"));
		colonnaCognome.setCellValueFactory(new PropertyValueFactory<Utente, String>("Cognome"));
		colonnaSesso.setCellValueFactory(new PropertyValueFactory<Utente, String>("Sesso"));
		colonnaCodiceFiscale.setCellValueFactory(new PropertyValueFactory<Utente, String>("CodiceFiscale"));
		colonnaDataNascita.setCellValueFactory(new PropertyValueFactory<Utente, String>("DataDiNascita"));
		
		tableViewUser.setItems(userList);
	}
	
	/**
	 * Metodo per inserire la lista dei workspace nella relativa tabella.
	 */
	public void workspaceTableView() {
		
		ObservableList<Workspace> workspaceList = FXCollections.observableArrayList();
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String fileName = "workspace.json";
		Workspace[] workspaceFromJson;
		Workspace workspaceX = null;
		
		try{
			JsonReader reader = new JsonReader(new FileReader(fileName));
			workspaceFromJson = gson.fromJson(reader, Workspace[].class);

	    	for(int i=0; i<workspaceFromJson.length; i++) {
	    		workspaceX = new Workspace(workspaceFromJson[i].getId(), workspaceFromJson[i].getDescrizione(), 
	    				workspaceFromJson[i].getListaAttivita());
	    		workspaceList.add(workspaceX);
			}
	    	
	    } catch (FileNotFoundException e){
	        e.printStackTrace();
	    }
		colonnaIdWorkspace.setCellValueFactory(new PropertyValueFactory<Workspace, Integer>("Id"));
		colonnaDescrizioneWorkspace.setCellValueFactory(new PropertyValueFactory<Workspace, String>("Descrizione"));
		
		tableViewWorkspace.setItems(workspaceList);
	}

	/**
	 * Metodo per inserire la lista delle attività nella relativa tabella.
	 */
	public void attivitaTableView() {
	
		ObservableList<Attivita> attivitaList = FXCollections.observableArrayList();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String fileName = "activities.json";
		Attivita[] attivitaFromJson;
		Attivita attivitaX = null;
		
		try{
			JsonReader reader = new JsonReader(new FileReader(fileName));
			attivitaFromJson = gson.fromJson(reader, Attivita[].class);
	
	    	for(int i=0; i<attivitaFromJson.length; i++) {
	    		attivitaX = new Attivita(attivitaFromJson[i].getId(), attivitaFromJson[i].getDescrizione(), attivitaFromJson[i].getDurata(),
	    				attivitaFromJson[i].getGiornoDisponibile(), attivitaFromJson[i].getDateDisponibili());
	    		attivitaList.add(attivitaX);
			}
	    	
	    } catch (FileNotFoundException e){
	        e.printStackTrace();
	    }
		colonnaIdAttivita.setCellValueFactory(new PropertyValueFactory<Attivita, Integer>("Id"));
		colonnaDescrizioneAttivita.setCellValueFactory(new PropertyValueFactory<Attivita, String>("Descrizione"));
		colonnaDurata.setCellValueFactory(new PropertyValueFactory<Attivita, Integer>("Durata"));
		colonnaGiornoDisponibile.setCellValueFactory(new PropertyValueFactory<Attivita, String>("GiornoDisponibile"));
		colonnaDateDisponibili.setCellValueFactory(new PropertyValueFactory<Attivita, String>("DateDisponibili"));
		
		tableViewAttivita.setItems(attivitaList);
	}
	
	/**
	 * Metodo per inserire la lista delle preferenze di tutti gli 
	 * utenti nella relativa tabella.
	 */
	public void preferenzeTableView() {
		
		ObservableList<Preferenza> preferenzeList = FXCollections.observableArrayList();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String fileName = "preferencesUsers.json";
		Preferenza[] preferenzeFromJson;
		Preferenza preferenzaX = null;
		
		try{
			JsonReader reader = new JsonReader(new FileReader(fileName));
			preferenzeFromJson = gson.fromJson(reader, Preferenza[].class);
	
	    	for(int i=0; i<preferenzeFromJson.length; i++) {
	    		preferenzaX = new Preferenza(preferenzeFromJson[i].getCfUtente(), preferenzeFromJson[i].getDescrizioneWorkspace(),
						preferenzeFromJson[i].getDescrizioneAttivita(), preferenzeFromJson[i].getData(), preferenzeFromJson[i].getOrario(),
						preferenzeFromJson[i].getNoteAggiuntive());
	    		preferenzeList.add(preferenzaX);
			}
	    	
	    } catch (FileNotFoundException e){
	        e.printStackTrace();
	    }
		colonnaCfUtente.setCellValueFactory(new PropertyValueFactory<Preferenza, String>("CfUtente"));
		colonnaDescrizioneWorkspacePref.setCellValueFactory(new PropertyValueFactory<Preferenza, String>("DescrizioneWorkspace"));
		colonnaDescrizioneAttivitaPref.setCellValueFactory(new PropertyValueFactory<Preferenza, String>("DescrizioneAttivita"));
		colonnaData.setCellValueFactory(new PropertyValueFactory<Preferenza, String>("Data"));
		colonnaOrario.setCellValueFactory(new PropertyValueFactory<Preferenza, String>("Orario"));
		colonnaNoteAggiuntive.setCellValueFactory(new PropertyValueFactory<Preferenza, String>("NoteAggiuntive"));
		
		tableViewPreferenze.setItems(preferenzeList);
	}
	
	/**
	 * Called to initialize a controller.
	 */
	public void initialize(URL location, ResourceBundle resources) {
		
		userTableView();
		workspaceTableView();
		attivitaTableView();
		preferenzeTableView();
		setComboboxCfUtente();
		
		ObservableList<String> combUserList = FXCollections.observableArrayList("Nome", "Cognome", "Sesso", "CodiceFiscale", "DataDiNascita");
		combUser.setItems(combUserList);
		ObservableList<String> combWorkspaceList = FXCollections.observableArrayList("Id", "Descrizione", "ListaAttivita");
		combWorkspace.setItems(combWorkspaceList);
		ObservableList<String> combAttivitaList = FXCollections.observableArrayList("Id", "Descrizione", "Durata", "GiornoDisponibile", "DateDisponibili");
		combAttivita.setItems(combAttivitaList);
		ObservableList<String> combPreferenzeList = FXCollections.observableArrayList("Data", "Orario", "NoteAggiuntive");
		combPreferenze.setItems(combPreferenzeList);
    }	
}

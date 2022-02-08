package application;
	
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonReader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static Stage stg; 

	@Override
	public void start(Stage primaryStage) {
		stg = primaryStage;
		primaryStage.setResizable(false);
			
		try {
			Parent root = FXMLLoader.load(getClass().getResource("GuiLogin.fxml"));
			//Parent root = FXMLLoader.load(getClass().getResource("GuiAdmin.fxml"));
			//Parent root = FXMLLoader.load(getClass().getResource("GuiUser.fxml"));

			Scene scene = new Scene(root, 800, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Prenotazione Medico");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo per cambiare finestra grafica.
	 */
	public void changeScene(String fxml) throws IOException {
		Parent pane = FXMLLoader.load(getClass().getResource(fxml));
		stg.getScene().setRoot(pane);
	}
		
	public static void main(String[] args) throws FileNotFoundException {
		
		launch(args);
		
		/**
		 * Istanzia l'amministratore.
		 */
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String fileName = "admin.json";
       
       	Admin admin = new Admin("Pietro", "Lelli", "PTRLL00R483S39H", "prenotazioneMedico00");
        	
    	try {
        	FileWriter writer = new FileWriter(fileName);
        	gson.toJson(admin, writer);
       		writer.close();
        } catch (JsonIOException | IOException e){
            ((Throwable) e).printStackTrace();
        }
        
		/**
		 * Istanzia utenti base.
		 */
        String fileName1 = "user.json";
        
        Scanner scan = new Scanner(new File("user.json")); 
        if (! scan.hasNextLine()) {
        	Utente user1 = new Utente("Massimo", "Rondelli", "M", "RNDMSM00E14A944O", "2000/5/14");
        	Utente user2 = new Utente("Marianna", "Gimigliano", "F", "MRNGMG00A24A944F", "2000/10/13");
        	Utente user3 = new Utente("Luca", "Rossi", "M", "LCARSS85T58H543L", "1985/04/12");
        	Utente user4 = new Utente("Filippo", "Rocchi", "M", "FLPRCC98F27F493G", "1998/11/02");
        	Utente user5 = new Utente("Francesca", "Mazzotti", "F", "FRCMZT67G462P890R", "1967/02/23");
        	LinkedList<Utente> user = new LinkedList<>();
       		user.add(user1);
       		user.add(user2);
       		user.add(user3);
       		user.add(user4);
       		user.add(user5);
       		
       		try {
	        	FileWriter writer = new FileWriter(fileName1);
	        	gson.toJson(user, writer);
	       		writer.close();
	        } catch (JsonIOException | IOException e){
	            ((Throwable) e).printStackTrace();
	        }

        }
        scan.close();
        
        /**
		 * Istanzia credenziali di accesso al programma.
		 */
        String fileName2 = "credentials.json";
        LinkedList<Credenziali> credenziali = new LinkedList<>();
        
        Scanner scan1 = new Scanner(new File("credentials.json")); 
        if (! scan1.hasNextLine()) {
	        Credenziali credenziali1 = new Credenziali("RNDMSM00E14A944O", "1234");
	    	Credenziali credenziali2 = new Credenziali("MRNGMG00A24A944F", "marianna1");
	    	Credenziali credenziali3 = new Credenziali("LCARSS85T58H543L", "lucarossi");
	    	Credenziali credenziali4 = new Credenziali("FLPRCC98F27F493G", "11021998");
	    	Credenziali credenziali5 = new Credenziali("FRCMZT67G462P890R", "mazz23");
	    	credenziali.add(credenziali1);
	    	credenziali.add(credenziali2);
	    	credenziali.add(credenziali3);
	    	credenziali.add(credenziali4);
	    	credenziali.add(credenziali5);
	        
	    	try{
	            FileWriter writer = new FileWriter(fileName2);
	            gson.toJson(credenziali, writer);
	            writer.close();
	        } catch (JsonIOException | IOException e){
	            e.printStackTrace();
	        }
        }
        scan1.close();
	   
        /**
		 * Istanzia attività base.
		 */
        String fileName3 = "activities.json";
        
        Scanner scan2 = new Scanner(new File("activities.json")); 
        if (! scan2.hasNextLine()) {
        	LinkedList<String> dateDisponibili1 = new LinkedList<String>();
            dateDisponibili1.add("19/08");
            dateDisponibili1.add("26/08");
            dateDisponibili1.add("02/09");
            dateDisponibili1.add("09/09");
            Attivita attivita1 = new Attivita(1, "Prescrizione Medicinali", 10, "Giovedì", dateDisponibili1);
            
            LinkedList<String> dateDisponibili2 = new LinkedList<String>();
            dateDisponibili2.add("21/08");
            dateDisponibili2.add("28/08");
            dateDisponibili2.add("04/09");
            Attivita attivita2 = new Attivita(2, "Visita Sportivo-Agonistica", 30, "Mercoledi", dateDisponibili2);
            
            LinkedList<String> dateDisponibili3 = new LinkedList<String>();
            dateDisponibili3.add("29/08");
            dateDisponibili3.add("05/09");
            dateDisponibili3.add("12/09");
            Attivita attivita3 = new Attivita(3, "Visita per Malattia", 20, "Giovedi", dateDisponibili3);
            
            LinkedList<String> dateDisponibili4 = new LinkedList<String>();
            dateDisponibili4.add("24/08");
            dateDisponibili4.add("31/08");
            dateDisponibili4.add("07/09");
            Attivita attivita4 = new Attivita(4, "Visita di Controllo Periodica", 15, "Sabato", dateDisponibili4);
            
            LinkedList<String> dateDisponibili5 = new LinkedList<String>();
            dateDisponibili5.add("24/08");
            dateDisponibili5.add("07/09");
            Attivita attivita5 = new Attivita(5, "Elettrocardiogramma", 30, "Sabato", dateDisponibili5);
            
            LinkedList<String> dateDisponibili6 = new LinkedList<String>();
            dateDisponibili6.add("30/08");
            dateDisponibili6.add("06/09");
            dateDisponibili6.add("20/09");
            dateDisponibili6.add("04/10");
            Attivita attivita6 = new Attivita(6, "Risonanza Magnetica Cardiaca", 15, "Lunedì", dateDisponibili6);
            
            LinkedList<String> dateDisponibili7 = new LinkedList<String>();
            dateDisponibili7.add("27/08");
            dateDisponibili7.add("03/09");
            dateDisponibili7.add("10/09");
            Attivita attivita7 = new Attivita(7, "Radiologia", 5, "Venerdì", dateDisponibili7);
            
            LinkedList<String> dateDisponibili8 = new LinkedList<String>();
            dateDisponibili8.add("31/08");
            dateDisponibili8.add("07/09");
            dateDisponibili8.add("14/09");
            dateDisponibili8.add("05/10");
            Attivita attivita8 = new Attivita(8, "Test da Sforzo", 25, "Martedì", dateDisponibili8);
            
            LinkedList<Attivita> attivita = new LinkedList<>();
            attivita.add(attivita1);
            attivita.add(attivita2);
            attivita.add(attivita3);
            attivita.add(attivita4);
            attivita.add(attivita5);
            attivita.add(attivita6);
            attivita.add(attivita7);
            attivita.add(attivita8);
            
            try{
                FileWriter writer = new FileWriter(fileName3);
                gson.toJson(attivita, writer);
                writer.close();
            } catch (JsonIOException | IOException e){
                ((Throwable) e).printStackTrace();
            }
        }
        scan2.close();
        
        /**
		 * Istanzia workspace base.
		 */
        String fileName4 = "workspace.json";
        Attivita[] attivitaFromJson;
        LinkedList<Attivita> listaAttivitaX = new LinkedList<>();
        Attivita attivitaX = null;
        
        Scanner scan3 = new Scanner(new File("workspace.json")); 
        if (! scan3.hasNextLine()) {
        	
        	try{
        		JsonReader reader = new JsonReader(new FileReader(fileName3));
    			attivitaFromJson = gson.fromJson(reader, Attivita[].class);
    			
    			for(int i=0; i<attivitaFromJson.length; i++) {
    	    		attivitaX = new Attivita(attivitaFromJson[i].getId(), attivitaFromJson[i].getDescrizione(), attivitaFromJson[i].getDurata(),
    	    				attivitaFromJson[i].getGiornoDisponibile(), attivitaFromJson[i].getDateDisponibili());
    	    		listaAttivitaX.add(attivitaX);
    			}
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        	
        	LinkedList<Attivita> listaAttivita1 = new LinkedList<>();
            listaAttivita1.add(listaAttivitaX.get(0));
            listaAttivita1.add(listaAttivitaX.get(2));
            listaAttivita1.add(listaAttivitaX.get(3));
            Workspace workspace1 = new Workspace(1, "Medico di Base", listaAttivita1);
            
            LinkedList<Attivita> listaAttivita2 = new LinkedList<>();
            listaAttivita2.add(listaAttivitaX.get(4));
            listaAttivita2.add(listaAttivitaX.get(5));
            Workspace workspace2 = new Workspace(2, "Cardiologo", listaAttivita2);
            
            LinkedList<Attivita> listaAttivita3 = new LinkedList<>();
            listaAttivita3.add(listaAttivitaX.get(1));
            listaAttivita3.add(listaAttivitaX.get(7));
            Workspace workspace3 = new Workspace(3, "Medico Sportivo", listaAttivita3);
            
            LinkedList<Attivita> listaAttivita4 = new LinkedList<>();
            listaAttivita4.add(listaAttivitaX.get(6));
            Workspace workspace4 = new Workspace(4, "Radiologo", listaAttivita4);
            
            LinkedList<Workspace> workspace = new LinkedList<>();
            workspace.add(workspace1);
            workspace.add(workspace2);
            workspace.add(workspace3);
            workspace.add(workspace4);
            
            try{
                FileWriter writer = new FileWriter(fileName4);
                gson.toJson(workspace, writer);
                writer.close();
            } catch (JsonIOException | IOException e){
                e.printStackTrace();
            }
        }
        scan3.close();
        
	}
}
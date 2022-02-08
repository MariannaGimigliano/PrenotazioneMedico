package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonReader;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class PrenotazioneMedicoBot extends TelegramLongPollingBot {

	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	String fileName = "preferencesUsers.json";
	String fileName2 = "user.json";
	String fileName3 = "workspace.json";
	String fileName4 = "credentials.json";
	String fileName5 = "activities.json";
	Preferenza[] preferenzeFromJson;
	Utente[] userFromJson;
	Workspace[] workspaceFromJson;
	Credenziali[] credenzialiFromJson;
	Attivita[] attivitaFromJson; 

	LinkedList<Credenziali> credenziali = new LinkedList<>();
	LinkedList<Preferenza> preferenze = new LinkedList<>();

	String cf = null;
	String workspace = null;
	String attivita = null;
	String data = null;
	String orario = null;
	String noteAggiuntive = null;
	String preferenza = null;
	String password = null;    
	String nuovaPassword = null;

	int login = 0;
	int settaPassword = 0;
	int count = 0;
	int aggiungiPreferenza = 0;
	int modificaPreferenza = 0;
	int modificaDati = 0;
	int countPreferenze = 0;
	int passwordErrata = 0;

	@Override
	public String getBotUsername(){
		//TODO Auto-generated methods stud
		return "PrenotazioneMedico_BOT";
	}

	@Override
	public void onUpdateReceived( Update update ) {
		if( update.hasMessage() && update.getMessage().hasText() ) {
			SendMessage sendMessage = new SendMessage();

			String msg = update.getMessage().getText();
			String username = update.getMessage().getChat().getUserName();
			String chatId = update.getMessage().getChatId().toString();

			System.out.println( username + " says: " + msg );

			switch( login ) {          	
			case 1:
				try {	
					JsonReader reader = new JsonReader(new FileReader(fileName));
					preferenzeFromJson = gson.fromJson(reader, Preferenza[].class);

					for(int i=0; i<preferenzeFromJson.length; i++) {
						Preferenza preferenzaX = new Preferenza(
								preferenzeFromJson[i].getCfUtente(), 
								preferenzeFromJson[i].getDescrizioneWorkspace(),
								preferenzeFromJson[i].getDescrizioneAttivita(), 
								preferenzeFromJson[i].getData(), 
								preferenzeFromJson[i].getOrario(),
								preferenzeFromJson[i].getNoteAggiuntive());
						preferenze.add(preferenzaX);
					}
					try{

						JsonReader reader2 = new JsonReader( new FileReader( fileName4 ) );
						credenzialiFromJson = gson.fromJson(reader2, Credenziali[].class);

						cf = update.getMessage().getText(); 
						
						for( int i = 0; i < credenzialiFromJson.length; i++ ) {    
							if( credenzialiFromJson[i].getCodiceFiscale().equals( cf ) ) {		
								login = 2; 
								count = i;
								passwordErrata = 1;
								sendMessage.setText( "Insersci password per effettuare il login." );	
								break;
							} 
							
							if( !credenzialiFromJson[i].getCodiceFiscale().equals( cf ) ) {
								sendMessage.setText( "Codice fiscale errato. Riprovare." );
							}
						}
						
						
					} catch( FileNotFoundException e ) {
						e.printStackTrace();
					}
				} catch( FileNotFoundException e ) {
					e.printStackTrace();
				}      
			break;
			
			case 2:
				try {	
					JsonReader reader = new JsonReader(new FileReader(fileName));
					preferenzeFromJson = gson.fromJson(reader, Preferenza[].class);

					for(int i=0; i<preferenzeFromJson.length; i++) {
						Preferenza preferenzaX = new Preferenza(
								preferenzeFromJson[i].getCfUtente(), 
								preferenzeFromJson[i].getDescrizioneWorkspace(),
								preferenzeFromJson[i].getDescrizioneAttivita(), 
								preferenzeFromJson[i].getData(), 
								preferenzeFromJson[i].getOrario(),
								preferenzeFromJson[i].getNoteAggiuntive());
						preferenze.add(preferenzaX);
					}       
					try{

						JsonReader reader2 = new JsonReader( new FileReader( fileName4 ) );
						credenzialiFromJson = gson.fromJson(reader2, Credenziali[].class);

						password = update.getMessage().getText();

						for( int i = 0; i < credenzialiFromJson.length; i++ ) {
							if( credenzialiFromJson[count].getPassword().equals("")) {
								login = 3;
								sendMessage.setText("Setta la tua nuova password");	
							} else if( credenzialiFromJson[count].getPassword().equals( password ) ) {
								login = 4;
								sendMessage.setText("Benvenuto nel bot Prenotazione Medico! \n\n" + "I comandi disponibili del bot sono i seguenti: \n\n"+
										"/aggiungipreferenza: Con questo comando si ha la possiblit� di aggiungere una preferenza all'utente. \n" +
										"/modificapreferenza: Con questo comando si ha la possibilit� di modificare una preferenza dell'utente. \n" +
										"/visualizzapreferenze: Con questo comando si ha la possibilit� di visualizzare tutte le preferenze dell'utente. \n"+
										"/help: Con questo comando si vedono tutti i comandi disponibili del bot.");  
							} else if(!credenzialiFromJson[count].getPassword().equals( password ) && credenzialiFromJson[i].getCodiceFiscale().equals( cf )){
								sendMessage.setText("Password errata. Riprovare");
							}
						}
						System.out.println( password + " " + cf );
						
					} catch( FileNotFoundException e ) {
						e.printStackTrace();
					}
				} catch( FileNotFoundException e ) {
					e.printStackTrace();
				}	

				break;
			case 3:
				login = 4;
				password = update.getMessage().getText();
				try {
					JsonReader reader = new JsonReader(new FileReader(fileName4));
					credenzialiFromJson = gson.fromJson(reader, Credenziali[].class);

					for(int i=0; i<credenzialiFromJson.length; i++) {
						Credenziali credenzialiX = new Credenziali(credenzialiFromJson[i].getCodiceFiscale(), credenzialiFromJson[i].getPassword());
						credenziali.add( credenzialiX );
					}

					credenziali.get(count).setPassword( password );

					sendMessage.setText( "Benvenuto nel bot Prenotazione Medico! \n\n" + "I comandi disponibili del bot sono i seguenti: \n\n"+
							"/aggiungipreferenza: Con questo comando si ha la possiblit� di aggiungere una preferenza all'utente. \n" +
							"/modificapreferenza: Con questo comando si ha la possibilit� di modificare una preferenza dell'utente. \n" +
							"/visualizzapreferenze: Con questo comando si ha la possibilit� di visualizzare tutte le preferenze dell'utente. \n"+
							"/help: Con questo comando si vedono tutti i comandi disponibili del bot.");   

					try{
						FileWriter writer = new FileWriter(fileName4);
						gson.toJson(credenziali, writer);
						writer.close();
					} catch (JsonIOException | IOException e){
						e.printStackTrace();
					}
				} catch ( FileNotFoundException e) {
					e.printStackTrace();
				}
				break;
			}    
		
			/*
			 * Switch per il comando /aggiungipreferenza
			 */
			switch( aggiungiPreferenza ) {
			// Case 1: WORKSPACE
			case 1:
				workspace = update.getMessage().getText();
				
				try {	
					JsonReader reader = new JsonReader(new FileReader(fileName));
					preferenzeFromJson = gson.fromJson(reader, Preferenza[].class);

					for(int i=0; i<preferenzeFromJson.length; i++) {
						Preferenza preferenzaX = new Preferenza(
								preferenzeFromJson[i].getCfUtente(), 
								preferenzeFromJson[i].getDescrizioneWorkspace(),
								preferenzeFromJson[i].getDescrizioneAttivita(), 
								preferenzeFromJson[i].getData(), 
								preferenzeFromJson[i].getOrario(),
								preferenzeFromJson[i].getNoteAggiuntive());
						preferenze.add(preferenzaX);
					}
					try{

						JsonReader reader2 = new JsonReader( new FileReader( fileName3 ) );
						workspaceFromJson = gson.fromJson(reader2, Workspace[].class);

						try{
							JsonReader readerX = new JsonReader( new FileReader( fileName5 ) );
							attivitaFromJson = gson.fromJson(readerX, Attivita[].class);

							// STAMPA DELLE ATTIVITA' RELATIVE AL WORKSPACE SELEZIONATO.
							String tmp = null;
													
							for( int i = 0; i < workspaceFromJson.length; i++ ) {
								if( workspaceFromJson[i].getDescrizione().trim().toLowerCase().equals( workspace.trim().toLowerCase() ) ) {
									tmp = "Attivit� disponbili: \n";
									tmp += "\n";
									
									for( int j = 0; j < workspaceFromJson[i].getListaAttivita().size(); j++) {
										tmp += workspaceFromJson[i].getListaAttivita().get(j).getDescrizione() + "\n"; 
									}
									aggiungiPreferenza = 2;
									tmp += "\nScegli una attivit�";
									break;
								}
								
								if( !workspaceFromJson[i].getDescrizione().trim().toLowerCase().equals( workspace.trim().toLowerCase())) {
									tmp = "Workspace errato. Riprovare";
								}
							}
							sendMessage.setText( tmp );

						} catch( FileNotFoundException e ) {
							e.printStackTrace();
						}        						           				            			
					} catch( FileNotFoundException e ) {
						e.printStackTrace();
					}
				} catch( FileNotFoundException e ) {
					e.printStackTrace();
				}        
				break;

				// CASE DELLE ATTIVITA'
			case 2:
				attivita = update.getMessage().getText();
				System.out.println( attivita );
				try {	
					JsonReader reader = new JsonReader(new FileReader(fileName));
					preferenzeFromJson = gson.fromJson(reader, Preferenza[].class);

					for(int i=0; i<preferenzeFromJson.length; i++) {
						Preferenza preferenzaX = new Preferenza(
								preferenzeFromJson[i].getCfUtente(), 
								preferenzeFromJson[i].getDescrizioneWorkspace(),
								preferenzeFromJson[i].getDescrizioneAttivita(), 
								preferenzeFromJson[i].getData(), 
								preferenzeFromJson[i].getOrario(),
								preferenzeFromJson[i].getNoteAggiuntive());
						preferenze.add(preferenzaX);
					}
					// STAMPA DELLE DATE DISPONIBILI PER L'ATTIVITA' SELEZIONATA.
					try{
						JsonReader reader2 = new JsonReader( new FileReader( fileName5 ) );
						attivitaFromJson = gson.fromJson(reader2, Attivita[].class);

						String tmp = null; 

						
						for(int i=0; i<attivitaFromJson.length; i++) {
							if(attivitaFromJson[i].getDescrizione().trim().toLowerCase().equals(attivita.trim().toLowerCase())) {
								tmp = "Date disponbili: \n";
								tmp += "\n";

								for(int j=0; j<attivitaFromJson[i].getDateDisponibili().size(); j++) {
									tmp += attivitaFromJson[i].getDateDisponibili().get(j) + "\n";
								}
								aggiungiPreferenza = 3;
								tmp += "\nScegli una data.";
								break;
							}	    	
							
							if(!attivitaFromJson[i].getDescrizione().trim().toLowerCase().equals( attivita.trim().toLowerCase())) {
								tmp = "Attivit� errata. Riprovare.";
							}
						}

						sendMessage.setText( tmp );

					} catch( FileNotFoundException e ) {
						e.printStackTrace();
					}
				} catch( FileNotFoundException e ) {
					e.printStackTrace();
				}        
				break;
			case 3:
				data = update.getMessage().getText();

				String gg = data.substring(0, 2);
				String mm = data.substring(3, 5);
				
				try {
					JsonReader reader2 = new JsonReader( new FileReader( fileName5 ) );
					attivitaFromJson = gson.fromJson(reader2, Attivita[].class);
					LinkedList<String> listaDate = new LinkedList<String>();

					for( int i = 0; i < attivitaFromJson.length; i++ ) {						
						listaDate.addAll(attivitaFromJson[i].getDateDisponibili());						
					}		

					for( int i = 0; i < listaDate.size(); i++ ) {
						if( listaDate.get(i).substring(0, 2).equals(gg) && listaDate.get(i).substring(3, 5).equals(mm)) {
							aggiungiPreferenza = 4;
							sendMessage.setText("A che ora vuoi prenotare la visita? Formato (HH:MM)");
							break;
						} else {
							sendMessage.setText("Data inserita non valida.");
						}
					}

				} catch( FileNotFoundException e ) {
					e.printStackTrace();
				}			 
				break;		
			case 4:
				orario = update.getMessage().getText();

				int hh = Integer.parseInt(orario.substring(0, 2));
				int minutes = Integer.parseInt(orario.substring(3, 5));

				if( hh < 0 || hh > 24 || minutes < 0 || minutes > 59  ) {
					sendMessage.setText("Orario non valido");
				} else {
					aggiungiPreferenza = 5;
					sendMessage.setText("\"Vuoi aggiungere una nota all'attivit�? (Si/No):	");
				}
				break;
			case 5:
				try {	
					JsonReader reader = new JsonReader(new FileReader(fileName));
					preferenzeFromJson = gson.fromJson(reader, Preferenza[].class);
					preferenze.clear();

					for(int i=0; i<preferenzeFromJson.length; i++) {
						Preferenza preferenzaX = new Preferenza(
								preferenzeFromJson[i].getCfUtente(), 
								preferenzeFromJson[i].getDescrizioneWorkspace(),
								preferenzeFromJson[i].getDescrizioneAttivita(), 
								preferenzeFromJson[i].getData(), 
								preferenzeFromJson[i].getOrario(),
								preferenzeFromJson[i].getNoteAggiuntive());
						
						preferenze.add(preferenzaX);
					}
					noteAggiuntive = update.getMessage().getText();

					if( noteAggiuntive.trim().toLowerCase().equals("Si".trim().toLowerCase())) {
						aggiungiPreferenza = 6;
						sendMessage.setText("Inserisci nota aggiuntiva:");
					} else if(noteAggiuntive.trim().toLowerCase().equals("No".trim().toLowerCase())){
						noteAggiuntive = "/";
						sendMessage.setText( "Attivit� registrata con successo." );
						
						Preferenza preferenzaX = new Preferenza(cf, workspace, attivita, data, orario, noteAggiuntive);
						preferenze.add(preferenzaX);
						System.out.println(cf + " " + workspace + " " + attivita + " " + data + " " + orario + " " + noteAggiuntive);
						
						try{
				            FileWriter writer = new FileWriter(fileName, false);
				            aggiungiPreferenza = 0;
				            gson.toJson(preferenze, writer);
				            writer.close();
				        } catch (JsonIOException | IOException e){
				            e.printStackTrace();
				        }
					}
				} catch( FileNotFoundException e) {
					e.printStackTrace();
				}

				break;

			case 6:

				try {	
					JsonReader reader = new JsonReader(new FileReader(fileName));
					preferenzeFromJson = gson.fromJson(reader, Preferenza[].class);
					preferenze.clear();
					
					for(int i=0; i<preferenzeFromJson.length; i++) {
						Preferenza preferenzaX = new Preferenza(
								preferenzeFromJson[i].getCfUtente(), 
								preferenzeFromJson[i].getDescrizioneWorkspace(),
								preferenzeFromJson[i].getDescrizioneAttivita(), 
								preferenzeFromJson[i].getData(), 
								preferenzeFromJson[i].getOrario(),
								preferenzeFromJson[i].getNoteAggiuntive());
						preferenze.add(preferenzaX);
					}
					noteAggiuntive = update.getMessage().getText();

					Preferenza preferenzaX = new Preferenza(cf, workspace, attivita, data, orario, noteAggiuntive);
					preferenze.add(preferenzaX);
					sendMessage.setText("Attivit� registrata con successo.");

					try{
						FileWriter writer = new FileWriter(fileName, false );
						gson.toJson(preferenze, writer);
						aggiungiPreferenza = 0;
						writer.close();
					} catch (JsonIOException | IOException e){
						e.printStackTrace();
					}
				} catch( FileNotFoundException e) {
					e.printStackTrace();
				}
			}

			/**
			 * Switch per il comando "/modificaPreferenza".
			 */
			switch( modificaPreferenza ) {
			case 1:
				preferenza = update.getMessage().getText();

				try{

					JsonReader reader2 = new JsonReader( new FileReader( fileName ) );
					preferenzeFromJson = gson.fromJson(reader2, Preferenza[].class);

					for( int i = 0; i < preferenzeFromJson.length; i++ ) {
						if( preferenza.equals(preferenzeFromJson[i].getDescrizioneAttivita()) && cf.equals(preferenzeFromJson[i].getCfUtente())) {
							modificaPreferenza = 2;

							countPreferenze = i;

							sendMessage.setText("Descrizione attivit�: " + 
									preferenzeFromJson[i].getDescrizioneAttivita() +"\n"+ 
									"Data: " + preferenzeFromJson[i].getData() +"\n"+
									"Orario: " + preferenzeFromJson[i].getOrario() +"\n"+ 
									"Note aggiuntive: " + preferenzeFromJson[i].getNoteAggiuntive() + "\n\n" + 
									"Cliccare 1 per modificare la Data. \n" +
									"Cliccare 2 per modificare l' Orario. \n"+
									"Cliccare 3 per modificare le Note Aggiuntive.");

						}
					}
				} catch( FileNotFoundException e ) {
					e.printStackTrace();
				}          		
				break;
			case 2:
				preferenza = update.getMessage().getText();

				if( preferenza.equals("1") ) {
					modificaPreferenza = 3;
					sendMessage.setText("Modifica della Data. \n"
							+ "Scrivere la nuova Data nel seguente formato: DD/MM/YYYY");
				} else if( preferenza.equals("2")) {
					modificaPreferenza = 4;
					sendMessage.setText("Modifica dell' Orario. \n" + 
							"Scrivere il nuovo Orario nel seguente formato: HH:MM");
				} else if( preferenza.equals("3")) {
					modificaPreferenza = 5;
					sendMessage.setText("Modifica delle Note Aggiuntive.");
				}
				break;
			case 3:
				preferenza = update.getMessage().getText();

				try {

					int gg = Integer.parseInt(preferenza.substring(0, 2));
					int mm = Integer.parseInt(preferenza.substring(3, 5));
					int yyyy = Integer.parseInt(preferenza.substring(6, 10));              		

					if( gg < 1 || gg > 31 || mm < 1 || mm > 12 || yyyy < 2021 ) {
						sendMessage.setText("Data non valida");
					}

					try{

						JsonReader reader2 = new JsonReader( new FileReader( fileName ) );
						preferenzeFromJson = gson.fromJson(reader2, Preferenza[].class);

						JsonReader reader = new JsonReader(new FileReader(fileName));
						preferenzeFromJson = gson.fromJson(reader, Preferenza[].class);
						preferenze.clear();

						for(int i=0; i<preferenzeFromJson.length; i++) {
							Preferenza preferenzaX = new Preferenza(
									preferenzeFromJson[i].getCfUtente(), 
									preferenzeFromJson[i].getDescrizioneWorkspace(),
									preferenzeFromJson[i].getDescrizioneAttivita(), 
									preferenzeFromJson[i].getData(), 
									preferenzeFromJson[i].getOrario(),
									preferenzeFromJson[i].getNoteAggiuntive());
							preferenze.add(preferenzaX);
						}

						preferenze.get(countPreferenze).setData( preferenza );
						try{
							FileWriter writer = new FileWriter(fileName, false);
							modificaPreferenza = 0;
							sendMessage.setText("Data modificata. Operazione avvenuta con successo.");
							gson.toJson(preferenze, writer);
							writer.close();
						} catch (JsonIOException | IOException e){
							e.printStackTrace();
						}

					} catch( FileNotFoundException e ) {
						e.printStackTrace();
					}          		
				} catch( Exception e ) {
					sendMessage.setText( "Errore formato data." );
				}
				break;
			case 4:

				preferenza = update.getMessage().getText();

				try {         			         
					int hh = Integer.parseInt(preferenza.substring(0, 2));
					int minutes = Integer.parseInt(preferenza.substring(3, 5));

					if( hh < 0 || hh > 24 || minutes < 0 || minutes > 59 ) {
						sendMessage.setText("Data non valida");
					} 

				} catch( Exception e ) {
					sendMessage.setText( "Errore formato data." );
				}

				try{

					JsonReader reader2 = new JsonReader( new FileReader( fileName ) );
					preferenzeFromJson = gson.fromJson(reader2, Preferenza[].class);

					JsonReader reader = new JsonReader(new FileReader(fileName));
					preferenzeFromJson = gson.fromJson(reader, Preferenza[].class);
					preferenze.clear();

					for(int i=0; i<preferenzeFromJson.length; i++) {
						Preferenza preferenzaX = new Preferenza(
								preferenzeFromJson[i].getCfUtente(), 
								preferenzeFromJson[i].getDescrizioneWorkspace(),
								preferenzeFromJson[i].getDescrizioneAttivita(), 
								preferenzeFromJson[i].getData(), 
								preferenzeFromJson[i].getOrario(),
								preferenzeFromJson[i].getNoteAggiuntive());
						preferenze.add(preferenzaX);
					}

					preferenze.get(countPreferenze).setOrario( preferenza );
					try{
						FileWriter writer = new FileWriter(fileName, false);
						modificaPreferenza = 0;
						sendMessage.setText("Orario modificato. Operazione avvenuta con successo.");
						gson.toJson(preferenze, writer);
						writer.close();
					} catch (JsonIOException | IOException e){
						e.printStackTrace();
					}

				} catch( FileNotFoundException e ) {
					e.printStackTrace();
				} catch( Exception e ) {
					sendMessage.setText( "Errore formato data." );
				}

				break;
			case 5:
				preferenza = update.getMessage().getText();

				try{

					JsonReader reader2 = new JsonReader( new FileReader( fileName ) );
					preferenzeFromJson = gson.fromJson(reader2, Preferenza[].class);

					JsonReader reader = new JsonReader(new FileReader(fileName));
					preferenzeFromJson = gson.fromJson(reader, Preferenza[].class);
					preferenze.clear();

					for(int i=0; i<preferenzeFromJson.length; i++) {
						Preferenza preferenzaX = new Preferenza(
								preferenzeFromJson[i].getCfUtente(), 
								preferenzeFromJson[i].getDescrizioneWorkspace(),
								preferenzeFromJson[i].getDescrizioneAttivita(), 
								preferenzeFromJson[i].getData(), 
								preferenzeFromJson[i].getOrario(),
								preferenzeFromJson[i].getNoteAggiuntive());
						preferenze.add(preferenzaX);
					}

					preferenze.get(countPreferenze).setNoteAggiuntive( preferenza );
					try{
						FileWriter writer = new FileWriter(fileName, false);
						modificaPreferenza = 0;
						sendMessage.setText("Note Aggiuntive modificate. Operazione avvenuta con successo.");
						gson.toJson(preferenze, writer);
						writer.close();
					} catch (JsonIOException | IOException e){
						e.printStackTrace();
					}

				} catch( FileNotFoundException e ) {
					e.printStackTrace();
				}
				break;
			}

			switch( msg ) {
			case "/login":
				login = 1;
				sendMessage.setText( "Inserisci codice fiscale per effettuare il login." );

				break;   	
			case "/help":
				if( login == 4 ) {
					sendMessage.setText("Ecco a te la lista dei comandi: \n "+
							" /aggiungipreferenze - Attraverso questo comando si ha la possiblit� di aggiungere una preferenze all'utente. \n"+
							" /visualizzapreferenze: Con questo comando si ha la possibilit� di visualizzare tutte le preferenze dell'utente. \n"+
							" /modificapreferenze - Attraverso questo comando si ha la possiblit� di modificare una preferenza dell'utente. "); 
				} else {
					sendMessage.setText("Non sei loggato al sistema. Effettua il login.");
					System.out.println("Non sei loggato al sistema. Effettua il login.");
				}

				break;

			case "/aggiungipreferenza":
				if ( login == 4 ) {
					aggiungiPreferenza = 1;

					try{
						JsonReader reader2 = new JsonReader( new FileReader( fileName3 ) );
						workspaceFromJson = gson.fromJson(reader2, Workspace[].class);

						String tmp = "Elenco Workspace: \n";
						tmp += "\n";
						for( int i = 0; i < workspaceFromJson.length; i++ ) {
							tmp += workspaceFromJson[i].getDescrizione() + "\n";
						}
						tmp += "\nScegli un Workspace per cui esprimere una preferenza.";
						sendMessage.setText( tmp );      			
					}
					catch( FileNotFoundException e ) {
						e.printStackTrace();
					}              		
				} else {
					sendMessage.setText("Non sei loggato al sistema. Effettua il login.");
					System.out.println("Non sei loggato al sistema. Effettua il login.");
				}
				break;

			case "/modificapreferenza":
				if( login == 4 ) {

					modificaPreferenza = 1;

					try {	
						JsonReader reader = new JsonReader( new FileReader( fileName ));
						preferenzeFromJson = gson.fromJson(reader, Preferenza[].class);

						String tmp = "Seleziona attivit� di cui modificare le preferenze: \n";
						tmp += "\n";
						for( int i = 0; i < preferenzeFromJson.length; i++ ) {
							if( preferenzeFromJson[i].getCfUtente().equals( cf ) ) {
								tmp += preferenzeFromJson[i].getDescrizioneAttivita() + "\n";
							}
						}
						System.out.println( tmp );
						sendMessage.setText( tmp );

					} catch( FileNotFoundException e ) {
						e.printStackTrace();
					}                        	
				} else {
					sendMessage.setText("Non sei loggato al sistema. Effettua il login.");
					System.out.println("Non sei loggato al sistema. Effettua il login.");
				}
				break;
			case "/visualizzapreferenze":
				if( login == 4 ) {		
					String tmp = "";					
					try {
						JsonReader reader2 = new JsonReader( new FileReader( fileName ) );
						preferenzeFromJson = gson.fromJson(reader2, Preferenza[].class);
						LinkedList<String> visualizzaPreferenze = new LinkedList<>();
						
						for( int i = 0; i < preferenzeFromJson.length; i++ ) {						
							visualizzaPreferenze.add( 
									preferenzeFromJson[i].getCfUtente() + 
									preferenzeFromJson[i].getDescrizioneWorkspace() +
									preferenzeFromJson[i].getDescrizioneAttivita() +
									preferenzeFromJson[i].getData() +
									preferenzeFromJson[i].getOrario() +
									preferenzeFromJson[i].getNoteAggiuntive() );
						}		
						
						for( int i = 0; i < visualizzaPreferenze.size(); i++ ) {
							if( visualizzaPreferenze.get(i).substring(0,16).equals(cf) ) {
									tmp += 
									"Workspace: " + preferenzeFromJson[i].getDescrizioneWorkspace() + "\n" + 
									"Attivit�: " + preferenzeFromJson[i].getDescrizioneAttivita() + "\n" +
									"Data: " + preferenzeFromJson[i].getData() + "\n" + 
									"Orario: " + preferenzeFromJson[i].getOrario() + "\n" +
									"Note Aggiuntive: " +preferenzeFromJson[i].getNoteAggiuntive() +"\n\n";
							}
						}
						sendMessage.setText( tmp );
					}catch( FileNotFoundException e) {
						e.printStackTrace();
					}
				}

				break;
			case "/exit":
				if( login == 4 ) {
					sendMessage.setText( "Logout effettuato con successo." );
					login = 0;
				} else {
					sendMessage.setText("Non sei loggato al sistema. Effettua il login.");
				}
				break;
			}

			sendMessage.setChatId( chatId );

			try {
				execute( sendMessage );
			} catch( TelegramApiException e ) {
				System.err.println( e.getMessage() );
			}
		}
	}

	@Override
	public String getBotToken(){
		// TODO Auto-generated method stub
		return "1840839233:AAGNZYACZfRgPAqEFG7RgD6Tb64d2Hzygt8";
	}
}

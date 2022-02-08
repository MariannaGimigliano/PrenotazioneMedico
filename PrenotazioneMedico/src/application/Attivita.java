package application;

import java.util.LinkedList;

public class Attivita {

	private int id;
	private String descrizione;
	private int durata; // durata in minuti
	private String giornoDisponibile;
	private LinkedList<String> dateDisponibili;
    
    public Attivita() {
    	//vuoto per Gson lib.
    }

    public Attivita(int id, String descrizione, int durata, String giornoDisponibile, LinkedList<String> dateDisponibili){
        this.id = id;
        this.descrizione = descrizione;
        this.durata = durata;
        this.giornoDisponibile = giornoDisponibile;
        this.dateDisponibili = dateDisponibili;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getDurata() {
        return durata;
    }
    public void setDurata(int durata) {
        this.durata = durata;
    }

    public String getGiornoDisponibile() {
        return giornoDisponibile;
    }
    public void setGiornoDisponibile(String giornoDisponibile) {
        this.giornoDisponibile = giornoDisponibile;
    }
    
    public LinkedList<String> getDateDisponibili(){
    	return dateDisponibili;
    }
    public void setDateDisponibili(LinkedList<String> dateDisponibili) {
    	this.dateDisponibili = dateDisponibili;
    }  
}

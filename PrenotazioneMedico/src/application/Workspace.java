package application;

import java.util.LinkedList;

public class Workspace {
	
	private int id;
	private String descrizione;
	private LinkedList<Attivita> listaAttivita;
    
    public Workspace() {
    	//vuoto per Gson lib.
    }
    
    public Workspace(int id, String descrizione, LinkedList<Attivita> listaAttivita){
        this.id = id;
        this.descrizione = descrizione;
        this.listaAttivita = listaAttivita;
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

	public LinkedList<Attivita> getListaAttivita() {
		return listaAttivita;
	}
	public void setListaAttivita(LinkedList<Attivita> listaAttivita) {
		this.listaAttivita = listaAttivita;
	}   
}

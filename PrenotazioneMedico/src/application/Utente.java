package application;

public class Utente {

	private String nome;
	private String cognome;
	private String sesso;
	private String codiceFiscale;
	private String dataDiNascita;

    public Utente(){
        //vuoto per Gson lib.
    }

    public Utente(String nome, String cognome, String sesso, String codiceFiscale, String dataDiNascita){
        this.nome = nome;
        this.cognome = cognome;
        this.sesso = sesso;
        this.codiceFiscale = codiceFiscale;
        this.dataDiNascita = dataDiNascita;
    }

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getDataDiNascita() {
		return dataDiNascita;
	}
	public void setDataDiNascita(String dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}	   
}

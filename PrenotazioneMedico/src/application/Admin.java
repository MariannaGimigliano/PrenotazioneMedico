package application;

public class Admin {

    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String password;
	
    public Admin(String nome, String cognome, String codiceFiscale, String password){
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.password = password;
    }

    public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getCodiceFiscale() {
        return codiceFiscale;
    }

	public String getPassword() {
		return password;
	}
}

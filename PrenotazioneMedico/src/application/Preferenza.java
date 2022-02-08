package application;

public class Preferenza {
	
	private String cfUtente;
	private String descrizioneWorkspace;
	private String descrizioneAttivita;
	private String data;
	private String orario;
	private String noteAggiuntive;
	
	public Preferenza() {
		//vuoto per Gson lib.
	}
	
	public Preferenza(String cfUtente, String descrizioneWorkspace, String descrizioneAttivita, String data, String orario, String noteAggiuntive) {
		this.data = data;
		this.orario = orario;
		this.noteAggiuntive = noteAggiuntive;
		this.cfUtente = cfUtente;
		this.descrizioneWorkspace = descrizioneWorkspace;
		this.descrizioneAttivita = descrizioneAttivita;
	}
	
	public String getCfUtente() {
		return cfUtente;
	}

	public void setCfUtente(String cfUtente) {
		this.cfUtente = cfUtente;
	}

	public String getDescrizioneWorkspace() {
		return descrizioneWorkspace;
	}
	public void setDescrizioneWorkspace(String descrizioneWorkspace) {
		this.descrizioneWorkspace = descrizioneWorkspace;
	}

	public String getDescrizioneAttivita() {
		return descrizioneAttivita;
	}
	public void setDescrizioneAttivita(String descrizioneAttivita) {
		this.descrizioneAttivita = descrizioneAttivita;
	}

	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	public String getOrario() {
		return orario;
	}
	public void setOrario(String orario) {
		this.orario = orario;
	}
	
	public String getNoteAggiuntive() {
		return noteAggiuntive;
	}
	public void setNoteAggiuntive(String noteAggiuntive) {
		this.noteAggiuntive = noteAggiuntive;
	}
}

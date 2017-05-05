package utility;

public class Risultati {

	private int totale_metodi;
	private int chiamate_dirette;
	private int chiamate_indirette;
	private int file_analizzati;
	private int totale_metodi_polimorfici;
	private int metodi_indipendenti;
	private int metodi_dipendenti;
	
	
	public Risultati(){
		this.totale_metodi=0;
		this.chiamate_dirette=0;
		this.chiamate_indirette=0;
		this.totale_metodi_polimorfici=0;
	}
	
	public void addMetodo(){
		this.totale_metodi++;
	}

	public void addChiamataDiretta(){
		this.chiamate_dirette++;
	}
	
	public void addChiamataIndiretta(){
		this.chiamate_indirette++;
	}
	
	public void addChiamateDirette(int c){
		this.chiamate_dirette+=c;
	}
	
	public void addChiamateIndirette(int c){
		this.chiamate_indirette+=c;
	}

	public int getTotale_metodi() {
		return totale_metodi;
	}


	public void setTotale_metodi(int totale_metodi) {
		this.totale_metodi = totale_metodi;
	}


	public int getChiamate_dirette() {
		return chiamate_dirette;
	}


	public void setChiamate_dirette(int chiamate_dirette) {
		this.chiamate_dirette = chiamate_dirette;
	}


	public int getChiamate_indirette() {
		return chiamate_indirette;
	}


	public void setChiamate_indirette(int chiamate_indirette) {
		this.chiamate_indirette = chiamate_indirette;
	}


	public int getFile_analizzati() {
		return file_analizzati;
	}

	public void setFile_analizzati(int file_analizzati) {
		this.file_analizzati = file_analizzati;
	}

	public int getTotale_metodi_polimorfici() {
		return totale_metodi_polimorfici;
	}

	public void setTotale_metodi_polimorfici(int totale_metodi_polimorfici) {
		this.totale_metodi_polimorfici = totale_metodi_polimorfici;
	}
	
	public void addMetodi_polimorfici(int metodi) {
		this.totale_metodi_polimorfici += metodi;
	}

	public int getMetodi_indipendenti() {
		return metodi_indipendenti;
	}

	public void setMetodi_indipendenti(int metodi_indipendenti) {
		this.metodi_indipendenti = metodi_indipendenti;
	}

	public int getMetodi_dipendenti() {
		return metodi_dipendenti;
	}

	public void setMetodi_dipendenti(int metodi_dipendenti) {
		this.metodi_dipendenti = metodi_dipendenti;
	}

	@Override
	public String toString() {
		return "Risultati [totale_metodi=" + totale_metodi + ", chiamate_dirette=" + chiamate_dirette
				+ ", chiamate_indirette=" + chiamate_indirette + ", file_analizzati=" + file_analizzati
				+ ", totale_metodi_polimorfici=" + totale_metodi_polimorfici + "]";
	}



	
	
}

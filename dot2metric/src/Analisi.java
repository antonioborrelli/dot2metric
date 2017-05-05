import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import costanti.Costanti;
import treeStructure.Tree;
import utility.ObjMetodo;

/**
 * FASE DI ANALISI: vengono analizzati i dati raccolti nella fase precedente, 
 * generando un albero delle chiamate per ogni metodo salvandoli in una lista di alberi 
 * e stampandoli poi in un file di output (trees.metric).
 * vengono analizzati nel completto tutti i metodi evidenziando quelli indipendenti, quelli dipendenti.
 * Dove per indipendenti sono i metodi che non vengono richiamati da altri metodi.
 * Vengono conteggiati i file analizzati, il numero di chiamte dirette e indirette,
 * il numeto totale di metodi dipendenti e indipendenti,
 * e il numero totale di metodi polimorfici. Infine viene salvato in un file (output.metric) l'albero genreale di tutti le chiamate dei metodi privo di ridondanze
 */
public class Analisi {
	private long execution_time;
	private String path_output;
	private HashMap<String,ArrayList<ObjMetodo>> mappa;
	private int totale_chiamate_dirette,totale_chiamate_indirette,totale_metodi_polimorfici,totale_metodi_indipendenti,totale_metodi_dipendenti;
	private File file_output, file_trees;

	
	public Analisi(String path_output, HashMap<String,ArrayList<ObjMetodo>> mappa){
		this.execution_time=0;
		this.path_output=path_output;
		this.mappa=mappa;
		this.totale_chiamate_dirette=0;
		this.totale_chiamate_indirette=0;
		this.totale_metodi_polimorfici=0;
		this.totale_metodi_indipendenti=0;
		this.totale_metodi_dipendenti=0;
		this.file_output= new File(this.path_output+"/"+Costanti.FILE_OUTPUT);;
		this.file_trees= new File(this.path_output+"/"+Costanti.FILE_OUTPUT_TREES);
	}
	
	public void start() throws FileNotFoundException{
		long start_ANALISI = System.currentTimeMillis();
		PrintStream stream_output= new PrintStream(this.file_output);
		PrintStream stream_trees= new PrintStream(this.file_trees);
		ArrayList<Tree<String,String>> alberi = new ArrayList<>();
		for(String key : this.mappa.keySet()){
			
			ArrayList<ObjMetodo> listaMetodi = this.mappa.get(key);
			for(ObjMetodo metodo : listaMetodi)
			{
				this.totale_chiamate_dirette += metodo.getChiamateDirette();
				this.totale_chiamate_indirette += metodo.getChiamateIndirette();
				alberi.add(metodo.getTree());
			}
			if(listaMetodi.size() > 1)
				this.totale_metodi_polimorfici++;
		}
		//Determina gli alberi indipendenti
		for(int i=0; i< alberi.size(); i++){
			Tree<String,String> albero = alberi.get(i);
			for(int j=0; j< alberi.size(); j++)
				if(i!=j && alberi.get(j).contains(albero.getRoot())){
					albero.setIndipendente(false);
				}
			
			stream_trees.println("***************************" + albero.getRoot().key() + "***************************");
			albero.printPreOrder(stream_trees);
			stream_trees.println();
			
			
			if(albero.isIndipendente()){
				totale_metodi_indipendenti++;
				albero.printPreOrder(stream_output);
			}else
				totale_metodi_dipendenti++;
			
		}
		stream_output.close();
		stream_trees.close();
		long end_ANALISI = System.currentTimeMillis();
		this.execution_time=end_ANALISI-start_ANALISI;
	}

	public long getExecution_time() {
		return execution_time;
	}

	public void setExecution_time(long execution_time) {
		this.execution_time = execution_time;
	}

	public String getPath_output() {
		return path_output;
	}

	public void setPath_output(String path_output) {
		this.path_output = path_output;
	}

	public HashMap<String, ArrayList<ObjMetodo>> getMappa() {
		return mappa;
	}

	public void setMappa(HashMap<String, ArrayList<ObjMetodo>> mappa) {
		this.mappa = mappa;
	}

	public int getTotale_chiamate_dirette() {
		return totale_chiamate_dirette;
	}

	public void setTotale_chiamate_dirette(int totale_chiamate_dirette) {
		this.totale_chiamate_dirette = totale_chiamate_dirette;
	}

	public int getTotale_chiamate_indirette() {
		return totale_chiamate_indirette;
	}

	public void setTotale_chiamate_indirette(int totale_chiamate_indirette) {
		this.totale_chiamate_indirette = totale_chiamate_indirette;
	}

	public int getTotale_metodi_polimorfici() {
		return totale_metodi_polimorfici;
	}

	public void setTotale_metodi_polimorfici(int totale_metodi_polimorfici) {
		this.totale_metodi_polimorfici = totale_metodi_polimorfici;
	}

	public int getTotale_metodi_indipendenti() {
		return totale_metodi_indipendenti;
	}

	public void setTotale_metodi_indipendenti(int totale_metodi_indipendenti) {
		this.totale_metodi_indipendenti = totale_metodi_indipendenti;
	}

	public int getTotale_metodi_dipendenti() {
		return totale_metodi_dipendenti;
	}

	public void setTotale_metodi_dipendenti(int totale_metodi_dipendenti) {
		this.totale_metodi_dipendenti = totale_metodi_dipendenti;
	}

	public File getFile_output() {
		return file_output;
	}

	public void setFile_output(File file_output) {
		this.file_output = file_output;
	}

	public File getFile_trees() {
		return file_trees;
	}

	public void setFile_trees(File file_trees) {
		this.file_trees = file_trees;
	}
	
	

}

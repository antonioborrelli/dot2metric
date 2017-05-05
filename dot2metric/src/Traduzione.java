import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import costanti.Costanti;
import grammatica.DotGrammar;
import grammatica.ParseException;
import utility.ObjMetodo;

/**
 * FASE DI TRADUZIONE: scorre tutti i file generati nella fase di preprocessing e 
 * li analizza singolarmente mediante mediante l'uso della grammadita sviluppata 
 * al fine di ottenere un oggetto ObjMetodo per ogni file. Tali oggetti vengono salvati in una hashmap 
 * che sarà successivamente analizzata nella fase di analisi.
 * Viene, inoltre, generato un file contenente per ogni metodo la lista di chiamate dirette e indirette ad esso relative
 * 
 */
public class Traduzione {
	private boolean isError;
	private long execution_time;
	private int totale_metodi;
	private File directory_input_modificati;
	private File file_output;
	private File file_chiamate;
	private File file_coppie;
	private String path_output;
	private HashMap<String,ArrayList<ObjMetodo>> mappa;
	
	public Traduzione(String path_output) throws FileNotFoundException{
		this.isError=false;
		this.execution_time=0;
		this.totale_metodi=0;
		this.path_output=path_output;
		this.directory_input_modificati = new File(this.path_output+"/"+Costanti.DIRECTORY_INPUT_MODIFICATI);
		this.file_output = new File(this.path_output+"/"+Costanti.FILE_OUTPUT);
		this.file_chiamate = new File(this.path_output+"/"+Costanti.FILE_OUTPUT_CHIAMATE);
		this.file_coppie = new File(this.path_output+"/"+Costanti.FILE_OUTPUT_COPPIE);
		this.mappa = new HashMap<String, ArrayList<ObjMetodo>>();
	}
	
	public boolean start() throws ParseException, IOException{
		long start_TRADUZIONE = System.currentTimeMillis();
		PrintStream stream_output= new PrintStream(this.file_output);
		PrintStream stream_chiamate= new PrintStream(this.file_chiamate);
		PrintStream stream_coppie= new PrintStream(this.file_coppie);
		if(this.directory_input_modificati.exists()){
			if(this.directory_input_modificati.isDirectory()){
				File[] files = this.directory_input_modificati.listFiles();
				for (File file : files) {
					FileInputStream fis = new FileInputStream(file);
					DotGrammar dot = new DotGrammar(fis);
					ObjMetodo metodo = dot.start();
					
					metodo.setNomeEsteso(metodo.getSimboli().getNome("Node1"));
					this.totale_metodi++;
					if(this.mappa.containsKey(metodo.getNomeEsteso())){
						ArrayList<ObjMetodo> lista = mappa.get(metodo.getNomeEsteso());
						lista.add(metodo);
						this.mappa.put(metodo.getNomeEsteso(), lista);
					}else{
						ArrayList<ObjMetodo> lista =new ArrayList<ObjMetodo>();
						lista.add(metodo);
						this.mappa.put(metodo.getNomeEsteso(),lista);
					}
					
					stream_chiamate.println("***************************" + metodo.getNomeEsteso() + "***************************");
					metodo.stampaMappaChiamate(stream_chiamate);
					stream_chiamate.println();
					metodo.printChiamateDirette(stream_coppie);
					fis.close();
				}
			}else{
				this.isError=true;
				System.out.println("ERRORE 04: Il percorso indicato non è relativo ad una directory");
			}
		}else{
			this.isError=true;
			System.out.println("ERRORE 03: Directory inesistente");
		}
		stream_output.close();
		stream_chiamate.close();
		stream_coppie.close();
		long end_TRADUZIONE = System.currentTimeMillis();
		this.execution_time=end_TRADUZIONE-start_TRADUZIONE;
		return this.isError;
	}
	


	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

	public long getExecution_time() {
		return execution_time;
	}

	public void setExecution_time(long execution_time) {
		this.execution_time = execution_time;
	}

	public int getTotale_metodi() {
		return totale_metodi;
	}

	public void setTotale_metodi(int totale_metodi) {
		this.totale_metodi = totale_metodi;
	}

	public File getDirectory_input_modificati() {
		return directory_input_modificati;
	}

	public void setDirectory_input_modificati(File directory_input_modificati) {
		this.directory_input_modificati = directory_input_modificati;
	}

	public File getFile_output() {
		return file_output;
	}

	public void setFile_output(File file_output) {
		this.file_output = file_output;
	}

	public File getFile_chiamate() {
		return file_chiamate;
	}

	public void setFile_chiamate(File file_chiamate) {
		this.file_chiamate = file_chiamate;
	}

	public File getFile_coppie() {
		return file_coppie;
	}

	public void setFile_coppie(File file_coppie) {
		this.file_coppie = file_coppie;
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


	
	
}

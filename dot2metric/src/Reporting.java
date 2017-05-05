import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import costanti.Costanti;

/**
 * FASE DI REPORTING: vengono salvati in un file di report (report.metric)
 * i risultati dell'analisi effettuata.
 */
public class Reporting {
	private long execution_time;
	private String path_output;
	private Preprocessing preprocessing;
	private Traduzione traduzione;
	private Analisi analisi;
	File file_report;
	
	
	public Reporting(String path_output,Preprocessing preprocessing, Traduzione traduzione, Analisi analisi){
		this.execution_time=0;
		this.path_output=path_output;
		this.file_report= new File(this.path_output+"/"+Costanti.FILE_OUTPUT_REPORT);
		this.preprocessing=preprocessing;
		this.traduzione=traduzione;
		this.analisi=analisi;
	}
	
	public void start() throws FileNotFoundException{
		long start_REPORTING = System.currentTimeMillis();
		PrintStream stream_report= new PrintStream(this.file_report);
		stream_report.println("Report");
		stream_report.println("\tfile analizzati: " + preprocessing.getFile_analizzati());
		stream_report.println("\ttotale metodi: " + traduzione.getTotale_metodi());
		stream_report.println("\ttotale chiamate dirette: " + analisi.getTotale_chiamate_dirette());
		stream_report.println("\ttotale chiamate indirette: " +  analisi.getTotale_chiamate_indirette());
		stream_report.println("\ttotale metodi polimorfici: " + analisi.getTotale_metodi_polimorfici());
		stream_report.println("\ttotale metodi indipendenti: " + analisi.getTotale_metodi_indipendenti());
		stream_report.println("\ttotale metodi dipendenti: " + analisi.getTotale_metodi_dipendenti());
		stream_report.close();
		long end_REPORTING = System.currentTimeMillis();
		execution_time=end_REPORTING-start_REPORTING;
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

	public Preprocessing getPreprocessing() {
		return preprocessing;
	}

	public void setPreprocessing(Preprocessing preprocessing) {
		this.preprocessing = preprocessing;
	}

	public Traduzione getTraduzione() {
		return traduzione;
	}

	public void setTraduzione(Traduzione traduzione) {
		this.traduzione = traduzione;
	}

	public Analisi getAnalisi() {
		return analisi;
	}

	public void setAnalisi(Analisi analisi) {
		this.analisi = analisi;
	}

	public File getFile_report() {
		return file_report;
	}

	public void setFile_report(File file_report) {
		this.file_report = file_report;
	}
	
	
}



import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.omg.Messaging.SyncScopeHelper;

import costanti.Costanti;
import grammatica.DotGrammar;
import grammatica.ParseException;
import treeStructure.Tree;
import utility.ObjMetodo;

public class dot2metric {

	public static void main(String[] args) throws IOException, ParseException {
		int totale_metodi=0;
		int totale_chiamate_dirette=0;
		int totale_chiamate_indirette=0;
		int totale_metodi_polimorfici=0;
		int totale_metodi_indipendenti=0;
		int totale_metodi_dipendenti=0;
		int file_analizzati=0; //TOTALE FILE ANALIZZATI
		
		boolean notError=true;
		File directory_input_modificati = new File(Costanti.PATH_DIRECTORY_OUTPUT+"/"+Costanti.DIRECTORY_INPUT_MODIFICATI);
		File file_output = new File(Costanti.PATH_DIRECTORY_OUTPUT+"/"+Costanti.FILE_OUTPUT);
		File file_chiamate = new File(Costanti.PATH_DIRECTORY_OUTPUT+"/"+Costanti.FILE_OUTPUT_CHIAMATE);
		File file_trees = new File(Costanti.PATH_DIRECTORY_OUTPUT+"/"+Costanti.FILE_OUTPUT_TREES);
		File file_report = new File(Costanti.PATH_DIRECTORY_OUTPUT+"/"+Costanti.FILE_OUTPUT_REPORT);
		HashMap<String,ArrayList<ObjMetodo>> mappa = new HashMap<>();
		PrintStream stream_output= new PrintStream(file_output);
		PrintStream stream_chiamate= new PrintStream(file_chiamate);
		PrintStream stream_trees= new PrintStream(file_trees);
		PrintStream stream_report= new PrintStream(file_report);
		
		System.out.println("************************* DOT2METRICS *************************");
		long start = System.currentTimeMillis();
		/**
		 * FASE DI PREPROCESSING: scorre tutti i file della cartella di input cercando 
		 * solo quelli che iniziano per class e terminano per .dot;
		 * rimuove gli spazi bianchi e elimina il oggni occorrenza del carattere "\l" dal singolo file,
		 * salvando, infine, i nuovi file generati nella cartella input_modificati
		 */
		long start_PREPROCESSING = System.currentTimeMillis();
		System.out.println("--> FASE DI PREPROCESSING AVVIATA");
		File directory_input = new File(Costanti.PATH_DIRECTORY_INPUT);
		if(directory_input.exists()){
			if(directory_input.isDirectory()){
				File[] files = directory_input.listFiles();
				
				deleteDirectory(Costanti.PATH_DIRECTORY_OUTPUT+"/"+Costanti.DIRECTORY_INPUT_MODIFICATI);
				new File(Costanti.PATH_DIRECTORY_OUTPUT+"/"+Costanti.DIRECTORY_INPUT_MODIFICATI).mkdirs();
				for (File file : files) {
					String nome_file = file.getName();
					if(nome_file.endsWith(".dot") && nome_file.startsWith("class")){
						
						File output = new File(Costanti.PATH_DIRECTORY_OUTPUT+"/"+Costanti.DIRECTORY_INPUT_MODIFICATI+"/"+nome_file);
						formattingLine(file,output);
						file_analizzati++;
						
					}
				}
				long end_PREPROCESSING = System.currentTimeMillis();
				System.out.println("--> FASE DI PREPROCESSING TERMINATA CON SUCCESSO [ " + (end_PREPROCESSING-start_PREPROCESSING)+ " millisecondi ]");
			}else{
				notError=false;
				System.out.println("ERRORE 02: Il percorso indicato non è relativo ad una directory");
				System.out.println("--> FASE DI PREPROCESSING NON TERMINATA CORRETTAMENTE");
			}
		}else{
			notError=false;
			System.out.println("ERRORE 01: Directory inesistente");
			System.out.println("--> FASE DI PREPROCESSING NON TERMINATA CORRETTAMENTE");
		}
		
		
		if(notError){
			long start_TRADUZIONE = System.currentTimeMillis();
			/**
			 * FASE DI TRADUZIONE: scorre tutti i file generati nella fase di preprocessing e 
			 * li analizza singolarmente mediante mediante l'uso della grammadita sviluppata 
			 * al fine di ottenere un oggetto ObjMetodo per ogni file. Tali oggetti vengono salvati in una hashmap 
			 * che sarà successivamente analizzata nella fase di analisi.
			 * Viene, inoltre, generato un file contenente per ogni metodo la lista di chiamate dirette e indirette ad esso relative
			 * 
			 */
			System.out.println("--> FASE DI TRADUZIONE AVVIATA");

			if(directory_input_modificati.exists()){
				if(directory_input_modificati.isDirectory()){
					File[] files = directory_input_modificati.listFiles();
					for (File file : files) {
						FileInputStream fis = new FileInputStream(file);
						DotGrammar dot = new DotGrammar(fis);
						ObjMetodo metodo = dot.start();
						
						metodo.setNomeEsteso(metodo.getSimboli().getNome("Node1"));
						totale_metodi++;
						if(mappa.containsKey(metodo.getNomeEsteso())){
							ArrayList<ObjMetodo> lista = mappa.get(metodo.getNomeEsteso());
							lista.add(metodo);
							mappa.put(metodo.getNomeEsteso(), lista);
						}else{
							ArrayList<ObjMetodo> lista =new ArrayList<ObjMetodo>();
							lista.add(metodo);
							mappa.put(metodo.getNomeEsteso(),lista);
						}
						
						stream_chiamate.println("***************************" + metodo.getNomeEsteso() + "***************************");
						metodo.stampaMappaChiamate(stream_chiamate);
						stream_chiamate.println();
						fis.close();
					}
					long end_TRADUZIONE = System.currentTimeMillis();
					System.out.println("--> FASE DI TRADUZIONE TERMINATA CON SUCCESSO [ "+(end_TRADUZIONE-start_TRADUZIONE)+" millisecondi ]");
				}else{
					notError=false;
					System.out.println("ERRORE 04: Il percorso indicato non è relativo ad una directory");
					System.out.println("--> FASE DI TRADUZIONE NON TERMINATA CORRETTAMENTE");
				}
			}else{
				notError=false;
				System.out.println("ERRORE 03: Directory inesistente");
				System.out.println("--> FASE DI TRADUZIONE NON TERMINATA CORRETTAMENTE");
			}
		}
		

		if(notError){
			long start_ANALISI = System.currentTimeMillis();
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
			System.out.println("--> FASE DI ANALISI AVVIATA");
			ArrayList<Tree<String,String>> alberi = new ArrayList<>();
			for(String key : mappa.keySet()){
				
				ArrayList<ObjMetodo> listaMetodi = mappa.get(key);
				for(ObjMetodo metodo : listaMetodi)
				{
					totale_chiamate_dirette += metodo.getChiamateDirette();
					totale_chiamate_indirette += metodo.getChiamateIndirette();
					alberi.add(metodo.getTree());
				}
				if(listaMetodi.size() > 1)
					totale_metodi_polimorfici++;
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
			long end_ANALISI = System.currentTimeMillis();
			System.out.println("--> FASE DI ANALISI TERMINATA [" + (end_ANALISI-start_ANALISI) + " millisecondi ]");
		}
		
		if(notError){
			long start_REPORTING = System.currentTimeMillis();
			/**
			 * FASE DI REPORTING: vengono salvati in un file di report (report.metric)
			 * i risultati dell'analisi effettuata.
			 */
			System.out.println("--> FASE DI REPORTING AVVIATA");
			stream_report.println("Report");
			stream_report.println("\tfile analizzati: " + file_analizzati);
			stream_report.println("\ttotale metodi: " + totale_metodi);
			stream_report.println("\ttotale chiamate dirette: " + totale_chiamate_dirette);
			stream_report.println("\ttotale chiamate indirette: " + totale_chiamate_indirette);
			stream_report.println("\ttotale metodi polimorfici: " + totale_metodi_polimorfici);
			stream_report.println("\ttotale metodi indipendenti: " + totale_metodi_indipendenti);
			stream_report.println("\ttotale metodi dipendenti: " + totale_metodi_dipendenti);
			
			
			long end_REPORTING = System.currentTimeMillis();
			System.out.println("--> FASE DI REPORTING TERMINATA [ " + (end_REPORTING-start_REPORTING) + " millisecondi ]");
		}
		long end = System.currentTimeMillis();
		System.out.println(" TEMPO TOTALE : [ " + (end-start) + " millisecondi ]");
		
		
		stream_output.close();
		stream_chiamate.close();
		stream_trees.close();
		stream_report.close();
		
	}
	
	/**
	 * Rimuove gli spazi bianchi e elimina il carattere "\l" dal singolo file
	 * @param file di input, file di output
	 * @throws IOException
	 */
	public static void formattingLine(File input, File output) throws IOException{
		
		
		Scanner read = new Scanner(input);
		FileWriter write = new FileWriter(output);
		
		
		
		while(read.hasNextLine()){
			String linea = read.nextLine();
			linea = linea.replaceAll(" ", "");
			linea = linea.replaceAll("\\\\l", "");
			write.write(linea+"\n");
			
		}
		read.close();
		write.close();
	}

	/**
	 * Elimina la directory e tutto il suo contenuto
	 * @param path della directory da eliminare
	 */
	private static void deleteDirectory(String path) {
		File dir = new File(path);
		
		if(dir.exists()){			
			if(dir.listFiles().length > 0){
				for(File f : dir.listFiles())
					f.delete();
			}
			dir.delete();
			
		}

	}
}

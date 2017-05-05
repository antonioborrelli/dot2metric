import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import costanti.Costanti;
/**
 * FASE DI PREPROCESSING: scorre tutti i file della cartella di input cercando 
 * solo quelli che iniziano per class e terminano per .dot;
 * rimuove gli spazi bianchi e elimina il oggni occorrenza del carattere "\l" dal singolo file,
 * salvando, infine, i nuovi file generati nella cartella input_modificati
 */
public class Preprocessing {
	private int file_analizzati; 
	private boolean isError;
	private String path_input,path_output;
	private long execution_time;

	public  Preprocessing(String path_input, String path_output){
		this.file_analizzati=0;
		this.isError=false;
		this.path_input=path_input;
		this.path_output=path_output;
		this.execution_time=0;
	}
	
	public boolean start() throws IOException{
		long start_PREPROCESSING = System.currentTimeMillis();
		File directory_input = new File(this.path_input);
		if(directory_input.exists()){
			if(directory_input.isDirectory()){
				File[] files = directory_input.listFiles();
				
				deleteDirectory(this.path_output+"/"+Costanti.DIRECTORY_INPUT_MODIFICATI);
				new File(this.path_output+"/"+Costanti.DIRECTORY_INPUT_MODIFICATI).mkdirs();
				for (File file : files) {
					String nome_file = file.getName();
					if(nome_file.endsWith(".dot") && nome_file.startsWith("class")
							&& !nome_file.startsWith("classarray") && !nome_file.startsWith("classbuffered") 
							&& !nome_file.startsWith("classint") 
							){
						
						File output = new File(this.path_output+"/"+Costanti.DIRECTORY_INPUT_MODIFICATI+"/"+nome_file);
						formattingLine(file,output);
						this.file_analizzati++;
						
					}
				}
				
			}else{
				this.isError=true;
				System.out.println("ERRORE 02: Il percorso indicato non è relativo ad una directory");
			}
		}else{
			this.isError=true;
			System.out.println("ERRORE 01: Directory inesistente");
		}
		long end_PREPROCESSING = System.currentTimeMillis();
		this.execution_time=end_PREPROCESSING-start_PREPROCESSING;
		return this.isError;
	}
	
	/**
	 * Rimuove gli spazi bianchi e elimina il carattere "\l" dal singolo file
	 * @param file di input, file di output
	 * @throws IOException
	 */
	public void formattingLine(File input, File output) throws IOException{
		
		
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
	public void deleteDirectory(String path) {
		File dir = new File(path);
		
		if(dir.exists()){			
			if(dir.listFiles().length > 0){
				for(File f : dir.listFiles())
					f.delete();
			}
			dir.delete();
			
		}

	}

	public int getFile_analizzati() {
		return file_analizzati;
	}

	public void setFile_analizzati(int file_analizzati) {
		this.file_analizzati = file_analizzati;
	}

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

	public String getPath_input() {
		return path_input;
	}

	public void setPath_input(String path_input) {
		this.path_input = path_input;
	}

	public String getPath_output() {
		return path_output;
	}

	public void setPath_output(String path_output) {
		this.path_output = path_output;
	}

	public long getExecution_time() {
		return execution_time;
	}

	public void setExecution_time(long execution_time) {
		this.execution_time = execution_time;
	}
	
	

}

package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import costanti.Costanti;
import grammatica.DotGrammar;
import grammatica.ParseException;
import utility.ObjMetodo;

public class selectInput {

	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub

		String path = Costanti.PATH_DIRECTORY_INPUT;
		
		ArrayList<String> listafile=new ArrayList<>();
		File directory = new File(path);
		
		if(directory.exists()){
			if(directory.isDirectory()){
				File[] files = directory.listFiles();
				
				
				deleteDirectory(Costanti.PATH_DIRECTORY_OUTPUT+"/"+Costanti.DIRECTORY_INPUT_MODIFICATI);
				new File(Costanti.PATH_DIRECTORY_OUTPUT+"/"+Costanti.DIRECTORY_INPUT_MODIFICATI).mkdirs();
				
				
				int count=0;
				for (File file : files) {
					String nome_file = file.getName();
					if(nome_file.endsWith(".dot") && nome_file.startsWith("class")){
						listafile.add(nome_file);
						
						
						formattingLine(path,nome_file);
						
						System.out.println("\nFile analizzato: " + nome_file);
						
						FileInputStream fis = new FileInputStream(new File(Costanti.PATH_DIRECTORY_OUTPUT+"/"+Costanti.DIRECTORY_INPUT_MODIFICATI+"/"+nome_file));
						DotGrammar dot = new DotGrammar(fis);
						ObjMetodo metodo = dot.start();
						
						System.out.println("**********************************************************************************************");
						String[] dati = getDati(metodo.getNome());
						System.out.println("Package: " + dati[0] + "\nClasse: " + dati[1] + "\nMetodo: "+ dati[2]);
						

						System.out.println("\nRELAZIONI METODI:");
						metodo.print();
						
						System.out.println("**********************************************************************************************");
						
						
						
						
						
						count++;
					}
				}
				System.out.println("\nTotale File: " + count);
				
			}else{
				System.out.println("ERRORE: Il percorso indicato non è relativo una directory");
			}
		}else{
			System.out.println("ERRORE: Directory inesistente");
		}
		
		
		
		
		
		
		
		
	}
	
	
	
	/**
	 * Ricava il nome della classe del package e del metodo dal percorso completo del metodo
	 * @param metodo
	 * @return
	 */
	
	private static String[] getDati(String metodo){
		String[] dati={"","",""};
		String m="";
		String c="";
		String p="";
		metodo = metodo.replaceAll("\"", "");
		String[] lista = metodo.split("\\.");
		
		boolean isCostruttore=false;
		String ultimo = lista[lista.length-1];		
		char a = ultimo.charAt(0);
		if('A'<= a && a <='Z')
			isCostruttore=true;
		
		if(isCostruttore)
		{
			m=ultimo;
			c=ultimo;
			p=lista[0];
			for(int i=1;i<lista.length-1;i++)
				p+="."+lista[i];
			
		}else{
			m=ultimo;
			if(lista.length > 2)
				c=lista[lista.length-2];
			
			p=lista[0];
			for(int i=1;i<lista.length-2;i++)
				p+="."+lista[i];
		}
		
		dati[0]=p;
		dati[1]=c;
		dati[2]=m;
		
		return dati;
	}
	
	/**
	 * Rimuove gli spazi bianchi e elimina il carattere "\l" dal singolo file
	 * @param nomeFile
	 * @throws IOException
	 */
	public static void formattingLine(String path, String nome) throws IOException{
		File input = new File(path+"/"+nome);
		File output = new File(Costanti.PATH_DIRECTORY_OUTPUT+"/"+Costanti.DIRECTORY_INPUT_MODIFICATI+"/"+nome);
		
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

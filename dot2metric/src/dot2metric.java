

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import costanti.Costanti;
import grammatica.DotGrammar;
import grammatica.DotGrammarTokenManager;
import grammatica.ParseException;
import grammatica.Token;
import utility.ObjMetodo;
import utility.TabellaSimboli;

public class dot2metric {

	
	
	public static void main(String[] args) throws ParseException, IOException {
		// TODO Auto-generated method stub

//		ArrayList<String> lista_metodi_chiamati = new ArrayList<>();
		
		String path = Costanti.PATH_DIRECTORY_INPUT+"/"+Costanti.PATH_FILE_INPUT;
		formattingLine(path);
		
		FileInputStream fis = new FileInputStream(new File(path));
		DotGrammar dot = new DotGrammar(fis);
		ObjMetodo metodo = dot.start();
		
		System.out.println("**********************************************************************************************");
		String[] dati = getDati(metodo.getNome());
		System.out.println("File: "+path);
		System.out.println("Package: " + dati[0] + "\nClasse: " + dati[1] + "\nMetodo: "+ dati[2]);
		

		System.out.println("\nRELAZIONI METODI:");
		metodo.print();
		
//		System.out.println("\nMETODO ANALIZZATO: " + metodo.getNome());
//		System.out.println("\nTABELLA DEI SIMBOLI:");
//		metodo.getSimboli().print();
		
		System.out.println("**********************************************************************************************");

		
//		Token token = null;
//		do{
//			token = dot.getNextToken();
//			String type = DotGrammarTokenManager.tokenImage[token.kind];
//			System.out.println("Value: \t" + token.image + "\t Line_inizio: \t" + token.beginLine + "\t Line_fine: \t" + token.endLine + "\t Column_inizio: \t" + token.beginColumn + "\t Column_fine: \t" + token.endColumn + "\t Type: \t" + type +"\n");
//		}while(token.kind != 0);
		
		
//		ArrayList<String> listafile = readInput(Costanti.PATH_DIRECTORY_INPUT);
//		
//		for (String nome_file : listafile) {
//			System.out.println("\n\n ******************************** FILE: " + nome_file + "************************************************ \n\n");
//			FileInputStream fis = new FileInputStream(new File(Costanti.PATH_DIRECTORY_INPUT+"/"+nome_file));
//			DotGrammar dot = new DotGrammar(fis);
//			System.out.println("\n\nNOME METODO : " + dot.start());
//			System.out.println("************************************************************************************************************************************************************************************************************************************************");
			
//			Token token = null;
//			do{
//				token = dot.getNextToken();
//				String type = DotGrammarTokenManager.tokenImage[token.kind];
//				System.out.println("Value: \t" + token.image + "\t Line_inizio: \t" + token.beginLine + "\t Line_fine: \t" + token.endLine + "\t Column_inizio: \t" + token.beginColumn + "\t Column_fine: \t" + token.endColumn + "\t Type: \t" + type +"\n");
//			}while(token.kind != 0);
//		}
		
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
	public static void formattingLine(String path) throws IOException{
		File input = new File(path);
		File output = new File(path+"_temp");
		
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
		output.renameTo(input);
	}

	
	
	private static ArrayList<String> readInput(String path){
		ArrayList<String> listafile=new ArrayList<>();
		File directory = new File(path);
		
		if(directory.exists()){
			if(directory.isDirectory()){
				File[] files = directory.listFiles();
				
				int count=0;
				for (File file : files) {
					String nome_file = file.getName();
					if(nome_file.endsWith(".dot") && nome_file.startsWith("class")){
						listafile.add(nome_file);
//						System.out.println(nome_file);
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
		
		return listafile;
		
	}
}

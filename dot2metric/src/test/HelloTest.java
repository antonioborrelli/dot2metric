package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import costanti.Costanti;
import grammatica.DotGrammar;
import grammatica.DotGrammarTokenManager;
import grammatica.ParseException;
import grammatica.Token;

public class HelloTest {

	
	
	public static void main(String[] args) throws FileNotFoundException, ParseException {
		// TODO Auto-generated method stub

		ArrayList<String> lista_metodi_chiamati = new ArrayList<>();
		FileInputStream fis = new FileInputStream(new File(Costanti.PATH_DIRECTORY_INPUT+"/"+Costanti.PATH_FILE_INPUT));
		DotGrammar dot = new DotGrammar(fis);
		System.out.println("NOME METODO : " + dot.test());
		
		
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
//			Token token = null;
//			do{
//				token = dot.getNextToken();
//				String type = DotGrammarTokenManager.tokenImage[token.kind];
//				System.out.println("Value: \t" + token.image + "\t Line_inizio: \t" + token.beginLine + "\t Line_fine: \t" + token.endLine + "\t Column_inizio: \t" + token.beginColumn + "\t Column_fine: \t" + token.endColumn + "\t Type: \t" + type +"\n");
//			}while(token.kind != 0);
//		}
		
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

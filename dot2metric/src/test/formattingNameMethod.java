package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import costanti.Costanti;
import utility.ObjMetodo;

public class formattingNameMethod {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String path = "input/pre.dot";
		formattingLine(path);
		

	}
	
	/**
	 * Rimuove gli spazi bianchi e elinima il carattere "\l" dal singolo file
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

}

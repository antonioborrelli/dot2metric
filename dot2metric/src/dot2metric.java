import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import grammatica.ParseException;

public class dot2metric {
	private static String path_input;
	private static String path_output;

	public static void main(String[] args) throws IOException, ParseException {

		
		loadConfig(args);

		System.out.println("************************* DOT2METRICS *************************");
		long start = System.currentTimeMillis();

		
		System.out.println("--> FASE DI PREPROCESSING AVVIATA");
		Preprocessing preprocessing = new Preprocessing(path_input, path_output);
		if(!preprocessing.start()){//PREPROCESSING
			System.out.println("--> FASE DI PREPROCESSING TERMINATA CON SUCCESSO [ " + preprocessing.getExecution_time() + " millisecondi ]");

			System.out.println("--> FASE DI TRADUZIONE AVVIATA");
			Traduzione traduzione = new Traduzione(path_output);
			if(!traduzione.start()){//TRADUZIONE
				System.out.println("--> FASE DI TRADUZIONE TERMINATA CON SUCCESSO [ "+ traduzione.getExecution_time() +" millisecondi ]");
			
				//ANALISI
				System.out.println("--> FASE DI ANALISI AVVIATA");
				Analisi analisi= new Analisi(path_output, traduzione.getMappa());
				analisi.start();
				System.out.println("--> FASE DI ANALISI TERMINATA CON SUCCESSO [ "+ analisi.getExecution_time() +" millisecondi ]");

				//REPORT
				System.out.println("--> FASE DI REPORTING AVVIATA");
				Reporting report = new Reporting(path_output, preprocessing, traduzione, analisi);
				report.start();
				System.out.println("--> FASE DI REPORTING TERMINATA [ " + report.getExecution_time() + " millisecondi ]");
			
			}else{
				System.out.println("--> FASE DI TRADUZIONE TERMINATA CON ERRORE [ "+ traduzione.getExecution_time() +" millisecondi ]");
			}
			
		}else{
			System.out.println("--> FASE DI PREPROCESSING ARRESTATA CON ERRORE [ " + preprocessing.getExecution_time() + " millisecondi ]");
		}

		long end = System.currentTimeMillis();
		System.out.println(" TEMPO TOTALE : [ " + (end-start) + " millisecondi ]");
		

		
	}
	
	public static void loadConfig(String[] args) throws FileNotFoundException{
		
		File config = new File("config_path.dat");
		if(args.length == 2){
			path_input = args[0];
			path_output = args[1];
			setConfig(path_input, path_output);
		}else if(config.exists()){
			Scanner read= new Scanner(config);
			path_input=read.nextLine().split("-->")[1];
			path_output=read.nextLine().split("-->")[1];
			read.close();
		}else{
			System.out.println("INSERIRE IL PATH DELLA CARTELLA DI INPUT: ");
			Scanner read =new Scanner(System.in);
			path_input=read.nextLine();
			System.out.println("INSERIRE IL PATH DELLA CARTELLA DI OUTPUT: ");
			path_output=read.nextLine();
			read.close();
			setConfig(path_input, path_output);
			
		}
	}
	
	
	
	public static void setConfig(String in, String out) throws FileNotFoundException{
		File config = new File("config_path.dat");
		PrintStream stream_config= new PrintStream(config);
		stream_config.println("PATH_INPUT-->"+in);
		stream_config.println("PATH_OUTPUT-->"+out);
		stream_config.close();
	}
}

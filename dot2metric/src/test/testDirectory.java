package test;

import java.io.File;
import java.io.IOException;

import costanti.Costanti;

public class testDirectory {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String directory = Costanti.PATH_DIRECTORY_OUTPUT+"/"+Costanti.DIRECTORY_INPUT_MODIFICATI;
		deleteDirectory(directory);
		
		
	}
	
	
	
	public static void deleteDirectory(String path) {
		File dir = new File(path);
		
		if(dir.exists()){
			System.out.println("Esiste!");
			
			if(dir.listFiles().length > 0){
				for(File f : dir.listFiles())
					f.delete();
			}
			
			if(dir.delete())
				System.out.println("Eliminata correttamente");
			else
				System.out.println("Non eliminata!");
		}else
			System.out.println("Non esiste");
	}

}

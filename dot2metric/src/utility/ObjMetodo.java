package utility;
import java.util.ArrayList;
import java.util.HashMap;

public class ObjMetodo {
	
	private String nome;
	private HashMap<String,ArrayList<String>> mappaArchi;
	private TabellaSimboli simboli;
	
	public ObjMetodo(String nome){
		this.nome=nome;
		this.mappaArchi=new HashMap<>();
		this.simboli = new TabellaSimboli();
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void addElement(String chiamante, String chiamato){
		
		if(this.mappaArchi.containsKey(chiamante))
			this.mappaArchi.get(chiamante).add(chiamato);
		else{
			ArrayList<String> a = new ArrayList<>();
			a.add(chiamato);
			this.mappaArchi.put(chiamante, a );
			}
		
	}
	
	public HashMap<String, ArrayList<String>> getMappa() {
		return mappaArchi;
	}

	public void setMappa(HashMap<String, ArrayList<String>> mappa) {
		this.mappaArchi = mappa;
	}
	
	public TabellaSimboli getSimboli() {
		return simboli;
	}

	public void setSimboli(TabellaSimboli simboli) {
		this.simboli = simboli;
	}

	public void print(){
		for (String chiamante : this.mappaArchi.keySet()) {
			ArrayList<String> chiamati = this.mappaArchi.get(chiamante);
			if(chiamati.size()>1){
				System.out.println(this.simboli.getNome(chiamante) );
				for(String a : chiamati)
					if(chiamante.equals("Node1"))
						System.out.println(" \t " + " --> " + this.simboli.getNome(a)+" chiamata diretta");
					else
						System.out.println(" \t " + " --> " + this.simboli.getNome(a)+" chiamata indiretta");
			}else
				if(chiamante.equals("Node1"))
					System.out.println(this.simboli.getNome(chiamante) + " --> " + this.simboli.getNome(chiamati.get(0))+" chiamata diretta");
				else
					System.out.println(this.simboli.getNome(chiamante) + " --> " + this.simboli.getNome(chiamati.get(0))+" chiamata indiretta");
			System.out.println();
		}
	}
	
}

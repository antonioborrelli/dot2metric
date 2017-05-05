package utility;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import treeStructure.Node;
import treeStructure.Tree;

public class ObjMetodo {
	
	private String nome_esteso;
	private HashMap<String,ArrayList<String>> mappaArchi;
	private TabellaSimboli simboli;
	private HashMap<String,ArrayList<String>> mappaChiamate;
	private HashMap<String,Boolean> mappaChiamateLette;
	
	public ObjMetodo(String nome){
		this.nome_esteso=nome;
		this.mappaArchi=new HashMap<>();
		this.mappaChiamate=new HashMap<>();
		this.mappaChiamateLette=new HashMap<>();
		this.simboli = new TabellaSimboli();
		
	}

	public String getNomeEsteso() {
		return nome_esteso;
	}

	public void setNomeEsteso(String nome) {
		this.nome_esteso = nome;
	}

	public void inserisciMappaChiamate(){
		for(String id_chiamante : mappaArchi.keySet()){
			String nome_chiamante = this.simboli.getNome(id_chiamante);
			ArrayList<String> nomi_chiamati = new ArrayList<>();
			ArrayList<String> id_chiamati = this.mappaArchi.get(id_chiamante);
			for(String id_chiamato: id_chiamati)
				nomi_chiamati.add(this.simboli.getNome(id_chiamato));
 			this.mappaChiamate.put(nome_chiamante, nomi_chiamati);
 			this.mappaChiamateLette.put(nome_chiamante,true);
		}
	}

	
	public String getNome_esteso() {
		return nome_esteso;
	}

	public void setNome_esteso(String nome_esteso) {
		this.nome_esteso = nome_esteso;
	}

	public HashMap<String, ArrayList<String>> getMappaArchi() {
		return mappaArchi;
	}

	public void setMappaArchi(HashMap<String, ArrayList<String>> mappaArchi) {
		this.mappaArchi = mappaArchi;
	}

	public HashMap<String, ArrayList<String>> getMappaChiamate() {
		return mappaChiamate;
	}

	public void setMappaChiamate(HashMap<String, ArrayList<String>> mappaChiamate) {
		this.mappaChiamate = mappaChiamate;
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

	public Tree<String,String> getTree(){
		Tree<String,String> albero = new Tree<String,String>();
		Node<String,String> radice = albero.insert(null, this.getNomeEsteso());
		if(this.mappaChiamate.get(this.getNomeEsteso())!=null){
		ArrayList<String> lista_chiamate_dirette = this.mappaChiamate.get(this.getNomeEsteso());
			for(String chiamato : lista_chiamate_dirette){
				Node<String,String> figlio = albero.insert(radice, chiamato);
				if(this.mappaChiamate.get(chiamato) != null){
					ArrayList<String> lista_chiamate_figlio = this.mappaChiamate.get(chiamato);
					insertNodo(figlio, lista_chiamate_figlio, albero);
				}
			}
		}
		return albero;
		
	}
	
	public void insertNodo(Node<String,String> padre,ArrayList<String> lista_chiamate, Tree<String,String> albero){
		if(lista_chiamate!=null){
			for(String chiamato : lista_chiamate){
				
				Node<String,String> figlio = albero.insert(padre, chiamato);
				if(this.mappaChiamate.get(chiamato) != null){
					if(this.mappaChiamateLette.containsKey(chiamato) && this.mappaChiamateLette.get(chiamato)){
						this.mappaChiamateLette.put(chiamato, false);
						ArrayList<String> lista_chiamate_figlio = this.mappaChiamate.get(chiamato);
						insertNodo(figlio, lista_chiamate_figlio, albero);
					}
				}
				
			}
		}
	}
	
	public void stampaMappaChiamate(PrintStream p){
		for(String chiamante : this.mappaChiamate.keySet()){
			p.println(chiamante);
			for(String chiamato : this.mappaChiamate.get(chiamante))
				if(chiamante.equals(this.nome_esteso))
					p.println("\t"+chiamato+"[DIRETTA]");
				else
					p.println("\t"+chiamato+"[INDIRETTA]");
		}
	}
	
	public void printChiamateDirette(PrintStream p){
		if(this.nome_esteso != "" && this.mappaChiamate!=null && this.mappaChiamate.containsKey(this.nome_esteso))
			for(String chiamato : this.mappaChiamate.get(this.nome_esteso))
				p.println(this.nome_esteso + " --> " + chiamato);
	}
	
	public int getChiamateDirette(){
		if(this.mappaArchi != null && this.mappaArchi.containsKey("Node1")) 
			return this.mappaArchi.get("Node1").size();
		return 0;
	}
	
	public int getChiamateIndirette(){
		int count=0;
		if(this.mappaArchi != null) 
			for(String node: this.mappaArchi.keySet())
				if(!node.equals("Node1"))
				count += this.mappaArchi.get(node).size();
			
		return count;
	}
}

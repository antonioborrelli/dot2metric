package utility;
import java.util.HashMap;

public class TabellaSimboli {

	private HashMap<String,String> tabella;
	
	public TabellaSimboli(){
		this.tabella= new HashMap<>();
	}
	
	public void addSimbolo(String id){
		this.tabella.put(id, "");
	}
	
	public void addSimbolo(String id,String nome){
		this.tabella.put(id, nome);
	}
	
	public String getNome(String id){
		return this.tabella.get(id);
	}
	
	public boolean isId(String id){
		return this.tabella.containsKey(id);
	}
	
	public boolean isChiamato(String nome){
		for(String s : tabella.keySet())
			if(!s.equals("Node1") && nome.equals(tabella.get(s)))
				return true;
		return false;
	}
	
	public void print(){
		for (String id : this.tabella.keySet()) {
			System.out.println(id + " : " + this.tabella.get(id));
		}
	}
}

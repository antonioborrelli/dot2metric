package treeStructure;

import java.io.PrintStream;

public class Tree <K extends Comparable<K>,V> {
	
	protected Node<K,V> root;
	private boolean isIndipendente;
	
	public Tree(){
		this.root=null;
		this.isIndipendente=true;
	}
	
	public Node<K, V> insert(Node<K,V> p, K element, V value) {
		Node<K, V> newNode = new Node<K, V>(element, value);
		
		if (p == null)
			this.root = newNode;
		else
			p.addChildren(newNode);
		
		return newNode;
	}
	
	public Node<K, V> insert(Node<K,V> p, K element) {
		return insert(p,element,null);
	}
	
	public void printPreOrder(PrintStream p){
		printPreOrder(this.root,p,0);
	}
	
	public void printPreOrder(Node<K,V> nodo, PrintStream p, int indice){
		
		for(int i=0;i<indice;i++)
			p.print("\t");
		p.println(nodo.key());
		for(Node<K,V> n : nodo.getChildrens())
			this.printPreOrder(n, p, (indice+1));

	}
	
	public void printPostOrder(PrintStream p){
		printPostOrder(this.root,p,0);
	}
	
	public void printPostOrder(Node<K,V> nodo, PrintStream p, int indice){
		for(int i=0;i<indice;i++)
			p.print("\t");
		for(Node<K,V> n : nodo.getChildrens())
			this.printPostOrder(n, p, (indice+1));
		p.println(nodo.key());

	}
	
	public boolean contains(Node<K,V> A){
		if(this.isEquals(this.root,A)){
			return true;
			}
		for(Node<K,V> B : this.root.getChildrens())
			if(this.isEquals(B,A))
				return true;
		return false;
		
	}

	public boolean isEquals(Node<K,V> A, Node<K,V> B){
		boolean equals=true;
		if(A.key().equals(B.key()) && A.getChildrens().size() == B.getChildrens().size()){
			if(A.getChildrens().size() > 0)
				for(int i=0; i < A.getChildrens().size(); i++){
					if(!isEquals(A.getChildrens().get(i), B.getChildrens().get(i)))
						return false;
					
				}
		}else
			equals=false;	
		
		return equals;
		
	}

	public Node<K, V> getRoot() {
		return root;
	}

	public void setRoot(Node<K, V> root) {
		this.root = root;
	}

	public boolean isIndipendente() {
		return isIndipendente;
	}

	public void setIndipendente(boolean isIndipendente) {
		this.isIndipendente = isIndipendente;
	}
	
	
	
}

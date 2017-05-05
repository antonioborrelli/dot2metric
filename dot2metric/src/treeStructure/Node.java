package treeStructure;

import java.util.ArrayList;

public class Node<K, V> implements Position<K, V> {

	private Node<K,V> parent;
	private ArrayList<Node<K,V>> childrens;
	private K key;
	private V value;
	
	
	public Node(K k){
		this.key=k;
		this.setValue(null);
		this.setParent(null);
		this.setChildrens(new ArrayList<>());
		
	}
	
	public Node(K k, V v){
		this.key=k;
		this.setValue(v);
		this.setParent(null);
		this.setChildrens(new ArrayList<>());
		
	}
	
	public Node(K k, V v, Node<K,V> parent){
		this.key=k;
		this.setValue(v);
		this.setParent(parent);
		this.setChildrens(new ArrayList<>());
		
	}
	
	public Node(K k, V v, Node<K,V> parent, ArrayList<Node<K,V>> childrens){
		this.key=k;
		this.setValue(v);
		this.setParent(parent);
		this.setChildrens(childrens);
		
	}
	
	public Position<K,V> getParent(){
		return this.parent;
	}
	
	public void setParent(Node<K,V> parent){
		this.parent=parent;
	}
	
	public void addChildren(Node<K,V> children){
		this.childrens.add(children);
	}
	public void setChildrens(ArrayList<Node<K,V>> childrens){
		this.childrens=childrens;
	}
	
	public ArrayList<Node<K,V>> getChildrens(){
		return this.childrens;
	}
	
	public void setKey(K k){
		this.key=k;
	}
	
	public void setValue(V v){
		this.value=v;
	}
	@Override
	public K key() {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	public V value() {
		// TODO Auto-generated method stub
		return value;
	}

}

package test;

import treeStructure.Node;
import treeStructure.Tree;

public class testTree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tree<String,String> albero = new Tree<String,String>();
		Node<String,String> radice = albero.insert(null, "ALBERO");
		Node<String,String> figlio_primo_livello01 = albero.insert(radice, "figlio01");
		Node<String,String> figlio_primo_livello02 = albero.insert(radice, "figlio02");
		Node<String,String> figlio_primo_livello03 = albero.insert(radice, "figlio03");
		Node<String,String> figlio_primo_livello04 = albero.insert(radice, "figlio043");
		
		Node<String,String> figlio_secondo_livello01 = albero.insert(figlio_primo_livello01, "figlio04");
		Node<String,String> figlio_secondo_livello01_bis = albero.insert(figlio_primo_livello01, "figlio04_bis");
		Node<String,String> figlio_secondo_livello02 = albero.insert(figlio_primo_livello02, "figlio05");
		Node<String,String> figlio_secondo_livello03 = albero.insert(figlio_primo_livello03, "figlio06");
		
		
		System.out.println("ATTRAVERSAMENTO PREORDER");
		albero.printPreOrder(System.out);
		

		
		Tree<String,String> albero_ = new Tree<String,String>();
		Node<String,String> radice_ = albero_.insert(null, "figlio01");
		Node<String,String> figlio_primo_livello01_ = albero_.insert(radice_, "figlio04");
		Node<String,String> figlio_primo_livello02_ = albero_.insert(radice_, "figlio04_bis");
//		Node<String,String> figlio_primo_livello03_ = albero_.insert(radice_, "figlio03");

		
		
		
		System.out.println();
		albero_.printPreOrder(System.out);
		
		System.out.println("figlio01 è contenuto in ALBERO??? " + albero.contains(albero_.getRoot()));
//		System.out.println(albero.isEquals(figlio_primo_livello01_,figlio_primo_livello01));
	}

	
	
	public static boolean isEquals(Node<String,String> A, Node<String,String> B){
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
}

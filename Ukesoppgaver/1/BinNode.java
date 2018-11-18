//Ukesoppgave 1.7
class BinNode {
	private Node root;
	
	private class Node {
		private int value;
		private Node left, right;
		
		public Node(int v) {
			this.value = v;	
		}
	}

	/** 1.7.1
	*insertVal og insert setter inn value inn i det binæresøktreet. Hvis value er lik en 
	*value som allerede finnes i treet, vil programmet gå lengre ned i det høyre subtreet 
	* og sette den på riktig plass der. */
	public void insertVal(int value) {
		if(root == null) {
			root = new Node(value);
		else if(root.value < value)
			insert(root.right, value);
		else if(root.value > value)
			insert(root.left, value);
		else if(root.value == value) {
			insert(root.right, value);
			System.out.println("Duplikat --- legges i høyre subtre");
		}
	}
	
	private void insert(Node n, int value) {
		if(n == null) {
			n = new Node(value);
		} else if(n.value < value) {
			insert(n.right, value);
		} else if(n.value > value) {
			insert(n.left, value);
		} else if(n.value == value) {
			insert(n.right, value);
			System.out.println("Duplikat --- legges i høyre subtre");
		}
	}
	/** 1.7.2
	* Hvis like verdier blir lagt inn i høyre subtre, og man traverserer gjennom den, vil
	* man først få verdien til den nederste noden som har en duplikat verdi. Med venstre
	* subtre besøker man først venstre subtre/barn, så foreldren og til høyre subtree/barn
	* derfor vil først duplikatene i venstresubtre stå først, deretter foreldre noden og 
	* så høyre subtre.
	void traverser(BinNode n) {
    	if (n != null) {
			< Gjør PREFIKS-operasjonene > Prefiks (preorder): behandle noden før vi går videre til barna.
	    traverser(n.venstre);
   			< Gjør INFIKS-operasjonene > Infiks (Inorder): behandler venstre subtre før forelder, så høyre subtre.
   			public List<Integer> inorderTraversal(TreeNode root) {
     			if(root !=null){
					helper(root);
        		}
 				return result;
   			}
			public void helper(TreeNode p){
				if(p.left!=null)
					helper(p.left);
				result.add(p.val);
				if(p.right!=null)
					helper(p.right);
			}
 	   traverser (n.hoyre);
   			< Gjør POSTFIKS-operasjonene > Postfiks (postorder): behandle noden etter at vi har besøkt alle barna til noden.
	}
	/* Given a binary tree, print its nodes according to the "bottom-up" postorder traversal. */
    void printPostorder(Node node) {
        if (node == null)
            return;
        printPostorder(node.left); // first recur on left subtree
		printPostorder(node.right); // then recur on right subtree
        System.out.print(node.key + " "); // now deal with the node
    }
    /* Given a binary tree, print its nodes in inorder*/
    void printInorder(Node node) {
        if (node == null)
            return;
        printInorder(node.left); //first recur on left child
        System.out.print(node.key + " "); //then print the data of node
        printInorder(node.right); //now recur on right child
    }
    /* Given a binary tree, print its nodes in preorder*/
    void printPreorder(Node node) {
        if (node == null)
            return;
        System.out.print(node.key + " "); //first print data of node
        printPreorder(node.left); //then recur on left sutree
        printPreorder(node.right); //now recur on right subtree
    }
    // Wrappers over above recursive functions
    void printPostorder()  {     printPostorder(root);  }
    void printInorder()    {     printInorder(root);   }
    void printPreorder()   {     printPreorder(root);  } */
    
    /** 1.7.3
    * insert og insertVal gjør samme som i 7.1, bare at her skal den legge noder med like
    * verdier inn i en liste hvor første node er noden som var den første med den verdien.
    * Dette er ikke en fullstendig kode. */
    private List<Node> nodeliste = new List<Node>();
    public void insertVal(int value) {
		if(root == null) {
			root = new Node(value);
		else if(root.value < value)
			insert(root.right, value);
		else if(root.value > value)
			insert(root.left, value);
		else if(root.value == value) {
			same(root, value);
			System.out.println("Duplikat --- legges i høyre subtre");
		}
	}
	
	private void insert(Node n, int value) {
		if(n == null) {
			n = new Node(value);
		} else if(n.value < value) {
			insert(n.right, value);
		} else if(n.value > value) {
			insert(n.left, value);
		} else if(n.value == value) {
			same(n, value);
			System.out.println("Duplikat --- legges i høyre subtre");
		}
	}
	
	//ikke en fullstendig kode
	private void same(Node forst, int value) {
		Node nesteLik = new Node(value);
		nodeliste.add(forste);
		nodeliste.add(nesteLik);
	}
    
    /**1.7.4
    * printPreorder skrive ut nodene med samme verdi i den rekkefølgen de ble lagt inn i*/
	void printPreorder() {
		printPreorder(root);
	}
	 void printPreorder(Node node) {
        if (node == null)
            return;
        System.out.print(node.value + " "); //first print data of node
        printPreorder(node.left); //then recur on left sutree
        printPreorder(node.right); //now recur on right subtree
    }
}

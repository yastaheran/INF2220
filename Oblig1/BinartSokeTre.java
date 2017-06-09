import java.util.*;

class BinartSokeTre {
	protected Node tmp, rotNode;
	private int totDybde;
	private String forsteAlfa, sisteAlfa; //tar vare paa forste og siste ordet i alfabetiskrekkefølge
	private char[] alfa = "abcdefghijklmnopqrstuvwxyz".toCharArray(); //for genering av likeord
	private String[] sm1, sm2, sm3, sm4;  //tar vare på arrayene som similar words-metodene lager
	private long startTime, estimatedTime;
	
	/**
	* Indre node-klasse
	*/
	private static class Node {
		protected Node venstre, hoyre;
		protected String ord;
		
		Node(Node l, Node r, String ord) {
			this.venstre = l;
			this.hoyre = r;
			this.ord = ord;
		}
	/* 
		public void settInn(Node nyNode) {
			int compare = nyNode.ord.compareTo(ord);
			
			if(compare < 0) {
				if(venstre == null) {
					venstre = nyNode;
				} else {
					venstre.settInn(nyNode);
				}
			} else if(compare > 0) {
				if(hoyre == null) {
					hoyre = nyNode;
				} else {
					hoyre.settInn(nyNode);
				}
			} else {
				//duplikater er ikke tillatt
			}
		}
	*/
			
	}
	
	/**
	* rekursivt innsettings metode 1 & 2
	*/
	public void settInn(String ord) {  //1
		if(rotNode == null) {
			rotNode = new Node(null, null,  ord);
		}
		settInn(ord, rotNode);
		
		/* 
		Node ny = new Node(null, null, ord);
		
		if(rotNode == null) { rotNode = ny; }
		else { rotNode.addNode(ny); }
		*/
		
	}
	
	private Node settInn(String ord, Node node) { //2
		Node tmp = new Node(null, null, ord);
		
		int compare = ord.compareToIgnoreCase(node.ord);
		
		if(compare < 0) {
			if(node.venstre == null) {
				node.venstre = tmp;
			} else {
				settInn(ord, node.venstre);
			}
		} else if(compare > 0){
			if(node.hoyre == null) {
				node.hoyre = tmp;
			} else {
				settInn(ord, node.hoyre);
			}
		} else {
			//ingen duplikater er tillatt i ordboken
		}
		return node;
	}
	
	/*
	* slettemetode som fjerner ordet fra treet og returnerer true dersom alt går som
	* planlagt. Rekursivt sletting
	* @param ord er ordet som skal fjernes.
	*/	
//	public Node fjern(String ord, Node n) {

	public void fjern(String ord) {
		Node node =  rotNode; //noden med ordet som skal slettes
		Node foreldre = null;
		boolean funnet = false;
		
		while(!funnet && node != null) {
			int d = node.ord.compareTo(ord);
			if(d == 0) {
				System.out.print(ord + " er slettet.");
				funnet = true;
			} else {
				foreldre = node;
				if(d > 0) {
					node = node.venstre;
				} else {
					node = node.hoyre;
				}
			}
			
			if(!funnet) {
				return;
			}
			
			//hvis node inneholder ordet, og en av subtrærne er tomme
			//skal den bruke det andre subtreet.
			if(node.venstre == null || node.hoyre == null) {
				Node nyttBarn;
				if(node.venstre == null) {
					nyttBarn = node.hoyre;
				} else {
					nyttBarn = node.hoyre;
				}
				
				//funnet i rotNoden
				if(foreldre == null) {
					rotNode = nyttBarn;
				} else if(foreldre.venstre == node) {
					foreldre.venstre = nyttBarn;
				} else {
					foreldre.hoyre = nyttBarn;
				}
				return;
			}
			
			//hvis node inneholder ordet, men ingen av subtrærne er tomme
			//skal den finne minste elementet i hoeyre subtre
			Node minsteForeldre = node;
			Node minsteNode = node.hoyre;
			while(minsteNode.venstre != null) {
				minsteForeldre = minsteNode;
				minsteNode = minsteNode.venstre;
			}
			
			//hvis minsteNode inneholder det minste barnet i hoeyre subtre skal den
			//flyttes.
			node.ord = minsteNode.ord;
			if(minsteForeldre == node) {
				minsteForeldre.hoyre = minsteNode.hoyre;
			} else {
				minsteForeldre.venstre = minsteNode.hoyre;
			}
			
		}
	
	}
	/*
    	if(n == null) { 
    		System.out.println("Tom node. BinartSokeTre.java linje 75");
    		return null;
    	}
    	if(ord.compareTo(n.ord) < 0) {
        	n.venstre = fjern(ord, n.venstre);
    	} else if(ord.compareTo(n.ord) > 0) {
        	n.hoyre = fjern(ord, n.hoyre);
    	} else {
    		if (n.venstre == null) {
            	n = n.hoyre;
        	} else if (n.hoyre == null) {
            	n = n.venstre;
        	} else {
           		n.ord = finnMin(n.hoyre);
           		n.hoyre = fjern(n.ord, n.hoyre);
        	}
		}
		System.out.print("Ordet " + n.ord + " er slettet");
		return n; 
	}
	*/
	/*
	* beregnDybdeMax blar rekursivt igjennom treet for å finne treets totale dybde, 
	* og returner dybden på treet. For hvert kall, går dypden på treet opp.
	*/
	public int beregnDybdeMax(Node n, int depth) { //2
		if(n == null) {
			return 0;
		} else if(n.venstre == null && n.hoyre == null) {
			return depth;
		} else {  
			/*
			* Henter max dybden til subtreene, og returnerer verdien til subtreet med
			* høyest verdi.
			*/
			int vMax = beregnDybdeMax(n.venstre, depth++);
			int hMax = beregnDybdeMax(n.hoyre, depth++);
			
			if(vMax > hMax) {
				return vMax;
			} else {
				return hMax;
			}
		}
	}
	
	/*
	* beregnDybdePrNode blar rekursivt igjennom treet for å finne nodens dybde. for hver
	* node skal den skrive ut dybden til noden.
	*/
	public Map<Integer, Integer> beregnDybdePrNode(Node n, int depth) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		int count = 0;	
		
		if(depth == 0 && rotNode != null) {
			count++;
			map.put(depth, count);
			beregnDybdePrNode(n.venstre, depth + 1, map);
			beregnDybdePrNode(n.hoyre, depth + 1, map);
		}
		return map;
	}
	
	public Map<Integer, Integer> beregnDybdePrNode(Node n, int depth, Map<Integer, Integer> map) {
		if(n == null) { 
			return map; 
		} 
		
		Integer count =  map.get(depth);
		
		if(count == null) { 
			count = 0; 
		}
		
		count++;
		
		map.put(depth, count);
		
		beregnDybdePrNode(n.venstre, depth + 1, map);
		beregnDybdePrNode(n.hoyre, depth + 1, map);
		
		return map;		
	}
	
	/*
	* gjensomsnitssDybde kaller på metoden tellNoder som blar rekursivt igjennom 
	* treet for å finne antall noder. Den kaller på beregnDybdeSum og deler 
	* summen av alle dybdene på total antall noder.
	*/
	public double gjennomsnittsDybde(Node n, int depth) {
		int antallNoder = tellNoder(n);
		int sumDybde = beregnDybdeSum(n, depth);
		
		double gjennomsnitt = sumDybde/antallNoder;
		
		return gjennomsnitt;
	}
	
	private int tellNoder(Node n) {
		if(n == null) {
			return 0;
		} else if(n.venstre == null && n.hoyre == null) {
			return 1;
		} else {
			return tellNoder(n.venstre) + tellNoder(n.hoyre);
		}
	}
	
	private int beregnDybdeSum(Node n, int depth) {
		if(n == null) {
			return 0;
		} else if(n.venstre == null && n.hoyre == null) {
			return depth;
		} else {
			return beregnDybdeSum(n.venstre, depth++) + beregnDybdeSum(n.hoyre, depth++);
		}
	}

	//henter siste ordet som er sist (alfabetisk)	
	public String getLast() {
		if(rotNode == null) {
			System.out.print("Treet er tomt");
			return null;
		} else {
			String siste = finnMax(rotNode);
			System.out.print("Siste ordet: " + siste);
			return siste;
		}
	}
	
    private String finnMax(Node node) {
    	if(node != null) {
    		while(node.hoyre != null) {
    			node = node.hoyre;
    		}
		}
		return node.ord;
    }

	//henter siste ordet som er forst (alfabetisk)		
	public String getFirst() {
		if(rotNode == null) {
			System.out.print("Treet er tomt");
			return null;
		} else {
			String forste = finnMin(rotNode);
			System.out.print("Forste ordet: " + forste);
			return forste;
		}
	}
	
	private String finnMin(Node node) {
        if (node == null) {
            return null;
        } else if (node.venstre == null) {
            return node.ord;
        }
        return finnMin(node.venstre);
    }
	
	//søkemetoder som returner true dersom ordet den finnes
	public boolean sokSIM(String ord, Node n) {
    	if (n == null) {
        	return false;
    	} else if (ord.compareTo(n.ord) < 0) {
        	return sokSIM(ord, n.venstre);
    	} else if (ord.compareTo(n.ord) > 0) {
        	return sokSIM(ord, n.hoyre);
    	} else if(ord.compareTo(n.ord) == 0) {
			return true;
		} 
		return false;
	}
	
	public boolean sok(String ord) { 
		 Node current = rotNode;
		 while(current != null) {
		 	int o = current.ord.compareTo(rotNode.ord);
		 	if(o == 0) {
		 		System.out.println("funnet: " + true);
		 		return true;
		 	} else if(o > 0) {
		 		current = current.venstre;
		 	} else if(o < 0){
		 		current = current.hoyre;
		 	} else {
		 		int antMatch = 0;
				sm1 = similarOne(ord);   //Sjekker forste genereringsregel
				for(int i = 0; i < sm1.length; i ++) {
					if(sokSIM(sm1[i], rotNode)) {	//gaar igjennom treet på nytt og tester det nye ordet
						System.out.println("Mente du: " + sm1[i]);
						antMatch++;
					}
				}
				System.out.println("Antall Match " + antMatch);
			
				antMatch = 0;
				sm2 = similarTwo(ord);  //Sjekker andre genereringsregel
				for(int i = 0; i < sm2.length; i ++) {
					if(sokSIM(sm2[i], rotNode)) {	//gaar igjennom treet på nytt og tester det nye ordet
						System.out.println("Mente du: " + sm2[i]);
						antMatch++;
					}
				}
				System.out.println("Antall Match " + antMatch);
			
				antMatch = 0;
				sm3 = similarTree(ord); //sjekker tredje genereringsregel
				for(int i = 0; i < sm1.length; i ++) {
					if(sokSIM(sm3[i], rotNode)) {	//gaar igjennom treet på nytt og tester det nye ordet
						System.out.println("Mente du: " + sm3[i]);
						antMatch++;
					}
				}
				System.out.println("Antall Match " + antMatch);
			
				antMatch = 0;
				sm4 = similarFour(ord); //Sjekker fjerde genereringsregel
				for(int i = 0; i < sm4.length; i ++) {
					if(sokSIM(sm4[i],rotNode)) {	//gaar igjennom treet på nytt og tester det nye ordet
						System.out.println("Mente du: " + sm4[i]);
						antMatch++;
					}
				}
		 	}
		 }
		 return false;
	}
		
	/**
	* Første genereringsregel. 
	* Hentet fra obligteksten.
	*/
	public String[] similarOne(String word){
		startTime = System.nanoTime();
   		char[] word_array = word.toCharArray();
    	char[] tmp;
    	String[] words = new String[word_array.length-1];
    	
    	for(int i = 0; i < word_array.length - 1; i++){
        	tmp = word_array.clone();
        	words[i] = swap(i, i + 1, tmp);
		}
		estimatedTime = System.nanoTime() - startTime;
		System.out.println("Det ble brukt " + estimatedTime + " nanosekund på å generere disse ordene:");
		for(int i = 0; i < words.length - 1; i++) {
			if(words[i] != null) { 
				System.out.println(words[i]); 
			}
		}
    	return words;
	}
	
	public String swap(int a, int b, char[] word){
    	char tmp = word[a];
   		word[a] = word[b];
		word[b] = tmp;
    	return new String(word);
	}
	
	/**
	* Andre genereringsregel.
	* @param ord er identisk ordet X, men en bokstav er byttet ut.
	*/
	public String[] similarTwo(String ord) {
		startTime = System.nanoTime();
		char[] ordArray = ord.toCharArray();
    	char[] tmp;
    	String[] words = new String[ordArray.length*28];
		
		for(int i = 0; i < ordArray.length - 1; i++) {
			for(char a: alfa) {
				if(ordArray[i] != a) {
					tmp = ordArray.clone();
					tmp[i] = a;
					words[i] = new String(tmp);
				}
			}
		}
		estimatedTime = System.nanoTime() - startTime;
		System.out.println("Det ble brukt " + estimatedTime + " nanosekund på å finne disse ordene:");
		for(int i = 0; i < words.length - 1; i++) {
			if(words[i] != null) { 
				System.out.println(words[i]); 
			}
		}
		return words;
	}
	
	/**
	* Tredje genereringsregel
	* @param ord er identisk ordet X, men en bokstav er fjernet
	*/
	public String[] similarTree(String ord) {
		startTime = System.nanoTime();
		char[] ordArray = ord.toCharArray();
    	char[] tmp = new char[ordArray.length + 1];
    	String[] words = new String[ordArray.length*29];
    	
		for(int i = 0; i < ordArray.length + 1; i++) {
			for(int j = 0; j < ordArray.length; j++) {
				if(j < i) {
					tmp[j] = ordArray[j];
				} else {
					tmp[j+1] = ordArray[j];
				}
			}
			for(char a : alfa){
				tmp[i] = a;
				words[i] = new String(tmp);
			}
		}
		estimatedTime = System.nanoTime() - startTime;
		System.out.println("Det ble brukt " + estimatedTime + " nanosekund på å finne disse ordene:");
		for(int i = 0; i < words.length - 1; i++) {
			if(words[i] != null) { 
				System.out.println(words[i]); 
			}
		}
		return words;
	}
	
	/**
	* Fjerde genereringsregel
	* @param ord er identisk med ordet X, men en bokstav er lagt til
	*/
	public String[] similarFour(String ord) {
		startTime = System.nanoTime();
		
		char[] ordArray = ord.toCharArray();
		String[] words = new String[ordArray.length]; //like ord
		char[] tmp = new char[ordArray.length-1];

		for (int i=0; i<ordArray.length; i++) {
			for (int j=0; j<ordArray.length-1; j++) {
				if (j<i) {
					tmp[j] = ordArray[j];
				} else {
					tmp[j] = ordArray[j+1];
				}
			}
			words[i] = new String(tmp);
		}
		estimatedTime = System.nanoTime() - startTime;
		System.out.println("Det ble brukt " + estimatedTime + " nanosekund på å finne disse ordene:");
		for(int i = 0; i < words.length - 1; i++) {
			if(words[i] != null) { 
				System.out.println(words[i]); 
			}
		}
		return words;
	}
}

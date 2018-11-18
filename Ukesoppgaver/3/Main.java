//Ukesoppgave3
/**3.7 Main for BinaryHashMap-filen */

class Main {

	public static void main (String[] args){
		int how_many_reps = 100000;
		long t_0, t_1, t_2;

		String[] names = {"Lisa", "Bart", "Homer", "Marge","Ned", "Maude", "Maggie", "Rod", "Todd",
						"Lisa", "Bart", "Kirk", "Milhouse", "Ned", "Maude", "Maggie", "Marge",
						"Sanjay", "Apu", "Clancy", "Ralph"};
	
		// Java is fast these days but multiple executions
		// will provide some useful statistics
		Main m = new Main();
		t_0 = System.currentTimeMillis();

		for(int i = 0; i < how_many_reps; i++){
			m.fillUpHashTable(names);
		}

		t_1 = System.currentTimeMillis();

		for(int i = 0; i < how_many_reps; i++){
			m.fillUpList(names);
		}

		t_2 = System.currentTimeMillis();


		System.out.println("time used by BinaryHashMap:   "+(t_1 - t_0)+" ms");
		System.out.println("time used by List         :   "+(t_2 - t_1)+" ms");


		String[] keys;
		Object[] elms;

		BinaryHashMap htable = m.fillUpHashTable(names);
		//System.out.println(htable);

		keys = htable.keys();
		for(String s: keys){
			System.out.println(s);
		}
	
		elms = htable.toArray();
		for(Object o : elms){
		}
	
		htable.remove("Lisa");
		htable.remove("Clancy");
		System.out.println(htable);
		
	}


	public BinaryHashMap fillUpHashTable(String[] names){

		BinaryHashMap hashTable = new BinaryHashMap();
		Person p;

		for(int i = 0; i < names.length; i++){
			p = new Person(names[i], i);
			hashTable.put(names[i], p);
		}
	
		return hashTable;
		//System.out.println(hashTable);

	}

	public BHList fillUpList(String[] names){

		Person p;
		BHList root = new BHList("Dummy", new Person("Dummy", -11));
		BHList last = root;

		for(int i = 0; i < names.length; i++){
		
			p = new Person(names[i], i);
			
			if(root.hasKey(names[i])){
				root.replace(names[i], p);
			}else{
				last.next = new BHList(names[i], p);
				last = last.next;
			}

		}
		
		return root.next;
		//System.out.println(root);
	}
}

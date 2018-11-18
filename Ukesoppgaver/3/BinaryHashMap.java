class BinaryHashMap {

	private BHList[] elements;
	private int size;

	BinaryHashMap(){
		elements = new BHList[2];
		size = 0;
	}

	public void put(String key, Object element){

		int hashValue = getHashValue(key);
		
		if(elements[hashValue] == null){

			elements[hashValue] = new BHList();
			elements[hashValue].addKey(key);
			elements[hashValue].addElement(element);

		
		}else if(elements[hashValue].hasKey(key)){

			 elements[hashValue].replace(key, element);		
			 return; //avoid increasing size when its unchanged

		}else{
			// insert at start of list to speed things up
			
			BHList old_root = elements[hashValue];
			BHList fresh_root = new BHList(key, element);

			fresh_root.next = old_root;
			elements[hashValue] = fresh_root;
		}

		this.size++;
	}

	public int size(){
		return this.size;
	}

	public boolean isEmpty(){
		return (this.size == 0);
	}

	/** 3.7.1: remove(String key), true dersom den finnes og fjernes, false ellers*/
	public boolean remove(String key){
		int hVal = getHashValue(key);
		
		if(elements[hVal] == null || ! elements[hVal].hasKey(key)){
			System.err.println("trying to remove element not in Hash");
			return false;
		}else if(elements[hVal].getKey().equals(key)) {//delete root-node special case
			BHList old_root = elements[hVal];
			elements[hVal]  = old_root.next;
			this.size--;
			return true;	
		}else{
			BHList father = elements[hVal].findFatherNode(key);
			if(father == null){
				System.out.println("something terrible has happend");
				return false;
			}else{
				father.next = father.next.next;
				this.size--;
				return true;
			}
		}

	}

	/** 3.7.2 String [] keys() med alle nøklene i BinaryHashMap*/
	public String[] keys(){

		String[] keys = new String[this.size];

		BHList r1 = elements[0];
		BHList r2 = elements[1];

		int i = 0;

		while(r1 != null){
			keys[i++] = r1.getKey();
			r1 = r1.next;
		}
	
		while(r2 != null){
			keys[i++] = r2.getKey();
			r2 = r2.next;
		}

		return keys;
	}
	
	/** 3.7.3 Object[] toArray(), returnerer alle verdiene fra hash-tabellen. */
	public Object[] toArray(){
		Object[] elms = new Object[this.size];

		BHList r1 = elements[0];
		BHList r2 = elements[1];

		int i = 0;

		while(r1 != null){
			elms[i++] = r1.getElement();
			r1 = r1.next;
		}
	
		while(r2 != null){
			elms[i++] = r2.getElement();
			r2 = r2.next;
		}

		return elms;
		
	}	
	/**
	* all elements hash to 0 or 1
	*/
	private int getHashValue(String str){
		return str.length() % 2;
	}

	public boolean contains(String key){
		int hVal = getHashValue(key);
		
		if(elements[hVal] == null){
			 return false;
		}else{
			return elements[hVal].hasKey(key);
		}
	}

	public Object get(String key){
		int hVal = getHashValue(key);
		return elements[hVal].get(key);
	}


	public String toString(){
		
		BHList root_1 = elements[0];
		BHList root_2 = elements[1];
		
		String r_str = "\nBinaryHashMap:\n";

		r_str += "\nList 0:\n\n";
		r_str += root_1.toString();
		r_str += "\n\nList 1:\n\n";
		r_str += root_2.toString();

		return r_str;
	}
}

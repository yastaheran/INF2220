class BHList {

	String key;
	Object element;
	BHList next;

	public BHList(){
		;
	}

	public BHList(String key, Object elm){
		this.key = key;
		this.element = elm;
	}

	public void addKey(String _key){
		this.key = _key;
	}

	public String getKey(){
		return this.key;
	}

	public void addElement(Object o){
		this.element = o;
	}

	public Object getElement(){
		return this.element;
	}

	public BHList getNext(){
		return this.next;
	}


	public boolean hasKey(String str){

		if(this.key.equals(str)){
			return true;
		}else if(this.next != null){
			return this.next.hasKey(str);
		}
		
		return false;
	}

	public void replace(String key, Object o){

		if(this.key.equals(key)){
			this.addElement(o);
			return;
		}else if(this.next != null){
			this.next.replace(key, o);
		}

	}

	public Object get(String key){
		if(this.key.equals(key)){
			return this.getElement();
		}else if(this.next != null){
			return this.next.get(key);
		}

		return null;
	}

	public BHList findFatherNode(String str){
		if(this.next != null && this.next.getKey().equals(str)){
			return this;
		}else if(this.next != null && ! this.next.getKey().equals(str)){
			return this.next.findFatherNode(str);
		}else{
			return null;
		}
	}

	public String toString(){
		String r_str = "BHList(";
		r_str += "key: "+key+", elmt: "+element.toString()+" ) ";
		
		if(this.next != null){
			r_str += "\n-->\n" + this.next.toString();
		}
		return r_str;
	}
}

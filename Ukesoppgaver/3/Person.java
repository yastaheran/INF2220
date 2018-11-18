class Person{
	String name;
	int ident;
	
	Person(String n, int id){
		this.name = n;
		this.ident = id;
	}

	public String toString(){
		return "Person( name: "+name+", id: "+ident+ " ) ";
	}
}

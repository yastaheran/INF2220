public class Kant {	
    private Kant neste; //peker til neste outedge
    private Kant nesteInEdge; 
    private Task task, precedingTask;
    
    /**
	* Konstruktør
	*/
    Kant(Task t) {
    	this.task = t; 
    	neste = null; 
    }
	/**
	* getTask metoden:
	* @return Task som skal hentes
	*/
    public Task getTask() {
    	return task; 
    }
    
    /**
	* addTask metoden:
	* @param t oppgaven som tilhører denne kanten
	*/
    public void addTask(Task t) {
    	task = t; 
    }
    
    /**
	* addBackPointer metoden:
	* @param bp er peker til forrige task
	*/
    public void addBackPointer(Task t) {
    	precedingTask = t; 
    }
    
    /**
	* getPrecedingTask metoden:
	* @return forrige task
	*/
    public Task getPrecedingTask() {
    	return precedingTask; 
    }
    
    /**
	* getNeste metoden:
	* @return neste outEdge
	*/
    public Kant getNeste() {
    	return neste; 
    }
    
    /**
	* addNeste metoden:
	* @param k neste kant som skal legges til
	*/
    public void addNeste(Kant k) {
    	neste = k;
    }
    
    /**
	* getNesteInEdge metoden:
	* @return neste inEdge
	*/
    public Kant getNesteInEdge() {
    	return nesteInEdge; 
    }
     
    /**
	* addNesteInEdge metoden:
	* @param inK neste inEdge som skal legges til
	*/
    public void addNesteInEdge(Kant in) {
    	nesteInEdge = in;
    }
    
}
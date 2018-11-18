public class Task {
    private int id, tid, staff, senesteStart, slack; 
    private int tidligsteStart = 0;
    private String navn;
    String state;//hvor state kan være lik untestet, tested, eller testing
	static int max = Integer.MAX_VALUE;
    private Kant outEdges, inEdges;
    int cntOutEdges, cntPredecessors;
	
	/**
	* Task konstruktoer #1
	* @param id Task objektets id
	* @param n Task objektets navn
	* @param tidEst Task objektets estimerte tid
	* @param manReq Task objektets manpower krav
	*/
    Task(int tID, String navn, int tidEst, int manReq) {
    	id = tID;
    	this.navn = navn; 
    	tid = tidEst;
    	staff = manReq;
    	outEdges = null;
    	inEdges = null; 
    	state = "untested";
    	senesteStart = max;
    }
	
	/**
	* Task konstruktoer #2
	*/
    Task() {
    	outEdges = null; 
    	inEdges = null; 
    	state = "untested";
    	senesteStart = max;
    }
    
    /**
	* Task konstruktoer #3
	* @param id
	*/
    Task(int tID) {
    	id = tID; 
    	navn = "dummy";
    	tid = 0;
    	senesteStart = max;
    }
	
	/**
	* oppdaterInfo metoden: oppdaterer informasjonen om Task objektet
	*/
    public void oppdaterInfo(int tID, String navn, int tidEst, int manReq) {
    	id = tID;
    	this.navn = navn; 
    	tid = tidEst;
    	staff = manReq;
    }

	/**
	* addEdge metoden: Legger til en ny outEdge
	* @param k kanten som skal legges til Task objektet
	*/
    public void addEdge(Kant kant) {
    	if (outEdges == null) {
    		cntOutEdges++;
    		outEdges = kant;
    	} else {
    		kant.addNeste(outEdges);
    		outEdges = kant; 
    		cntOutEdges++;
    	}
    }

	/**
	* addInEdge metoden: Legger til en ny inEdge
	* @param inK inEdge-en som skal legges til Task objektet
	*/
    public void addInEdge(Kant inEdge) {
    	if (inEdges == null) {
    		inEdges = inEdge;
    	} else {
    		inEdge.addNesteInEdge(inEdges);
    		inEdges = inEdge; 
    	}
    }
	
	/**
	* oppdaterSlack metoden: oppdater slack
	*/
    public void oppdaterSlack() {
    	slack = senesteStart - tidligsteStart;
    }
    
    /**
	* oppdaterStart metode: oppdaterer tidligste start for Task objektet
	* @param t tidligste starttid for Task objektet
	*/
    public void oppdaterStart(int t) {
    	if (tidligsteStart < t) {
    		tidligsteStart = t; 
    	} 
    }
    
    /**
	* oppdaterSenesteStart metode: oppdaterer seneste start for Task objektet
	* @param t seneste starttid for Task objektet
	*/
    public void oppdaterSenesteStart(int t) {
    	if (senesteStart > t) {
    		senesteStart = t;
    	} 
    }
    
    /**
	* getStartTid metoden:
	* @return tidligste start for Task objektet
	*/
    public int getStartTid() {
    	return tidligsteStart; 
    }
    
    /**
	* getSenesteStart metoden:
	* @return seneste start for Task objektet
	*/
    public int getSenesteStart() {
    	return senesteStart; 
    }
	
	/**
	* dependencies metoden:
	* @param dep antall oppgaver dette Task objektet er avhengig av
	*/
    public void dependencies(int dep) {
    	cntPredecessors = dep; 
    }
    
	/**
	* getNavn metoden:
	* @return navnet for Task objektet
	*/
    public String getNavn() {
    	return navn; 
    }
	
	/**
	* getEdges metoden:
	* @return outEdges for Task objektet
	*/
    public Kant getEdges() {
    	return outEdges; 
    }
     
	/**
	* getInEdges metoden:
	* @return inEdge-ene for Task objektet
	*/
    public Kant getInEdges() {
    	return inEdges; 
    }
    
    /**
	* endreInEdges metoden:
	* @param in inEgde-et som skal endres
	*/
    public void endreInEdges(Kant in) {
    	inEdges = in;
    }
    
    /**
	* getState metoden:
	* @return henter statusen til Task objektet
	*/
    public String getState() {
    	return state;  
    }
	
	/**
	* getTid metoden:
	* @return den estimerte tiden det tar for denne oppgaven aa bli fullfoert
	*/
    public int getTid() {
    	return tid; 
    }
    
    /**
	* getId metoden:
	* @return IDen til Task objektet
	*/
    public int getId() {
    	return id;
    }
    
	/**
	* getStaff metoden:
	* @return antall staff for Task objektet
	*/
    public int getStaff() {
    	return staff; 
    }
	
    /**
	* getSlack metoden:
	* @return slack for Task objektet
	*/
	public int getSlack() {
    	return slack;
    }
	
}
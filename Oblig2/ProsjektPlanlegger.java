import java.io.*;
import java.util.*;

public class ProsjektPlanlegger {
    static String filnavn, manPower;
    Task start;
    Task[] oppgaver;//peker array med alle oppgavene
    boolean cycleFound = false;
    int completionTime; 
	
    public static void main(String[] args) {
    	if (args.length == 2) {
    		filnavn = args[0]; 
    		manPower = args[1];
    		new ProsjektPlanlegger();
    	}
    	else {
    		System.out.println("Mangler filnavn og/eller manpower. ");
    		System.out.println("java ProsjektPlanlegger <filnavn>.txt manpower");
    	}
    }
    
	/**
	* Konstruktør
	*/
    ProsjektPlanlegger() {
    	lesFil();
    	testForCycle();
    	if (!cycleFound) {
    		makeEventNodeGraph();
    		tidligsteStart();
    		senesteStart();
    		printTasks();
    		startProsjekt();
    	}
    }
	
	/**
	* lesFil metoden: leser inn oppgave fra filen filnavn, og oppretter Task objekter
    * og tilhorende kanter i henhold til innlest info. Resultatet er en event-node graf
    * uten ekstra dummy noder/kanter hvor oppgaver er avhengig av flere andre
    * oppgaver. 
	*/
    public void lesFil() {
    	int totalTasks;
    	int tID; 
    	String navn; 
    	int tidEst; 
    	int manReq; 
    	int depEdges;//array
    	
    	try {
    		Scanner sc = new Scanner(new File(filnavn));
    		totalTasks = sc.nextInt();//totalt ant oppgaver
    		oppgaver = new Task[totalTasks];			
			
    		int tIndex = 0;
    		while(sc.hasNext()) {
				
    			tID = sc.nextInt();
    			navn = sc.next();
    			tidEst = sc.nextInt();
    			manReq = sc.nextInt();
				
    			//tester om oppgaven allerede er opprettet
    			if (oppgaver[tIndex] == null) {
    				oppgaver[tIndex] = new Task(tID, navn, tidEst, manReq); 
    			} else {
    				//oppgaven er allerede opprettet
    				oppgaver[tIndex].oppdaterInfo(tID, navn, tidEst, manReq);
    			}
    			
    			//legger til kantene dette Task objektet er avhengig av:
    			int teller = 0;
    			while((depEdges = sc.nextInt()) != 0) {
    				//sjekker om objektet task[depEdges-1] eksisterer fra for:
    				if(oppgaver[depEdges-1] == null) {
    					oppgaver[depEdges-1] = new Task();
    				}
					
    				Kant kant = new Kant(oppgaver[tIndex]);
    				//legger kant til i oppgaver[depEdges] sine kanter:
    				oppgaver[depEdges-1].addEdge(kant);
    				
    				//legger til en tilbakepeker i oppgaver[tIndex] til kant objektet som 
    				//peker paa dette objektet
    				oppgaver[tIndex].addInEdge(kant);
    				//legger til en tilbakepeker i objektet kant
    				kant.addBackPointer(oppgaver[depEdges-1]);
    				teller++;
    			}
    			//all info for denne oppgaven er naa lest inn
    			oppgaver[tIndex].dependencies(teller);
    			tIndex++;
    		}
    		//filen er ferdig lest inn
    	}
    	catch (IOException e) {
    		System.out.println("IOException : ");
    		System.out.println(e.getMessage()+".");
    	}
    }
	
	/**
	* testForCycle metoden: gaar igjennom alle objekter i opggaver arrayen, og starter 
	* metoden cycelSearch for hver metode som ikke er testet
	* tidligere
	*/
    public void testForCycle()  {
    	for (Task t: oppgaver) {
    		if (t.state != "tested" && !cycleFound) {
    			cycleSearch(t);
    		}
    	}
    	System.out.println("");
    }
    
    /**
	* cycleSearch metoden: gaar igjennom alle etterfolgere til t rekursivt, og ser om
	* om noen av disse danner en loekke, om det finnes en loekke settes cycleFound = true
	* og rekursonen stopper. Loekken som er funnet skrives til slutt ut.
	* @param t Task objektet som undersokes
	*/
    public void cycleSearch(Task t) {
    	if (t.state == "testing") {
    		cycleFound = true; 
    		System.out.println("Prosjektet kan ikke gjennomfores. ");
    		System.out.print("Funnet lokke: ");
    		System.out.print(t.getId());
    	} else if (t.state == "untested" && !cycleFound) {
    		t.state = "testing";
    		Kant kant = t.getEdges();
    		
    		//gaar igjennom alle Task objekter som er avhengig av oppgave t
    		while (kant != null && !cycleFound) {
    			cycleSearch(kant.getTask());
    			kant = kant.getNeste();
    		}
    		t.state = "tested";
    		//skriver ut lokken som evt er funnet:
    		if (cycleFound) {
    			System.out.print(" <-- " + t.getId());
    		}
    	}
    }
	
	/**
    * makeEventNodeGraph  metoden: gjoer om grafen fra activity-node graf til 
    * event-node graf. 
    */
    public void makeEventNodeGraph() {
    	start = new Task();//peker til forste Task i grafen
    	for (Task t : oppgaver) {
    		if (t.cntPredecessors == 0)  {
    			start.addEdge(new Kant(t));
    		}
    		else if (t.cntPredecessors > 1 ) {
    			Task tmpTask = new Task(t.getId());
    			Kant tmpKant = new Kant(t);
    			tmpTask.addEdge(tmpKant);
    			Kant inEdge = t.getInEdges();
    			//endrer alle inEdge (kanter som peker paa t)
    			//til aa peke paa tmpTask isteden og endrer inedgenes timevariabel til 0:
    			while (inEdge != null) {
    				//inEdge.task = tmpTask; rettet
    				inEdge.addTask(tmpTask);
    				//inEdge.changeTime(0);
    				inEdge = inEdge.getNesteInEdge();
    			}	
    			tmpTask.endreInEdges(t.getInEdges());
    			t.endreInEdges(tmpKant);
    			tmpKant.addBackPointer(tmpTask);
    			tmpTask.cntPredecessors = t.cntPredecessors;
    			t.cntPredecessors = 1; 
    			
    		}
    	}
    }
	
	/**
    * tidligsteStart metoden: regner ut (i topologisk rekkefoelge) tidligste startverdien
    * for alle Task objekter.
    */
    public void tidligsteStart() {
    	Liste list = new Liste();
    	Task forrige;//Task objekt med inngrad 0, og med naboene current
    	Task current;//nabo til forrige
    	Task siste = null;//siste Task objekt i grafen. 
    	Kant kant = start.getEdges();
    	
    	//legger inn alle Task objekter som start peker paa inn i en liste: 
    	while (kant != null) {
    		current = kant.getTask();
    		current.oppdaterStart(0);
    		list.push(current);
    		kant = kant.getNeste(); 
    	}
    	
    	int startTid;//earlistStart tid til current
    	forrige = list.pop();
    	while (forrige != null) {
    		kant = forrige.getEdges();
    		//gaar igjennom alle naboer til current: 
    		while (kant != null) {
    			current = kant.getTask();
    			startTid = forrige.getTid() + forrige.getStartTid();
    			current.oppdaterStart(startTid);
    			if (--current.cntPredecessors == 0) {
    				list.push(current);
    			}
    			kant = kant.getNeste();
    		}
    		siste = forrige; 
    		forrige = list.pop();//tar ut en ny 'task' fra list
    		
    	}
    	completionTime = siste.getTid() + siste.getStartTid();
    }
	
	/**
    * senesteStart metoden: gaar igjennom grafen topologisk, men baklengs for 
    * aa finne seneste tiden en task kan begynnes paa uten aa forsinke prosjektet.
    */
    public void senesteStart() {
    	Liste list = new Liste();
    	Task forrige;
    	Task current;
    	Kant kant;
    	
    	//finner alle task obj som ikke har naboer:
    	for (Task t: oppgaver) {
    		kant = t.getEdges();
    		if (kant == null) {
    			t.oppdaterSenesteStart(completionTime-t.getTid());
    			t.oppdaterSlack();//regner ut slack i t
    			list.push(t);
    		} 
    	}
    	int startTid;
    	forrige = list.pop();
    	while (forrige != null) {
    		kant = forrige.getInEdges();
    		while (kant != null) {
    			current = kant.getPrecedingTask();
    			startTid = forrige.getSenesteStart() - current.getTid();
    			current.oppdaterSenesteStart(startTid);
    			current.oppdaterSlack();
    			if (--current.cntOutEdges == 0) {
    				list.push(current);
    			}
    			kant = kant.getNesteInEdge();
    		}
    		forrige = list.pop();
    	}
    }
    
    /**
    * printTasks metoden: skriver ut informasjon om alle Task objektene.
    */
    public void printTasks() {
    	Task depTask;
    	Kant e;
    	try {
    		PrintWriter pw = new PrintWriter("output.txt");
    		for(Task t : oppgaver) {
    			//skriver til utskriftsfilen foerst
    			pw.println("---------------------------------");
	    		pw.println("Task: Identity number: " + t.getId());
    			pw.println("---------------------------------");
    			pw.println("Name: " + t.getNavn());
    			pw.println("Time to finish this task: " + t.getTid());
	    		pw.println("Manpower required: " + t.getStaff());
    			pw.println("Slack: " + t.getSlack());
    			pw.println("Latest start time: " + t.getSenesteStart());
    			pw.println("Tasks depending on this task: ");
    			
    			//skriver saa ut til skjermen
    			System.out.println("---------------------------------");
   	 			System.out.println("Task: Identity number: "+ t.getId());
    			System.out.println("---------------------------------");
    			System.out.println("Name: "+ t.getNavn());
    			System.out.println("Time to finish this task: "+t.getTid());
    			System.out.println("Manpower required: "+t.getStaff());
    			System.out.println("Slack: "+ t.getSlack());
    			System.out.println("Latest start time: "+ t.getSenesteStart());
    			e = t.getEdges();
    			System.out.println("Tasks depending on this task: ");
    			
    			while (e != null) {
    				depTask = e.getTask();
    				pw.println("Task: " + depTask.getId());
    				System.out.println("Task: " + depTask.getId());
    				e = e.getNeste();
    			}
    			pw.println("");
    			System.out.println("");
    		}
    		pw.println("---------------------------------");
     		pw.println("");
    		System.out.println("---------------------------------");
    		System.out.println("");
    	} catch(FileNotFoundException ex) {
    		System.out.println("Error");
    	} 
    }

	/**
    * startProsjekt metoden: gaar igjennom grafen topologisk, hvor alle paabegynte
    * task legges i listen liste, sorteres etter naar de er ferdige. Tasks som begynner,
    * legges inn i startTasks listen, og ferdige task legges inn i ferdigTask.
    * Disse skrives deretter ut i printTasks.
    */
    public void startProsjekt() {
    	Liste list = new Liste();
    	Liste starterT = new Liste();
    	Liste ferdigT = new Liste();
    	int time = 0;
    	int forrigeTid;
    	Task forrige = start;//avsluttet task
    	Task current;//naboene til forrige
    	Kant kant; 
    	int manpower = 0;
    	
    	while (forrige != null) {
    		kant = forrige.getEdges();
    		forrigeTid = time;
    		time = forrige.getTid() + forrige.getStartTid();
    		
    		if (forrigeTid != time ) {
    			printLister(forrigeTid, starterT, ferdigT, manpower);
    		}
    		manpower -= forrige.getStaff();
    		if (forrige != start) {
    			ferdigT.push(forrige);
    		}
    		//gaar igjennom naboene til forrige og ser om noen er klare til aa starte:
    		while (kant != null) {
    			current = kant.getTask();
    			if (time == current.getStartTid()) {
    				//hopper over eventuelle dummy noder: 
    				if (current.getNavn() == "dummy") {
    					Kant e = current.getEdges();
    					current = e.getTask();
    				}
    				manpower += current.getStaff();
    				list.pushSorted(current);
    				starterT.push(current);
    			}
    			kant = kant.getNeste();
    		}
    		forrige = list.pop();
    		
    	}
    	printLister(time, starterT, ferdigT, manpower);
    	System.out.print("**** Shortest possible project execution is " + completionTime + " ****");
    }
    
    /**
    * printLister metoden: Ferdige og startede task objekter ved tiden tid skrives ut.
    * @param tid 
    * @param s liste av task objektene som er startet 
    * @param f liste av task objekter som er ferdige
    * @param mpow antall manpower i arbeid ved tid time
    */
    public  void printLister(int tid, Liste s, Liste f, int mpow ) {
    	Task starter = s.pop();
    	Task ferdig = f.pop();
    	
    	if (starter != null || ferdig != null) {
    		System.out.println("Time: " + tid);
    		//skriver ut task som er ferdige:
    		while (ferdig != null) {
    			System.out.println("        Finished: " + ferdig.getId());
    			ferdig = f.pop();
    		}
    		//skriver ut tasks som begynner:
    		while (starter != null) {
    			System.out.println("        Starting: " + starter.getId());
    			starter = s.pop();
    		}
    		System.out.println("   Current staff: " + mpow);
    	} 
    }
}





/**
* Liste klassen er en FIFO lenkeliste
*/
public class Liste {
	Node forste = null; 
	Node siste = null; 
	
	class Node {
		Node neste;
		Task task;
		
		Node(Task t) {
			task = t; 
		}
	}
	
	/**
	* push metoden: Et Task objekt settes inn bakers i lenkelisten
	* @param t objekt som settes inn
	*/
	public void push(Task t) {
		Node ny = new Node(t);
		if (forste == null) {
			forste = ny;
			siste = ny; 
		} else {
			siste.neste = ny;
			siste = ny; 
		}
	}
	
	/**
	* pushSorted metoden: Legger t inn i listen, sortert etter naar task tidligst
	* kan vaere ferdig med sin oppgave
	* @param t objektet som settes inn
	*/
	public void pushSorted(Task t) {
		Node ny = new Node(t);
		Node current = forste; 
		Task c; 
		Node forrige = null;
		int cEndTime;
		int tEndTime = t.getStartTid() + t.getTid();
		while (current != null) {
			c = current.task;
			cEndTime = c.getStartTid() + c.getTid();
			if (cEndTime < tEndTime) {
				forrige = current;
				current = current.neste;
			} else break;
		}
		if (current == forste) {
			forste = ny; 
			forste.neste = current;
		} else if (current == null)  {
			forrige.neste = ny;
		} else {
			forrige.neste = ny;
			 ny.neste = current; 
		}
	}
	
	/**
	* pop metoden: foerste elementet i listen returneres, og denne sin neste settes
	* til aa vaere foerste
	* @return task objektet, eller null om listen er tom
	*/
	public Task pop() {
		Node ny = forste;
		if (forste != null) {
			forste = forste.neste; 
			return ny.task; 
		} 
		return null; 
	}
}

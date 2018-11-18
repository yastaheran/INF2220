import java.util.*;
import java.io.*;

/* klassen skal lese inn filen fra kommandolinjen for så å kalle på en metode som finner nålen i høystakken*/
public class Oblig3 {
	private static String needle, haystack;
	
	/**
	* MAIN metoden:
	* @param args en array med argumentene fra kommandolinjen
	*/ 
    public static void main(String[] args){
    	ArrayList<Integer> match = null;
    //	Hashmap<Integer, String> match = new HashMap<Integer,String>();
    
       if(args.length != 2) {
          System.out.println("BRUK: >java Assigment3 <needle>.txt <haystack>.txt");
          System.exit(1);
       } else if(args.length == 2) {
          	needle = lesFil(args[0]); //nålen
          	haystack = lesFil(args[1]); //høystakken
       }
       
       if(needle == null || haystack == null) {
       		System.out.println("needle eller/og haystack er tom");
			return;
       }
       
       match = boyermoore(needle, haystack);
       printMatches(match, needle.length(), haystack);
    }
    
    /**
    * LESFIL metoden: leser filen ved hjelp av BufferedReader og FileReader
    * @param fil navnet på filen som skal leses
    * @return første linje i teksten som har blitt lest
    */
    public static String lesFil(String fil) {
    	try {
    		int nread;
    		String output = null; //Stringen som senere skal returneres
    		char[] inbuffer =  new char[1024*1024];
    		BufferedReader br = new BufferedReader(new FileReader(fil));
    		while((nread = br.read(inbuffer, 0, inbuffer.length)) > 0 ) {
    			if(output == null) {
    				output =  new String(inbuffer, 0, nread);
    			} else {
    				output =  output + new String(inbuffer, 0, nread);
    			}
    		}
    		br.close(); //lukker bufferen når jeg er ferdig med den
    		return output;
    	} catch(IOException e) {
    		System.err.println("IOException " + e.getMessage());
    		return null;
    	}
    }
    
    /**
    * BOYERM metoden: modifisert versjon av boyer moore algoritmen som kan håndtere
    * wildcards.
    * Best case: O(h/n) - hvis det ikke er noen like bokstaver eller wildcards
    * Worst case: O(h^2) og O(n^2) - når nålen er 1/2 av høystakken
    * Regne ut shift for offset i while-løkke -> O(k)
    * @param n stringen som er nålen
    * @param h stringen som er høystakken
    * @return array med offsets i høystakken
    */
    public static ArrayList<Integer> boyermoore(String n, String h){
    	int badCharacterShift[] = bmBadCharacterShitf(n);
    	int offset = 0;
    	long iter = 0; //runtime variabel
    	ArrayList<Integer> resultat =  new ArrayList<Integer>();
    	
    	while(offset + n.length() <= h.length()) {
    		int shift = badCharacterShift[h.charAt(offset + n.length() - 1)];
    		int i;
    		
    		for(i = n.length() - 1; i >= 0; i--) {
    			char c = n.charAt(i);
    			iter++;
    			if(c != '_' && c != h.charAt(offset + i)) {
    				offset += shift;
    				break;
    			}
    		}
    		
    		if(i < 0) { //hvis ingen mismatch under iterasjon --> lagre offset og shift
    			resultat.add(offset);
    			offset += shift;
    		}
    	}
        System.err.println("iterations: " + iter);
        System.err.println("needle:     " + needle.length());
        System.err.println("haystack:   " + haystack.length());
        return resultat;
    }
	
	/**
	* BMBADCHARACTERSHIFT metoden: generer bcs array som brukes i boyermoore metoden.
	* Kjøretiden er O(n.length). Siste for-løkke finner siste wildcard og setter standard 
	* shift til å være på samme linje. Boyer bremser ned jo nærmere wildcardet er til 
	* slutten av strengen, unntatt siste char. Hvis den nest siste char av nålen er 
	* et wildcard, så mister vi bcs.
	* @param n nålen
	* @return badCharacterShift array
	*/
	public static int[] bmBadCharacterShitf(String n) {
		int[] bcs = new int[256];
		int i;
		
		for(i = 0; i < bcs.length; i++) {
			bcs[i] = n.length();
		}
		
		for(i = 0; i < n.length() - 1; i++) {
			bcs[(int) n.charAt(i)] = n.length() - i -1;
		}

		for(i = n.length() - 2; i >= 0; i--) {
			if(n.charAt(i) == '_') {
				int shift = n.length() - i - 1;
				for (int j = 0; j < bcs.length; j++) {
					if(bcs[j] > shift) {
						bcs[j] = shift;
					}
				}
				break;
			}
		}
		
		return bcs;
	}

	/**
	* PRINTMATCHES metoden: printer ut alle substringene som matchet mellom needle og
	* haystack. Denne metoden tar ikke for seg at det er flere matcher eller større
	* lengde som er større enn lengden til høystakken.
	* @param m liste med match
	* @param l lengden på nålen
	* @param h høystakken som er blitt undersøkt
	*/
    public static void printMatches(ArrayList<Integer> m, int l, String h) {
    	for(int offset: m) {
    		System.out.println(offset + ": '" + haystack.substring(offset, offset + l) + "'");
    	}
    }
}


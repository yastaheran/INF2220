import java.util.*;
import java.io.*;

public class Ordbok {
	private static BinartSokeTre bst = new BinartSokeTre();
	private static String ord;
	private static String[] sm1, sm2, sm3, sm4;
	
	
	public static void main(String[] args){
		lesFil();
		kommandoLinje();
	}

	public static void kommandoLinje() {
		Scanner leser = new Scanner(System.in); //tar imot input fra brukeren
		while(true) { //loekken blir kjoert saa lenge kommandoen q ikke er gitt
			System.out.println("Velg en Kommando");
			System.out.println("Sok - for sok av ord");
			System.out.println("Slett -  for sletting av ord");
			System.out.println("Q - for avlsutt");
			System.out.print("Kommando ");
			String k = leser.nextLine();
			if(k.equalsIgnoreCase("Q")) {
				skrivFil();
				return;
			} else if(k.equalsIgnoreCase("Sok")) {
				System.out.print("Soke etter ord: ");
				String ord = leser.nextLine();
			//	bst.sok(k, bst.rotNode);
				bst.sok(ord);
			} else if(k.equalsIgnoreCase("Slett")) {
				System.out.print("Slette ord: ");
				String ord = leser.nextLine();
			//	bst.fjern(ord, bst.rotNode);
				bst.fjern(ord);
			} else {
				System.out.println("Ugyldig kommando\n");
			}
		}	
	}
	
	/**
	* Metoden skal lese fra filen dictionary.txt
	*/
	public static void lesFil() {
		try {
			File fil = new File("dictionary.txt");
			Scanner reader = new Scanner(fil);
			ord = reader.nextLine();
			
            while(reader.hasNextLine()) {
   				ord = reader.nextLine();
		//		bst.settInn(ord, bst.rotNode);
      			bst.settInn(ord);
           }
			reader.close();
			System.out.println("Filen er lest");
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
		}
	}
	
	/**
	* Metoden skal skrive til filen utskrift.txt
	*/
	public static void skrivFil() {
		try {
			PrintWriter pw = new PrintWriter("utskrift.txt");
			pw.println("Dybden på treet er " + bst.beregnDybdeMax(bst.rotNode, 0));
			pw.println("Det er " + bst.beregnDybdePrNode(bst.rotNode, 0) + " noder pr dybde");
			pw.println("Gjennomsnittsdybden på alle nodene er " + bst.gjennomsnittsDybde(bst.rotNode, 0));
			pw.println("Første ordet: " + bst.getFirst());
			pw.println("Siste ordet: " + bst.getLast());
			
			//Spell-Check 1
			pw.println("Spell-check av ordet achieve");
			int antMatch = 0;
			sm1 = bst.similarOne("achieve");
			for(int i = 0; i < sm1.length; i++){
				if(sm1[i] != null) {
					pw.println(sm1[i] + "\n");
					antMatch++;
				}
			}
			pw.println("Antall Match " + antMatch);
			
			//Spell-Check 2
			pw.println("Spell-check av ordet achiese");
			antMatch = 0;
			sm2 = bst.similarTwo("achiese");
			for(int i = 0; i < sm2.length; i++){
				if(sm2[i] != null) {
					pw.println(sm2[i] + "\n");
					antMatch++;
				}
			}
			pw.println("Antall Match " + antMatch);
			
			//Spell-Check 3
			pw.println("Spell-check av ordet ahcieve");
			antMatch = 0;
			sm3 = bst.similarTree("achiese");
			for(int i = 0; i < sm3.length; i++){
				if(sm3[i] != null) {
					pw.println(sm3[i] + "\n");
					antMatch++;
				}
			}
			pw.println("Antall match " + antMatch);
			
			//Spell-Check 4
			pw.println("Spell-check av ordet achievee");
			antMatch = 0;
			sm4 = bst.similarFour("achievee");
			for(int i = 0; i < sm4.length; i++){
				pw.println(sm4[i] + "\n");
				antMatch++;
			}
			pw.println("Antall match " + antMatch);
			
			pw.close();
		} catch (IOException e) {
			System.out.println("Error reading file");                  
		}
	}
}

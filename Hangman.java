/**
* En Hangman hvor ordet blir lagt til i terminal, hvis random = et tilfeldig ord velges
* av programmet
*/

import java.util.Scanner;
import java.util.Random;

public class Hangman {
	private int liv = 6;
	private ferdig = false;
	//Ordene for "random"
	private static String [] randomWords = 	{"geography", "cat", "yesterday", "java", "truck", "opportunity",
											 "fish", "token", "transportation", "bottom", "apple", "cake",
											 "remote", "pocket", "terminology", "arm", "cranberry", "tool",
											 "caterpillar", "spoon", "watermelon", "laptop", "toe", "toad",
											 "fundamental", "capitol", "garbage", "anticipate", "apple"};
	/** Main-metoden */
	public static void main(String[] args) {
		String secretWord;
		
		if(args.length != 1) {
			System.out.println("Usage: <secretword> \n enter "random" for a random word ");
			return -1;
		}
		if(args[0].equalsIgnoreCase("random")) { //random ord
			Random randomI =  new Randdom();
			int i = randIndex.nextInt(randomWords.length);
			secretWord = randomWords[i];
		} else {
			secretWord = args[0];
		}
		startSpill(secretWord);
	}
	
	/** startSpill: starter spillet*/
	public static void startSpill(String ord) {
		int antallBokstaver = ord.length;
		StringBuffer dashes = lagDash(ord);
		String gjettet, gjettetBokstaver; //gjettetBokstaver = bokstaver allerede gjettet
		char bokstav;
		Scanner in = new Scanner(System.out);
		
		while(!ferdig) {
			System.out.println("Guess word: " + dashes);
			System.out.println("Antall liv igjen: " + liv);
			System.out.print("Gjett bokstav eller ord: ");
			gjettet = in.next();
			
			if(gjettet.length > 1) { //dersom det er et helt ord som blir gjettet
				if(gjettet.equalsIgnoreCase(secretWord)) { System.out.println("Du vant"); }
				else {System.out.println("Du tapte"); }
				ferdig = true;
			} else { //en bokstav blir gjettet
				bokstav = gjettet.charAt(0);
				gjettetBokstaver += bokstav;
				if(ord.indexOf(bokstav) < 0) {
					liv --;
					System.out.println("bad guess -");
				} else {
					matchLetter(ord, dashes, letter);
				}
				
				System.out.println("Bodyparts left: " + liv);
				if(liv == 0) { System.out.println("You lost"); done = true; }
				if(ord.equals(dashes.toString())) { System.out.println("You won"); done = true;}
			}
		}
	}
	
	/** matchLetter: sjekker om bokstaven matcher en eller flere av bokstavene */
	public static void matchLetter(String s, StringBuffer d, char l) {
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) == l) d.setCharAt(index, letter);
		}
		System.out.println("good guess");
	}
	
	/** lagDash: lager linjer som skal representere hver bokstav i ordet*/
	public static StringBuffer lagDash(String ord) {
		StringBuffer d = new StringBuffer(ord.length());
		for(int i = 0; i < ord.length(); i++)
			dashes.append("-");
		return d;
	}
}
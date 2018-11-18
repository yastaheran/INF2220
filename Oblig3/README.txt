Explanation of algorithm and why it works
	Jeg antar at min versjon av Boyer Moore algoritmen fungerer.
	Wildcardene blir behandlet som bokstaver, og den matcher alle bokstaver. BCS er laget
	slik at første bokstaven i høystakken som ikke matchet nålen er på samme linje. 
	Dersom det finnes flere like bokstaver i nålen, velges den som er nærmest slutten av
	stringen. 
	
How to compile my program
	javac Oblig3.java
	Denne filen inneholder main-metoden.
			
Any assumptions done during implementation and pecularities	
	har antatt at java sine integers er store nok for nålen og høystakken.	
	
Testene
	Test1: nål -> test1.txt og høystakk -> test1h.txt
		   enkel string, med en wildcard
		   output: 0: 'aab', 3: 'abb', 7: 'acb', 12: 'adb', 18: 'aeb', 25: 'afb', 33: 'agb'
	Test2: nål -> test2.txt og høystakk -> test2h.txt
		   enkel string, uten wildcard
		   output: 0, 3, 7, 12, 18, 25, 33: 'ab'
	Test3: nål -> test3.txt og høystakk -> test3h.txt
		   3 wildcards
		   output: 0: 'hel', 1: 'ell', 2: 'llo', 3: 'lob', 4: 'oba', 5: 'bal'
				   6: 'all', 7: 'llo', 8: 'lot', 9: 'ota', 10: 'tak', 11: 'akk'
				   12: 'kkk', 13: 'kka', 14: 'kan', 15: 'ans', 16: 'nsk', 17: 'skj'
				   18: 'kje'
	Test4: nål -> test4.txt og høystakk -> test4h.txt
		   4 wildcards
		   output: 14: 'operaluset', 29: 'operahuset'
	Test5: nål -> test5.txt og høystakk -> test5h.txt
		   begge filene er tomme, fører til at programmet avlsutter

Status	
	Alt ser ut til å fungere
	
Credit
	foiler fra forelesningene
	java-biblioteket
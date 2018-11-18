import inf101.*;
public class Ibsen {
        public static void main(String[] args) {
            String ord;
            int antallOrd = 0;

            String sep = ". :,;-?!\"";
            Inn innfil = new Inn(args[0]);
            IbsenTre tre = new IbsenTre();

            innfil.skipSep(sep);
            while (!innfil.endOfFile()) {
                ord = innfil.inString(sep).toLowerCase();
                tre.settInn(new IbsenElem(ord));
                antallOrd++;
                innfil.skipSep(sep);
            }

            System.out.println("Totalt antall ord: " + antallOrd);
            System.out.println("Antall ulike ord: " + tre.size());

            IbsenFrekTre frekTre = new IbsenFrekTre();
            frekTre.innsetting(tre.getRot());

            frekTre.skrivInnfiks();
        }
}

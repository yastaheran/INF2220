//  Klasse for � ta vare p� ordene og telle opp antall forekomster:
class IbsenElem implements Comparable {
        String ord; int antall;

        IbsenElem (String s) {
            ord = s; antall = 1;
        }

        public int compareTo(Object x) {
            IbsenElem e = (IbsenElem) x;
            return ord.compareTo(e.ord);
        }

        public String toString() {
            return (ord + " " + antall);
        }
}

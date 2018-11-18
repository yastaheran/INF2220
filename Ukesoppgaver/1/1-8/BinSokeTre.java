//  Vanlig binært søketre der "noe gjøres" ved like elementer.
// (Fordi klassen er public, skulle den ha ligget på en egen fil.
//  Fjern ordet "public" på neste linje hvis du vil kompilere filen.)
public class BinSokeTre {
        protected BinNode rot;
        protected int antallNoder = 0;

        private void settInn(Comparable x, BinNode n) {
            int i = sammenlign(x, n.element);
            if (i < 0) {
                if (n.venstre == null) {
                    n.venstre = new BinNode(x);
                    antallNoder++;
                } else {
                    settInn(x, n.venstre);
                }
            } else if (i > 0) {
                if (n.hoyre == null) {
                    n.hoyre = new BinNode(x);
                    antallNoder++;
                } else {
                    settInn(x, n.hoyre);
                }   
            } else {
                oppdater(n, x);
            }
        }

        public void settInn(Comparable x) {
            if (rot == null) {
                rot = new BinNode(x);
                antallNoder++;
            } else {
                settInn(x, rot);
            }
        }

        protected void oppdater(BinNode n, Comparable x) {
            // Default: Ingenting gjoeres for like elementer.
        }

        protected int sammenlign(Comparable n1, Comparable n2) {
            return n1.compareTo(n2);
        }

        public void skrivInnfiks() {
            innfiks(rot);
        }
  
        private void innfiks(BinNode n) {
            if (n != null) {
                innfiks(n.venstre);
                System.out.println(n.element.toString());
                innfiks(n.hoyre);
            }
        }

        public int size() {
            return antallNoder;
        }

        public BinNode getRot() {
            return rot;
        }

}   // slutt class BinSokeTre

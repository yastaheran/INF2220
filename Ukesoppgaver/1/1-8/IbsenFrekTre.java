
class IbsenFrekTre extends BinSokeTre  {

        protected int sammenlign(Comparable c1, Comparable c2) {

            IbsenElem e1 = (IbsenElem) c1;
            IbsenElem e2 = (IbsenElem) c2;

            return e1.antall - e2.antall;
        }

        protected void oppdater(BinNode n, Comparable e) {
            if (n.venstre == null) {
	        n.venstre = new BinNode(e);
            } else {
	        BinNode b = new BinNode(e);
	        b.venstre = n.venstre;
                n.venstre = b;
            }
            antallNoder++;
        }

        public void innsetting(BinNode n) {
            if (n != null) {
                settInn(n.element);
                innsetting(n.venstre);
                innsetting(n.hoyre);
            }
        }
}

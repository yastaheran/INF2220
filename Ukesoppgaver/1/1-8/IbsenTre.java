class IbsenTre extends BinSokeTre {

        protected void oppdater(BinNode n, Comparable e) {
            IbsenElem ie = (IbsenElem) n.element;
            ie.antall++;
        }

}


//Ukesoppgave 2.5
/** 2.5.1 number(BinNode t) gir antall noder */
puplic int number(BinNode t) {
	int sum, v, h;
	if(t == null) {
		return 0;
	} else {
		v = number(t.left);
		h = number(t.right);
	}
	sum = v + h + 1;
	return sum;
}

/** 2.5.2 sum(BinNode t) gir summen av data verdiene i alle nodene i treet.*/
public int sum(BinNode t) {
	int sum, v, h;
	if(t == null) {
		return 0;
	} else {
		v = sum(t.left);
		h = sum(t.right);
	}
	sum = t.data + v + h;
	return sum;
}

/** 2.6 sum og number fra oppgave 5, skrevet inne i selve BinNode-klassen */
puplic int number() {
	int sum, v, h;
	if(this == null) {
		return 0;
	} else {
		v = number(this.left);
		h = number(this.right);
	}
	sum = v + h + 1;
	return sum;
}
public int sum(BinNode t) {
	int sum, v, h;
	if(this == null) {
		return 0;
	} else {
		v = sum(this.left);
		h = sum(this.right);
	}
	sum = this.data + v + h;
	return sum;
}

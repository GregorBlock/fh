package aufgabe1;

import java.util.ArrayList;

public class Model {

	private Person person;
	private ArrayList<Distance> l1;
	private ArrayList<Distance> l2;
	private ArrayList<Distance> lInfinity;
	
	public Model(Person person, ArrayList<Distance> l1, ArrayList<Distance> l2,
			ArrayList<Distance> lInfinity) {
		this.l1 = l1;
		this.l2 = l2;
		this.lInfinity = lInfinity;
		this.person = person;
	}

	public Person getPerson() {
		return person;
	}

	public ArrayList<Distance> getL1() {
		return l1;
	}

	public ArrayList<Distance> getL2() {
		return l2;
	}

	public ArrayList<Distance> getlInfinity() {
		return lInfinity;
	}
}

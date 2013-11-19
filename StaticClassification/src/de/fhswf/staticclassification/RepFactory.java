/**
 * 
 */
package de.fhswf.staticclassification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author Natalie Block
 * 
 */
public final class RepFactory {

	public enum PersonKlasifikator {
		normal(0), overweight(1);

		private int klassifikation;

		PersonKlasifikator(int klassifikation) {
			this.klassifikation = klassifikation;
		}

		public int getKlassifikation() {
			return klassifikation;
		}
	}

	public enum RepType {
		Avarage, Median;
	}

	private RepFactory() {
	}

	public static Rep createRep(RepType repType) {
		switch (repType) {
			case Avarage :
				return new AvarageRep();
			case Median :
				return new MedianRep();
			default :
				throw new IllegalArgumentException("Falscher Rep Typ");

		}
	}

	public interface Rep {
		public Person findRep(PersonKlasifikator personKlasifikator,
				DatabaseArrayList database);
	}

	public static class MedianRep implements Rep {

		@Override
		public Person findRep(PersonKlasifikator personKlasifikator,
				DatabaseArrayList database) {

			ArrayList<Person> selectedPerson = database
					.selectPersonOf(personKlasifikator);

			// Sortieren

			Comparator<Person> cmp = new Comparator<Person>() {
				@Override
				public int compare(Person o1, Person o2) {
					return o1.getGroesse().compareTo(o2.getGroesse());
				}
			};

			Collections.sort(selectedPerson, cmp);

			if ((selectedPerson.size() % 2) == 0) {
				return selectedPerson.get(selectedPerson.size() / 2);
			} else {
				return selectedPerson.get(selectedPerson.size() + 1 / 2);
			}
		}

	}

	public static class AvarageRep implements Rep {

		@Override
		public Person findRep(PersonKlasifikator personKlasifikator,
				DatabaseArrayList database) {

			double averageGewicht = 0;
			double averageGroesse = 0;

			ArrayList<Person> selectedPerson = database
					.selectPersonOf(personKlasifikator);

			// arithmetische Mittel
			for (int i = 0; i < selectedPerson.size(); i++) {
				averageGewicht += selectedPerson.get(i).getGewicht();
				averageGroesse += selectedPerson.get(i).getGroesse();
			}

			averageGewicht /= 100;
			averageGroesse /= 100;
			return new Person(averageGroesse, averageGewicht,
					personKlasifikator);
		}

	}

}

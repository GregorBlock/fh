package de.fhswf.staticclassification;

import de.fhswf.staticclassification.RepFactory.PersonKlasifikator;
import de.fhswf.staticclassification.RepFactory.Rep;
import de.fhswf.staticclassification.RepFactory.RepType;

/**
 * @author Natalie Block
 * 
 */
public class MaximumCorrelationClassifier {

	DatabaseArrayList database;

	public MaximumCorrelationClassifier() {

		database = new DatabaseArrayList();
		calculateErrorRate(RepType.Avarage, PersonKlasifikator.normal);
		calculateErrorRate(RepType.Median, PersonKlasifikator.normal);
		calculateErrorRate(RepType.Avarage, PersonKlasifikator.overweight);
		calculateErrorRate(RepType.Median, PersonKlasifikator.overweight);

	}

	private double calculateErrorRate(RepType repType,
			PersonKlasifikator klasifikator) {

		Rep r = RepFactory.createRep(repType);
		Person rep = r.findRep(klasifikator, database);
		
		System.out.println(rep + " " + repType.toString());
		return 0;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MaximumCorrelationClassifier();
	}

}

package aufgabe1;


public class Distance {
	private Double distance;
	private Person testPerson;

	public Distance(Double distance, Person testPerson) {
		this.distance = distance;
		this.testPerson = testPerson;
	}

	public Double getDistance() {
		return distance;
	}

	public Person getTestPerson() {
		return testPerson;
	}

	@Override
	public String toString() {
		return "Model2 [distance=" + distance + ", testPerson=" + testPerson
				+ "]";
	}

}

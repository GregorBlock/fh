package aufgabe1;

public class Model {

	private Double value;
	private int indexDatabase;
	private int indexTestdata;

	public Model(Double value, int indexDatabase, int indexTestdata) {
		this.value = value;
		this.indexDatabase = indexDatabase;
		this.indexTestdata = indexTestdata;
	}

	public int getIndexDatabase() {
		return indexDatabase;
	}

	public void setIndexDatabase(int indexDatabase) {
		this.indexDatabase = indexDatabase;
	}

	public int getIndexTestdata() {
		return indexTestdata;
	}

	public void setIndexTestdata(int indexTestdata) {
		this.indexTestdata = indexTestdata;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Value: " + value + " Index: " + indexDatabase;
	}

}

package aufgabe1;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JFrame;

import de.erichseifert.gral.data.DataSeries;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.BarPlot;
import de.erichseifert.gral.plots.PlotArea;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.axes.AxisRenderer;
import de.erichseifert.gral.plots.points.DefaultPointRenderer2D;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.Insets2D;

public class LinePlotTest extends JFrame {
	private static final long serialVersionUID = 1L;
	private static ArrayList<Person> databaseList;

	public enum ix {
		L1, L2, LInfinity;
	}

	@SuppressWarnings("unchecked")
	public LinePlotTest() {
		FileWriter fileWriter = null;
		databaseList = new ArrayList<Person>();
		DataTable databaseTable = new DataTable(Double.class, Double.class);
		DataTable testDataTable = new DataTable(Double.class, Double.class);
		DataTable nnDataTable = new DataTable(Double.class, Double.class);

		try {
			fileWriter = new FileWriter("Bla");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		createDatabase(databaseTable, testDataTable);
		wirte(fileWriter, databaseList);

		nearestNeighbour(nnDataTable);

		calculateDistance();

		XYPlot plot = stylePlot(databaseTable, testDataTable, nnDataTable);

		// Display on screen
		getContentPane().add(new InteractivePanel(plot), BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setMinimumSize(getContentPane().getMinimumSize());
		setSize(504, 327);
	}

	private void calculateDistance() {

		ArrayList<Model> calculatedNeighbours = new ArrayList<Model>();

		for (int i = 0; i < 100; i++) {
			ArrayList<Distance> l1 = new ArrayList<Distance>();
			ArrayList<Distance> l2 = new ArrayList<Distance>();
			ArrayList<Distance> lInfinity = new ArrayList<Distance>();
			for (int i2 = 100; i2 < databaseList.size(); i2++) {

				l1.add(new Distance(KNND1(databaseList.get(i),
						databaseList.get(i2)), databaseList.get(i2)));
				l2.add(new Distance(KNND2(databaseList.get(i),
						databaseList.get(i2)), databaseList.get(i2)));
				lInfinity.add(new Distance(DInfnity(databaseList.get(i),
						databaseList.get(i2)), databaseList.get(i2)));
			}
			calculatedNeighbours.add(new Model(databaseList.get(i), l1, l2,
					lInfinity));
		}
		System.out.println("\nl1");
		calculateErrorRate(calculatedNeighbours, ix.L1);
		System.out.println("\nl2");
		calculateErrorRate(calculatedNeighbours, ix.L2);
		System.out.println("\nlInfinity");
		calculateErrorRate(calculatedNeighbours, ix.LInfinity);
	}

	private void calculateErrorRate(ArrayList<Model> calculatedNeighbours,
			ix type) {

		int k[] = {1, 3, 5, 9, 15, 31};
		HashMap<Integer, Integer> errorRate = new HashMap<Integer, Integer>();

		for (int i2 = 0; i2 < k.length; i2++) {
			int right = 0;
			int wrong = 0;
			for (int i = 0; i < 100; i++) {
				switch (type) {
					case L1 :
						if (isCorrect(calculatedNeighbours.get(i).getPerson(),
								calculatedNeighbours.get(i).getL1(), k[i2])) {
							right++;
						} else {
							wrong++;
						}
						break;
					case L2 :
						if (isCorrect(calculatedNeighbours.get(i).getPerson(),
								calculatedNeighbours.get(i).getL2(), k[i2])) {
							right++;
						} else {
							wrong++;
						}
						break;
					case LInfinity :
						if (isCorrect(calculatedNeighbours.get(i).getPerson(),
								calculatedNeighbours.get(i).getlInfinity(),
								k[i2])) {
							right++;
						} else {
							wrong++;
						}
						break;
				}
				errorRate.put(k[i2], (100 / (right + wrong) * wrong));
			}
		}

		Iterator<Integer> keySetIterator = errorRate.keySet().iterator();

		while (keySetIterator.hasNext()) {
			Integer key = keySetIterator.next();
			System.out.println("k: " + key + " value: " + errorRate.get(key));
		}
	}

	private boolean isCorrect(Person person, ArrayList<Distance> lX, int k) {

		Comparator<Distance> cmp = new Comparator<Distance>() {
			@Override
			public int compare(Distance o1, Distance o2) {
				return o1.getDistance().compareTo(o2.getDistance());
			}
		};
		int class0 = 0;
		int class1 = 0;

		Collections.sort(lX, cmp);

		for (int i = 0; i < k; i++) {
			if (lX.get(i).getTestPerson().getKlassifikation() == 0) {
				class0++;
			} else {
				class1++;
			}
		}

		if (class0 > class1) {
			if (person.getKlassifikation() == 0) {
				return true;
			}
		} else {
			if (person.getKlassifikation() == 1) {
				return true;
			}
		}
		return false;
	}

	private double KNND1(Person databaseModel, Person testDataModel) {

		return Math
				.abs(databaseModel.getGewicht() - testDataModel.getGewicht())
				+ Math.abs(databaseModel.getGroesse()
						- testDataModel.getGroesse());
	}

	private double KNND2(Person databaseModel, Person testDataModel) {
		return Math
				.sqrt(((databaseModel.getGewicht() - testDataModel.getGewicht()) * (databaseModel
						.getGewicht() - testDataModel.getGewicht()))
						+ ((databaseModel.getGroesse() - testDataModel
								.getGroesse()) * (databaseModel.getGroesse() - testDataModel
								.getGroesse())));

	}

	private double DInfnity(Person databaseModel, Person testDataModel) {

		return Math.max(
				Math.abs(databaseModel.getGewicht()
						- testDataModel.getGewicht()),
				Math.abs(databaseModel.getGroesse()
						- testDataModel.getGroesse()));
	}

	private void nearestNeighbour(DataTable nnDataTable) {
		Person model;
		Person paul = new Person(175, 89, -1);
		Person temp = null;

		double d = Double.MAX_VALUE;
		double d2 = -1;
		ArrayList<Double> dlist = new ArrayList<Double>();

		for (int i = 0; i < 100; i++) {
			model = databaseList.get(i);
			d2 = Math.sqrt(((model.getGewicht() - paul.getGewicht()) * (model
					.getGewicht() - paul.getGewicht()))
					+ ((model.getGroesse() - paul.getGroesse()) * (model
							.getGroesse() - paul.getGroesse())));

			dlist.add(d);

			if (d2 < d) {
				d = d2;
				temp = model;
			}

		}

		if (temp != null) {
			nnDataTable.add(temp.getGroesse(), temp.getGewicht());
		}
	}

	private void createDatabase(DataTable database, DataTable testData) {
		Person model;
		double groesse;
		double gewicht;
		int klassifikation;
		for (int i = 0; i < 200; i++) {
			groesse = 162 + (7 * i % 19) + (5 * i % 7) * (i % 3);
			gewicht = (groesse - 100 + 2 * (i % 2 - 0.5)
					* (3 * i % 11 + 5 * i % 13 + 1));
			klassifikation = i % 2;
			if (i % 19 == 0 && i != 0) {
				klassifikation = 1 - klassifikation;
			}

			model = new Person(groesse, gewicht, klassifikation);
			databaseList.add(model);
			if (i < 100)
				database.add(groesse, gewicht);
			else
				testData.add(groesse, gewicht);
		}
	}

	private XYPlot stylePlot(DataTable database, DataTable testData,
			DataTable nnDataTable) {

		// Create series
		DataSeries series1 = new DataSeries("Series 1", database, 0, 1);
		DataSeries series2 = new DataSeries("Series 2", testData, 0, 1);
		DataSeries series3 = new DataSeries("Series 3", nnDataTable, 0, 1);

		XYPlot plot = new XYPlot(series1, series2, series3);
		// Style the plot
		double insetsTop = 20.0, insetsLeft = 60.0, insetsBottom = 60.0, insetsRight = 40.0;
		plot.setInsets(new Insets2D.Double(insetsTop, insetsLeft, insetsBottom,
				insetsRight));
		plot.setSetting(BarPlot.TITLE, "Aufgabe 2");

		// Style the plot area
		plot.getPlotArea().setSetting(PlotArea.COLOR,
				new Color(0.0f, 0.3f, 1.0f));
		plot.getPlotArea().setSetting(PlotArea.BORDER, new BasicStroke(2f));

		// Style data series
		PointRenderer points1 = new DefaultPointRenderer2D();
		points1.setSetting(PointRenderer.SHAPE, new Ellipse2D.Double(-3.0,
				-3.0, 6.0, 6.0));
		points1.setSetting(PointRenderer.COLOR, new Color(0f, 1.0f, 0f, 1.0f));
		plot.setPointRenderer(series1, points1);

		PointRenderer points2 = new DefaultPointRenderer2D();
		points2.setSetting(PointRenderer.SHAPE, new Rectangle2D.Double(-2.5,
				-2.5, 5, 5));
		points2.setSetting(PointRenderer.COLOR, new Color(1.0f, 0.0f, 0.0f,
				1.0f));
		plot.setPointRenderer(series2, points2);

		PointRenderer points3 = new DefaultPointRenderer2D();
		points3.setSetting(PointRenderer.SHAPE, new Rectangle2D.Double(-2.5,
				-2.5, 5, 5));
		points3.setSetting(PointRenderer.COLOR, new Color(0.0f, 0.0f, 1.0f,
				1.0f));
		plot.setPointRenderer(series3, points3);

		// Style axes
		plot.getAxisRenderer(XYPlot.AXIS_X).setSetting(AxisRenderer.LABEL,
				"Größe");
		plot.getAxisRenderer(XYPlot.AXIS_Y).setSetting(AxisRenderer.LABEL,
				"Gewicht");
		plot.getAxisRenderer(XYPlot.AXIS_X).setSetting(
				AxisRenderer.TICKS_SPACING, 1.0);
		plot.getAxisRenderer(XYPlot.AXIS_Y).setSetting(
				AxisRenderer.TICKS_SPACING, 2.0);
		plot.getAxisRenderer(XYPlot.AXIS_X).setSetting(
				AxisRenderer.INTERSECTION, -Double.MAX_VALUE);
		plot.getAxisRenderer(XYPlot.AXIS_Y).setSetting(
				AxisRenderer.INTERSECTION, -Double.MAX_VALUE);
		return plot;
	}

	private void wirte(FileWriter fileWriter, ArrayList<Person> list2) {
		try {
			for (int i = 0; i < 200; i++)
				fileWriter.write(i + " " + databaseList.get(i).toString()
						+ "\n");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		LinePlotTest frame = new LinePlotTest();
		frame.setVisible(true);
	}
}

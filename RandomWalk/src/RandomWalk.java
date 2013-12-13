import java.util.ArrayList;
import java.util.Random;


/**
 * @author Gregor
 *
 */
public class RandomWalk {
	
	private static int MAXITER = 128;
	private Model s0;
	private ArrayList<Model> database;
	
	public RandomWalk(int n)
	{
		createDatabase(n);
		Random random = new Random();
		s0 = database.get(random.nextInt(database.size()));
	}

	public void start() {
		
		Model sStrich = getSStrich(s0);
		
		if(sStrich.getValue()>= s0.getValue())
		{
			database.set(s0.getIndex()+1, sStrich);
		}
		else
		{
			database.set(s0.getIndex()+1, s0);
		}
		
		int startIndex = s0.getIndex()+1;
		
		for(int i = 0; i < MAXITER; i++)
		{
			Model s = database.get(startIndex);
			Model sStrich2 = getSStrich(s);
			if(sStrich2.getValue()>= s.getValue())
			{
				database.set(s.getIndex()+1, sStrich2);
			}
			else
			{
				database.set(s.getIndex()+1, s);
			}
		}
	}

	private void createDatabase(int n) {

		database = new ArrayList<Model>();
		
		for(int i = 0; i < n; i++)
		{
			database.add(new Model(i,funktion()));
		}
	}
	
	private double funktion()
	{
		double bla1 = 0.0;
		double bla2 = 0.0;
		for(int x = 0; x< database.size();x++)
		{
			bla1 += 100 * (Math.sin((300*x)-100 ) * Math.sin((300*x)-100 ));
			bla2 += (3*x)-1*(3*x)-1;	
		}
		return bla1 + bla2;
	}

	private Model getSStrich(Model s) {
		Random generator = new Random();
		int start;
		int end;

		if(s.getIndex()<2)
		{
			start = s.getIndex();
			end = s.getIndex()+4;
		}
		else if(s.getIndex()<database.size()-2)
		{
			start = s.getIndex()-2;
			end = s.getIndex(); 
		}
		else
		{
			start = s.getIndex()-2;
			end = s.getIndex()+2; 
		}
		return database.get(generator.nextInt(end-start) + start);	
	}
}
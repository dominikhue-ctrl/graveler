package classes;

import java.util.SplittableRandom;
import java.util.concurrent.Callable;

public class Task implements Callable<int[]> 
{
	private int attempts;
	
	public Task(int attempts) {
	    this.attempts= attempts;
	  }

	public static int[] roll( int attempts )
	{
		
		// Result-Array that saves how many times how often he got paralyzed
		int[] results = new int[178];
		
		int safe_turn;

		int paralysed;

		// class to randomize numbers with even probability
		SplittableRandom random = new SplittableRandom();
		

		// Roll random Numbers and count
		for (int i = 0; i < attempts; i += 1)
		{
			// Reset paralysed and safe_turn for the next attempt
			safe_turn = 55; // He has 54 safe turns, but needs to fail a 55th time to die
			paralysed = 0;
			while (safe_turn > 0 && paralysed < 177)
			{

				if (random.nextInt(0, 4) == 0)
				{
					paralysed += 1;
				}
				else
				{
					safe_turn -= 1;
				}
			}

			// Save result for this run
			results[paralysed] += 1;

		}

		
		return results;
	}

	@Override
	public int[] call() throws Exception
	{
		return roll(attempts);
	}
	
}

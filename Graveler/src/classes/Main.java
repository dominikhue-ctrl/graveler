/**
 * 
 */
package classes;

import java.util.SplittableRandom;

/**
 * Austins way doesn't take into account times where graveler doesn't manage to
 * get a single paralysis in the first 55 but get some later on which falsifies
 * the Statistics a little. Therefore i decided that instead of flatly rolling
 * 231 times i will count how many times graveler isn't paralyzed to know when
 * he used up all his 54 safe turns Then i count how many turns he was paralyzed
 * One will notice that this will lower the results as some of the better
 * attempts in reality would have failed already by having graveler explode and
 * the calculated paralyzed would have happened after gravelers death in game.
 * 
 * for that attempt Also instead of just saving the highest number i will output
 * a more specific statistic
 */
public class Main
{

	// Main method
	public static void main(String[] args)
	{

		int safe_turn;

		int paralysed;

		int max_value = 0;

		// class to randomize numbers with even probability
		SplittableRandom random = new SplittableRandom();

		// Result-Array that saves how many times how often he got paralyzed
		int[] results = new int[178];

		// Roll random Numbers and count
		for (int i = 0; i < 1000000000; i += 1)
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

		// output Results
		for (int a = 0; a < 178; a += 1)
		{
			if (results[a] > 0)
			{
				max_value = a;
			}
			
			System.out.println(a + " times paralyzed: " + results[a] + " times");
		}

		System.out.println("The best attempt had " + max_value + " paralysed turns.");

	}

}

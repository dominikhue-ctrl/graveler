/**
 * 
 */
package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Austins way doesn't take into account times where graveler doesn't manage to
 * get a single paralysis in the first 55 but get some later on which falsifies
 * the Statistics a little. Therefore i decided that instead of flatly rolling
 * 231 times i will count how many times graveler isn't paralyzed to know when
 * he used up all his 54 safe turns Then i count how many turns he was paralyzed
 * One will notice that this will lower the results as some of the better
 * attempts in reality would have failed already by having graveler explode and
 *  some of the calculated paralyzes would have happened after gravelers death in game.
 * 
 * for that attempt Also instead of just saving the highest number i will output
 * a more specific statistic
 * 
 * This is an alternative Attempt where i attempt to use multi-threading to cut
 * down time.
 * This works well as dividing the same code into 100 Tasks speeds the whole code up 
 * from ~255 seconds to around 24.24 seconds
 */
public class Main_multi
{

	// Main method
	public static void main(String[] args)
	{
		//Change run and task_number as needed but make sure run is dividable by task_number
		
		// How many Runs should be done
		int run = 1000000000;
		
		// In how many Tasks should the work be split
		// In my case around 100 seemed to be a good sweetspot for 1000000000
		int task_number = 100;

		int max_value = 0;
		
		int attempts = 0;

		// Result-Array that saves how many times how often he got paralyzed
		int[] results = new int[178];

		ExecutorService executor = (ExecutorService) Executors.newFixedThreadPool(10);

		List<Task> taskList = new ArrayList<>();
		
		// Measuring how long it takes
		long start = System.currentTimeMillis();
		
		for (int i = 0; i < task_number; i++)
		{
			Task task = new Task(run/task_number);
			taskList.add(task);
		}

		// Execute all tasks and get reference to Future objects
		List<Future<int[]>> resultList = null;

		try
		{
			resultList = executor.invokeAll(taskList);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		executor.shutdown();
		
		//Combine Results
		for (int i = 0; i < resultList.size(); i++)
		{
			
			// Get Result for a the Task
			try
			{
				Future<int[]> future = resultList.get(i);
				int[] result = future.get();
				
				// Add Result from Task to Full Result
				for (int a = 0; a < 178; a += 1)
				{
					results[a] += result[a];
				}
				
			}
			catch (InterruptedException | ExecutionException e)
			{
				e.printStackTrace();
			}
		}


		// output Results
		for (int a = 0; a < 178; a += 1)
		{


			if (results[a] > 0)
			{
				max_value = a;
				attempts = attempts + results[a] ;
			}

			System.out.println(a + " times paralyzed: " + results[a] + " times");
		}

		System.out.println("The best attempt had " + max_value + " paralysed turns.");
		
		// How long did this Execution take?
		long finish = System.currentTimeMillis();
		long full_seconds = TimeUnit.MILLISECONDS.toSeconds(finish-start);
		System.out.println("The Execution of " + run + " Runs took " + full_seconds + "." + ( finish-start-full_seconds ) + " Seconds." );
		

	}

}

package Bus_Simulator;

import java.util.LinkedList;
import java.util.Random; 

public class Stops extends EventOccurrance {
	// waiting queue implementation of the passengers
	
	private LinkedList<InformationOfPassengers> pass = new LinkedList<InformationOfPassengers>(); 
	public static Double InterArrivalRate = 0.0;
	private Integer busStopNo;
	
	// Ordered list of buses w.r.t their arrival times
	private LinkedList<Bus> busAtStop = new LinkedList<Bus>();
	
	static int[] arr1 = new int[100000];
	int i = 0;
	
	//Random number generator from lecture codes 
	
	private static int seed = 10000;
	
	public static Double random()
	{
		Double x = - Math.log((seed + 1) / 65536.0);
		seed = (25173 * seed + 13849) % 65536;
		return x;
	}
		
	public Stops(Integer busStopNo) 
	{
		this.busStopNo = busStopNo;
	}

	public String getStatus() 
	{
		String var = "Stop_No=" + busStopNo + "\tNo_Passengers=" + pass.size();  // creates a string of the bus stop number and the number of passengers waiting in the queue
		int ass = geti();  // function call to deal with the average waiting times
		arr1[ass]=pass.size();
		seti();   // increment counter 
		return var;
	}
	public int geti()
	{
		return i;
	}
	public void seti() 
	{
		i = i+1;
	}
	
	public StringBuilder getStatusB() 
	{
		StringBuilder strBuilder = new StringBuilder();  // creates a string builder src: https://docs.oracle.com/javase/7/docs/api/java/lang/StringBuilder.html
		int ass = geti(); 
		arr1[ass]=pass.size();
		seti();
		String var2 = "|Stop_No =" + busStopNo + "\tNo_Passengers=" + pass.size(); //creates a string variable holding the stop No and the number of passengers in the queue
		strBuilder.append(var2);
		busAtStop.forEach((B)->
		{
			strBuilder.append(B.getStatusB());    //appends the status for every even of Bus B in the list
		});
		strBuilder.append("|");    //appends | to the stringbuilder to maintain formatting
		return strBuilder;
	}
	
	public Integer getPassengersCount()
	{
		return pass.size();   //gets the number of passengers in waiting queue
	}
	
	// Handling event generation 
	// arrival of passengers and adding future events 
	
	public void generateEvent() 
	{
		pass.add(new InformationOfPassengers()); // Adding New passenger
		PerformedEvent(); 
		// Set time for next passenger arrival
		Random rand = new Random();                           //SRC :- https://www.geeksforgeeks.org/generating-random-numbers-in-java/
		double rand_dub1 = rand.nextDouble();
		SetEventTime(StartSim.tim + InterArrivalRate * rand_dub1);  //replace rand_dub1 with random(); method call to use the magic numbers given in our code.
		
		// Adds the event to the list
		addEvent(this);
	}
	public static void avg()   // to ind out the average waiting queue
	{
		int summer = 0;
		for(int j = 0; j < arr1.length ; j++)
		{
			summer = summer + arr1[j];
		}
		int vaal;
		vaal = summer/45; // this is a hard coded value which needs to be changed based on the console output.
		System.out.println("average size of the waiting queue is " + vaal);
		
	}
	public void passengerBoardBus() 
	{
		pass.removeFirst();  //upon a passenger boarding the passenger is removed from the list hence there being one passenger who has left the stop and boarded the bus
	}
	
	public boolean passengersAvailable()
	{
		if (pass.size() > 0)  // if there are more than 0 passengers in the queue
		{
			return true;
		}
		else
		{
			return false;   // if there are no passengers in the queue
		}
	}
	
	public void busArrived(Bus bus) 
	{
		busAtStop.add(bus);    //event where the bus has arrived at said bus stop.. this bus is added to the bus stop
	}
	
	// Buses will leave depending on the order in which they came in
	public Bus busDeparting(Bus bus)
	{
		
		if (busAtStop.peekFirst().equals(bus)==true) // departs if the bus already exists in the stop and the event is finished
		{
			busAtStop.remove(bus);
			return null;
		}
		else
		{
			return busAtStop.get(busAtStop.indexOf(bus) -1 ); 
		}
	}
	
	public boolean isThisBusHere(Bus bus)  // checks if the bus is at the stop or not 
	{
		return busAtStop.contains(bus);
	}
}
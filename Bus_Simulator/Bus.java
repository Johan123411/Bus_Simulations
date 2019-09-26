package Bus_Simulator;

import java.util.LinkedList;

public class Bus extends EventOccurrance
{
	private LinkedList<Stops> busStops = new LinkedList<Stops>(); //Linked list of type BusStop 
	public static Double timeToTravelToNextStop = 0.0;   
	private Integer busNo;
	
	public String getStatus()    //status for the BUS
	{
		String var1 = "|Bus_No=" + busNo + "\t" + busStops.getFirst().getStatus() + "|"; //creates a string of the bus number and the status of the first bus stop in the list 
		return var1;
	}
	
	public String getStatusB() 
	{
		String var2 = "\tBus_No=" + busNo;  //creates a string of busNO
		return var2;
	}
	
	public Integer getBusNo() 
	{
		return busNo;  //returns bus no
	}
	
	public Bus(LinkedList<Stops> busStops, Integer busNo, Integer interval) //a constructor of the class Bus takes in the Linked List of bus stops and 2 integers one for the bus number and the other for the interval
	{
		this.busNo = busNo;
		this.busStops.addAll(busStops);
		// Initializing buses at stops of equal distance.
		int count = 0; 
		while (count < (busNo - 1) * interval) 
		{
			this.busStops.addLast(this.busStops.removeFirst()); 
			count=count+1;
		}
	}
	
		
	// this handles the event of passenger arrival , if there are no passengers on bus stop the bus moves to the next stop if there are passengers then their arrival event is set + their boarding time
	public void generateEvent() 
	{
		Stops currentStop = busStops.getFirst(); //checking current stop where the bus is..
		
		
		// checks  if the bus is already in the stop
		if (currentStop.isThisBusHere(this) == false)  
		{
			currentStop.busArrived(this); // if not then call BusStop.java 's method busArrived which adds the bus to the BusStop LinkedList
		}
		
		// Checks if the passengers on stop
		if (currentStop.passengersAvailable()==true) //in case of passenger queue existing
		{
			currentStop.passengerBoardBus(); //calls the passenger Board bus method in BusStop.java 
			SetEventTime(StartSim.tim + InformationOfPassengers.BoardTime);  //Calls the SetEventTime method in Events.java and performs the bracketed arguments.
		}
		else 
		{
			// If there are no passengers in the stop i.e passenger queue is 0 then move on to the next stop
			Bus busAhead = currentStop.busDeparting(this); //creates a variable busAhead of type Bus and calls the method busDeparting for this particular event.
			if (busAhead == null) 
			{
				SetEventTime(StartSim.tim + timeToTravelToNextStop); 
				busStops.removeFirst();                //removes the first stop in list
				busStops.addLast(currentStop); 		// adds the current stop to the last of the List
			} 
			else 
			{
				SetEventTime(busAhead.theTimeOfEvent());  // sets time of event 
			}
		}
		PerformedEvent();
		addEvent(this);
	}
}
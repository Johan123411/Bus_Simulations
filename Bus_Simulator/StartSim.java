package Bus_Simulator;

import java.util.LinkedList;   //SRC:- https://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html

public class StartSim {
		
	public static Double tim = 0.0; // Current time in the simulation   
	public static LinkedList<EventOccurrance> events = new LinkedList<EventOccurrance>(); // Current stack of the events to occur in future  SRC:- https://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html
	public static LinkedList<Stops> busStop = new LinkedList<Stops>(); // List of current bus stops and their statuses
	public static LinkedList<Bus> busses = new LinkedList<Bus>(); // List of buses and their statuses

	public static void main(String[] args) {
		//Initialization variables for Simulation
		Double simTime = 8 * 60 * 60.0;   //simulation time runs for 8 hrs 
		Integer noOfBuses = 5;                    // there are 5 busses
		Bus.timeToTravelToNextStop = 5 * 60.0;    //takes 5 mins to travel to the next stop
		Integer noOfBusStops = 15;                //the total number of bus stops = 15
		InformationOfPassengers.BoardTime = 2.0;         //time taken to board is 2.0
		Stops.InterArrivalRate = 12.0; // Mean arrival time of passengers is 5 per minute, now 60/5 = 12 Therefore arrival of passenger every 12 seconds is assumed
		
		// Creation of buses and their stops
	
		int counter = 1;
		//creation of bus
		
		while (noOfBusStops >= counter) 
		{
			busStop.add(new Stops(counter));  //adds a new bus to linked list busStops
			busStop.getLast().generateEvent(); // Generating first event for it i.e a passenger arrival
			counter++;  //increment counter
		}
								
		counter = 1; //resets counter to 1
		
		//creation of bus stops
		
		while (noOfBuses >= counter) 
		{
			busses.add(new Bus(busStop, counter, noOfBusStops/noOfBuses)); //adds a new bus to buses linked list
			busses.getLast().generateEvent();//Generating the first event for it
			counter++;
		}
		
		long checkpoint = 0;
		
		System.out.println("						                                        ****************** Bus Service Starts ******************");
		System.out.println("====================================================================================================================================================================================================================================================================================================================");
		
		while (tim <= simTime) {
			EventOccurrance currentEvent = events.removeFirst(); //creates an object of class Event and stores the first value of the event list.
			tim = currentEvent.theTimeOfEvent();     //sets tim to the return value of method theTimeOfEvent() in Event.java.
			currentEvent.generateEvent();             //calls the abstract method generateEvent() in Event.java ;
			if (tim >= checkpoint) {
// Run as getStatus for Buses & getStatusB for bus stops
				StringBuilder status = new StringBuilder();
				
//    			BLOCK 1 - for buses;	   <--------------------uncomment this block and comment BLOCK 2 to test for BUSES		
			    
				busses.forEach((B) -> {status.append(B.getStatus() + "\t");});     //for each Bus B in the linked list buses the you append the value returned by B.getStatus() to the stringbuilder object status.
				System.out.println(status);  

 //      		BLOCK 2 - for bus stops;   <--------------------uncomment this block and comment BLOCK 1 to test for STOPS
				
//		     	busStops.forEach((B) -> {status.append(B.getStatusB() + "\t");});   //for each Bus B in the linked list buses the you append the value returned by B.getStatusB() to the stringbuilder object status.
//		    	System.out.println(status);
		
				System.out.println("====================================================================================================================================================================================================================================================================================================================");
				checkpoint += 60 * 60;
			}
		} 
		System.out.println("						                                           ****************** Bus service stops ******************");
		Stops.avg(); //calls the average function in BusStop.java
	}
}

// Used https://github.com/Lunarantic/JavaSimulations/tree/master/src/busservice  as the main reference 




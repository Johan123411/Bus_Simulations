package Bus_Simulator;


public class InformationOfPassengers {
	private Double Arrival_Time;   //arrival time of passenger
	public static Double BoardTime = 0.0;  //boarding time of passenger
	
	public InformationOfPassengers() 
	{
		Arrival_Time = StartSim.tim;  //sets arrival time to the timer variable in MainJavaSimulator
	}
	
	public InformationOfPassengers(double time_of_arrival)  //constructor of the class PassengerInfo 
	{
		this();
		this.Arrival_Time = time_of_arrival;  
	}
}
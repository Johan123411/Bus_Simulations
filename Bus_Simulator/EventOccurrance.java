package Bus_Simulator;

public abstract class EventOccurrance 
{
	private Double EventTime;   
	public abstract void generateEvent(); // src: https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html
	private Integer pos = -1;
	
	public Integer getPos() 
	{
		return pos;  // returns the position 
	}
	
	protected void SetEventTime(Double timeOfEvent) 
	{
		this.EventTime = timeOfEvent;  //method to set event time 
	}
	
	public Double theTimeOfEvent() 
	{
		return EventTime;           // method to get the event time
	}
	
	public void PerformedEvent()
	{
		pos = 0;           //position of the bus is set to 0 
	}
	
	public void PositionInc()
	{
		pos = pos + 1;   //increment the position
	}
	
	// Adding event into the linked list and sorting them on the basis of time.
	
	public void addEvent(EventOccurrance event) 
	{
		StartSim.events.forEach((E) -> 
		{
			if (E.theTimeOfEvent() <= event.theTimeOfEvent())  
			{
			event.PositionInc();
			}
		});
		
		StartSim.events.add(event.getPos(), event);
	}
}
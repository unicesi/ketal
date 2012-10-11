package co.edu.icesi.ketal.distribution;

import java.util.Map;
import java.util.Vector;

import co.edu.icesi.ketal.core.Event;

public class KetalMessageHandler implements BrokerMessageHandler{

	private Vector<Event> vector;
	public KetalMessageHandler() {
	vector = new Vector<Event>();	
	}
	
	//The way this handle the message is adding in the events vector.
	@Override
	public Event handle(Event event, Map metadata) {
		System.out.println(this.hashCode() + "- KetalHandler! Event: " + event + " Metadata: " + metadata);
		System.err.println(this.hashCode() +"- Processing: " + event.toString());
		vector.add(event);
		return event;
		
	}
	// New methog implemented of interface.
	public Vector<Event> getVectorEvents()
	{
		return vector;
	}
	
}

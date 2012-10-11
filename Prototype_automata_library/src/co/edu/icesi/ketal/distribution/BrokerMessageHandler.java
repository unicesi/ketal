package co.edu.icesi.ketal.distribution;

import java.util.Map;
import java.util.Vector;

import co.edu.icesi.ketal.core.Event;


public interface BrokerMessageHandler {
	
	public Object handle(Event event, Map metadata);

}

package co.edu.icesi.ketal.distribution;

import java.util.Map;
import java.util.Vector;

import co.edu.icesi.ketal.core.Event;


public class DefaultMessageHandler implements BrokerMessageHandler {

	
	public DefaultMessageHandler() {
	
	}
	
	@Override
	public Object handle(Event event, Map metadata) {
		System.out.println("DefaultHandler! Event: " + event + " Metadata: " + metadata);
		System.err.println("Processing: " + event.toString());
		return event;
	}
	
}

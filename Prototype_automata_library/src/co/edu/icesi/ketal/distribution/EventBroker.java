package co.edu.icesi.ketal.distribution;

import java.util.Map;

import co.edu.icesi.ketal.core.Event;


/**
* This Interface is the main element to decouple the transport layer
* from the operator-framework. Concrete brokers must implement
* this interface and use specific transport libraries e.g., JGroups.
* 
* The framework creates an EventBroker with an specialized 
* message handler. Such Handler has the semantics of message reception
* that are different for each platform.
* 
* Current implementation only provides asynchronous dispatching of 
* messages (no answer is expected). However this may change to support
* new functionalities.
* 
* @author Luis Daniel Benavides Navarro
* @version 0.1, 27-10-2010
*/
public interface EventBroker {
	
	//Brodcast a message and does not care about any answer
	public void multicast(Event e, Map metadata);
	
	//
	public Object handle(Event e, Map metadata);
	
	//Written by Andrés Barrera
	public String getAddress();
	
}

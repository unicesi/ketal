package co.edu.icesi.ketal.core;

/**
 * Facade State of the dk.brics.automaton.State
 */
public class State {
	private dk.brics.automaton.State state;
	
	// This Event is saved to show what event happened to stay in this State
	private Event eventCausethisState;
	
	/**
	 * Constructor
	 * @param state
	 */
	public State(dk.brics.automaton.State state){
		this.state = state;
	}
	
	/**
	 * Get the State attribute
	 * @return State
	 */
	public dk.brics.automaton.State getState(){
		return this.state;
	}
	
	/**
	 * Set State attribute
	 * @param state
	 */
	public void setState(dk.brics.automaton.State state){
		this.state = state;
	}
	
	/**
	 * Get the Event that happened and make the transition to this current State
	 * @return Event
	 */
	public Event getEventCauseThisState()
	{
		return eventCausethisState;
	}
	
	/**
	 * Set the Event that happened and make the transition to this current State
	 * @return The New Event, makes the transition happens.
	 */
	public void setEventCauseThisState(Event event)
	{
		this.eventCausethisState=event;
	}
}

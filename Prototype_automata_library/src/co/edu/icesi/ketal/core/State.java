package co.edu.icesi.ketal.core;

/**
 * Facade State of the dk.brics.automaton.State
 */
public class State {
	private dk.brics.automaton.State state;
	
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
}

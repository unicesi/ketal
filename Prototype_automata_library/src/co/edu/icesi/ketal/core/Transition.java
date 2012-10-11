package co.edu.icesi.ketal.core;

/**
 * Extension of the dk.brics.automaton.Transition.
 * Expression is part of the alphabet to move of one state to other.
 * TODO: Review how Expressions should be used in transitions. They are not used
 * right now.
 */
public class Transition {
	private dk.brics.automaton.Transition transition;
	private State begin;
	private State end;
	private Expression expression;
	
	/**
	 * Constructor
	 * @param transition
	 * @param begin
	 */
	public Transition(dk.brics.automaton.Transition transition, State begin){
		this.transition = transition;
		this.begin = begin;
		this.end = new State(this.transition.getDest());
	}

	/**
	 * Get the Transition
	 * @return Transition
	 */
	public dk.brics.automaton.Transition getTransition() {
		return transition;
	}

	/**
	 * Get the State attribute begin
	 * @return State
	 */
	public State getBegin() {
		return begin;
	}

	/**
	 * Get the State attribute End
	 * @return State
	 */
	public State getEnd() {
		return end;
	}

	/**
	 * Get the Expression associated with this Transition
	 * @return Expression
	 */
	public Expression getExpression() {
		return expression;
	}
}

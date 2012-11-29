package co.edu.icesi.ketal.core;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import dk.brics.automaton.RegExp;

/**
 * Finite-State automaton, with recognition of Events mapped through a Character. 
 *
 */
public class Automaton {
	
	private dk.brics.automaton.Automaton automaton;
	private State begin;
	private State current;
	private Set<State> endStates;
	private Set<Transition> transitions;
	private Hashtable<Expression, Character> expressions;
	
	/**
	 * Empty constructor. Permits to create an Automaton. 
	 */
	public Automaton(){
		
		automaton= new dk.brics.automaton.Automaton();		
		begin = null;
		current = null;
		endStates = new HashSet<State>();
		transitions= new HashSet<Transition>();
		expressions = new Hashtable<Expression,Character>();
	}

	/**
	 * Constructor. Permits to create an Automaton with a specific Regular Expression
	 * @param regexp. Regular Expression.
	 */
	public Automaton(String regexp){
		
		automatonByRegularExpression(regexp);
		
	}
	/**
	 * Constructs an Automaton, with a Regular Expression, and a set of expressions.
	 * @param regexp: Regular Expression
	 * @param expressions: HashTable<Object, Character> of Expressions, that are matched with a character.
	 * @throws KetalException 
	 * 
	 *  
	 */
	public Automaton(String regexp, Hashtable<Expression,Character> expressions) throws KetalException{
		automaton = new RegExp(regexp).toAutomaton();
		automaton.removeDeadTransitions();
		
		Enumeration<Character> elements = expressions.elements();
		Character result = null;

		while (elements.hasMoreElements())
		{
			result = (Character) elements.nextElement();
			
			if (regexp.indexOf(result.charValue())==-1)
			{
				throw new KetalException("Elements of the Regular Expression doesnt match with the Mapped Expressions");
			}
		}
		
		this.expressions = expressions;
	}
	
	/**
	 * Constructs an Automaton, with specific params.
	 * @param transitions: Transitions, between states.
	 * @param begin: Start State.
	 * @param end: Set of end states.
	 */
	public Automaton(Set<Transition> transitions, State begin, Set<State> end){
		this.begin = begin;
		this.endStates = end;
		this.transitions = transitions;
		
		createInstanceAutomaton(transitions, begin, end);
	}
	
	/**
	 * Constructs an Automaton, with specific params.
	 * @param transitions: Transitions, between states.
	 * @param begin: Start State.
	 * @param end: Set of end states.
	 */
	public Automaton(Set<Transition> transitions, State begin, Set<State> end, Hashtable<Expression,Character> expressions){
		this.begin = begin;
		this.endStates = end;
		this.transitions = transitions;
		this.expressions = expressions;
		
	}
	
	private void createInstanceAutomaton(Set<Transition> transitions, State begin, Set<State> end)
	{
		dk.brics.automaton.State anState;
		dk.brics.automaton.State nextState;
		dk.brics.automaton.Transition transition;
		
		Set<State> states = new HashSet();
		
		for(Transition tran: transitions)
		{
			tran.getBegin();
			
		}
		
	}
	
	
	/**
	 * Get the State attribute begin
	 * @return Start State of the automaton.
	 */
	public State getBegin() {
		return begin;
	}

	/**
	 * Get the Set of end states
	 * @return set of end States.
	 */
	public Set<State> getEndStates() {
		return endStates;
	}

	/**
	 * Get the set of transitions
	 * @return set of Transitions between states.
	 */
	public Set<Transition> getTransitions() {
		return transitions;
	}

	/**
	 * Get the current state of the automaton
	 * @return Current state of the automaton.
	 */
	public State getCurrentState(){
		return this.current;
	}
	
	/**
	 * Look for an Expression in the HashTable and get its character
	 * @param exp Expression to be searched in the HashTable
	 * @return Character mapped to a specific Expression.
	 */
	public Character getCharacterMapExpression(Expression exp){
		
		if(exp !=null){
	
			//Search in the HashTable Expressions if the Expression exp exists.
			Character c = (Character) expressions.get(exp);
			
			if (c != null){
				return c.charValue();
			}
		}
		return '-';
	}

	/**
	 * Get the HashTable with the mapping of expressions and characters
	 * @return HashTable mapping expressions with characters
	 */
	public Hashtable<Expression,Character> getMapAlphabet()
	{
		return expressions;
	}

	/**
	 * Set a regular expression of this automaton.
	 * @param regexp Regular expression that will define the language recognized by this automaton
	 */
	public void setRegularExpression(String regexp)
	{
		automatonByRegularExpression(regexp);
	}

	/**
	 * Establish if the current state, has a transition to other, consuming the param.
	 * @param c char to be performed
	 * @return boolean if the perform was successful
	 * 
	 * 
	 * Modified by Andres Barrera 2011-Nov-18
	 */
	public boolean perform(Event event, char c)
	{
		if (current != null)
		{
			if (current.getState() != null)
			{
				State temp = current;
				temp.setState(current.getState().step(c));
				if (temp.getState()!= null)
				{
					current.setState(current.getState().step(c));
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Evaluate, if an Event is already mapped.
	 * @param event to be evaluated
	 * @return True: Event has mapped. False other case.
	 */
	public boolean evaluate(Event event){
		
		if (checkEvent(event)){
			
			// Get the char mapped with the given event
			Expression exp = getExpression(event);
			
			if(exp.evaluate(event))
			{
				char c = getCharacterMapExpression(exp);
				if (c != '-'){				
					return perform(event,c);
				}	
			}
					
		}		
		return false;
	}
	
	/**
	 * Check if the given event is present in an expression of any transition in the automaton
	 * @param event to be checked
	 * @return boolean true if there is some transition that has it, false in other case
	 */
	private boolean checkEvent(Event event){
		
		State current = getCurrentState();
		//Obtain the transitions.
		Iterator<Transition> i = getTransitions().iterator();
		Transition t;
		
		//Evaluate each Transition
		// If the event is found in an Expression, the algorithm returns true, in other case False.
		while (i.hasNext()){
			t = i.next();
			if (t.getBegin().equals(current)){
				if (t.getExpression().evaluate(event)){
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	/**
	 * Association between an Expression and a Character.
	 * @param exp Expression to be mapped with the character
	 * @param symbol Character to be mapped with the expression
	 * @return boolean indicates if the mapping was successful
	 */
	public boolean mapExpressionToAlphabet(Expression exp, Character symbol)
	{
		int size = expressions.size();
		expressions.put(exp, symbol);

		if (expressions.size() > size)
		{
			return true;
		}
		else
		{
			return false;
		}

	}
	
	/**
	 * Looks for an expression associated with the event
	 * @param ev Event to be evaluated with the expressions of the automaton
	 * @return Expression associated with the given event, null if not found
	 */
	public Expression getExpression(Event ev) {
		
		// First we get all the expressions used in the transitions of the automaton
		Enumeration<Expression> keys = expressions.keys();
		Expression result = null;
		boolean done = false;

		// Iterates until there is no more expressions to evaluate or the variable done is true
		while (keys.hasMoreElements() && !done)
		{
			result = (Expression) keys.nextElement();
			
			if (result.evaluate(ev))
			{
				// If the evaluation is successful, we change the value of the variable and finish the cycle
				done = true;
			}

		}
		
		// The return depends on the done variable value
		if (done == false)
		{
			return null;
		}
		else
		{
			return result;
		}
	}
	
	/**
	 * Main method, using the Vector<Event> can define if the sequence of Events are recognized by the Automaton.
	 * @param word Vector of Events to be evaluated
	 * @return boolean
	 */
	public boolean isAWordAutomaton(Vector<Event> word)
	{
		
		boolean over=false;
		//We create a string to hold the mapped character for each event
		//thus we can use an external library that supports regular
		//expressions like dk.brics.automaton
			String myWord= "";
			Event eventWord=null;
			Character charEvent=new Character('-');
			
			
			for(int index=0; (word.size()>index)&&!over; index++)
			{
				eventWord=word.get(index);
				
				//In this code, first of all, get the expression that have a Event, then obtains the characther mapped to that Expression
				charEvent= getCharacterMapExpression(getExpression((eventWord)));
				if(charEvent=='-')
				{
					over=true;
					return false;
				}
				else
				{
					//Concat the character found into a String
					myWord=myWord+(charEvent.toString());
				}
			}
			
			//Define if the sequence of characteres (mapped of Events) is accepted by 
			//this automaton.
			return automaton.run(myWord);
	}
	
	/**
	 * Method used to create an automaton and initialize the attributes with the given regular expression
	 * @param regexp to be used to create the automaton
	 */
	private void automatonByRegularExpression(String regexp)
	{
		//First we create an dk.brics.automaton.Automaton with a specific regular expression
		automaton = new RegExp(regexp).toAutomaton();
		
		//Then we remove dead transitions
		automaton.removeDeadTransitions();
		if (!automaton.isDeterministic()){
			automaton.determinize();
		}
		
		//We map the begin and current State attributes with the initial state of the automaton
		begin = new State(automaton.getInitialState());
		current = new State(automaton.getInitialState());
		
		//We initialize the endStates HashSet and we fill it with the accept states of the automaton
		endStates = new HashSet<State>();
		Iterator<dk.brics.automaton.State> i = automaton.getAcceptStates().iterator();
		while (i.hasNext()){
			endStates.add(new State(i.next()));
		}
		//We initialize the transitions HashSet 
		transitions = new HashSet<Transition>();
		//We get all the automaton's states
		Iterator<dk.brics.automaton.State> i2 = automaton.getStates().iterator();
		Iterator<dk.brics.automaton.Transition> i3;
		dk.brics.automaton.Transition nextTran=null;
		dk.brics.automaton.State nextState = null;
		while (i2.hasNext()){
			nextState=i2.next();
			//For each state, we get all the state's transitions
			i3 = i2.next().getTransitions().iterator();
			while (i3.hasNext()){
				nextTran=i3.next();
				// We fill the HashSet with those transitions
				transitions.add(new Transition(nextTran, new State(nextState)));
			}
		}
	}
}

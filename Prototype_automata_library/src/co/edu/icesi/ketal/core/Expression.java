package co.edu.icesi.ketal.core;

/**
 * Expression. Permits the evaluation of an event and generates a boolean result
 */
public interface Expression {
	
	/**
	 * This method must compare the Event that has with the param.
	 * @param event: Param
	 * @return True, if param and the Event of the expression are equals.
	 */
	public boolean evaluate(Event event);
	
	//TODO:
	//public boolean evaluate(Expression myExp);
	
}

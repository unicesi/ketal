package co.edu.icesi.ketal.core;

/**
 * Expression. compares the relation of events causality.
 *
 */
public class CausalEqualsExpression implements Expression
{
	public Event event;
	
	/* (non-Javadoc)
	 * @see co.edu.icesi.ketal.core.Expression#evaluate(co.edu.icesi.ketal.core.Event)
	 */
	@Override
	public boolean evaluate(Event event)
	{
		return false;
	}

	/**
	 * Get the event associated
	 * @return Event evaluated by the Expression
	 */
	public Event getEvent()
	{
		return event;
	}
	
	/**
	 * Set the event associated
	 * @param event Event evaluated by the Expression
	 * @return boolean
	 */
	public boolean setEvent(Event event)
	{
		try
		{
			this.event=event;
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
}

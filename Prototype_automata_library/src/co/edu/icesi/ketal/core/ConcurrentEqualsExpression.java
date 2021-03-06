package co.edu.icesi.ketal.core;

/**
 * Expression. compares the relation of events concurrency.
 *
 */
public class ConcurrentEqualsExpression implements Expression
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
	
	@Override
	/**
	 * Get the event associated
	 * @return Event evaluated by the Expression
	 */
	public Event getEvent()
	{
		return event;
	}
	
	@Override
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

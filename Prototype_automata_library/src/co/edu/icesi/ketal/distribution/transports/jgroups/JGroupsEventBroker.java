package co.edu.icesi.ketal.distribution.transports.jgroups;

import java.util.Map;

import co.edu.icesi.ketal.core.Event;
import co.edu.icesi.ketal.distribution.BrokerMessage;
import co.edu.icesi.ketal.distribution.BrokerMessageHandler;
import co.edu.icesi.ketal.distribution.DefaultMessageHandler;
import co.edu.icesi.ketal.distribution.EventBroker;


/**
 * This class manages the communication of messages using JGroups. It implements
 * the Interfaces EvenBroker so it can be used in framework using the class
 * EventBroker.
 * 
 * @author Luis Daniel Benavides Navarro
 * @version 0.1
 */
public class JGroupsEventBroker implements EventBroker {
	
	private JGroupsFacade monitor;
	private String groupName;
	private BrokerMessageHandler messageHandler;
	
	public JGroupsEventBroker(String groupName, BrokerMessageHandler bmh){
		this.groupName = groupName;
		this.messageHandler = bmh;
		this.monitor = new JGroupsFacade(groupName, this);
	}
	
	public JGroupsEventBroker(String groupName) {
		this(groupName, new DefaultMessageHandler());
	}

	@Override
	public void multicast(Event e, Map metadata) {
		BrokerMessage m = new BrokerMessage(e, metadata);
		monitor.broadcastMessageAsync(m);
	}

	@Override
	public Object handle(Event e, Map metadata) {
		return messageHandler.handle(e, metadata);
	}
	
	@Override
	public String getAddress()
	{
		return monitor.getChannel().getAddress().toString();
	}

}

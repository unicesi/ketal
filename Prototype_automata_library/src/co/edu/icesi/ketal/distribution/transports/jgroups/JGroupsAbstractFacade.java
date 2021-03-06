package co.edu.icesi.ketal.distribution.transports.jgroups;

import org.jgroups.Channel;
import org.jgroups.JChannel;
import org.jgroups.ReceiverAdapter;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.RpcDispatcher;
import org.jgroups.util.RspList;

import co.edu.icesi.ketal.distribution.EventBroker;

/**
 * Contains the channel that is used to send sync or async messages through the
 * network
 * 
 * @author dduran
 * 
 */
public abstract class JGroupsAbstractFacade extends ReceiverAdapter {

	// Default logger
	static org.apache.log4j.Logger logger = org.apache.log4j.Logger
			.getLogger(JGroupsAbstractFacade.class);

	// Channel object, this is part of Jgroups API
	Channel channel;
	RpcDispatcher disp;
	RspList rsp_list;
	RequestOptions opts;
	// RequestHandler disp;

	// The properties configuring the Jgroups communication stack
	// Properties are managed by class DistributionProperties
	String props = DistributionProperties.getInstance()
			.getStack("AspectsGroup");

	String groupName;
	EventBroker jeb;
	static int typeOfMsgSent = 0;

	private Object signal = new Object();

	/**
	 * Method that contains the instructions to initialize the channel and the
	 * messages receiver
	 */
	public abstract void initializeFacade();

	/**
	 * Initializes the channel and receives the base group name and the specific
	 * EventBroker (to handle the messages) as parameters
	 * 
	 * @param groupName
	 * @param jeb
	 */
	public JGroupsAbstractFacade(String groupName, EventBroker jeb) {
		this.groupName = groupName;
		this.jeb = jeb;
		try {
			channel = new JChannel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initializes the channel and receives the base group name as a parameter
	 * 
	 * @param groupName
	 */
	public JGroupsAbstractFacade(String groupName) {
		this.groupName = groupName;
		try {
			channel = new JChannel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static org.apache.log4j.Logger getLogger() {
		return logger;
	}

	public static void setLogger(org.apache.log4j.Logger logger) {
		JGroupsAbstractFacade.logger = logger;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public RpcDispatcher getDisp() {
		return disp;
	}

	public void setDisp(RpcDispatcher disp) {
		this.disp = disp;
	}

	public RspList getRsp_list() {
		return rsp_list;
	}

	public void setRsp_list(RspList rsp_list) {
		this.rsp_list = rsp_list;
	}

	public RequestOptions getOpts() {
		return opts;
	}

	public void setOpts(RequestOptions opts) {
		this.opts = opts;
	}

	public String getProps() {
		return props;
	}

	public void setProps(String props) {
		this.props = props;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public EventBroker getJeb() {
		return jeb;
	}

	public void setJeb(EventBroker jeb) {
		this.jeb = jeb;
	}

	public static int getTypeOfMsgSent() {
		return typeOfMsgSent;
	}

	public static void setTypeOfMsgSent(int typeOfMsgSent) {
		JGroupsAbstractFacade.typeOfMsgSent = typeOfMsgSent;
	}

	public Object getSignal() {
		return signal;
	}

	public void setSignal(Object signal) {
		this.signal = signal;
	}
}

package co.edu.icesi.ketal.distribution.transports.jgroups;

// JGroups imports

import java.util.concurrent.Future;

import org.jgroups.Address;
import org.jgroups.Channel;
//import org.jgroups.ChannelClosedException;
//import org.jgroups.ChannelNotConnectedException;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.Receiver;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.blocks.GroupRequest;
import org.jgroups.blocks.MessageDispatcher;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.util.QueueClosedException;
import org.jgroups.util.Rsp;
import org.jgroups.util.RspList;

import co.edu.icesi.ketal.distribution.BrokerMessage;
import co.edu.icesi.ketal.distribution.EventBroker;

/**
 * This class manages the communication of messages using JGroups. It implements
 * the Interfaces EvenBroker so it can be used in framework using the class
 * EventBroker.
 * 
 * @author Luis Daniel Benavides Navarro
 * @version 0.1
 */
public class JGroupsFacade extends ReceiverAdapter {

	// Default logger
	static org.apache.log4j.Logger logger = org.apache.log4j.Logger
			.getLogger(JGroupsFacade.class);

	// Channel object, this is part of Jgroups API
	Channel channel;

	// Message Dispatcher, Jgroups API
	MessageDispatcher disp;
	// RequestHandler disp;

	// This is a list of responses that is fill when a multicast message is sent
	// inside a
	// group of hosts
	RspList rsp_list;

	// The properties configuring the Jgroups communication stack
	// Properties are managed by class DistributionProperties
	String props = DistributionProperties.getInstance()
			.getStack("AspectsGroup");

	private String groupName;
	private EventBroker jeb;

	public JGroupsFacade(String groupName, JGroupsEventBroker jeb) {
		this.groupName = groupName;
		this.jeb = jeb;
		initializeFacade();
	}

	public JGroupsFacade() {
		initializeFacade();
	}

	/**
	 * Starts the communication infrastructure
	 */
	private void initializeFacade() {
		try {
			// channel = new JChannel(props);
			channel = new JChannel();
			// With setOpet we set the Not self delivery property
			// channel.setOpt(Channel.LOCAL, false);
			// this.disp = new MessageDispatcher(channel, null, null, this);
			channel.setReceiver(this);
			// channel.connect(DistributionProperties.getInstance().getGroupName(
			// groupName));
			// channel.connect("group");

			channel.connect(groupName);

			// logger.debug(channel.getView().toString());
		} catch (Exception e) {
			logger.debug(e);
		}
	}

	public Channel getChannel() {
		return channel;
	}

	// private void broadcastMessage(BrokerMessage m, int x ) {
	//
	// }

	/**
	 * Broadcast a message and do not care bout the answers
	 * 
	 * @param jm
	 */
	public void broadcastMessageAsync(BrokerMessage m) {
		try {
			// channel.send(null, null, m);
			// Modified by Oscar Kiyoshige Garcés.
			channel.send(null, m);
		} catch (Exception ex) {
			logger.error("Error no message delivery: ", ex);
		}
	}

	public Receiver broadcastMessageAsyncReturnFirst(Message m) {
		try {
			// Modified by Jhonny Ocampo
			// channel.send(null, m);
			channel.send(m);
			//channel.send(new Message(null, null, "LOL"));
			if (channel.getReceiver() == null) {
				Receiver rec = new ReceiverMessage();
				channel.setReceiver(rec);
				return rec;
			} else {
				return channel.getReceiver();
			}
		} catch (Exception ex) {
			logger.error(ex);
		}
		return null;
	}

	public void viewAccepted(View new_view) {
		System.out.println("** view: " + new_view);
	}

	public void receive(Message msg) {
		BrokerMessage bm = (BrokerMessage) msg.getObject();
		if (bm != null) {
			jeb.handle(bm.getEvent(), bm.getMetadata());
		} else {
			logger.warn("null BrokerMessage received on method receive");
		}

	}

	public static void main(String[] args) throws Exception {
		/*JGroupsFacade jgf = new JGroupsFacade("Prueba", null);
		 * Thread.sleep(5000); BrokerMessage msg = new BrokerMessage(null,
		 * null);
		 */
		// m.
		// jgf.broadcastMessageAsync(msg);
	}

	/**
	 * Se envían varios mensajes y se muestra el primero de los mensajes recibidos almacenados
	 * en la cola de mensajes del Reciever obtenido del método broadcastMessageAsyncReturnFirst(Message)
	 */
	//Modified by David Durán - Jhonny Ocampo
	//Se necesita identificar los mensajes para conocer si provienen de unicast o broadcast
	public static void testCaseOne() {
		JGroupsFacade jgf = new JGroupsFacade("Prueba", null);
		Message mensaje = new Message(null, null, "hello world");

		jgf.channel.setReceiver(new ReceiverMessage());
		Receiver m = jgf.broadcastMessageAsyncReturnFirst(mensaje);
		Message mes;
		try {
			if (m != null) {
				ReceiverMessage r = (ReceiverMessage) m;
				if (r.getMessages().size() == 0) {
					System.out.println("no se ha recibido el mensaje");
				} else {
					mes = (Message) r.getMessages().peek();
					System.out.println(mes.getSrc() + " " + mes.getDest()
							+ " : " + mes.getObject());
				}

				Thread.sleep(2000);

				System.out.println("se desperto");
				if (r.getMessages().size() == 0) {
					System.out.println("no se ha recibido el mensaje");
				} else {
					mes = (Message) r.getMessages().peek();
					System.out.println(mes.getSrc() + " " + mes.getDest()
							+ " : " + mes.getObject());
				}

			} else {
				System.out.println("nulo");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (QueueClosedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

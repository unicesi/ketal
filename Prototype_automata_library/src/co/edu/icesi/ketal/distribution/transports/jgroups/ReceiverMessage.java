package co.edu.icesi.ketal.distribution.transports.jgroups;

import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.util.Queue;
import org.jgroups.util.QueueClosedException;
import org.omg.CORBA.OMGVMCID;

import co.edu.icesi.ketal.distribution.BrokerMessage;


public class ReceiverMessage extends ReceiverAdapter {

	//private Message mensaje;
	//Modified by David Durán
	private Queue messages = new Queue();
	@Override
	public void receive(Message msg) {
		try {
			messages.add(msg);
		} catch (QueueClosedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*public Message getMensaje() {
		return mensaje;
	}
	public void setMensaje(Message mensaje) {
		this.mensaje = mensaje;
	}*/
	public Queue getMessages() {
		return messages;
	}
	public void setMessages(Queue messages) {
		this.messages = messages;
	}
	
	
	
}

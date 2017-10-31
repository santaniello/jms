package br.com.felipe.jms.queues;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import br.com.felipe.jms.models.Pedido;

public class QueueProducer {
	
	private Session session;

	public QueueProducer(Session session) {
		this.session = session;
	}
	
	public void produces(Destination queue, String content){
		try {
			MessageProducer producer = session.createProducer(queue);
			Message message = session.createTextMessage(content);
			producer.send(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}	
	
	// Enviando diretamente um objeto para a fila
	// Lembrando que o objeto tem que implementar a interface serializable do java...
	public void produces(Destination queue, Pedido content){
		try {
			MessageProducer producer = session.createProducer(queue);
			Message message = session.createObjectMessage(content);
			producer.send(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}	

}

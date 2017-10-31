package br.com.felipe.jms.topics;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

public class TopicProducer {
	
	private Session session;

	public TopicProducer(Session session) {
		this.session = session;
	}
	
	public void produces(Destination topic, String content){
		try {
			MessageProducer producer = session.createProducer(topic);
			Message message = session.createTextMessage(content);
			producer.send(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public void producesWithSelector(Destination topic, String content){
		try {
			MessageProducer producer = session.createProducer(topic);
			Message message = session.createTextMessage(content);
			//criando o selector que será filtrado no consumidor...
			message.setBooleanProperty("ebook", false);
			producer.send(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}

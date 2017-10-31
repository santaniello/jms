package br.com.felipe.jms.conf;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *  Classe responsável por ler o arquivo jndi.properties
 * 
 * */

public class Context {
	
	private ConnectionFactory factory;
	private Destination queue;
	private Destination topicProducer;
	private Destination dlqQueue;
	private Topic topicConsumer;
	
	public Context()  {
		try {
			InitialContext context = new InitialContext();
			this.factory = (ConnectionFactory) context.lookup("ConnectionFactory");
			this.queue = (Destination)context.lookup("financeiro");
			this.topicProducer = (Destination)context.lookup("loja");
			this.topicConsumer = (Topic)context.lookup("loja");
			this.dlqQueue      = (Destination)context.lookup("DLQ"); 
			context.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public ConnectionFactory getFactory() {
		return factory;
	}

	public void setFactory(ConnectionFactory factory) {
		this.factory = factory;
	}

	public Destination getQueue() {
		return queue;
	}

	public void setQueue(Destination queue) {
		this.queue = queue;
	}

	public Destination getTopicProducer() {
		return topicProducer;
	}

	public void setTopicProducer(Destination topicProducer) {
		this.topicProducer = topicProducer;
	}

	public Topic getTopicConsumer() {
		return topicConsumer;
	}

	public void setTopicConsumer(Topic topicConsumer) {
		this.topicConsumer = topicConsumer;
	}

	public Destination getDlqQueue() {
		return dlqQueue;
	}

	public void setDlqQueue(Destination dlqQueue) {
		this.dlqQueue = dlqQueue;
	}
	
	
	
	
}

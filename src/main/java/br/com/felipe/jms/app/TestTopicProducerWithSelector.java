package br.com.felipe.jms.app;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.NamingException;

import br.com.felipe.jms.conf.ConnectionJmsFactory;
import br.com.felipe.jms.conf.Context;
import br.com.felipe.jms.conf.SessionManager;
import br.com.felipe.jms.topics.TopicProducer;

public class TestTopicProducerWithSelector {	
	public static void main(String[] args) throws JMSException, NamingException, InterruptedException {		
		Context context = new Context();
		
        ConnectionJmsFactory factory = new ConnectionJmsFactory(context);
		Connection connection = factory.getConnection("estoque");
		// No caso de topicos, temos de identificar a conexão		
				        
		SessionManager manager = new SessionManager(connection);
		Session session = manager.getSession();
		TopicProducer producer = new TopicProducer(session);
		producer.producesWithSelector(context.getTopicProducer(),"<pedido><id>123</id></pedido>");
		
		Thread.sleep(10000);
				
        manager.close(session);
        factory.close(connection);        
	}

}

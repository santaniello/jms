package br.com.felipe.jms.app;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.NamingException;

import br.com.felipe.jms.conf.ConnectionJmsFactory;
import br.com.felipe.jms.conf.Context;
import br.com.felipe.jms.conf.SessionManager;
import br.com.felipe.jms.topics.TopicConsumer;

public class TestTopicConsumerWithSelector {	
	public static void main(String[] args) throws JMSException, NamingException, InterruptedException {		
		Context context = new Context();
		
        ConnectionJmsFactory factory = new ConnectionJmsFactory(context);
        // No caso de topicos, temos de identificar a conexão						        
		Connection connection = factory.getConnection("comercial");
		SessionManager manager = new SessionManager(connection);
		Session session = manager.getSession();
		
		TopicConsumer consumer = new TopicConsumer(session);		
		// criando a condição para filtrar as mensagens pelo header!
		consumer.consumes(context.getTopicConsumer(),"ebook is null OR ebook=false",false);
				
		Thread.sleep(10000);
				
        manager.close(session);
        factory.close(connection);        
	}

}

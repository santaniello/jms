package br.com.felipe.jms.app;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.NamingException;

import br.com.felipe.jms.conf.ConnectionJmsFactory;
import br.com.felipe.jms.conf.Context;
import br.com.felipe.jms.conf.SessionManager;
import br.com.felipe.jms.queues.QueueProducer;

public class TestQueueProducer {	
	public static void main(String[] args) throws JMSException, NamingException, InterruptedException {		
		Context context = new Context();
		
        ConnectionJmsFactory factory = new ConnectionJmsFactory(context);
		Connection connection = factory.getConnection(null);
				        
		SessionManager manager = new SessionManager(connection);
		Session session = manager.getSession();
		QueueProducer producer = new QueueProducer(session);
		producer.produces(context.getQueue(),"<pedido><id>123</id></pedido>");
		
		Thread.sleep(10000);
				
        manager.close(session);
        factory.close(connection);        
	}

}

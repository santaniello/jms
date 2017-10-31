package br.com.felipe.jms.app;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.NamingException;

import br.com.felipe.jms.conf.ConnectionJmsFactory;
import br.com.felipe.jms.conf.Context;
import br.com.felipe.jms.conf.SessionManager;
import br.com.felipe.jms.queues.QueueConsumer;

public class TestPedidoObjectConsumerWithTransaction {	
	public static void main(String[] args) throws JMSException, NamingException, InterruptedException {
		/*
		 * A partir da versão 5.12.2 do ActiveMQ é preciso configurar explicitamente quais pacotes
		 * podem ser deserializados. Sem ter essa configuração você receberá um exceção na hora 
		 * de consumir uma ObjectMessage. 		 * 
		 * 
		 * Habilitando a desearilazação de todos os pacotes da aplicação
		 * */
		System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","*");
		
		Context context = new Context();
		
        ConnectionJmsFactory factory = new ConnectionJmsFactory(context);
		Connection connection = factory.getConnection(null);
				        
		SessionManager manager = new SessionManager(connection);
		Session session = manager.getTransactionSession();
		QueueConsumer consumidor = new QueueConsumer(session);
		consumidor.consumesWithTransaction(context.getQueue());
		
		Thread.sleep(10000);
				
        manager.close(session);
        factory.close(connection);        
	}

}

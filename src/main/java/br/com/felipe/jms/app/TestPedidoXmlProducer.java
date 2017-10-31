package br.com.felipe.jms.app;

import java.io.StringWriter;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.NamingException;
import javax.xml.bind.JAXB;

import br.com.felipe.jms.conf.ConnectionJmsFactory;
import br.com.felipe.jms.conf.Context;
import br.com.felipe.jms.conf.SessionManager;
import br.com.felipe.jms.models.Pedido;
import br.com.felipe.jms.models.PedidoFactory;
import br.com.felipe.jms.queues.QueueProducer;

public class TestPedidoXmlProducer {	
	public static void main(String[] args) throws JMSException, NamingException, InterruptedException {		
		Context context = new Context();
		
        ConnectionJmsFactory factory = new ConnectionJmsFactory(context);
		Connection connection = factory.getConnection(null);
				        
		SessionManager manager = new SessionManager(connection);
		Session session = manager.getSession();
		QueueProducer producer = new QueueProducer(session);
		
		Pedido pedido = new PedidoFactory().geraPedidoComValores();
		
		// Convertendo um objeto para um xml e enviando para afila
		StringWriter writer = new StringWriter();
		JAXB.marshal(pedido, writer);
		String xml = writer.toString();

		producer.produces(context.getQueue(),xml);
		
		
		Thread.sleep(10000);
				
        manager.close(session);
        factory.close(connection);        
	}

}

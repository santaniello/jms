package br.com.felipe.jms.conf;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

/**
 * Classe que fabrica conexôes JMS 
 * 
 * */

public class ConnectionJmsFactory {
	
	private Context context;
		
	public ConnectionJmsFactory(Context context) {
		this.context = context;
	}

	public Connection getConnection(String idConnection){
		try {
			ConnectionFactory factory = this.context.getFactory();
			
			Connection connection = factory.createConnection();
			// Não podemos esquecer de startar a conexão!
			connection.setClientID(idConnection);
			connection.start();
			return connection;
		} catch (JMSException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("Erro ao obter a conexão");
	}
	
	public void close(Connection connection){
		try {
			// Não podemos esquecer de parar a conexão!
			connection.stop();
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}

package br.com.felipe.jms.conf;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;


/**
 * Classe que gerencia sessões JMS 
 * 
 * A Session no JMS abstrai o trabalho transacional e confirmação do recebimento da mensagem. Além disso,
 * também serve para produzir o MessageConsumer! É um objeto todo poderoso que criamos a partir da conexão.
 * 
 * 
 * */
public class SessionManager {
	
	private Connection connection;

	public SessionManager(Connection connection){
		this.connection = connection;		
	}
	
	public Session getSession(){
		Session session;
		try {			
			/* O primeiro parâmetro do método createSession define se queremos usar o tratamento 
			 * da transação como explícito. Como colocamos false, não é preciso chamar session.commit() 
			 * ou session.rollback().
			 * 
			 * O Session.AUTO_ACKNOWLEDGE diz que queremos automaticamente (através da Session) confirmar o 
			 * recebimento da mensagem JMS.
			 * */
			session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			return session;
		} catch (JMSException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("Erro ao criar Sessão !");		
	}
	
	public Session getTransactionSession(){
		Session session;
		try {			
			/*
			 * Obtendo uma Session com comportamento transacional (commit, rollback)
			 * 
			 * */
			session = this.connection.createSession(true, Session.SESSION_TRANSACTED);
			return session;
		} catch (JMSException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("Erro ao criar Sessão !");		
	}
	
	public void close(Session session){
		try{
		session.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
}

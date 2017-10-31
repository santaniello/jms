package br.com.felipe.jms.queues;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import br.com.felipe.jms.models.Pedido;

public class QueueConsumer {

	private Session session;

	public QueueConsumer(Session session) {
		this.session = session;
	}

	// Destination é a fila que vamos consumir!
	public void consumes(Destination queue) {
		try {
			MessageConsumer consumer = session.createConsumer(queue);

			/*
			 * receive serve apenas para 1 mensagem Message message =
			 * consumer.receive(); System.out.println("Recebendo msg: " +
			 * message);
			 */

			/*
			 * Ao contrario do consumer.receive, quando registramos um listener,
			 * ele fica "escutando" a fila e processo a mensagem assim que ela é
			 * armazenada na fila JMS.
			 * 
			 * OBS: O MessageLister é uma implementação de um listener porém
			 * caso queiramos implementar o nosso, basta criar uma classe e
			 * implementar a interface MessageListener
			 * 
			 */

			consumer.setMessageListener(new MessageListener() {
				public void onMessage(Message message) {
					TextMessage textMessage = (TextMessage) message;
					try {
						System.out.println(textMessage.getText());
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	// Destination é a fila que vamos consumir!
	public void consumesObjectMessage(Destination queue) {
				
		try {
			MessageConsumer consumer = session.createConsumer(queue);

			/*
			 * receive serve apenas para 1 mensagem Message message =
			 * consumer.receive(); System.out.println("Recebendo msg: " +
			 * message);
			 */

			/*
			 * Ao contrario do consumer.receive, quando registramos um listener,
			 * ele fica "escutando" a fila e processo a mensagem assim que ela é
			 * armazenada na fila JMS.
			 * 
			 * OBS: O MessageLister é uma implementação de um listener porém
			 * caso queiramos implementar o nosso, basta criar uma classe e
			 * implementar a interface MessageListener
			 * 
			 */

			consumer.setMessageListener(new MessageListener() {
				public void onMessage(Message message) {
					ObjectMessage objectMessage = (ObjectMessage) message;

					try {
						Pedido pedido = (Pedido) objectMessage.getObject();
						System.out.println(pedido.getCodigo());
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void consumesWithTransaction(Destination queue) {
		try {
			MessageConsumer consumer = session.createConsumer(queue);

			/*
			 * receive serve apenas para 1 mensagem Message message =
			 * consumer.receive(); System.out.println("Recebendo msg: " +
			 * message);
			 */

			/*
			 * Ao contrario do consumer.receive, quando registramos um listener,
			 * ele fica "escutando" a fila e processo a mensagem assim que ela é
			 * armazenada na fila JMS.
			 * 
			 * OBS: O MessageLister é uma implementação de um listener porém
			 * caso queiramos implementar o nosso, basta criar uma classe e
			 * implementar a interface MessageListener
			 * 
			 */

			consumer.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message message) {
					ObjectMessage objectMessage = (ObjectMessage) message;
					try {
						Pedido pedido = (Pedido) objectMessage.getObject();
						System.out.println(pedido.getCodigo());
						session.commit();// confirma o recebimento da mensagem!
					} catch (JMSException e) {
						e.printStackTrace();
						try {
							session.rollback(); // da rolback na transação e manda a mensagem para a fila DLQ..
						} catch (JMSException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}	
					}
				}
			});
		} catch (JMSException e) {
			e.printStackTrace();
		}		
	}

}

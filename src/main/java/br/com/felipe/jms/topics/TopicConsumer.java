package br.com.felipe.jms.topics;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

public class TopicConsumer {

	private Session session;

	public TopicConsumer(Session session) {
		this.session = session;
	}

	/* Topic é o topico que vamos consumir!
	 * messageSelector é um filtro aonde podemos escolher quais mensagens queremos receber
	 * OBS: Não podemos filtrar o corpo de uma mensagem e sim o seu header... para usarmos
	 * o messageSelector devemos passar o parametro pelo header da mensagem para ser avaliado...  
	 
	 *acceptSameConnection é um booleano que diz se queremos aceitar mensagens que foram produzidas
	 *com a mesma conexão...
	 *
	 */
	public void consumes(Topic topic,String messageSelector, boolean acceptSameConnection ) {
		try {
			/*
			 * O problema é que o tópico não sabe quantos consumidores iremos ter. 
			 * Isso é diferente da Fila que define que tem de entregar a mensagem para um 
			 * e tanto faz onde o consumidor esteja ela vai entregar para UM. 
			 * Um tópico não sabe se vai ter um sistema interessado na mensagem 
			 * ou vários outros. Por isso, devemos criar uma assinatura durável...
			 * 
			 * OBS: se nenhum consumidor estiver configurado através da assinatura, a mensagem
			 * ficará perdida...
			 * 
			 * OBS: Um tópico difererente de uma fila entrega uma mesma mensagem para todos os
			 * consumidores (broadcast)...
			 * 
			 * */
			MessageConsumer consumer = session.createDurableSubscriber(topic, "assinatura",messageSelector,acceptSameConnection);

			// criando uma assinatura sem filtro (messageSelector)
			// MessageConsumer consumer = session.createDurableSubscriber(topic, "assinatura");
			
			
			
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
}

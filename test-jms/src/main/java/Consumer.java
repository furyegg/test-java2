import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer {

	private String user = ActiveMQConnection.DEFAULT_USER;
	private String password = ActiveMQConnection.DEFAULT_PASSWORD;
	private String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	private boolean transacted = Constants.TRANSACTED;
	private String subject = "replyQueueXbrl";
	private boolean topic = Constants.TOPIC;
	private String consumerName = "Kyle";
	private String clientId = "consumer1";

	private MessageProducer replyProducer;
	Session session;
	private Destination destination;

	public static void main(String[] args) {
		Consumer c = new Consumer();
		c.consume();
	}

	private void consume() {
		Connection connection = null;
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
		try {
			connection = connectionFactory.createConnection();
			if (clientId != null) {
				connection.setClientID(clientId);
			}

			session = connection.createSession(transacted, Session.AUTO_ACKNOWLEDGE);
			if (topic) {
				destination = session.createTopic(subject);
			} else {
				destination = session.createQueue(subject);
			}

			replyProducer = session.createProducer(null);
			replyProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			MessageConsumer consumer = null;
			if (topic) {
				consumer = session.createDurableSubscriber((Topic) destination, consumerName);
				// consumer.setMessageListener(this);
				connection.start();
			} else {
				connection.start();
				consumer = session.createConsumer(destination, "JMSCorrelationID='xbrlRequestCorrelationId'");
//				consumer = session.createConsumer(destination);
				Message message = consumer.receive();
				onMessage(message);
			}

			consumer.close();
			session.close();

		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {

				}
			}
		}
	}

	public void onMessage(Message message) {
		if (message == null) {
			System.out.println("Haven't received any message!");
			return;
		}
		
		if (message instanceof TextMessage) {
			String msg = null;
			try {
				msg = ((TextMessage) message).getText();
			} catch (JMSException e) {
				e.printStackTrace();
			}
			System.out.println("I received my first jms message: " + msg);

			try {
				if (message.getJMSReplyTo() != null) {
					replyProducer.send(message.getJMSReplyTo(),
							session.createTextMessage("Reply: " + message.getJMSMessageID()));
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println(message.getClass());
		}

		if (transacted) {
			try {
				session.commit();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}

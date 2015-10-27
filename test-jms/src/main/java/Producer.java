import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {

	private String user = ActiveMQConnection.DEFAULT_USER;
	private String password = ActiveMQConnection.DEFAULT_PASSWORD;
	private String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	private boolean transacted = Constants.TRANSACTED;
	private String subject = "replyQueueXbrl";
	private boolean topic = Constants.TOPIC;
	private Destination destination;

	public static void main(String[] args) {
		Producer p = new Producer();
		p.send();
	}

	private void send() {
		Connection connection = null;
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
		try {
			connection = connectionFactory.createConnection();
			connection.start();

			Session session = connection.createSession(transacted, Session.AUTO_ACKNOWLEDGE);
			if (topic) {
				destination = session.createTopic(subject);
			} else {
				destination = session.createQueue(subject);
			}

			MessageProducer producer = session.createProducer(destination);
			TextMessage message = session.createTextMessage("My first jms message to Queue.");
			message.setJMSCorrelationID("def1234567");
			producer.send(message);
			if (transacted) {
				session.commit();
			}
			System.out.println("send my first jms message successfully!");
			producer.close();

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

}

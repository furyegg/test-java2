import org.apache.activemq.broker.BrokerService;


public class TestBroker {

	public static void main(String[] args) throws Exception {
		BrokerService broker = new BrokerService();

		// configure the broker
		broker.addConnector("tcp://172.20.20.146:61616");

		broker.start();
	}

}
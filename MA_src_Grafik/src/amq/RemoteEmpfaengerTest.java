package amq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import amqdata.Hand;

public class RemoteEmpfaengerTest {

	private String server = "tcp://localhost:61616";
	// private String server = "tcp://192.168.0.112:61616"; // PC3

	private Connection connection;
	private Session session;
	private MessageConsumer consumerLinks;
	private MessageConsumer consumerRechts;
	private MyConsumer myConsumer;

	private Hand links = new Hand();
	private Hand rechts = new Hand();

	boolean schubGeben = false;

	volatile boolean weiter = true;

	public RemoteEmpfaengerTest() {
		super();
		try {
			initActiveMq();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setWeiter(boolean weiter) {
		this.weiter = weiter;
	}

	public boolean isWeiter() {
		return weiter;
	}

	public Hand getLinks() {
		return links;
	}

	public Hand getRechts() {
		return rechts;
	}

	private void initActiveMq() throws JMSException {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(server);
		connection = connectionFactory.createConnection();
		connection.start();

		// Create a Session
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Destination destination = session.createQueue("Hand.Links");

		// Create a MessageConsumer from the Session to the Topic or Queue
		consumerLinks = session.createConsumer(destination);
		new Thread(new MyConsumer(consumerLinks, links)).start();

		destination = session.createQueue("Hand.Rechts");
		consumerRechts = session.createConsumer(destination);
		new Thread(new MyConsumer(consumerRechts, rechts)).start();

	}

	private void cleanUpActiveMq() throws JMSException {

		consumerLinks.close();
		consumerRechts.close();
		session.close();
		connection.close();

	}

	public class MyConsumer implements Runnable, ExceptionListener {
		private MessageConsumer consumer;

		Hand hand;

		public MyConsumer(MessageConsumer consumer, Hand hand) {
			super();
			this.consumer = consumer;
			this.hand = hand;
		}

		public void run() {
			System.out.println("Consumer started!!!");
			try {
				while (isWeiter()) {

					// Wait for a message
					Message message = consumer.receive(1000);

					if (message instanceof TextMessage) {
						TextMessage textMessage = (TextMessage) message;
						String text = textMessage.getText();
						this.hand.update(new Hand(text));
						System.out.println(this.hand);

					} else {
						System.out.println("Received2: " + message);
					}
				}

			} catch (Exception e) {
				System.out.println("Caught: " + e);
				e.printStackTrace();
			}
		}

		public synchronized void onException(JMSException ex) {
			System.out.println("JMS Exception occured.  Shutting down client.");
		}
	}

	public void cleanUp() {
		try {
			cleanUpActiveMq();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		new RemoteEmpfaengerTest();
	}

}

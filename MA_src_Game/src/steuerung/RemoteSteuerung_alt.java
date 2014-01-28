package steuerung;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class RemoteSteuerung_alt implements Steuerung {

	private Connection connection;
	private Session session;
	private MessageConsumer consumer;
	private MyConsumer myConsumer;
	

	
	boolean schubGeben = false;
	

	public RemoteSteuerung_alt(){
		super();
		try {
			initActiveMq();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initActiveMq() throws JMSException {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		connection = connectionFactory.createConnection();
		connection.start();

		// Create a Session
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Destination destination = session.createQueue("TEST.FOO");

		// Create a MessageConsumer from the Session to the Topic or Queue
		consumer = session.createConsumer(destination);
		
		new Thread (new MyConsumer(consumer)).start();
		
	}

	private void cleanUpActiveMq() throws JMSException {

		consumer.close();
		session.close();
		connection.close();

	}

	public class MyConsumer implements Runnable,ExceptionListener {
		private MessageConsumer consumer;
		
		String hoch = "s";
		String runter = "w";
		String links = "a";
		String rechts = "d";
		
		String gas = "r";
		
		String notHoch = "-s";
		String notRunter = "-w";
		String notLinks = "-a";
		String notRechts = "-d";
		
		public MyConsumer(MessageConsumer consumer) {
			super();
			this.consumer = consumer;
		}

		public void run() {
			System.out.println("Consumer started!!!");
			boolean weiter = true;
			try {
				while(weiter){
				// Create a ConnectionFactory

				// Create a Connection

				// Create the destination (Topic or Queue)

				// Wait for a message
				Message message = consumer.receive(1000);

				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					String text = textMessage.getText();
					System.out.println("Received1: " + text);
					if(text.toString() == "p"){
						weiter = false;
					}
					
					if(gas.equalsIgnoreCase(text.toString())){
						schubGeben = true;
					}
					
					
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

	@Override
	public boolean isNaseHoch() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isNaseRunter() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRollenLinks() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRollenRechts() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSeitenruderLinks() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSeitenruderRechts() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSchubGeben() {
		return schubGeben;
	}

	@Override
	public boolean isSchubWegnehmen() {
		// TODO Auto-generated method stub
		return false;
	}
	
	

	@Override
	public float getSchub() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public void cleanUp() {
		try {
			cleanUpActiveMq();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean isFahrwerkHoch() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFahrwerkRunter() {
		// TODO Auto-generated method stub
		return false;
	}

}

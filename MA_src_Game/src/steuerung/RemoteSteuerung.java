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

public class RemoteSteuerung implements Steuerung {
	
//	private String server = "tcp://localhost:61616";
//	private String server = "tcp://192.168.0.112:61616"; // PC3
	private String server = "tcp://192.168.14.100:61616"; // Powerwall

	private Connection connection;
	private Session session;
	private MessageConsumer consumer;
	private MyConsumer myConsumer;
	

	private float schub = 0;
	
	boolean schubGeben = false;
	
	boolean fahrwerkEingezogen;
	boolean links;
	boolean rechts;
	boolean hoch;
	boolean runter;
	

	public RemoteSteuerung(){
		super();
		try {
			initActiveMq();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initActiveMq() throws JMSException {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(server);
		connection = connectionFactory.createConnection();
		connection.start();

		// Create a Session
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Destination destination = session.createQueue("SpaceSim.Steuerung");

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
					//System.out.println("Received1: " + text);
					if(text.toString() == "p"){
						weiter = false;
					}
					
					parse(text.toString());
					
					
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
	
	private void parse(String s){
		String[] arr = s.split(";");
		schub = Float.parseFloat(arr[0]);
		
		// arr[1] fahrgestell
		

//			 fahrwerkEingezogen?"-h":"h";
//			 links?"a":"-a";
//			 rechts?"d":"-d";
//			 hoch?"s":"-s";
//			 runter?"w":"-w";
		
		
		fahrwerkEingezogen = "h".equals(arr[1]);
		links = "a".equals(arr[2]);
		rechts = "d".equals(arr[3]);
		hoch = "s".equals(arr[4]);
		runter = "w".equals(arr[5]);
	}

	@Override
	public boolean isNaseHoch() {
		return hoch;
	}

	@Override
	public boolean isNaseRunter() {
		return runter;
	}

	@Override
	public boolean isRollenLinks() {
		return links;
	}

	@Override
	public boolean isRollenRechts() {
		return rechts;
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
	
	public float getSchub(){
		return schub;
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
		return fahrwerkEingezogen;
	}

	@Override
	public boolean isFahrwerkRunter() {
		return !fahrwerkEingezogen;
	}

}

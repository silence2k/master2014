package amq;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import data.HandART2;

public class AMQ_Sender {
	
	private static final long sleeptime = 100;
	
	private String server = "tcp://localhost:61616";
	//private String server = "tcp://192.168.0.112:61616"; // PC3

	private Connection connection;
	private Session session;
	private MessageProducer producerRechts;
	private MessageProducer producerLinks;
	
	private volatile boolean weiter = true;
	
	private HandART2 rechts;
	private HandART2 links;




	public AMQ_Sender(HandART2 rechts, HandART2 links) throws JMSException{
		super();
		this.rechts = rechts;
		this.links = links;
		
		initActiveMQ();
		initSenderThread();
		
	}






	private void initActiveMQ() throws JMSException {
		// Create a ConnectionFactory
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(server);

		// Create a Connection
		connection = connectionFactory.createConnection();
		connection.start();

		// Create a Session
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// Create the destination (Topic or Queue)
		Destination destination = session.createQueue("Hand.Rechts");

		// Create a MessageProducer from the Session to the Topic or Queue
		producerRechts = session.createProducer(destination);
		producerRechts.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		
		destination = session.createQueue("Hand.Links");
		
		producerLinks = session.createProducer(destination);
		producerLinks.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	}
	
	
	private void initSenderThread(){
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(weiter){
					try {
						Thread.sleep(sleeptime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(!weiter){
						break;
					}
					send();
				}
				try {
					cleanUPActiveMq();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t.start();
	}

	private void cleanUPActiveMq() throws JMSException {
		weiter = false;
		// Clean up
		session.close();
		connection.close();
	}
	
	private void send(){
		TextMessage message;
		try {
			message = session.createTextMessage(rechts.toString());
			producerRechts.send(message);
			
			message = session.createTextMessage(links.toString());
			producerLinks.send(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}
	
	
	

	

}

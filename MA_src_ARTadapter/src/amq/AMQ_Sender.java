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
import data.KopfART;

public class AMQ_Sender {
	
	private static final long sleeptime = 100;
	
//	private String server = "tcp://localhost:61616";
	//private String server = "tcp://192.168.0.112:61616"; // PC3
	private String server = "tcp://192.168.14.100:61616"; // Powerwall

	private Connection connection;
	private Session session;
	private MessageProducer producerRechts;
	private MessageProducer producerLinks;
	
	private MessageProducer producerKopf;
	
	private volatile boolean weiter = true;
	
	private HandART2 rechts;
	private HandART2 links;
	
	private KopfART kopf;




	public AMQ_Sender(HandART2 rechts, HandART2 links) throws JMSException{
		super();
		this.rechts = rechts;
		this.links = links;
		
		initActiveMQ();
		initSenderThread();
		
	}

	public AMQ_Sender(KopfART kopf) throws JMSException{
		this.kopf = kopf;
		
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
		Destination destination = null;

		if(rechts != null){
		destination = session.createQueue("Hand.Rechts");
		producerRechts = session.createProducer(destination);
		producerRechts.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		}
		if(links != null){
		destination = session.createQueue("Hand.Links");
		producerLinks = session.createProducer(destination);
		producerLinks.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		}
		if(kopf != null){
		destination = session.createQueue("Kopf");
		producerKopf = session.createProducer(destination);
		producerKopf.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		}
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
			if(rechts !=null){
			message = session.createTextMessage(rechts.toString());
			producerRechts.send(message);
			}
			if(links != null){
				message = session.createTextMessage(links.toString());
				producerLinks.send(message);
			}
			if(kopf != null){
				message = session.createTextMessage(kopf.toString());
				//System.out.println("Kopf: "+message.getText());
				producerKopf.send(message);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}
	
	
	

	

}

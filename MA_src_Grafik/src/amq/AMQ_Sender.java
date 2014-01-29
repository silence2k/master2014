package amq;

import hud.steuerung.SpaceCoreSteuerung;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class AMQ_Sender {
	
	private static final long sleeptime = 50;
	
//	private String server = "tcp://localhost:61616";
	//private String server = "tcp://192.168.0.112:61616"; // PC3
	private String server = "tcp://192.168.14.100:61616"; // Powerwall

	private Connection connection;
	private Session session;
	private MessageProducer producerSteuerung;
	
	private volatile boolean weiter = true;
	
	private SpaceCoreSteuerung steuerung;



	public AMQ_Sender(SpaceCoreSteuerung steuerung) throws JMSException{
		super();
		this.steuerung = steuerung;
		
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

		
		destination = session.createQueue("SpaceCore.Steuerung");
		producerSteuerung = session.createProducer(destination);
		producerSteuerung.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		
		
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

			message = session.createTextMessage(steuerung.getAMQMessage());
			producerSteuerung.send(message);

		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}
	
	
	

	

}

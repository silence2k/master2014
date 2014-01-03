package amq;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;

import amqdata.Hand;
import data.Hand_alt;

public class FernSteuerung {
	
	private static final long sleeptime = 100;

	private Connection connection;
	private Session session;
	private MessageProducer producerRechts;
	private MessageProducer producerLinks;
	
	private MyHand rechts;
	private MyHand links;
	
	private volatile boolean weiter = true;

	public static void main(String... args) {
		try {
			FernSteuerung scs = new FernSteuerung();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}
	
	

	public FernSteuerung() throws JMSException {
		super();
		initHands();
		initGui();
		initActiveMQ();
		initSenderThread();
	}
	
	private void initHands(){
		rechts = new MyHand(-1,0,0.2f);
		links = new MyHand(1,0,0.2f);
	}



	private void initGui() {
		JFrame meinFrame = new JFrame("Beispiel JFrame");
		meinFrame.addWindowListener(new MyWindowListener(this));
		meinFrame.setSize(200, 200);
		meinFrame.add(new JLabel("Beispiel JLabel"));
		meinFrame.addKeyListener(new MyKey(this));
		meinFrame.setVisible(true);
	}


	private void initActiveMQ() throws JMSException {
		// Create a ConnectionFactory
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"tcp://localhost:61616");

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
	
	private void auswerten(String s){
		switch (s) {
		case "t":
			links.addY();
			break;
		case "g":
			links.subY();
			break;
		case "f":
			links.subX();
			break;
		case "h":
			links.addX();
			break;
		case "b":
			links.toggleGrab();
			break;
		case "r":
			links.subZ();
			break;
		case "z":
			links.addZ();
			break;
		case "i":
			rechts.addY();
			break;
		case "k":
			rechts.subY();
			break;
		case "j":
			rechts.subX();
			break;
		case "l":
			rechts.addX();
			break;
		case "m":
			rechts.toggleGrab();
			break;
		case "u":
			rechts.subZ();
			break;
		case "o":
			rechts.addZ();
			break;

		default:
			break;
		}

	}
	
	

	private static class MyKey implements KeyListener {
		
		private FernSteuerung scs;
		public MyKey(FernSteuerung scs) {
			super();
			this.scs = scs;
		}

		@Override
		public void keyPressed(KeyEvent arg0) {
//			System.out.println(arg0.getKeyChar());
			//scs.send(arg0.getKeyChar()+"");
			scs.auswerten((arg0.getKeyChar()+"").toLowerCase());
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

	}
	
	private static class MyWindowListener implements WindowListener{
		
		private FernSteuerung scs;

		public MyWindowListener(FernSteuerung scs) {
			super();
			this.scs = scs;
		}

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosing(WindowEvent e) {
			try {
				scs.cleanUPActiveMq();
			} catch (JMSException e1) {
				e1.printStackTrace();
			}
			System.exit(0);
			
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

}

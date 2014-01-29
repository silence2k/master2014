package hud;

import javax.jms.JMSException;

import hud.steuerung.SpaceCoreSteuerungImpl;
import schalttafel.artefakte.Artefakt;
import schalttafel.artefakte.Hebel1;
import schalttafel.artefakte.Joystick;
import schalttafel.artefakte.Schieber1;
import schalttafel.artefakte.Schieber3;
import schalttafel.verkleidung.Schiebere3Verkleidung;
import amq.AMQ_Sender;
import amq.RemoteEmpfaenger;
import amqdata.Kopf;
import anzeige.Anzeige;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

public class Anzeige_Aufbau2 extends Anzeige {
	
	AMQ_Sender sender;
	private RemoteEmpfaenger empfaenger;

	Hebel1 hebel = new Hebel1();
	Schieber3 schieber = new Schieber3();
	Schiebere3Verkleidung verkleidung = new Schiebere3Verkleidung();

	Joystick joystick = new Joystick();
	
	Kopf kopf;
	
	SpaceCoreSteuerungImpl scsI = new SpaceCoreSteuerungImpl();

	long lasttime = System.currentTimeMillis();
	long deltaTime = 0;

	boolean toggle = false;

	public static void main(String[] args) throws JMSException {
		Anzeige_Aufbau2 app = new Anzeige_Aufbau2();
		app.start();
	}
	
	public Anzeige_Aufbau2() throws JMSException{
		sender = new AMQ_Sender(scsI);
	}

	@Override
	public void simpleInitApp() {
		
		empfaenger = new RemoteEmpfaenger();
		this.flyCam.setEnabled(false);
		cam.setLocation(new Vector3f(0f, 5f, 5f));

		setupKeys();

		rootNode.attachChild(hebel.init(physic, assetManager, new Vector3f(1, 0, 0)));

		rootNode.attachChild(schieber.init(physic, assetManager, new Vector3f(-1, 0, 0)));
		rootNode.attachChild(verkleidung.init(physic, assetManager, new Vector3f(-1, 0, 0)));

		rootNode.attachChild(joystick.init(physic, assetManager, new Vector3f(0, 0, 0)));

		handRechts.init(physic, assetManager, new Vector3f(0, 0.5f, 0.1f));

		handRechts.rotate((float) (3 * Math.PI / 2.0), 0, 0);
		// rootNode.attachChild(handLinks.init(physic, assetManager, new
		// Vector3f(-1, 0, 0.2f)));

		artefakte.add(hebel);
		artefakte.add(schieber);
		artefakte.add(joystick);
		
		
		hebel.setAnzeiger(scsI.getHebelAnzeiger());
		schieber.setAnzeiger(scsI.getSchieberAnzeiger());
		joystick.setAnzeiger(scsI.getJoyStickAnzeiger());

		/** You must add a light to make the model visible */
		DirectionalLight sun = new DirectionalLight();
		sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f).normalizeLocal());
		rootNode.addLight(sun);
	}

	@Override
	public void simpleUpdate(float tpf) {
		// TODO Auto-generated method stub
		super.simpleUpdate(tpf);
		refreshTime();

		for (Artefakt arte : artefakte) {
			arte.update(deltaTime);
		}
		
		
		updateCamera();
	}
	
	private void updateCamera(){
		
		kopf = empfaenger.getKopf();
		float scale = 20;

		this.getCamera().setLocation(new Vector3f((float) kopf.getX() / scale, 20, -(float) kopf.getY() / scale));

		Quaternion q = new Quaternion();

		Vector3f vX = new Vector3f(kopf.getEtaM()[0], kopf.getEtaM()[1], kopf.getEtaM()[2]);
		Vector3f vY = new Vector3f(kopf.getThetaM()[0], kopf.getThetaM()[1], kopf.getThetaM()[2]);
		Vector3f vZ = new Vector3f(kopf.getPhiM()[0], kopf.getPhiM()[1], kopf.getPhiM()[2]);

		System.out.println("CamPos x:" + kopf.getX() + " y: " + kopf.getY());

		q.fromAxes(vX, vY, vZ);
		q.normalizeLocal();

		this.getCamera().setAxes(q);
	}

	private void refreshTime() {
		long time = System.currentTimeMillis();
		deltaTime = time - lasttime;
		lasttime = time;
	}

	private void setupKeys() {

		inputManager.addMapping("ra_hoch", new KeyTrigger(KeyInput.KEY_U));
		inputManager.addMapping("ra_runter", new KeyTrigger(KeyInput.KEY_O));
		inputManager.addMapping("ra_links", new KeyTrigger(KeyInput.KEY_J));
		inputManager.addMapping("ra_rechts", new KeyTrigger(KeyInput.KEY_L));
		inputManager.addMapping("ra_greifen", new KeyTrigger(KeyInput.KEY_M));
		inputManager.addMapping("ra_rein", new KeyTrigger(KeyInput.KEY_I));
		inputManager.addMapping("ra_raus", new KeyTrigger(KeyInput.KEY_K));

		inputManager.addListener(this, "ra_hoch", "ra_runter", "ra_links", "ra_rechts", "ra_greifen", "ra_rein",
				"ra_raus");
	}

	public void onAnalog(String binding, float value, float tpf) {

		switch (binding) {
		case "ra_hoch":
			handRechts.hoch(deltaTime);
			break;
		case "ra_runter":
			handRechts.runter(deltaTime);
			break;
		case "ra_links":
			handRechts.links(deltaTime);
			break;
		case "ra_rechts":
			handRechts.rechts(deltaTime);
			break;
		case "ra_rein":
			handRechts.rein(deltaTime);
			break;
		case "ra_raus":
			handRechts.raus(deltaTime);
			break;
		case "ra_greifen":
			handRechts.toggleGreifen();
			break;

		default:
			break;
		}
	}

}

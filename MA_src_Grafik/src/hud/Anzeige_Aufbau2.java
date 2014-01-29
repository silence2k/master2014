package hud;

import hud.steuerung.SpaceCoreSteuerungImpl;

import javax.jms.JMSException;

import schalttafel.artefakte.Artefakt;
import schalttafel.artefakte.Hebel1;
import schalttafel.artefakte.Joystick;
import schalttafel.artefakte.Schieber3;
import schalttafel.verkleidung.Schiebere3Verkleidung;
import aktor.AktorCopit;
import amq.AMQ_Sender;
import amq.RemoteEmpfaenger;
import amqdata.Hand;
import amqdata.Kopf;
import anzeige.Anzeige;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

public class Anzeige_Aufbau2 extends Anzeige {
	
	float versatzX = 300;
	float versatzY = 120;
	
	float handVersatzX = 0;
	
	float handScaleY = 0.5f;
	
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
		
		handRechts = new AktorCopit(this, rechts);
		handLinks = new AktorCopit(this, links);
		
		empfaenger = new RemoteEmpfaenger();
		this.flyCam.setEnabled(false);
		cam.setLocation(new Vector3f(0f, 1f, 1f));

		setupKeys();
		
		float y = -0.5f;
		float z = 0.0f;
		
		float zJoy = -0.2f;
		float yJoy = -0.7f;
		
		float xSchieber = -0.7f;

		rootNode.attachChild(hebel.init(physic, assetManager, new Vector3f(-1.5f, y, 0)));

		rootNode.attachChild(schieber.init(physic, assetManager, new Vector3f(xSchieber, y, z)));
		rootNode.attachChild(verkleidung.init(physic, assetManager, new Vector3f(xSchieber, y, z)));

		rootNode.attachChild(joystick.init(physic, assetManager, new Vector3f(1, yJoy, zJoy)));

		handRechts.init(physic, assetManager, new Vector3f(1, 0.5f, 0.1f));
		handLinks.init(physic, assetManager, new Vector3f(-1, 0.5f, 0.1f));

		handRechts.rotate((float) (3 * Math.PI / 2.0), 0, 0);
		handLinks.rotate((float) (3 * Math.PI / 2.0), 0, 0);
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
		
		
		//buildPowerwall();
		//buildWand();
	}

	@Override
	public void simpleUpdate(float tpf) {
		// TODO Auto-generated method stub
		super.simpleUpdate(tpf);
		refreshTime();

		for (Artefakt arte : artefakte) {
			arte.update(deltaTime);
		}
		Hand h = empfaenger.getLinks();

		handLinks.update(h.getX(), h.getY()*handScaleY, h.getZ(), h.isGrab());

		h = empfaenger.getRechts();
		handRechts.update(h.getX(), h.getY()*handScaleY, h.getZ(), h.isGrab());
		
		updateCamera();
	}
	
	private void updateCamera(){
		
		kopf = empfaenger.getKopf();
		float scale = 20;

//		this.getCamera().setLocation(new Vector3f((float) kopf.getX() / scale, 0, -(float) kopf.getY() / scale));
		
		
		
	//	System.out.println("CamPos x:" + kopf.getX() + " y: " + kopf.getY() + " z: "+ kopf.getZ());
		//System.out.println("CamPos: "+this.getCamera().getLocation());

		Quaternion q = new Quaternion();

		Vector3f vX = new Vector3f(kopf.getEtaM()[0], kopf.getEtaM()[1], kopf.getEtaM()[2]);
		Vector3f vY = new Vector3f(kopf.getThetaM()[0], kopf.getThetaM()[1], kopf.getThetaM()[2]);
		Vector3f vZ = new Vector3f(kopf.getPhiM()[0], kopf.getPhiM()[1], kopf.getPhiM()[2]);

//		System.out.println("CamPos x:" + kopf.getX() + " y: " + kopf.getY() + " z: "+ kopf.getZ());

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
	
	
	private void buildPowerwall() {
		Box pwBox = new Box(100, 20, 1);
		Geometry pw = new Geometry("powerwall", pwBox);
		Material mat_tl = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat_tl.setColor("Color", new ColorRGBA(1f, 0f, 0f, 1f));
		pw.setMaterial(mat_tl);
		rootNode.attachChild(pw);

		pw.setLocalTranslation(0, 10, -100);
	}
	
	private void buildWand() {
		Box pwBox = new Box(100, 20, 1);
		Geometry pw = new Geometry("powerwall", pwBox);
		Material mat_tl = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat_tl.setColor("Color", new ColorRGBA(1f, 1f, 1f, 1f));
		pw.setMaterial(mat_tl);
		rootNode.attachChild(pw);

		pw.setLocalTranslation(0, 10, 100);
	}

}

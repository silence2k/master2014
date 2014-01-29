package hud;

import hud.steuerung.SpaceCoreSteuerungTest;

import javax.jms.JMSException;

import amq.AMQ_Sender;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;

public class KeyboardSteuerungSpacecore extends SimpleApplication implements AnalogListener {
	
	SpaceCoreSteuerungTest scs = new SpaceCoreSteuerungTest();
	
	AMQ_Sender sender;
	
	long lasttime = System.currentTimeMillis();
	long deltaTime = 0;
	
	double dx = 10;

	boolean toggle = false;
	
	

	public KeyboardSteuerungSpacecore() throws JMSException {
		super();
		sender = new AMQ_Sender(scs);
	}


	public static void main(String[] args) throws JMSException {
		KeyboardSteuerungSpacecore app = new KeyboardSteuerungSpacecore();
		app.start();
	}

	@Override
	public void simpleInitApp() {

		cam.setLocation(new Vector3f(0f, 0f, 0f));

		setupKeys();


		/** You must add a light to make the model visible */
		DirectionalLight sun = new DirectionalLight();
		sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f).normalizeLocal());
		rootNode.addLight(sun);
	}

	@Override
	public void simpleUpdate(float tpf) {
		super.simpleUpdate(tpf);
		refreshTime();


	}

	private void refreshTime() {
		long time = System.currentTimeMillis();
		deltaTime = time - lasttime;
		lasttime = time;
	}

	private void setupKeys() {
		
//		String hoch = "s";
//		String runter = "w";
//		String links = "a";
//		String rechts = "d";
//		
//		String gas = "r";
//		
//		String notHoch = "-s";
//		String notRunter = "-w";
//		String notLinks = "-a";
//		String notRechts = "-d";

		inputManager.addMapping("hoch", new KeyTrigger(KeyInput.KEY_S));
		inputManager.addMapping("runter", new KeyTrigger(KeyInput.KEY_W));
		inputManager.addMapping("links", new KeyTrigger(KeyInput.KEY_A));
		inputManager.addMapping("rechts", new KeyTrigger(KeyInput.KEY_D));
		inputManager.addMapping("gas", new KeyTrigger(KeyInput.KEY_R));
		inputManager.addMapping("gas_weg", new KeyTrigger(KeyInput.KEY_F));
		inputManager.addMapping("fahrgestellRein", new KeyTrigger(KeyInput.KEY_O));
		inputManager.addMapping("fahrgestellRaus", new KeyTrigger(KeyInput.KEY_L));

		inputManager.addListener(this, "hoch", "runter", "links", "rechts", "gas", "gas_weg",
				"fahrgestellRein","fahrgestellRaus");
	}

	public void onAnalog(String binding, float value, float tpf) {

		switch (binding) {
		case "hoch":
			scs.setHoch(true);
			break;
		case "runter":
			scs.setRunter(true);
			break;
		case "links":
			scs.setLinks(true);
			break;
		case "rechts":
			scs.setRechts(true);
			break;
		case "gas":
			scs.setSchub(dx*deltaTime/500);
			break;
		case "gas_weg":
			scs.setSchub(-dx*deltaTime/500);
			break;
		case "fahrgestellRein":
			scs.setFahrwerkEingezogen(true);
			break;
		case "fahrgestellRaus":
			scs.setFahrwerkEingezogen(false);
			break;

		default:
			break;
		}
	}

}

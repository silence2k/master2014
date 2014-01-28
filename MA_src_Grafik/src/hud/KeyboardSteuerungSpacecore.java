package hud;

import schalttafel.artefakte.Artefakt;
import anzeige.Anzeige;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;

public class KeyboardSteuerungSpacecore extends Anzeige {

	long lasttime = System.currentTimeMillis();
	long deltaTime = 0;

	boolean toggle = false;

	public static void main(String[] args) {
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
		// TODO Auto-generated method stub
		super.simpleUpdate(tpf);
		refreshTime();

		for (Artefakt arte : artefakte) {
			arte.update(deltaTime);
		}
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
			
			break;
		case "runter":
			
			break;
		case "ra_links":
			
			break;
		case "rechts":
			
			break;
		case "rein":

			break;
		case "gas":
		
			break;
		case "gas_weg":
			
			break;
		case "fahrgestellRein":
			
			break;
		case "fahrgestellRaus":
	
			break;

		default:
			break;
		}
	}

}

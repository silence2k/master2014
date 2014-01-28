package hud;

import schalttafel.artefakte.Artefakt;
import schalttafel.artefakte.Hebel1;
import schalttafel.artefakte.Joystick;
import schalttafel.artefakte.Schieber1;
import schalttafel.artefakte.Schieber3;
import schalttafel.verkleidung.Schiebere3Verkleidung;
import anzeige.Anzeige;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;

public class Anzeige_Aufbau1 extends Anzeige {

	Hebel1 hebel = new Hebel1();
	Schieber3 schieber = new Schieber3();
	Schiebere3Verkleidung verkleidung = new Schiebere3Verkleidung();

	Joystick joystick = new Joystick();

	long lasttime = System.currentTimeMillis();
	long deltaTime = 0;

	boolean toggle = false;

	public static void main(String[] args) {
		Anzeige_Aufbau1 app = new Anzeige_Aufbau1();
		app.start();
	}

	@Override
	public void simpleInitApp() {

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

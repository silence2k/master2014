package anzeige.bastel;

import schalttafel.artefakte.Artefakt;
import schalttafel.artefakte.Griff1;
import anzeige.Anzeige;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;

public class Anzeige_Griff1 extends Anzeige {

	Griff1 griff1 = new Griff1();

	Griff1 griff2 = new Griff1();

	// AnzeigerLampe lampe = new AnzeigerLampe();

	long lasttime = System.currentTimeMillis();
	long deltaTime = 0;

	boolean toggle = false;

	public static void main(String[] args) {
		Anzeige_Griff1 app = new Anzeige_Griff1();
		app.start();
	}

	@Override
	public void simpleInitApp() {

		cam.setLocation(new Vector3f(0f, 0f, 5f));

		setupKeys();

		rootNode.attachChild(griff1.init(physic, assetManager, new Vector3f(1, 0, 0)));
		// rootNode.attachChild(lampe.init(physic, assetManager, new Vector3f(0,
		// 1, 0)));

		rootNode.attachChild(griff2.init(physic, assetManager, new Vector3f(-1, 0, 0)));

		// griff1.setAnzeiger(lampe);

		handRechts.init(physic, assetManager, new Vector3f(1, 0, 0.2f));
		// rootNode.attachChild(handLinks.init(physic, assetManager, new
		// Vector3f(-1, 0, 0.2f)));

		artefakte.add(griff1);
		artefakte.add(griff2);

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

		inputManager.addMapping("ra_hoch", new KeyTrigger(KeyInput.KEY_I));
		inputManager.addMapping("ra_runter", new KeyTrigger(KeyInput.KEY_K));
		inputManager.addMapping("ra_links", new KeyTrigger(KeyInput.KEY_J));
		inputManager.addMapping("ra_rechts", new KeyTrigger(KeyInput.KEY_L));
		inputManager.addMapping("ra_greifen", new KeyTrigger(KeyInput.KEY_M));
		inputManager.addMapping("ra_rein", new KeyTrigger(KeyInput.KEY_U));
		inputManager.addMapping("ra_raus", new KeyTrigger(KeyInput.KEY_O));

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

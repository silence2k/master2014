package anzeige;

import schalttafel.artefakte.Artefakt;
import schalttafel.artefakte.Schalter2;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;

public class Anzeige_Schalter extends Anzeige {

	Schalter2 schalter1 = new Schalter2();

	long lasttime = System.currentTimeMillis();
	long deltaTime = 0;

	boolean toggle = false;

	public static void main(String[] args) {
		Anzeige_Schalter app = new Anzeige_Schalter();
		app.start();
	}

	@Override
	public void simpleInitApp() {

		cam.setLocation(new Vector3f(0f, 2f, 0f));

		setupKeys();

		rootNode.attachChild(schalter1.init(physic, assetManager, new Vector3f(0, 0, 0)));

		rootNode.attachChild(handLinks.init(physic,assetManager, new Vector3f(-0.1f, 0, 0.2f)));

		artefakte.add(schalter1);

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
			arte.update();
		}
	}

	private void refreshTime() {
		long time = System.currentTimeMillis();
		deltaTime = time - lasttime;
		lasttime = time;
	}

	private void setupKeys() {

		inputManager.addMapping("la_links", new KeyTrigger(KeyInput.KEY_H));
		inputManager.addMapping("la_rechts", new KeyTrigger(KeyInput.KEY_K));
		inputManager.addMapping("la_greifen", new KeyTrigger(KeyInput.KEY_M));
		inputManager.addMapping("la_rein", new KeyTrigger(KeyInput.KEY_U));
		inputManager.addMapping("la_raus", new KeyTrigger(KeyInput.KEY_J));

		inputManager.addListener(this, "la_links", "la_rechts", "la_greifen", "la_rein", "la_raus");
	}

	public void onAnalog(String binding, float value, float tpf) {

		switch (binding) {
		case "la_links":
			handLinks.links(deltaTime);
			break;
		case "la_rechts":
			handLinks.rechts(deltaTime);
			break;
		case "la_rein":
			handLinks.rein(deltaTime);
			break;
		case "la_raus":
			handLinks.raus(deltaTime);
			break;
		case "la_greifen":
			handLinks.toggleGreifen();
			break;
		default:
			break;
		}
	}

}

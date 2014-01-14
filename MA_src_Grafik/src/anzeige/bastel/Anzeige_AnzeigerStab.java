package anzeige.bastel;

import schalttafel.anzeige.AnzeigerStab;
import schalttafel.artefakte.Artefakt;
import anzeige.Anzeige;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;

public class Anzeige_AnzeigerStab extends Anzeige {

	AnzeigerStab stab = new AnzeigerStab();

	long lasttime = System.currentTimeMillis();
	long deltaTime = 0;
	
	float deltaY = 1f;

	boolean toggle = false;

	public static void main(String[] args) {
		Anzeige_AnzeigerStab app = new Anzeige_AnzeigerStab();
		app.start();
	}

	@Override
	public void simpleInitApp() {

		cam.setLocation(new Vector3f(0f, 0f, 5f));

		setupKeys();

		rootNode.attachChild(stab.init(assetManager, new Vector3f(0, 0, 0)));

		//handRechts.init(physic, assetManager, new Vector3f(1, 0, 0.2f));
		// rootNode.attachChild(handLinks.init(physic, assetManager, new
		// Vector3f(-1, 0, 0.2f)));

		anzeiger.add(stab);

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

		inputManager.addMapping("ra_hoch", new KeyTrigger(KeyInput.KEY_I));
		inputManager.addMapping("ra_runter", new KeyTrigger(KeyInput.KEY_K));
		

		inputManager.addListener(this,"ra_hoch","ra_runter");
	}

	public void onAnalog(String binding, float value, float tpf) {

		switch (binding) {
		case "ra_hoch":
			hoch(deltaTime);
			break;
		case "ra_runter":
			runter(deltaTime);
			break;
		default:
			break;
		}
	}
	
	private void hoch(long time){
		
		stab.hoch((float)(deltaY*time/1000.0));
	}

	private void runter(long time){
		stab.runter((float)(deltaY*time/1000.0));
	}
}

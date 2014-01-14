package anzeige.bastel;

import schalttafel.Schalttafel;
import schalttafel.artefakte.Artefakt;
import anzeige.Anzeige;

import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;

public class Anzeige_Schalttafel extends Anzeige {

	Schalttafel schalttafel = new Schalttafel();

	long lasttime = System.currentTimeMillis();
	long deltaTime = 0;

	boolean toggle = false;

	public static void main(String[] args) {
		Anzeige_Schalttafel app = new Anzeige_Schalttafel();
		app.start();
	}

	@Override
	public void simpleInitApp() {

		cam.setLocation(new Vector3f(0f, 0f, 5f));

		rootNode.attachChild(schalttafel.init(physic, assetManager, new Vector3f(0, 0, 0)));

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

	public void onAnalog(String binding, float value, float tpf) {

	}

}

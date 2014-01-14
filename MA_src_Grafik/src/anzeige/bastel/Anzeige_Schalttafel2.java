package anzeige.bastel;

import schalttafel.Schalttafel;
import schalttafel.anzeige.AnzeigerLampe;
import schalttafel.anzeige.AnzeigerStab;
import schalttafel.artefakte.Artefakt;
import schalttafel.artefakte.Hebel1;
import schalttafel.artefakte.Rad1;

import anzeige.Anzeige;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;

public class Anzeige_Schalttafel2 extends Anzeige {

	Schalttafel schalttafel = new Schalttafel();
	Rad1 rad1 = new Rad1();
	AnzeigerStab as1 = new AnzeigerStab();

	long lasttime = System.currentTimeMillis();
	long deltaTime = 0;

	boolean toggle = false;

	public static void main(String[] args) {
		Anzeige_Schalttafel2 app = new Anzeige_Schalttafel2();
		app.start();
	}

	@Override
	public void simpleInitApp() {

		cam.setLocation(new Vector3f(0f, 0f, 5f));

		rootNode.attachChild(schalttafel.init(physic, assetManager, new Vector3f(0, 0, 0)));

		rootNode.attachChild(rad1.init(physic, assetManager, new Vector3f(0, 0, 0)));
		rootNode.attachChild(as1.init(physic, assetManager, new Vector3f(0, 1, 0)));
		rad1.setAnzeiger(as1);

		handRechts.init(physic, assetManager, new Vector3f(1, 0, 0.2f));
		// rootNode.attachChild(handLinks.init(physic, assetManager, new
		// Vector3f(-1, 0, 0.2f)));

		artefakte.add(rad1);

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

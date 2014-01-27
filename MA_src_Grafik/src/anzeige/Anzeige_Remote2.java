package anzeige;

import schalttafel.artefakte.Button1;
import schalttafel.artefakte.Hebel1;
import schalttafel.artefakte.Rad1;
import schalttafel.artefakte.Schalter2;
import schalttafel.artefakte.Schieber1;
import schalttafel.artefakte.Schieber2;
import anzeige.remote.RemoteAnzeige;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;

public class Anzeige_Remote2 extends RemoteAnzeige {

	Rad1 rad1 = new Rad1();
	Rad1 rad2 = new Rad1();
	Rad1 rad3 = new Rad1();

	Hebel1 hebel1 = new Hebel1();
	Hebel1 hebel2 = new Hebel1();

	Schieber1 schieber1 = new Schieber1();
	Schieber2 schieber2 = new Schieber2();

	Button1 button1 = new Button1();
	Button1 button2 = new Button1();

	Schalter2 schalter1 = new Schalter2();

	boolean toggle = false;

	public static void main(String[] args) {
		Anzeige_Remote2 app = new Anzeige_Remote2();
		app.start();
	}

	@Override
	public void simpleInitApp() {

		super.simpleInitApp();

		cam.setLocation(new Vector3f(0f, 0f, 5f));

		rootNode.attachChild(rad1.init(physic, assetManager, new Vector3f(0.4f, -0.5f, 0)));
		rootNode.attachChild(rad2.init(physic, assetManager, new Vector3f(1.2f, -0.5f, 0)));
		rootNode.attachChild(rad3.init(physic, assetManager, new Vector3f(0.8f, 0.5f, 0)));

		rootNode.attachChild(schieber1.init(physic, assetManager, new Vector3f(-0.8f, -0.5f, 0)));
		rootNode.attachChild(schieber2.init(physic, assetManager, new Vector3f(-0.8f, -1.0f, 0)));

		rootNode.attachChild(hebel1.init(physic, assetManager, new Vector3f(-0.8f, 0.4f, 0)));
		rootNode.attachChild(hebel2.init(physic, assetManager, new Vector3f(-1.2f, 0.4f, 0)));

		rootNode.attachChild(button1.init(physic, assetManager, new Vector3f(1.3f, 0.4f, 0)));
		rootNode.attachChild(button2.init(physic, assetManager, new Vector3f(1.7f, 0.4f, 0)));

		handRechts.init(physic, assetManager, new Vector3f(1.5f, 1, 0.2f));
		handLinks.init(physic, assetManager, new Vector3f(-0.5f, 1, 0.2f));

		artefakte.add(rad1);
		artefakte.add(rad2);
		artefakte.add(rad3);

		artefakte.add(hebel1);
		artefakte.add(hebel2);

		artefakte.add(schieber1);
		artefakte.add(schieber2);

		artefakte.add(button1);
		artefakte.add(button2);

		// artefakte.add(schalter1);

		/** You must add a light to make the model visible */
		DirectionalLight sun = new DirectionalLight();
		sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f).normalizeLocal());
		rootNode.addLight(sun);
	}

	private void setupKeys() {
		inputManager.addMapping("ende", new KeyTrigger(KeyInput.KEY_ESCAPE));
		inputManager.addListener(this, "ende");
	}

}

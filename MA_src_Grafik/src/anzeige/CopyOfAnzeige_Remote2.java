package anzeige;

import schalttafel.artefakte.Artefakt;
import schalttafel.artefakte.Button1;
import schalttafel.artefakte.Hebel1;
import schalttafel.artefakte.Rad1;
import schalttafel.artefakte.Schalter2;
import schalttafel.artefakte.Schieber1;
import schalttafel.artefakte.Schieber2;
import amq.RemoteEmpfaenger;
import amqdata.Hand;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;

public class CopyOfAnzeige_Remote2 extends Anzeige {
	
	RemoteEmpfaenger empfaenger;

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

	long lasttime = System.currentTimeMillis();
	long deltaTime = 0;

	boolean toggle = false;

	public static void main(String[] args) {
		CopyOfAnzeige_Remote2 app = new CopyOfAnzeige_Remote2();
		app.start();
	}

	@Override
	public void simpleInitApp() {
		
		empfaenger = new RemoteEmpfaenger();

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

		handRechts.init(physic,assetManager, new Vector3f(1.5f, 1, 0.2f));
		handLinks.init(physic,assetManager, new Vector3f(-0.5f, 1, 0.2f));

		artefakte.add(rad1);
		artefakte.add(rad2);
		artefakte.add(rad3);

		artefakte.add(hebel1);
		artefakte.add(hebel2);
		
		artefakte.add(schieber1);
		artefakte.add(schieber2);

		artefakte.add(button1);
		artefakte.add(button2);

		//artefakte.add(schalter1);

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
		Hand h = empfaenger.getLinks();
		
		handLinks.update(h.getX(), h.getY(), h.getZ(), h.isGrab());
		
		h = empfaenger.getRechts();
		handRechts.update(h.getX(), h.getY(), h.getZ(), h.isGrab());
		
	}

	private void refreshTime() {
		long time = System.currentTimeMillis();
		deltaTime = time - lasttime;
		lasttime = time;
	}
	
	private void setupKeys() {
		inputManager.addMapping("ende", new KeyTrigger(KeyInput.KEY_ESCAPE));
		inputManager.addListener(this, "ende");
	}
	
	public void onAnalog(String binding, float value, float tpf) {
		switch (binding) {
		case "ende":
			empfaenger.setWeiter(false);
			break;
		default:
			break;
		}
	}
	

//	private void setupKeys() {
//
//		inputManager.addMapping("la_hoch", new KeyTrigger(KeyInput.KEY_T));
//		inputManager.addMapping("la_runter", new KeyTrigger(KeyInput.KEY_G));
//		inputManager.addMapping("la_links", new KeyTrigger(KeyInput.KEY_F));
//		inputManager.addMapping("la_rechts", new KeyTrigger(KeyInput.KEY_H));
//		inputManager.addMapping("la_greifen", new KeyTrigger(KeyInput.KEY_B));
//		inputManager.addMapping("la_rein", new KeyTrigger(KeyInput.KEY_R));
//		inputManager.addMapping("la_raus", new KeyTrigger(KeyInput.KEY_Z));
//
//		inputManager.addMapping("ra_hoch", new KeyTrigger(KeyInput.KEY_I));
//		inputManager.addMapping("ra_runter", new KeyTrigger(KeyInput.KEY_K));
//		inputManager.addMapping("ra_links", new KeyTrigger(KeyInput.KEY_J));
//		inputManager.addMapping("ra_rechts", new KeyTrigger(KeyInput.KEY_L));
//		inputManager.addMapping("ra_greifen", new KeyTrigger(KeyInput.KEY_M));
//		inputManager.addMapping("ra_rein", new KeyTrigger(KeyInput.KEY_U));
//		inputManager.addMapping("ra_raus", new KeyTrigger(KeyInput.KEY_O));
//
//		inputManager.addListener(this, "la_hoch", "la_runter", "la_links", "la_rechts", "la_greifen", "la_rein",
//				"la_raus", "ra_hoch", "ra_runter", "ra_links", "ra_rechts", "ra_greifen", "ra_rein", "ra_raus");
//	}

//	public void onAnalog(String binding, float value, float tpf) {
//
//		switch (binding) {
//		case "la_hoch":
//			handLinks.hoch(deltaTime);
//			break;
//		case "la_runter":
//			handLinks.runter(deltaTime);
//			break;
//		case "la_links":
//			handLinks.links(deltaTime);
//			break;
//		case "la_rechts":
//			handLinks.rechts(deltaTime);
//			break;
//		case "la_rein":
//			handLinks.rein(deltaTime);
//			break;
//		case "la_raus":
//			handLinks.raus(deltaTime);
//			break;
//		case "la_greifen":
//			handLinks.toggleGreifen(dichtesterGriff(handLinks));
//			break;
//		case "ra_hoch":
//			handRechts.hoch(deltaTime);
//			break;
//		case "ra_runter":
//			handRechts.runter(deltaTime);
//			break;
//		case "ra_links":
//			handRechts.links(deltaTime);
//			break;
//		case "ra_rechts":
//			handRechts.rechts(deltaTime);
//			break;
//		case "ra_rein":
//			handRechts.rein(deltaTime);
//			break;
//		case "ra_raus":
//			handRechts.raus(deltaTime);
//			break;
//		case "ra_greifen":
//			handRechts.toggleGreifen(dichtesterGriff(handRechts));
//			break;
//
//		default:
//			break;
//		}
//	}

}

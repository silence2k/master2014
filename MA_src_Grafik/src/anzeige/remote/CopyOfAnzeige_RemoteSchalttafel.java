package anzeige.remote;

import schalttafel.Schalttafel;
import schalttafel.anzeige.AnzeigerLampe;
import schalttafel.anzeige.AnzeigerPfeil;
import schalttafel.anzeige.AnzeigerStab;
import schalttafel.artefakte.Artefakt;
import schalttafel.artefakte.Button1;
import schalttafel.artefakte.Griff1;
import schalttafel.artefakte.Hebel1;
import schalttafel.artefakte.Hebel2;
import schalttafel.artefakte.Rad1;
import schalttafel.artefakte.Rad2;
import schalttafel.artefakte.Schalter2;
import schalttafel.artefakte.Schieber1;
import schalttafel.artefakte.Schieber2;
import schalttafel.verkleidung.Hebel1Verkleidung;
import schalttafel.verkleidung.Hebel2Verkleidung;
import schalttafel.verkleidung.Schieber1Verkleidung;
import schalttafel.verkleidung.Schieber2Verkleidung;

import com.jme3.app.StatsAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

public class CopyOfAnzeige_RemoteSchalttafel extends RemoteAnzeige {

	Schalttafel schalttafel = new Schalttafel();

	Node node = new Node();
	int state = 9;

	long warteZeit;

	long lasttime = System.currentTimeMillis();
	long deltaTime = 0;

	boolean toggle = false;

	public static void main(String[] args) {
		CopyOfAnzeige_RemoteSchalttafel app = new CopyOfAnzeige_RemoteSchalttafel();
		AppSettings newSetting = new AppSettings(false);
		newSetting.setFrameRate(50);
		app.setSettings(newSetting);
		app.start();
	}

	@Override
	public void simpleInitApp() {
		
		super.simpleInitApp();

		warteZeit = System.currentTimeMillis();

//		if (stateManager.getState(StatsAppState.class) != null) {
//			stateManager.getState(StatsAppState.class).toggleStats();
//		}

		this.flyCam.setEnabled(false);

		setupKeys();

		cam.setLocation(new Vector3f(0f, 0f, 5f));

		rootNode.attachChild(schalttafel.init(physic, assetManager, new Vector3f(0, 0, 0)));

		rootNode.attachChild(node);

		switchState(state);

		// rootNode.attachChild(rad1.init(physic, assetManager, new Vector3f(0,
		// 0, 0)));
		// rootNode.attachChild(as1.init(physic, assetManager, new Vector3f(0,
		// 1, 0)));
		// rad1.setAnzeiger(as1);

		handRechts.init(physic, assetManager, new Vector3f(1, 0, 0.2f));
		handLinks.init(physic, assetManager, new Vector3f(-1, 0, 0.2f));

		// artefakte.add(rad1);

		/** You must add a light to make the model visible */
		DirectionalLight sun = new DirectionalLight();
		sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f).normalizeLocal());
		rootNode.addLight(sun);
	}

//	@Override
//	public void simpleUpdate(float tpf) {
//		// TODO Auto-generated method stub
//		super.simpleUpdate(tpf);
//		refreshTime();
//
//		for (Artefakt arte : artefakte) {
//			arte.update(deltaTime);
//		}
//	}

	private void refreshTime() {
		long time = System.currentTimeMillis();
		deltaTime = time - lasttime;
		lasttime = time;
	}

	private void setupKeys() {

		inputManager.addMapping("next", new KeyTrigger(KeyInput.KEY_T));
		inputManager.addMapping("prev", new KeyTrigger(KeyInput.KEY_R));

		inputManager.addMapping("ra_hoch", new KeyTrigger(KeyInput.KEY_I));
		inputManager.addMapping("ra_runter", new KeyTrigger(KeyInput.KEY_K));
		inputManager.addMapping("ra_links", new KeyTrigger(KeyInput.KEY_J));
		inputManager.addMapping("ra_rechts", new KeyTrigger(KeyInput.KEY_L));
		inputManager.addMapping("ra_greifen", new KeyTrigger(KeyInput.KEY_M));
		inputManager.addMapping("ra_rein", new KeyTrigger(KeyInput.KEY_U));
		inputManager.addMapping("ra_raus", new KeyTrigger(KeyInput.KEY_O));

		inputManager.addListener(this, "next", "prev", "ra_hoch", "ra_runter", "ra_links", "ra_rechts", "ra_greifen",
				"ra_rein", "ra_raus");
	}

	public void onAnalog(String binding, float value, float tpf) {
		super.onAnalog(binding, value, tpf);
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
		case "next":
			switchState(1);
			break;
		case "prev":
			switchState(-1);
			break;
		default:
			break;
		}
	}

	private void switchState(int dx) {
		if (System.currentTimeMillis() > warteZeit) {
			warteZeit = System.currentTimeMillis() + 500;
			state += dx;
			if (this.state > 9)
				this.state = 0;
			if (this.state < 0) {
				this.state = 9;
			}

			node.detachAllChildren();
			artefakte.clear();
			switch (state) {
			case 0:
				hebelTafel(1, -0.5f, 1, 1);
				break;
			case 1:
				hebel2Tafel(1, -0.5f, 1, 1);
				break;
			case 2:
				radTafel(1, -0.5f, 1, 1);
				break;
			case 3:
				rad2Tafel(1, -0.5f, 1, 1);
				break;
			case 4:
				schieberTafel(0.4f, -0.85f, 0.4f, 0.3f);

				break;
			case 5:
				schieber2Tafel(0.4f, -0.4f, 0.6f, 0.3f);
				break;
			case 6:
				knopfTafel(1, -0.5f, 1, 1);
				break;
			case 7:
				kippschalterTafel(1, -0.5f, 1, 1);
				break;
			case 8:
				griffTafel(1, -0.5f, 1, 1);
				break;
			case 9:
				komplettesSchaltpultTafel();
				break;

			default:
				break;
			}
		}

	}

	private void hebelTafel(float x1, float y1, float x2, float y2) {
		Hebel1 hebel1 = new Hebel1();
		AnzeigerLampe lampe1 = new AnzeigerLampe();
		Hebel1Verkleidung verkleidung = new Hebel1Verkleidung();
		node.attachChild(hebel1.init(physic, assetManager, new Vector3f(x1, y1, 0)));
		node.attachChild(verkleidung.init(physic, assetManager, new Vector3f(x1, y1, 0)));

		node.attachChild(lampe1.init(physic, assetManager, new Vector3f(x2, y2, 0)));
		hebel1.setAnzeiger(lampe1);
		artefakte.add(hebel1);
	}

	private void hebel2Tafel(float x1, float y1, float x2, float y2) {
		Hebel2 hebel2 = new Hebel2();
		AnzeigerLampe lampe1 = new AnzeigerLampe();
		Hebel2Verkleidung verkleidung = new Hebel2Verkleidung();
		node.attachChild(hebel2.init(physic, assetManager, new Vector3f(x1, y1, 0)));
		node.attachChild(verkleidung.init(physic, assetManager, new Vector3f(x1, y1, 0)));

		node.attachChild(lampe1.init(physic, assetManager, new Vector3f(x2, y2, 0)));
		hebel2.setAnzeiger(lampe1);
		artefakte.add(hebel2);
	}

	private void radTafel(float x1, float y1, float x2, float y2) {
		Rad1 rad = new Rad1();
		AnzeigerStab stab = new AnzeigerStab();
		node.attachChild(rad.init(physic, assetManager, new Vector3f(x1, y1, 0)));
		node.attachChild(stab.init(physic, assetManager, new Vector3f(x2, y2, 0)));
		rad.setAnzeiger(stab);
		artefakte.add(rad);
	}

	private void rad2Tafel(float x1, float y1, float x2, float y2) {
		Rad2 rad = new Rad2();
		AnzeigerStab stab = new AnzeigerStab();
		node.attachChild(rad.init(physic, assetManager, new Vector3f(x1, y1, 0)));
		node.attachChild(stab.init(physic, assetManager, new Vector3f(x2, y2, 0)));
		rad.setAnzeiger(stab);
		artefakte.add(rad);
	}

	private void schieberTafel(float x1, float y1, float x2, float y2) {
		Schieber1 schieber = new Schieber1();
		AnzeigerPfeil pfeil = new AnzeigerPfeil();
		Schieber1Verkleidung verkleidung = new Schieber1Verkleidung();
		node.attachChild(schieber.init(physic, assetManager, new Vector3f(x1, y1, 0)));
		node.attachChild(verkleidung.init(physic, assetManager, new Vector3f(x1, y1, 0)));
		node.attachChild(pfeil.init(physic, assetManager, new Vector3f(x2, y2, 0)));
		schieber.setAnzeiger(pfeil);
		artefakte.add(schieber);
	}

	private void schieber2Tafel(float x1, float y1, float x2, float y2) {
		Schieber2 schieber = new Schieber2();
		AnzeigerPfeil pfeil = new AnzeigerPfeil();
		Schieber2Verkleidung verkleidung = new Schieber2Verkleidung();
		node.attachChild(schieber.init(physic, assetManager, new Vector3f(x1, y1, 0)));
		node.attachChild(verkleidung.init(physic, assetManager, new Vector3f(x1, y1, 0)));
		node.attachChild(pfeil.init(physic, assetManager, new Vector3f(x2, y2, 0)));
		schieber.setAnzeiger(pfeil);
		artefakte.add(schieber);
	}

	private void knopfTafel(float x1, float y1, float x2, float y2) {
		Button1 button = new Button1();
		AnzeigerLampe lampe1 = new AnzeigerLampe();
		node.attachChild(button.init(physic, assetManager, new Vector3f(x1, y1, -0.05f)));
		node.attachChild(lampe1.init(physic, assetManager, new Vector3f(x2, y2, 0)));
		button.setAnzeiger(lampe1);
		artefakte.add(button);
	}

	private void kippschalterTafel(float x1, float y1, float x2, float y2) {
		Schalter2 schalter = new Schalter2();
		AnzeigerLampe lampe1 = new AnzeigerLampe();
		node.attachChild(schalter.init(physic, assetManager, new Vector3f(x1, y1, 0)));
		node.attachChild(lampe1.init(physic, assetManager, new Vector3f(x2, y2, 0)));
		schalter.setAnzeiger(lampe1);
		artefakte.add(schalter);
	}

	private void griffTafel(float x1, float y1, float x2, float y2) {
		Griff1 griff = new Griff1();
		AnzeigerLampe lampe1 = new AnzeigerLampe();
		node.attachChild(griff.init(physic, assetManager, new Vector3f(x1, y1, 0)));
		node.attachChild(lampe1.init(physic, assetManager, new Vector3f(x2, y2, 0)));
		griff.setAnzeiger(lampe1);
		artefakte.add(griff);
	}

	private void komplettesSchaltpultTafel() {
		float yhebel = -0.8f;
		float yhebellampe = -0.1f;
		hebelTafel(-0.5f, yhebel, -0.5f, yhebellampe);
		hebelTafel(-1.1f, yhebel, -1.1f, yhebellampe);
		hebel2Tafel(-1.8f, yhebel, -1.8f, yhebellampe);

		schieberTafel(0.4f, -1.25f, 0.4f, -0.3f);
		schieber2Tafel(0.8f, -0.7f, 1, -0.3f);

		float yRad = 0.5f;
		float yRadStab = 1.0f;
		radTafel(-1.8f, yRad, -1.8f, yRadStab);
		radTafel(-1.1f, yRad, -1.1f, yRadStab);
		rad2Tafel(-0.4f, yRad, -0.4f, yRadStab);

		float yKnopf = 0.5f;
		float yKnopfLampe = 1.0f;
		knopfTafel(0.5f, yKnopf, 0.5f, yKnopfLampe);
		knopfTafel(1, yKnopf, 1, yKnopfLampe);
		kippschalterTafel(1.5f, yKnopf, 1.5f, yKnopfLampe);

		griffTafel(2,yKnopf, 2, yKnopfLampe);

	}

}

package anzeige.bastel;

import schalttafel.Schalttafel;
import schalttafel.anzeige.AnzeigerLampe;
import schalttafel.anzeige.AnzeigerPfeil;
import schalttafel.anzeige.AnzeigerStab;
import schalttafel.artefakte.Artefakt;
import schalttafel.artefakte.Hebel1;
import schalttafel.artefakte.Hebel2;
import schalttafel.artefakte.Rad1;
import schalttafel.artefakte.Schieber1;
import schalttafel.artefakte.Schieber2;
import anzeige.Anzeige;

import com.jme3.app.StatsAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class Anzeige_Schalttafel2 extends Anzeige {

	Schalttafel schalttafel = new Schalttafel();
	
	
	Node node = new Node();
	int state = 0;
	
	long warteZeit;

	long lasttime = System.currentTimeMillis();
	long deltaTime = 0;

	boolean toggle = false;

	public static void main(String[] args) {
		Anzeige_Schalttafel2 app = new Anzeige_Schalttafel2();
		app.start();
	}

	@Override
	public void simpleInitApp() {
		
		warteZeit = System.currentTimeMillis();

		if (stateManager.getState(StatsAppState.class) != null) {
			stateManager.getState(StatsAppState.class).toggleStats();
		}
		
		this.flyCam.setEnabled(false);
		
		setupKeys();

		cam.setLocation(new Vector3f(0f, 0f, 5f));

		rootNode.attachChild(schalttafel.init(physic, assetManager, new Vector3f(0, 0, 0)));
		
		rootNode.attachChild(node);
		
		switchState(state);

//		rootNode.attachChild(rad1.init(physic, assetManager, new Vector3f(0, 0, 0)));
//		rootNode.attachChild(as1.init(physic, assetManager, new Vector3f(0, 1, 0)));
//		rad1.setAnzeiger(as1);

		handRechts.init(physic, assetManager, new Vector3f(1, 0, 0.2f));
		// rootNode.attachChild(handLinks.init(physic, assetManager, new
		// Vector3f(-1, 0, 0.2f)));

//		artefakte.add(rad1);

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

		inputManager.addMapping("next", new KeyTrigger(KeyInput.KEY_T));
		inputManager.addMapping("prev", new KeyTrigger(KeyInput.KEY_R));

		inputManager.addMapping("ra_hoch", new KeyTrigger(KeyInput.KEY_I));
		inputManager.addMapping("ra_runter", new KeyTrigger(KeyInput.KEY_K));
		inputManager.addMapping("ra_links", new KeyTrigger(KeyInput.KEY_J));
		inputManager.addMapping("ra_rechts", new KeyTrigger(KeyInput.KEY_L));
		inputManager.addMapping("ra_greifen", new KeyTrigger(KeyInput.KEY_M));
		inputManager.addMapping("ra_rein", new KeyTrigger(KeyInput.KEY_U));
		inputManager.addMapping("ra_raus", new KeyTrigger(KeyInput.KEY_O));

		inputManager.addListener(this, "next", "prev", "ra_hoch", "ra_runter", "ra_links", "ra_rechts", "ra_greifen", "ra_rein", "ra_raus");
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
		if(System.currentTimeMillis() > warteZeit){
			warteZeit = System.currentTimeMillis() + 1000;
			this.state = (this.state + dx)%4;
		node.detachAllChildren();
		artefakte.clear();
		switch (state) {
		case 0:
			hebelTafel();
			break;
		case 1:
			hebel2Tafel();
			break;
		case 2:
			schieberTafel();
			break;
		case 3:
			schieber2Tafel();
			break;

		default:
			break;
		}
		}

	}

	private void hebelTafel() {
		Hebel1 hebel1 = new Hebel1();
		AnzeigerLampe lampe1 = new AnzeigerLampe();
		node.attachChild(hebel1.init(physic, assetManager, new Vector3f(0, 0, 0)));
		node.attachChild(lampe1.init(physic, assetManager, new Vector3f(0, 1, 0)));
		hebel1.setAnzeiger(lampe1);
		artefakte.add(hebel1);
	}
	private void hebel2Tafel() {
		Hebel2 hebel2 = new Hebel2();
		AnzeigerLampe lampe1 = new AnzeigerLampe();
		node.attachChild(hebel2.init(physic, assetManager, new Vector3f(0, 0, 0)));
		node.attachChild(lampe1.init(physic, assetManager, new Vector3f(0, 1, 0)));
		hebel2.setAnzeiger(lampe1);
		artefakte.add(hebel2);
	}

	private void schieberTafel() {
		Schieber1 schieber = new Schieber1();
		AnzeigerPfeil pfeil = new AnzeigerPfeil();
		node.attachChild(schieber.init(physic, assetManager, new Vector3f(0, 0, 0)));
		node.attachChild(pfeil.init(physic, assetManager, new Vector3f(0, 1, 0)));
		schieber.setAnzeiger(pfeil);
		artefakte.add(schieber);
	}
	
	private void schieber2Tafel() {
		Schieber2 schieber = new Schieber2();
		AnzeigerPfeil pfeil = new AnzeigerPfeil();
		node.attachChild(schieber.init(physic, assetManager, new Vector3f(0, 0, 0)));
		node.attachChild(pfeil.init(physic, assetManager, new Vector3f(0, 1, 0)));
		schieber.setAnzeiger(pfeil);
		artefakte.add(schieber);
	}

}

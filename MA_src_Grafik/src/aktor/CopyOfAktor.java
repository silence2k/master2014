package aktor;

import schalttafel.artefakte.AktorGriff;
import schalttafel.artefakte.Greifbar;
import anzeige.Anzeige;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.material.MatParam;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.TangentBinormalGenerator;

public class CopyOfAktor {

	private enum Zustand {
		offen, gegriffen, nichtsgegriffen
	}

	/* config */

	// geschwindigkeit pro sekunde
	final float speed = 1f;

	// entfernung ab wann ein objekt von der hand gegriffen werden kann
	final float maxgreifen = 0.3f;

	// nach welcher zeit kann man den zustand der hand aendern
	final long toggleTime = 400;

	/* ******************** */

	Anzeige anzeige;

	// private Spatial graficObject;
	//
	// private Spatial graOffen;
	//
	// private Spatial graGegriffen;

	private GraHand graHand;

	private Zustand zustand = Zustand.offen;

	private long lastToggle = System.currentTimeMillis();

	private Material mat_lit = null;

	private AktorGriff griff;

	private AudioNode audioGreifen;

	private AudioNode audioNichtGreifen;

	private boolean links;

	private boolean physic;

	public CopyOfAktor(Anzeige anzeige, boolean links) {
		super();
		this.anzeige = anzeige;
		this.links = links;

	}

	public void init(boolean physic, AssetManager assetManager, Vector3f position) {
		/**
		 * A bumpy rock with a shiny light effect. To make bumpy objects you
		 * must create a NormalMap.
		 */

		audioGreifen = new AudioNode(assetManager, "sound/greifen.wav", false);
		audioGreifen.setLooping(false);

		audioNichtGreifen = new AudioNode(assetManager, "sound/nichtgreifen.wav", false);
		audioNichtGreifen.setLooping(false);

		if (physic) {
			initPhysic(assetManager, position);
		} else {
			initGraphic(assetManager, position);
		}
		this.physic = physic;

		// return graficObject;
	}

	private void initPhysic(AssetManager assetManager, Vector3f position) {
		Sphere rock = new Sphere(16, 16, 0.15f);
		Spatial graficObject = new Geometry("Aktor", rock);
		rock.setTextureMode(Sphere.TextureMode.Projected); // better quality on
															// spheres
		TangentBinormalGenerator.generate(rock); // for lighting effect
		mat_lit = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
		mat_lit.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Terrain/Pond/Pond.jpg"));
		mat_lit.setTexture("NormalMap", assetManager.loadTexture("Textures/Terrain/Pond/Pond_normal.png"));
		mat_lit.setBoolean("UseMaterialColors", true);
		mat_lit.setColor("Specular", ColorRGBA.White);
		mat_lit.setColor("Diffuse", ColorRGBA.White);
		mat_lit.setFloat("Shininess", 5f); // [0,128]
		graficObject.setMaterial(mat_lit);
		graficObject.setLocalTranslation(position); // Move it a bit
		graficObject.rotate(1.6f, 0, 0); // Rotate it a bit

		graHand = new GraHand(graficObject, mat_lit);
	}

	private void initGraphic(AssetManager assetManager, Vector3f position) {
		if (links) {
			graHand = new GraHand(assetManager, "obj/hand/linksOffen.obj", "obj/hand/linksGegriffen.obj");
		} else {
			graHand = new GraHand(assetManager, "obj/hand/rechtsOffen.obj", "obj/hand/rechtsGegriffen.obj");
		}

		graHand.setLocalTranslation(position);

	}

	public void update(float x, float y, float z, boolean greifen) {
		// System.out.println("x: "+x+" y: "+y+" z: "+z);
		
		if(z < 0){
			z = 0;
		}
		if(z > 1){
			z = 1f;
		}
		
		graHand.setLocalTranslation(x, y, z);
		greifen(greifen);
	}

	private float delta(long deltaTime) {
		return (float) (deltaTime / 1000.0 * speed);
	}

	public void hoch(long deltaTime) {
		Vector3f v3 = graHand.getLocalTranslation();
		graHand.setLocalTranslation(v3.x, v3.y + delta(deltaTime), v3.z);
	}

	public void runter(long deltaTime) {
		Vector3f v3 = graHand.getLocalTranslation();
		graHand.setLocalTranslation(v3.x, v3.y - delta(deltaTime), v3.z);
	}

	public void links(long deltaTime) {
		Vector3f v3 = graHand.getLocalTranslation();
		graHand.setLocalTranslation(v3.x - delta(deltaTime), v3.y, v3.z);
	}

	public void rechts(long deltaTime) {
		Vector3f v3 = graHand.getLocalTranslation();
		graHand.setLocalTranslation(v3.x + delta(deltaTime), v3.y, v3.z);
	}

	public void rein(long deltaTime) {
		Vector3f v3 = graHand.getLocalTranslation();
		graHand.setLocalTranslation(v3.x, v3.y, v3.z - delta(deltaTime));
	}

	public void raus(long deltaTime) {
		Vector3f v3 = graHand.getLocalTranslation();
		graHand.setLocalTranslation(v3.x, v3.y, v3.z + delta(deltaTime));
	}

	private boolean isGreifbar(Greifbar greifbaresObject) {
		if (greifbaresObject != null) {
			float distance = greifbaresObject.distanceFreierGriff(this);
			return greifbaresObject.isGreifbar() && distance < maxgreifen;
		}
		return false;
	}

	public void toggleGreifen() {
		AktorGriff tmpGriff = anzeige.dichtesterGriff(this);
		if (System.currentTimeMillis() - toggleTime > lastToggle) {
			lastToggle = System.currentTimeMillis();

			if (tmpGriff!= null &&zustand == Zustand.offen) {
				if (tmpGriff.isGreifbar() && tmpGriff.distance(this) < maxgreifen) {
					graHand.greifen();

					zustand = Zustand.gegriffen;

					this.griff = tmpGriff;
					this.griff.greifen(this);

					audioGreifen.playInstance();

				} else {
					setNichtgreifen();

				}

			} else {
				graHand.oeffnen();

				zustand = Zustand.offen;
				if (this.griff != null) {
					this.griff.loslassen();
					this.griff = null;
				}

			}
		}

	}

	private void greifen(boolean greifen) {
		if (greifen == false && zustand != Zustand.offen) {
			toggleGreifen();
		} else if (greifen == true && zustand == Zustand.offen) {
			toggleGreifen();
		}
	}

	private void setNichtgreifen() {
		zustand = Zustand.nichtsgegriffen;
		graHand.nichtGreifen();

		audioNichtGreifen.playInstance();
	}

	public Vector3f getLocalTranslation() {
		return graHand.getLocalTranslation();
	}

	class GraHand {
		private boolean kugel = false;

		private Spatial graficObject;

		private Material mat;

		private Geometry graOffen;

		private Geometry graGegriffen;

		private ColorRGBA normal;

		private ColorRGBA rot;

		public GraHand(Spatial graficObject, Material mat) {
			this.graficObject = graficObject;
			this.mat = mat;
			kugel = true;
		}

		public GraHand(AssetManager assetManager, String pfadOffen, String pfadGeschlossen) {
			graOffen = (Geometry) assetManager.loadModel(pfadOffen);
			// graOffenMat = new MyMaterial(graOffen.getMaterial());

			graGegriffen = (Geometry) assetManager.loadModel(pfadGeschlossen);
			// graGegriffenMat = new MyMaterial(graGegriffen.getMaterial());

			Material m = graGegriffen.getMaterial();
			MatParam vColor = m.getParam("Diffuse");
			normal = new ColorRGBA((ColorRGBA) vColor.getValue());
			rot = new ColorRGBA(1, 0, 0, normal.a);

			graficObject = graOffen;
			anzeige.getRootNode().attachChild(graficObject);
		}

		public void oeffnen() {
			if (kugel) {
				setColor(ColorRGBA.White);
			} else {

				anzeige.getRootNode().detachChild(graficObject);
				graOffen.setLocalTranslation(graficObject.getLocalTranslation());
				graficObject = graOffen;
				anzeige.getRootNode().attachChild(graficObject);
			}
		}

		public void greifen() {
			if (kugel) {
				setColor(ColorRGBA.Green);
			} else {
				anzeige.getRootNode().detachChild(graficObject);
				graGegriffen.setLocalTranslation(graficObject.getLocalTranslation());
				graficObject = graGegriffen;
				anzeige.getRootNode().attachChild(graficObject);

				Material m = graGegriffen.getMaterial();
				MatParam vColor = m.getParam("Diffuse");
				vColor.setValue(normal);
			}
		}

		public void nichtGreifen() {
			if (kugel) {
				setColor(ColorRGBA.Red);
			} else {
				anzeige.getRootNode().detachChild(graficObject);
				graGegriffen.setLocalTranslation(graficObject.getLocalTranslation());
				graficObject = graGegriffen;
				anzeige.getRootNode().attachChild(graficObject);

				Material m = graGegriffen.getMaterial();
				MatParam vColor = m.getParam("Diffuse");
				vColor.setValue(rot);
			}
		}

		public void setLocalTranslation(float x, float y, float z) {
			graficObject.setLocalTranslation(x, y, z);
		}

		public void setLocalTranslation(Vector3f localTranslation) {
			graficObject.setLocalTranslation(localTranslation);
		}

		public Vector3f getLocalTranslation() {
			return graficObject.getLocalTranslation();
		}

		private void setColor(ColorRGBA color) {
			mat.setColor("Specular", color);
			mat.setColor("Diffuse", color);
		}

	}

}

package aktor;

import schalttafel.artefakte.Greifbar;
import schalttafel.artefakte.Griff;
import anzeige.Anzeige;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.TangentBinormalGenerator;

public class Aktor {

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

	private Spatial graficObject;
	
	private Spatial graOffen;
	
	private Spatial graGegriffen;
	
	private Zustand zustand = Zustand.offen;

	private long lastToggle = System.currentTimeMillis();

	private Material mat_lit = null;

	private Griff griff;

	private AudioNode audioGreifen;

	private AudioNode audioNichtGreifen;
	
	private boolean links;
	
	private boolean physic;
	

	public Aktor(Anzeige anzeige, boolean links) {
		super();
		this.anzeige = anzeige;
		this.links = links;
		
	}

	public Spatial init(boolean physic, AssetManager assetManager, Vector3f position) {
		/**
		 * A bumpy rock with a shiny light effect. To make bumpy objects you
		 * must create a NormalMap.
		 */
		
		audioGreifen = new AudioNode(assetManager, "sound/greifen.wav", false);
		audioGreifen.setLooping(false);

		audioNichtGreifen = new AudioNode(assetManager, "sound/nichtgreifen.wav", false);
		audioNichtGreifen.setLooping(false);

		if(physic){
			initPhysic(assetManager, position);
		}else{
			initGraphic(assetManager, position);
		}
		this.physic = physic;
		
		return graficObject;
	}
	
	
	private void initPhysic(AssetManager assetManager, Vector3f position) {
		Sphere rock = new Sphere(16, 16, 0.15f);
		graficObject = new Geometry("Aktor", rock);
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
	}
	
	private void initGraphic(AssetManager assetManager, Vector3f position) {
		if(links){
			graOffen = assetManager.loadModel("obj/hand/linksOffen.obj");
			graGegriffen = assetManager.loadModel("obj/hand/linksGegriffen.obj");
		}else{
			graOffen = assetManager.loadModel("obj/hand/rechtsOffen.obj");
			graGegriffen = assetManager.loadModel("obj/hand/rechtsGegriffen.obj");
		}
		graficObject = graOffen;
		graficObject.setLocalTranslation(position);
	}
	
	
	
	public void update(float x, float y, float z, boolean greifen){
		//System.out.println("x: "+x+" y: "+y+" z: "+z);
		graficObject.setLocalTranslation(x, y, z);
		greifen(greifen);
	}

	private float delta(long deltaTime) {
		return (float) (deltaTime / 1000.0 * speed);
	}

	public void hoch(long deltaTime) {
		Vector3f v3 = graficObject.getLocalTranslation();
		graficObject.setLocalTranslation(v3.x, v3.y + delta(deltaTime), v3.z);
	}

	public void runter(long deltaTime) {
		Vector3f v3 = graficObject.getLocalTranslation();
		graficObject.setLocalTranslation(v3.x, v3.y - delta(deltaTime), v3.z);
	}

	public void links(long deltaTime) {
		Vector3f v3 = graficObject.getLocalTranslation();
		graficObject.setLocalTranslation(v3.x - delta(deltaTime), v3.y, v3.z);
	}

	public void rechts(long deltaTime) {
		Vector3f v3 = graficObject.getLocalTranslation();
		graficObject.setLocalTranslation(v3.x + delta(deltaTime), v3.y, v3.z);
	}

	public void rein(long deltaTime) {
		Vector3f v3 = graficObject.getLocalTranslation();
		graficObject.setLocalTranslation(v3.x, v3.y, v3.z - delta(deltaTime));
	}

	public void raus(long deltaTime) {
		Vector3f v3 = graficObject.getLocalTranslation();
		graficObject.setLocalTranslation(v3.x, v3.y, v3.z + delta(deltaTime));
	}

	private boolean isGreifbar(Greifbar greifbaresObject) {
		if (greifbaresObject != null) {
			float distance = greifbaresObject.distanceFreierGriff(this);
			return greifbaresObject.isGreifbar() && distance < maxgreifen;
		}
		return false;
	}

	private void setColor(ColorRGBA color) {
		mat_lit.setColor("Specular", color);
		mat_lit.setColor("Diffuse", color);
	}
	
	private void greifen(){
		anzeige.getRootNode().detachChild(graficObject);
		graGegriffen.setLocalTranslation(graficObject.getLocalTranslation());
		graficObject = graGegriffen;
		anzeige.getRootNode().attachChild(graficObject);
	}
	
	private void oeffnen(){
		anzeige.getRootNode().detachChild(graficObject);
		graOffen.setLocalTranslation(graficObject.getLocalTranslation());
		graficObject = graOffen;
		anzeige.getRootNode().attachChild(graficObject);
	}

	public void toggleGreifen() {
		Griff tmpGriff = anzeige.dichtesterGriff(this);
		if (System.currentTimeMillis() - toggleTime > lastToggle) {
			lastToggle = System.currentTimeMillis();

			if (zustand == Zustand.offen) {
				if (tmpGriff.isGreifbar() && tmpGriff.distance(this) < maxgreifen) {
					if(physic){
						setColor(ColorRGBA.Green);
					}else{
						greifen();
					}
					
					zustand = Zustand.gegriffen;

					this.griff = tmpGriff;
					this.griff.greifen(this);

					audioGreifen.playInstance();

				} else {
					setNichtgreifen();

					
				}

			} else {
				if(physic){
					setColor(ColorRGBA.White);
				}else{
					oeffnen();
				}
				
				zustand = Zustand.offen;
				if (this.griff != null) {
					this.griff.loslassen();
					this.griff = null;
				}

			}
		}

	}
	
	private void greifen(boolean greifen) {
		if(greifen == false && zustand != Zustand.offen){
			toggleGreifen();
		}else if(greifen == true && zustand == Zustand.offen){
			toggleGreifen();
		}
	}

	private void setNichtgreifen() {
		zustand = Zustand.nichtsgegriffen;
		if(physic){
			setColor(ColorRGBA.Red);
		}else{
			greifen();
		}
		
		audioNichtGreifen.playInstance();
	}

	public Vector3f getLocalTranslation() {
		return graficObject.getLocalTranslation();
	}
}

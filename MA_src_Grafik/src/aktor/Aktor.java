package aktor;

import schalttafel.artefakte.Artefakt;
import schalttafel.artefakte.Greifbar;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.TangentBinormalGenerator;

public class Aktor {
	
	private enum Zustand {
		offen, gegriffen, nichtsgegriffen
	}
	
	
	/* config  */
	
	// geschwindigkeit pro sekunde
		final float speed = 1f;
	
		// entfernung ab wann ein objekt von der hand gegriffen werden kann
		final float maxgreifen = 0.3f;
	
		// nach welcher zeit kann man den zustand der hand aendern
		final long toggleTime = 400;
		
		
	/* ******************** */

	private Geometry graficObject;

	private Zustand zustand = Zustand.offen;

	private long lastToggle = System.currentTimeMillis();

	private Material mat_lit = null;
	
	private Artefakt gegriffenesArtefakt;
	
	private AudioNode audioGreifen;
	
	private AudioNode audioNichtGreifen;
	
	
	

	public Geometry init(AssetManager assetManager, Vector3f position) {
		/**
		 * A bumpy rock with a shiny light effect. To make bumpy objects you
		 * must create a NormalMap.
		 */
		
		 audioGreifen = new AudioNode(assetManager, "sound/greifen.wav", false);
		 audioGreifen.setLooping(false);
		 
		 audioNichtGreifen = new AudioNode(assetManager, "sound/nichtgreifen.wav", false);
		 audioNichtGreifen.setLooping(false);
		
		
		
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
		return graficObject;
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
		graficObject.setLocalTranslation(v3.x, v3.y, v3.z + delta(deltaTime) );
	}
	
	

	private boolean isGreifbar(Greifbar greifbaresObject) {
		if (greifbaresObject != null) {
			float distance = greifbaresObject.distanceFreierGriff(this);
			return greifbaresObject.isGreifbar() && distance < maxgreifen;
		}
		return false;
	}
	
	private void setColor(ColorRGBA color){
		mat_lit.setColor("Specular", color);
		mat_lit.setColor("Diffuse", color);
	}
	

	public void toggleGreifen(Artefakt greifbaresObject) {
		if (System.currentTimeMillis() - toggleTime > lastToggle) {
			lastToggle = System.currentTimeMillis();

			if (zustand == Zustand.offen) {
				if (isGreifbar(greifbaresObject)) {
					setColor(ColorRGBA.Green);
					zustand = Zustand.gegriffen;
					
					gegriffenesArtefakt = greifbaresObject;
					greifbaresObject.setGreifbar(false);
					greifbaresObject.setAktor(this);
					
					audioGreifen.playInstance();
					
				} else {
					setNichtgreifen();
					
					audioNichtGreifen.playInstance();
				}

			} else {
				setColor(ColorRGBA.White);
				zustand = Zustand.offen;
				if(gegriffenesArtefakt!= null){
					gegriffenesArtefakt.setGreifbar(true);
					gegriffenesArtefakt.setAktor(null);
				}

			}
		}

	}
	
	public void setNichtgreifen(){
		zustand = Zustand.nichtsgegriffen;
		setColor(ColorRGBA.Red);
	}

	public Vector3f getLocalTranslation(){
		return graficObject.getLocalTranslation();
	}
}

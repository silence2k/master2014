package schalttafel.artefakte;

import java.util.List;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Button1 extends Artefakt {
	
	protected float translationDX = 0.01f;

	protected float translation = 0;
	
	private boolean pressed = false;
		
		protected AudioNode audioButtonPress;
			
		
		
		protected void init(){
			// Button draussen
			minTrans= 0f;
			
			// Button gedrueckt
			maxTrans = 0.2f;
		}
	

	
	@Override
	public Node init(AssetManager assetManager, Vector3f position) {
		init();
		
		
		audioButtonPress = new AudioNode(assetManager, "sound/buttonpress.wav", false);
		audioButtonPress.setLooping(false);
		
		/** Load a teapot model (OBJ file from test-data) */
		graficObject = (Node) assetManager.loadModel("obj/button1/button1.obj");

		List<Spatial> childs = graficObject.getChildren();
		for (Spatial spatial : childs) {
			System.out.println(spatial);
		}

		Geometry g = (Geometry) graficObject.getChild("button1-geom-1");
		Material mat_default = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat_default.setColor("Color", new ColorRGBA(0.5f, 0.5f, 0.5f, 1f));
		g.setMaterial(mat_default);

		g = (Geometry) graficObject.getChild("button1-geom-0");
		// mat_default = new Material( assetManager,
		// "Common/MatDefs/Misc/ShowNormals.j3md");
		griffmaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		griffmaterial.setColor("Color", new ColorRGBA(0f, 1f, 0f, 1f));
		g.setMaterial(griffmaterial);

		buildGriff1(new Vector3f(0, 0, 0.2f), assetManager);
		
		graficObject.setLocalTranslation(position);

		return graficObject;
	}

	@Override
	public void update() {
		if (aktor != null) {

			float distance = griff1.getWorldTranslation().distance(aktor.getLocalTranslation());

			graficObject.move(0,0, translationDX);

			float distanceRechts = griff1.getWorldTranslation().distance(aktor.getLocalTranslation());

			graficObject.move(0,0,-2f * translationDX);

			float distanceLinks = griff1.getWorldTranslation().distance(aktor.getLocalTranslation());
			
			graficObject.move(0, 0,translationDX);

			if (distance < distanceRechts) {
				if (distance < distanceLinks) {
					// nichts tun
				} else {
					myTranslate(griff1, distance, -translationDX);
				}
			} else {
				if (distanceRechts < distanceLinks) {
					myTranslate(griff1, distance, translationDX);
				} else {
					myTranslate(griff1, distance, -translationDX);
				}
			}
		}

	}

	private void myTranslate(Geometry g, float distance, float translationDX) {
		float oldDistance = distance;
		float newDistance = 0;
		while (true) {
			if(!isBeweglichMaxTrans(translationDX, translation)&&!pressed){
				audioButtonPress.play();
				break;
			}else if(!isBeweglichMinTrans(translationDX, translation)){
				pressed = false;
				break;
			}
			
			this.translation+= translationDX;
			
			graficObject.move(0,0 , translationDX);
			newDistance = g.getWorldTranslation().distance(aktor.getLocalTranslation());
			if (newDistance > oldDistance) {
				break;
			}
			oldDistance = newDistance;
		}

	}
	
	public void setGreifbar(boolean greifbar) {
		this.greifbar = greifbar;
		if (this.greifbar) {
			griffmaterial.setColor("Color", new ColorRGBA(0f, 1f, 0f, 1f));
			graficObject.setLocalTranslation(graficObject.getLocalTranslation().x, graficObject.getLocalTranslation().y, minTrans);
			translation = 0;
			pressed = false;
		} else {
			griffmaterial.setColor("Color", new ColorRGBA(1f, 0f, 0f, 1f));
		}

	}

}

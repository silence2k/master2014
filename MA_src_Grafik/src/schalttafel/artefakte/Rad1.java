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

public class Rad1 extends Rad {
	
	private AudioNode audioRaddreh;
	private AudioNode audioRadende;

	public Node init(AssetManager assetManager, Vector3f position) {
		init();
		
		audioRaddreh = new AudioNode(assetManager, "sound/raddreh.wav", false);
		audioRaddreh.setLooping(false);
		
		audioRadende = new AudioNode(assetManager, "sound/radende.wav", false);
		audioRadende.setLooping(false);
		
		
		
		graficObject = (Node) assetManager.loadModel("obj/rad1/rad1.obj");

		List<Spatial> childs = graficObject.getChildren();
		for (Spatial spatial : childs) {
			System.out.println(spatial);
		}

		Geometry g = (Geometry) graficObject.getChild("rad1-geom-0");
		Material mat_default = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat_default.setColor("Color", new ColorRGBA(0.5f, 0.5f, 0.5f, 1f));
		g.setMaterial(mat_default);

		g = (Geometry) graficObject.getChild("rad1-geom-1");
		// mat_default = new Material( assetManager,
		// "Common/MatDefs/Misc/ShowNormals.j3md");
		griffmaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		griffmaterial.setColor("Color", new ColorRGBA(0f, 1f, 0f, 1f));
		g.setMaterial(griffmaterial);

		buildGriff1(new Vector3f(0, 0.2f, 0), assetManager);

		graficObject.setLocalTranslation(position);

		return graficObject;
	}

	public void update() {

		if (aktor != null) {

			Geometry g = (Geometry) graficObject.getChild("griff1");

			float distance = g.getWorldTranslation().distance(aktor.getLocalTranslation());

			graficObject.rotate(0, 0, rotationDX);

			float distanceRechts = g.getWorldTranslation().distance(aktor.getLocalTranslation());

			graficObject.rotate(0, 0, -2f * rotationDX);

			float distanceLinks = g.getWorldTranslation().distance(aktor.getLocalTranslation());
			
			// wieder zurueckstellen
			graficObject.rotate(0, 0, rotationDX);
			
			if (distance < distanceRechts) {
				if (distance < distanceLinks) {
					// nichts tun
				} else {
					myRotate(g, distance, -rotationDX);
				}
			} else {
				if (distanceRechts < distanceLinks) {
					myRotate(g, distance, rotationDX);
				} else {
					myRotate(g, distance, -rotationDX);
				}
			}
		}

	}

	private void myRotate(Geometry g, float distance, float rotationDX) {
		float oldDistance = distance;
		float newDistance = 0;
		
		while (true) {
			if(!isBeweglichRotation(rotationDX, rotation)){
				audioRadende.play();
				break;
			}
			
			this.rotation+= rotationDX;
			graficObject.rotate(0, 0, rotationDX);
			newDistance = g.getWorldTranslation().distance(aktor.getLocalTranslation());
			if (newDistance > oldDistance) {
				//audioRaddreh.pause();
				break;
			}
			oldDistance = newDistance;
			audioRaddreh.play();
		}
		System.out.println("roation: "+ rotation);
	}

	public void rotate(long deltaTime) {

		float f = (float) (-0.5f * deltaTime / 1000.0);

		graficObject.rotate(0, 0, f);
	}



	
	

}

package schalttafel.artefakte;

import java.util.List;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Schieber1 extends Artefakt {
	
	
protected float translation = 0.01f;
	
	
	
	
	
	float grenzeOben = 0.6f;
	
	float grenzeUnten = 2.6f;
	
	
	
	protected void init(){
		// Heben oben max
		minTrans= 1f;
		
		// Heben unten max
		maxTrans = 0f;
	}
	
	
	

	@Override
	public void setGreifbar(boolean greifbar) {
		this.greifbar = greifbar;
		if (this.greifbar) {
			griffmaterial.setColor("Color", new ColorRGBA(0f, 1f, 0f, 1f));
		} else {
			griffmaterial.setColor("Color", new ColorRGBA(1f, 0f, 0f, 1f));
		}

	}

	@Override
	public Node init(AssetManager assetManager, Vector3f position) {
		/** Load a teapot model (OBJ file from test-data) */
		graficObject = (Node) assetManager.loadModel("obj/schieber1/schieber1.obj");

		List<Spatial> childs = graficObject.getChildren();
		for (Spatial spatial : childs) {
			System.out.println(spatial);
		}

		Geometry g = (Geometry) graficObject.getChild("schieber1-geom-1");
		Material mat_default = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat_default.setColor("Color", new ColorRGBA(0.5f, 0.5f, 0.5f, 1f));
		g.setMaterial(mat_default);

		g = (Geometry) graficObject.getChild("schieber1-geom-0");
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

			graficObject.move(0, translation, 0);

			float distanceRechts = griff1.getWorldTranslation().distance(aktor.getLocalTranslation());

			graficObject.move(0,-2f * translation, 0);

			float distanceLinks = griff1.getWorldTranslation().distance(aktor.getLocalTranslation());
			
			graficObject.move(0, translation, 0);

			if (distance < distanceRechts) {
				if (distance < distanceLinks) {
					// nichts tun
				} else {
					myTranslate(griff1, distance, -translation);
				}
			} else {
				if (distanceRechts < distanceLinks) {
					myTranslate(griff1, distance, translation);
				} else {
					myTranslate(griff1, distance, -translation);
				}
			}
		}

	}

	private void myTranslate(Geometry g, float distance, float rotation) {
		float oldDistance = distance;
		float newDistance = 0;
		while (true) {
			graficObject.move(0, rotation, 0);
			newDistance = g.getWorldTranslation().distance(aktor.getLocalTranslation());
			if (newDistance > oldDistance) {
				break;
			}
			oldDistance = newDistance;
		}

	}

}

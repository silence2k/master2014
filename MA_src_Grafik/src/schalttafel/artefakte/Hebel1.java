package schalttafel.artefakte;

import java.util.List;

import aktor.Aktor;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Hebel1 extends Hebel {

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
		graficObject = (Node) assetManager.loadModel("obj/hebel1/hebel1.obj");

		List<Spatial> childs = graficObject.getChildren();
		for (Spatial spatial : childs) {
			System.out.println(spatial);
		}

		Geometry g = (Geometry) graficObject.getChild("hebel1-geom-0");
		Material mat_default = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat_default.setColor("Color", new ColorRGBA(0.5f, 0.5f, 0.5f, 1f));
		g.setMaterial(mat_default);

		g = (Geometry) graficObject.getChild("hebel1-geom-1");
		// mat_default = new Material( assetManager,
		// "Common/MatDefs/Misc/ShowNormals.j3md");
		griffmaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		griffmaterial.setColor("Color", new ColorRGBA(0f, 1f, 0f, 1f));
		g.setMaterial(griffmaterial);

		buildGriff1(new Vector3f(0, 0.2f, 0), assetManager);

		graficObject.setLocalTranslation(position);

		return graficObject;
	}

	@Override
	public void update() {
		if (aktor != null) {

			float distance = griff1.getWorldTranslation().distance(aktor.getLocalTranslation());

			graficObject.rotate(rotation, 0, 0);

			float distanceRechts = griff1.getWorldTranslation().distance(aktor.getLocalTranslation());

			graficObject.rotate(-2f * rotation, 0, 0);

			float distanceLinks = griff1.getWorldTranslation().distance(aktor.getLocalTranslation());

			if (distance < distanceRechts) {
				if (distance < distanceLinks) {
					// nichts tun
				} else {
					myRotate(griff1, distance, -rotation);
				}
			} else {
				if (distanceRechts < distanceLinks) {
					myRotate(griff1, distance, rotation);
				} else {
					myRotate(griff1, distance, -rotation);
				}
			}
		}

	}

	private void myRotate(Geometry g, float distance, float rotation) {
		float oldDistance = distance;
		float newDistance = 0;
		while (true) {
			graficObject.rotate(rotation, 0, 0);
			newDistance = g.getWorldTranslation().distance(aktor.getLocalTranslation());
			if (newDistance > oldDistance) {
				break;
			}
			oldDistance = newDistance;
		}

	}

}

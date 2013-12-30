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

public class Rad2 extends Rad {

	private Aktor aktor2;

	private Material griffmaterial2;

	private boolean greifbar2 = true;

	public Node init(AssetManager assetManager, Vector3f position) {
		/** Load a teapot model (OBJ file from test-data) */
		graficObject = (Node) assetManager.loadModel("obj/rad2/rad2.obj");

		List<Spatial> childs = graficObject.getChildren();
		for (Spatial spatial : childs) {
			System.out.println(spatial);
		}

		// Geometry g = (Geometry)graficObject.getChild("rad2-geom-0");
		// Material mat_default = new Material(assetManager,
		// "Common/MatDefs/Misc/Unshaded.j3md");
		// mat_default.setColor("Color", new ColorRGBA(0.5f,0.5f,0.5f, 1f));
		// g.setMaterial(mat_default);

		Geometry g = (Geometry) graficObject.getChild("rad2-geom-0");
		// mat_default = new Material( assetManager,
		// "Common/MatDefs/Misc/ShowNormals.j3md");
		griffmaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		griffmaterial.setColor("Color", new ColorRGBA(1f, 0f, 0f, 1f));
		g.setMaterial(griffmaterial);

		g = (Geometry) graficObject.getChild("rad2-geom-1");
		// mat_default = new Material( assetManager,
		// "Common/MatDefs/Misc/ShowNormals.j3md");
		griffmaterial2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		griffmaterial2.setColor("Color", new ColorRGBA(0f, 1f, 0f, 1f));
		g.setMaterial(griffmaterial2);

		graficObject.attachChild(buildGriff("griff1", new Vector3f(0.2f, 0, 0), assetManager));
		// graficObject.attachChild(buildGriff("griff2", new Vector3f(-0.2f, 0,
		// 0), assetManager));

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

	private void myRotate(Geometry g, float distance, float rotation) {
		float oldDistance = distance;
		float newDistance = 0;
		while (true) {
			graficObject.rotate(0, 0, rotation);
			newDistance = g.getWorldTranslation().distance(aktor.getLocalTranslation());
			if (newDistance > oldDistance) {
				break;
			}
			oldDistance = newDistance;
		}

	}

	public void rotate(long deltaTime) {

		float f = (float) (-0.5f * deltaTime / 1000.0);

		graficObject.rotate(0, 0, f);
	}

	@Override
	public boolean isGreifbar() {
		return greifbar;
	}


	@Override
	public Vector3f getGreifbarePostion() {
		Geometry g = (Geometry) graficObject.getChild("griff1");
		return g.getWorldTranslation();
	}

	public void setAktor(Aktor aktor) {
		this.aktor = aktor;
	}

}

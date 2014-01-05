package schalttafel.artefakte;

import java.util.List;

import aktor.Aktor;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Rad2 extends Rad {

	protected Aktor aktor2;

	private boolean greifbar2 = true;

	public Node init(boolean physic, AssetManager assetManager, Vector3f position) {
		/** Load a teapot model (OBJ file from test-data) */
		graficObject = (Node) assetManager.loadModel("obj/rad2/rad2.obj");

		List<Spatial> childs = graficObject.getChildren();
		for (Spatial spatial : childs) {
			System.out.println(spatial);
		}

		Geometry g = (Geometry) graficObject.getChild("rad2-geom-0");
		MyMaterial m = new MyMaterial(g.getMaterial());
		m.setColor(new ColorRGBA(0f, 1f, 0f, 1f));
		buildGriff1(new Vector3f(0.2f, 0, 0), m, assetManager);

		g = (Geometry) graficObject.getChild("rad2-geom-1");
		m = new MyMaterial(g.getMaterial());
		m.setColor(new ColorRGBA(0f, 1f, 1f, 1f));
		buildGriff2(new Vector3f(-0.2f, 0, 0), m, assetManager);

		return graficObject;
	}

	public void update() {

		if (griff1.isGegriffen() && griff2.isGegriffen()) {

			float distance = griff1.distanceToActor();

			graficObject.rotate(0, 0, rotationDX);

			float distanceRechts = griff1.distanceToActor();

			graficObject.rotate(0, 0, -2f * rotationDX);

			float distanceLinks = griff1.distanceToActor();

			graficObject.rotate(0, 0, rotationDX);

			if (distance < distanceRechts) {
				if (distance < distanceLinks) {
					// nichts tun
				} else {
					myRotate(griff1, distance, -rotationDX);
				}
			} else {
				if (distanceRechts < distanceLinks) {
					myRotate(griff1, distance, rotationDX);
				} else {
					myRotate(griff1, distance, -rotationDX);
				}
			}
		}

	}

	private void myRotate(Griff griff, float distance, float rotation) {
		float oldDistance = distance;
		float newDistance = 0;
		while (true) {
			graficObject.rotate(0, 0, rotation);
			newDistance = griff.distanceToActor();
			if (newDistance > oldDistance) {
				break;
			}
			oldDistance = newDistance;
		}

	}

}

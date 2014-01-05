package schalttafel.artefakte;

import java.util.List;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Hebel1 extends Hebel {

	@Override
	public Node init(boolean physik, AssetManager assetManager, Vector3f position) {
		/** Load a teapot model (OBJ file from test-data) */
		graficObject = (Node) assetManager.loadModel("obj/hebel1/hebel1.obj");

		List<Spatial> childs = graficObject.getChildren();
		for (Spatial spatial : childs) {
			System.out.println(spatial);
		}

		// Geometry g = (Geometry) graficObject.getChild("hebel1-geom-1");
		// Material mat_default = new Material(assetManager,
		// "Common/MatDefs/Misc/Unshaded.j3md");
		// mat_default.setColor("Color", new ColorRGBA(0.5f, 0.5f, 0.5f, 1f));
		// g.setMaterial(mat_default);

		Geometry g = (Geometry) graficObject.getChild("hebel1-geom-0");
		MyMaterial m = new MyMaterial(g.getMaterial());
		m.setColor(Greifbar);

		buildGriff1(new Vector3f(0, 0.2f, 0), m, assetManager);

		graficObject.setLocalTranslation(position);

		return graficObject;
	}

	@Override
	public void update() {
		if (griff1.isGegriffen()) {

			float distance = griff1.distanceToActor();

			graficObject.rotate(rotation, 0, 0);
			float distanceRechts = griff1.distanceToActor();

			graficObject.rotate(-2f * rotation, 0, 0);
			float distanceLinks = griff1.distanceToActor();

			graficObject.rotate(rotation, 0, 0);

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

	private void myRotate(Griff griff, float distance, float rotation) {
		float oldDistance = distance;
		float newDistance = 0;
		while (true) {
			graficObject.rotate(rotation, 0, 0);
			newDistance = griff.distanceToActor();
			if (newDistance > oldDistance) {
				break;
			}
			oldDistance = newDistance;
		}

	}

}

package schalttafel.artefakte;

import java.util.List;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Rad2 extends Rad {

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

	@Override
	public void update(long deltaTime) {

		if (griff1.isGegriffen() && griff2.isGegriffen()) {

			float distanceGriff1 = griff1.distanceToActor();
			float distanceGriff2 = griff2.distanceToActor();

			graficObject.rotate(0, 0, rotationDX);

			float distanceRechtsGriff1 = griff1.distanceToActor();
			float distanceRechtsGriff2 = griff2.distanceToActor();

			graficObject.rotate(0, 0, -2f * rotationDX);

			float distanceLinksGriff1 = griff1.distanceToActor();
			float distanceLinksGriff2 = griff2.distanceToActor();

			graficObject.rotate(0, 0, rotationDX);

			float distanceMitte = (distanceGriff1 + distanceGriff2) / 2.0f;

			if (distanceGriff1 > distanceGriff2) {
				rotateWahl(distanceGriff1, distanceRechtsGriff1, distanceLinksGriff1, griff1, griff2, distanceMitte);
			} else {
				rotateWahl(distanceGriff2, distanceRechtsGriff2, distanceLinksGriff2, griff2, griff1, distanceMitte);
			}

		}

	}

	private void rotateWahl(float distance, float distanceRechts, float distanceLinks, AktorGriff rotateGriff,
			AktorGriff andererGriff, float distanceMitte) {
		if (distance < distanceRechts) {
			if (distance < distanceLinks) {
				// nichts tun
			} else {
				myRotate(rotateGriff, distance, -rotationDX, andererGriff, distanceMitte);
			}
		} else {
			if (distanceRechts < distanceLinks) {
				myRotate(rotateGriff, distance, rotationDX, andererGriff, distanceMitte);
			} else {
				myRotate(rotateGriff, distance, -rotationDX, andererGriff, distanceMitte);
			}
		}
	}

	private void myRotate(AktorGriff griff, float distance, float rotation, AktorGriff andererGriff, float distanceMitte) {
		float oldDistance = distance;
		float newDistanceGriffRotate = 0;
		float newDistanceAndererGriff = 0;
		while (true) {
			graficObject.rotate(0, 0, rotation);
			newDistanceGriffRotate = griff.distanceToActor();
			newDistanceAndererGriff = andererGriff.distanceToActor();
			if (newDistanceGriffRotate > oldDistance || newDistanceAndererGriff > distanceMitte) {
				break;
			}
			oldDistance = newDistanceGriffRotate;
		}

	}

}

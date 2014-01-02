package schalttafel.artefakte;

import java.util.List;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Schalter2 extends Artefakt {

	protected float rotationDX = 0.01f;
	protected float rotation = 0;

	float grenzeOben = 0.6f;

	float grenzeUnten = 2.6f;

	protected void init() {
		// Heben oben max
		minRot = -0.45f;

		// Heben unten max
		maxRot = 0.45f;
	}

	@Override
	public Node init(AssetManager assetManager, Vector3f position) {
		init();
		/** Load a teapot model (OBJ file from test-data) */
		graficObject = (Node) assetManager.loadModel("obj/schalter2/schalter2.obj");

		List<Spatial> childs = graficObject.getChildren();
		for (Spatial spatial : childs) {
			System.out.println(spatial);
		}

		// Geometry g = (Geometry) graficObject.getChild("schalter2-geom-1");
		// Material mat_default = new Material(assetManager,
		// "Common/MatDefs/Misc/Unshaded.j3md");
		// mat_default.setColor("Color", new ColorRGBA(0.5f, 0.5f, 0.5f, 1f));
		// g.setMaterial(mat_default);

		Geometry g = (Geometry) graficObject.getChild("schalter2-geom-0");
		MyMaterial m = new MyMaterial(g.getMaterial());
		m.setColor(Greifbar);

		buildGriff1(new Vector3f(-0.1f, 0, 0.05f), m, assetManager);

		g = (Geometry) graficObject.getChild("schalter2-geom-2");
		m = new MyMaterial(g.getMaterial());
		m.setColor(Inaktiv);

		buildGriff2(new Vector3f(0.1f, 0, 0.05f), m, assetManager);

		graficObject.setLocalTranslation(position);

		graficObject.rotate(0, maxRot, 0);
		rotation = maxRot;

		return graficObject;
	}

	@Override
	public void update() {
		if (griff1.isGegriffen() || griff2.isGegriffen()) {
			Griff aktiverGriff = isOn() ? griff2 : griff1;

			System.out.println("rotation: " + rotation);

			float distance = aktiverGriff.distanceToActor();

			graficObject.rotate(0, rotationDX, 0);
			float distanceRechts = aktiverGriff.distanceToActor();

			graficObject.rotate(0, -2f * rotationDX, 0);
			float distanceLinks = aktiverGriff.distanceToActor();

			graficObject.rotate(0, rotationDX, 0);

			if (distance < distanceRechts) {
				if (distance < distanceLinks) {
					// nichts tun
				} else {
					myRotate(aktiverGriff, distance, -rotationDX);
				}
			} else {
				if (distanceRechts < distanceLinks) {
					myRotate(aktiverGriff, distance, rotationDX);
				} else {
					myRotate(aktiverGriff, distance, -rotationDX);
				}
			}
		}

	}

	private void myRotate(Griff griff, float distance, float rotationDx) {
		float oldDistance = distance;
		float newDistance = 0;
		while (true) {

			if (!isBeweglichMaxRot(rotationDx, rotation) && griff.equals(griff2)) {
				griff2.loslassen();
				griff2.setAktive(false);
				griff1.setAktive(true);

				break;
			}
			if (!isBeweglichMinRot(rotationDx, rotation) && griff.equals(griff1)) {
				griff1.loslassen();
				griff1.setAktive(false);
				griff2.setAktive(true);

				break;
			}
			if (isBeweglichRotation(rotationDx, rotation)) {
				rotation += rotationDx;
				graficObject.rotate(0, rotationDx, 0);
			}
			newDistance = griff.distanceToActor();
			if (newDistance >= oldDistance) {
				break;
			}
			oldDistance = newDistance;
		}

	}

	public boolean isOn() {
		return griff2.isAktive();
	}

}

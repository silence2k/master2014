package schalttafel.artefakte;

import java.util.List;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Rad1 extends Rad {

	private AudioNode audioRaddreh;
	private AudioNode audioRadende;

	public Node init(boolean physic, AssetManager assetManager, Vector3f position) {
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

		// Geometry g = (Geometry) graficObject.getChild("rad1-geom-0");
		// Material mat_default = new Material(assetManager,
		// "Common/MatDefs/Misc/Unshaded.j3md");
		// mat_default.setColor("Color", new ColorRGBA(0.5f, 0.5f, 0.5f, 1f));
		// g.setMaterial(mat_default);

		Geometry g = (Geometry) graficObject.getChild("rad1-geom-1");
		MyMaterial m = new MyMaterial(g.getMaterial());
		m.setColor(Greifbar);
		buildGriff1(new Vector3f(0, 0.2f, 0), m, assetManager);

		graficObject.setLocalTranslation(position);

		return graficObject;
	}

	public void update(long deltaTime) {

		if (griff1.isGegriffen()) {

			float distance = griff1.distanceToActor();

			graficObject.rotate(0, 0, rotationDX);

			float distanceRechts = griff1.distanceToActor();

			graficObject.rotate(0, 0, -2f * rotationDX);

			float distanceLinks = griff1.distanceToActor();

			// wieder zurueckstellen
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
		
		updateAnzeige(zielwert(),deltaTime);
	}
	
	

	private void myRotate(Griff griff, float distance, float rotationDX) {
		float oldDistance = distance;
		float newDistance = 0;

		while (true) {
			if (!isBeweglichRotation(rotationDX, rotation)) {
				audioRadende.play();
				break;
			}

			this.rotation += rotationDX;
			graficObject.rotate(0, 0, rotationDX);
			newDistance = griff.distanceToActor();
			if (newDistance > oldDistance) {
				// audioRaddreh.pause();
				break;
			}
			oldDistance = newDistance;
			audioRaddreh.play();
		}
	}

	public void rotate(long deltaTime) {

		float f = (float) (-0.5f * deltaTime / 1000.0);

		graficObject.rotate(0, 0, f);
	}

}

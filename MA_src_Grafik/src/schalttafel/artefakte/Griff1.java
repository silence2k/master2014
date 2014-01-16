package schalttafel.artefakte;

import java.util.List;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Griff1 extends Artefakt{
	
	protected float translationDX = 0.01f;

	protected float translation = 0;

	protected AudioNode audioSchieberEnde;

	float grenzeOben = 0.6f;

	float grenzeUnten = 2.6f;

	protected void init() {
		// Heben oben max
		minTrans = 0f;

		// Heben unten max
		maxTrans = 0.5f;
	}

	@Override
	public Node init(boolean physic, AssetManager assetManager, Vector3f position) {
		init();

		audioSchieberEnde = new AudioNode(assetManager, "sound/radende.wav", false);
		audioSchieberEnde.setLooping(false);

		/** Load a teapot model (OBJ file from test-data) */
		graficObject = (Node) assetManager.loadModel("obj/griff/griff1.obj");

		List<Spatial> childs = graficObject.getChildren();
		for (Spatial spatial : childs) {
			System.out.println(spatial);
		}

		Geometry g = (Geometry) graficObject.getChild("griff1-geom-0");

		MyMaterial m = new MyMaterial(g.getMaterial());
		m.setColor(Greifbar);

		buildGriff1(new Vector3f(0, 0, 0.2f), m, assetManager);

		graficObject.setLocalTranslation(position);

		return graficObject;
	}

	protected float zielwert() {
		float tmp = maxTrans - minTrans;
		return translation / tmp * 100f;
	}

	@Override
	public void update(long deltaTime) {
		if (griff1.isGegriffen()) {

			float distance = griff1.distanceToActor();

			graficObject.move(0,0, translationDX);

			float distanceRechts = griff1.distanceToActor();

			graficObject.move(0, 0,-2f * translationDX);

			float distanceLinks = griff1.distanceToActor();

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
		updateAnzeige(zielwert(), deltaTime);
	}

	private void myTranslate(AktorGriff griff, float distance, float translationDX) {
		float oldDistance = distance;
		float newDistance = 0;
		while (true) {
			if (!isBeweglichTranslation(translationDX, translation)) {
				audioSchieberEnde.play();
				break;
			}

			this.translation += translationDX;

			graficObject.move(0, 0,translationDX);
			newDistance = griff.distanceToActor();
			if (newDistance > oldDistance) {
				break;
			}
			oldDistance = newDistance;
		}

	}

}

package schalttafel.artefakte;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class Schieber3 extends Schieber {

	@Override
	public Node init(boolean physic, AssetManager assetManager, Vector3f position) {
		super.init(physic, assetManager, position);

		// Heben oben max
		minTrans = -0.75f;

		// Heben unten max
		maxTrans = 0;

		graficObject.rotate((float) (Math.PI / -2.0), 0, 0);

		return graficObject;
	}

	@Override
	public void update(long deltaTime) {
		if (griff1.isGegriffen()) {

			float distance = griff1.distanceToActor();

			graficObject.move(0, 0, translationDX);
			float distanceRechts = griff1.distanceToActor();

			graficObject.move(0, 0, -2f * translationDX);
			float distanceLinks = griff1.distanceToActor();

			graficObject.move(0, 0, translationDX);

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

			graficObject.move(0, 0, translationDX);
			newDistance = griff.distanceToActor();
			if (newDistance > oldDistance) {
				break;
			}
			oldDistance = newDistance;
		}

	}

}

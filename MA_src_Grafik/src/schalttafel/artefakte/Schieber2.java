package schalttafel.artefakte;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class Schieber2 extends Schieber {

	@Override
	public Node init(boolean physic, AssetManager assetManager, Vector3f position) {
		super.init(physic, assetManager, position);

		graficObject.rotate(0, 0, (float) (Math.PI / 2.0));

		return graficObject;
	}

	@Override
	public void update(long deltaTime) {
		if (griff1.isGegriffen()) {

			float distance = griff1.distanceToActor();

			graficObject.move(translationDX, 0, 0);
			float distanceRechts = griff1.distanceToActor();

			graficObject.move(-2f * translationDX, 0, 0);
			float distanceLinks = griff1.distanceToActor();

			graficObject.move(translationDX, 0, 0);

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

			graficObject.move(translationDX, 0, 0);
			newDistance = griff.distanceToActor();
			if (newDistance > oldDistance) {
				break;
			}
			oldDistance = newDistance;
		}

	}

}

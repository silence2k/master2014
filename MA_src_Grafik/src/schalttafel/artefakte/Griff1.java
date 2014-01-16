package schalttafel.artefakte;

public class Griff1 extends Schieber {

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

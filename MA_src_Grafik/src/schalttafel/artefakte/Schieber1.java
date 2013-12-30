package schalttafel.artefakte;

import com.jme3.scene.Geometry;

public class Schieber1 extends Schieber {
	
	

	@Override
	public void update() {
		if (aktor != null) {

			float distance = griff1.getWorldTranslation().distance(aktor.getLocalTranslation());

			graficObject.move(0, translationDX, 0);

			float distanceRechts = griff1.getWorldTranslation().distance(aktor.getLocalTranslation());

			graficObject.move(0,-2f * translationDX, 0);

			float distanceLinks = griff1.getWorldTranslation().distance(aktor.getLocalTranslation());
			
			graficObject.move(0, translationDX, 0);

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

	}

	private void myTranslate(Geometry g, float distance, float translationDX) {
		float oldDistance = distance;
		float newDistance = 0;
		while (true) {
			if(!isBeweglichTranslation(translationDX, translation)){
				audioSchieberEnde.play();
				break;
			}
			
			this.translation+= translationDX;
			
			graficObject.move(0, translationDX, 0);
			newDistance = g.getWorldTranslation().distance(aktor.getLocalTranslation());
			if (newDistance > oldDistance) {
				break;
			}
			oldDistance = newDistance;
		}

	}

}

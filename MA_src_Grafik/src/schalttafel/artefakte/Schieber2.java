package schalttafel.artefakte;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

public class Schieber2 extends Schieber {
	
	

	@Override
	public Node init(AssetManager assetManager, Vector3f position) {
		super.init(assetManager, position);
		
		graficObject.rotate(0, 0, (float)(Math.PI/2.0));
		
		return graficObject;
	}
	
	
	

	@Override
	public void update() {
		if (aktor != null) {

			float distance = griff1.getWorldTranslation().distance(aktor.getLocalTranslation());

			graficObject.move(translationDX,0, 0);

			float distanceRechts = griff1.getWorldTranslation().distance(aktor.getLocalTranslation());

			graficObject.move(-2f * translationDX,0, 0);

			float distanceLinks = griff1.getWorldTranslation().distance(aktor.getLocalTranslation());
			
			graficObject.move(translationDX, 0 , 0);

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
			
			graficObject.move(translationDX,0, 0);
			newDistance = g.getWorldTranslation().distance(aktor.getLocalTranslation());
			if (newDistance > oldDistance) {
				break;
			}
			oldDistance = newDistance;
		}

	}

}

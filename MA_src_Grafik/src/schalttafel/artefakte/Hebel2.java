package schalttafel.artefakte;

import java.util.List;

import aktor.Aktor;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Hebel2 extends Hebel {

	protected Aktor aktor2;

	@Override
	public Node init(boolean physic, AssetManager assetManager, Vector3f position) {
		init();
		/** Load a teapot model (OBJ file from test-data) */
		graficObject = (Node) assetManager.loadModel("obj/hebel2/hebel2.obj");

		List<Spatial> childs = graficObject.getChildren();
		for (Spatial spatial : childs) {
			System.out.println(spatial);
		}

		Geometry g = (Geometry) graficObject.getChild("hebel2-geom-0");
		MyMaterial m = new MyMaterial(g.getMaterial());
		m.setColor(Greifbar);

		buildGriff1(new Vector3f(-0.2f, 0.22f, 0), m, assetManager);
		buildGriff2(new Vector3f( 0.2f, 0.22f, 0), m, assetManager);
		
		rotation = 0.2f;

		graficObject.setLocalTranslation(position);

		return graficObject;
	}

	@Override
	public void update(long deltaTime) {
		if (griff1.isGegriffen() && griff2.isGegriffen()) {

			float distanceGriff1 = griff1.distanceToActor();
			float distanceGriff2 = griff2.distanceToActor();

			graficObject.rotate(rotationDX,0,0);

			float distanceRechtsGriff1 = griff1.distanceToActor();
			float distanceRechtsGriff2 = griff2.distanceToActor();

			graficObject.rotate(-2f * rotationDX,0,0);

			float distanceLinksGriff1 = griff1.distanceToActor();
			float distanceLinksGriff2 = griff2.distanceToActor();

			graficObject.rotate(rotationDX,0,0);
			
			float distanceMitte = (distanceGriff1 + distanceGriff2) / 2.0f;

			if(distanceGriff1 > distanceGriff2){
				rotateWahl(distanceGriff1, distanceRechtsGriff1, distanceLinksGriff1, griff1,griff2, distanceMitte);
			}else{
				rotateWahl(distanceGriff2, distanceRechtsGriff2, distanceLinksGriff2, griff2, griff1,distanceMitte);
			}
		}

	}
	
	private void rotateWahl(float distance, float distanceRechts, float distanceLinks, Griff rotateGriff, Griff andererGriff, float distanceMitte) {
		if (distance <= distanceRechts) {
			if (distance <= distanceLinks) {
				// nichts tun
			} else {
				myRotate(rotateGriff, distance, -rotationDX,andererGriff,distanceMitte);
			}
		} else {
			if (distanceRechts < distanceLinks) {
				myRotate(rotateGriff, distance, rotationDX,andererGriff,distanceMitte);
			} else {
				myRotate(rotateGriff, distance, -rotationDX,andererGriff,distanceMitte);
			}
		}
	}

//	private void myRotate(Griff griff, float distance, float rotation,Griff andererGriff,float distanceMitte) {
//		float oldDistance = distance;
//		float newDistance = 0;
//		while (true) {
//			graficObject.rotate(rotation, 0, 0);
//			newDistance = griff.distanceToActor();
//			if (newDistance > oldDistance) {
//				break;
//			}
//			oldDistance = newDistance;
//		}
//
//	}
	
	private void myRotate(Griff griff, float distance, float rotationDX, Griff andererGriff, float distanceMitte) {
		float oldDistance = distance;
		float newDistanceGriffRotate = 0;
		float newDistanceAndererGriff = 0;
		while (true) {
			if (!isBeweglichRotation(rotationDX, rotation)) {
				//audioRadende.play();
				break;
			}
			graficObject.rotate(rotationDX, 0, 0);
			this.rotation += rotationDX;
			newDistanceGriffRotate = griff.distanceToActor();
			newDistanceAndererGriff = andererGriff.distanceToActor();
			if (newDistanceGriffRotate > oldDistance || newDistanceAndererGriff > distanceMitte) {
				break;
			}
			oldDistance = newDistanceGriffRotate;
		}

	}

}

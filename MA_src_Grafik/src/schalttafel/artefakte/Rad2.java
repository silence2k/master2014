package schalttafel.artefakte;

import java.util.List;

import aktor.Aktor;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Rad2 extends Rad {
	
	protected Aktor aktor2;


	private boolean greifbar2 = true;

	public Node init(AssetManager assetManager, Vector3f position) {
		/** Load a teapot model (OBJ file from test-data) */
		graficObject = (Node) assetManager.loadModel("obj/rad2/rad2.obj");

		List<Spatial> childs = graficObject.getChildren();
		for (Spatial spatial : childs) {
			System.out.println(spatial);
		}

		Geometry g = (Geometry) graficObject.getChild("rad2-geom-0");
		griffmaterial = new MyMaterial(g.getMaterial());
		griffmaterial.setColor(new ColorRGBA(0f, 1f, 0f, 1f));
		buildGriff1(new Vector3f(0.2f, 0, 0), assetManager);
		
		
		g = (Geometry) graficObject.getChild("rad2-geom-1");
		griffmaterial2 = new MyMaterial(g.getMaterial());
		griffmaterial2.setColor(new ColorRGBA(0f, 1f, 1f, 1f));
		buildGriff2(new Vector3f(-0.2f, 0, 0), assetManager);

		return graficObject;
	}

	public void update() {

		if (aktor != null && aktor2 != null) {

			Geometry g = (Geometry) graficObject.getChild("griff1");

			float distance = g.getWorldTranslation().distance(aktor.getLocalTranslation());

			graficObject.rotate(0, 0, rotationDX);

			float distanceRechts = g.getWorldTranslation().distance(aktor.getLocalTranslation());

			graficObject.rotate(0, 0, -2f * rotationDX);

			float distanceLinks = g.getWorldTranslation().distance(aktor.getLocalTranslation());
			
			graficObject.rotate(0, 0, rotationDX);

			if (distance < distanceRechts) {
				if (distance < distanceLinks) {
					// nichts tun
				} else {
					myRotate(g, distance, -rotationDX);
				}
			} else {
				if (distanceRechts < distanceLinks) {
					myRotate(g, distance, rotationDX);
				} else {
					myRotate(g, distance, -rotationDX);
				}
			}
		}

	}

	private void myRotate(Geometry g, float distance, float rotation) {
		float oldDistance = distance;
		float newDistance = 0;
		while (true) {
			graficObject.rotate(0, 0, rotation);
			newDistance = g.getWorldTranslation().distance(aktor.getLocalTranslation());
			if (newDistance > oldDistance) {
				break;
			}
			oldDistance = newDistance;
		}

	}


	@Override
	public boolean isGreifbar() {
		return greifbar;
	}
	
	@Override
	public float distanceFreierGriff(Aktor aktor) {
		float distance = Float.MAX_VALUE;
		float distance2 = Float.MAX_VALUE;
		if(greifbar){
			distance = griff1.getWorldTranslation().distance(aktor.getLocalTranslation());
		}
		if(greifbar2){
			distance2 = griff2.getWorldTranslation().distance(aktor.getLocalTranslation());
		}
		
		return Math.min(distance, distance2);
	}

	public void setAktor(Aktor aktor) {
		this.aktor = aktor;
	}

}

package schalttafel.artefakte;

import java.util.List;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Schalter2 extends Artefakt {
	
	protected float rotationDX = 0.01f;
	protected float rotation = 0;
	
	float grenzeOben = 0.6f;
	
	float grenzeUnten = 2.6f;

	private boolean on;
	
	
	
	protected void init(){
		// Heben oben max
		minRot= -0.45f;
		
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

		Geometry g = (Geometry) graficObject.getChild("schalter2-geom-1");
		Material mat_default = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat_default.setColor("Color", new ColorRGBA(0.5f, 0.5f, 0.5f, 1f));
		g.setMaterial(mat_default);

		g = (Geometry) graficObject.getChild("schalter2-geom-0");
		griffmaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		griffmaterial.setColor("Color", new ColorRGBA(0f, 1f, 0f, 1f));
		g.setMaterial(griffmaterial);

		buildGriff1(new Vector3f(-0.1f, 0, 0.05f), assetManager);
		
		
		
		g = (Geometry) graficObject.getChild("schalter2-geom-2");
		griffmaterial2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		griffmaterial2.setColor("Color", new ColorRGBA(1f, 1f, 1f, 1f));
		g.setMaterial(griffmaterial2);

		buildGriff2(new Vector3f(0.1f, 0, 0.05f), assetManager);
		
		graficObject.setLocalTranslation(position);
		
		graficObject.rotate(0, maxRot, 0);
		rotation = maxRot;

		return graficObject;
	}

	@Override
	public void update() {
		System.out.println("rotation: "+rotation);
		if (aktor != null) {
			Geometry aktiverGriff= on?griff2:griff1;
			
			System.out.println("rotation: "+rotation);

			float distance = aktiverGriff.getWorldTranslation().distance(aktor.getLocalTranslation());

			graficObject.rotate(0, rotationDX,0);
			float distanceRechts = aktiverGriff.getWorldTranslation().distance(aktor.getLocalTranslation());

			graficObject.rotate(0,-2f * rotationDX,0);
			float distanceLinks = aktiverGriff.getWorldTranslation().distance(aktor.getLocalTranslation());
			
			graficObject.rotate(0, rotationDX,0);

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

	private void myRotate(Geometry g, float distance, float rotationDx) {
		float oldDistance = distance;
		float newDistance = 0;
		while (true) {
			
			if(!isBeweglichMaxRot(rotationDx, rotation)&&g.equals(griff2)){
				on = false;
				griffmaterial2.setColor("Color", new ColorRGBA(1f, 1f, 1f, 1f));
				griffmaterial.setColor("Color", new ColorRGBA(0f, 1f, 0f, 1f));
				aktor.setNichtgreifen();
				aktor = null;
				break;
			}
			if(!isBeweglichMinRot(rotationDx, rotation)&&g.equals(griff1)){
				on = true;
				griffmaterial.setColor("Color", new ColorRGBA(1f, 1f, 1f, 1f));
				griffmaterial2.setColor("Color", new ColorRGBA(0f, 1f, 0f, 1f));
				aktor.setNichtgreifen();
				aktor = null;
				break;
			}
			if(isBeweglichRotation(rotationDx, rotation)){
				rotation += rotationDx;
				graficObject.rotate(0, rotationDx,0);
			}
			newDistance = g.getWorldTranslation().distance(aktor.getLocalTranslation());
			if (newDistance >= oldDistance) {
				break;
			}
			oldDistance = newDistance;
		}

	}

}

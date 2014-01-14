package schalttafel.anzeige;

import schalttafel.artefakte.MyMaterial;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

public class AnzeigerLampe extends Anzeiger {

	private boolean an;

	private MyMaterial myMaterial;

	@Override
	public Node init(boolean physic, AssetManager assetManager, Vector3f position) {

		/** Load a teapot model (OBJ file from test-data) */
		graficObject = (Node) assetManager.loadModel("obj/anzeigerlicht/anzeigerlicht.obj");

		anzeige = (Geometry) graficObject.getChild("anzeigerlicht-geom-1");
		myMaterial = new MyMaterial(anzeige.getMaterial());
		myMaterial.setColor(Aus);
		an = false;

		graficObject.setLocalTranslation(position);

		return graficObject;
	}

	@Override
	public void update(float deltaTime) {

	}

	@Override
	public void setZielWert(float zielwert) {
		if (!an) {
			if (zielwert < 20) {
				setAn(true);
			}
		} else {
			if (zielwert > 80) {
				setAn(false);
			}
		}
	}

	@Override
	public void setAn(boolean an) {
		this.an = an;
		if (an)
			myMaterial.setColor(An);
		else
			myMaterial.setColor(Aus);
	}

	// public void hoch(float delta){
	// Vector3f f = graficObject.getLocalScale();
	// graficObject.setLocalScale(f.x, f.y+delta, f.z);
	// }
	//
	// public void runter(float delta){
	// Vector3f f = graficObject.getLocalScale();
	// graficObject.setLocalScale(f.x, f.y-delta, f.z);
	// }

}

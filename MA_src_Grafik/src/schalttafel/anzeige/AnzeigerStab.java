package schalttafel.anzeige;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

public class AnzeigerStab extends Anzeiger {

	

	protected float wert = 0;

	protected float wertNull;

	protected float wertHundert;

	protected float difWert;

	@Override
	public Node init(boolean physic, AssetManager assetManager, Vector3f position) {

		graficObject = new Node();
		/** Load a teapot model (OBJ file from test-data) */
		anzeige = (Geometry) assetManager
				.loadModel("obj/anzeigerstab/anzeigerstab.obj");

		
		graficObject.attachChild(anzeige);
		
		graficObject.attachChild(assetManager.loadModel("obj/anzeigerstab/anzeigerstab_verkleidung.obj"));

		wertNull = anzeige.getLocalScale().y;

		wertHundert = wertNull + 10;

		difWert = wertHundert - wertNull;
		
		graficObject.setLocalTranslation(position);

		return graficObject;
	}

	@Override
	public void update(float deltaTime) {
		float delta = (difWert / 100f * zielWert) - wert;
		if (delta != 0) {
			if(delta < 0.1f && delta > 0){
				delta = 0.1f;
			}else if(delta > -0.1f && delta < 0){
				delta = -0.1f;
			}
			delta = delta * deltaTime / 1000f;
			wert += delta;
			Vector3f f = anzeige.getLocalScale();
			anzeige.setLocalScale(f.x, f.y + delta, f.z);
		}
	}
	@Override
	public void setZielWert(float zielwert) {
		if (zielwert < 0f || zielwert > 100f) {
			throw new RuntimeException(
					"Der Zzielwert liegt ausserhalb von 0 und 100, zielwert: "
							+ zielwert);
		}
		this.zielWert = zielwert;
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

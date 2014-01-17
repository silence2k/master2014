package schalttafel.verkleidung;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

public class Hebel2Verkleidung extends Verkleidung {

	@Override
	public Geometry init(boolean physic, AssetManager assetManager, Vector3f position) {
		/** Load a teapot model (OBJ file from test-data) */
		graficObject = (Geometry) assetManager.loadModel("obj/hebel2/hebel2_verkleidung.obj");

		graficObject.setLocalTranslation(position);

		return graficObject;
	}

}

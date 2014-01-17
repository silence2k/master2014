package schalttafel.verkleidung;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class Schieber1Verkleidung extends Verkleidung2 {

	@Override
	public Node init(boolean physic, AssetManager assetManager, Vector3f position) {
		/** Load a teapot model (OBJ file from test-data) */
		graficObject = (Node) assetManager.loadModel("obj/schieber1/schieber1_verkleidung.obj");

		graficObject.setLocalTranslation(position);

		return graficObject;
	}

}

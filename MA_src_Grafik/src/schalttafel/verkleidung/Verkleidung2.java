package schalttafel.verkleidung;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public abstract class Verkleidung2 {

	protected Node graficObject;

	public abstract Node init(boolean physic, AssetManager assetManager, Vector3f position);

}

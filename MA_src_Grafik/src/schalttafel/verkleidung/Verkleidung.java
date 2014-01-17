package schalttafel.verkleidung;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

public abstract class Verkleidung {

	protected Geometry graficObject;

	public abstract Geometry init(boolean physic, AssetManager assetManager, Vector3f position);

}

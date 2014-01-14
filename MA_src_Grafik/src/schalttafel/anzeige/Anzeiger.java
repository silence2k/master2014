package schalttafel.anzeige;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

public abstract class Anzeiger {

	protected Vector3f position;
	
	protected Geometry graficObject;
	
	public abstract void update();
	
	public abstract Geometry init(AssetManager assetManager, Vector3f position);

}

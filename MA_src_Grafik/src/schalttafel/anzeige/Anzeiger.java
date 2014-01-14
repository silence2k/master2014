package schalttafel.anzeige;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

public abstract class Anzeiger {
	
	protected float zielWert = 0;

	protected Vector3f position;
	
	protected Geometry anzeige;
	
	protected Node graficObject;
	
	public abstract void update(float deltaTime);
	
	public abstract Node init(boolean physic, AssetManager assetManager, Vector3f position);
	
	public abstract void setZielWert(float zielwert);

}

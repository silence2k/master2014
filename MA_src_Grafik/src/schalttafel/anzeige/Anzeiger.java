package schalttafel.anzeige;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

public abstract class Anzeiger implements IF_Anzeiger{

//	protected final ColorRGBA An = new ColorRGBA(0f, 1f, 0f, 1f);
//	protected final ColorRGBA Aus = new ColorRGBA(1f, 0f, 0f, 1f);
	
	protected final ColorRGBA An = new ColorRGBA(1f, 1f, 0f, 1f);
	protected final ColorRGBA Aus = new ColorRGBA(0.05f, 0.05f, 0.05f, 1f);
	
	protected float schwellWertMin = 20;
	
	protected float schwellWertMax = 80;

	protected float zielWert = 0;

	protected Vector3f position;

	protected Geometry anzeige;

	protected Node graficObject;

	public abstract Node init(boolean physic, AssetManager assetManager, Vector3f position);

	public abstract void setAn(boolean an);

}

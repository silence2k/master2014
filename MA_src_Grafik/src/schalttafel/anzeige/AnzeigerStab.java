package schalttafel.anzeige;

import java.util.List;

import schalttafel.artefakte.MyMaterial;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class AnzeigerStab extends Anzeiger {

	@Override
	public Geometry init(AssetManager assetManager, Vector3f position) {
		
		/** Load a teapot model (OBJ file from test-data) */
		graficObject = (Geometry) assetManager.loadModel("obj/anzeigerstab/anzeigerstab.obj");

//		List<Spatial> childs = graficObject.getChildren();
//		for (Spatial spatial : childs) {
//			System.out.println(spatial);
//		}

		// Geometry g = (Geometry) graficObject.getChild("hebel1-geom-1");
		// Material mat_default = new Material(assetManager,
		// "Common/MatDefs/Misc/Unshaded.j3md");
		// mat_default.setColor("Color", new ColorRGBA(0.5f, 0.5f, 0.5f, 1f));
		// g.setMaterial(mat_default);

//		Geometry g = (Geometry) graficObject.getChild("anzeigerstab-geom-0");
//		MyMaterial m = new MyMaterial(g.getMaterial());
//		m.setColor(ColorRGBA.Cyan);



		graficObject.setLocalTranslation(position);

		return graficObject;
	}

	@Override
	public void update() {
		

	}
	
	public void hoch(float delta){
		Vector3f f = graficObject.getLocalScale();
		graficObject.setLocalScale(f.x, f.y+delta, f.z);
	}
	
	public void runter(float delta){
		Vector3f f = graficObject.getLocalScale();
		graficObject.setLocalScale(f.x, f.y-delta, f.z);
	}

	


}

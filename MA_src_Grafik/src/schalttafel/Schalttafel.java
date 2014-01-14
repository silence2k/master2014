package schalttafel;

import java.util.List;

import schalttafel.artefakte.MyMaterial;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Schalttafel {
	
	private Node graficObject;
	
	private MyMaterial material;

	public Node init(boolean physik, AssetManager assetManager, Vector3f position) {

		
		/** Load a teapot model (OBJ file from test-data) */
		graficObject = (Node) assetManager.loadModel("obj/schalttafel/schalttafel1.obj");

		List<Spatial> childs = graficObject.getChildren();
		for (Spatial spatial : childs) {
			System.out.println(spatial);
		}

		// Geometry g = (Geometry) graficObject.getChild("hebel1-geom-1");
		// Material mat_default = new Material(assetManager,
		// "Common/MatDefs/Misc/Unshaded.j3md");
		// mat_default.setColor("Color", new ColorRGBA(0.5f, 0.5f, 0.5f, 1f));
		// g.setMaterial(mat_default);

		Geometry g = (Geometry) graficObject.getChild("schalttafel1-geom-0");
		material = new MyMaterial(g.getMaterial());

		graficObject.setLocalTranslation(position);
		

		return graficObject;
	}



}

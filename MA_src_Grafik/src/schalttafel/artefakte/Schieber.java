package schalttafel.artefakte;

import java.util.List;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public abstract class Schieber extends Artefakt {
	
	protected float translationDX = 0.01f;

	protected float translation = 0;
		
		protected AudioNode audioSchieberEnde;
		
		
		
		float grenzeOben = 0.6f;
		
		float grenzeUnten = 2.6f;
		
		
		
		protected void init(){
			// Heben oben max
			minTrans= 0f;
			
			// Heben unten max
			maxTrans = 0.5f;
		}
	
	
	@Override
	public Node init(AssetManager assetManager, Vector3f position) {
		init();
		
		
		audioSchieberEnde = new AudioNode(assetManager, "sound/radende.wav", false);
		audioSchieberEnde.setLooping(false);
		
		/** Load a teapot model (OBJ file from test-data) */
		graficObject = (Node) assetManager.loadModel("obj/schieber1/schieber1.obj");

		List<Spatial> childs = graficObject.getChildren();
		for (Spatial spatial : childs) {
			System.out.println(spatial);
		}

		Geometry g = (Geometry) graficObject.getChild("schieber1-geom-1");
		Material mat_default = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat_default.setColor("Color", new ColorRGBA(0.5f, 0.5f, 0.5f, 1f));
		g.setMaterial(mat_default);

		g = (Geometry) graficObject.getChild("schieber1-geom-0");
		// mat_default = new Material( assetManager,
		// "Common/MatDefs/Misc/ShowNormals.j3md");
		griffmaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		griffmaterial.setColor("Color", new ColorRGBA(0f, 1f, 0f, 1f));
		g.setMaterial(griffmaterial);

		buildGriff1(new Vector3f(0, 0, 0.2f), assetManager);
		
		graficObject.setLocalTranslation(position);

		return graficObject;
	}

}

package schalttafel.artefakte;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.TangentBinormalGenerator;

public abstract class Artefakt {
	
	
	
	protected Geometry buildGriff(String name, Vector3f position, AssetManager assetManager){
		Sphere rock = new Sphere(6, 6, 0.02f);
		Geometry geo = new Geometry(name, rock);
		rock.setTextureMode(Sphere.TextureMode.Projected); // better quality on
															// spheres
		TangentBinormalGenerator.generate(rock); // for lighting effect
		
		 Material mat_default = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
	     mat_default.setColor("Color", new ColorRGBA(1f,1f,0f, 1f));
		
//		Material mat_lit = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
//		mat_lit.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Terrain/Pond/Pond.jpg"));
//		mat_lit.setTexture("NormalMap", assetManager.loadTexture("Textures/Terrain/Pond/Pond_normal.png"));
//		mat_lit.setBoolean("UseMaterialColors", true);
//		mat_lit.setColor("Specular", ColorRGBA.Yellow);
//		mat_lit.setColor("Diffuse", ColorRGBA.Yellow);
//		mat_lit.setFloat("Shininess", 5f); // [0,128]
		geo.setMaterial(mat_default);
//		test.setLocalTranslation(0, 0.2f, 0); // Move it a bit
		
		geo.setLocalTranslation(position);
		
		return geo;
	}

}

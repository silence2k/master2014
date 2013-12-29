package schalttafel.artefakte;

import java.util.List;

import aktor.Aktor;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.TangentBinormalGenerator;

public class Rad1 extends Rad implements Greifbar{
	
	Node graficObject;
	private boolean greifbar = true;
	
	float rotation = 0.01f;
	
	Material griffmaterial;
	
	Aktor aktor;
	
	
	public Node init(AssetManager assetManager){
        /** Load a teapot model (OBJ file from test-data) */
		graficObject = (Node) assetManager.loadModel("obj/rad1/rad1.obj");
        
        List<Spatial> childs = graficObject.getChildren();
        for (Spatial spatial : childs) {
			System.out.println(spatial);
		}
        
        Geometry g = (Geometry)graficObject.getChild("rad1-geom-0");
        Material mat_default = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_default.setColor("Color", new ColorRGBA(0.5f,0.5f,0.5f, 1f));
        g.setMaterial(mat_default);
        
        
        g = (Geometry)graficObject.getChild("rad1-geom-1");
//        mat_default = new Material( assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        griffmaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        griffmaterial.setColor("Color", new ColorRGBA(0f,1f,0f, 1f));
        g.setMaterial(griffmaterial);
        
        
        /* TEST */
        
//		Sphere rock = new Sphere(8, 8, 0.05f);
//		Geometry test = new Geometry("griff1", rock);
//		rock.setTextureMode(Sphere.TextureMode.Projected); // better quality on
//															// spheres
//		TangentBinormalGenerator.generate(rock); // for lighting effect
//		Material mat_lit = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
//		mat_lit.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Terrain/Pond/Pond.jpg"));
//		mat_lit.setTexture("NormalMap", assetManager.loadTexture("Textures/Terrain/Pond/Pond_normal.png"));
//		mat_lit.setBoolean("UseMaterialColors", true);
//		mat_lit.setColor("Specular", ColorRGBA.Yellow);
//		mat_lit.setColor("Diffuse", ColorRGBA.Yellow);
//		mat_lit.setFloat("Shininess", 5f); // [0,128]
//		test.setMaterial(mat_lit);
//		test.setLocalTranslation(0, 0.2f, 0); // Move it a bit
//		test.rotate(0, 0, 0); // Rotate it a bit
		
//		0, 0.2f, 0
        
		graficObject.attachChild(buildGriff("griff1", new Vector3f(0, 0.2f, 0), assetManager));

        return graficObject;
	}
	
	
	
	
	public void update(){
		
		if(aktor != null){
		
		Geometry g = (Geometry)graficObject.getChild("griff1");
		
		float distance = g.getWorldTranslation().distance(aktor.getTranslation());
		
		graficObject.rotate(0, 0, rotation);
		
		float distanceRechts = g.getWorldTranslation().distance(aktor.getTranslation());
		
		graficObject.rotate(0, 0, -2f*rotation);
		
		float distanceLinks = g.getWorldTranslation().distance(aktor.getTranslation());
		
		if(distance < distanceRechts){
			if(distance < distanceLinks){
				// nichts tun
			}else{
				myRotate(g, distance, -rotation);
			}
		}else{
			if(distanceRechts < distanceLinks){
				myRotate(g, distance, rotation);
			}else{
				myRotate(g, distance, -rotation);
			}
		}
		}
		
	}
	
	
	private void myRotate(Geometry g, float distance, float rotation){
		float oldDistance = distance;
		float newDistance = 0;
		while(true){
			graficObject.rotate(0, 0, rotation);
			newDistance = g.getWorldTranslation().distance(aktor.getTranslation());
			if(newDistance > oldDistance){
				break;
			}
			oldDistance = newDistance;
		}
		
		
	}
	
	
	public void rotate(long deltaTime){
		
		float f = (float)(-0.5f * deltaTime / 1000.0);
		
		graficObject.rotate(0, 0, f);
	}



	@Override
	public boolean isGreifbar() {
		return greifbar;
	}



	@Override
	public void setGreifbar(boolean greifbar) {
		this.greifbar = greifbar;
		if(this.greifbar){
			 griffmaterial.setColor("Color", new ColorRGBA(0f,1f,0f, 1f));
		}else{
			 griffmaterial.setColor("Color", new ColorRGBA(1f,0f,0f, 1f));
		}
	}



	@Override
	public Vector3f getGreifbarePostion() {
		Geometry g = (Geometry)graficObject.getChild("griff1");
		return g.getWorldTranslation();	
	}


	public void setAktor(Aktor aktor) {
		this.aktor = aktor;
	}

	
	
	
	

}

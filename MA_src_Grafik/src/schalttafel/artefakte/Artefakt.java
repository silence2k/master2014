package schalttafel.artefakte;

import aktor.Aktor;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.TangentBinormalGenerator;

public abstract class Artefakt implements Greifbar {
	
	
	// grenze min rotation
	float minRot;
	
	// grenze max rotation
	float maxRot;
	
	// grenze min translation
	float minTrans;
	
	// grenze max translation
	float maxTrans;
	
	

	protected Node graficObject;

	protected Aktor aktor;

	protected Material griffmaterial;

	protected boolean greifbar = true;

	protected Geometry griff1 = null;

	public abstract Node init(AssetManager assetManager, Vector3f position);

	public abstract void update();
	
	protected abstract void init();

	public float distanceFreierGriff(Aktor aktor) {
		return griff1.getWorldTranslation().distance(aktor.getLocalTranslation());
	}

	protected void buildGriff1(Vector3f position, AssetManager assetManager) {
		griff1 = buildGriff("griff1", position, assetManager);
	}

	protected Geometry buildGriff(String name, Vector3f position, AssetManager assetManager) {
		Sphere rock = new Sphere(6, 6, 0.05f);
		Geometry geo = new Geometry(name, rock);
		rock.setTextureMode(Sphere.TextureMode.Projected); // better quality on
															// spheres
		TangentBinormalGenerator.generate(rock); // for lighting effect

		Material mat_default = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat_default.setColor("Color", new ColorRGBA(1f, 1f, 0f, 1f));

		// Material mat_lit = new Material(assetManager,
		// "Common/MatDefs/Light/Lighting.j3md");
		// mat_lit.setTexture("DiffuseMap",
		// assetManager.loadTexture("Textures/Terrain/Pond/Pond.jpg"));
		// mat_lit.setTexture("NormalMap",
		// assetManager.loadTexture("Textures/Terrain/Pond/Pond_normal.png"));
		// mat_lit.setBoolean("UseMaterialColors", true);
		// mat_lit.setColor("Specular", ColorRGBA.Yellow);
		// mat_lit.setColor("Diffuse", ColorRGBA.Yellow);
		// mat_lit.setFloat("Shininess", 5f); // [0,128]
		geo.setMaterial(mat_default);
		// test.setLocalTranslation(0, 0.2f, 0); // Move it a bit

		geo.setLocalTranslation(position);

		graficObject.attachChild(geo);

		return geo;
	}

	public void setAktor(Aktor aktor) {
		this.aktor = aktor;
	}

	public boolean isGreifbar() {
		return greifbar;
	}

	public Vector3f getGreifbarePostion() {
		return griff1.getWorldTranslation();
	}
	
	
	protected boolean isBeweglichRotation(float dx, float aktuelleRotation){
		float tmp = aktuelleRotation + dx;
		
		//System.out.println("tmp: "+tmp+" max: "+maxRot+" min: "+minRot);
		
		if(tmp <= maxRot && tmp >= minRot){
			return true;
		}
		return false;
	}
	
	protected boolean isBeweglichTranslation(float dx, float aktuelleTranslation){
		return isBeweglichMaxTrans(dx, aktuelleTranslation) && isBeweglichMinTrans(dx, aktuelleTranslation);
	}
	
	protected boolean isBeweglichMaxTrans(float dx, float aktuelleTranslation){
		return (aktuelleTranslation + dx) <= maxTrans;
	}
	
	protected boolean isBeweglichMinTrans(float dx, float aktuelleTranslation){
		return (aktuelleTranslation + dx) >= minTrans;
	}
	
	public void setGreifbar(boolean greifbar) {
		this.greifbar = greifbar;
		if (this.greifbar) {
			griffmaterial.setColor("Color", new ColorRGBA(0f, 1f, 0f, 1f));
		} else {
			griffmaterial.setColor("Color", new ColorRGBA(1f, 0f, 0f, 1f));
		}

	}

}

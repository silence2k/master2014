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
	
	protected final ColorRGBA Greifbar = new ColorRGBA(0f, 1f, 0f, 1f);
	protected final ColorRGBA Gegriffen = new ColorRGBA(1f, 0f, 0f, 1f);
	protected final ColorRGBA Inaktiv = new ColorRGBA(1f, 1f, 1f, 1f);
	
	
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

	protected MyMaterial griffmaterial;

	protected Geometry griff1 = null;
	
	
	protected MyMaterial griffmaterial2;

	protected Geometry griff2 = null;
	
	
	protected boolean greifbar = true;
	
	
	

	public abstract Node init(AssetManager assetManager, Vector3f position);

	public abstract void update();
	
	protected abstract void init();

	protected void buildGriff1(Vector3f position, AssetManager assetManager) {
		griff1 = buildGriff("griff1", position, assetManager);
	}
	
	protected void buildGriff2(Vector3f position, AssetManager assetManager) {
		griff2 = buildGriff("griff2", position, assetManager);
	}

	protected Geometry buildGriff(String name, Vector3f position, AssetManager assetManager) {
		Sphere rock = new Sphere(6, 6, 0.05f);
		Geometry geo = new Geometry(name, rock);
		rock.setTextureMode(Sphere.TextureMode.Projected); // better quality on
															// spheres
		TangentBinormalGenerator.generate(rock); // for lighting effect

		Material mat_default = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat_default.setColor("Color", new ColorRGBA(1f, 1f, 0f, 1f));
		geo.setMaterial(mat_default);

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
	
	
	public float distanceFreierGriff(Aktor aktor) {
		float distance = Float.MAX_VALUE;
		if(greifbar){
			distance = griff1.getWorldTranslation().distance(aktor.getLocalTranslation());
		}
		return distance;
	}

	
	
	protected boolean isBeweglichRotation(float dx, float aktuelleRotation){
		return isBeweglichMaxRot(dx, aktuelleRotation) && isBeweglichMinRot(dx, aktuelleRotation);
	}
	
	protected boolean isBeweglichMaxRot(float dx, float aktuelleRotation){
		System.out.println("MaxRot dx: "+dx+" aktuelleRotation: " +aktuelleRotation+" maxRot: "+maxRot+" =>> "+((aktuelleRotation + dx) <= maxRot));
		return (aktuelleRotation + dx) <= maxRot;
	}
	
	protected boolean isBeweglichMinRot(float dx, float aktuelleRotation){
		System.out.println("MinRot dx: "+dx+" aktuelleRotation: " +aktuelleRotation+" minRot: "+minRot+" =>> "+((aktuelleRotation + dx) >= minRot));
		return (aktuelleRotation + dx) >= minRot;
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
			griffmaterial.setColor(new ColorRGBA(0f, 1f, 0f, 1f));
		} else {
			griffmaterial.setColor(new ColorRGBA(1f, 0f, 0f, 1f));
		}

	}

}

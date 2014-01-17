package schalttafel.artefakte;

import schalttafel.anzeige.Anzeiger;
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

	protected final float rotationDX = 0.001f;

	protected Node graficObject;

	protected AktorGriff griff1;

	protected AktorGriff griff2 = null;

	protected Anzeiger anzeiger;

	public abstract Node init(boolean physic, AssetManager assetManager, Vector3f position);

	public abstract void update(long deltaTime);

	protected abstract void init();

	protected void buildGriff1(Vector3f position, MyMaterial material, AssetManager assetManager) {
		griff1 = new AktorGriff(this, material, buildGriff("griff1", position, assetManager));
	}

	protected void buildGriff2(Vector3f position, MyMaterial material, AssetManager assetManager) {
		griff2 = new AktorGriff(this, material, buildGriff("griff2", position, assetManager));
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

	public boolean isGreifbar() {
		return griff1.isGreifbar() || (griff2 != null && griff2.isGreifbar());
	}

	public float distanceFreierGriff(Aktor aktor) {
		float distance = Float.MAX_VALUE;
		float distance2 = Float.MAX_VALUE;
		if (griff1.isGreifbar()) {
			distance = griff1.distance(aktor);
		}
		if (griff2 != null && griff2.isGreifbar()) {
			distance2 = griff2.distance(aktor);
		}

		return Math.min(distance, distance2);
	}

	public AktorGriff dichtesterFreierGriff(Aktor aktor) {
		float dis1 = Float.MAX_VALUE;
		float dis2 = Float.MAX_VALUE;
		if (griff1.isGreifbar()) {
			dis1 = griff1.distance(aktor);
		}
		if (griff2 != null && griff2.isGreifbar()) {
			dis2 = griff2.distance(aktor);
		}

		return dis1 < dis2 ? griff1 : griff2;
	}

	protected boolean isBeweglichRotation(float dx, float aktuelleRotation) {
		return isBeweglichMaxRot(dx, aktuelleRotation) && isBeweglichMinRot(dx, aktuelleRotation);
	}

	protected boolean isBeweglichMaxRot(float dx, float aktuelleRotation) {
		// System.out.println("MaxRot dx: " + dx + " aktuelleRotation: " +
		// aktuelleRotation + " maxRot: " + maxRot
		// + " =>> " + ((aktuelleRotation + dx) <= maxRot));
		return (aktuelleRotation + dx) <= maxRot;
	}

	protected boolean isBeweglichMinRot(float dx, float aktuelleRotation) {
		// System.out.println("MinRot dx: " + dx + " aktuelleRotation: " +
		// aktuelleRotation + " minRot: " + minRot
		// + " =>> " + ((aktuelleRotation + dx) >= minRot));
		return (aktuelleRotation + dx) >= minRot;
	}

	protected boolean isBeweglichTranslation(float dx, float aktuelleTranslation) {
		return isBeweglichMaxTrans(dx, aktuelleTranslation) && isBeweglichMinTrans(dx, aktuelleTranslation);
	}

	protected boolean isBeweglichMaxTrans(float dx, float aktuelleTranslation) {
		//System.out.println(aktuelleTranslation + dx +" : "+maxTrans);
		return (aktuelleTranslation + dx) <= maxTrans;
	}

	protected boolean isBeweglichMinTrans(float dx, float aktuelleTranslation) {
		//System.out.println(aktuelleTranslation + dx +" : "+minTrans);
		return (aktuelleTranslation + dx) >= minTrans;
	}

	public void setAnzeiger(Anzeiger anzeiger) {
		this.anzeiger = anzeiger;
	}

	protected void updateAnzeige(float zielwert, long deltaTime) {
		if (anzeiger != null) {
			anzeiger.setZielWert(zielwert);
			anzeiger.update(deltaTime);
		}
	}

	// public void setGreifbar(boolean greifbar) {
	// this.greifbar = greifbar;
	// if (this.greifbar) {
	// griffmaterial.setColor(new ColorRGBA(0f, 1f, 0f, 1f));
	// } else {
	// griffmaterial.setColor(new ColorRGBA(1f, 0f, 0f, 1f));
	// }
	//
	// }

}

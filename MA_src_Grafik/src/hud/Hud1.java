package hud;

import reciver.UdpReciver;
import reciver.parser.ParserSimple;

import amqdata.Kopf;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.ScreenshotAppState;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.terrain.geomipmap.TerrainGrid;
import com.jme3.terrain.geomipmap.TerrainGridLodControl;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.grid.FractalTileLoader;
import com.jme3.terrain.geomipmap.lodcalc.DistanceLodCalculator;
import com.jme3.terrain.noise.ShaderUtils;
import com.jme3.terrain.noise.basis.FilteredBasis;
import com.jme3.terrain.noise.filter.IterativeFilter;
import com.jme3.terrain.noise.filter.OptimizedErode;
import com.jme3.terrain.noise.filter.PerturbFilter;
import com.jme3.terrain.noise.filter.SmoothFilter;
import com.jme3.terrain.noise.fractal.FractalSum;
import com.jme3.terrain.noise.modulator.NoiseModulator;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.util.TangentBinormalGenerator;

import data.Standard6D;

public class Hud1 extends SimpleApplication {

	private Material mat_terrain;
	private TerrainGrid terrain;
	private float grassScale = 64;
	private float dirtScale = 16;
	private float rockScale = 128;

	private CharacterControl player3;
	private FractalSum base;
	private PerturbFilter perturb;
	private OptimizedErode therm;
	private SmoothFilter smooth;
	private IterativeFilter iterate;

	private long startTime;

	private Kopf kopf;

	private static final float deltaDefault = 0.05f;
	private static final double grenze = 400.0;

	private float deltaX = 0;
	private float deltaY = 0;
	private float deltaZ = 0;

	public Hud1(Kopf kopf) {
		super();
		this.kopf = kopf;
	}

	public static void main(final String[] args) {
		Kopf kopf = new Kopf();

//		UdpReciver reciver = new UdpReciver();
//		reciver.addDataParser(new ParserSimple(kopf));
//		Thread t = new Thread(reciver);
//		t.start();

		Hud1 app = new Hud1(kopf);
		app.start();

	}

	private void buildTerrain() {
		this.mat_terrain = new Material(this.assetManager, "Common/MatDefs/Terrain/HeightBasedTerrain.j3md");

		// Parameters to material:
		// regionXColorMap: X = 1..4 the texture that should be appliad to state
		// X
		// regionX: a Vector3f containing the following information:
		// regionX.x: the start height of the region
		// regionX.y: the end height of the region
		// regionX.z: the texture scale for the region
		// it might not be the most elegant way for storing these 3 values, but
		// it packs the data nicely :)
		// slopeColorMap: the texture to be used for cliffs, and steep mountain
		// sites
		// slopeTileFactor: the texture scale for slopes
		// terrainSize: the total size of the terrain (used for scaling the
		// texture)
		// GRASS texture
		Texture grass = this.assetManager.loadTexture("Textures/Terrain/splat/grass.jpg");
		grass.setWrap(WrapMode.Repeat);
		this.mat_terrain.setTexture("region1ColorMap", grass);
		this.mat_terrain.setVector3("region1", new Vector3f(15, 200, this.grassScale));

		// DIRT texture
		Texture dirt = this.assetManager.loadTexture("Textures/Terrain/splat/dirt.jpg");
		dirt.setWrap(WrapMode.Repeat);
		this.mat_terrain.setTexture("region2ColorMap", dirt);
		this.mat_terrain.setVector3("region2", new Vector3f(0, 20, this.dirtScale));

		// ROCK texture
		Texture rock = this.assetManager.loadTexture("Textures/Terrain/Rock2/rock.jpg");
		rock.setWrap(WrapMode.Repeat);
		this.mat_terrain.setTexture("region3ColorMap", rock);
		this.mat_terrain.setVector3("region3", new Vector3f(198, 260, this.rockScale));

		this.mat_terrain.setTexture("region4ColorMap", rock);
		this.mat_terrain.setVector3("region4", new Vector3f(198, 260, this.rockScale));

		this.mat_terrain.setTexture("slopeColorMap", rock);
		this.mat_terrain.setFloat("slopeTileFactor", 32);

		this.mat_terrain.setFloat("terrainSize", 513);

		this.base = new FractalSum();
		this.base.setRoughness(0.7f);
		this.base.setFrequency(1.0f);
		this.base.setAmplitude(1.0f);
		this.base.setLacunarity(2.12f);
		this.base.setOctaves(8);
		this.base.setScale(0.02125f);
		this.base.addModulator(new NoiseModulator() {

			@Override
			public float value(float... in) {
				return ShaderUtils.clamp(in[0] * 0.5f + 0.5f, 0, 1);
			}
		});

		FilteredBasis ground = new FilteredBasis(this.base);

		this.perturb = new PerturbFilter();
		this.perturb.setMagnitude(0.119f);

		this.therm = new OptimizedErode();
		this.therm.setRadius(5);
		this.therm.setTalus(0.011f);

		this.smooth = new SmoothFilter();
		this.smooth.setRadius(1);
		this.smooth.setEffect(0.7f);

		this.iterate = new IterativeFilter();
		this.iterate.addPreFilter(this.perturb);
		this.iterate.addPostFilter(this.smooth);
		this.iterate.setFilter(this.therm);
		this.iterate.setIterations(1);

		ground.addPreFilter(this.iterate);

		this.terrain = new TerrainGrid("terrain", 33, 129, new FractalTileLoader(ground, 256f));

		this.terrain.setMaterial(this.mat_terrain);
		this.terrain.setLocalTranslation(0, 0, 0);
		this.terrain.setLocalScale(2f, 1f, 2f);
		this.rootNode.attachChild(this.terrain);

		TerrainLodControl control = new TerrainGridLodControl(this.terrain, this.getCamera());
		control.setLodCalculator(new DistanceLodCalculator(33, 2.7f)); // patch
																		// size,
																		// and a
																		// multiplier
		this.terrain.addControl(control);

		this.viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
	}

	private void buildSphere() {
		/**
		 * A bumpy rock with a shiny light effect. To make bumpy objects you
		 * must create a NormalMap.
		 */
		Sphere rock = new Sphere(32, 32, 2f);
		Geometry shiny_rock = new Geometry("Shiny rock", rock);
		rock.setTextureMode(Sphere.TextureMode.Projected); // better quality on
															// spheres
		TangentBinormalGenerator.generate(rock); // for lighting effect
		Material mat_lit = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
		mat_lit.setTexture("DiffuseMap", assetManager.loadTexture("Textures/Terrain/Pond/Pond.jpg"));
		mat_lit.setTexture("NormalMap", assetManager.loadTexture("Textures/Terrain/Pond/Pond_normal.png"));
		mat_lit.setBoolean("UseMaterialColors", true);
		mat_lit.setColor("Specular", ColorRGBA.White);
		mat_lit.setColor("Diffuse", ColorRGBA.White);
		mat_lit.setFloat("Shininess", 5f); // [0,128]
		shiny_rock.setMaterial(mat_lit);
		shiny_rock.setLocalTranslation(0, 2, -2); // Move it a bit
		shiny_rock.rotate(1.6f, 0, 0); // Rotate it a bit
		rootNode.attachChild(shiny_rock);

		/** Must add a light to make the lit object visible! */
		DirectionalLight sun = new DirectionalLight();
		sun.setDirection(new Vector3f(1, 0, -2).normalizeLocal());
		sun.setColor(ColorRGBA.White);
		rootNode.addLight(sun);

		this.viewPort.setBackgroundColor(new ColorRGBA(0.0f, 0.0f, 0f, 1f));
	}

	private void buildRaum() {
		Box bodenBox = new Box(100, 1, 100);
		Geometry boden = new Geometry("boden", bodenBox);
		Material mat_tl = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat_tl.setColor("Color", new ColorRGBA(0.5f, 0.5f, 0.5f, 1f));
		boden.setMaterial(mat_tl);
		rootNode.attachChild(boden);

		buildPowerwall();
		buildFenster();
		buildWand();
	}

	private void buildPowerwall() {
		Box pwBox = new Box(100, 20, 1);
		Geometry pw = new Geometry("powerwall", pwBox);
		Material mat_tl = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat_tl.setColor("Color", new ColorRGBA(1f, 0f, 0f, 1f));
		pw.setMaterial(mat_tl);
		rootNode.attachChild(pw);

		pw.setLocalTranslation(0, 10, 100);
	}

	private void buildFenster() {
		Box pwBox = new Box(1, 20, 100);
		Geometry pw = new Geometry("powerwall", pwBox);
		Material mat_tl = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat_tl.setColor("Color", new ColorRGBA(0f, 0f, 1f, 1f));
		pw.setMaterial(mat_tl);
		rootNode.attachChild(pw);

		pw.setLocalTranslation(100, 10, 0);
	}

	private void buildWand() {
		Box pwBox = new Box(1, 20, 100);
		Geometry pw = new Geometry("powerwall", pwBox);
		Material mat_tl = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat_tl.setColor("Color", new ColorRGBA(0.7f, 0.7f, 0.7f, 1f));
		pw.setMaterial(mat_tl);
		rootNode.attachChild(pw);

		pw.setLocalTranslation(-100, 10, 0);
	}

	@Override
	public void simpleInitApp() {
		this.flyCam.setMoveSpeed(100f);
		ScreenshotAppState state = new ScreenshotAppState();
		this.stateManager.attach(state);

		// TERRAIN TEXTURE material

		// buildSphere();

		buildRaum();

		// this.getCamera().setLocation(new Vector3f(0, 150, 0));
		this.getCamera().setLocation(new Vector3f(0, 20, 0));

		startTime = System.currentTimeMillis();
	}

	@Override
	public void simpleUpdate(final float tpf) {

		// long l = System.currentTimeMillis()-startTime;
		//
		// float f = (float)(l/50);
		//
		// this.getCamera().setLocation(new Vector3f(0, 150, -f));

		// System.out.println(kopf);
		// updateDelta();
		//
		// float tmpX = this.getCamera().getLocation().x + deltaX;
		// float tmpY = this.getCamera().getLocation().z + deltaY;
		//
		// this.getCamera().setLocation(new Vector3f(tmpX, 20, tmpY));

		float scale = 20;

		this.getCamera().setLocation(new Vector3f(kopf.getX() / scale, 20, -kopf.getY() / scale));

		Quaternion q = new Quaternion();

		Vector3f vX = new Vector3f(kopf.getEtaM()[0], kopf.getEtaM()[1], kopf.getEtaM()[2]);
		Vector3f vY = new Vector3f(kopf.getThetaM()[0], kopf.getThetaM()[1], kopf.getThetaM()[2]);
		Vector3f vZ = new Vector3f(kopf.getPhiM()[0], kopf.getPhiM()[1], kopf.getPhiM()[2]);

		System.out.println("kopf x:" + kopf.getX() + " y: " + kopf.getY());

		q.fromAxes(vX, vY, vZ);
		q.normalizeLocal();

		this.getCamera().setAxes(q);

		// this.getCamera().setAxes(new Quaternion(x, y, z, w));
	}

	private void updateDelta() {
		deltaX = 0;
		deltaY = 0;
		if (kopf.getX() > grenze) {
			deltaX = deltaDefault;
		} else if (kopf.getX() < -grenze) {
			deltaX = -deltaDefault;
		}
		if (kopf.getY() > grenze) {
			deltaY = -deltaDefault;
		} else if (kopf.getY() < -grenze) {
			deltaY = deltaDefault;
		}

	}
}

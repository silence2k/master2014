package schalttafel.artefakte;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Joystick extends Artefakt {

	private float xRotation;
	private float zRotation;

	private List<MyRotate> myRotateList;

	@Override
	public Node init(boolean physik, AssetManager assetManager, Vector3f position) {
		init();

		/** Load a teapot model (OBJ file from test-data) */
		graficObject = (Node) assetManager.loadModel("obj/joystick/joystick.obj");

		List<Spatial> childs = graficObject.getChildren();
		for (Spatial spatial : childs) {
			System.out.println(spatial);
		}

		// Geometry g = (Geometry) graficObject.getChild("hebel1-geom-1");
		// Material mat_default = new Material(assetManager,
		// "Common/MatDefs/Misc/Unshaded.j3md");
		// mat_default.setColor("Color", new ColorRGBA(0.5f, 0.5f, 0.5f, 1f));
		// g.setMaterial(mat_default);

		Geometry g = (Geometry) graficObject.getChild("joystick-geom-1");
		MyMaterial m = new MyMaterial(g.getMaterial());
		m.setColor(Greifbar);

		buildGriff1(new Vector3f(0, 0.40f, 0), m, assetManager);

		graficObject.setLocalTranslation(position);

		// graficObject.rotate(0, 0, 2);

		return graficObject;
	}

	@Override
	protected void init() {
		minRot = -2;
		maxRot = 2;

		initRotate();

	}

	@Override
	public void update(long deltaTime) {
		if (griff1.isGegriffen()) {

			List<MyRotate> list = messen();
			Collections.sort(list);

			list.get(0).myRotate(griff1);

		}

	}

	private List<MyRotate> messen() {
		/*
		 * - 1 2 3 + 4 5 - 6 7 8 +
		 */

		graficObject.rotate(rotationDX, 0, -rotationDX);
		myRotateList.get(0).setSortDistance(griff1.distanceToActor());
		graficObject.rotate(-rotationDX, 0, 0);
		myRotateList.get(1).setSortDistance(griff1.distanceToActor());
		graficObject.rotate(-rotationDX, 0, 0);
		myRotateList.get(2).setSortDistance(griff1.distanceToActor());
		graficObject.rotate(2 * rotationDX, 0, rotationDX);
		myRotateList.get(3).setSortDistance(griff1.distanceToActor());
		graficObject.rotate(-2 * rotationDX, 0, 0);
		myRotateList.get(4).setSortDistance(griff1.distanceToActor());
		graficObject.rotate(2 * rotationDX, 0, rotationDX);
		myRotateList.get(5).setSortDistance(griff1.distanceToActor());
		graficObject.rotate(-rotationDX, 0, 0);
		myRotateList.get(6).setSortDistance(griff1.distanceToActor());
		graficObject.rotate(-rotationDX, 0, 0);
		myRotateList.get(7).setSortDistance(griff1.distanceToActor());
		// wieder ins center stellen
		graficObject.rotate(rotationDX, 0, -rotationDX);

		return new ArrayList<>(myRotateList);
	}

	private void initRotate() {
		myRotateList = new ArrayList<>(8);

		/*
		 * - 1 2 3 + 4 5 - 6 7 8 +
		 */

		myRotateList.add(new MyRotate(rotationDX, -rotationDX));
		myRotateList.add(new MyRotate(0, -rotationDX));
		myRotateList.add(new MyRotate(-rotationDX, -rotationDX));
		myRotateList.add(new MyRotate(rotationDX, 0));
		myRotateList.add(new MyRotate(-rotationDX, 0));
		myRotateList.add(new MyRotate(rotationDX, rotationDX));
		myRotateList.add(new MyRotate(0, rotationDX));
		myRotateList.add(new MyRotate(-rotationDX, rotationDX));
	}

	private class MyRotate implements Comparable<MyRotate> {

		private final float xAngle;
		private final float zAngle;

		private float sortDistance;

		public MyRotate(float xAngle, float zAngle) {
			super();
			this.xAngle = xAngle;
			this.zAngle = zAngle;
		}

		public void setSortDistance(float sortDistance) {
			this.sortDistance = sortDistance;
		}

		public void myRotate(AktorGriff griff) {
			float oldDistance = sortDistance;
			float newDistance = 0;
			float tmpX = xAngle;
			float tmpZ = zAngle;
			while (true) {
				if (!isBeweglichRotation(xAngle, xRotation) && !isBeweglichRotation(zAngle, zRotation)) {
					// audioRadende.play();
					break;
				} else if (isBeweglichRotation(xAngle, xRotation) && !isBeweglichRotation(zAngle, zRotation)) {
					tmpZ = 0;
				} else if (!isBeweglichRotation(xAngle, xRotation) && isBeweglichRotation(zAngle, zRotation)) {
					tmpX = 0;
				}
				xRotation += tmpX;
				zRotation += tmpZ;

				graficObject.rotate(tmpX, 0, tmpZ);
				newDistance = griff.distanceToActor();
				if (newDistance > oldDistance) {
					break;
				}
				oldDistance = newDistance;
			}

		}

		@Override
		public int compareTo(MyRotate o) {
			return Float.compare(sortDistance, o.sortDistance);
		}

	}

}

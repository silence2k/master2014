package obj.loader;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import spacecore.Face;
import spacecore.WorldObject;
import collision.TestCube;

public class CollisionDetection {

	private static float SMALL_NUM = 0.0001f;

	public int detectColliosion(TestCube test, List<WorldObject> obj2) {
		int collision = 0;
		Vector3f v0 = test.Position;
		Vector3f v1 = null;
		
		List<Face> faces = test.getFaces();

		for (WorldObject worldObject : obj2) {
			List<Vector3f> woPolygone = worldObject.getModel().vertices;
			for (Vector3f verticelFlight : verticesFlight) {
				v1 = worldObject.getPt();
				for (Vector3f vectorObj2 : woPolygone) {
					collision = collision(verticelFlight, v0, vectorObj2, v1);
					if (collision == 1) {
						System.err.println("Collision with : "
								+ worldObject.getWorldObjectType().toString());
						break;
					} else {

						System.err.println("No collision with : "
								+ worldObject.getWorldObjectType().toString());

					}
				}
			}
		}
		return 0;
	}

	public int collision(Vector3f vectorObj1, Vector3f v0, Vector3f vectorObj2,
			Vector3f v1) {
		int maxc = 0;
		int crashValue = 0;
		Vector3f u = new Vector3f();

		u = Vector3f.cross(vectorObj1, vectorObj2, u);
		// System.err.println("u " + "u.x: " + u.x + " u.y" + u.y + "u.z: " +
		// u.z);
		float ax = (u.x >= 0 ? u.x : -u.x);
		float ay = (u.y >= 0 ? u.y : -u.y);
		float az = (u.z >= 0 ? u.z : -u.z);
		System.err.println(" ax: " + ax + " ay: " + ay + " az: " + az);

		if ((ax + ay + az) < SMALL_NUM) { // Pn1 and Pn2 are near parallel
			// test if disjoint or coincide
			Vector3f v = new Vector3f();
			v = Vector3f.sub(v1, v0, v);

			float dot = Vector3f.dot(vectorObj1, v);
			if (dot == 0) // Pn2.V0 lies in Pn1
			{
				System.err.println("dot == 0: " + dot);
				return 1; // Pn1 and Pn2 coincide
			} else {
				System.err.println("dot <> 0: " + dot);
				return 0;
			}
		}

		if (ax > ay) {
			if (ax > az)
				maxc = 1;
			else
				maxc = 3;
		} else {
			if (ay > az)
				maxc = 2;
			else
				maxc = 3;
		}

		System.err.println("maxc: " + maxc);
		Vector3f iP = new Vector3f(); // collisionVector
		float d1, d2; // the constants in the 2 plane equations
		// d1 = -dot(Pn1.n, Pn1.V0); // note: could be pre-stored with plane
		// d2 = -dot(Pn2.n, Pn2.V0); // ditto
		d1 = -(Vector3f.dot(vectorObj1, v0));
		System.err.println("d1" + d1);
		d2 = -(Vector3f.dot(vectorObj2, v1));
		System.err.println("d2" + d2);

		switch (maxc) { // select max coordinate
		case 1: // intersect with x=0
			iP.x = 0;
			iP.y = (d2 * vectorObj1.z - d1 * vectorObj2.z) / u.x;
			iP.z = (d1 * vectorObj2.y - d2 * vectorObj1.y) / u.x;
			break;
		case 2: // intersect with y=0
			iP.x = (d1 * vectorObj2.z - d2 * vectorObj1.z) / u.y;
			iP.y = 0;
			iP.z = (d2 * vectorObj1.x - d1 * vectorObj2.x) / u.y;
			break;
		case 3: // intersect with z=0
			iP.x = (d2 * vectorObj1.y - d1 * vectorObj2.y) / u.z;
			iP.y = (d1 * vectorObj2.x - d2 * vectorObj1.x) / u.z;
			iP.z = 0;
		}
		// L->P0 = iP;
		// L->P1 = iP + u;
		System.err.println("iP.x " + iP.x + " iP.y " + iP.y + " iP.z " + iP.z);
		if (iP.x <= 0 || iP.y <= 0 || iP.z <= 0) {
			crashValue = 1;
		}

		return crashValue;
	}
}

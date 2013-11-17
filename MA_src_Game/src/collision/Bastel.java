package collision;

import org.lwjgl.util.vector.Vector3f;

public class Bastel {
	private boolean checkTriangleCollision(Triangle triangle1, Triangle triangle2) {
	    Vector3f n1 = getN(triangle1);
	    Vector3f n2 = getN(triangle2);
	    Vector3f v = null;
	    v = Vector3f.cross(n1, n2, v);

	    Vector3f p = new Vector3f();

	    float pxy = n1.z * triangle1.vertex1.position.z * n1.x * n1.y;
	    float px = n1.z * triangle1.vertex1.position.z * n1.x * n1.y * triangle1.vertex1.position.y;
	    float py = n1.z * triangle1.vertex1.position.z * n1.x * triangle1.vertex1.position.x * n1.y;
	    float p0 = n1.z * triangle1.vertex1.position.z * n1.x * triangle1.vertex1.position.x * n1.y * triangle1.vertex1.position.y;

	    p.x = (p0/(px/pxy))/pxy;
	    p.y = (p0/(py/pxy))/pxy;

	    Vector3f x = null;
	    x = Vector3f.add(p, v, x);

	    Vector3f xq1 = null;
	    xq1 = Vector3f.sub(x, triangle1.vertex1.position, xq1);
	    float i1 = Vector3f.dot(xq1, n1);

	    Vector3f xq2 = null;
	    xq2 = Vector3f.sub(x, triangle2.vertex1.position, xq2);
	    float i2 = Vector3f.dot(xq2, n2);

	    if (i1 == 0 && i2 == 0) {
	        return true;
	    }
	    return false;
	}
	
	
//	Vector3f n1 = getN(triangle1);
//    Vector3f n2 = getN(triangle2);
//    Vector3f v = null;
//    v = Vector3f.cross(n1, n2, v);
//
//    Vector3f p = new Vector3f();
//
////P0 calcuiation
//
//    float C1 = n1.x * triangle1.vertex1.position.x + n1.y * triangle1.vertex1.position.y + n1.z * triangle1.vertex1.position.z;
//
//    float C2 = n2.x * triangle2.vertex1.position.x + n2.y * triangle2.vertex1.position.y + n2.z * triangle2.vertex1.position.z;
//
//    float Kn = n2.x / n1.x;
//
//    p.y = (C2 - C1 * Kn) / (n2.y - n1.y * Kn);
//
//    p.x = (C1 - n1.y * p.y) / n2.x;
//
//p.z = 0;
//
////P0 calcuiation ends
//
//    Vector3f x = null;
//    x = Vector3f.add(p, v, x);

	private Vector3f getN(Triangle triangle) {
	    Vector3f vn1 = null;
	    vn1 = Vector3f.sub(triangle.vertex2.position, triangle.vertex1.position, vn1);
	    Vector3f vn2 = null;
	    vn2 = Vector3f.sub(triangle.vertex3.position, triangle.vertex1.position, vn2);
	    Vector3f n = null;
	    n = Vector3f.cross(vn1, vn2, n);
	    return n;
	}
}

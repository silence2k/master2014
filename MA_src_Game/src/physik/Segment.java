package physik;

import org.lwjgl.util.vector.Vector3f;

public class Segment {

	private Vector3f p1;
	private Vector3f p2;
	
	public Segment(Vector3f p1, Vector3f p2) {
		super();
		this.p1 = p1;
		this.p2 = p2;
	}

	public Vector3f getP1() {
		return p1;
	}

	public Vector3f getP2() {
		return p2;
	}
	
	
	
}

package collision;

import org.lwjgl.util.vector.ReadableVector3f;
import org.lwjgl.util.vector.Vector3f;

public class MyVector3f extends Vector3f {

	public static final int xAchse = 0;
	public static final int yAchse = 1;
	public static final int zAchse = 2;
	
	
	
	
	public MyVector3f() {
		super();
		// TODO Auto-generated constructor stub
	}


	
	public MyVector3f(Vector3f v3f){
		this(v3f.x,v3f.y,v3f.z);
	}


	public MyVector3f(float x, float y, float z) {
		super(x, y, z);
		// TODO Auto-generated constructor stub
	}




	public MyVector3f(ReadableVector3f src) {
		super(src);
		// TODO Auto-generated constructor stub
	}




	public float get(int achse){
		switch (achse) {
		case xAchse:
			return super.x;
		case yAchse:
			return super.y;
		case zAchse:
			return super.z;
		default:
			throw new RuntimeException("der Wert:"+achse+" passt nicht!!!!");
		}
	}

}

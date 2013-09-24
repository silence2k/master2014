package util.position;

import util.values.Length;

public abstract class AbstractPosition {
	
	protected Length x, y, z;
	
//	public static Position instanceOf(double x, double y){
//		return Position2D.instanceOf(x, y);
//	}
//	
//	public static Position instanceOf(double x, double y, double z){
//		return Position3D.instanceOf(x, z, y);
//	}

//	@Override
//	public double getX() {
//		return x;
//	}
//
//	@Override
//	public double getY() {
//		return y;
//	}
//	
//	@Override
//	public double getZ() {
//		return z;
//	}
	
//	@Override
//	public int compareTo(Position position){
//		double obj1 = (Math.abs(this.getX()) + Math.abs(this.getY()) + Math.abs(this.getZ()));
//		double obj2 = (Math.abs(position.getX()) + Math.abs(position.getY()) + Math.abs(position.getZ()));
//		
//		return Double.compare(obj1, obj2);
//	}
//	
//	@Override
//	public boolean equals(Object obj){
//		if(this == obj)
//			return true;
//		if(!(obj instanceof AbstractPosition))
//			return false;
//		AbstractPosition ap = (AbstractPosition)obj;
//		if(this.getX() == ap.getX() && this.getY() == ap.getY() && this.getZ() == ap.getZ())
//			return true;
//		return false;
//	}

	/**
	 * This strings purpose is to make the origin of consoleoutputs (for debugging) easier to allocate.
	 */
	private String deb = getClass().getName() + ": ";

	/**
	 * This methods only purpose is to give the programmer more comfortability in generating a 
	 * System.out.println(Object), which in addition has the classpath and -name prepended.<br><br>
	 * .toString() is called on all objects also.
	 * @param obj - The object which's result of .toString() is to be printed on the console.
	 */
	private void out(Object obj) {
		System.out.println(deb + obj.toString());
	}
}

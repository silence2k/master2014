package util.angle;

import util.Rotationmatrix;

public class Angle3D extends AbstractAngle {
	
//	protected Angle2D x, y, z;
	Rotationmatrix rotationmatrix;
	
//	private Angle3D(Angle2D x, Angle2D y, Angle2D z){
//		this.x = x;
//		this.y = y;
//		this.z = z;
//	}
	
//	private Angle3D(double x, double y, double z){
//		this.x = Angle2D.instanceOf(x);
//		this.y = Angle2D.instanceOf(y);
//		this.z = Angle2D.instanceOf(z);
//	}
	
	private Angle3D(Rotationmatrix rotationmatrix){
		this.rotationmatrix = rotationmatrix;
	}
	
//	private Angle3D(double x, double y, double z, Rotationmatrix rotationmatrix){
//		this.x = (Angle2D)Angle2D.instanceOf(x);
//		this.y = (Angle2D)Angle2D.instanceOf(y);
//		this.z = (Angle2D)Angle2D.instanceOf(z);
//		this.rotationmatrix = rotationmatrix;
//	}
	
	public static Angle3D instanceOf(){
		return new Angle3D(Rotationmatrix.instanceOf());
	}
	
	public static Angle3D instanceOf(Angle2D x, Angle2D y, Angle2D z){
		return new Angle3D(Rotationmatrix.instanceOf(x, y, z));
	}
	
//	public static Angle3D instanceOf(double x, double y, double z){
//		return new Angle3D(x, y, z);
//	}
	
	public static Angle3D instanceOf(Rotationmatrix rotationmatrix){
		return new Angle3D(rotationmatrix); 
	}
	
//	public static Angle3D instanceOf(double x, double y, double z, Rotationmatrix rotationmatrix){
//		return new Angle3D(x, y, z, rotationmatrix); 
//	}
	
	public Rotationmatrix getRotationmatrix(){
		return rotationmatrix;
	}

	public Angle2D getX() {	
		return rotationmatrix.getX();
//		return x;
	}

	public Angle2D getY() {
		return rotationmatrix.getY();
//		return y;
	}

	public Angle2D getZ() {
		return rotationmatrix.getZ();
//		return z;
	}
	
	public boolean inbetween(Angle3D angle1, Angle3D angle2){
		boolean x, y, z;
		
		x = this.getX().inbetween(angle1.getX(), angle2.getX());
		y = this.getY().inbetween(angle1.getY(), angle2.getY());
		z = this.getZ().inbetween(angle1.getZ(), angle2.getZ());
		
		return (x && y && z);
	}
	
	public Angle3D add(Angle3D angle){
		return this.add(angle.getX(), angle.getY(), angle.getZ());
	}
	
	public Angle3D add(Angle2D xValue, Angle2D yValue, Angle2D zValue){
//		double newX = (this.getX().add(xValue)).getValue() % 360;
//		double newY = (this.getY().add(yValue)).getValue() % 360;
//		double newZ = (this.getZ().add(zValue)).getValue() % 360;
//		
//		return Angle3D.instanceOf(newX, newY, newZ);
		
		return Angle3D.instanceOf(getX().add(xValue), getY().add(yValue), getZ().add(zValue));
	}
	
//	public Angle3D add(double xValue, double yValue, double zValue){
//		return Angle3D.instanceOf(this.getX().add(xValue), this.getY().add(yValue), this.getZ().add(zValue));
//	}
	
	public Angle3D subtract(Angle3D angle){
		return subtract(angle.getX(), angle.getY(), angle.getZ());
	}
	
//	public Angle3D subtract(Angle2D x, Angle2D y, Angle2D z){
//		return subtract(x.getValue(), y.getValue(), z.getValue());
//	}
	
	public Angle3D subtract(Angle2D xValue, Angle2D yValue, Angle2D zValue){
		
		return Angle3D.instanceOf(getX().subtract(xValue), getY().subtract(yValue), getZ().subtract(zValue));
//		double newX, newY, newZ;
//		
//		newX = this.getX().getValue() - xValue;
//		newY = this.getY().getValue() - yValue;
//		newZ = this.getZ().getValue() - zValue;
//		
//		if(newX < 0)
//			newX = 360 - newX;
//		if(newY < 0)
//			newY = 360 - newX;
//		if(newY < 0)
//			newY = 360 - newX;
//		
//		return Angle3D.instanceOf(newX, newY, newZ);
	}
	
	public String toString(){
		return "Angle3D(X:"+getX().getValue()+", Y:"+getY().getValue()+", Z:"+getZ().getValue()+")";
	}

	
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

package util.converter;

import util.Rotationmatrix;
import util.angle.Angle2D;
import util.angle.Angle3D;
import util.source.Source;
import util.source.SourceImpl;
import util.values.Degree180;


public class RotationMirror implements Converter {
	
	private boolean x, y, z;

	private RotationMirror(boolean x, boolean y, boolean z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static Converter instanceOf(boolean x, boolean y, boolean z){
		return new RotationMirror(x, y, z);
	}
	
	/**
	 * Returns whether or not this Converter mirrors the rotation direction of the x-axis.
	 * @return
	 */
	public boolean getXMirror(){
		return x;
	}
	
	/**
	 * Returns whether or not this Converter mirrors the rotation direction of the y-axis.
	 * @return
	 */
	public boolean getYMirror(){
		return y;
	}
	
	/**
	 * Returns whether or not this Converter mirrors the rotation direction of the z-axis.
	 * @return
	 */
	public boolean getZMirror(){
		return z;
	}

	@Override
	public Source convert(Source source) {
		Source newSource = SourceImpl.getCopy(source);
		
//		Angle2D oldXA = source.getAngle().getX();
//		Angle2D oldYA = source.getAngle().getY();
//		Angle2D oldZA = source.getAngle().getZ();
		
		double newX = source.getAngle().getX().getValue().toDegree180().getDouble();
		double newY = source.getAngle().getY().getValue().toDegree180().getDouble();
		double newZ = source.getAngle().getZ().getValue().toDegree180().getDouble();
		
		Rotationmatrix rm = source.getAngle().getRotationmatrix();
//		Angle2D newXA = source.getAngle().getX();
//		Angle2D newYA = source.getAngle().getY();
//		Angle2D newZA = source.getAngle().getZ();
		if(getXMirror()){
			rm = rm.rotateX(Angle2D.instanceOf(Degree180.valueOf(-newX*2)));
		}
		if(getYMirror()){
			rm = rm.rotateY(Angle2D.instanceOf(Degree180.valueOf(-newY*2)));
		}
		if(getZMirror()){
			rm = rm.rotateZ(Angle2D.instanceOf(Degree180.valueOf(-newZ*2)));
		}		
		
		return newSource.setAngle(Angle3D.instanceOf(rm));
	}
	
	@Override
	public int compareTo(Converter arg) {
		return Double.compare(this.hashCode(), arg.hashCode());
	}
	
	@Override
	public String toString(){
		return "RotationMirror(X:"+x+", Y:"+y+", Z:"+z+")";
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

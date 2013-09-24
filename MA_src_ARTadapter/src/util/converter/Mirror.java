package util.converter;

import util.position.Position3D;
import util.source.Source;
import util.source.SourceImpl;
import util.values.Values;


/**
 * This Converter mirrors the attributes of a Source (as specified) on the x- and/or x-axis.
 * @author Malle
 *
 */
public class Mirror implements Converter {
	
	// Required, only when the orientation of the Sources shall also be affected by the Mirror.
//	private static Matrix<Double> xMatrix = new Matrix<Double>(new Double[][] {{-1.0, 0.0, 0.0},{0.0, 1.0, 0.0},{0.0, 0.0, 1.0}});
//	private static Matrix<Double> yMatrix = new Matrix<Double>(new Double[][] {{1.0, 0.0, 0.0},{0.0, -1.0, 0.0},{0.0, 0.0, 1.0}});
//	private static Matrix<Double> zMatrix = new Matrix<Double>(new Double[][] {{1.0, 0.0, 0.0},{0.0, 1.0, 0.0},{0.0, 0.0, -1.0}});
	
	private boolean x, y, z;
	
	private Mirror(boolean xAxis, boolean yAxis, boolean zAxis){
		x = xAxis;
		y = yAxis;
		z = zAxis;
	}
	
	/**
	 * Creates a new Mirror-Converter.
	 * @param xAxis - Whether or not the converter should mirror on the x-axis.
	 * @param yAxis - Whether or not the converter should mirror on the y-axis.
	 * @return A new Mirror-Converter.
	 */
	public static Converter instanceOf(boolean xAxis, boolean yAxis, boolean zAxis){
		return new Mirror(xAxis, yAxis, zAxis);
	}

	/**
	 * Returns whether or not this Converter mirrors on the x-axis.
	 * @return
	 */
	public boolean getXMirror(){
		return x;
	}
	
	/**
	 * Returns whether or not this Converter mirrors on the y-axis.
	 * @return
	 */
	public boolean getYMirror(){
		return y;
	}
	
	/**
	 * Returns whether or not this Converter mirrors on the z-axis.
	 * @return
	 */
	public boolean getZMirror(){
		return z;
	}
	
	@Override
	public Source convert(Source source) {
		Source newSource = SourceImpl.getCopy(source);
		
		if(x || y || z){
			double newX = source.getPosition().getX().m();
			double newY = source.getPosition().getY().m();
			double newZ = source.getPosition().getZ().m();;
			
			if(x){
				newX = newX*-1;
			}
			if(y){
				newY = newY*-1;
			}
			if(z){
				newZ = newZ*-1;
			}
			newSource = newSource.setPosition(Position3D.instanceOf(Values.lengthInM(newX), Values.lengthInM(newY), Values.lengthInM(newZ)));
		}
		
		return newSource;
	}
	
	public String toString(){
		return "Mirror(MirrorX-Axis:" + x + ", MirrorY-Axis:"+ y + ", MirrorZ-Axis:"+ z + ")";
	}
	
	@Override
	public int compareTo(Converter arg) {
		return Double.compare(this.hashCode(), arg.hashCode());		
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

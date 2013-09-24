package util.converter;

import util.position.Position3D;
import util.source.Source;
import util.source.SourceImpl;
import util.values.Values;

/**
 * This Converter shifts the Source by a given ammount on each axis.
 * @author Malle
 *
 */
public class Shifter implements Converter {

	private double x = 0;
	private double y = 0;
	private double z = 0;
	
	private Shifter(){
		
	}
	
	/**
	 * Creates a new Shift-Converter with the shifting-values set to the default of 0.
	 * @return A new Shift-Converter whose conversion has no effect until the shifting-values are changed.
	 */
	public static Shifter instanceOf(){
		return new Shifter();
	}
	
	/**
	 * Creates a new Shift-Converter with the shifting-values set to the given value.
	 * @param value - The value to which the shifting-values are to be set.
	 * @return A new Shift-Converter.
	 */
	public static Shifter instanceOf(double value){
		Shifter shifter = new Shifter();
		shifter.setShift(value);
		return shifter;
	}
	
	/**
	 * Creates a new Shift-Converter with the shifting-values set to the given values.
	 * @param xValue - The value to which the x-shifting-value is to be set.
	 * @param yValue - The value to which the y-shifting-value is to be set.
	 * @return A new Shift-Converter.
	 */
	public static Shifter instanceOf(double xValue, double yValue, double zValue){
		Shifter shifter = new Shifter();
		shifter.setXShift(xValue);
		shifter.setYShift(yValue);
		shifter.setZShift(zValue);
		return shifter;
	}
	
	/**
	 * Sets the x-shifting-value to the given value.
	 * @param value - The value for the x-attribute.
	 */
	public void setXShift(double value){
		x = value;
	}
	
	/**
	 * Sets the y-shifting-value to the given value.
	 * @param value - The value for the y-attribute.
	 */
	public void setYShift(double value){
		y = value;
	}
	
	/**
	 * Sets the z-shifting-value to the given value.
	 * @param value - The value for the z-attribute.
	 */
	public void setZShift(double value){
		z = value;
	}
	
	/**
	 * Sets the shifting-values to the given value.
	 * @param value - The value for all attributes.
	 */
	public void setShift(double value){
		x = value;
		y = value;
		z = value;
	}
	
	/**
	 * Returns the x-shifting-value.
	 * @return The x-shifting value.
	 */
	public double getXShift(){
		return x;
	}
	
	/**
	 * Returns the y-shifting-value.
	 * @return The y-shifting value.
	 */
	public double getYShift(){
		return y;
	}
	
	/**
	 * Returns the z-shifting-value.
	 * @return The z-shifting value.
	 */
	public double getZShift(){
		return z;
	}
	
	public String toString(){
		return "Shifter(X-Shift:"+ x + ", Y-Shift:" + y + ", Z-Shift:" + z + ")";
	}
	
	@Override
	public int compareTo(Converter arg) {
		return Double.compare(this.hashCode(), arg.hashCode());
	}

	@Override
	public Source convert(Source source) {
		double newX = source.getPosition().getX().m()+getXShift();
		double newY = source.getPosition().getY().m()+getYShift();
		double newZ = source.getPosition().getZ().m()+getZShift();
		
		return source.setPosition(Position3D.instanceOf(Values.lengthInM(newX), Values.lengthInM(newY), Values.lengthInM(newZ)));
	}
	
	/**
	 * This strings purpose is to make the origin of consoleoutputs (for debugging) easier to allocate.
	 */
	private String deb = getClass().getName()+": ";
	
	/**
	 * This methods only purpose is to give the programmer more comfortability in generating a 
	 * System.out.println(Object), which in addition has the classpath and -name prepended.<br><br>
	 * .toString() is called on all objects also.
	 * @param obj - The object which's result of .toString() is to be printed on the console.
	 */
	private void out(Object obj){
		System.out.println(deb+obj.toString());
	}
}

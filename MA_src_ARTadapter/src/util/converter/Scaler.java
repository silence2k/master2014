package util.converter;

import util.position.Position3D;
import util.source.Source;
import util.source.SourceImpl;
import util.values.Values;

/**
 * This Converter multiplies the position-attributes (x and y) of the Source by the values specified 
 * thereby bringing the Source to another scale.<br><br>
 * Values above 1 will scale-up. Values below 1 will scale-down. A value of 1 will result on no change.
 * @author Malle
 *
 */
public class Scaler implements Converter {
	
	private double x = 1;
	private double y = 1;
	private double z = 1;

	
	private Scaler(){
		
	}
	
	/**
	 * Creates a new Scaler-Converter with the scaling-values set to the default of 1.
	 * @return A new Scaler-Converter whose conversion has no effect until the scaling-values are changed.
	 */
	public static Converter instanceOf(){
		return new Scaler();
	}
	
	/**
	 * Creates a new Scale-Converter with the scaling-values set to the given value.
	 * @param value - The value to which the scaling-values are to be set.
	 * @return A new Scaler-Converter.
	 */
	public static Converter instanceOf(double value){
		Scaler scaler = new Scaler();
		scaler.setMultiplier(value);
		return scaler;
	}
	
	/**
	 * Creates a new Scale-Converter with the scaling-values set to the given values.
	 * @param xValue - The value to which the scaling-value of x is to be set.
	 * @param yValue - The value to which the scaling-value of y is to be set.
	 * @return A new Scale-Converter.
	 */
	public static Converter instanceOf(double xValue, double yValue, double zValue){
		Scaler scaler = new Scaler();
		scaler.setXMultiplier(xValue);
		scaler.setYMultiplier(yValue);
		scaler.setZMultiplier(zValue);
		return scaler;
	}
	
	/**
	 * Sets the x-scale-value to the given value.
	 * @param value - The scaling-value for the x-attribute.
	 */
	public void setXMultiplier(double value){
		x = value;
	}
	
	/**
	 * Sets the y-scale-value to the given value.
	 * @param value - The scaling-value for the y-attribute.
	 */
	public void setYMultiplier(double value){
		y = value;
	}
	
	/**
	 * Sets the z-scale-value to the given value.
	 * @param value - The scaling-value for the z-attribute.
	 */
	public void setZMultiplier(double value){
		z = value;
	}
	
	/**
	 * Sets all, the x-, the y- and the z-scaling-value to the given values.
	 * @param xValue - The scaling-value for the x-attribute.
	 * @param yValue - The scaling-value for the y-attribute.
	 * @param zValue - The scaling-value for the z-attribute.
	 */
	public void setMultiplier(double xValue, double yValue, double zValue){
		setXMultiplier(xValue);
		setYMultiplier(yValue);
		setZMultiplier(yValue);
	}
	
	/**
	 * Sets all scaling-values to the given value.
	 * @param value - The scaling-value for all attributes.
	 */
	public void setMultiplier(double value){
		setXMultiplier(value);
		setYMultiplier(value);
		setZMultiplier(value);
	}
	
	/**
	 * Returns the scaling-value of x.
	 * @return The y-scaling-value.
	 */
	public double getXMultiplier(){
		return x;
	}
	
	/**
	 * Returns the scaling-value of y.
	 * @return The y-scaling-value.
	 */
	public double getYMultiplier(){
		return y;
	}
	
	/**
	 * Returns the scaling-value of z.
	 * @return The z-scaling-value.
	 */
	public double getZMultiplier(){
		return y;
	}

	@Override
	public Source convert(Source source) {
		double newX = source.getPosition().getX().m()*getXMultiplier();
		double newY = source.getPosition().getY().m()*getYMultiplier();
		double newZ = source.getPosition().getZ().m()*getZMultiplier();
		
		return source.setPosition(Position3D.instanceOf(Values.lengthInMM(newX), Values.lengthInM(newY), Values.lengthInM(newZ)));
	}
	
	public String toString(){
		return "Scaler(X-Modifier:"+getXMultiplier()+", Y-Modifier:"+getYMultiplier()+", Z-Modifier:"+getZMultiplier()+")";
	}
	
	public int hashCode(){
		return (int)Math.round(getXMultiplier()+getYMultiplier()+getZMultiplier());
	}

	@Override
	public int compareTo(Converter arg) {
		return Double.compare(this.hashCode(), arg.hashCode());
	}
	
	public boolean equals(Object obj){
		if(this == obj)
			return true;
		if(!(obj instanceof Scaler))
			return false;
		Scaler scaler = (Scaler)obj;
		if(!((Double)(this.getXMultiplier())).equals(scaler.getXMultiplier()))
			return false;
		if(!((Double)(this.getYMultiplier())).equals(scaler.getYMultiplier()))
			return false;
		if(!((Double)(this.getZMultiplier())).equals(scaler.getZMultiplier()))
			return false;
		return true;
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

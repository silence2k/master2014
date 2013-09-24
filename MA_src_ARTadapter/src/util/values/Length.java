package util.values;

public interface Length extends Scalar, Comparable<Length>{
	
	/**
	 * Returns the Length in meters.
	 * @return The Length in meters.
	 */
	public abstract double m();
	/**
	 * Returns the Length in centimeters.
	 * @return The Length in centimeters.
	 */
	public abstract double cm();
	/**
	 * Returns the Length in millimeters.
	 * @return The Length in millimeters.
	 */
	public abstract double mm();
	
	public abstract Length add(Length length);
	public abstract Length sub(Length length);
	public abstract Length mul(double factor);
	public abstract Length div(double factor);
	public abstract double div(Length length);
	
	public boolean lt(Length length);
	public boolean le(Length length);
	public boolean gt(Length length);
	public boolean ge(Length length);
	
	public boolean equals(Object obj);
	
	public abstract String toString();

}

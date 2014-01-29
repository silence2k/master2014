package obj.loader;


public class Vertices3D 
{
	/** Wert der X-Achse*/
	private double x;
	/** Wert der Y-Achse*/
	private double y;
	/** Wert der Z-Achse*/
	private double z;
	
	/**
	 * Erzeugt default einen Vertices3D mit 0,0,0
	 */
	public Vertices3D()
	{
		x = 0;
		y = 0;
		z = 0;
	}
	
	/**
	 * Erzeugt einen Vertices3D
	 * @param x Wert der X-Achse
	 * @param y Wert der Y-Achse
	 * @param z Wert der Z-Achse
	 */
	public Vertices3D(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Erzeugt einen CenterPoint mit den Werten von X und Z
	 * @return
	 */
	public CenterPoint getCenterPoint_XZ()
	{
		return new CenterPoint(x,z,"");
	}

	/**
	 * Erzeugt einen BoundPoint mit den Werten von X und Y und dem CenterPoint
	 * @return
	 */
	public BoundPoint getBoundPoint_XY(CenterPoint p)
	{
		return new BoundPoint_Impl(x,y,p);
	}
	
	/**
	 * Erzeugt einen BoundPoint mit den Werten von X und Z und dem CenterPoint
	 * @return
	 */
	public BoundPoint getBoundPoint_XZ(CenterPoint p)
	{
		return new BoundPoint_Impl(x,z,p);
	}
	
	/**
	 * Erzeugt einen BoundPoint mit den Werten von Y und Z und dem CenterPoint
	 * @return
	 */
	public BoundPoint getBoundPoint_YZ(CenterPoint p)
	{
		return new BoundPoint_Impl(y,z,p);
	}

	protected double getX() {
		return x;
	}

	protected double getY() {
		return y;
	}

	protected double getZ() {
		return z;
	}

}

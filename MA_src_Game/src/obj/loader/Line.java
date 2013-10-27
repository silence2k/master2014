package obj.loader;

public class Line 
{
	protected Vertices3D point1;
	protected Vertices3D point2;
	
	/**
	 * Erzeugt eine Line zwischen 2 Punkten
	 * @param point1
	 * @param point2
	 */
	public Line(Vertices3D point1, Vertices3D point2)
	{
		this.point1 = point1;
		this.point2 = point2;
	}

}

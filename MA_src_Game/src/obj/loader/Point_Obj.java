package obj.loader;


public class Point_Obj extends Vertices3D 
{
	private String id;

	/**
	 * Erzeugt einen Point der default auf 0,0,0 sitzt
	 * @param id die ID, die der Point bekommen soll
	 */
	public Point_Obj(String id) 
	{
		super();
		this.id = id;
	}

	/**
	 * Erzeugt einen neuen Point "per Hand"
	 * 
	 * @param x Wert auf X-Achse
	 * @param y Wert auf Y-Achse
	 * @param z Wert auf Z-Achse
	 * @param id die ID, die der Point bekommen soll
	 */
	public Point_Obj(double x, double y, double z, String id) 
	{
		super(x, y, z);
		this.id = id;
	}
	/**
	 * Erzeugt einen neuen Point mit Hilfe eines Vertices3D
	 * 
	 * @param vertix Der Vertix, der die Werte fuer die 3 Achsen liefert
	 * @param id die ID, die der Point bekommen soll
	 */
	public Point_Obj(Vertices3D vertix, String id)
	{
		this(vertix.getX(),vertix.getY(),vertix.getZ(),id);
	}

	/**
	 * @see obj.loader.Vertices3D#getCenterPoint_XZ()
	 */
	@Override
	public CenterPoint getCenterPoint_XZ() 
	{
		return new CenterPoint(super.getX(),super.getZ(),id);
	}

	protected String getId() {
		return id;
	}
	
	

}

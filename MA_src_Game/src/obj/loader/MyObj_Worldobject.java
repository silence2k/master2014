package obj.loader;

import spacecore.WorldObject;

public class MyObj_Worldobject extends MyOBJ 
{

	public MyObj_Worldobject(String filename) 
	{
		super(filename);
		// TODO Auto-generated constructor stub
	}
	
	public WorldObject getWorldObject()
	{
		return null;
	}
	/**
	 * erzeugt ein neues WorldObjekt und setzt den Centerpoint ein.
	 * @param point
	 * @return das WorldObjekt incl dem uebergebenen CenterPoints
	 */
	public WorldObject getWorldObject(CenterPoint point)
	{
		return new ABS_WorldObject(Util.getShape(this.getAllFaces().get(0), point),currentMtl.getName());
	}
	
	

}

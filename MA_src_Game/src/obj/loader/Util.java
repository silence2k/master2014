package obj.loader;


import java.util.ArrayList;
import java.util.List;


public class Util 
{
	/**
	 * Erzeugt aus dem uebergebenen Face und dem Centerpoint ein Shape_WorldObject
	 * @param f Liste der Faces
	 * @param centerPoint der CenterPoint der eingebaut werden soll
	 * @return
	 */
	public static Shape_WorldObject getShape(Face f, CenterPoint centerPoint)
	{
		return new Shape_Impl(getPointList(f.getVertices(),centerPoint), centerPoint);	
	}
	
	/**
	 * Erzeugt aus einer Liste von Vertices3D eine Liste von BoundPoints mit dem übergebenen CenterPoint
	 * @param v3dList Liste der Vertices3D
	 * @param centerPoint der CenterPoint der eingebaut werden soll
	 * @return
	 */
	private static List<BoundPoint> getPointList(List<Vertices3D> v3dList, CenterPoint centerPoint)
	{
		List<BoundPoint> resultList = new ArrayList<BoundPoint>();
		for (Vertices3D v3d : v3dList) 
		{
			resultList.add(v3d.getBoundPoint_XZ(centerPoint));
		}
		return resultList;
	}
}

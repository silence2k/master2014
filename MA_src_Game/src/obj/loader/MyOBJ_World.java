package obj.loader;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class MyOBJ_World extends MyOBJ 
{
	protected CenterPoint centerpoint;
	
	/**
	 * 
	 * @param file das File aus dem die Daten fuer die World geladen werden sollen
	 * @param cp Der Centerpoint der in das MyOBJ_World soll
	 */
	public MyOBJ_World(String file,CenterPoint cp) 
	{
		super(file);
		centerpoint = cp;
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @return Liste aller Tueren aus dem File.
	 */
	public List<ABS_Door> getDoors()
	{
		Set<String> keys = materials.keySet();
		List<ABS_Door> doors = new ArrayList<ABS_Door>();
		for (String key : keys) 
		{
			if(key.startsWith("door_"))
			{
				doors.add(new ABS_Door(Util.getShape(faces.get(materials.get(key)),centerpoint), key));
			}
		}
		return doors;
	}
	/**
	 * 
	 * @return Liste aller Waende die in dem File abgespeichert sind.
	 */
	public List<ABS_WorldObject> getWalls()
	{
		Set<String> keys = materials.keySet();
		List<ABS_WorldObject> walls = new ArrayList<ABS_WorldObject>();
		for (String key : keys) 
		{
			if(key.startsWith("wall_"))
			{
				walls.add(new ABS_Door(Util.getShape(faces.get(materials.get(key)),centerpoint), key));
			}
		}
		return walls;
	}
	
	/**
	 * 
	 * @return Liste aller Punkte die als Ziel für einen Pax dienen koennen
	 */
	public List<CenterPoint> getGoalPoints()
	{
		List<CenterPoint> goalList = new ArrayList<CenterPoint>();
		for (Point_Obj point : points) 
		{
			if(point.getId().startsWith("goal_"))
			{
			goalList.add(point.getCenterPoint_XZ());
			}
		}
		return goalList;
	}
	
	/**
	 * 
	 * @return Liste der Sitzpunkte, auf den dann Sitze verankert werden koennen
	 */
	public List<CenterPoint> getSeatPoints()
	{
		List<CenterPoint> cpList = new ArrayList<CenterPoint>();
		for (Point_Obj point : points) 
		{
			if(point.getId().startsWith("seat_")) // sucht alle punkte raus, dessen Id mit "seat_" beginnen
			{
				cpList.add(point.getCenterPoint_XZ());
			}
		}
		return cpList;
	}
}

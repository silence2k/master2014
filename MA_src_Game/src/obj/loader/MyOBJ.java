package obj.loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author potrat_o
 *
 */
public class MyOBJ 
{
	/**
	 * FAKTOR bestimmt den Faktor mit denen die Werte der Vertices multipliziert wird
	 * wird zur Groessenaenderung benutzt
	 */
	public static final int FAKTOR = 100;
	
//	protected int vertices_count;
//	protected int points_count;
//	protected int lines_count;
//	protected int faces_count;
//	protected int materials_count;  // werden ersetz durch die size einer jeweiligen liste
	
	/** Liste der Vertices3D*/
	protected List<Vertices3D> vertices;
	/** Liste der Lines*/
	protected List<Line> lines;
	/** Map der Materials*/
	protected Map<String,Mtl> materials;
	/** Map der Faces*/
	protected Map<Mtl, Face> faces;
	/** Liste der Points*/
	protected List<Point_Obj> points;
	/** als letztes verwendete Mtl*/
	protected Mtl currentMtl;
	
	
	/*
	#	                Vertices: 56
	#	                  Points: 16
	#	                   Lines: 0
	#	                   Faces: 8
	#	               Materials: 24
	*/

	
	/**
	 * Erzeugt neues MyOBJ mit Daten aus dem File
	 * @param filename
	 */
	public MyOBJ(String filename)
	{
		this();
		try {
			loadData(filename);
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private MyOBJ()
	{
		vertices = new ArrayList<Vertices3D>();
		vertices.add(null); // 0. Stelle mit null belegen weil OBJ bei 1 anfaengt zu zaehlen
		lines = new ArrayList<Line>();
		lines.add(null); // 0. Stelle mit null belegen weil OBJ bei 1 anfaengt zu zaehlen
		materials = new HashMap<String, Mtl>();
		faces = new HashMap<Mtl, Face>();
		points = new ArrayList<Point_Obj>();
	}
	
	/**
	 * Gibt alle Faces zurueck
	 * @return
	 */
	public List<Face>getAllFaces()
	{
		List<Face> faceList = new ArrayList<Face>();
		Set<String> s = materials.keySet();
		for (String string : s) 
		{
			faceList.add(faces.get(materials.get(string)));
		}
		return faceList;
	}
	
	private void loadData(String filename) throws IOException
	{
		// read every line in the text file into the list
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		while (true)
		{
			String line = reader.readLine();
			// no more lines to read
			if (line == null)
			{
				reader.close();
				break;
			}
			// add every line except for comments
			if (!line.startsWith("#"))
			{
				if(line.startsWith("v"))
				{
					addVertices3D(line);
				}
				else if(line.startsWith("l"))
				{
					addLine(line);
				}
				else if(line.startsWith("f"))
				{
					addFace(line);
				}
				else if(line.startsWith("usemtl"))
				{
					addMtl(line);
				}
				else if(line.startsWith("p"))
				{
					addPoint(line);
				}
			}
		}
	}
	
	private void addPoint(String line)
	{
		String [] s = line.split(" ");
		points.add(new Point_Obj(vertices.get(Integer.parseInt(s[1])),currentMtl.getName()));
	}
	
	/**
	 * Parsen einer Face und hinzufuegen der Face zur Face-Liste
	 */
	private void addFace(String line)
	{
		String [] s = line.split(" "); // f 45 21 34
		Face face = null;
		List<Vertices3D> liste = new ArrayList<Vertices3D>();
		int size = s.length;
		for(int i = 1; i < size; i++)
		{
			liste.add(vertices.get(Integer.parseInt(s[i])));
		}
		face = new Face(liste,currentMtl);
		faces.put(currentMtl, face);
	}
	
	/**
	 * Parsen einer Mtl und hinzufuegen der Mtl zur Mtl-Liste
	 */
	private void addMtl(String line)
	{
		String [] s = line.split(" ");
		Mtl tmp = materials.get(s[1]);
		if(tmp == null)
		{
			tmp = new Mtl(s[1]);
			materials.put(s[1], tmp);
		}
		currentMtl = tmp;
	}
	
	/**
	 * Parsen einer Vertices3D und hinzufuegen der Vertices3D zur Vertices3D-Liste
	 */
	private void addVertices3D(String line)
	{
		String [] s = line.split(" ");
		double x = Double.parseDouble(s[1])*FAKTOR; // die Werte werden faktorisiert
		double y = Double.parseDouble(s[2])*FAKTOR;
		double z = Double.parseDouble(s[3])*FAKTOR;
		
		vertices.add(new Vertices3D(x,y,z));
	}
	
	private void addLine(String line)
	{
			String [] s = line.split(" ");
			//l 1 13
			int p1 = Integer.parseInt(s[1]);
			int p2 = Integer.parseInt(s[2]);
			
			lines.add(new Line(vertices.get(p1),vertices.get(p2)));
	}
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		MyOBJ obj = new MyOBJ("src/shape_data/obj/obj_data/map/map.obj");
		System.out.println(obj.toString());
	}

	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("Vertices3D: ");
		sb.append(vertices.size()-1);
		sb.append("\nLines: ");
		sb.append(lines.size()-1);
		sb.append("\nMaterials: ");
		sb.append(materials.keySet().size());
		sb.append("\nPoints: ");
		sb.append(points.size());

		return sb.toString();
	}

}

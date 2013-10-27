package obj.loader;

import java.util.List;

/**
 * @author potrat_o
 * 
 * Ein Face ist ein Polygon mit min. 3 Punkten
 *
 */
public class Face 
{
	protected List<Vertices3D> vertices; // Die Punkte aus denen die Face besteht, in Reihenfolge eines Polygonzuges
	protected Mtl material; // Das Maeterial das diese Face hat, Farbe, Leutkraft, etc
	
	/**
	 * Erzeugte mit hilfe der Vertices und der Materila ein neues Face
	 * @param vertices
	 * @param material
	 */
	public Face(List<Vertices3D> vertices, Mtl material)
	{
		this.vertices = vertices;
		this.material = material;
	}

	
	protected List<Vertices3D> getVertices() {
		return vertices;
	}

	protected void setVertices(List<Vertices3D> vertices) {
		this.vertices = vertices;
	}
}

package data;

public class Standard3D {
	
	private String id;
	
	private double x;
	private double y;
	private double z;
	
	
	public Standard3D(Standard3D stan){
		this(stan.getId(), stan.getX(), stan.getY(),stan.getZ());
	
	}
	
	
	public Standard3D(String id, double x, double y, double z) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public String getId() {
		return id;
	}


	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	
	public  Standard3D getCopy(){
		Standard3D s = new Standard3D(this.id, this.x, this.y, this.z);
		return s;
	}

	public double abstand(Standard3D s3d){
		double tx = x - s3d.x;
		double ty = y - s3d.y;
		double tz = z - s3d.z;
		return Math.sqrt(tx*tx+ty*ty+tz*tz);
	}
}

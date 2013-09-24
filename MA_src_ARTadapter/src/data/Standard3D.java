package data;

public class Standard3D {
	
	private double x;
	private double y;
	private double z;
	
	
	public Standard3D(Standard3D stan){
		this(stan.getX(), stan.getY(),stan.getZ());
	
	}
	
	
	public Standard3D(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
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
		Standard3D s = new Standard3D(this.x, this.y, this.z);
		return s;
	}

}

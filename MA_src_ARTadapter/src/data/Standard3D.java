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
	
	public double abstandBreiteHoehe(Standard3D s3d){
		double tx = x - s3d.x;
		double tz = z - s3d.z;
		return Math.sqrt(tx*tx+tz*tz);
	}
	
	public double abstandTiefe(Standard3D s3d){
		
		return (y - s3d.y) > 0?(y - s3d.y):-(y - s3d.y);
	}
	
	public double abstandOhneY(Standard3D s3d){
		double tx = x - s3d.x;
		double ty = y - s3d.y;
		double tz = z - s3d.z;
		return Math.sqrt(tx*tx+tz*tz);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Standard3D other = (Standard3D) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Standard3D [id=" + id + ", x=" + x + ", y=" + y + ", z=" + z + "]";
	}
	
	
	
}

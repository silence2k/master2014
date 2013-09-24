package util.position;

public interface Position extends Comparable<Position> {
	
	public double getX();
	public double getY();
	public double getZ();
	
	public boolean equals(Object obj);
	
	public String toString();
	
}

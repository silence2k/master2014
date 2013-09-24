package util.angle;

public interface Angle {
	
	public Angle getX();
	public Angle getY();
	public Angle getZ();
	
	public Angle add(Angle angle);
	public Angle add(double value);
	public Angle subtract(Angle angle);
	public Angle subtract(double value);
	
	public boolean equals(Object obj);
}

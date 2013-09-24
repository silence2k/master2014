package util.source;

import util.angle.Angle3D;
import util.position.Position3D;


public interface Source {
	
	public boolean setID(int id);
	public Source setPosition(Position3D position);
	public Source setAngle(Angle3D angle);
	
	public Integer getID();
	public String getName();
	public long getTimeStamp();
	public Position3D getPosition();
	public Angle3D getAngle();
	public Source getNewestCopy();
	
	public void update(long timeStamp, Position3D position, Angle3D angle);
	
	public boolean isCopy();	
	
}

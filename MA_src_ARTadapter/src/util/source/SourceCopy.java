package util.source;

import util.angle.Angle3D;
import util.position.Position3D;


public class SourceCopy extends AbstractSource {

//	private SourceCopy(Source source){
//		this.id = source.getID();
//		this.name = source.getName();
//		this.timeStamp = source.getTimeStamp();
//		this.position = source.getPosition();
//		this.angle = source.getAngle();
//		
//		setNewCopy(this);
//	}
	
	private SourceCopy(Integer id, String name, long timeStamp, Position3D position, Angle3D angle){
		this.id = id;
		this.name = name;
		this.timeStamp = timeStamp;
		this.position = position;
		this.angle = angle;
		
		setNewCopy(this);
	}
	
	public static SourceCopy instanceOf(Source source){
		return new SourceCopy(source.getID(), source.getName(), source.getTimeStamp(), source.getPosition(), source.getAngle());
//		return new SourceCopy(source);
	}
	
	public static SourceCopy instanceOf(Source source, Position3D position){
		return new SourceCopy(source.getID(), source.getName(), source.getTimeStamp(), position, source.getAngle());
	}
	
	public static SourceCopy instanceOf(Source source, Angle3D angle){
		return new SourceCopy(source.getID(), source.getName(), source.getTimeStamp(), source.getPosition(), angle);
	}
	
	public static SourceCopy instanceOf(Source source, long timeStamp, Position3D position, Angle3D angle){
		return new SourceCopy(source.getID(), source.getName(), timeStamp, position, angle);
	}
	
	@Override
	public boolean setID(int id) {
		return false;
	}
	
	@Override
	public void update(long timeStamp, Position3D position, Angle3D angle){
		this.setTimeStamp(timeStamp);
		this.setPosition(position);
		this.setAngle(angle);
		
		setNewCopy(SourceCopy.instanceOf(this));
	}

	@Override
	public boolean isCopy() {
		return true;
	}

}

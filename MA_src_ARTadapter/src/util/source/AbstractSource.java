package util.source;

import util.angle.Angle3D;
import util.position.Position3D;


public abstract class AbstractSource implements Source, Comparable<Source> {

	protected Integer id;
	protected String name;
	protected long timeStamp;
	protected Position3D position;
	protected Angle3D angle;

	protected Source newestCopy;
	
	// TODO Attributes, that may be implemented in a later stage:
	//Type
	//Color
	//DopplerEffect
	
	protected void setTimeStamp(long timeStamp){
		this.timeStamp = timeStamp;
	}
	
//	@Override
//	public Source setPosition(Position3D position){
//		this.position = position;
//		return this;
//	}
	
	@Override
	public Source setPosition(Position3D position){
		return SourceCopy.instanceOf(this, position);
	}
	
//	@Override
//	public Source setAngle(Angle3D angle){
//		this.angle = angle;
//		return this;
//	}
	
	@Override
	public Source setAngle(Angle3D angle){
		return SourceCopy.instanceOf(this, angle);
	}
	

	
	@Override
	public Integer getID(){
		return id;
	}
	
	@Override
	public String getName(){
		return name;
	}
	
	@Override
	public long getTimeStamp(){
		return timeStamp;
	}
	
	@Override
	public Position3D getPosition(){
		return position;
	}
	
	@Override
	public Angle3D getAngle(){
		return angle;
	}
	
	@Override
	public synchronized Source getNewestCopy(){
		SourceImpl original = (SourceImpl)SourceImpl.getSource(this.getID());
		return original.newestCopy;
	}
	
	protected synchronized void setNewCopy(SourceCopy source){
		SourceImpl original = (SourceImpl)SourceImpl.getSource(source.getID());
		original.newestCopy = source;
	}
	
	@Override
	public String toString(){
		return "Source(ID:"+id+", Name:"+name+", X:"+getPosition().getX()+", Y:"+getPosition().getY()+", Angle:"+angle+")";
	}
	
	@Override
	public int hashCode(){
		return getID();
	}
	
	@Override
	public int compareTo(Source arg) {
		return Double.compare(this.getID(), arg.getID());
	}
	
	@Override
	public boolean equals(Object arg){
		if(this == arg)
			return true;
		if(!(arg instanceof Source))
			return false;
		Source sObj = (Source)arg;
		if(!(this.getID() == sObj.getID()))
			return false;
		if(!(this.getPosition().getX().equals(sObj.getPosition().getX())))
			return false;
		if(!(this.getPosition().getY().equals(sObj.getPosition().getY())))
			return false;
		if(!((this.getAngle().getZ().equals(sObj.getAngle().getZ()))))
			return false;
		
		return true;
	}
	
	/**
	 * This strings purpose is to make the origin of consoleoutputs (for debugging) easier to allocate.
	 */
	private String deb = getClass().getName()+": ";
	
	/**
	 * This methods only purpose is to give the programmer more comfortability in generating a 
	 * System.out.println(Object) (for debugging), which in addition has the classpath and -name prepended.<br><br>
	 * .toString() is called on all objects also.
	 * @param obj - The object which's result of .toString() is to be printed on the console.
	 */
	private void out(Object obj){
		System.out.println(deb+obj.toString());
	}
}

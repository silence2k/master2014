package util.values;

import util.angle.Angle3D;
import util.position.Position3D;

public class NullSource extends AbstractSource implements Source {

	private static NullSource entity = new NullSource();
	
	private NullSource(){
		this.name = "NullSource";
		this.id = -1;
		this.position = Position3D.instanceOf();
		this.angle = Angle3D.instanceOf();
		this.timeStamp = System.currentTimeMillis();
	}
	
	public static NullSource instanceOf(){
		return entity;
	}
	
	@Override
	public boolean setID(int id) {
		return false;
	}
	

	@Override
	public void update(long timeStamp, Position3D position, Angle3D angle) {
	}

	@Override
	public boolean isCopy() {
		return false;
	}
	
	/**
	 * This strings purpose is to make the origin of consoleoutputs (for debugging) easier to allocate.
	 */
	private String deb = getClass().getName() + ": ";

	/**
	 * This methods only purpose is to give the programmer more comfortability in generating a 
	 * System.out.println(Object), which in addition has the classpath and -name prepended.<br><br>
	 * .toString() is called on all objects also.
	 * @param obj - The object which's result of .toString() is to be printed on the console.
	 */
	private void out(Object obj) {
		System.out.println(deb + obj.toString());
	}
}

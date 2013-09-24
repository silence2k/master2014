package util.angle;

public abstract class AbstractAngle{
	
//	public Angle add(double value){
//		if(this instanceof Angle1D){
//			Angle1D angle = (Angle1D) this;
//			return angle.add(value);
//		}
//		return this;
//	}
//	
//	public Angle subtract(double value){
//		if(this instanceof Angle1D){
//			Angle1D angle = (Angle1D) this;
//			return angle.subtract(value);
//		}
//		return this;
//	}
	
//	@Override
//	public boolean equals(Object obj){
//		if(this == obj)
//			return true;
//		if(!(obj instanceof AbstractAngle))
//			return false;
//		AbstractAngle aa = (AbstractAngle)obj;
//		if(this.getX() == aa.getX() && this.getY() == aa.getY() && this.getZ() == aa.getZ())
//			return true;
//		return false;
//	}

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

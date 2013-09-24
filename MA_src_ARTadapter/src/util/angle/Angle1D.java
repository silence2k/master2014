package util.angle;

public class Angle1D {

	private double value;
	
	private Angle1D(double value){
		this.value = value;
	}
	
	public static Angle1D instanceOf(double value){
		return new Angle1D(value);
	}
	
	public double getValue(){
		return value;
	}
	
	public Angle1D add(Angle1D angle){
		return add(angle.getValue());
	}
	
	public Angle1D add(double value){
		return Angle1D.instanceOf((this.getValue() + value) % 360);
	}
	
	public Angle1D subtract(Angle1D angle){
		return subtract(angle.getValue());
	}
	
	public Angle1D subtract(double value){
		double result = this.getValue() - value;
		while(result < 0){
			result = result + 360;
		}
		return Angle1D.instanceOf(result);
	}
	
	public int compareTo(Angle1D angle){
		if(this.getValue() < angle.getValue())
			return -1;
		if(this.getValue() > angle.getValue())
			return 1;
		return 0;
	}
	
	public boolean gt(Angle1D angle){
		return (compareTo(angle) > 0);
	}
	
	public boolean ge(Angle1D angle){
		return (compareTo(angle) >= 0);
	}
	
	public boolean lt(Angle1D angle){
		return (compareTo(angle) < 0);
	}
	
	public boolean le(Angle1D angle){
		return (compareTo(angle) <= 0);
	}
	
//	public Angle getDifference(Angle1D angle){
//		Angle small;
//		Angle big;
//		
//		if(this.le(angle)){
//				small = this;
//				big = angle;
//		}
//		else{
//			small = angle;
//			big = this;
//		}
//		
//		big = big.subtract(small);
//		
//		return (big.getX()<180) ? big : Angle1D.instanceOf((360-big.getX())*(-1));
//	}
	
	/**
	 * Tests whether or not the Angle lies within the short angle between <b>angle1</b> and <b>angle2</b>.
	 * @param angle1
	 * @param angle2
	 * @return true - In case, that the big angle minus the small angle does not exceed 180¡ 
	 * and the testsubject is smaller or equal than the big angle.<br>
	 * true - In case, that the big angle minus the small angle does exceed 180¡ 
	 * and the testsubject is bigger or equal to the big angle.<br>
	 * false - otherwise.
	 */
	public boolean inbetween(Angle1D angle1, Angle1D angle2){
		Angle1D small = (angle1.le(angle2) ? angle1 : angle2);
		Angle1D big = (angle1.le(angle2) ? (Angle1D)angle2.subtract(small) : (Angle1D)angle1.subtract(small));
		Angle1D toTest = (Angle1D)this.subtract(small);
		
		if(big.getValue() < 180)
			return toTest.le(big);
		else
			return toTest.ge(big);
	}
	
	@Override
	public String toString(){
		return "Angle1D("+getValue()+")";
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

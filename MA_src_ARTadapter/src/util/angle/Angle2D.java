package util.angle;

import util.position.Position2D;
import util.position.Position3D;
import util.values.Degree180;
import util.values.Degree360;
import util.values.Radiant;

public class Angle2D {

	private Radiant value;
	
	private Angle2D(Radiant value){
		this.value = value;
	}
	
	public static Angle2D instanceOf(){
		return new Angle2D(Radiant.valueOf());
	}
	
	public static Angle2D instanceOf(Radiant value){
		return new Angle2D(value);
	}
	
	public static Angle2D instanceOf(Degree180 degree){
		return new Angle2D(Radiant.valueOf(degree));
	}
	
	public static Angle2D instanceOf(Degree360 degree){
		return new Angle2D(Radiant.valueOf(degree));
	}
	
	/**
	 * Creates a new instance of Angle2D with the angle of <b>position1</b> to <b>position2</b>.
	 * @param angle1
	 * @param angle2
	 * @return
	 */
	public static Angle2D instanceOf(Position2D position1, Position2D position2){
		return new Angle2D(Radiant.valueOf(Math.atan2(position2.getY().sub(position1.getY()).m(), position2.getX().sub(position1.getX()).m())));
	}
	
	public Radiant getValue(){
		return value;
	}
	
	public Angle2D add(Angle2D angle){
		return add(angle.getValue());
	}
	
	public Angle2D add(Radiant value){
		return Angle2D.instanceOf((this.getValue().add(value)));
	}
	
	public Angle2D subtract(Angle2D angle){
		return subtract(angle.getValue());
	}
	
	public Angle2D subtract(Radiant value){
		return Angle2D.instanceOf(this.getValue().subtract(value));
	}
	
	public Angle2D mul(double value){
		return Angle2D.instanceOf(Radiant.valueOf(this.getValue().getDouble() * value));
	}
	
	public Angle2D div(double value){
		return Angle2D.instanceOf(Radiant.valueOf(this.getValue().getDouble() / value));
	}
	
	public int compareTo(Angle2D angle){
		return this.getValue().compareTo(angle.getValue());
	}
	
	public boolean gt(Angle2D angle){
		return (compareTo(angle) > 0);
	}
	
	public boolean ge(Angle2D angle){
		return (compareTo(angle) >= 0);
	}
	
	public boolean lt(Angle2D angle){
		return (compareTo(angle) < 0);
	}
	
	public boolean le(Angle2D angle){
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
	public boolean inbetween(Angle2D angle1, Angle2D angle2){
		Angle2D small = (angle1.le(angle2) ? angle1 : angle2);
		Angle2D big = (angle1.le(angle2) ? angle2.subtract(small) : angle1.subtract(small));
		Angle2D toTest = this.subtract(small);
		
		Angle2D angle0 = Angle2D.instanceOf();
		
//		if(toTest.ge(small) && toTest.le(big))
//			return true;
//		else
//			return false;
		
		if(big.getValue().toDegree180().getDouble() >= 0)
			return toTest.le(big);
		else
			return toTest.ge(big);
	}
	
	public Angle2D differenceTo(Angle2D angle){
		return angle.subtract(this);
	}
	
	@Override
	public boolean equals(Object obj){
		if(this == obj)
			return true;
		if(!(obj instanceof Angle2D))
			return false;
		Angle2D a = (Angle2D)obj;
		if(this.getValue().equals(a.getValue()))
			return true;
		return false;
	}
	
	@Override
	public String toString(){
		return "Angle2D("+getValue()+")";
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

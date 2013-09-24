package util;

import util.angle.Angle2D;
import util.position.Position2D;
import util.values.Length;
import util.values.Values;

public class Straight2D implements Straight {

	private double m;
	private Length b;
	
	private Straight2D(Position2D point1, Position2D point2){
		
		if(point1.getX().equals(point2.getX()))
			throw new IllegalArgumentException("The two positions may not have the same x-value!");
		if(point1.getY().equals(point2.getY()))
			throw new IllegalArgumentException("The two positions may not have the same y-value!");
		
		Length x1, x2, y1, y2;
		
		x1 = point1.getX();
		y1 = point1.getY();
		x2 = point2.getX();
		y2 = point2.getY();
		
		this.m = (y2.sub(y1)).div(x2.sub(x1));
		out("m="+m);
		this.b = y1.sub(x1.mul(m));
	}
	
	private Straight2D(Position2D point, Angle2D angle){
		this.m = Math.tan(angle.getValue().getDouble());
		this.b = point.getY().sub(point.getX().mul(m));
	}
	
	public static Straight2D instanceOf(Position2D point1, Position2D point2){
		return new Straight2D(point1, point2);
	}
	
	public static Straight2D instanceOf(Position2D point, Angle2D angle){
		return new Straight2D(point, angle);
	}
	
	public static Position2D getIntersection(Straight2D straight1, Straight2D straight2){
		double m1 = straight1.getM();
		double m2 = straight2.getM();
		double b1 = straight1.getB().m();
		double b2 = straight2.getB().m();
		
//		System.out.println(m1+", "+m2+", "+b1+", "+b2);
		
		Length x = Values.lengthInM((b2 - b1) / (m1 - m2));
		Length y = straight1.getY(x);
		
//		System.out.println(x);
		
		return Position2D.instanceOf(x, y);
	}
	
	@Override
	public double getM(){
		return m;
	}
	
	@Override
	public Length getB(){
		return b;
	}
	
//	public Angle2D getAngle(){
//		return Math.
//	}
	
	@Override
	public Length getX(Length y) {
		return y.sub(getB()).div(getM());
	}

	@Override
	public Length getY(Length x) {
		return x.mul(getM()).add(getB());
	}
	
	@Override
	public String toString(){
		return "Straight2D(m="+getM()+", b="+getB()+")";
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

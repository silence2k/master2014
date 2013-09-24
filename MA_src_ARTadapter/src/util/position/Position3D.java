package util.position;

import static util.values.Values.*;

import util.values.Length;
import util.values.Values;

public class Position3D extends AbstractPosition implements Comparable<Position3D> { 
	
	private Position3D(Length x, Length y, Length z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static Position3D instanceOf(){
		return new Position3D(lengthInM(0), lengthInM(0), lengthInM(0));
	}
	
	public static Position3D instanceOf(Length x, Length y, Length z){
		return new Position3D(x, y, z);
	}
	
	public Length getX() {
		return x;
	}

	public Length getY() {
		return y;
	}
	
	public Length getZ() {
		return z;
	}
	
	public Position3D add(Position3D position){
		return Position3D.instanceOf(this.getX().add(position.getX()), this.getY().add(position.getY()), this.getZ().add(position.getZ()));
	}
	
	public Position3D subtract(Position3D position){
		return Position3D.instanceOf(this.getX().sub(position.getX()), this.getY().sub(position.getY()), this.getZ().add(position.getZ()));
	}
	
	public Position3D mul(double value){
		return Position3D.instanceOf(this.getX().mul(value), this.getY().mul(value), this.getZ().mul(value));
	}
	
	public Position3D div(double value){
		return Position3D.instanceOf(this.getX().div(value), this.getY().div(value), this.getZ().div(value));
	}
	
	public Length distanceTo(Position3D position){
		double x1 = this.getX().m();
		double y1 = this.getY().m();
		double z1 = this.getZ().m();
		
		double x2 = position.getX().m();
		double y2 = position.getY().m();
		double z2 = position.getZ().m();
		
		double v1 = Math.pow((x2-x1), 2);
		double v2 = Math.pow((y2-y1), 2);
		double v3 = Math.pow((z2-z1), 2);
		
		double pResult = Math.sqrt((v1) + (v2) + (v3));
		
		return Values.lengthInM(pResult);
	}
	
	public int compareTo(Position3D position){
		Length obj1 = (this.getX()).add(this.getY()).add((this.getZ()));
		Length obj2 = (position.getX()).add(position.getY()).add((position.getZ()));
		
		return obj1.compareTo(obj2);
	}
	
	@Override
	public boolean equals(Object obj){
		if(this == obj)
			return true;
		if(!(obj instanceof Position3D))
			return false;
		Position3D p = (Position3D)obj;
		if(this.getX() == p.getX() && this.getY() == p.getY() && this.getZ() == p.getZ())
			return true;
		return false;
	}
	
	public String toString(){
		return "Position(X:"+x+", Y:"+y+", Z:"+z+")";
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

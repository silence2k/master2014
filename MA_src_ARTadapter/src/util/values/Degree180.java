package util.values;

public class Degree180 implements Comparable<Degree180> {

	private double value;
	
	private Degree180(double value){
		this.value = normalize(value);
	}
	
	public static Degree180 valueOf(){
		return new Degree180(0);
	}
	
	public static Degree180 valueOf(double value){
		return new Degree180(value);
	}
	
	public static Degree180 valueOf(Degree360 degree){
		return new Degree180(degree.getDouble());	
	}
	
	public static Degree180 valueOf(Radiant radiant){
		return new Degree180(Math.toDegrees(radiant.getDouble()));
	}
	
	private double normalize(double value){
		double result = value;
		
		while(result > 180){
			result = result - 360;
		}
		while(result < -180){
			result = result + 360;
		}
		
		return result;
	}
	
	public double getDouble(){
		return value;
	}
	
	public Degree180 add(Degree180 degree){
		return Degree180.valueOf(this.getDouble() + degree.getDouble());
	}
	
	public Degree180 subtract(Degree180 degree){
//		double result = this.getDouble() - degree.getDouble();
//		while(result < 0){
//			result = result + 360;
//		}
		return Degree180.valueOf(this.getDouble() - degree.getDouble());
	}
	
	@Override
	public int compareTo(Degree180 o) {
		return Double.compare(this.getDouble(), o.getDouble());
	}
	
	@Override
	public boolean equals(Object obj){
		if(this == obj)
			return true;
		if(!(obj instanceof Degree180))
			return false;
		Degree180 d = (Degree180)obj;
		return (Double.compare(this.getDouble(), d.getDouble()) == 0);
	}
	
	@Override
	public String toString(){
		return "Degree180("+getDouble()+")";
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

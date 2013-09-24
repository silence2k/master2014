package util.values;


/**
 * A Degree is a positive value as 0 <= value < 360.
 */
public class Degree360 implements Comparable<Degree360> {
	
	private double value;
	
	private Degree360(double value){
		this.value = normalize(value);
	}
	
	public static Degree360 valueOf(){
		return new Degree360(0);
	}
	
	public static Degree360 valueOf(double value){
		return new Degree360(value);
	}
	
	public static Degree360 valueOf(Degree180 degree){
//		if(degree.getDouble() < 0)
//			return new Degree360(360 - degree.getDouble());
//		else
			return new Degree360(degree.getDouble());
	}
	
	public static Degree360 valueOf(Radiant radiant){
		return new Degree360(Math.toDegrees(radiant.getDouble()));
	}
	
	private double normalize(double value){
		double result = value;
		
		if(result >= 0)
			result = (result % 360);
		else
			result = (360 + (result % 360));
		
		return result;
	}
	
	public double getDouble(){
		return value;
	}
	
	public Degree360 add(Degree360 degree){
//		return Degree360.valueOf((this.getDouble() + degree.getDouble()) % 360);
		return Degree360.valueOf(this.getDouble() + degree.getDouble());
	}
	
	public Degree360 subtract(Degree360 degree){
//		double result = this.getDouble() - degree.getDouble();
//		while(result < 0){
//			result = result + 360;
//		}
//		return Degree360.valueOf(result);
		
		return Degree360.valueOf(this.getDouble() - degree.getDouble());
	}
	
	@Override
	public int compareTo(Degree360 o) {
		return Double.compare(this.getDouble(), o.getDouble());
	}
	
	@Override
	public boolean equals(Object obj){
		if(this == obj)
			return true;
		if(!(obj instanceof Degree360))
			return false;
		Degree360 d = (Degree360)obj;
		return (Double.compare(this.getDouble(), d.getDouble()) == 0);
	}
	
	@Override
	public String toString(){
		return "Degree360("+getDouble()+")";
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

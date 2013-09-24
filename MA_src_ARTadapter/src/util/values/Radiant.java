package util.values;

public class Radiant implements Comparable<Radiant> {
	
	private double value;
	
	private double MAX = Math.PI*2;
	
	private Radiant(double value){
		this.value = normalize(value);
	}
	
	public static Radiant valueOf(){
		return new Radiant(0);
	}
	
	public static Radiant valueOf(double value){
		return new Radiant(value);
	}
	
	public static Radiant valueOf(Degree180 degree){
		return new Radiant(Math.toRadians(degree.getDouble()));
	}
	
	public static Radiant valueOf(Degree360 degree){
		return new Radiant(Math.toRadians(degree.getDouble()));
	}
	
	private double normalize(double value){
		double result = value;
		
		if(value >= 0)
			result = Math.abs(result % MAX);
		else
			result = (MAX + (result % MAX));
		
		return result;
	}
	
	public double getDouble(){
		return value;
	}
	
	public Radiant add(Radiant radiant){
		return Radiant.valueOf((this.getDouble() + radiant.getDouble()) % 360);
	}
	
	public Radiant subtract(Radiant radiant){
		double result = this.getDouble() - radiant.getDouble();
		while(result < 0){
			result = result + MAX;
		}
		return Radiant.valueOf(result);
	}
	
	@Override
	public int compareTo(Radiant o) {
		return Double.compare(this.getDouble(), o.getDouble());
	}
	
	@Override
	public boolean equals(Object obj){
		if(this == obj)
			return true;
		if(!(obj instanceof Radiant))
			return false;
		Radiant r = (Radiant)obj;
		if(this.compareTo(r) == 0)
			return true;
		return false;
	}
	
	public Degree180 toDegree180(){
		return Degree180.valueOf(this);
	}
	
	public Degree360 toDegree360(){
		return Degree360.valueOf(this);
	}
	
	@Override
	public String toString(){
		return("Radiant("+getDouble())+")";
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

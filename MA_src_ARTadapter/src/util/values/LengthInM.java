package util.values;

import static util.values.Values.*;

public class LengthInM extends AbstractScalar implements Length {
	
	private final double meters;
	
	private LengthInM(double meters){
		this.meters = meters;
	}

	public static Length valueOf(double meters){
		return new LengthInM(meters);
	}

	@Override
	public double m() {
		return meters;
	}

	@Override
	public double cm() {
		return meters/CMINM;
	}

	@Override
	public double mm() {
		return meters/MMINM;
	}

	@Override
	public Length add(Length length) {
		return LengthInM.valueOf(this.m()+length.m());
	}

	@Override
	public Length sub(Length length) {
		return LengthInM.valueOf(this.m()-length.m());
	}

	@Override
	public Length mul(double factor) {
		return LengthInM.valueOf(this.m()*factor);
	}

	@Override
	public Length div(double factor) {
		return LengthInM.valueOf(this.m()/factor);
	}

	@Override
	public double div(Length length) {
		return this.m()/length.m();
	}
	
	@Override
	public int hashCode(){
		return hashDouble(meters);
	}
	
	@Override
	public int compareTo(Length length){
		return Double.compare(this.m(), length.m());
	}
	
	@Override
	public boolean lt(Length length) {
		return (this.compareTo(length)== -1);
	}

	@Override
	public boolean le(Length length) {
		return (this.compareTo(length)== -1 || this.compareTo(length)== 0);
	}

	@Override
	public boolean gt(Length length) {
		return (this.compareTo(length) == +1);
	}

	@Override
	public boolean ge(Length length) {
		return (this.compareTo(length)== +1 || this.compareTo(length)== 0);
	}
	
	@Override
	public boolean equals(Object o){
		if (o == this)
			return true;
		if (!(o instanceof Length))
			return false;
		Length l = (Length)o;
		return Double.compare(this.m(), l.m()) == 0;
	}
	
	@Override
	public String toString(){
		return "Length(" + m() +" meters)";
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

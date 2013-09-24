package util.values;


public class Values {
	
	private Values(){
		
	}
	
	public static final Length ZEROLENGTH = lengthInM(0);
	
	public static final Source NULLSOURCE = NullSource.instanceOf();
	
	/**
	 * Centimeter in meter.
	 */
	public static final double CMINM = 0.01;
	/**
	 * Millimeter in meter.
	 */
	public static final double MMINM = 0.001;
	
	
	/**
	 * Milliseconds in seconds.
	 */
	static final double MSINS = 0.001;
	/**
	 * Minutes in seconds.
	 */
	static final double MINS = 60;
	/**
	 * Hours in seconds.
	 */
	static final double HINS = 3600;	
	
	public static Length lengthInM(double meters){
		return LengthInM.valueOf(meters);
	}
	
	public static Length lengthInCM(double centimeters){
		return LengthInM.valueOf(centimeters * CMINM);
	}
	
	public static Length lengthInMM(double millimeters){
		return LengthInM.valueOf(millimeters * MMINM);
	}
	
//	public static TimeDiff timeDiffInMS(double millisecond){
//		return TimeDiffInS.valueOf(millisecond * MSINS);
//	}
//	
//	public static TimeDiff timeDiffInS(double second){
//		return TimeDiffInS.valueOf(second);
//	}
//	
//	public static TimeDiff timeDiffInM(double minute){
//		return TimeDiffInS.valueOf(minute * MINS);
//	}
	
	/**
	 * Returns the difference in time between the two arguments.<p>
	 * 
	 * @param millisecond1
	 * @param millisecond2
	 * @return A positive TimeDiff, when <b>millisecond1</b> is smaller then <b>millisecond2</b>.<br>
	 * A negative TimeDiff, otherwise.
	 */
//	public static TimeDiff timeDiffInMS(double millisecond1, double millisecond2){
//		return timeDiffInMS(millisecond2 - millisecond1);
//	}
//	
//	public static TimeDiff timeDiffInS(double second1, double second2){
//		return timeDiffInS(second2 - second1);
//	}
//	
//	public static TimeDiff timeDiffInM(double minute1, double minute2){
//		return timeDiffInM(minute2 - minute1);
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

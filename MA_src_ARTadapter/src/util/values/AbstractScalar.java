package util.values;

public abstract class AbstractScalar {
	
	protected static int hashDouble(double val){
		long longBits = Double.doubleToLongBits(val);
		return (int) (longBits ^ (longBits >>> 32));
	}

}

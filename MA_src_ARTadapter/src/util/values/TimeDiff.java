package util.values;

public interface TimeDiff extends Scalar, Comparable<TimeDiff>{
	
	public abstract double s();
	public abstract double m();
	public abstract double h();
	
	public abstract TimeDiff add(TimeDiff time);
	public abstract TimeDiff sub(TimeDiff time);
	public abstract TimeDiff mul(double factor);
	public abstract TimeDiff div(double factor);
	public abstract double div(TimeDiff time);
	
	public boolean lt(TimeDiff time);
	public boolean le(TimeDiff time);
	public boolean gt(TimeDiff time);
	public boolean ge(TimeDiff time);

}

package util.filter;

import java.util.Set;

import util.source.Source;


public interface Filter extends Comparable<Filter> {
	
	public Set<Source> filter(Set<Source> sourceSet);
	public Source filter(Source source);
	
	public int hashCode();
	public boolean equals(Object obj);

}

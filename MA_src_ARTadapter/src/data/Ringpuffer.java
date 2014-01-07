package data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Ringpuffer<E>{
	
	private int index;
	
	private List<Collection<E>> list;

	public Ringpuffer(int size) {
		super();
		init(size);
	}
	
	private void init(int size){
		list = new ArrayList<>(size);
		for(int i = 0; i < size; i++){
			list.add(new ArrayList<E>());
		}
	}
	
	
	public void add(Collection<E> collection){
		list.set(index++, collection);
		index %= list.size();
	}
	
	public List<E>getAll(){
		List<E> all = new ArrayList<>();
		for(Collection<E> c: list){
			all.addAll(c);
		}
		return all;
	}

}

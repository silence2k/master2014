package data;

public abstract class ARTartefakt {
	
	public double faktor = 1;
	
	protected long id;
	protected double quality;
	

	public long getId() {
		return id;
	}
	
	public void setQuality(double quality) {
		this.quality = quality;
	}
	
	
	protected void parseIDandQuality(String s){
		
	}
	
	public void update(String s){
		parse(s);
//		System.out.println(this);
	}
	
	protected abstract void parse(String s);
	
	public void print(String[] arr){
		for (String string : arr) {
			System.out.println(string);
		}
	}
	

}

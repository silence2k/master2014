package data;

public class Standard3DExtented extends Standard3D {

	private int frameNr;

	public Standard3DExtented(Standard3D stan, int frameNr) {
		super(stan);
		this.frameNr = frameNr;
	}
	
	public String getKey(){
		return frameNr+"_"+getId();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + frameNr;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Standard3DExtented other = (Standard3DExtented) obj;
		if (frameNr != other.frameNr)
			return false;
		return true;
	}
	
	
	
	
	

}

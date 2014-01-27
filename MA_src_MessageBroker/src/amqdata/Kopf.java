package amqdata;

public class Kopf {

	protected float x;
	protected float y;
	protected float z;
	
	protected float eta;
	protected float theta;
	protected float phi;
	
	protected float[] etaM = new float[3];
	protected float[] thetaM = new float[3];
	protected float[] phiM = new float[3];
	
	
	public Kopf(){
		
	}
	
	public Kopf(String amq){
		parse(amq);
	}
	
	
	
	public void update(Kopf kopf){
		this.x = kopf.x;
		this.y = kopf.y;
		this.z = kopf.z;
		
		this.eta = kopf.eta;
		this.theta = kopf.theta;
		this.phi = kopf.phi;
		
		this.etaM[0] = kopf.etaM[0];
		this.etaM[1] = kopf.etaM[1];
		this.etaM[2] = kopf.etaM[2];
		
		this.thetaM[0] = kopf.thetaM[0];
		this.thetaM[1] = kopf.thetaM[1];
		this.thetaM[2] = kopf.thetaM[2];
		
		this.phiM[0] = kopf.phiM[0];
		this.phiM[1] = kopf.phiM[1];
		this.phiM[2] = kopf.phiM[2];
		
	}

	
	
	private void parse(String s){
		String arr[] = s.split(";");
//		for(String st : arr){
//			System.out.println(st);
//		}
		
		x = Float.parseFloat(arr[0].substring(7));
		y = Float.parseFloat(arr[1].substring(2));
		z = Float.parseFloat(arr[2].substring(2));
		eta = Float.parseFloat(arr[3].substring(4));
		theta = Float.parseFloat(arr[4].substring(6));
		phi = Float.parseFloat(arr[5].substring(4));
		parseRot(arr[6]);
	}
	
	private void parseRot(String part){
		String arr[] = part.split(", ");
//		for(String st : arr){
//			System.out.println(st);
//		}
		
		etaM[0] = Float.parseFloat(arr[0].substring(5));
		etaM[1] = Float.parseFloat(arr[1]);
		etaM[2] = Float.parseFloat(arr[2]);
		
		thetaM[0] = Float.parseFloat(arr[3]);
		thetaM[1] = Float.parseFloat(arr[4]);
		thetaM[2] = Float.parseFloat(arr[5]);
		
		phiM[0] = Float.parseFloat(arr[6]);
		phiM[1] = Float.parseFloat(arr[7]);
		phiM[2] = Float.parseFloat(arr[8].substring(0,arr[8].length()-2));
	}



	public float getX() {
		return x;
	}



	public float getY() {
		return y;
	}



	public float getZ() {
		return z;
	}



	public float getEta() {
		return eta;
	}



	public float getTheta() {
		return theta;
	}



	public float getPhi() {
		return phi;
	}



	public float[] getEtaM() {
		return etaM;
	}



	public float[] getThetaM() {
		return thetaM;
	}



	public float[] getPhiM() {
		return phiM;
	}
	
	
	
}

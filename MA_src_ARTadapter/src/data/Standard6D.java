package data;

import java.util.Arrays;

import reciver.parser.DataParser;
import util.Rotationmatrix;

public class Standard6D extends ARTartefakt implements DataParser {
	
	
	//6d 1 [12 1.000][-1654.938 -1470.036 1295.103 -170.9444 22.1562 61.4650][0.442421 -0.895931 0.039636 -0.813655 -0.419593 -0.402378 0.377133 0.145771 -0.914615]
	private double x;
	private double y;
	private double z;
	
	private double eta;
	private double theta;
	private double phi;
	
//	private double rot[];
	
	private float[] etaM = new float[3];
	private float[] thetaM = new float[3];
	private float[] phiM = new float[3];
	
	Rotationmatrix rotM = Rotationmatrix.instanceOf();
	
	
	public Standard6D(){
		super();
	}
	
	public Standard6D(String s){
		this();
		parse(s);
	}
	
	public Standard6D(Standard6D stan){
		this(stan.getX(), stan.getY(),stan.getZ());
	}
	
	
	public Standard6D(double x, double y, double z) {
		this();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	
	public  Standard6D getCopy(){
		Standard6D s = new Standard6D(this.x, this.y, this.z);
		return s;
	}
	
	//[12 1.000][-1654.938 -1470.036 1295.103 -170.9444 22.1562 61.4650][0.442421 -0.895931 0.039636 -0.813655 -0.419593 -0.402378 0.377133 0.145771 -0.914615]
	@Override
	public void parse(String s){
		String arr[]=s.split("]");
		for(int i = 0; i < arr.length; i++){
			arr[i] = arr[i].substring(1);
		}
		
		parseIDandQuality(arr[0]);
		parsePositionAndAngle(arr[1]);
		parseRotationsMatrix(arr[2]);
		
		
	}
	
	private void parsePositionAndAngle(String s){
		String arr[]=s.split(" ");
		//print(arr);
		
		x = faktor * Double.parseDouble(arr[0]);
		y = faktor * Double.parseDouble(arr[1]);
		z = faktor * Double.parseDouble(arr[2]);
		eta = Double.parseDouble(arr[3]);
		theta = Double.parseDouble(arr[4]);
		phi = Double.parseDouble(arr[5]);
	
	}
	
	private void parseRotationsMatrix(String s){
		//0.442421 -0.895931 0.039636 -0.813655 -0.419593 -0.402378 0.377133 0.145771 -0.914615
		String arr[]=s.split(" ");
		double doub[] = new double[9];
		for(int i = 0; i < arr.length; i++){
			doub[i] = Double.parseDouble(arr[i]);
		}
		
		rotM = Rotationmatrix.instanceOf(doub);
		
		rotM = Rotationmatrix.instanceOf(rotM.getX(), rotM.getZ(), rotM.getY());
		
		Double tmp[] = rotM.getDataX();
		
		for(int i = 0; i < etaM.length; i++){
			etaM[i] = tmp[i].floatValue();
		}
		tmp = rotM.getDataY();
		for(int i = 0; i < thetaM.length; i++){
			thetaM[i] = tmp[i].floatValue();
		}
		tmp = rotM.getDataZ();
		for(int i = 0; i < phiM.length; i++){
			phiM[i] = tmp[i].floatValue();
		}
		
		
//		for(int i = 0; i < etaM.length; i++){
//			etaM[i] = Float.parseFloat(arr[i]);
//		}
//		
//		for(int i = 0; i < thetaM.length; i++){
//			thetaM[i] = Float.parseFloat(arr[3+i]);
//		}
//		
//		for(int i = 0; i < phiM.length; i++){
//			phiM[i] = Float.parseFloat(arr[6+i]);
//		}
		
	}

	@Override
	public String toString() {
		return "Standard6D [x=" + x + ", y=" + y + ", z=" + z + ", eta=" + eta
				+ ", theta=" + theta + ", phi=" + phi + ", rot="
				+ Arrays.toString(rotM.getData()) + "]";
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	public static void main(String args[]){
		String s = "[12 1.000][-1654.938 -1470.036 1295.103 -170.9444 22.1562 61.4650][0.442421 -0.895931 0.039636 -0.813655 -0.419593 -0.402378 0.377133 0.145771 -0.914615]";
		Standard6D s6 = new Standard6D(s);
		System.out.println("s: "+s);
		System.out.println(s6.toString());
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

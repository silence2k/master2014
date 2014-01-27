package data;

import java.util.Arrays;


public class KopfART extends Standard6D{
	
	public static final double scale = 1;
	
	public static final double dx = 0;
	public static final double dy = 0;
	public static final double dz = 0;
	
	
	
	@Override
	public String toString() {
		return "Kopf[x=" + (x*scale+dx) + ";y=" + (y*scale+dy) + ";z=" + (z*scale+dz) + ";eta=" + eta
				+ ";theta=" + theta + ";phi=" + phi + ";rot="
				+ Arrays.toString(rotM.getData()) + "]";
	}
	
	
	public void updateAMQ(String amqString){
		parseAMQ(amqString);
	}
	
	
	public static void main(String args[]){
		KopfART k = new KopfART();
		//System.out.println(k.toString());
		
		String s = "Kopf[x=0.0;y=0.0;z=0.0;eta=0.0;theta=0.0;phi=0.0;rot=[1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0]]";
		k.parseAMQ(s);
	}
	
	
	private void parseAMQ(String s){
		String arr[] = s.split(";");
//		for(String st : arr){
//			System.out.println(st);
//		}
		
		x = Double.parseDouble(arr[0].substring(7));
		y = Double.parseDouble(arr[1].substring(2));
		z = Double.parseDouble(arr[2].substring(2));
		eta = Double.parseDouble(arr[3].substring(4));
		theta = Double.parseDouble(arr[4].substring(6));
		phi = Double.parseDouble(arr[5].substring(4));
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


}

package util;

import util.angle.Angle2D;
import util.angle.Angle3D;
import util.values.Radiant;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Rotationmatrix {
	
//	private double[][] array = new double[3][3];
	private Matrix matrix;
	
	private Rotationmatrix(double v11, double v12, double v13, double v21, double v22, double v23, double v31, double v32, double v33){
		Double[][] array = new Double[3][3];
		
		array[0][0] = v11;
		array[0][1] = v21;
		array[0][2] = v31;
		
		array[1][0] = v12;
		array[1][1] = v22;
		array[1][2] = v32;
		
		array[2][0] = v13;
		array[2][1] = v23;
		array[2][2] = v33;
		
		this.matrix = new Matrix(array);
	}
	
	private Rotationmatrix(Double[][] matrix){

		Matrix result = new Matrix(matrix);
		if(checkDimensions(result))
			this.matrix = new Matrix(matrix);
//		this.array = matrix;
	}
	
	private Rotationmatrix(Matrix matrix){
		this.matrix = matrix;
	}
	
	private Rotationmatrix(Angle2D x, Angle2D y, Angle2D z){
		double xV = x.getValue().getDouble();
		double yV = y.getValue().getDouble();
		double zV = z.getValue().getDouble();
		Matrix rX = new Matrix(new Double[][] {{1.0, 0.0, 0.0},{0.0, Math.cos(xV), -Math.sin(xV)},{0.0, Math.sin(xV), Math.cos(xV)}});
		Matrix rY = new Matrix(new Double[][] {{Math.cos(yV), 0.0, Math.sin(yV)},{0.0, 1.0, 0.0},{-Math.sin(yV), 0.0, Math.cos(yV)}});
		Matrix rZ = new Matrix(new Double[][] {{Math.cos(zV), -Math.sin(zV), 0.0},{Math.sin(zV), Math.cos(zV), 0.0},{0.0, 0.0, 1.0}});
		
		Matrix r = rX.mul(rY.mul(rZ));
//		Matrix r = (rZ.mul(rY)).mul(rX);
//		Matrix r = rY.mul(rZ).mul(rX);
		matrix = r;
	}
	
//	private Rotationmatrix(Angle1D x, Angle1D y, Angle1D z){
//		double xV = x.getValue();
//		double yV = y.getValue();
//		double zV = z.getValue();
//		
//		double r11,r12,r13,r21,r22,r23,r31,r32,r33;
//		
//		r11 = cos(zV) * cos(yV);
//		r12 = cos(zV) * sin(yV) * sin(xV) - sin(zV) * cos(xV);
//		r13 = cos(zV) * sin(yV) * cos(xV) + sin(zV) * sin(xV);
//		
//		r21 = sin(zV) * cos(yV);
//		r22 = sin(zV) * sin(yV) * sin(xV) + cos(zV) * cos(xV);
//		r23 = sin(zV) * sin(yV) * cos(xV) - cos(zV) * sin(xV);
//		
//		r31 = -sin(yV);
//		r32 = cos(yV) * sin(xV);
//		r33 = cos(yV) * cos(xV);
//		
//		Double[][] matrix = {{r11, r21, r31},{r12, r22, r32},{r13, r23, r33}};
//		
//		this.matrix = new Matrix(matrix);;
//	}
	
	private boolean checkDimensions(Matrix matrix){
		
		if(matrix.getColumnCount() != 3)
			throw new IllegalArgumentException("Number of columns must be 3 but is "+ matrix.getColumnCount() +"!");
		if(matrix.getRowCount() != 3)
			throw new IllegalArgumentException("Number of rows must be 3 but is "+ matrix.getRowCount() +"!");
		
		// Deprecated
//		int cols = array.length;
//		if(cols == 3){
//			if(matrix[0].length != 3)
//				throw new IllegalArgumentException("Number of rows must be 3 but is "+ matrix[0].length +"!");
//		}else{
//			throw new IllegalArgumentException("Number of columns must be 3 but is "+ cols +"!");
//		}
		
		return true;
	}
	
	public static Rotationmatrix instanceOf(double[] arr){
		return new Rotationmatrix(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8]);
	}
	
	public static Rotationmatrix instanceOf(double v11, double v12, double v13, double v21, double v22, double v23, double v31, double v32, double v33){
		return new Rotationmatrix(v11, v12, v13, v21, v22, v23, v31, v32, v33);
	}
	
	public static Rotationmatrix instanceOf(Double[][] matrix){
		return new Rotationmatrix(matrix);
	}
	
	public static Rotationmatrix instanceOf(Matrix matrix){
		return new Rotationmatrix(matrix);
	}
	
	public static Rotationmatrix instanceOf(Angle2D x, Angle2D y, Angle2D z){
		return new Rotationmatrix(x, y, z);
	}
	
	public static Rotationmatrix instanceOf(){
		return new Rotationmatrix(new Double[][] {{1.0, 0.0, 0.0},{0.0, 1.0, 0.0},{0.0, 0.0, 1.0}});
	}
	
//	public double get11(){
//		return matrix.getObject(0, 0);
//	}
//	public double get12(){
//		return matrix.getObject(0, 1);
//	}
//	public double get13(){
//		return matrix.getObject(0, 2);
//	}
//	public double get21(){
//		return array[1][0];
//	}
//	public double get22(){
//		return array[1][1];
//	}
//	public double get23(){
//		return array[1][2];
//	}
//	public double get31(){
//		return array[2][0];
//	}
//	public double get32(){
//		return array[2][1];
//	}
//	public double get33(){
//		return array[2][2];
//	}
	
	private double getValue(int row, int column){
		return matrix.get(row, column);
	}
	
	public double get(int row, int column){
		return matrix.get(row, column);
	}
	
	public Matrix getMatrix(){
		return matrix;
	}
	
	public Angle2D getX(){
		double r32 = getValue(3, 2);
		double r33 = getValue(3, 3);
		
		double cos = Math.cos(getY().getValue().getDouble());
		double arg1 = r32/cos;
		double arg2 = r33/cos;
		
		return Angle2D.instanceOf(Radiant.valueOf(Math.atan2(arg1, arg2)));
	}
	
	public Angle2D getY(){
		double r31 = getValue(3, 1);
		double r11 = getValue(1, 1);
		double r21 = getValue(2, 1);
//		out(r31 + ", "+ r11 + ", " + r21);
		double arg1 = r31*(-1);
		double arg21 = Math.pow(r11, 2);
		double arg22 = Math.pow(r21, 2);
		double arg2 = Math.sqrt(arg21 + arg22);
		
		return Angle2D.instanceOf(Radiant.valueOf(Math.atan2(arg1, arg2)));
	}
	
	public Angle2D getZ(){
		double r21 = getValue(2, 1);
		double r11 = getValue(1, 1);

		double cos = Math.cos(getY().getValue().getDouble());
		double arg1 = r21/cos;
		double arg2 = r11/cos;
		
		return Angle2D.instanceOf(Radiant.valueOf(Math.atan2(arg1, arg2)));
	}
	
	public Rotationmatrix add(Matrix matrix){
		if((matrix.getRowCount() != 3) || (matrix.getRowCount() != 3))
			throw new IllegalArgumentException("Matrix must be of the same type!");
		
		Double[][] result = new Double[3][3];
		
		for(int c=0; c<3; c++){
			for(int r=0; r<3; r++){
				result[c][r] = this.getValue(c, r) + matrix.get(c, r);
			}
		}
		
		return Rotationmatrix.instanceOf(result);
	}
	
	public Rotationmatrix subtract(Matrix matrix){
		if((matrix.getRowCount() != 3) || (matrix.getRowCount() != 3))
			throw new IllegalArgumentException("Matrix must be of the same type (3x3)!");
		
		Double[][] result = new Double[3][3];
		
		for(int c=0; c<3; c++){
			for(int r=0; r<3; r++){
				result[c][r] = this.getValue(c, r) - matrix.get(c, r);
			}
		}
		
		return Rotationmatrix.instanceOf(result);
	}
	
	public Rotationmatrix mul(Matrix matrix){
		if((matrix.getRowCount() != 3) || (matrix.getRowCount() != 3))
			throw new IllegalArgumentException("Matrix must be of the type 3x3!");
		
		Matrix pResult = getMatrix().mul(matrix);
		
//		Double[][] result = new Double[3][3];
//		
//		for(int c=0; c<3; c++){
//			for(int r=0; r<3; r++){
//				result[c][r] = 
//						this.get(1, r+1) * matrix.get(c+1, 1) +
//						this.get(2, r+1) * matrix.get(c+1, 2) +
//						this.get(3, r+1) * matrix.get(c+1, 3);
//			}
//		}
//		
		return Rotationmatrix.instanceOf(pResult);
	}
	
	public Rotationmatrix mul(Rotationmatrix matrix){
		return mul(matrix.getMatrix());
	}
	
	public Rotationmatrix mul(Double value){		
		Double[][] result = new Double[3][3];
		
		for(int c=0; c<3; c++){
			for(int r=0; r<3; r++){
				result[c][r] = 
						this.getValue(c, r) * value;
			}
		}
		
		return Rotationmatrix.instanceOf(result);
	}
	
	public Rotationmatrix rotateX(Angle2D angle){
		double v = angle.getValue().getDouble();
		double sin = Math.sin(v);
		double cos = Math.cos(v);

		Double[][] result = {
				{1.0, 0.0, 0.0},
				{0.0, cos, -sin},
				{0.0, sin, cos}};		
		
		return this.mul(Rotationmatrix.instanceOf(result));
	}
	
	public Rotationmatrix rotateY(Angle2D angle){
		double v = angle.getValue().getDouble();
		double sin = Math.sin(v);
		double cos = Math.cos(v);
		
		Double[][] result = {
				{cos, 0.0, sin},
				{0.0, 1.0, 0.0},
				{-sin, 0.0, cos}};
		
		return this.mul(Rotationmatrix.instanceOf(result));
	}

	public Rotationmatrix rotateZ(Angle2D angle){
		double v = angle.getValue().getDouble();
		double sin = Math.sin(v);
		double cos = Math.cos(v);
		
		Double[][] result = {
				{cos, -sin, 0.0},
				{sin, cos, 0.0},
				{0.0, 0.0, 1.0}};
		
		return this.mul(Rotationmatrix.instanceOf(result));
	}
	
	public Rotationmatrix transpose(){
		return instanceOf(matrix.transpose());
	}
	
	@Override
	public String toString(){
		return matrix.toString();
	}
	
	public boolean equals(Object obj){
		if(this == obj)
			return true;
		if(!(obj instanceof Rotationmatrix))
			return false;
		Rotationmatrix rm = (Rotationmatrix)obj;
		return this.getMatrix().equals(rm.getMatrix());
	}
	
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
	
	public double[] getData(){
		return matrix.getData();
	}
	
	public Double[] getDataX(){
		return matrix.getDataX();
	}
	
	public Double[] getDataY(){
		return matrix.getDataY();
	}
	
	public Double[] getDataZ(){
		return matrix.getDataZ();
	}
}

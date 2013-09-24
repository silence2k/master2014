package util;

public class Matrix {
	
	private Double[][] matrix;
	
	public Matrix(Double[][] objects){
		matrix = objects.clone();
	}
	
	// TODO Is there a way to get the generic type into the static method?
//	public static Matrix<T> instanceOf(T[][] objects){
//		return new Matrix<T>(objects);
//	}
	
	public int getRowCount(){
		if(getColumnCount() > 0)
			return matrix[0].length;
		else
			return 0;
	}
	
	public int getColumnCount(){
		return matrix.length;
	}
	
//	public T getObject(int column, int row){
//		if(column>0 && column<=getColumnSize() && row>0 && row<=getRowSize())
//			return matrix[column-1][row-1];
//		else
//			throw new IllegalArgumentException("Column/row-combination out of bounds! Must be between (1,1) and (getColumnSize(),getRowSize(), bus was ("+getColumnSize()+","+getRowSize()+")!");
//	}

	public Double get(int row, int column){
		if(column>0 && column<=getColumnCount() && row>0 && row<=getRowCount())
			return matrix[row-1][column-1];
		else
			throw new IllegalArgumentException("Column/row-combination out of bounds! Must be between (1,1) and (getColumnSize(),getRowSize(), bus was ("+column+","+row+")!");
	}
	
	public Matrix mul(Matrix matrix){
		if(getColumnCount() != matrix.getRowCount())
			throw new IllegalArgumentException("The Matrix does not have the right dimensions!");
		
		Matrix a = this;
		Matrix b = matrix;
		
		Double[][] result = new Double[a.getRowCount()][b.getColumnCount()];
		
		for(int c=1; c<=getColumnCount(); c++){
			for(int r=1; r<=getRowCount(); r++){
				double value = 0; 
				
				for(int i = 1; i<=getColumnCount(); i++){
					value = value + (a.get(r, i) * b.get(i, c));
				}
				
				result[r-1][c-1] = value;
			}
		}
		
		return new Matrix(result);
	}
	
	public Matrix transpose(){
		
		int newRows = this.getColumnCount();
		int newColumns = this.getRowCount();
		
		Double[][] pResult = new Double[newColumns][newRows]; 
		
		for(int c = 0; c<getColumnCount(); c++){
			for(int r = 0; r<getRowCount(); r++){
				pResult[c][r] = get(r+1, c+1);
			}
		}
		
		return new Matrix(pResult);
		
//		T[][] pResult = this.matrix.clone();
//		out(matrix.length);
//		for(int r = 0; r<getRowCount(); r++){
//			for(int c = 0; c<getColumnCount(); c++){
//				out(r+", "+c);
//				pResult[r][c] = getObject(r+1, c+1);
//			}
//		}
//
//		return new Matrix<T>(pResult);
	}
	
	@Override
	public String toString(){
		String result = "Matrix(\n";
		for(int r = 1; r <= getRowCount(); r++){
			result = result.concat("[");
			for(int c = 1; c <= getColumnCount(); c++){
				result = result.concat(get(r, c)+" ");
			}
			result = result.concat("]\n");
		}
		
		result = result.concat(")");
		return result;
	}
	
	public boolean equals(Object obj){
		if(this == obj)
			return true;
		if(!(obj instanceof Matrix))
			return false;
		Matrix m = (Matrix)obj;
		if(getRowCount() != m.getRowCount())
			return false;
		if(getColumnCount() != m.getColumnCount())
			return false;
		
		for(int c = 1; c<=getColumnCount(); c++){
			for(int r = 1; r<=getRowCount(); r++){
				if(!(Double.compare(get(r, c), m.get(r, c)) == 0)){
//					out("this = "+getObject(r, c) +", that = "+ m.getObject(r, c));
					return false;
				}
			}
		}
		
		return true;
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
		double d[] = new double[9];
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[i].length; j++){
				d[i*3+j]=matrix[i][j];
			}
		}
		return d;
	}
	
	public Double[] getDataX(){
		return matrix[0];
	}
	
	public Double[] getDataY(){
		return matrix[1];
	}
	
	public Double[] getDataZ(){
		return matrix[2];
	}
}
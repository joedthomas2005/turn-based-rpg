package matrices;

public abstract class Matrix {
	float[][] matrix;
	protected Matrix(float[][] data) {
		this.matrix = data;
	}
	
	public abstract Matrix multiply(Matrix other);
	public abstract Matrix add(Matrix other);

}

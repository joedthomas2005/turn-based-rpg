package matrices.exceptions;

public class VectorSizeMismatchException extends MathsException{
	private static final long serialVersionUID = -3632474855573712487L;

	public VectorSizeMismatchException(int lengthA, int lengthB, String operation) {
		super("Cannot " + operation + " a size " + lengthA + " vector and a size " + lengthB + " vector.");
	}
}

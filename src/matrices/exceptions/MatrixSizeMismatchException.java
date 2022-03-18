package matrices.exceptions;

public class MatrixSizeMismatchException extends MathsException{
    public MatrixSizeMismatchException(int[] sizeA, int[] sizeB, String operation){
        super(String.format("Cannot %s a %dx%d matrix and a %dx%d matrix.", operation, sizeA[0], sizeA[1], sizeB[0], sizeB[1]));
    }
}

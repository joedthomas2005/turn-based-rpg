package matrices.exceptions;

public class CoFactorNonSquareException extends MathsException{
    public CoFactorNonSquareException(int... size){
        super(String.format("Cannot calculate cofactors for non-square matrix of size %dx%d",size[0],size[1]));
    }
}

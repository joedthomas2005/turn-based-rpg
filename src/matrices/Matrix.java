package matrices;
import matrices.exceptions.MatrixSizeMismatchException;

/**
 * A generic matrix class with defined mathematical operations.
 * Cannot be constructed directly, you must use one of the available static factory methods.
 * If more matrix sizes are required it is simple to write your own factory method, 
 * they just need to produce a row x column sized float array and pass it into the constructor.
 * This was done this way to avoid having any internal logic in the constructor and to make it possible 
 * to just pass in the values for the matrix one by one. 
 */

public class Matrix {

    final int[] size;
    float[][] data;

    private Matrix(float[][] data){
        this.data = data;
        this.size = new int[]{data.length, data[0].length};
    }

    /**
     * Returns a string representation of the matrix
     * Columns will be shown separated by spaces with each row on a new line
     * @return a string representation of the matrix
     */

    public String toString(){
        String string = "";
        for(float[] row : data){
            for(float column : row){
                string += column + " ";
            }
            string += "\n";
        }

        return string;
    }

    /**
    * Adds two same sized matrices and returns the result. 
    * Trying to add two differently sized matrices will throw an exception.
    * @param other the right hand matrix to add to this one
    * @return the resulting matrix after the addition
    */
    public Matrix add(Matrix other) throws MatrixSizeMismatchException{


        if(this.size[0] != other.size[0] || this.size[1] != other.size[1]){
            throw new MatrixSizeMismatchException(this.size, other.size, "add");
        }

        float[][] result = new float[this.size[0]][this.size[1]];
		for(int i = 0; i < this.size[0]; i++){
			for(int j = 0; j < this.size[1]; j++){
				result[i][j] = this.data[i][j] + other.data[i][j];
			}
		}

        return new Matrix(result);
    }

    /**
     * Transforms (multiplies) a given vector by this matrix and returns the resultant vector.
     * @param the vector to transform
     * @return the resultant vector post transformation
     */
    public Vector transform(Vector other) throws MatrixSizeMismatchException{
        if(other.data.length != this.size[0] || this.size[0] != this.size[1]){
            throw new MatrixSizeMismatchException(this.size, new int[]{other.data.length, 1}, "multiply");
        }

        float data[] = new float[other.data.length];
        for(int row = 0; row < this.size[0]; row++){
            float value = 0;
            for(int column = 0; column < this.size[1]; column++){
                value += this.data[row][column] * other.data[column];
            }
            data[row] = value;
        }
        return new Vector(data);
    }

    /**
     * Multiply two matrices together, with this one on the left.
     * The number of columns on the other matrix must be equal to the number of rows of this one.
     * @param other the right hand matrix to multiply with this one
     * @return a matrix with the number of rows of this matrix and the number of columns of right hand one
     */
    public Matrix multiply(Matrix other) throws MatrixSizeMismatchException{
        
        if(this.size[1] != other.size[0]){ //Number of columns on left != number of rows on right
            throw new MatrixSizeMismatchException(this.size, other.size, "multiply");
        }

        float[][] result = new float[this.size[0]][other.size[1]];

        for(int row = 0; row < this.size[0]; row++){

            for(int column = 0; column < this.size[1]; column++){

                float value = 0;
                for(int i = 0; i < other.size[0]; i++){
                    value += this.data[row][i] * other.data[i][column];
                }
                result[row][column] = value;
            }
        }
        

        return new Matrix(result);
        
    }

    //#region Factory methods

    /**
    * Factory method to construct a 3x3 matrix
    * @param tl the value at row <b>0</b> column <b>0</b>
    * @param tc the value at row <b>0</b> column <b>1</b>
    * @param tr the value at row <b>0</b> column <b>2</b>
    * @param cl the value at row <b>1</b> column <b>0</b>
    * @param cc the value at row <b>1</b> column <b>1</b>
    * @param cr the value at row <b>1</b> column <b>2</b>
    * @param bl the value at row <b>2</b> column <b>0</b>
    * @param bc the value at row <b>2</b> column <b>1</b>
    * @param br the value at row <b>2</b> column <b>2</b>
    * @return A 3x3 matrix 
    */
    public static Matrix Matrix3x3(
        float tl, float tc, float tr, 
        float cl, float cc, float cr, 
        float bl, float bc, float br){

            float[][] data = new float[][]{{tl,tc,tr},{cl,cc,cr},{bl,bc,br}};
            return new Matrix(data);
    }

    /**
    * Factory method to construct a 3x2 matrix
    * @param tl the value at row <b>0</b> column <b>0</b>
    * @param tr the value at row <b>0</b> column <b>1</b>
    * @param cl the value at row <b>1</b> column <b>0</b>
    * @param cr the value at row <b>1</b> column <b>1</b>
    * @param bl the value at row <b>2</b> column <b>0</b>
    * @param br the value at row <b>2</b> column <b>1</b>
    * @return A 3x2 matrix 
    */
    public static Matrix Matrix3x2(
        float tl, float tr,
        float cl, float cr, 
        float bl, float br){

            float[][] data = new float[][]{{tl,tr},{cl,cr},{bl,br}};
            return new Matrix(data);
    }

    /**
    * Factory method to construct a 2x3 matrix
    * @param tl the value at row <b>0</b> column <b>0</b>
    * @param tc the value at row <b>0</b> column <b>1</b>
    * @param tr the value at row <b>0</b> column <b>2</b>
    * @param bl the value at row <b>1</b> column <b>0</b>
    * @param bc the value at row <b>1</b> column <b>1</b>
    * @param br the value at row <b>1</b> column <b>2</b>
    * @return A 2x3 matrix 
    */
    public static Matrix Matrix2x3(
        float tl, float tc, float tr,
        float bl, float bc, float br){
        
            float[][] data = new float[][]{{tl,tc,tr},{bl,bc,br}};
            return new Matrix(data);
    }

    /**
    * Factory method to construct a 2x2 matrix
    * @param tl the value at row <b>0</b> column <b>0</b>
    * @param tr the value at row <b>0</b> column <b>1</b>
    * @param bl the value at row <b>1</b> column <b>0</b>
    * @param br the value at row <b>1</b> column <b>1</b>
    * @return A 2x2 matrix 
    */
    public static Matrix Matrix2x2(
        float tl, float tr,
        float bl, float br){
            
            float[][] data = new float[][]{{tl,tr},{bl,br}};
            return new Matrix(data);
    }

    /**
     * 
     * @param tl the value at row <b>0</b> column <b>0</b>
     * @param tcl the value at row <b>0</b> column <b>1</b>
     * @param tcr the value at row <b>0</b> column <b>2</b>
     * @param tr the value at row <b>0</b> column <b>3</b>
     * @param ctl the value at row <b>1</b> column <b>0</b>
     * @param ctcl the value at row <b>1</b> column <b>1</b>
     * @param ctcr the value at row <b>1</b> column <b>2</b>
     * @param ctr the value at row <b>1</b> column <b>3</b>
     * @param cbl the value at row <b>2</b> column <b>0</b>
     * @param cbcl the value at row <b>2</b> column <b>1</b>
     * @param cbcr the value at row <b>2</b> column <b>2</b>
     * @param cbr the value at row <b>2</b> column <b>3</b>
     * @param bl the value at row <b>3</b> column <b>0</b>
     * @param bcl the value at row <b>3</b> column <b>1</b>
     * @param bcr the value at row <b>3</b> column <b>2</b>
     * @param br the value at row <b>3</b> column <b>3</b>
     * @return A 4x4 matrix
     */
    public static Matrix Matrix4x4(
        float tl, float tcl, float tcr, float tr,
        float ctl, float ctcl, float ctcr, float ctr,
        float cbl, float cbcl, float cbcr, float cbr,
        float bl, float bcl, float bcr, float br){

            float[][] data = new float[][]{{tl,tcl,tcr,tr},{ctl,ctcl,ctcr,ctr},{cbl,cbcl,cbcr,cbr},{bl,bcl,bcr,br}};
            return new Matrix(data);
        }

    /**
     * Construct a 4x4 Identity Matrix which when multiplied by any 4x1 column vector returns that vector unchanged.
     * @return a 4x4 identity matrix
     */
    public static Matrix IdentityMatrix4x4(){
        return Matrix4x4(
            1,0,0,0,
            0,1,0,0,
            0,0,1,0,
            0,0,0,1);
    }

    /**
     * Construct a 3x3 Identity Matrix which when multiplied by any 3x1 column vector returns that vector unchanged.
     * @return a 3x3 identity matrix
     */
    public static Matrix IdentityMatrix3x3(){
        return Matrix3x3(
            1,0,0,
            0,1,0,
            0,0,1
        );
    }

    /**
     * Construct a 2x2 Identity Matrix which when multiplied by any 2x1 column vector returns that vector unchanged.
     * @return a 2x2 identity matrix
     */
    public static Matrix IdentityMatrix2x2(){
        return Matrix2x2(
            1, 0,
            0, 1);
    }
    //#endregion
}

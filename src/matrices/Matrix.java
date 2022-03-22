package matrices;
import matrices.exceptions.CoFactorNonSquareException;
import matrices.exceptions.MatrixSizeMismatchException;

/**
 * A generic matrix class with defined mathematical operations.
 * Cannot be constructed directly, you must use one of the available static factory methods.
 * If more matrix sizes are required it is simple to write your own factory method, 
 * they just need to produce a row x column sized float array and pass it into the constructor.
 * This was done this way to avoid having any internal logic in the constructor and to make it possible 
 * to just pass in the values for the matrix one by one. 
 */

public final class Matrix { //Final as constructor is private so I want an error to be thrown if I try and extend

    final int[] size;
    public float[][] data;

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
     * Calculate the cofactor of this matrix for a given element.
     * This results in a cofactor matrix with the row and column of that element removed.
     * @param row the row index of the element
     * @param column the column index of the element
     * @return a matrix with 1 less row and column than this one
     * @throws CoFactorNonSquareException
     */
    public Matrix minor(int row, int column) throws CoFactorNonSquareException{
        
        if(this.size[0] != this.size[1]){
            throw new CoFactorNonSquareException(size);
        }
        
        int curRow = 0; 
        int curColumn = 0;

        float[][] data = new float[this.size[0]-1][this.size[0]-1];
        for(int r = 0; r < this.size[0]; r++){
            for(int c = 0; c < this.size[0]; c++){

                if(r != row && c != column){
                    data[curRow][curColumn++] = this.data[r][c];
                    
                    if(curColumn == this.size[0] - 1){
                        curColumn = 0;
                        curRow++;
                    }
                }
            }
        }
        return new Matrix(data);
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
     * If the vector has less rows than the transform matrix (which usually has 4) it will be padded with 1s.
     * These will not be removed in the resulting vector so you will have to remove them yourself.
     * @param the vector to transform
     * @return the resultant vector post transformation
     */
    public Vector transform(Vector other) throws MatrixSizeMismatchException{
        if(this.size[0] != this.size[1]){
            throw new MatrixSizeMismatchException(this.size, new int[]{other.data.length, 1}, "multiply");
        }

        Vector toTransform = new Vector(new float[this.size[0]]);
        for(int i = 0; i < other.data.length; i++){
            toTransform.data[i] = other.data[i];
        }

        for(int i = other.data.length; i < this.size[0]; i++){
            toTransform.data[i] = 1;
        }

        float[] data = new float[toTransform.data.length];
        for(int row = 0; row < this.size[0]; row++){
            float value = 0;
            for(int column = 0; column < this.size[1]; column++){
                value += this.data[row][column] * toTransform.data[column];
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

    /**
     * Constructs a rotation matrix, multiplies this matrix by it and returns the result.
     * @param pitch the angle to rotate on the x axis
     * @param yaw the angle to rotate on the y axis
     * @param roll the angle to rotate on the z axis (use this for 2D rotation)
     * @return a new matrix rotated by the given angles
     */
    public Matrix rotate(float pitch, float yaw, float roll) throws MatrixSizeMismatchException{
        Matrix rotationMatrix = RotationMatrix(pitch, yaw, roll);
        return this.multiply(rotationMatrix);
    }

    /**
     * Constructs a translation matrix, multiplies this matrix by it and returns the result.
     * @param x the amount to translate in the x axis
     * @param y the amount to translate in the y axis
     * @param z the amount to translate in the z axis
     * @return a new matrix translated by the given values
     */
    public Matrix translate(float x, float y, float z) throws MatrixSizeMismatchException{
        Matrix translationMatrix = TranslationMatrix(x, y, z);
        return this.multiply(translationMatrix);
    }

    /**
     * Constructs a translation matrix, multiplies this matrix by it and returns the result.
     * @param x the amount to translate in the x axis
     * @param y the amount to translate in the y axis
     * @return a new matrix translated by the given values (0 in the z axis)
     */
    public Matrix translate(float x, float y) throws MatrixSizeMismatchException{
        Matrix translationMatrix = TranslationMatrix(x, y);
        return this.multiply(translationMatrix);
    }

    /**
     * Constructs a scale matrix, multiplies this matrix by it and returns the result.
     * @param x the amount to scale in the x axis
     * @param y the amount to scale in the y axis
     * @param z the amount to scale in the z axis
     * @return a new matrix scaled by the given values
     */
    public Matrix scale(float x, float y, float z) throws MatrixSizeMismatchException{
        Matrix scaleMatrix = ScaleMatrix(x, y, z);
        return this.multiply(scaleMatrix);
    }

    /**
     * Constructs a scale matrix, multiplies this matrix by it and returns the result.
     * @param x the amount to scale in the x axis
     * @param y the amount to scale in the y axis
     * @return a new matrix scaled by the given values (1 in the z axis)
     */
    public Matrix scale(float x, float y) throws MatrixSizeMismatchException{
        Matrix scaleMatrix = ScaleMatrix(x, y);
        return this.multiply(scaleMatrix);
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

    /**
     * Create a scale matrix with a given x, y and z scale factor.
     * This returns a matrix which can be applied to a vector with the .transform method.
     * @param x the x scale factor
     * @param y the y scale factor
     * @param z the z scale factor
     * @return a 4x4 transformation matrix
     */
    public static Matrix ScaleMatrix(float x, float y, float z){

        Matrix scaleMatrix = IdentityMatrix4x4();
        scaleMatrix.data[0][0] = x;
        scaleMatrix.data[1][1] = y;
        scaleMatrix.data[2][2] = z;
        return scaleMatrix;
    }

    /**
     * Create a scale matrix with a given x, y scale factor.
     * This returns a matrix which can be applied to a vector with the .transform method.
     * @param x the x scale factor
     * @param y the y scale factor
     * @param z the z scale factor
     * @return a 4x4 transformation matrix
     */
    public static Matrix ScaleMatrix(float x, float y){

        return ScaleMatrix(x,y,1);

    }
    
    /**
     * Create a translation matrix with a given x, y and z translation.
     * This returns a matrix which can be applied to a vector with the .transform method.
     * @param x the number of units to translate in the x axis
     * @param y the number of units to translate in the y axis
     * @param z the number of units to translate in the z axis
     * @return a 4x4 transformation matrix
     */
    public static Matrix TranslationMatrix(float x, float y, float z){

        Matrix translateMatrix = IdentityMatrix4x4();
        translateMatrix.data[0][3] = x;
        translateMatrix.data[1][3] = y;
        translateMatrix.data[2][3] = z;
        
        return translateMatrix;
    }

    /**
     * Construct a translation matrix with a given x and y translation.
     * This returns a matrix which can be applied to a vector with the .transform method.
     * @param x the number of units to translate in the x axis
     * @param y the number of units to translate in the y axis
     * @return a 4x4 transformation matrix
     */
    public static Matrix TranslationMatrix(float x, float y){
        return TranslationMatrix(x,y,0);
    }

    /**
     * Construct a rotation matrix with a given pitch, yaw and roll angle
     * @param pitch the angle to rotate in the pitch(x) axis
     * @param yaw the angle to rotate in the yaw(y) axis
     * @param roll the angle to rotate in the roll(z) axis
     * @return a 4x4 transformation matrix
     * @throws MatrixSizeMismatchException
     */
    public static Matrix RotationMatrix(float pitch, float yaw, float roll) throws MatrixSizeMismatchException{
        
        Matrix pitchMatrix = IdentityMatrix4x4();
        Matrix yawMatrix = IdentityMatrix4x4();
        Matrix rollMatrix = IdentityMatrix4x4();

        float cosPitch = (float) Math.cos(Math.toRadians(pitch));
        float sinPitch = (float) Math.sin(Math.toRadians(pitch));

        pitchMatrix.data[1][1] = cosPitch;
        pitchMatrix.data[1][2] = -sinPitch;
        pitchMatrix.data[2][1] = sinPitch;
        pitchMatrix.data[2][2] = cosPitch;

        float cosYaw = (float) Math.cos(Math.toRadians(yaw));
        float sinYaw = (float) Math.sin(Math.toRadians(yaw));

        yawMatrix.data[0][0] = cosYaw;
        yawMatrix.data[0][2] = sinYaw;
        yawMatrix.data[2][0] = -sinYaw;
        yawMatrix.data[2][2] = cosYaw;

        float cosRoll = (float) Math.cos(Math.toRadians(roll));
        float sinRoll = (float) Math.sin(Math.toRadians(roll));

        rollMatrix.data[0][0] = cosRoll;
        rollMatrix.data[0][1] = -sinRoll;
        rollMatrix.data[1][0] = sinRoll;
        rollMatrix.data[1][1] = cosRoll;

        return pitchMatrix.multiply(yawMatrix).multiply(rollMatrix);
    }
    
    public static Matrix OrthographicMatrix(float left, float right, float bottom, float top, float far, float near){
       
        Matrix ortho = IdentityMatrix4x4();
        ortho.data[0][0] = 2/(right - left);
        ortho.data[1][1] = 2/(top - bottom);
        ortho.data[2][2] = -2/(far - near);
        ortho.data[0][3] = -((right + left)/(right - left));
        ortho.data[1][3] = -((top + bottom)/(top - bottom));
        ortho.data[2][3] = -((far + near)/(far - near));
        return ortho;
    }
    //#endregion
}

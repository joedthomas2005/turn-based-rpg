package matrices;

import matrices.exceptions.VectorSizeMismatchException;

/**
 * A generic column vector class that can contain any number of elements.
 * This can be used for holding movements with direction (which can be calculated by the angleBetween method with an axis vector)
 * and magnitude which can be calculated with the .getLength() method. 
 * It can also represent cartesian coordinates as a vector from the origin.
 */
public class Vector {
	public float[] data;
	
	/**
	 * Construct a new instance of the vector class.
	 * @param data an arbitrary number of values to hold in the vector
	 */
	public Vector(float ...data) {
		this.data = new float[data.length];

		for(int i = 0; i < data.length; i++) {
			this.data[i] = data[i];
		}
		
	}
	
	
	public float x() {
		return this.data[0];
	}
	
	public float y() {
		try {
			return this.data[1];
		}
		catch(Exception e) {
			System.err.println("WARNING: VECTOR DOES NOT HAVE Y VALUE.");
			return 0;
		}
	}
	
	public float z() {
		try {
			return this.data[2];
		}
		catch(Exception e) {
			System.err.println("WARNING: VECTOR DOES NOT HAVE Z VALUE.");
			return 0;
		}
	}
	
	public float w() {
		try {
			return this.data[3];
		}
		
		catch(Exception e) {
			System.err.println("WARNING: VECTOR DOES NOT HAVE W VALUE.");
			return 0;
		}
	}
	/**
	 * Performs a component-wise addition (all components of this vector are added to the corresponding components of the other)
	 * between this and another vector of the same size and returns the resultant vector. 
	 * @param other the vector to add to this one
	 * @return the resultant vector after the addition
	 */
	public Vector add(Vector other) throws VectorSizeMismatchException {
		
		if(this.data.length != other.data.length) {
			throw new VectorSizeMismatchException(this.data.length, other.data.length, "add");
		}
		float[] result = new float[data.length];
		for(int i = 0; i < data.length; i++) {
			
			result[i] = data[i] + other.data[i];
			
		}		
		return new Vector(result);
	}

	/**
	 * Adds a given scalar value to all components of the vector and returns the resultant vector. 
	 * @param other the scalar value to add
	 * @return the resultant vector after the addition
	 */
	public Vector add(float other){
		float[] result = new float[data.length];

		for(int i = 0; i < data.length; i++){
			result[i] = data[i] + other;
		}
		return new Vector(result);
	}

	/**
	 * Multiplies all components of this vector by a given scalar value and returns the resultant vector. 
	 * @param other the scalar value to multiply by
	 * @return the resultant vector after the multiplication
	 */
	public Vector multiply(float other){
		float[] result = new float[data.length];

		for(int i = 0; i < data.length; i++){
			result[i] = data[i] * other;
		}

		return new Vector(result);
	}
	
	/**
	 * Negates all components of this vector and returns a vector pointing in the opposite direction with the same magnitude.
	 * @return a vector with equal magnitude but in the opposite direction
	 */
	public Vector negate(){
		float[] result = new float[data.length];
		for(int i = 0; i < data.length; i++){
			result[i] = 0 - data[i];
		}

		return new Vector(result);
	}

	/**
	 * Multiplies all components of this vector by the corresponding components of another given vector and returns the dot product.
	 * This is equal to the lengths of both vectors multiplied together multiplied by the cosine of the angle between them.
	 * @param other the other vector to calculate the dot product of
	 * @return the dot product as a scalar float value equal to ||this|| * ||other|| * cos(angle) 
	 */
 	public float dot(Vector other) throws VectorSizeMismatchException{
		
		float result = 0;

		if(this.data.length != other.data.length) {
			throw new VectorSizeMismatchException(this.data.length, other.data.length, "multiply");
		}
		
		for(int i = 0; i < data.length; i++){
			result += data[i] * other.data[i];
		}
		
		return result;
	}

	/**
	 * Calculates the angle in radians between this and another vector by calculating the dot product, 
	 * dividing by the lengths multiplied together and taking the arccos of the result.
	 * @param other the other vector to calculate the angle between
	 * @return the angle in radians between this and the other vector
	 */
	public double angleBetween(Vector other) throws VectorSizeMismatchException{
		
		float dotProduct = this.dot(other);
		dotProduct /= this.getLength() * other.getLength();
		return Math.acos(dotProduct);
	}

	/**
	 * Performs a cross product multiplication to calculate a vector orthogonal to this and another given vector
	 * (e.g. an x axis vector (1,0,0) and a y axis vector (0,1,0) would produce a z axis vector (0,0,1)).
	 * @param other the other vector to calculate the cross product of
	 * @return a vector orthogonal to this and the other vector
	 * @throws VectorSizeMismatchException
	 */
	public Vector cross(Vector other) throws VectorSizeMismatchException{
		
		if(this.data.length != 3 || other.data.length != 3){
			throw new VectorSizeMismatchException(this.data.length, other.data.length, "cross multiply");
		}

		float [] result = new float[]{
			this.data[1] * other.data[2] - this.data[2] * other.data[1],
			this.data[2] * other.data[0] - this.data[0] * other.data[2],
			this.data[0] * other.data[1] - this.data[1] * other.data[0]
		};

		return new Vector(result);
	}
	
	/**
	 * Calculates the length of this vector by using pythagoras to calculate the 
	 * square root of the squares of all the components summed together.
	 * @return the length of this vector
	 */
	public double getLength() {

		float valuesSquared = 0;
		for(float f: this.data){
			valuesSquared += f*f;
		}

		return (Math.sqrt(valuesSquared));
	}
	
	/**
	 * Returns a string representation of this vector in the form
	 * (a,b,c,d...)
	 * @return a string representing this vector
	 */
	public String toString() {
		String result = "(";
		for(int i = 0; i < data.length; i++) {
			result += data[i];
			if(i != data.length - 1) {
				result += ",";
			}
		}
		result += ")";
		return result;
	}
}

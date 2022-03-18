package matrices;

import matrices.exceptions.VectorSizeMismatchException;

public class Vector {
	public float[] data;
	
	public Vector(float ...data) {
		this.data = new float[data.length];

		for(int i = 0; i < data.length; i++) {
			this.data[i] = data[i];
		}
		
	}
	
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

	public Vector add(float other){
		float[] result = new float[data.length];

		for(int i = 0; i < data.length; i++){
			result[i] = data[i] + other;
		}
		return new Vector(result);
	}

	public Vector multiply(float other){
		float[] result = new float[data.length];

		for(int i = 0; i < data.length; i++){
			result[i] = data[i] * other;
		}

		return new Vector(result);
	}
	

	public Vector negate(){
		float[] result = new float[data.length];
		for(int i = 0; i < data.length; i++){
			result[i] = 0 - data[i];
		}

		return new Vector(result);
	}


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

	public double angleBetween(Vector other) throws VectorSizeMismatchException{
		
		float dotProduct = this.dot(other);
		dotProduct /= this.getLength() * other.getLength();
		return Math.acos(dotProduct);
	}

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
	
	
	public double getLength() {

		float valuesSquared = 0;
		for(float f: this.data){
			valuesSquared += f*f;
		}

		return (Math.sqrt(valuesSquared));
	}
	
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

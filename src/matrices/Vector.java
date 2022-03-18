package matrices;

import java.util.ArrayList;

import matrices.exceptions.VectorSizeMismatchException;

public class Vector {
	public float[] data;
	
/**	public Vector(ArrayList<Float> data) {
		for(float i : data) {
			this.data.add(i);
		}
	}**/
	
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
	
	public Vector multiply(Vector other) throws VectorSizeMismatchException{
		
		if(this.data.length != other.data.length) {
			throw new VectorSizeMismatchException(this.data.length, other.data.length, "multiply");
		}
		
		
	}
	
	public float getLength() {
		
	}
	
	@Override
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

package engine;

import static org.lwjgl.opengl.GL11.glGetError;

import engine.exceptions.TextureLoadException;
import matrices.Matrix;
import matrices.exceptions.MatrixSizeMismatchException;

public class GameObject {

	protected float x, y, z, pitch, yaw, roll, xScale, yScale;
	protected Matrix trans = Matrix.IdentityMatrix4x4();
	protected Boolean visible;
	
	protected GameObject(float x, float y, float z, float pitch, float yaw, float roll, float xScale, float yScale) throws TextureLoadException, MatrixSizeMismatchException {
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll; 
		this.xScale = xScale;
		this.yScale = yScale;
		glGetError();
		
		genTransformMatrix();
	}
	
	public void move(float x, float y, float z) throws MatrixSizeMismatchException {
		this.x += x;
		this.y += y;
		this.z += z;
		this.genTransformMatrix();
	}
	
	public void setX(float x) throws MatrixSizeMismatchException {
		this.x = x;
		this.genTransformMatrix();
	}
	
	public void setY(float y) throws MatrixSizeMismatchException {
		this.y = y;
		this.genTransformMatrix();

	}
	
	public void setZ(float z) throws MatrixSizeMismatchException {
		this.z = z;
		this.genTransformMatrix();

	}
	
	public void rotate(float pitch, float yaw, float roll) {
		this.pitch += pitch;
		this.yaw += yaw;
		this.roll += roll;
	}
	
	public void setPitch(float pitch) throws MatrixSizeMismatchException {
		this.pitch = pitch;
		this.genTransformMatrix();
	}
	
	public void setYaw(float yaw) throws MatrixSizeMismatchException {
		this.yaw = yaw;
		this.genTransformMatrix();
	}
	
	public void setRoll(float roll) throws MatrixSizeMismatchException {
		this.roll = roll;
		this.genTransformMatrix();
	}
	
	public void scale(float x, float y) throws MatrixSizeMismatchException {
		this.xScale *= x;
		this.yScale *= y;
		this.genTransformMatrix();
	}
	
	public void setXScale(float x) throws MatrixSizeMismatchException {
		this.xScale = x;
		this.genTransformMatrix();
	}
	
	public void setYScale(float y) throws MatrixSizeMismatchException {
		this.yScale = y;
		this.genTransformMatrix();
	}
	
	protected final void genTransformMatrix(){
		this.trans = Matrix.IdentityMatrix4x4().translate(x,y,z).rotate(pitch,yaw,roll).scale(xScale,yScale);
    }
	
	public float getX() {
		return x;
		
	}
	
	public float getY() {
		return y;
	}
	
	public float getZ() {
		return z;
	}

	public float getXScale(){
		return xScale;
	}
	public float getYScale(){
		return yScale;
	}

	public Matrix getTransformMatrix(){
		return trans;
	}
}

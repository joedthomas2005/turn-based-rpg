package engine;

import static org.lwjgl.opengl.GL11.glGetError;


import org.joml.*;

import engine.exceptions.DrawElementsException;
import engine.exceptions.TextureBindException;
import engine.exceptions.TextureLoadException;

public abstract class GameObject {

	protected float x, y, z, pitch, yaw, roll, xScale, yScale;
	protected Matrix4f trans = new Matrix4f();
	public abstract void draw(ShaderController controller, Camera camera) throws TextureBindException, DrawElementsException, CloneNotSupportedException;
	protected String texturePath;
	protected int textureID;
	protected Boolean visible;
	
	protected GameObject(float x, float y, float z, float pitch, float yaw, float roll, float xScale, float yScale, String texture, TextureController textureManager) throws TextureLoadException {
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll; 
		this.xScale = xScale;
		this.yScale = yScale;
		this.visible = true;
		glGetError();
		
		this.texturePath = texture;
		this.textureID = textureManager.getTexture(texture);
	
	}
	
	public int getTextureID() {
		return textureID;
	}
	
	public String getTexturePath() {
		return texturePath;
	}
	

	public void move(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;

	}
	
	public void setZ(float z) {
		this.z = z;

	}
	
	
	public void rotate(float pitch, float yaw, float roll) {
		this.pitch += pitch;
		this.yaw += yaw;
		this.roll += roll;
	}
	
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	
	public void setRoll(float roll) {
		this.roll = roll;
	}
	
	public void scale(float x, float y) {
		this.xScale *= x;
		this.yScale *= y;
	}
	
	public void setXScale(float x) {
		this.xScale = x;
	}
	
	public void setYScale(float y) {
		this.yScale = y;
	}
	
	protected void genTransformMatrix() {
		this.trans.identity().translate(x,y,z).rotateXYZ(pitch,yaw,roll).scale(xScale,yScale,1);
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
}

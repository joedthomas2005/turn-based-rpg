package engine;
import java.util.ArrayList;

import org.joml.*;
public abstract class GameObject {

	protected float x, y, z, pitch, yaw, roll, xScale, yScale;
	protected Matrix4f trans;
	protected GameObject(float x, float y, float z, float pitch, float yaw, float roll, float xScale, float yScale) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll;
		this.xScale = xScale;
		this.yScale = yScale;
	}
	
	void move(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}
	
	void setX(float x) {
		this.x = x;
	}
	
	void setY(float y) {
		this.y = y;
	}
	
	void setZ(float z) {
		this.z = z;
	}
	
	
	void rotate(float pitch, float yaw, float roll) {
		this.pitch += pitch;
		this.yaw += yaw;
		this.roll += roll;
	}
	
	void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	void setYaw(float yaw) {
		this.yaw = yaw;
	}
	
	void setRoll(float roll) {
		this.roll = roll;
	}
	
	void scale(float x, float y) {
		this.xScale *= x;
		this.yScale *= y;
	}
	
	void setXScale(float x) {
		this.xScale = x;
	}
	
	void setYScale(float y) {
		this.yScale = y;
	}
	
	void genTransformMatrix() {
		this.trans = new Matrix4f().translate(new Vector3f(x,y,z)).rotateXYZ(pitch, yaw, roll).scale(xScale, yScale, 1);
	}
}

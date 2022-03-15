package engine;
import org.joml.*;
import org.joml.Math;
public class Camera {
	
	private Matrix4f view = new Matrix4f();
	private float pitch,yaw;
	private Vector3f position;
	private Boolean moved = true;
	
	public Camera(float x, float y, float z, float pitch, float yaw){
		this.position = new Vector3f(x,y,z);
		this.pitch = pitch;
		this.yaw = yaw;
		genViewMatrix();
	}
	
	public void update() {
		this.moved = false;
	}
	
	public void genViewMatrix() {
		
		view.identity();
		view.rotate((float)Math.toRadians(pitch), new Vector3f(1,0,0))
			.rotate((float)Math.toRadians(yaw), new Vector3f(0, 1, 0));
		
		view.translate(-position.x, -position.y, position.z);

	}
	
	public void move(float x, float y, float z) {
		
		this.position.x += x;
		this.position.y += y;
		this.position.z += z;
		this.moved = true;
		genViewMatrix();
		
	}
	
	public void rotate(float pitch, float yaw) {
		this.pitch += pitch;
		this.yaw += yaw;
		this.moved = true;
		genViewMatrix();
	}
	
	public void setPos(float x, float y, float z) {
		this.position = new Vector3f(x,y,z);
		this.moved = true;
		genViewMatrix();
	}
	
	public void setPos(Vector3f position) {
		this.position = position;
		this.moved = true;
		genViewMatrix();
	}
	
	public void setRot(float pitch, float yaw) {
		this.pitch = pitch;
		this.yaw = yaw;
		this.moved = true;
		genViewMatrix();
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public Matrix4f getView() {
		return view;
	}

	public Vector3f getPos(){
		return position;
	}

	public float getX(){
		return position.x;
	}

	public float getY(){
		return position.y;
	}

	public float getZ(){
		return position.z;
	}
	
	public Boolean getMoved() {
		return moved;
	}
}

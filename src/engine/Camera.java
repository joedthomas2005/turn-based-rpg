package engine;
import org.joml.*;
import org.joml.Math;
public class Camera {
	
	private Matrix4f view;
	private float pitch,yaw;
	private Vector3f camForward;
	private Vector3f position;
	private final static Vector3f upGlobal = new Vector3f(0.0f,1.0f,0.0f);
	
	public Camera(float x, float y, float z, float pitch, float yaw){
		this.position = new Vector3f(x,y,z);
		this.pitch = pitch;
		this.yaw = yaw;
		genVectors();
	}
	
	public void genVectors() {
		/**float xFacing = Math.cos(Math.toRadians(this.yaw)) * Math.cos(Math.toRadians(this.pitch));
		float yFacing = Math.sin(Math.toRadians(this.pitch));
		float zFacing = Math.sin(Math.toRadians(this.yaw)) * Math.cos(Math.toRadians(this.pitch));
		//this.camForward = new Vector3f(xFacing, yFacing, zFacing).normalize();
		this.camForward = new Vector3f(0.0f,0.0f,0.0f);
		System.out.println("camForward: " + camForward.toString());
		System.out.println("Position: " + position.toString());
		System.out.println("UpGlobal: " + upGlobal.toString());
		System.out.println("Position + camForward: " + position.add(camForward).toString());
		this.view = new Matrix4f();
		this.view = this.view.lookAt(position, camForward, upGlobal);
		**/
		this.view = new Matrix4f().translate(this.position);
	}
	
	public void move(float x, float y, float z, Boolean useGlobal) {
		if(useGlobal) {
			this.position.x += x;
			this.position.y += y;
			this.position.z += z;
		}
		else {
			this.position = this.position.add(camForward.mul(z))
					.add(camForward.cross(upGlobal).normalize().mul(x))
					.add(upGlobal.mul(y));
		}
		genVectors();
	}
	
	public void rotate(float pitch, float yaw) {
		this.pitch += pitch;
		this.yaw += yaw;
		genVectors();
	}
	
	public void setPos(float x, float y, float z) {
		this.position = new Vector3f(x,y,z);
		genVectors();
	}
	
	public void setPos(Vector3f position) {
		this.position = position;
		genVectors();
	}
	
	public void setRot(float pitch, float yaw) {
		this.pitch = pitch;
		this.yaw = yaw;
		genVectors();
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
}

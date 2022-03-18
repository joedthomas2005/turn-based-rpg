package engine;
import org.joml.*;
import org.joml.Math;
public class Camera {
	
	private final Matrix4f view = new Matrix4f();
	private Vector3f position;
	private Boolean moved = true;
	
	public Camera(float x, float y, float z){
		this.position = new Vector3f(-x,-y,z);
		genViewMatrix();
	}
	
	public Vector3f screenToWorld(Vector3f screenCoords) {
		Matrix4f invertedView = new Matrix4f(view);
		invertedView.invert().transformPosition(screenCoords);
		return screenCoords;
	}
	
	public Vector3f screenToWorld(float x, float y) {
		return screenToWorld(new Vector3f(x, y, 0));
	}
	
	public Vector3f worldToScreen(Vector3f worldCoords) {
		Matrix4f view = new Matrix4f(this.view);
		view.transformPosition(worldCoords);
		return worldCoords;
	}
	
	public Vector3f worldToScreen(float x, float y) {
		return worldToScreen(new Vector3f(x,y,0));
	}
	
	public void update() {
		this.moved = false;
	}
	
	public final void genViewMatrix() {
		
		view.identity().translate(position);

	}
	
	public void move(double x, double y, float z) {
		
		this.position.x += -x;
		this.position.y += -y;
		this.position.z += z;
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

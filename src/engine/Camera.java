package engine;

import matrices.Matrix;
import matrices.Vector;
import matrices.exceptions.MatrixSizeMismatchException;

public class Camera {
	
	private Matrix view = Matrix.IdentityMatrix4x4();
	private Vector position;
	private Boolean moved = true;
	
	public Camera(float x, float y, float z) throws MatrixSizeMismatchException{
		this.position = new Vector(-x,-y,z);
		genViewMatrix();
	}
	
	public Vector screenToWorld(Vector screenCoords) throws MatrixSizeMismatchException {

		Matrix invertedView = Matrix.IdentityMatrix4x4().translate(position.negate().data[0], position.negate().data[1], position.negate().data[2]);
        worldCoords = invertedView.transform(screenCoords);
		return worldCoords;
	}
	
	public Vector screenToWorld(float x, float y) throws MatrixSizeMismatchException {
		return screenToWorld(new Vector(x, y, 0));
	}
	
	public Vector worldToScreen(Vector worldCoords) throws MatrixSizeMismatchException {
        Vector screenCoords = view.transform(worldCoords);
		return screenCoords;
	}
	
	public Vector worldToScreen(float x, float y) throws MatrixSizeMismatchException {
		return worldToScreen(new Vector(x,y,0));
	}
	
	public void update() {
		this.moved = false;
	}
	
	public final void genViewMatrix() throws MatrixSizeMismatchException {
		
		this.view = Matrix.IdentityMatrix4x4().translate(position.data[0], position.data[1], position.data[2]);
        System.out.println("view matrix: " + view.toString());

	}
	
	public void move(double x, double y, float z) throws MatrixSizeMismatchException {
		
		this.position.data[0] += -x;
		this.position.data[1] += -y;
		this.position.data[2] += z;
		this.moved = true;
		genViewMatrix();
		
	}
	
	public void setPos(float x, float y, float z) throws MatrixSizeMismatchException {
		this.position = new Vector(x,y,z);
		this.moved = true;
		genViewMatrix();
	}
	
	public void setPos(Vector position) throws MatrixSizeMismatchException {
		this.position = position;
		this.moved = true;
		genViewMatrix();
	}

	public Matrix getView() {
		return view;
	}

	public Vector getPos(){
		return position;
	}

	public float getX(){
		return position.data[0];
	}

	public float getY(){
		return position.data[1];
	}

	public float getZ(){
		return position.data[2];
	}
	
	public Boolean getMoved() {
		return moved;
	}
}

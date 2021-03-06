package engine;

import matrices.Matrix;
import matrices.Vector;
import matrices.exceptions.MatrixSizeMismatchException;

public class Camera extends GameObject{
	
	private Boolean moved = true;
	
	public Camera(float x, float y, float z){
		super(-x,-y,z,0,0,0,1,1);
		genTransformMatrix();
	}
	
	@Override
	public void move(float x, float y, float z){
		super.move(-x,-y,-z);
	}
	
	public Vector screenToWorld(Vector screenCoords) throws MatrixSizeMismatchException {

		Matrix invertedView = Matrix.IdentityMatrix4x4().translate(-x, -y, -z);
        return invertedView.transform(screenCoords);
	}
	
	public Vector screenToWorld(float x, float y) throws MatrixSizeMismatchException {
		return screenToWorld(new Vector(x, y, 0));
	}
	
	public Vector worldToScreen(Vector worldCoords) throws MatrixSizeMismatchException {
		return trans.transform(worldCoords);
	}
	
	public Vector worldToScreen(float x, float y) throws MatrixSizeMismatchException {
		return worldToScreen(new Vector(x,y,0));
	}
	
	public void update() {
		this.moved = false;
	}

	public Boolean getMoved() {
		return moved;
	}
}

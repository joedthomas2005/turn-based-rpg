package engine;

import matrices.Matrix;
import matrices.Vector;
import matrices.exceptions.MatrixSizeMismatchException;

public class Camera extends GameObject{
	
	private Boolean moved = true;
	private Window window;
	
	public Camera(float x, float y, float z, Window window){
		super(-x,-y,z,0,0,0,1,1);
		this.window = window;
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
	
	public boolean isRectVisible(float left, float right, float bottom, float top) {
		Vector leftTop = worldToScreen(left, top);
		Vector rightBottom = worldToScreen(right, bottom);

		return (leftTop.x() < window.getWidth()
				&& rightBottom.x() > 0
				&& rightBottom.y() < window.getHeight()
				&& leftTop.y() > 0);
	}
	
	public void update() {
		this.moved = false;
	}

	public Boolean getMoved() {
		return moved;
	}
}

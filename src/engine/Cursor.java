package engine;

import matrices.Vector;

public class Cursor{

	private final InputController inputController;
	private final Camera camera;
	public final DrawableGameObject cursor;

	public Cursor(DrawableCreator squareCreator, InputController input, Camera camera){
		this.cursor = squareCreator.create(0,0,0,50,50,"animated_cursor.png");
		this.inputController = input;
		this.camera = camera;
	}
	
	public void update(){
		
		Vector worldCoord = camera.screenToWorld(new Vector((float)inputController.getMouseX(), (float)inputController.getMouseY(), 0));

		cursor.setX(worldCoord.data[0]);
		cursor.setY(worldCoord.data[1]);
		
	}
}

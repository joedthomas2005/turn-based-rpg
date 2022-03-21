package engine;

import matrices.Vector;

public class Cursor{

	private final InputController inputController;
	private final Camera camera;
	public final DrawableGameObject cursor;

	public Cursor(SquareFactory squareFactory, InputController input, Camera camera){
		this.cursor = squareFactory.create(0,0,0,50,50,"cursor.png");
		this.inputController = input;
		this.camera = camera;
	}
	
	public void update(){
		
		Vector worldCoord = camera.screenToWorld(new Vector((float)inputController.getMouseX(), (float)inputController.getMouseY(), 0));
		
		System.out.println("Moving cursor to " + worldCoord.data[0] + "," + worldCoord.data[1]);
		cursor.setX(worldCoord.data[0]);
		cursor.setY(worldCoord.data[1]);
		
	}
}

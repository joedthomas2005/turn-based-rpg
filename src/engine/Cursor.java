package engine;

import matrices.Vector;

public class Cursor{

	private final InputController inputController;
	private final Camera camera;
	public final Actor cursor;
	
	public Cursor(ActorManager actorManager, InputController input, Camera camera){
		this.cursor = actorManager.create(0,0,0,100,110,"animated_cursor.png",
		 new Animator(new SpriteSheetParser(4, 2), new int[]{0,0}, new int[]{1,3}));
		this.inputController = input;
		this.camera = camera;
	}
	
	public void draw(){
		
		if(cursor.getAnimationState() == 1 && cursor.animationFinished()){
			cursor.setAnimationState(0);
		}

		Vector worldCoord = camera.screenToWorld(new Vector((float)inputController.getMouseX(), (float)inputController.getMouseY(), 0));

		cursor.setX(worldCoord.data[0]);
		cursor.setY(worldCoord.data[1]);
	
		cursor.draw();
	}

	public void click(){
		cursor.setAnimationState(1);
	}
}

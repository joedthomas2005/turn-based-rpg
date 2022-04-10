package engine;

import matrices.Vector;

public class Cursor{

	private final InputController inputController;
	private final Camera camera;
	private Boolean enabled;
	public final Actor cursor;
	
	public Cursor(ActorManager actorManager, InputController input, Camera camera, String texture){
		this.cursor = actorManager.create(0,0,0,100,110,texture,
				new Animator(new SpriteSheetParser(4, 2), 10, new int[]{0,0}, new int[]{1,3})); //0,0 must be the default cursor state and then there must be a 3 frame click animation
				
		this.inputController = input;
		this.camera = camera;
		this.enabled = false;
	}
	
	public void draw(){
		if(enabled) {
			
			if(cursor.getAnimationState() == 1 && cursor.animationFinished()){
				cursor.setAnimationState(0);
			}

			Vector worldCoord = camera.screenToWorld(new Vector((float)inputController.getMouseX(), (float)inputController.getMouseY(), 0));

			cursor.setX(worldCoord.data[0]);
			cursor.setY(worldCoord.data[1]);
	
			cursor.draw();
		}
	}
	
	public void click(){
		if(enabled) {
			cursor.setAnimationState(1);
		}
		else {
			System.err.println("WARNING: Cursor is disabled. Click() will have no effect.");
		}
	}
	
	public void show() {
		this.enabled = true;
	}
	
	public void hide() {
		this.enabled = false;
	}

	public Actor getActor(){
		return this.cursor;
	}
}

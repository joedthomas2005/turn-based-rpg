package engine;
import org.joml.Vector3f;

import engine.exceptions.TextureException;

public class Cursor extends Square{
	private InputController inputController;
	private Camera camera;
	
	public Cursor(TextureController textureController, InputController inputController, Camera camera) throws TextureException {
		super(0,0,0,0,0,50,50,"cursor.png",textureController);
		this.inputController = inputController;
		this.camera = camera;
	}
	
	public void update() {
		
		Vector3f worldCoord = camera.screenToWorld(new Vector3f((float)inputController.getMouseX(), (float)inputController.getMouseY(), 0));
		
		setX(worldCoord.x);
		setY(worldCoord.y);
		
	}
}

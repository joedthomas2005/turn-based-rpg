package engine;
import org.joml.Vector3f;

import engine.exceptions.TextureException;
import matrices.Vector;
import matrices.exceptions.MatrixSizeMismatchException;

public class Cursor extends Square{
	private final InputController inputController;
	private final Camera camera;
	
	public Cursor(TextureController textureController, InputController inputController, Camera camera) throws TextureException, MatrixSizeMismatchException {
		super(0,0,0,0,0,50,50,"cursor.png",textureController);
		this.inputController = inputController;
		this.camera = camera;
	}
	
	public void update() throws MatrixSizeMismatchException {
		
		Vector worldCoord = camera.screenToWorld(new Vector((float)inputController.getMouseX(), (float)inputController.getMouseY(), 0));
		
        System.out.println("cursor drawn at: " + worldCoord.toString());
		setX(worldCoord.data[0]);
		setY(worldCoord.data[1]);
		
	}
}

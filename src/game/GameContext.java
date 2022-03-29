package game;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_HIDDEN;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;

import java.io.IOException;

import engine.*;
import engine.exceptions.ShaderException;

public class GameContext {
	
	private Window window;
	private ShaderController shaderController;
	private Camera camera;
	private TileMap tileMap;
	private InputController inputController;
	private ActorManager actorManager;
	private Cursor cursor;
	
	private double currentFrame;
	private double time;
	private double lastFrameTime;
	private double deltaTime;
	
	public void create(int width, Boolean fullscreen) throws ShaderException, IOException {
		//Enable GLFW
		glfwInit();

		//Create window and context
		window = new Window(width, "Turn Based RPG", 1, fullscreen);
		window.setColor(1, 1, 1, 1);
		glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);	
		
		//Setup required components and controllers
		BufferController bufferController = new BufferController();
		camera = new Camera(0,0,0, window);

		inputController = new InputController(window);
		shaderController = new ShaderController("vertex.hlsl", "frag.hlsl", window);
		TextureController textureController = new TextureController();
	
		DrawableCreator squareCreator = new SquareCreator(textureController, shaderController);
		this.actorManager = new ActorManager(squareCreator, camera);
		//Initialise any shapes that will be used and finally bind the VAO
				
		squareCreator.initialise(bufferController);
		bufferController.bind();
		
		cursor = new Cursor(actorManager, inputController, camera);

		tileMap = new TileMap("test.tlm", actorManager);
		
	}
	
	
}

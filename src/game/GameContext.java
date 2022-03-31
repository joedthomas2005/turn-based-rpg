package game;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_HIDDEN;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;

import java.io.IOException;
import java.util.ArrayList;

import engine.*;
import engine.exceptions.ShaderException;

public class GameContext {
	
	private Window window;
	private ShaderController shaderController;
	private Camera camera;
	private ArrayList<TileMap> tileMaps = new ArrayList<TileMap>();
	private ArrayList<Actor> actors = new ArrayList<Actors>();
	private InputController inputController;
	private ActorManager actorManager;
	private Cursor cursor;
	
	private double currentFrame;
	private double time;
	private double lastFrameTime;
	private double deltaTime;
	
	public void create(int width, Boolean fullscreen, String windowTitle, String cursor) throws ShaderException, IOException {
		//Enable GLFW
		glfwInit();

		//Create window and context
		window = new Window(width, windowTitle, 1, fullscreen);
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

		this.cursor = new Cursor(actorManager, inputController, camera, cursor);
	}
	

	public void enableCursor() {
		this.cursor.show();
	}
	
	public void disableCursor() {
		this.cursor.hide();
	}
	
	public void loadTilemap(String path) {
		this.tileMaps.add(new TileMap(path, this.actorManager));
	}
	
	public void addActor(Actor object) {
		this.actors.add(object);
	}
	
}

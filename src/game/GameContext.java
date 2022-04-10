package game;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

import java.io.IOException;
import java.util.ArrayList;

import engine.*;
import engine.exceptions.ShaderException;

public class GameContext {
	
	private Window window;
	private ShaderController shaderController;
	private Camera camera;
	private CameraController cameraController;
	private ArrayList<TileMap> tileMaps = new ArrayList<TileMap>();
	private InputController inputController;
	private ActorManager actorManager;
	private Cursor cursor;
	
	private int currentFrame = 0;
	private double time = 0;
	private double lastFrameTime = 0;
	private double deltaTime = 0;
	
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
		
		cameraController = new CameraController(camera, inputController, 10f);
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
		actorManager.add(object);
	}
	
	public Actor createRect(float x, float y, float rotation, float xScale, float yScale, String texture){
		return actorManager.create(x, y, rotation, xScale, yScale, texture);
	}

	public void startFrame() {
		currentFrame++;
		time = glfwGetTime();
		deltaTime = (time - lastFrameTime) * 100;
		
		glClear(GL_COLOR_BUFFER_BIT);
		this.camera.bindView(this.shaderController);
		
		if(inputController.isKeyDown(GLFW_KEY_ESCAPE)) {
			glfwSetWindowShouldClose(window.getWindow(), true);
		}
		
		if(inputController.leftMouseClicked()) {
			this.cursor.click();
		}
		
		for(TileMap tilemap: tileMaps) {
			tilemap.draw();
		}
		
	}
	
	public void endFrame() {

		actorManager.drawAll();
		actorManager.animateAll(currentFrame);
		
		this.cursor.draw();
		this.cursor.cursor.animate(currentFrame);
		
		cameraController.update(deltaTime);
		inputController.reset();
		window.update();
		lastFrameTime = time;
		
	}
	
	public Window getWindow() {
		return this.window;
	}
	
	public InputController getInputController() {
		return this.inputController;
	}
	
	public ShaderController getShaderController() {
		return this.shaderController;
	}
	
	public ActorManager getActorManager() {
		return this.actorManager;
	}
	
	public ArrayList<TileMap> getTileMapArray(){
		return this.tileMaps;
	}
	
	public TileMap getTileMap(int tileMap) {
		return this.tileMaps.get(tileMap);
	}
	
	public Camera getCamera() {
		return this.camera;
	}
	
	public CameraController getCameraController() {
		return this.cameraController;
	}
	
	public Cursor getCursor() {
		return this.cursor;
	}
	
	public Actor getCursorRaw() {
		return this.cursor.cursor;
	}
	
}

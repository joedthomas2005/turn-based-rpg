import engine.*;
import engine.exceptions.ShaderException;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30.*;
import java.io.IOException;

import matrices.Vector;
public final class Game implements Runnable{

	private Window window;
	private Camera camera;
	private InputController inputController;
	private CameraController cameraController;
	private ActorManager actorManager;
	private TileMap tileMap;
	private Cursor cursor;
	private double deltaTime;
	private double time;
	private double lastTime;
	private int curFrame;

    public Game() throws ShaderException, IOException{
		initialise();
	};

	private void initialise() throws ShaderException, IOException{
		//Enable GLFW
		glfwInit();

		//Create window and context
		window = new Window(1920, "Turn Based RPG", 1, true);
		window.setColor(1, 1, 1, 1);
		glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);	
		
		//Setup required components and controllers
		BufferController bufferController = new BufferController();
		camera = new Camera(0,0,0, window);

		inputController = new InputController(window);
		ShaderController shaderController = new ShaderController("vertex.hlsl", "frag.hlsl", window);
		cameraController = new CameraController(camera, shaderController, inputController, 10f);
		TextureController textureController = new TextureController("cursor.png", "placeholder.png", "2frame.png", "animated_cursor.png", "4frame.png");
	
		DrawableCreator squareCreator = new SquareCreator(textureController, shaderController);
		this.actorManager = new ActorManager(squareCreator, camera);
		//Initialise any shapes that will be used and finally bind the VAO
				
		squareCreator.initialise(bufferController);
		bufferController.bind();
		
		cursor = new Cursor(actorManager, inputController, camera);

		tileMap = new TileMap("test.tlm", actorManager);
	}

	@Override
	public void run(){
		time = glfwGetTime();
		lastTime = glfwGetTime();
		deltaTime = 0;
		
		curFrame = 0;
		while(!window.shouldClose()){
			update();
		}

		window.destroy();		
	}


	public final void update() {
		curFrame++;
		time = glfwGetTime();
		deltaTime = (time - lastTime) * 100;
	
		glClear(GL_COLOR_BUFFER_BIT);
		//Draw loop
		tileMap.draw();
		cursor.draw();

		if(inputController.leftMouseClicked()){
			cursor.click();
		}

		if(inputController.isKeyDown(GLFW_KEY_ESCAPE)){
			glfwSetWindowShouldClose(window.getWindow(), true);
		}

		if(curFrame % 10 == 0){
			cursor.cursor.animate();
		}
	
		cameraController.update((float)deltaTime);
		inputController.reset();
		window.update();
		lastTime = time;
	}
}

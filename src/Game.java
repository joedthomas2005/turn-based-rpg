import engine.*;
import engine.exceptions.ShaderException;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30.*;
import java.io.IOException;
import java.util.ArrayList;

import matrices.Vector;
import matrices.exceptions.MatrixSizeMismatchException;

public final class Game implements Runnable{

	private Window window;
	private BufferController bufferController;
	private Camera camera;
	private InputController inputController;
	private ShaderController shaderController;
	private CameraController cameraController;
	private TextureController textureController;
	private DrawableCreator squareCreator;
	private ArrayList<DrawableGameObject> drawable;
	private Cursor cursor;
	private double deltaTime;
	private double time;
	private double lastTime;

    public Game() throws ShaderException, IOException{
		initialise();
	};

	private void initialise() throws ShaderException, IOException{
		//Enable GLFW
		glfwInit();

		//Create window and context
		window = new Window(1366, "Turn Based RPG", 1, false);
		window.setColor(1, 1, 1, 1);
		glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);	
		
		//Setup required components and controllers
		bufferController = new BufferController();
		camera = new Camera(0,0,0);
		inputController = new InputController(window, 1f);
		shaderController = new ShaderController("vertex.hlsl", "frag.hlsl", window);
		cameraController = new CameraController(camera, shaderController, inputController, 10f);
		textureController = new TextureController("cursor.png", "placeholder.png", "2frame.png", "animated_cursor.png");
				
		squareCreator = new SquareCreator(textureController);
		//Initialise any shapes that will be used and finally bind the VAO
				
		squareCreator.initialise(bufferController);
		bufferController.bind();
		
		//Create object array. This will be handled by a controller later 
		drawable = new ArrayList<DrawableGameObject>();
		DrawableGameObject square = squareCreator.create(0,0,0,100,100,"2frame.png",2,1);
		drawable.add(square);
		cursor = new Cursor(squareCreator, inputController, camera);
	}

	@Override
	public void run(){
		time = glfwGetTime();
		lastTime = glfwGetTime();
		deltaTime = 0;
		
		while(!window.shouldClose()){
			update();
		}

		window.destroy();		
	}


	public final void update() {
		
		time = glfwGetTime();
		deltaTime = (time - lastTime) * 100;
	
		glClear(GL_COLOR_BUFFER_BIT);
		
		//Draw loop
		cursor.update();
			
		for(DrawableGameObject object : drawable){
			object.draw(shaderController, camera);
			object.setFrame(object.getFrame() + 1);
		}

		cursor.cursor.draw(shaderController, camera);
			
		if(inputController.leftMouseClicked()){
				
			Vector squarePosition = camera.screenToWorld((float)inputController.getMouseX(), (float)inputController.getMouseY());
			    
			drawable.add(squareCreator.create(squarePosition.data[0],squarePosition.data[1],
				 0,100,100,"2frame.png",2,1));
		}
			
		if(inputController.rightMouseClicked()){
				
			Vector worldCoord = camera.screenToWorld((float)inputController.getMouseX(), (float)inputController.getMouseY());

			GameObject toRemove = null;
			for(GameObject object: drawable){
					
				if(worldCoord.data[0] < object.getX() + object.getXScale() && worldCoord.data[0] > object.getX() - object.getXScale() &&
						worldCoord.data[1] < object.getY() + object.getYScale() && worldCoord.data[1] > object.getY() - object.getYScale()){
					toRemove = object;
				}	
			}
				
			if(toRemove != null){
				drawable.remove(toRemove);
			}
		}
		
		if(inputController.isKeyDown(GLFW_KEY_LEFT_SHIFT)){
			cursor.cursor.setFrame(1);
		}
		else if(inputController.isKeyDown(GLFW_KEY_SPACE)){
			cursor.cursor.setFrame(2);
		}
		else{
			cursor.cursor.setFrame(0);
		}
		if(inputController.isKeyDown(GLFW_KEY_ESCAPE)){
			glfwSetWindowShouldClose(window.getWindow(), true);
		}

		cameraController.update((float)deltaTime);
		inputController.reset();
		window.update();
		lastTime = time;
	}
}

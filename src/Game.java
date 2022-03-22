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
		camera = new Camera(0,0,0);

		inputController = new InputController(window);
		ShaderController shaderController = new ShaderController("vertex.hlsl", "frag.hlsl", window);
		cameraController = new CameraController(camera, shaderController, inputController, 10f);
		TextureController textureController = new TextureController("cursor.png", "placeholder.png", "2frame.png", "animated_cursor.png", "4frame.png");
		DrawableCreator squareCreator = new SquareCreator(textureController);

		this.actorManager = new ActorManager(squareCreator, shaderController);
		//Initialise any shapes that will be used and finally bind the VAO
				
		squareCreator.initialise(bufferController);
		bufferController.bind();
		
		//Create object array. This will be handled by a controller later 
		actorManager.add(actorManager.create(0,0,0,100,100,"placeholder.png"));
		cursor = new Cursor(actorManager, inputController, camera);
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
		
		actorManager.drawAll();
		cursor.draw();

		if(inputController.leftMouseClicked()){
			cursor.click();
			Vector squarePosition = camera.screenToWorld((float)inputController.getMouseX(), (float)inputController.getMouseY());
			Animator squareAnimator = new Animator(new SpriteSheetParser(4, 2), new int[]{0,1}, new int[]{2,3});
			actorManager.add(actorManager.create(squarePosition.data[0], squarePosition.data[1], 0, 100, 100, "4frame.png", squareAnimator));
		}
			
		if(inputController.rightMouseClicked()){
				
			Vector worldCoord = camera.screenToWorld((float)inputController.getMouseX(), (float)inputController.getMouseY());

			Actor toRemove = null;
			for(Actor actor: actorManager.getActors()){
					
				if(worldCoord.data[0] < actor.getX() + actor.getXScale() && worldCoord.data[0] > actor.getX() - actor.getXScale() &&
						worldCoord.data[1] < actor.getY() + actor.getYScale() && worldCoord.data[1] > actor.getY() - actor.getYScale() && actor != cursor.cursor){
					toRemove = actor;
				}	
			}
				
			if(toRemove != null){
				actorManager.delete(toRemove);
			}
		}


		if(inputController.isKeyDown(GLFW_KEY_ESCAPE)){
			glfwSetWindowShouldClose(window.getWindow(), true);
		}

		if(curFrame % 100 == 0){
			actorManager.animateAll();	
		}

		if(curFrame % 30 == 0){
			cursor.cursor.animate();
		}

		if(inputController.isKeyFirstPressed(GLFW_KEY_LEFT_CONTROL)){
			for(Actor actor : actorManager.getActors()){
				actor.nextAnimationState();
			}
		}


	
		cameraController.update((float)deltaTime);
		inputController.reset();
		window.update();
		lastTime = time;
	}
}

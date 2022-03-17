import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.stb.STBImage.*;
import java.io.IOException;
import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import engine.BufferController;
import engine.Camera;
import engine.CameraController;
import engine.Cursor;
import engine.GameObject;
import engine.InputController;
import engine.ShaderController;
import engine.Square;
import engine.TextureController;
import engine.Window;
import engine.exceptions.DrawElementsException;
import engine.exceptions.ShaderException;
import engine.exceptions.TextureException;


public class Main {
	
	public static void main(String[] args) throws ShaderException, TextureException, IOException, DrawElementsException, CloneNotSupportedException {
		
		//Enable GLFW
		glfwInit();

		//Create window and context
		Window window = new Window(1920, "Turn Based RPG", 1, true);
		window.setColor(1, 1, 1, 1);
		glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);	

		//Setup required components and controllers
		BufferController bufferManager = new BufferController();
		Camera camera = new Camera(0,0,0,0,0);
		InputController inputController = new InputController(window, 1f);
		ShaderController shaderController = new ShaderController("shader.vert", "shader.frag", window);
		CameraController cameraController = new CameraController(camera, shaderController, inputController, 10f);
		TextureController textureController = new TextureController("cursor.png", "placeholder.png");

		//Initialise any shapes that will be used and finally bind the VAO
		Square.initialise(bufferManager);
		bufferManager.bind();

		//Create object array. This will be handled by a controller later 
		ArrayList<GameObject> objects = new ArrayList<GameObject>();		
		Cursor cursor = new Cursor(textureController, inputController, camera);
		
		double time = glfwGetTime();
		double lastTime = glfwGetTime();
		double deltaTime = 0;
		
		
		while(!window.shouldClose()) {
			
			time = glfwGetTime();
			deltaTime = (time - lastTime) * 100;
			
			glClear(GL_COLOR_BUFFER_BIT);

			//Draw loop
			cursor.update();
			
			for(GameObject object : objects){
				object.draw(shaderController, camera, objects);
			}
			cursor.draw(shaderController, camera, objects);
			
			if(inputController.leftMouseClicked()){
				
				Vector3f squarePosition = camera.screenToWorld((float)inputController.getMouseX(), (float)inputController.getMouseY());
				
				objects.add(new Square(squarePosition.x,squarePosition.y,
					 0,0,0,100,100,"placeholder.png", textureController));
			}
			
			if(inputController.rightMouseClicked()){
				
				Vector3f worldCoord = camera.screenToWorld((float)inputController.getMouseX(), (float)inputController.getMouseY());

				GameObject toRemove = null;
				for(GameObject object: objects){
					
					if(worldCoord.x < object.getX() + object.getXScale() && worldCoord.x > object.getX() - object.getXScale() &&
							worldCoord.y < object.getY() + object.getYScale() && worldCoord.y > object.getY() - object.getYScale()){
						toRemove = object;
					}	
				}
				
				if(toRemove != null){
					objects.remove(toRemove);
				}
			}
			
			update(window, inputController, cameraController, deltaTime);
			lastTime = time;
		}
		
		window.destroy();
	}
	
	private static void update(Window window, InputController inputController, CameraController cameraController, double deltaTime) {
		cameraController.update((float)deltaTime);
		inputController.reset();
		window.update();
	}

}

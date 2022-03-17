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
	
	private static int frame = 0;
	public static void main(String[] args) throws ShaderException, TextureException, IOException, DrawElementsException, CloneNotSupportedException {
		
		//Enable GLFW
		glfwInit();

		//Create window and context
		Window window = new Window(1280,720, "Turn Based RPG", 1, false);
		window.setColor(0, 0, 0, 1);
		glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);	

		//Setup required components and controllers
		stbi_set_flip_vertically_on_load(true);
		BufferController bufferManager = new BufferController();
		Camera camera = new Camera(0,0,0,0,0);
		InputController inputController = new InputController(window, 0.1f);
		ShaderController shaderController = new ShaderController("shader.vert", "shader.frag", window);
		CameraController cameraController = new CameraController(camera, shaderController, inputController, 5);
		TextureController textureController = new TextureController("awesomeface.png", "cursor.png");
		
		glDepthMask(false);
		glDisable(GL_DEPTH_TEST);
		//Initialise any shapes that will be used and finally bind the VAO
		Square.initialise(bufferManager);
		bufferManager.bind();
		
		//Create object array. This will be handled by a controller later 
		ArrayList<GameObject> objects = new ArrayList<GameObject>();		
		Square cursor = new Square(0,0,0,0,0,0,50,50,"cursor.png", textureController);
		while(!window.shouldClose()) {
			//Clear window for drawing

			glClear(GL_COLOR_BUFFER_BIT);

			//Draw loop
			for(GameObject object : objects){
				object.draw(shaderController, camera, objects);
			}
			cursor.draw(shaderController, camera, objects);
			
			if(inputController.isKeyFirstPressed(GLFW_KEY_SPACE)){
				Vector3f squarePosition = new Vector3f((float)inputController.getMouseX(), (float)inputController.getMouseY(), 0);
				Matrix4f cameraTransform = new Matrix4f();
				camera.getView().get(cameraTransform);
				cameraTransform.invert().transformPosition(squarePosition);
				objects.add(new Square(squarePosition.x,squarePosition.y,squarePosition.z,
					 0,0,0,100,100,"awesomeface.png", textureController));
			}
			
			if(inputController.isKeyDown(GLFW_KEY_BACKSPACE)){
				Vector3f worldCoord = new Vector3f((float)inputController.getMouseX(), (float)inputController.getMouseY(), 0);
				Matrix4f cameraTransform = new Matrix4f();
				camera.getView().get(cameraTransform);
				cameraTransform.invert().transformPosition(worldCoord);

				GameObject toRemove = null;
				for(GameObject object: objects){
					if(worldCoord.x < object.getX() + object.getXScale() && worldCoord.x > object.getX() - object.getXScale()){
						toRemove = object;
					}	
				}
				if(toRemove != null){
					objects.remove(toRemove);
				}

			}
			
			Vector3f worldCoord = new Vector3f((float)inputController.getMouseX(), (float)inputController.getMouseY(), 0);
			Matrix4f cameraTransform = new Matrix4f();
			camera.getView().get(cameraTransform);
			cameraTransform.invert().transformPosition(worldCoord);
			cursor.setX(worldCoord.x);
			cursor.setY(worldCoord.y);
			//Perform necessary updates for next frame
			update(window, inputController, cameraController);
		}
		
		window.destroy();
	}
	
	private static void update(Window window, InputController inputController, CameraController cameraController) {
		cameraController.update();
		inputController.reset();
		window.update();
		if(frame % 600 == 0) {
			System.gc();
		}
		frame += 1;
	}

}

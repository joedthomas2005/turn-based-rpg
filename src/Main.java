import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.stb.STBImage.*;
import java.io.IOException;
import java.util.ArrayList;

import org.joml.Vector3f;

import java.lang.Math;
import engine.BufferController;
import engine.Camera;
import engine.CameraController;
import engine.GameObject;
import engine.InputController;
import engine.ShaderController;
import engine.Square;
import engine.Window;
import engine.exceptions.DrawElementsException;
import engine.exceptions.ShaderException;
import engine.exceptions.TextureException;


public class Main {
	
	public static void main(String[] args) throws ShaderException, TextureException, IOException, DrawElementsException {
		
		//Enable GLFW
		glfwInit();

		//Create window and context
		Window window = new Window(1920,1080, "Turn Based RPG", 1, true);
		window.setColor(0, 0, 0, 1);
		glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);	

		//Setup required components and controllers
		BufferController bufferManager = new BufferController();
		Camera camera = new Camera(0,0,0,0,0);
		InputController inputController = new InputController(window, 0.1f);
		ShaderController shaderController = new ShaderController("shader.vert", "shader.frag", window);
		CameraController cameraController = new CameraController(camera, shaderController, inputController);
		stbi_set_flip_vertically_on_load(true);

		//Initialise any shapes that will be used and finally bind the VAO
		Square.initialise(bufferManager);
		bufferManager.bind();
		
		//Create object array. This will be handled by a controller later 
		ArrayList<GameObject> objects = new ArrayList<GameObject>();
		
		
		while(!window.shouldClose()) {
			//Clear window for drawing
			glClear(GL_COLOR_BUFFER_BIT);

			//Draw loop
			for(GameObject object : objects){
				object.draw(shaderController);
			}

			if(inputController.isKeyDown(GLFW_KEY_SPACE)){
				objects.add(new Square(cameraController.getCamera().getX(),
					cameraController.getCamera().getY(),
					cameraController.getCamera().getZ() - 1,
					 0,0,0,0.25f,0.25f,"awesomeface.png"));
			}
			//Perform necessary updates for next frame
			cameraController.update();
			window.update();
		}
		
		window.destroy();
	}

}

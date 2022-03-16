import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.stb.STBImage.*;
import java.io.IOException;
import java.util.ArrayList;

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
	
	public static void main(String[] args) throws ShaderException, TextureException, IOException, DrawElementsException, CloneNotSupportedException {
		
		//Enable GLFW
		glfwInit();

		//Create window and context
		Window window = new Window(1280,720, "Turn Based RPG", 1, false);
		window.setColor(1, 1, 1, 1);
		glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);	

		//Setup required components and controllers
		stbi_set_flip_vertically_on_load(true);
		BufferController bufferManager = new BufferController();
		Camera camera = new Camera(0,0,0,0,0);
		InputController inputController = new InputController(window, 0.1f);
		ShaderController shaderController = new ShaderController("shader.vert", "shader.frag", window);
		CameraController cameraController = new CameraController(camera, shaderController, inputController);
		TextureController textureController = new TextureController("awesomeface.png", "cursor.png");
		glDepthMask(false);
		glDisable(GL_DEPTH_TEST);
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
				object.draw(shaderController, camera, objects);
			}

			if(inputController.isKeyDown(GLFW_KEY_SPACE)){
				objects.add(new Square((float)inputController.getMouseX(),(float)inputController.getMouseY(),0,
					 0,0,0,100,100,"awesomeface.png", textureController));
			}
			
			//Perform necessary updates for next frame
			
			cameraController.update();
			window.update();
			System.gc();
		}
		
		window.destroy();
	}

}

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

import java.io.IOException;
import java.util.ArrayList;

import engine.*;
import engine.exceptions.*;

import matrices.*;
import matrices.exceptions.*;

public class Main {
	
	public static void main(String[] args) throws MathsException, TextureException, DrawElementsException, ShaderException, IOException {
		
        System.out.println("Starting program");		
		//Enable GLFW
		glfwInit();

        System.out.println("glfw initialised. creating window");
		//Create window and context
		Window window = new Window(1920, "Turn Based RPG", 1, false);
		System.out.println("window created");
        window.setColor(1, 1, 1, 1);
		glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);	

		//Setup required components and controllers
		BufferController bufferManager = new BufferController();
		Camera camera = new Camera(0,0,0);
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
        objects.add(new Square(0,0,0,0,0,1,1,"placeholder.png",textureController));
		
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
				
				Vector squarePosition = camera.screenToWorld((float)inputController.getMouseX(), (float)inputController.getMouseY());
			    System.out.println("creating square at "+squarePosition.data[0]+","+squarePosition.data[1]);	
				objects.add(new Square(squarePosition.data[0],squarePosition.data[1],
					 0,0,0,100,100,"placeholder.png", textureController));
			}
			
			if(inputController.rightMouseClicked()){
				
				Vector worldCoord = camera.screenToWorld((float)inputController.getMouseX(), (float)inputController.getMouseY());

				GameObject toRemove = null;
				for(GameObject object: objects){
					
					if(worldCoord.data[0] < object.getX() + object.getXScale() && worldCoord.data[0] > object.getX() - object.getXScale() &&
							worldCoord.data[1] < object.getY() + object.getYScale() && worldCoord.data[1] > object.getY() - object.getYScale()){
						toRemove = object;
					}	
				}
				
				if(toRemove != null){
					objects.remove(toRemove);
				}
			}
			
			if(inputController.isKeyDown(GLFW_KEY_ESCAPE)){
				glfwSetWindowShouldClose(window.getWindow(), true);
			}

			GameLoop.update(window, inputController, cameraController, deltaTime);
			lastTime = time;
		}
		
		window.destroy();

	}
}

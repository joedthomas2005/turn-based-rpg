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
		Window window = new Window(1366, "Turn Based RPG", 1, false);
		System.out.println("window created");
        window.setColor(1, 1, 1, 1);
		glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);	

		//Setup required components and controllers
		BufferController bufferController = new BufferController();
		Camera camera = new Camera(0,0,0);
		InputController inputController = new InputController(window, 1f);
		ShaderController shaderController = new ShaderController("shader.vert", "shader.frag", window);
		CameraController cameraController = new CameraController(camera, shaderController, inputController, 10f);
		TextureController textureController = new TextureController("cursor.png", "placeholder.png");
		
		ArrayList<DrawableCreator> factories = new ArrayList<DrawableCreator>();
		SquareFactory squareFactory = new SquareFactory(textureController);
		factories.add(squareFactory);
		
		//Initialise any shapes that will be used and finally bind the VAO
		
		for(DrawableCreator factory : factories){
			factory.initialise(bufferController);
		}

		bufferController.bind();

		//Create object array. This will be handled by a controller later 
		ArrayList<DrawableGameObject> drawable = new ArrayList<DrawableGameObject>();
		
		Cursor cursor = new Cursor(squareFactory, inputController, camera);
		
		double time = glfwGetTime();
		double lastTime = glfwGetTime();
		double deltaTime = 0;

		while(!window.shouldClose()) {
			
			time = glfwGetTime();
			deltaTime = (time - lastTime) * 100;
			
			glClear(GL_COLOR_BUFFER_BIT);

			//Draw loop
			cursor.update();
			
			for(DrawableGameObject object : drawable){
				object.draw(shaderController, camera);
			}
			cursor.cursor.draw(shaderController, camera);
			
			if(inputController.leftMouseClicked()){
				
				Vector squarePosition = camera.screenToWorld((float)inputController.getMouseX(), (float)inputController.getMouseY());
			    
				System.out.println("creating square at "+squarePosition.data[0]+","+squarePosition.data[1]);	
				drawable.add(squareFactory.create(squarePosition.data[0],squarePosition.data[1],
					 0,100,100,"placeholder.png"));
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
			
			if(inputController.isKeyDown(GLFW_KEY_ESCAPE)){
				glfwSetWindowShouldClose(window.getWindow(), true);
			}

			GameLoop.update(window, inputController, cameraController, deltaTime);
			lastTime = time;
		}
		
		window.destroy();

	}
}

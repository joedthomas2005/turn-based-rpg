import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.stb.STBImage.*;
import java.io.IOException;
import java.util.ArrayList;

import org.joml.Vector3f;

import java.lang.Math;
import engine.BufferController;
import engine.Camera;
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
		
		
		glfwInit();
		//glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);
		Window window = new Window(2560,1440, "Turn Based RPG", 1, true);
		//GLUtil.setupDebugMessageCallback();
		stbi_set_flip_vertically_on_load(true);
		glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);	
		BufferController bufferManager = new BufferController();
		Square.initialise(bufferManager);
		bufferManager.bind();
		InputController inputController = new InputController(window, 0.1f);
		ShaderController shaderController = new ShaderController("shader.vert", "shader.frag", window);
		Camera camera = new Camera(0,0,0,0,0);
		
		ArrayList<GameObject> objects = new ArrayList<GameObject>();
		objects.add(new Square(0,0,-1f,0,0,0,0.25f,0.25f,"awesomeface.png"));
		objects.add(new Square(0,0,-1f,0,0,0,0.1f,0.1f,"cursor.png"));	

		window.setColor(0, 0, 0, 1);
		
		shaderController.use(camera.getView());
		while(!window.shouldClose()) {
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			for(GameObject object : objects){
				object.draw(shaderController);
			}

			if(inputController.isKeyDown(GLFW_KEY_A)){
				camera.move(0.1f, 0, 0, true);
				shaderController.use(camera.getView());
			}
			if(inputController.isKeyDown(GLFW_KEY_D)){
				camera.move(-0.1f, 0, 0, true);
				shaderController.use(camera.getView());
			}
		
			float screenX = (float) (inputController.getMouseX()* 2 / window.getWidth() - 1.0f);
			float screenY = (float) (1.0f - 2.0f * inputController.getMouseX() / window.getHeight());
			Vector3f screen = new Vector3f(screenX, screenY, 1.0f);
		//	Vector3f world = shaderController.getProjection().inverse().mul(screen);


			objects.get(1).setX((float));

			objects.get(1).setY(0.0f - (float) (1.0f - 2.0f * inputController.getMouseY() / window.getHeight()));
			window.update();
			
		}
		
		window.destroy();
	}

}

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.stb.STBImage.*;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.Math;
import engine.BufferController;
import engine.Camera;
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
		Window window = new Window(400,400, "Turn Based RPG", 1, false);
		//GLUtil.setupDebugMessageCallback();
		stbi_set_flip_vertically_on_load(true);
		glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);	
		BufferController bufferManager = new BufferController();
		Square.initialise(bufferManager);
		bufferManager.bind();
		InputController inputController = new InputController(window, 0.1f);
		ShaderController shaderController = new ShaderController("shader.vert", "shader.frag", window);
		Camera camera = new Camera(0,0,0,0,0);
		
		ArrayList<Square> squares = new ArrayList<Square>();
		squares.add(new Square(0,0,0,0,0,0,0.15f,0.2f,"cursor.png"));	

		window.setColor(0, 0, 0, 1);
		
		shaderController.use(camera.getView());
		while(!window.shouldClose()) {
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			for(Square square : squares){
				square.draw(shaderController);
			}

			if(inputController.isKeyDown(GLFW_KEY_A)){
				camera.move(0.1f, 0, 0, true);
				shaderController.use(camera.getView());
			}
			if(inputController.isKeyDown(GLFW_KEY_D)){
				camera.move(-0.1f, 0, 0, true);
				shaderController.use(camera.getView());
			}
		
			squares.get(0).setX((float)inputController.getMouseX()/(float)window.getWidth());
			System.out.println("x:"+inputController.getMouseX());
			System.out.println("y:"+inputController.getMouseY());		
			squares.get(0).setY((float)inputController.getMouseY()/(float)window.getHeight());
			window.update();
			
		}
		
		window.destroy();
	}

}

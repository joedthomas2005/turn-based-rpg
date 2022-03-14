import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.stb.STBImage.*;
import java.io.IOException;


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
		Window window = new Window(450,400, "Turn Based RPG", 1, false);
		//GLUtil.setupDebugMessageCallback();
		stbi_set_flip_vertically_on_load(true);
		
		BufferController bufferManager = new BufferController();
		Square.initialise(bufferManager);
		bufferManager.bind();
		InputController inputController = new InputController(window, 0.1f);
		ShaderController shaderController = new ShaderController("shader.vert", "shader.frag", window);
		Camera camera = new Camera(0,0,0,0,0);
		
		Square square = new Square(0,0,0,0,0,0,1,1,"awesomeface.png");
		
		window.setColor(0, 0, 0, 1);
		shaderController.use(camera.getView());

		while(!window.shouldClose()) {
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			square.draw(shaderController);
			camera.setRot(inputController.getPitch(), 90 + inputController.getYaw());
			//System.out.println(camera.getPitch());
			//System.out.println(camera.getYaw());
			window.update();
			
		}
		
		window.destroy();
	}
	
}

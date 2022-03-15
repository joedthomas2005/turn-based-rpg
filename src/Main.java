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
		Window window = new Window(450,400, "Turn Based RPG", 1, false);
		//GLUtil.setupDebugMessageCallback();
		stbi_set_flip_vertically_on_load(true);
		
		BufferController bufferManager = new BufferController();
		Square.initialise(bufferManager);
		bufferManager.bind();
		InputController inputController = new InputController(window, 0.1f);
		ShaderController shaderController = new ShaderController("shader.vert", "shader.frag", window);
		Camera camera = new Camera(0,0,0,0,0);
		
		ArrayList<Square> squares = new ArrayList<Square>();

		window.setColor(0, 0, 0, 1);
		shaderController.use(camera.getView());

		while(!window.shouldClose()) {
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			for(Square square : squares){
				square.rotate(0,0,1.0f);
				square.draw(shaderController);
			}

			if(inputController.isKeyDown(GLFW_KEY_SPACE)){
				float x = (float)Math.random();
				float y = (float)Math.random();
				if(Math.random() > 0.5){
					x = 0 - x;
				}
				if(Math.random() > 0.5){
					y = 0 - y;
				}

				float roll = (float)Math.random() * (360);
				squares.add(new Square(x,y,0,0,0,roll,1,1,"awesomeface.png"));
			}

			if(inputController.isKeyDown(GLFW_KEY_BACKSPACE)){
				squares.remove(squares.size() - 1);
			}

			camera.setRot(inputController.getPitch(), 90 + inputController.getYaw());
			//System.out.println(camera.getPitch());
			//System.out.println(camera.getYaw());
			window.update();
			
		}
		
		window.destroy();
	}

}

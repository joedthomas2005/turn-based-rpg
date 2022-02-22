import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL;

public class Main {
	
	public static void main(String[] args) {
		
		
		glfwInit();
		
		Window window = new Window(900,800, "Turn Based RPG", 1, false);
		GL.createCapabilities();
		
		window.setColor(1, 1, 0, 1);
		
		while(!window.shouldClose()) {
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			window.update();
			
		}
		
		window.destroy();
	}
	
}

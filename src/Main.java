import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL;

public class Main {
	
	public static void main(String[] args) {
		
		
		glfwInit();
		
		Window window = new Window(900,800, "Turn Based RPG", 1, false);
		
		ArrayList<Float> vertices = new ArrayList<Float>(Arrays.asList(1.2f,3.2f,5.5f,6.6f));
		ArrayList<Integer> indices = new ArrayList<Integer>(Arrays.asList(1,3,5,6));
		
		Buffers BufferManager = new Buffers(vertices,indices);
		
		
		window.setColor(1, 1, 0, 1);
		
		while(!window.shouldClose()) {
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			window.update();
			
		}
		
		window.destroy();
	}
	
}

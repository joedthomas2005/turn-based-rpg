import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import engine.BufferController;
import engine.InputController;
import engine.ShaderController;
import engine.Window;


public class Main {
	
	public static void main(String[] args) throws Exception {
		
		
		glfwInit();
		
		Window window = new Window(900,800, "Turn Based RPG", 1, false);
		
		ArrayList<Float> vertices = new ArrayList<Float>(Arrays.asList(1.2f,3.2f,5.5f,6.6f));
		ArrayList<Integer> indices = new ArrayList<Integer>(Arrays.asList(1,3,5,6));
		
		@SuppressWarnings("unused")
		BufferController bufferManager = new BufferController(vertices,indices);
		@SuppressWarnings("unused")
		InputController inputController = new InputController(window, 0.1f);
		@SuppressWarnings("unused")
		ShaderController shaderController = new ShaderController("shader.vert", "shader.frag", window);
		
		
		window.setColor(1, 1, 0, 1);
		
		while(!window.shouldClose()) {
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			System.out.println(window.getWidth());
			window.update();
			
		}
		
		window.destroy();
	}
	
}

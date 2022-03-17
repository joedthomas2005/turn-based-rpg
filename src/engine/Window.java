package engine;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.*;

import java.nio.*;
import static org.lwjgl.opengl.GL11.*;


public class Window {
	
	private int vsync;
	private long window;
	private long monitor;
	private int width;
	private int height;
	
	private CharSequence title;
	
	public Window(int width, CharSequence title, int vsync, Boolean fullScreen) {
		
		this.title = title;
		this.vsync = vsync;
		
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
		
			this.monitor = 0;
			this.window = 0;
		
			if(fullScreen) {
				this.monitor = glfwGetPrimaryMonitor();
			}
		
			glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
			glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
			this.window = glfwCreateWindow(width, width * 9/16, this.title, monitor, 0);
		
			if(this.window == 0) {
				System.err.println("GLFW FAILED LOADING WINDOW");
			}
		
			glfwMakeContextCurrent(window);
			glfwSwapInterval(this.vsync);
			glfwGetFramebufferSize(window, pWidth, pHeight);
			this.width = pWidth.get();
			this.height = pHeight.get();
			System.out.println(height);
			glfwSetFramebufferSizeCallback(window, GLFWFramebufferSizeCallback.create((windowPointer, newWidth, newHeight) -> {
				this.width = newWidth;
				this.height = newHeight;
				glViewport(0,0,newWidth,newHeight);
				}));
		}
		GL.createCapabilities();
	}
	
	public void initViewport() {
		glViewport(0, 0, width, height);
	}
	
	public void setColor(float r, float g, float b, float a) {
		glClearColor(r,g,b,a);
	}
	
	public Boolean shouldClose() {
		return glfwWindowShouldClose(this.window);
	}
	
	public long getWindow() {
		return this.window;	
	}
	
	public void update() {
		glfwSwapBuffers(this.window);
		glfwPollEvents();
	}
	
	public void destroy() {
		glfwDestroyWindow(this.window);
		glfwTerminate();
		
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
}

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.*;

import java.nio.*;
import static org.lwjgl.opengl.GL11.*;


public class Window {
	
	private int width;
	private int height;
	private int vsync;
	private long window;
	private long monitor;
	private IntBuffer pWidth;
	private IntBuffer pHeight;
	
	private CharSequence title;
	
	public Window(int width, int height, CharSequence title, int vsync, Boolean fullScreen) {
		
		this.width = width;
		this.height = height;
		this.title = title;
		this.vsync = vsync;
		
		MemoryStack stack = MemoryStack.stackPush();
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
		this.window = glfwCreateWindow(width, height, title, monitor, 0);
		
		if(this.window == 0) {
			System.err.println("GLFW FAILED LOADING WINDOW");
		}
		
		glfwMakeContextCurrent(window);
		glfwSwapInterval(vsync);
		glfwGetFramebufferSize(window, pWidth, pHeight);
		
		glfwSetFramebufferSizeCallback(window, GLFWFramebufferSizeCallback.create((windowPointer, newWidth, newHeight) -> {	//Pogger.
			glViewport(0,0,newWidth,newHeight);
			}));
		
		GL.createCapabilities();
	}
	
	public void initViewport() {
		glViewport(0, 0, this.pWidth.get(0), this.pHeight.get(0));
	}
	
	public void setColor(float r, float g, float b, float a) {
		glClearColor(r,g,b,a);
	}
	
	Boolean shouldClose() {
		return glfwWindowShouldClose(this.window);
	}
	
	long getWindow() {
		return this.window;	
	}
	
	void update() {
		glfwSwapBuffers(this.window);
		glfwPollEvents();
	}
	
	void destroy() {
		glfwDestroyWindow(this.window);
		glfwTerminate();
		
	}
	
}
